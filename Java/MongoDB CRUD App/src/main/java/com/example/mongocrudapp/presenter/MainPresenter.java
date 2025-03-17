package com.example.mongocrudapp.presenter;

import com.example.mongocrudapp.view.*;

public class MainPresenter {

    private final IMainView iMainView;
    public MainPresenter(IMainView iMainView){
        this.iMainView = iMainView;
    }

    public void operationsWithUsers(){
        UserGUI userGUI = new UserGUI();
        userGUI.setVisible(true);
        this.iMainView.hideWindow();
    }

    public void operationsWithSessions(){
        SessionGUI sessionGUI = new SessionGUI();
        sessionGUI.setVisible(true);
        this.iMainView.hideWindow();
    }

}
