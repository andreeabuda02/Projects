package com.example.mongocrudapp.view;

import org.bson.types.ObjectId;

import javax.swing.*;

public interface IUserView {
    void displayMessage(String message);

    void setVisibleFieldsUser(Integer i);

    ObjectId getIdUser();
    String getNameUser();
    String getEmailUser();
    String getPasswordUser();

    void setNameUser(String nameUser);
    void setEmailUser(String emailUser);
    void setPasswordUser(String passwordUser);

    void hideWindow();
    void findAllUsers();

    String getIdSearchField();
    String getNameSearchField();
    String getEmailSearchField();
    String getPasswordSearchField();

    JTable getUsersTable();
    JScrollPane getUsersScrollPane();

    void clearFields();
    void setFieldsEnabled(boolean enabled);

    boolean isIdSortChecked();
    boolean isNameSortChecked();
    boolean isEmailSortChecked();
    boolean isPasswordSortChecked();
    boolean isAscendingSortChecked();
    boolean isDescendingSortChecked();
    void displayUserCount(long count);


}
