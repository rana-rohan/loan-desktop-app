package ui;

import service.ApiService;

import javax.swing.*;

public class ApplyLoanUI {

    public static void show() {

        JFrame form = new JFrame("Apply Loan");
        form.setSize(400, 250);
        form.setLayout(null);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(50, 50, 100, 25);
        form.add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(150, 50, 150, 25);
        form.add(amountField);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(150, 100, 100, 30);

        submitBtn.addActionListener(e -> {

            String amount = amountField.getText();

            // 🔴 Validation
            if (amount.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter amount!");
                return;
            }

            String response = ApiService.applyLoan(LoginUI.token, amount);

            JOptionPane.showMessageDialog(null, response);

            // 🔥 table refresh
            DashboardUI.loadLoans();

            // 🔥 form close
            form.dispose();
        });

        form.add(submitBtn);

        form.setVisible(true);
    }
}