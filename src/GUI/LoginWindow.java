package GUI;

import core.ShoppingCart;
import core.WestminsterShoppingManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserManager userManager;

    public LoginWindow() {
        super("Login/Register");
        userManager = new UserManager();
        initializeComponents();
        layoutComponents();
        initializeListeners();
    }

    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
    }

    private void layoutComponents() {
        setLayout(new GridLayout(3, 2));
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(registerButton);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userManager.loginUser(usernameField.getText(), new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Login Successful");
                    dispose();
                    new MainWindow(new ShoppingCart(), new WestminsterShoppingManager()).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Login Failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userManager.registerUser(usernameField.getText(), new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Registration Successful. Please login.");
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Registration Failed: User already exists", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
