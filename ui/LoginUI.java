package ui;

import service.ApiService;

import javax.swing.*;

public class LoginUI {

    public static String token = "";

    public static void show() {

        JFrame frame = new JFrame("Login");
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 50, 80, 25);
        frame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(150, 50, 150, 25);
        frame.add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 80, 25);
        frame.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 100, 150, 25);
        frame.add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(150, 150, 100, 30);

        loginBtn.addActionListener(e -> {

            String email = emailField.getText();
            String password = new String(passField.getPassword());

            String response = ApiService.login(email, password);

            if (!response.equals("ERROR") && !response.isEmpty()) {

                token = response;

                frame.dispose();
                DashboardUI.show();

            } else {
                JOptionPane.showMessageDialog(null, "Login Failed!");
            }
        });

        frame.add(loginBtn);
        frame.setVisible(true);
    }
}