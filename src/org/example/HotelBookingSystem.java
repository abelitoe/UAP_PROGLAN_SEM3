package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class HotelBookingSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showLoginScreen();
        });
    }

    private static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Sistem Booking Hotel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel usernameLabel = new JLabel("Username");
        JTextField usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");

        c.insets = new Insets(10, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        loginPanel.add(titleLabel, c);

        c.insets = new Insets(5, 5, 5, 5);
        c.gridy++;
        c.gridwidth = 1;
        loginPanel.add(usernameLabel, c);

        c.gridx++;
        loginPanel.add(usernameField, c);

        c.gridx = 0;
        c.gridy++;
        loginPanel.add(passwordLabel, c);

        c.gridx++;
        loginPanel.add(passwordField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        loginPanel.add(loginButton, c);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.equals("admin") && password.equals("password")) {
                JOptionPane.showMessageDialog(loginFrame, "Login Berhasil");
                loginFrame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Username atau Password salah");
            }
        });

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }

    private static void showMainMenu() {
        JFrame mainMenuFrame = new JFrame("Menu Utama");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(400, 300);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Sistem Booking Hotel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton tamuButton = new JButton("Menu Tamu");
        JButton kamarButton = new JButton("Menu Kamar");
        JButton pemesananButton = new JButton("Menu Pemesanan");

        tamuButton.addActionListener(e -> showTamuList());
        kamarButton.addActionListener(e -> showKamarList());
        pemesananButton.addActionListener(e -> showPemesananList());

        mainPanel.add(titleLabel);
        mainPanel.add(tamuButton);
        mainPanel.add(kamarButton);
        mainPanel.add(pemesananButton);

        mainMenuFrame.add(mainPanel);
        mainMenuFrame.setVisible(true);
    }

    private static void showTamuList() {
        JFrame tamuFrame = new JFrame("Data Tamu");
        tamuFrame.setSize(800, 400);

        String[] columnNames = {"Nama", "Kartu Kredit", "Alamat", "Negara", "Jenis Kelamin", "Nomor Telepon"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        JButton deleteButton = new JButton("Hapus");

        addButton.addActionListener(e -> {
            JTextField namaField = new JTextField();
            JTextField kartuKreditField = new JTextField();
            JTextField alamatField = new JTextField();
            JTextField negaraField = new JTextField();
            JTextField jenisKelaminField = new JTextField();
            JTextField nomorTeleponField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(6, 2));
            inputPanel.add(new JLabel("Nama:"));
            inputPanel.add(namaField);
            inputPanel.add(new JLabel("Kartu Kredit:"));
            inputPanel.add(kartuKreditField);
            inputPanel.add(new JLabel("Alamat:"));
            inputPanel.add(alamatField);
            inputPanel.add(new JLabel("Negara:"));
            inputPanel.add(negaraField);
            inputPanel.add(new JLabel("Jenis Kelamin:"));
            inputPanel.add(jenisKelaminField);
            inputPanel.add(new JLabel("Nomor Telepon:"));
            inputPanel.add(nomorTeleponField);

            int result = JOptionPane.showConfirmDialog(tamuFrame, inputPanel, "Tambah Tamu", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                model.addRow(new Object[]{
                        namaField.getText(),
                        kartuKreditField.getText(),
                        alamatField.getText(),
                        negaraField.getText(),
                        jenisKelaminField.getText(),
                        nomorTeleponField.getText()
                });
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(tamuFrame, "Pilih baris yang ingin dihapus");
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        tamuFrame.add(scrollPane, BorderLayout.CENTER);
        tamuFrame.add(buttonPanel, BorderLayout.SOUTH);
        tamuFrame.setVisible(true);
    }

    private static void showKamarList() {
        JFrame kamarFrame = new JFrame("Data Kamar");
        kamarFrame.setSize(800, 400);

        String[] columnNames = {"Nomor Kamar", "Tipe Kamar", "Wifi", "Bathtub", "Harga"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        JButton deleteButton = new JButton("Hapus");

        addButton.addActionListener(e -> {
            JTextField nomorKamarField = new JTextField();
            JTextField tipeKamarField = new JTextField();
            JTextField wifiField = new JTextField();
            JTextField bathtubField = new JTextField();
            JTextField hargaField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(5, 2));
            inputPanel.add(new JLabel("Nomor Kamar:"));
            inputPanel.add(nomorKamarField);
            inputPanel.add(new JLabel("Tipe Kamar:"));
            inputPanel.add(tipeKamarField);
            inputPanel.add(new JLabel("Wifi:"));
            inputPanel.add(wifiField);
            inputPanel.add(new JLabel("Bathtub:"));
            inputPanel.add(bathtubField);
            inputPanel.add(new JLabel("Harga:"));
            inputPanel.add(hargaField);

            int result = JOptionPane.showConfirmDialog(kamarFrame, inputPanel, "Tambah Kamar", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                model.addRow(new Object[]{
                        nomorKamarField.getText(),
                        tipeKamarField.getText(),
                        wifiField.getText(),
                        bathtubField.getText(),
                        hargaField.getText()
                });
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(kamarFrame, "Pilih baris yang ingin dihapus");
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        kamarFrame.add(scrollPane, BorderLayout.CENTER);
        kamarFrame.add(buttonPanel, BorderLayout.SOUTH);
        kamarFrame.setVisible(true);
    }

    private static void showPemesananList() {
        JFrame pemesananFrame = new JFrame("Data Pemesanan");
        pemesananFrame.setSize(800, 400);

        String[] columnNames = {"Kode Pemesanan", "Nama Tamu", "Nomor Kamar", "Check-in", "Check-out", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        JButton deleteButton = new JButton("Hapus");

        addButton.addActionListener(e -> {
            JTextField kodeField = new JTextField();
            JTextField namaField = new JTextField();
            JTextField nomorKamarField = new JTextField();
            JTextField checkInField = new JTextField();
            JTextField checkOutField = new JTextField();
            JTextField statusField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(6, 2));
            inputPanel.add(new JLabel("Kode Pemesanan:"));
            inputPanel.add(kodeField);
            inputPanel.add(new JLabel("Nama Tamu:"));
            inputPanel.add(namaField);
            inputPanel.add(new JLabel("Nomor Kamar:"));
            inputPanel.add(nomorKamarField);
            inputPanel.add(new JLabel("Check-in:"));
            inputPanel.add(checkInField);
            inputPanel.add(new JLabel("Check-out:"));
            inputPanel.add(checkOutField);
            inputPanel.add(new JLabel("Status:"));
            inputPanel.add(statusField);

            int result = JOptionPane.showConfirmDialog(pemesananFrame, inputPanel, "Tambah Pemesanan", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                model.addRow(new Object[]{
                        kodeField.getText(),
                        namaField.getText(),
                        nomorKamarField.getText(),
                        checkInField.getText(),
                        checkOutField.getText(),
                        statusField.getText()
                });
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(pemesananFrame, "Pilih baris yang ingin dihapus");
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        pemesananFrame.add(scrollPane, BorderLayout.CENTER);
        pemesananFrame.add(buttonPanel, BorderLayout.SOUTH);
        pemesananFrame.setVisible(true);
    }
}