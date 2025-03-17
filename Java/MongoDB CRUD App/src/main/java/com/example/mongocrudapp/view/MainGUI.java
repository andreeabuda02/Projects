package com.example.mongocrudapp.view;

import com.example.mongocrudapp.presenter.MainPresenter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainGUI extends JFrame implements IMainView{

    private final JLabel titleText = new JLabel("Welcome to my Database Management App!");

    private final JLabel subtitleText = new JLabel("Please choose a collection on witch do you want to perform a CRUD operation:");

    private final JButton userCrudButton = new JButton("USERS");
    private final JButton sessionCrudButton = new JButton("SESSIONS");
    private final JButton exitButton = new JButton("EXIT");

    private static MainGUI frame;
    private final MainPresenter presenter;

    public void interfaceAspect(){
        titleText.setBounds(350, 30, 820, 100);
        titleText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 38));
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY, 2);
        titleText.setBorder(lineBorder);

        subtitleText.setBounds(180, 180, 1500, 100);
        subtitleText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));

        userCrudButton.setBounds(300, 370, 350, 200);
        sessionCrudButton.setBounds(880, 370, 350, 200);
        exitButton.setBounds(700, 750, 150, 40);

        userCrudButton.setBackground(new Color(184, 143, 239));
        sessionCrudButton.setBackground(new Color(184, 143, 239));
        exitButton.setBackground(new Color(174, 162, 224));

        userCrudButton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 25));
        sessionCrudButton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 25));
        exitButton.setFont(new Font("Arial",  Font.ITALIC, 18));

        userCrudButton.setOpaque(true);
        sessionCrudButton.setOpaque(true);
        exitButton.setOpaque(true);

        userCrudButton.setBorderPainted(true);
        sessionCrudButton.setBorderPainted(true);
        exitButton.setBorderPainted(true);

        userCrudButton.setFocusPainted(false);
        sessionCrudButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        userCrudButton.setForeground(Color.WHITE);
        sessionCrudButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);

    }

    private void addComponents() {
        add(titleText);
        add(subtitleText);
        add(userCrudButton);
        add(sessionCrudButton);
        add(exitButton);
    }

    public MainGUI() throws HeadlessException{
        super("MongoDB CRUD Application");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        frame = this;

        this.interfaceAspect();
        this.addComponents();

        presenter = new MainPresenter(this);

        JPanel windowP =new JPanel();
        windowP.setBackground(new Color(200, 200, 232));
        windowP.setLayout(null);
        add(windowP);

        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(false);
        this.setVisible(true);

        exitButton.addActionListener(e -> {
            System.out.println("Exit...");
            System.exit(0);
        });

        userCrudButton.addActionListener(e -> {
            System.out.println("Navigating to User CRUD operations...");
            frame.presenter.operationsWithUsers();
        });


        sessionCrudButton.addActionListener(e -> {
            System.out.println("Navigating to Sessions CRUD operations...");
            frame.presenter.operationsWithSessions();
        });
    }

    @Override
    public void hideWindow() {
        this.setVisible(false);
    }


}
