package com.example.mongocrudapp.presenter;

import com.example.mongocrudapp.model.User;
import com.example.mongocrudapp.model.repository.UserRepository;
import com.example.mongocrudapp.utils.MongoDBConnection;
import com.example.mongocrudapp.view.IUserView;
import com.example.mongocrudapp.view.MainGUI;
import com.example.mongocrudapp.view.UserGUI;
import com.mongodb.client.MongoDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UserPresenter {
    private final UserRepository repository;
    private final IUserView iUserView;

    public UserPresenter(IUserView iUserView) {
        this.iUserView = iUserView;
        MongoDatabase database = MongoDBConnection.connect("sample_mflix");
        this.repository = new UserRepository(database);
    }

    public void goBack() {
        MainGUI mainGUI = new MainGUI();
        mainGUI.setVisible(true);
        this.iUserView.hideWindow();
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public void completeFields(ObjectId selectedId) {
        if (selectedId != null) {
            User user = repository.getUserById(selectedId);
            if (user != null) {
                iUserView.setNameUser(user.getName());
                iUserView.setEmailUser(user.getEmail());
                iUserView.setPasswordUser(user.getPassword());
            }
        }
    }

    public void createUser() {
        String name = iUserView.getNameUser();
        String email = iUserView.getEmailUser();
        String password = iUserView.getPasswordUser();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            iUserView.displayMessage("You need to complete all the fields.");
            return;
        }

        if (repository.getUserByEmail(email) != null) {
            iUserView.displayMessage("This email is already in our database.");
            return;
        }

        try {
            User user = new User(null, name, email, password);
            repository.createUser(user);
            iUserView.displayMessage("User created successfully.");
            iUserView.findAllUsers();
            ((UserGUI) iUserView).refreshUserIds();
            iUserView.clearFields();
        } catch (Exception ex) {
            iUserView.displayMessage("Something went wrong. The operation cannot be executed.");
            ex.printStackTrace();
        }
    }


    public void updateUser() {
        ObjectId id = iUserView.getIdUser();
        if (id == null) {
            iUserView.displayMessage("No user selected for update.");
            return;
        }

        String newName = iUserView.getNameUser();
        String newEmail = iUserView.getEmailUser();
        String newPassword = iUserView.getPasswordUser();

        if (newName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
            iUserView.displayMessage("The fields cannot be empty.");
            return;
        }

        try {
            repository.updateUser(id.toString(), newName, newEmail, newPassword);
            iUserView.displayMessage("User updated successfully!");
            iUserView.clearFields();
            iUserView.findAllUsers();
        } catch (Exception ex) {
            iUserView.displayMessage("Something went wrong. The operation cannot be executed.");
            ex.printStackTrace();
        }
    }


    public void deleteUser() {
        ObjectId id = iUserView.getIdUser();
        if (id == null) {
            iUserView.displayMessage("No user selected for deletion.");
            return;
        }

        try {
            boolean success = repository.deleteUser(id);
            if (success) {
                iUserView.displayMessage("User deleted successfully!");
                ((UserGUI) iUserView).refreshUserIds();
                iUserView.clearFields();
                iUserView.findAllUsers();
            } else {
                iUserView.displayMessage("Something went wrong. The operation cannot be executed.");
            }
        } catch (Exception ex) {
            iUserView.displayMessage("Something went wrong. The operation cannot be executed.");
            ex.printStackTrace();
        }
    }


    private String getUserFieldValue(User user, String fieldName) {
        try {
            Field field = user.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(user);

            return value != null ? value.toString() : "";
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String[] showUserFields(List<User> list) {
        if (list != null && !list.isEmpty()) {
            User user = list.get(0);
            Field[] fields = user.getClass().getDeclaredFields();
            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            return fieldNames;
        } else {
            JOptionPane.showMessageDialog(null, "The list is empty or null.");
            return new String[0];
        }
    }

    public void createUserTable(List<User> list, JTable table, JScrollPane scrollPane) {
        String[] columnNames = showUserFields(list);

        int numRows = list.size();
        int numCols = columnNames.length;
        String[][] data = new String[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            User user = list.get(i);
            for (int j = 0; j < numCols; j++) {
                data[i][j] = getUserFieldValue(user, columnNames[j]);
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
        scrollPane.setViewportView(table);
    }

    public void showFilteredTableData() {
        String idField = iUserView.getIdSearchField();
        String name = iUserView.getNameSearchField();
        String email = iUserView.getEmailSearchField();
        String password = iUserView.getPasswordSearchField();

        boolean isIdFilled       = idField != null && !idField.trim().isEmpty();
        boolean isNameFilled     = name != null && !name.trim().isEmpty();
        boolean isEmailFilled    = email != null && !email.trim().isEmpty();
        boolean isPasswordFilled = password != null && !password.trim().isEmpty();

        if (!isIdFilled && !isNameFilled && !isEmailFilled && !isPasswordFilled) {
            iUserView.findAllUsers();
            return;
        }

        List<User> filteredUsers = new ArrayList<>();

        if (isIdFilled) {
            try {
                ObjectId objId = new ObjectId(idField);
                if (isNameFilled && isEmailFilled && isPasswordFilled) {
                    filteredUsers = repository.getUsersByIdNameEmailAndPassword(objId, name, email, password);
                } else if (isNameFilled && isEmailFilled) {
                    filteredUsers = repository.getUsersByIdNameAndEmail(objId, name, email);
                } else if (isNameFilled && isPasswordFilled) {
                    filteredUsers = repository.getUsersByIdAndPassword(objId, password);
                } else if (isNameFilled) {
                    filteredUsers = repository.getUsersByIdAndName(objId, name);
                } else if (isEmailFilled) {
                    filteredUsers = repository.getUsersByIdAndEmail(objId, email);
                } else if (isPasswordFilled) {
                    filteredUsers = repository.getUsersByIdAndPassword(objId, password);
                } else {
                    User user = repository.getUserById(objId);
                    if (user != null) filteredUsers.add(user);
                }
            } catch (IllegalArgumentException e) {
                iUserView.displayMessage("Invalid ID format.");
                return;
            }
        } else {
            if (isNameFilled && isEmailFilled && isPasswordFilled) {
                filteredUsers = repository.getUsersByNameEmailAndPassword(name, email, password);
            } else if (isNameFilled && isEmailFilled) {
                filteredUsers = repository.getUsersByNameAndEmail(name, email);
            } else if (isNameFilled && isPasswordFilled) {
                filteredUsers = repository.getUsersByNameAndPassword(name, password);
            } else if (isEmailFilled && isPasswordFilled) {
                filteredUsers = repository.getUsersByEmailAndPassword(email, password);
            } else if (isNameFilled) {
                filteredUsers = repository.getUsersByName(name);
            } else if (isEmailFilled) {
                User user = repository.getUserByEmail(email);
                if (user != null) filteredUsers.add(user);
            } else {
                filteredUsers = repository.getUsersByPassword(password);
            }
        }

        if (!filteredUsers.isEmpty()) {
            createUserTable(filteredUsers, iUserView.getUsersTable(), iUserView.getUsersScrollPane());
        } else {
            iUserView.displayMessage("No users found matching the given criteria.");
        }
    }

    public void sortAllUsers() {
        String sortField = null;
        if (iUserView.isIdSortChecked()) sortField = "_id";
        else if (iUserView.isNameSortChecked()) sortField = "name";
        else if (iUserView.isEmailSortChecked()) sortField = "email";
        else if (iUserView.isPasswordSortChecked()) sortField = "password";

        if (sortField == null) {
            iUserView.displayMessage("Please select a field to sort.");
            return;
        }

        boolean ascending = iUserView.isAscendingSortChecked();
        if (!ascending && !iUserView.isDescendingSortChecked()) {
            iUserView.displayMessage("Please select a sorting direction.");
            return;
        }

        List<User> currentData = getCurrentTableData();
        List<User> sortedData = repository.sortUsers(currentData, sortField, ascending);

        createUserTable(sortedData, iUserView.getUsersTable(), iUserView.getUsersScrollPane());
    }

    private List<User> getCurrentTableData() {
        List<User> users = new ArrayList<>();
        JTable table = iUserView.getUsersTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            User user = new User();
            user.setId(new ObjectId(model.getValueAt(i, 0).toString()));
            user.setName(model.getValueAt(i, 1).toString());
            user.setEmail(model.getValueAt(i, 2).toString());
            user.setPassword(model.getValueAt(i, 3).toString());
            users.add(user);
        }
        return users;
    }

    public void showUserCount() {
        long count = repository.countUsers();
        iUserView.displayUserCount(count);
    }

}
