package com.example.mongocrudapp.presenter;

import com.example.mongocrudapp.model.Session;
import com.example.mongocrudapp.model.repository.SessionRepository;
import com.example.mongocrudapp.utils.MongoDBConnection;
import com.example.mongocrudapp.view.ISessionView;
import com.example.mongocrudapp.view.IUserView;
import com.example.mongocrudapp.view.MainGUI;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;



public class SessionPresenter {
    private final ISessionView iSessionView;
    private final SessionRepository sessionRepository;

    private final UserPresenter userPresenter;

    public SessionPresenter(ISessionView view) {
        this.iSessionView = view;
        MongoDatabase database = MongoDBConnection.connect("sample_mflix");
        this.sessionRepository = new SessionRepository(database);
        this.userPresenter = new UserPresenter(new IUserView() {
            @Override public void displayMessage(String message) {}
            @Override public void setVisibleFieldsUser(Integer i) {}
            @Override public ObjectId getIdUser() { return null; }
            @Override public String getNameUser() { return null; }
            @Override public String getEmailUser() { return null; }
            @Override public String getPasswordUser() { return null; }
            @Override public void setNameUser(String nameUser) {}
            @Override public void setEmailUser(String emailUser) {}
            @Override public void setPasswordUser(String passwordUser) {}
            @Override public void hideWindow() {}
            @Override public void findAllUsers() {}
            @Override public String getIdSearchField() { return null; }
            @Override public String getNameSearchField() { return null; }
            @Override public String getEmailSearchField() { return null; }
            @Override public String getPasswordSearchField() { return null; }
            @Override public JTable getUsersTable() { return null; }
            @Override public JScrollPane getUsersScrollPane() { return null; }
            @Override public void clearFields() {}
            @Override public void setFieldsEnabled(boolean enabled) {}
            @Override public boolean isIdSortChecked() { return false; }
            @Override public boolean isNameSortChecked() { return false; }
            @Override public boolean isEmailSortChecked() { return false; }
            @Override public boolean isPasswordSortChecked() { return false; }
            @Override public boolean isAscendingSortChecked() { return false; }
            @Override public boolean isDescendingSortChecked() { return false; }

            @Override
            public void displayUserCount(long count) {

            }
        });
    }

    public void goBack() {
        MainGUI mainGUI = new MainGUI();
        mainGUI.setVisible(true);
        this.iSessionView.hideWindow();
    }

    public List<Session> getAllSessions() {
        return sessionRepository.getAllSessions();
    }

    public void completeFields(ObjectId selectedSessionId) {
        if (selectedSessionId != null) {
            Session session = sessionRepository.getSessionById(selectedSessionId);
            if (session != null) {
                iSessionView.setSelectedUserId(session.getUserId());
                iSessionView.setJwtForSession(session.getJwt());
            } else {
                iSessionView.displayMessage("Session not found!");
            }
        }
    }

    public void completeFieldsUsers(String selectedUserId) {
        if (selectedUserId != null && !selectedUserId.isEmpty()) {
            try {
                ObjectId userIdAsObjectId = new ObjectId(selectedUserId);
                userPresenter.completeFields(userIdAsObjectId);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid User ID format: " + e.getMessage());
            }
        }
    }


    public void createSession() {
        String selectedUserId = iSessionView.getSelectedUserId();
        String jwt = iSessionView.getJwtField();

        if (selectedUserId == null || jwt == null || jwt.trim().isEmpty()) {
            iSessionView.displayMessage("You must select a User ID and provide a valid JWT!");
            return;
        }

        try {
            ObjectId userIdObjectId = new ObjectId(selectedUserId);
            Session session = new Session(null, userIdObjectId.toHexString(), jwt);

            sessionRepository.createSession(session);
            iSessionView.displayMessage("Session created successfully!");
            iSessionView.findAllSessions();
            iSessionView.clearFields();
        } catch (IllegalStateException e) {
            iSessionView.displayMessage(e.getMessage());
        } catch (IllegalArgumentException ex) {
            iSessionView.displayMessage("Invalid User ID format!");
            ex.printStackTrace();
        } catch (Exception ex) {
            iSessionView.displayMessage("Error creating session: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void updateSession() {
        ObjectId selectedSessionId = (ObjectId) iSessionView.getSessionId();

        if (selectedSessionId == null) {
            iSessionView.displayMessage("No session selected for update.");
            return;
        }

        String updatedUserId = iSessionView.getSelectedUserId();
        String updatedJwt = iSessionView.getJwtField();

        if (updatedUserId.isEmpty() || updatedJwt.isEmpty()) {
            iSessionView.displayMessage("The fields cannot be empty.");
            return;
        }

        try {
            sessionRepository.updateSession(selectedSessionId.toString(), updatedUserId, updatedJwt);
            iSessionView.displayMessage("Session updated successfully!");
            iSessionView.clearFields();
            //iSessionView.refreshSessionsIds();
            iSessionView.findAllSessions();
        } catch (Exception e) {
            iSessionView.displayMessage("Error updating session: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteSession() {
        ObjectId selectedSessionId = (ObjectId) iSessionView.getSessionId();

        if (selectedSessionId == null) {
            iSessionView.displayMessage("Please select a Session ID to delete!");
            return;
        }

        try {
            boolean deleted = sessionRepository.deleteSession(selectedSessionId);

            if (deleted) {
                iSessionView.displayMessage("Session deleted successfully!");
                iSessionView.refreshSessionsIds();
                iSessionView.clearFields();
                iSessionView.findAllSessions();
            } else {
                iSessionView.displayMessage("Session not found or could not be deleted.");
            }
        } catch (Exception e) {
            iSessionView.displayMessage("Error deleting session: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getSessionFieldValue(Session session, String fieldName) {
        try {
            Field field = session.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(session);

            return value != null ? value.toString() : "";
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String[] showSessionFields(List<Session> list) {
        if (list != null && !list.isEmpty()) {
            Session session = list.get(0);
            Field[] fields = session.getClass().getDeclaredFields();
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

    public void createSessionTable(List<Session> list, JTable table, JScrollPane scrollPane) {
        String[] columnNames = showSessionFields(list);

        int numRows = list.size();
        int numCols = columnNames.length;
        String[][] data = new String[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            Session session = list.get(i);
            for (int j = 0; j < numCols; j++) {
                data[i][j] = getSessionFieldValue(session, columnNames[j]);
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
        scrollPane.setViewportView(table);
    }

    public void showFilteredTableData() {
        String idField = iSessionView.getSessionIdSearchField();
        String userId = iSessionView.getUserIdSearchField();
        String jwt = iSessionView.getJwtSearchField();

        boolean isIdFilled = idField != null && !idField.trim().isEmpty();
        boolean isUserIdFilled = userId != null && !userId.trim().isEmpty();
        boolean isJwtFilled = jwt != null && !jwt.trim().isEmpty();

        if (!isIdFilled && !isUserIdFilled && !isJwtFilled) {
            iSessionView.findAllSessions();
            return;
        }

        List<Session> filteredSessions = new ArrayList<>();

        if (isIdFilled) {
            try {
                ObjectId sessionId = new ObjectId(idField);
                if (isUserIdFilled && isJwtFilled) {
                    filteredSessions = sessionRepository.getUsersByIdUserIdAndJwt(sessionId, userId, jwt);
                } else if (isUserIdFilled) {
                    filteredSessions = sessionRepository.getUsersByIdAndUserId(sessionId, userId);
                } else if (isJwtFilled) {
                    filteredSessions = sessionRepository.getUsersByIdAndJwt(sessionId, jwt);
                } else {
                    Session session = sessionRepository.getSessionById(sessionId);
                    if (session != null) filteredSessions.add(session);
                }
            } catch (IllegalArgumentException e) {
                iSessionView.displayMessage("Invalid ID format.");
                return;
            }
        } else {
            if (isUserIdFilled && isJwtFilled) {
                filteredSessions = sessionRepository.getUsersByUserIdAndJwt(userId, jwt);
            } else if (isUserIdFilled) {
                Session session = sessionRepository.getUserByUserId(userId);
                if (session != null) filteredSessions.add(session);
            } else {
                filteredSessions = sessionRepository.getSessionsByJwt(jwt);
            }
        }

        if (!filteredSessions.isEmpty()) {
            createSessionTable(filteredSessions, iSessionView.getSessionsTable(), iSessionView.getSessionsScrollPane());
        } else {
            iSessionView.displayMessage("No sessions found matching the given criteria.");
        }
    }


    public void sortAllSessions() {
        String sortField = null;
        if (iSessionView.isIdSortChecked()) sortField = "_id";
        else if (iSessionView.isUserSortChecked()) sortField = "user_id";
        else if (iSessionView.isJwtSortChecked()) sortField = "jwt";

        if (sortField == null) {
            iSessionView.displayMessage("Please select a field to sort.");
            return;
        }

        boolean ascending = iSessionView.isAscendingSortChecked();
        if (!ascending && !iSessionView.isDescendingSortChecked()) {
            iSessionView.displayMessage("Please select a sorting direction.");
            return;
        }

        List<Session> currentData = getCurrentTableData();
        List<Session> sortedData = sessionRepository.sortSessions(currentData, sortField, ascending);

        createSessionTable(sortedData, iSessionView.getSessionsTable(), iSessionView.getSessionsScrollPane());
    }

    private List<Session> getCurrentTableData() {
        List<Session> sessions = new ArrayList<>();
        JTable table = iSessionView.getSessionsTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            Session session = new Session();
            session.setId(new ObjectId(model.getValueAt(i, 0).toString()));
            session.setUserId(model.getValueAt(i, 1).toString());
            session.setJwt(model.getValueAt(i, 2).toString());
            sessions.add(session);
        }
        return sessions;
    }

    public void showSessionCount() {
        long count = sessionRepository.countSessions();
        iSessionView.displaySessionCount(count);
    }

}
