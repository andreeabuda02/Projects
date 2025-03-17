package com.example.mongocrudapp.view;

import com.example.mongocrudapp.model.User;
import com.example.mongocrudapp.presenter.UserPresenter;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class UserGUI extends JFrame implements IUserView {
    private final JLabel titleText = new JLabel("Here you can proceed many operations with the users:");

    private final JButton createUserButton = new JButton("CREATE");
    private final JButton updateUserButton = new JButton("UPDATE");
    private final JButton deleteUserButton = new JButton("DELETE");
    private final JButton findUserButton = new JButton("FIND");

    private final JButton backButton = new JButton("Back");

    private final JComboBox<ObjectId> idUserField = new JComboBox<>();
    private final JTextField nameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField passwordField = new JTextField();

    private final JLabel idUserLabel = new JLabel("id:");
    private final JLabel nameLabel = new JLabel("name:");
    private final JLabel emailLabel = new JLabel("email:");
    private final JLabel passwordLabel = new JLabel("password:");

    private final JButton executeButton = new JButton("Execute");

    private final JLabel findText = new JLabel("Find the users by they data:");

    private final JTextField idSearchField = new JTextField();
    private final JTextField nameSearchField = new JTextField();
    private final JTextField emailSearchField = new JTextField();
    private final JTextField passwordSearchField = new JTextField();

    private final JLabel idSearchLabel = new JLabel("Id:");
    private final JLabel nameSearchLabel = new JLabel("Name:");
    private final JLabel emailSearchLabel = new JLabel("Email:");
    private final JLabel passwordSearchLabel = new JLabel("Password:");

    private final JButton searchButton = new JButton("Search");

    private final JLabel sortText = new JLabel("If you want to sort them, please select the fields on with do you want to sort them:");

    private final JLabel idSortLabel = new JLabel("Id");
    private final JLabel nameSortLabel = new JLabel("Name");
    private final JLabel emailSortLabel = new JLabel("Email");
    private final JLabel passwordSortLabel = new JLabel("Password");
    private final JCheckBox idSortBox = new JCheckBox();
    private final JCheckBox nameSortBox = new JCheckBox();
    private final JCheckBox emailSortBox = new JCheckBox();
    private final JCheckBox passwordSortBox = new JCheckBox();

    private final JLabel directionText = new JLabel("Do you want to sort them ascending or descending?");

    private final JLabel ascendingSortLabel = new JLabel("Ascending");
    private final JLabel descendingSortLabel = new JLabel("Descending");
    private final JCheckBox ascendingSortBox = new JCheckBox();
    private final JCheckBox descendingSortBox = new JCheckBox();
    private final JButton sortButton = new JButton("Sort");

    private final JButton countButton = new JButton("Count");

    private final JTextField countField = new JTextField();

    private final JLabel countLabel = new JLabel("The number of users in this database is = ");


    private final JTable usersTable = new JTable();
    private final JScrollPane usersScrollPane = new JScrollPane(usersTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private static UserGUI frame;

    private final UserPresenter presenter;
    private String actionType;


    private void interfaceAspect() {
        titleText.setBounds(400, 10, 700, 80);
        titleText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 25));

        createUserButton.setBounds(250, 100, 200, 50);
        updateUserButton.setBounds(500, 100, 200, 50);
        deleteUserButton.setBounds(750, 100, 200, 50);
        findUserButton.setBounds(1000, 100, 200, 50);
        createUserButton.setFont(new Font("Arial", Font.BOLD, 15));
        updateUserButton.setFont(new Font("Arial", Font.BOLD, 15));
        deleteUserButton.setFont(new Font("Arial", Font.BOLD, 15));
        findUserButton.setFont(new Font("Arial", Font.BOLD, 15));

        backButton.setBounds(700, 750, 150, 40);
        backButton.setBackground(new Color(184, 143, 239));

        executeButton.setBounds(720, 630, 100, 40);
        executeButton.setVisible(false);

        idUserLabel.setBounds(600, 300, 70, 40);
        idUserField.setBounds(670, 300, 200, 40);
        nameLabel.setBounds(600, 350, 70, 40);
        nameField.setBounds(670, 350, 200, 40);
        emailLabel.setBounds(600, 400, 70, 40);
        emailField.setBounds(670, 400, 200, 40);
        passwordLabel.setBounds(600, 450, 70, 40);
        passwordField.setBounds(670, 450, 200, 40);

        idUserLabel.setVisible(false);
        idUserField.setVisible(false);
        nameLabel.setVisible(false);
        nameField.setVisible(false);
        emailLabel.setVisible(false);
        emailField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);

        usersScrollPane.setBounds(50, 360, 1440, 370);
        usersTable.setEnabled(false);
        usersScrollPane.setViewportView(usersTable);
        usersScrollPane.setVisible(false);

        findText.setBounds(100, 160, 600, 20);
        findText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        findText.setVisible(false);

        idSearchLabel.setBounds(100, 190, 20, 30);
        idSearchField.setBounds(120, 190, 200, 30);
        nameSearchLabel.setBounds(340, 190, 50, 30);
        nameSearchField.setBounds(380, 190, 200, 30);
        emailSearchLabel.setBounds(600, 190, 50, 30);
        emailSearchField.setBounds(640, 190, 200, 30);
        passwordSearchLabel.setBounds(860, 190, 60, 30);
        passwordSearchField.setBounds(922, 190, 200, 30);

        searchButton.setBounds(1200, 185, 100, 40);

        idSearchLabel.setVisible(false);
        idSearchField.setVisible(false);
        nameSearchLabel.setVisible(false);
        nameSearchField.setVisible(false);
        emailSearchLabel.setVisible(false);
        emailSearchField.setVisible(false);
        passwordSearchLabel.setVisible(false);
        passwordSearchField.setVisible(false);
        searchButton.setVisible(false);


        sortText.setBounds(100, 240, 700, 20);
        sortText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        sortText.setVisible(false);

        idSortLabel.setBounds(100, 260, 10, 30);
        idSortBox.setBounds(110, 265, 20, 20);
        idSortBox.setBackground(new Color(200, 200, 232));

        nameSortLabel.setBounds(200, 260, 50, 30);
        nameSortBox.setBounds(230, 265, 20, 20);
        nameSortBox.setBackground(new Color(200, 200, 232));

        emailSortLabel.setBounds(320, 260, 50, 30);
        emailSortBox.setBounds(350, 265, 20, 20);
        emailSortBox.setBackground(new Color(200, 200, 232));

        passwordSortLabel.setBounds(440, 260, 60, 30);
        passwordSortBox.setBounds(495, 265, 20, 20);
        passwordSortBox.setBackground(new Color(200, 200, 232));

        directionText.setBounds(100, 300, 400, 20);
        directionText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        directionText.setVisible(false);

        ascendingSortLabel.setBounds(100, 320, 60, 30);
        ascendingSortBox.setBounds(160, 325, 20, 20);
        ascendingSortBox.setBackground(new Color(200, 200, 232));

        descendingSortLabel.setBounds(200, 320, 70, 30);
        descendingSortBox.setBounds(270, 325, 20, 20);
        descendingSortBox.setBackground(new Color(200, 200, 232));

        sortButton.setBounds(800, 240, 100, 40);

        idSortLabel.setVisible(false);
        idSortBox.setVisible(false);
        nameSortLabel.setVisible(false);
        nameSortBox.setVisible(false);
        emailSortLabel.setVisible(false);
        emailSortBox.setVisible(false);
        passwordSortLabel.setVisible(false);
        passwordSortBox.setVisible(false);

        ascendingSortLabel.setVisible(false);
        descendingSortLabel.setVisible(false);
        ascendingSortBox.setVisible(false);
        descendingSortBox.setVisible(false);

        sortButton.setVisible(false);

        countButton.setBounds(1200, 240, 100, 40);
        countLabel.setBounds(1100, 300, 300, 20);
        countField.setBounds(1350, 300, 60, 20);

        countLabel.setVisible(false);
        countField.setVisible(false);
        countButton.setVisible(false);
    }

    private void addComponents() {
        add(titleText);
        add(backButton);
        add(executeButton);

        add(createUserButton);
        add(updateUserButton);
        add(deleteUserButton);
        add(findUserButton);

        add(idUserField);
        add(idUserLabel);
        add(nameField);
        add(nameLabel);
        add(emailField);
        add(emailLabel);
        add(passwordField);
        add(passwordLabel);

        add(usersTable);
        add(usersScrollPane);

        add(findText);
        add(idSearchField);
        add(idSearchLabel);
        add(nameSearchField);
        add(nameSearchLabel);
        add(emailSearchField);
        add(emailSearchLabel);
        add(passwordSearchField);
        add(passwordSearchLabel);
        add(searchButton);

        add(sortText);
        add(idSortLabel);
        add(idSortBox);
        add(nameSortLabel);
        add(nameSortBox);
        add(emailSortLabel);
        add(emailSortBox);
        add(passwordSortLabel);
        add(passwordSortBox);
        add(directionText);
        add(ascendingSortLabel);
        add(ascendingSortBox);
        add(descendingSortLabel);
        add(descendingSortBox);
        add(sortButton);

        add(countField);
        add(countLabel);
        add(countButton);
    }

    public UserGUI() throws HeadlessException {
        super("User Management Page");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        frame = this;

        this.interfaceAspect();
        this.addComponents();

        presenter = new UserPresenter(this);

        JPanel windowP =new JPanel();
        windowP.setBackground(new Color(200, 200, 232));
        windowP.setLayout(null);
        add(windowP);

        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(false);
        this.setVisible(true);

        initializeUserIds();
        configureSortCheckBoxes();

        backButton.addActionListener(e -> frame.presenter.goBack());

        idUserField.addItemListener(e -> frame.presenter.completeFields((ObjectId) idUserField.getSelectedItem()));

        createUserButton.addActionListener(e -> {
            System.out.println("Debug: 'Create' button clicked.");

            actionType = "Create";
            displayMessage("Now you can add an user. Use an unique email.");

            clearFields();
            setFieldsEnabled(true);

            idUserField.setEnabled(false);

            setVisibleFieldsUser(1);
        });

        updateUserButton.addActionListener(e -> {
            System.out.println("Debug: 'Update' button clicked.");

            actionType = "Update";
            displayMessage("Now you can update an user. Please choose the user by it's id.");
            clearFields();
            setFieldsEnabled(true);
            idUserField.setEnabled(true);
            frame.presenter.completeFields((ObjectId) idUserField.getSelectedItem());
            setVisibleFieldsUser(2);
        });

        deleteUserButton.addActionListener(e -> {
            System.out.println("Debug: 'Delete' button clicked.");

            actionType = "Delete";
            displayMessage("Now you can delete an user. Please choose the user by it's id.");
            clearFields();
            setFieldsEnabled(false);
            idUserField.setEnabled(true);
            frame.presenter.completeFields((ObjectId) idUserField.getSelectedItem());
            setVisibleFieldsUser(3);
        });


        findUserButton.addActionListener(e -> {
            actionType = "Find";
            clearFields();
            frame.findAllUsers();
            setVisibleFieldsUser(4);
        });


        executeButton.addActionListener(e -> {
            if ("Create".equals(actionType)) {
                System.out.println("Debug: Executing 'Create' action.");
                frame.presenter.createUser();
            } else if ("Update".equals(actionType)) {
                System.out.println("Debug: Executing 'Update' action.");
                frame.presenter.updateUser();
            } else if ("Delete".equals(actionType)) {
                System.out.println("Debug: Executing 'Delete' action.");
                frame.presenter.deleteUser();
            }
            clearFields();
        });

        searchButton.addActionListener(e -> frame.presenter.showFilteredTableData());

        sortButton.addActionListener(e ->  frame.presenter.sortAllUsers());

        ascendingSortBox.addActionListener(e -> {
            if (ascendingSortBox.isSelected()) {
                descendingSortBox.setSelected(false);
            }
        });

        descendingSortBox.addActionListener(e -> {
            if (descendingSortBox.isSelected()) {
                ascendingSortBox.setSelected(false);
            }
        });

        countButton.addActionListener(e -> {
            System.out.println("Debug: 'Count' button clicked.");
            presenter.showUserCount();
        });

    }

    @Override
    public void hideWindow() {
        this.setVisible(false);
    }

    @Override
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void initializeUserIds() {
        List<User> users = presenter.getAllUsers();
        if (users != null && !(users).isEmpty()) {
            for (User user : users) {
                idUserField.addItem(user.getId());
                System.out.println("Debug: Loaded User ID -> " + user.getId());
            }
        } else {
            idUserField.addItem(null);
            System.out.println("Debug: No User IDs found.");
        }
    }

    public void refreshUserIds() {
        idUserField.removeAllItems();

        List<User> users = presenter.getAllUsers();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                idUserField.addItem(user.getId());
            }
        }
    }

    @Override
    public void setVisibleFieldsUser(Integer i) {
        if (i == 1) {
            idUserLabel.setVisible(false);
            idUserField.setVisible(false);
            nameLabel.setVisible(true);
            nameField.setVisible(true);
            emailLabel.setVisible(true);
            emailField.setVisible(true);
            passwordLabel.setVisible(true);
            passwordField.setVisible(true);
            executeButton.setVisible(true);

            usersTable.setVisible(false);
            usersScrollPane.setVisible(false);

            findText.setVisible(false);
            idSearchLabel.setVisible(false);
            idSearchField.setVisible(false);
            nameSearchLabel.setVisible(false);
            nameSearchField.setVisible(false);
            emailSearchLabel.setVisible(false);
            emailSearchField.setVisible(false);
            passwordSearchLabel.setVisible(false);
            passwordSearchField.setVisible(false);
            searchButton.setVisible(false);

            sortText.setVisible(false);
            idSortLabel.setVisible(false);
            idSortBox.setVisible(false);
            nameSortLabel.setVisible(false);
            nameSortBox.setVisible(false);
            emailSortLabel.setVisible(false);
            emailSortBox.setVisible(false);
            passwordSortLabel.setVisible(false);
            passwordSortBox.setVisible(false);
            directionText.setVisible(false);
            ascendingSortLabel.setVisible(false);
            ascendingSortBox.setVisible(false);
            descendingSortLabel.setVisible(false);
            descendingSortBox.setVisible(false);
            sortButton.setVisible(false);

            countLabel.setVisible(false);
            countField.setVisible(false);
            countButton.setVisible(false);
        } else {
            if(i == 2) {
                idUserLabel.setVisible(true);
                idUserField.setVisible(true);
                nameLabel.setVisible(true);
                nameField.setVisible(true);
                emailLabel.setVisible(true);
                emailField.setVisible(true);
                passwordLabel.setVisible(true);
                passwordField.setVisible(true);
                executeButton.setVisible(true);

                usersTable.setVisible(false);
                usersScrollPane.setVisible(false);

                findText.setVisible(false);
                idSearchLabel.setVisible(false);
                idSearchField.setVisible(false);
                nameSearchLabel.setVisible(false);
                nameSearchField.setVisible(false);
                emailSearchLabel.setVisible(false);
                emailSearchField.setVisible(false);
                passwordSearchLabel.setVisible(false);
                passwordSearchField.setVisible(false);
                searchButton.setVisible(false);

                sortText.setVisible(false);
                idSortLabel.setVisible(false);
                idSortBox.setVisible(false);
                nameSortLabel.setVisible(false);
                nameSortBox.setVisible(false);
                emailSortLabel.setVisible(false);
                emailSortBox.setVisible(false);
                passwordSortLabel.setVisible(false);
                passwordSortBox.setVisible(false);
                directionText.setVisible(false);
                ascendingSortLabel.setVisible(false);
                ascendingSortBox.setVisible(false);
                descendingSortLabel.setVisible(false);
                descendingSortBox.setVisible(false);
                sortButton.setVisible(false);

                countLabel.setVisible(false);
                countField.setVisible(false);
                countButton.setVisible(false);
            }
            else {
                if(i==3){
                    idUserLabel.setVisible(true);
                    idUserField.setVisible(true);
                    nameField.setEnabled(true);
                    emailField.setEnabled(true);
                    passwordField.setEnabled(true);
                    nameLabel.setVisible(true);
                    nameField.setVisible(true);
                    emailLabel.setVisible(true);
                    emailField.setVisible(true);
                    passwordLabel.setVisible(true);
                    passwordField.setVisible(true);
                    executeButton.setVisible(true);

                    usersTable.setVisible(false);
                    usersScrollPane.setVisible(false);

                    findText.setVisible(false);
                    idSearchLabel.setVisible(false);
                    idSearchField.setVisible(false);
                    nameSearchLabel.setVisible(false);
                    nameSearchField.setVisible(false);
                    emailSearchLabel.setVisible(false);
                    emailSearchField.setVisible(false);
                    passwordSearchLabel.setVisible(false);
                    passwordSearchField.setVisible(false);
                    searchButton.setVisible(false);

                    sortText.setVisible(false);
                    idSortLabel.setVisible(false);
                    idSortBox.setVisible(false);
                    nameSortLabel.setVisible(false);
                    nameSortBox.setVisible(false);
                    emailSortLabel.setVisible(false);
                    emailSortBox.setVisible(false);
                    passwordSortLabel.setVisible(false);
                    passwordSortBox.setVisible(false);
                    directionText.setVisible(false);
                    ascendingSortLabel.setVisible(false);
                    ascendingSortBox.setVisible(false);
                    descendingSortLabel.setVisible(false);
                    descendingSortBox.setVisible(false);
                    sortButton.setVisible(false);

                    countLabel.setVisible(false);
                    countField.setVisible(false);
                    countButton.setVisible(false);
                }else{
                    if(i==4){
                        idUserLabel.setVisible(false);
                        idUserField.setVisible(false);
                        nameLabel.setVisible(false);
                        nameField.setVisible(false);
                        emailLabel.setVisible(false);
                        emailField.setVisible(false);
                        passwordLabel.setVisible(false);
                        passwordField.setVisible(false);
                        executeButton.setVisible(false);

                        usersTable.setVisible(true);
                        usersScrollPane.setVisible(true);

                        findText.setVisible(true);
                        idSearchLabel.setVisible(true);
                        idSearchField.setVisible(true);
                        nameSearchLabel.setVisible(true);
                        nameSearchField.setVisible(true);
                        emailSearchLabel.setVisible(true);
                        emailSearchField.setVisible(true);
                        passwordSearchLabel.setVisible(true);
                        passwordSearchField.setVisible(true);
                        searchButton.setVisible(true);

                        sortText.setVisible(true);
                        idSortLabel.setVisible(true);
                        idSortBox.setVisible(true);
                        nameSortLabel.setVisible(true);
                        nameSortBox.setVisible(true);
                        emailSortLabel.setVisible(true);
                        emailSortBox.setVisible(true);
                        passwordSortLabel.setVisible(true);
                        passwordSortBox.setVisible(true);

                        directionText.setVisible(true);
                        ascendingSortLabel.setVisible(true);
                        ascendingSortBox.setVisible(true);
                        descendingSortLabel.setVisible(true);
                        descendingSortBox.setVisible(true);
                        sortButton.setVisible(true);

                        countLabel.setVisible(true);
                        countField.setVisible(true);
                        countButton.setVisible(true);
                    }
                }
            }
        }
    }

    @Override
    public void clearFields() {
        idUserField.setSelectedItem(null);
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");

        idSearchField.setText("");
        nameSearchField.setText("");
        emailSearchField.setText("");
        passwordSearchField.setText("");

        countField.setText("");
    }

    @Override
    public void setFieldsEnabled(boolean enabled) {
        nameField.setEnabled(enabled);
        emailField.setEnabled(enabled);
        passwordField.setEnabled(enabled);
    }

    @Override
    public ObjectId getIdUser() {
        return (ObjectId) idUserField.getSelectedItem();
    }

    @Override
    public String getNameUser() {
        return nameField.getText();
    }

    @Override
    public String getEmailUser() {
        return emailField.getText();
    }

    @Override
    public String getPasswordUser() {
        return passwordField.getText();
    }

    @Override
    public void setNameUser(String nameUser) {
        this.nameField.setText(nameUser);
    }

    @Override
    public void setEmailUser(String emailUser) {
        this.emailField.setText(emailUser);
    }

    @Override
    public void setPasswordUser(String passwordUser) {
        this.passwordField.setText(passwordUser);
    }

    @Override
    public void findAllUsers() {
        frame.setTitle("Users Table");

        List<User> users = presenter.getAllUsers();
        if (users != null && !users.isEmpty()) {
            System.out.println("Debug: Total users found -> " + users.size());
            presenter.createUserTable(users, usersTable, usersScrollPane);
        } else {
            displayMessage("No users found...");
        }
    }

    @Override
    public String getIdSearchField() {
        return idSearchField.getText();
    }


    @Override
    public String getNameSearchField() {
        return nameSearchField.getText();
    }

    @Override
    public String getEmailSearchField() {
        return emailSearchField.getText();
    }

    @Override
    public String getPasswordSearchField() {
        return passwordSearchField.getText();
    }

    @Override
    public JTable getUsersTable() {
        return this.usersTable;
    }

    @Override
    public JScrollPane getUsersScrollPane() {
        return this.usersScrollPane;
    }

    //sort part
    private void configureSortCheckBoxes() {
        idSortBox.addActionListener(e -> {
            if (idSortBox.isSelected()) {
                deselectOtherCheckBoxes("id");
            }
        });

        nameSortBox.addActionListener(e -> {
            if (nameSortBox.isSelected()) {
                deselectOtherCheckBoxes("name");
            }
        });

        emailSortBox.addActionListener(e -> {
            if (emailSortBox.isSelected()) {
                deselectOtherCheckBoxes("email");
            }
        });

        passwordSortBox.addActionListener(e -> {
            if (passwordSortBox.isSelected()) {
                deselectOtherCheckBoxes("password");
            }
        });
    }

    private void deselectOtherCheckBoxes(String selected) {
        idSortBox.setSelected("id".equals(selected));
        nameSortBox.setSelected("name".equals(selected));
        emailSortBox.setSelected("email".equals(selected));
        passwordSortBox.setSelected("password".equals(selected));
    }

    @Override
    public boolean isIdSortChecked() {
        return idSortBox.isSelected();
    }

    @Override
    public boolean isNameSortChecked() {
        return nameSortBox.isSelected();
    }

    @Override
    public boolean isEmailSortChecked() {
        return emailSortBox.isSelected();
    }

    @Override
    public boolean isPasswordSortChecked() {
        return passwordSortBox.isSelected();
    }

    @Override
    public boolean isAscendingSortChecked() {
        return ascendingSortBox.isSelected();
    }

    @Override
    public boolean isDescendingSortChecked() {
        return descendingSortBox.isSelected();
    }

    @Override
    public void displayUserCount(long count) {
        countField.setText(String.valueOf(count));
        System.out.println("Debug: Total users displayed -> " + count);
    }


}

