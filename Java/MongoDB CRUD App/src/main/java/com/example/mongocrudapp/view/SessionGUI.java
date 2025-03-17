package com.example.mongocrudapp.view;

import com.example.mongocrudapp.model.Session;
import com.example.mongocrudapp.model.User;
import com.example.mongocrudapp.presenter.SessionPresenter;
import com.example.mongocrudapp.presenter.UserPresenter;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Objects;

public class SessionGUI extends JFrame implements ISessionView {
    private final JLabel titleText = new JLabel("Here you can proceed many operations with the sessions:");

    private final JButton createSessionButton = new JButton("CREATE");
    private final JButton updateSessionButton = new JButton("UPDATE");
    private final JButton deleteSessionButton = new JButton("DELETE");
    private final JButton findSessionButton = new JButton("FIND");

    private final JButton backButton = new JButton("Back");
    private final JButton executeButton = new JButton("Execute");

    private final JComboBox<ObjectId> sessionIdField = new JComboBox<>();
    private final JComboBox<String> userIdField = new JComboBox<>();
    private final JTextField jwtField = new JTextField();

    private final JLabel sessionIdLabel = new JLabel("Session ID:");
    private final JLabel userIdLabel = new JLabel("User ID:");
    private final JLabel jwtLabel = new JLabel("JWT:");

    private final JLabel sessionIdSearchLabel = new JLabel("Session ID:");
    private final JLabel userIdSearchLabel = new JLabel("User ID:");
    private final JLabel jwtSearchLabel = new JLabel("JWT:");

    private final JTextField sessionIdSearchField = new JTextField();
    private final JTextField userIdSearchField = new JTextField();
    private final JTextField jwtSearchField = new JTextField();

    private final JLabel findText = new JLabel("Find sessions by details:");
    private final JButton searchButton = new JButton("Search");

    private final JLabel sortText = new JLabel("Sort sessions by field:");
    private final JLabel directionText = new JLabel("Sort direction:");

    private final JCheckBox idSortBox = new JCheckBox("ID");
    private final JCheckBox userIdSortBox = new JCheckBox("User ID");
    private final JCheckBox jwtSortBox = new JCheckBox("JWT");

    private final JCheckBox ascendingSortBox = new JCheckBox("Ascending");
    private final JCheckBox descendingSortBox = new JCheckBox("Descending");
    private final JLabel idSortLabel = new JLabel("ID");
    private final JLabel userIdSortLabel = new JLabel("User ID");
    private final JLabel jwtSortLabel = new JLabel("JWT");

    private final JLabel ascendingLabel = new JLabel("Ascending");
    private final JLabel descendingLabel = new JLabel("Descending");

    private final JButton sortButton = new JButton("Sort");

    private final JButton countButton = new JButton("Count");

    private final JTextField countField = new JTextField();

    private final JLabel countLabel = new JLabel("The number of sessions in this database is = ");

    private final JTable sessionsTable = new JTable();
    private final JScrollPane sessionsScrollPane = new JScrollPane(sessionsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private static SessionGUI frame;
    private final SessionPresenter sessionPresenter;

    IUserView userView = new UserGUI();
    UserPresenter userPresenter = new UserPresenter(userView);

    private String actionType;

    private void interfaceAspect() {
        titleText.setBounds(400, 10, 700, 80);
        titleText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 25));

        createSessionButton.setBounds(250, 100, 200, 50);
        updateSessionButton.setBounds(500, 100, 200, 50);
        deleteSessionButton.setBounds(750, 100, 200, 50);
        findSessionButton.setBounds(1000, 100, 200, 50);
        createSessionButton.setFont(new Font("Arial", Font.BOLD, 15));
        updateSessionButton.setFont(new Font("Arial", Font.BOLD, 15));
        deleteSessionButton.setFont(new Font("Arial", Font.BOLD, 15));
        findSessionButton.setFont(new Font("Arial", Font.BOLD, 15));

        backButton.setBounds(700, 750, 150, 40);
        backButton.setBackground(new Color(184, 143, 239));

        executeButton.setBounds(720, 630, 100, 40);
        executeButton.setVisible(false);

        findText.setBounds(100, 160, 600, 20);
        findText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        findText.setVisible(false);

        sessionIdLabel.setBounds(600, 300, 70, 40);
        sessionIdField.setBounds(670, 300, 200, 40);
        userIdLabel.setBounds(600, 350, 70, 40);
        userIdField.setBounds(670, 350, 200, 40);
        jwtLabel.setBounds(600, 400, 70, 40);
        jwtField.setBounds(670, 400, 200, 40);

        sessionIdLabel.setVisible(false);
        sessionIdField.setVisible(false);
        userIdLabel.setVisible(false);
        userIdField.setVisible(false);
        jwtField.setVisible(false);
        jwtLabel.setVisible(false);

        sessionsScrollPane.setBounds(50, 360, 1440, 370);
        sessionsTable.setEnabled(false);
        sessionsScrollPane.setViewportView(sessionsTable);
        sessionsScrollPane.setVisible(false);

        sessionIdSearchLabel.setBounds(100, 190, 70, 30);
        sessionIdSearchField.setBounds(170, 190, 200, 30);
        userIdSearchLabel.setBounds(400, 190, 60, 30);
        userIdSearchField.setBounds(460, 190, 200, 30);
        jwtSearchLabel.setBounds(690, 190, 60, 30);
        jwtSearchField.setBounds(730, 190, 200, 30);

        searchButton.setBounds(1000, 185, 100, 40);

        sessionIdSearchLabel.setVisible(false);
        sessionIdSearchField.setVisible(false);
        userIdSearchLabel.setVisible(false);
        userIdSearchField.setVisible(false);
        jwtSearchLabel.setVisible(false);
        jwtSearchField.setVisible(false);
        searchButton.setVisible(false);


        sortText.setBounds(100, 240, 700, 20);
        sortText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        sortText.setVisible(false);


        idSortLabel.setBounds(140, 265, 50, 20);
        idSortBox.setBounds(110, 265, 20, 20);

        userIdSortLabel.setBounds(260, 265, 70, 20);
        userIdSortBox.setBounds(230, 265, 20, 20);

        jwtSortLabel.setBounds(380, 265, 50, 20);
        jwtSortBox.setBounds(350, 265, 20, 20);

        ascendingLabel.setBounds(140, 325, 70, 20);
        ascendingSortBox.setBounds(110, 325, 20, 20);
        ascendingSortBox.setBackground(new Color(200, 200, 232));

        descendingLabel.setBounds(260, 325, 90, 20);
        descendingSortBox.setBounds(230, 325, 20, 20);
        descendingSortBox.setBackground(new Color(200, 200, 232));


        idSortBox.setBackground(new Color(200, 200, 232));
        userIdSortBox.setBackground(new Color(200, 200, 232));
        jwtSortBox.setBackground(new Color(200, 200, 232));

        directionText.setBounds(100, 300, 400, 20);
        directionText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        directionText.setVisible(false);

        sortButton.setBounds(500, 250, 100, 40);

        idSortBox.setVisible(false);
        userIdSortBox.setVisible(false);
        jwtSortBox.setVisible(false);
        idSortLabel.setVisible(false);
        userIdSortLabel.setVisible(false);
        jwtSortLabel.setVisible(false);

        ascendingSortBox.setVisible(false);
        descendingSortBox.setVisible(false);
        ascendingLabel.setVisible(false);
        descendingLabel.setVisible(false);

        sortButton.setVisible(false);

        countButton.setBounds(1200, 240, 100, 40);
        countLabel.setBounds(1100, 300, 300, 20);
        countField.setBounds(1360, 300, 60, 20);

        countLabel.setVisible(false);
        countField.setVisible(false);
        countButton.setVisible(false);

    }

    private void addComponents() {
        add(titleText);
        add(createSessionButton);
        add(updateSessionButton);
        add(deleteSessionButton);
        add(findSessionButton);
        add(backButton);
        add(executeButton);

        add(sessionIdLabel);
        add(sessionIdField);
        add(userIdLabel);
        add(userIdField);
        add(jwtLabel);
        add(jwtField);

        add(findText);
        add(sessionIdSearchField);
        add(userIdSearchField);
        add(jwtSearchField);
        add(sessionIdSearchLabel);
        add(userIdSearchLabel);
        add(jwtSearchLabel);

        add(searchButton);

        add(sortText);
        add(idSortBox);
        add(userIdSortBox);
        add(jwtSortBox);
        add(directionText);
        add(ascendingSortBox);
        add(descendingSortBox);
        add(sortButton);

        add(idSortLabel);
        add(userIdSortLabel);
        add(jwtSortLabel);
        add(ascendingLabel);
        add(descendingLabel);

        add(sessionsScrollPane);

        add(countField);
        add(countLabel);
        add(countButton);
    }

    public SessionGUI() throws HeadlessException {
        super("Session Management Page");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        frame = this;

        this.interfaceAspect();
        this.addComponents();

        sessionPresenter = new SessionPresenter(this);

        JPanel windowP = new JPanel();
        windowP.setBackground(new Color(200, 200, 232));
        windowP.setLayout(null);
        add(windowP);

        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(false);
        this.setVisible(true);

        initializeUserIds();
        initializeSessionsIds();
        configureSortCheckBoxes();

        backButton.addActionListener(e -> frame.sessionPresenter.goBack());

        sessionIdField.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ObjectId selectedSessionId = (ObjectId) sessionIdField.getSelectedItem();
                System.out.println("Debug: Selected Session ID -> " + selectedSessionId);
                if (selectedSessionId != null) {
                    sessionPresenter.completeFields(selectedSessionId);
                }
            }
        });

        userIdField.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedUserId = (String) userIdField.getSelectedItem();
                System.out.println("Debug: Selected User ID -> " + selectedUserId);
                frame.sessionPresenter.completeFieldsUsers(selectedUserId);
            }
        });

        createSessionButton.addActionListener(e -> {
            System.out.println("Debug: 'Create' button clicked");
            actionType = "Create";
            displayMessage("Now you can add a session.");
            clearFields();
            setFieldsEnabled(true);
            sessionIdField.setEnabled(false);
            setVisibleFieldsSession(1);
        });

        updateSessionButton.addActionListener(e -> {
            System.out.println("Debug: 'Update' button clicked");
            actionType = "Update";
            displayMessage("Now you can update a session.");
            clearFields();
            setFieldsEnabled(true);
            sessionIdField.setEnabled(true);
            frame.sessionPresenter.completeFields((ObjectId) sessionIdField.getSelectedItem());
            setVisibleFieldsSession(2);
        });

        deleteSessionButton.addActionListener(e -> {
            System.out.println("Debug: 'Delete' button clicked");
            actionType = "Delete";
            displayMessage("Now you can delete a session. ");
            clearFields();
            setFieldsEnabled(false);
            sessionIdField.setEnabled(true);
            frame.sessionPresenter.completeFields((ObjectId) sessionIdField.getSelectedItem());
            setVisibleFieldsSession(3);
        });


        findSessionButton.addActionListener(e -> {
            actionType = "Find";
            clearFields();
            frame.findAllSessions();
            setVisibleFieldsSession(4);
        });

        executeButton.addActionListener(e -> {
            if ("Create".equals(actionType)) {
                frame.sessionPresenter.createSession();
            } else if ("Update".equals(actionType)) {
                frame.sessionPresenter.updateSession();
            } else if ("Delete".equals(actionType)) {
                frame.sessionPresenter.deleteSession();
            }
            clearFields();
        });

        searchButton.addActionListener(e -> frame.sessionPresenter.showFilteredTableData());

        sortButton.addActionListener(e -> frame.sessionPresenter.sortAllSessions());

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
            sessionPresenter.showSessionCount();
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
        List<User> users = userPresenter.getAllUsers();
        if (users != null && !(users).isEmpty()) {
            for (User user : users) {
                userIdField.addItem(user.getId().toString());
            }
        } else {
            userIdField.addItem(null);
        }
    }


    private void initializeSessionsIds() {
        List<Session> sessions = sessionPresenter.getAllSessions();
        if (sessions != null && !sessions.isEmpty()) {
            for (Session session : sessions) {
                sessionIdField.addItem(session.getId());
            }
        } else {
            sessionIdField.addItem(null);
        }
    }

    @Override
    public void refreshSessionsIds() {
        sessionIdField.removeAllItems();
        List<Session> sessions = sessionPresenter.getAllSessions();
        if (sessions != null && !sessions.isEmpty()) {
            for (Session session : sessions) {
                sessionIdField.addItem(session.getId());
            }
        }
    }

    @Override
    public void setVisibleFieldsSession(Integer i) {
        if (i == 1) {
            sessionIdLabel.setVisible(false);
            sessionIdField.setVisible(false);

            userIdLabel.setVisible(true);
            userIdField.setVisible(true);

            jwtLabel.setVisible(true);
            jwtField.setVisible(true);

            executeButton.setVisible(true);

            sessionsTable.setVisible(false);
            sessionsScrollPane.setVisible(false);

            findText.setVisible(false);
            sessionIdSearchField.setVisible(false);
            sessionIdSearchLabel.setVisible(false);
            userIdSearchLabel.setVisible(false);
            userIdSearchField.setVisible(false);
            jwtSearchField.setVisible(false);
            jwtSearchLabel.setVisible(false);
            searchButton.setVisible(false);

            sortText.setVisible(false);
            idSortBox.setVisible(false);
            userIdSortBox.setVisible(false);
            jwtSortBox.setVisible(false);
            directionText.setVisible(false);
            ascendingSortBox.setVisible(false);
            descendingSortBox.setVisible(false);

            idSortLabel.setVisible(false);
            userIdSortLabel.setVisible(false);
            jwtSortLabel.setVisible(false);
            ascendingLabel.setVisible(false);
            descendingLabel.setVisible(false);

            sortButton.setVisible(false);

            countLabel.setVisible(false);
            countField.setVisible(false);
            countButton.setVisible(false);
        } else {
            if (i == 2) {
                sessionIdLabel.setVisible(true);
                sessionIdField.setVisible(true);
                userIdLabel.setVisible(true);
                userIdField.setVisible(true);
                jwtLabel.setVisible(true);
                jwtField.setVisible(true);
                executeButton.setVisible(true);

                sessionsTable.setVisible(false);
                sessionsScrollPane.setVisible(false);

                findText.setVisible(false);
                sessionIdSearchField.setVisible(false);
                sessionIdSearchLabel.setVisible(false);
                userIdSearchLabel.setVisible(false);
                userIdSearchField.setVisible(false);
                jwtSearchField.setVisible(false);
                jwtSearchLabel.setVisible(false);
                searchButton.setVisible(false);

                sortText.setVisible(false);
                idSortBox.setVisible(false);
                userIdSortBox.setVisible(false);
                jwtSortBox.setVisible(false);
                directionText.setVisible(false);
                ascendingSortBox.setVisible(false);
                descendingSortBox.setVisible(false);

                idSortLabel.setVisible(false);
                userIdSortLabel.setVisible(false);
                jwtSortLabel.setVisible(false);
                ascendingLabel.setVisible(false);
                descendingLabel.setVisible(false);

                sortButton.setVisible(false);

                countLabel.setVisible(false);
                countField.setVisible(false);
                countButton.setVisible(false);
            } else {
                if (i == 3) {
                    sessionIdLabel.setVisible(true);
                    sessionIdField.setVisible(true);
                    userIdLabel.setVisible(true);
                    userIdField.setVisible(true);
                    jwtLabel.setVisible(true);
                    jwtField.setVisible(true);
                    executeButton.setVisible(true);

                    sessionsTable.setVisible(false);
                    sessionsScrollPane.setVisible(false);

                    findText.setVisible(false);
                    sessionIdSearchField.setVisible(false);
                    sessionIdSearchLabel.setVisible(false);
                    userIdSearchLabel.setVisible(false);
                    userIdSearchField.setVisible(false);
                    jwtSearchField.setVisible(false);
                    jwtSearchLabel.setVisible(false);
                    searchButton.setVisible(false);

                    sortText.setVisible(false);
                    idSortBox.setVisible(false);
                    userIdSortBox.setVisible(false);
                    jwtSortBox.setVisible(false);
                    directionText.setVisible(false);
                    ascendingSortBox.setVisible(false);
                    descendingSortBox.setVisible(false);

                    idSortLabel.setVisible(false);
                    userIdSortLabel.setVisible(false);
                    jwtSortLabel.setVisible(false);
                    ascendingLabel.setVisible(false);
                    descendingLabel.setVisible(false);

                    sortButton.setVisible(false);

                    countLabel.setVisible(false);
                    countField.setVisible(false);
                    countButton.setVisible(false);
                } else {
                    if (i == 4) {
                        sessionIdLabel.setVisible(false);
                        sessionIdField.setVisible(false);
                        userIdLabel.setVisible(false);
                        userIdField.setVisible(false);
                        jwtLabel.setVisible(false);
                        jwtField.setVisible(false);
                        executeButton.setVisible(false);

                        sessionsTable.setVisible(true);
                        sessionsScrollPane.setVisible(true);

                        findText.setVisible(true);
                        sessionIdSearchField.setVisible(true);
                        sessionIdSearchLabel.setVisible(true);
                        userIdSearchLabel.setVisible(true);
                        userIdSearchField.setVisible(true);
                        jwtSearchField.setVisible(true);
                        jwtSearchLabel.setVisible(true);
                        searchButton.setVisible(true);

                        sortText.setVisible(true);
                        idSortBox.setVisible(true);
                        userIdSortBox.setVisible(true);
                        jwtSortBox.setVisible(true);
                        directionText.setVisible(true);
                        ascendingSortBox.setVisible(true);
                        descendingSortBox.setVisible(true);

                        idSortLabel.setVisible(true);
                        userIdSortLabel.setVisible(true);
                        jwtSortLabel.setVisible(true);
                        ascendingLabel.setVisible(true);
                        descendingLabel.setVisible(true);

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
        jwtField.setText("");
        sessionIdField.setSelectedItem(null);
        userIdField.setSelectedItem(null);

        jwtSearchField.setText("");
        sessionIdSearchField.setText("");
        userIdSearchField.setText("");

        countField.setText("");
    }

    @Override
    public void setFieldsEnabled(boolean enabled) {
        userIdField.setEnabled(enabled);
        jwtField.setEnabled(enabled);
    }

    @Override
    public ObjectId getSessionId() {
        return (ObjectId) sessionIdField.getSelectedItem();
    }


    @Override
    public String getJwtField() {
        return jwtField.getText();
    }


    @Override
    public void setJwtForSession(String jwt) {
        if (jwt != null) {
            jwtField.setText(jwt);
            System.out.println("Debug: JWT set in field -> " + jwt);
        } else {
            jwtField.setText("");
            System.out.println("Debug: JWT is null!");
        }
    }


    @Override
    public void findAllSessions() {
        frame.setTitle("Sessions Table");

        List<Session> sessions = sessionPresenter.getAllSessions();
        if (sessions != null && !sessions.isEmpty()) {
            System.out.println("Debug: Total sessions fetched -> " + sessions.size());
            sessionPresenter.createSessionTable(sessions, sessionsTable, sessionsScrollPane);
        } else {
            displayMessage("No sessions found...");
        }
    }

    @Override
    public String getSessionIdSearchField() {
        return sessionIdSearchField.getText();
    }


    @Override
    public String getUserIdSearchField() {
        return userIdSearchField.getText();
    }

    @Override
    public String getJwtSearchField() {
        return jwtSearchField.getText();
    }

    @Override
    public JTable getSessionsTable() {
        return this.sessionsTable;
    }

    @Override
    public JScrollPane getSessionsScrollPane() {
        return this.sessionsScrollPane;
    }

    //sort part
    private void configureSortCheckBoxes() {
        idSortBox.addActionListener(e -> {
            if (idSortBox.isSelected()) {
                deselectOtherCheckBoxes("id");
            }
        });

        userIdSortBox.addActionListener(e -> {
            if (userIdSortBox.isSelected()) {
                deselectOtherCheckBoxes("user");
            }
        });

        jwtSortBox.addActionListener(e -> {
            if (jwtSortBox.isSelected()) {
                deselectOtherCheckBoxes("jwt");
            }
        });

    }

    private void deselectOtherCheckBoxes(String selected) {
        idSortBox.setSelected("id".equals(selected));
        userIdSortBox.setSelected("user".equals(selected));
        jwtSortBox.setSelected("jwt".equals(selected));
    }

    @Override
    public boolean isIdSortChecked() {
        return idSortBox.isSelected();
    }

    @Override
    public boolean isUserSortChecked() {
        return userIdSortBox.isSelected();
    }

    @Override
    public boolean isJwtSortChecked() {
        return jwtSortBox.isSelected();
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
    public String getSelectedUserId() {
        String selected = Objects.requireNonNull(userIdField.getSelectedItem()).toString();
        if (selected != null) {
            System.out.println("Debug: Selected User ID -> " + selected);
            return selected;
        }
        return null;
    }

    @Override
    public void setSelectedUserId(String userId) {
        for (int i = 0; i < userIdField.getItemCount(); i++) {
            String currentId = userIdField.getItemAt(i);
            if (currentId.equals(userId)) {
                userIdField.setSelectedItem(currentId);
                break;
            }
        }
        System.out.println("Set User ID to: " + userId);
    }

    @Override
    public void displaySessionCount(long count) {
        countField.setText(String.valueOf(count));
        System.out.println("Debug: Total sessions displayed -> " + count);
    }


}
