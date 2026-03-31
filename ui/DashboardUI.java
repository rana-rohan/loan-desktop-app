package ui;

import service.ApiService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

public class DashboardUI {

    static JTable table;

    public static void show() {

        JFrame dashboard = new JFrame("Dashboard");
        dashboard.setSize(500, 400);
        dashboard.setLayout(null);
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome! You are logged in");
        label.setBounds(50, 30, 300, 30);
        dashboard.add(label);

        // 🔹 Table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 450, 120);
        dashboard.add(scrollPane);

        // 🔹 Get Loans Button
        JButton getLoanBtn = new JButton("Get Loans");
        getLoanBtn.setBounds(150, 60, 150, 30);

        getLoanBtn.addActionListener(e -> {
            loadLoans();
        });

        dashboard.add(getLoanBtn);

        // 🔹 Apply Loan Button
        JButton applyLoanBtn = new JButton("Apply Loan");
        applyLoanBtn.setBounds(150, 250, 150, 30);

        applyLoanBtn.addActionListener(e -> {
            ApplyLoanUI.show();
        });

        dashboard.add(applyLoanBtn);

        dashboard.setVisible(true);
    }

    public static void loadLoans() {

        try {
            String response = ApiService.getLoans(LoginUI.token);

            if (response.equals("UNAUTHORIZED")) {
                JOptionPane.showMessageDialog(null, "Session expired! Please login again.");
                System.exit(0);
                return;
            }

            JSONArray jsonArray = new JSONArray(response);

            String[][] data = new String[jsonArray.length()][3];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                data[i][0] = String.valueOf(obj.getInt("id"));
                data[i][1] = String.valueOf(obj.getDouble("amount"));
                data[i][2] = obj.getString("status");
            }

            String[] column = {"ID", "Amount", "Status"};

            table.setModel(new javax.swing.table.DefaultTableModel(data, column));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading loans!");
        }
    }
}