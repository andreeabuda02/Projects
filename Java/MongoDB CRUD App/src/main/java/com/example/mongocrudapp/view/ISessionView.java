package com.example.mongocrudapp.view;

import javax.swing.*;

public interface ISessionView {

    void hideWindow();
    void setVisibleFieldsSession(Integer i);

    void displayMessage(String message);
    void clearFields();
    void setFieldsEnabled(boolean enabled);

    String getSessionIdSearchField();
    String getUserIdSearchField();
    String getJwtSearchField();
    String getSelectedUserId();
    String getJwtField();
    void setSelectedUserId(String userId);
    void setJwtForSession(String jwt);

    // sort and filter
    boolean isIdSortChecked();
    boolean isUserSortChecked();
    boolean isJwtSortChecked();
    boolean isAscendingSortChecked();
    boolean isDescendingSortChecked();

    // tables
    JTable getSessionsTable();
    JScrollPane getSessionsScrollPane();
    void findAllSessions();
    void refreshSessionsIds();
    Object getSessionId();

    void displaySessionCount(long count);
}
