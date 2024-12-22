package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class HotelBookingSystem {
    private static final ArrayList<String[]> kamarList = new ArrayList<>();
    private static final ArrayList<String[]> tamuList = new ArrayList<>();
    private static final ArrayList<String[]> pemesananList = new ArrayList<>();

    public static void main(String[] args) {
        loadDataFromFile();
        SwingUtilities.invokeLater(HotelBookingSystem::showLoginScreen);
    }
    private static void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("hotel_data.txt"))) {
            writer.println("Data Kamar:");
            for (String[] kamar : kamarList) {
                writer.println(String.join(",", kamar));
            }

            writer.println("\nData Tamu:");
            for (String[] tamu : tamuList) {
                writer.println(String.join(",", tamu));
            }

            writer.println("\nData Pemesanan:");
            for (String[] pemesanan : pemesananList) {
                writer.println(String.join(",", pemesanan));
            }

            JOptionPane.showMessageDialog(null, "Data berhasil disimpan ke file hotel_data.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data: " + e.getMessage());
        }
    }

    private static void loadDataFromFile() {
        File file = new File("hotel_data.txt");
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<String[]> currentList = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Data Kamar:")) {
                    currentList = kamarList;
                } else if (line.startsWith("Data Tamu:")) {
                    currentList = tamuList;
                } else if (line.startsWith("Data Pemesanan:")) {
                    currentList = pemesananList;
                } else if (!line.isBlank() && currentList != null) {
                    currentList.add(line.split(","));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat memuat data: " + e.getMessage());
        }
    }

    private static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 400);

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

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Sistem Booking Hotel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel buttonPanel = getjPanel();

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        mainMenuFrame.add(mainPanel);
        mainMenuFrame.setVisible(true);
    }

    private static JPanel getjPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton tamuButton = new JButton("Menu Tamu");
        JButton kamarButton = new JButton("Menu Kamar");
        JButton pemesananButton = new JButton("Menu Pemesanan");
        JButton calculateButton = new JButton("Hitung Total Kamar");


        tamuButton.addActionListener(e -> showTamuList());
        kamarButton.addActionListener(e -> showKamarList());
        pemesananButton.addActionListener(e -> showPemesananList());
        calculateButton.addActionListener(e -> calculateTotal());

        buttonPanel.add(tamuButton);
        buttonPanel.add(kamarButton);
        buttonPanel.add(pemesananButton);
        buttonPanel.add(calculateButton);
        return buttonPanel;
    }

    private static void showTamuList() {
        JFrame tamuFrame = new JFrame("Data Tamu");
        tamuFrame.setSize(800, 400);

        String[] columnNames = {"Nama", "Kartu Kredit", "Alamat", "Negara", "Jenis Kelamin", "Nomor Telepon"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        for (String[] tamu : tamuList) {
            model.addRow(tamu);
        }
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
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
                String[] newTamu = {
                        namaField.getText(),
                        kartuKreditField.getText(),
                        alamatField.getText(),
                        negaraField.getText(),
                        jenisKelaminField.getText(),
                        nomorTeleponField.getText()
                };
                model.addRow(newTamu);
                tamuList.add(newTamu);
                saveDataToFile();
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                JTextField namaField = new JTextField((String) model.getValueAt(selectedRow, 0));
                JTextField kartuKreditField = new JTextField((String) model.getValueAt(selectedRow, 1));
                JTextField alamatField = new JTextField((String) model.getValueAt(selectedRow, 2));
                JTextField negaraField = new JTextField((String) model.getValueAt(selectedRow, 3));
                JTextField jenisKelaminField = new JTextField((String) model.getValueAt(selectedRow, 4));
                JTextField nomorTeleponField = new JTextField((String) model.getValueAt(selectedRow, 5));

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

                int result = JOptionPane.showConfirmDialog(tamuFrame, inputPanel, "Edit Tamu", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    model.setValueAt(namaField.getText(), selectedRow, 0);
                    model.setValueAt(kartuKreditField.getText(), selectedRow, 1);
                    model.setValueAt(alamatField.getText(), selectedRow, 2);
                    model.setValueAt(negaraField.getText(), selectedRow, 3);
                    model.setValueAt(jenisKelaminField.getText(), selectedRow, 4);
                    model.setValueAt(nomorTeleponField.getText(), selectedRow, 5);
                    tamuList.set(selectedRow, new String[]{
                            namaField.getText(),
                            kartuKreditField.getText(),
                            alamatField.getText(),
                            negaraField.getText(),
                            jenisKelaminField.getText(),
                            nomorTeleponField.getText()
                    });
                    saveDataToFile();
                }
            } else {
                JOptionPane.showMessageDialog(tamuFrame, "Pilih baris yang ingin diedit");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                tamuList.remove(selectedRow);
                saveDataToFile();
            } else {
                JOptionPane.showMessageDialog(tamuFrame, "Pilih baris yang ingin dihapus");
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        tamuFrame.add(scrollPane, BorderLayout.CENTER);
        tamuFrame.add(buttonPanel, BorderLayout.SOUTH);
        tamuFrame.setVisible(true);
    }
    private static void showKamarList() {
        JFrame kamarFrame = new JFrame("Data Kamar");
        kamarFrame.setSize(800, 400);

        String[] columnNames = {"Kode Kamar", "Lantai", "Nomor Kamar", "Tipe Kamar", "Harga per Malam"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        for (String[] kamar : kamarList) {
            model.addRow(kamar);
        }

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Edit");
        JButton addButton = new JButton("Tambah");
        JButton deleteButton = new JButton("Hapus");

        addButton.addActionListener(e -> {
            JTextField kodeField = new JTextField();
            JTextField lantaiField = new JTextField();
            JTextField nomorField = new JTextField();
            JComboBox<String> tipeField = new JComboBox<>(new String[]{"Ekonomi", "Bisnis", "VIP"});

            JLabel hargaLabel = new JLabel("Harga per Malam:");
            JTextField hargaField = new JTextField();
            hargaField.setEditable(false);

            tipeField.addActionListener(event -> {
                String tipe = (String) tipeField.getSelectedItem();
                if (tipe != null) {
                    switch (tipe) {
                        case "Ekonomi" -> hargaField.setText("200000");
                        case "Bisnis" -> hargaField.setText("250000");
                        case "VIP" -> hargaField.setText("300000");
                    }
                }
            });

            JPanel inputPanel = new JPanel(new GridLayout(5, 2));
            inputPanel.add(new JLabel("Kode Kamar:"));
            inputPanel.add(kodeField);
            inputPanel.add(new JLabel("Lantai:"));
            inputPanel.add(lantaiField);
            inputPanel.add(new JLabel("Nomor Kamar:"));
            inputPanel.add(nomorField);
            inputPanel.add(new JLabel("Tipe Kamar:"));
            inputPanel.add(tipeField);
            inputPanel.add(hargaLabel);
            inputPanel.add(hargaField);

            int result = JOptionPane.showConfirmDialog(kamarFrame, inputPanel, "Tambah Kamar", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String[] newKamar = {
                        kodeField.getText(),
                        lantaiField.getText(),
                        nomorField.getText(),
                        (String) tipeField.getSelectedItem(),
                        hargaField.getText()
                };
                model.addRow(newKamar);
                kamarList.add(newKamar);
                saveDataToFile();
            }
        });
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String kodeKamar = (String) model.getValueAt(selectedRow, 0);
                String lantai = (String) model.getValueAt(selectedRow, 1);
                String nomorKamar = (String) model.getValueAt(selectedRow, 2);
                String tipeKamar = (String) model.getValueAt(selectedRow, 3);
                String harga = (String) model.getValueAt(selectedRow, 4);

                JTextField kodeField = new JTextField(kodeKamar);
                JTextField lantaiField = new JTextField(lantai);
                JTextField nomorField = new JTextField(nomorKamar);
                JComboBox<String> tipeField = new JComboBox<>(new String[]{"Ekonomi", "Bisnis", "VIP"});
                tipeField.setSelectedItem(tipeKamar);

                JLabel hargaLabel = new JLabel("Harga per Malam:");
                JTextField hargaField = new JTextField(harga);
                hargaField.setEditable(false);

                tipeField.addActionListener(event -> {
                    String tipe = (String) tipeField.getSelectedItem();
                    if (tipe != null) {
                        switch (tipe) {
                            case "Ekonomi" -> hargaField.setText("200000");
                            case "Bisnis" -> hargaField.setText("250000");
                            case "VIP" -> hargaField.setText("300000");
                        }
                    }
                });

                JPanel inputPanel = new JPanel(new GridLayout(5, 2));
                inputPanel.add(new JLabel("Kode Kamar:"));
                inputPanel.add(kodeField);
                inputPanel.add(new JLabel("Lantai:"));
                inputPanel.add(lantaiField);
                inputPanel.add(new JLabel("Nomor Kamar:"));
                inputPanel.add(nomorField);
                inputPanel.add(new JLabel("Tipe Kamar:"));
                inputPanel.add(tipeField);
                inputPanel.add(hargaLabel);
                inputPanel.add(hargaField);

                int result = JOptionPane.showConfirmDialog(kamarFrame, inputPanel, "Edit Kamar", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    model.setValueAt(kodeField.getText(), selectedRow, 0);
                    model.setValueAt(lantaiField.getText(), selectedRow, 1);
                    model.setValueAt(nomorField.getText(), selectedRow, 2);
                    model.setValueAt(tipeField.getSelectedItem(), selectedRow, 3);
                    model.setValueAt(hargaField.getText(), selectedRow, 4);

                    kamarList.set(selectedRow, new String[]{
                            kodeField.getText(),
                            lantaiField.getText(),
                            nomorField.getText(),
                            (String) tipeField.getSelectedItem(),
                            hargaField.getText()
                    });
                    saveDataToFile();
                }
            } else {
                JOptionPane.showMessageDialog(kamarFrame, "Pilih baris yang ingin diedit terlebih dahulu.", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }
        });


        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                kamarList.remove(selectedRow);
                saveDataToFile();
            } else {
                JOptionPane.showMessageDialog(kamarFrame, "Pilih baris yang ingin dihapus");
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        kamarFrame.add(scrollPane, BorderLayout.CENTER);
        kamarFrame.add(buttonPanel, BorderLayout.SOUTH);
        kamarFrame.setVisible(true);
    }

    private static void calculateTotal() {
        int total = 0;
        for (String[] kamar : kamarList) {
            total += Integer.parseInt(kamar[4]);
        }
        JOptionPane.showMessageDialog(null, "Total Harga Kamar: Rp " + total);
    }

    private static void showPemesananList() {
        JFrame pemesananFrame = new JFrame("Data Pemesanan");
        pemesananFrame.setSize(800, 400);

        String[] columnNames = {"Kode Pemesanan", "Nama Tamu", "Nomor Kamar", "Check-in", "Check-out", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        for (String[] pemesanan : pemesananList) {
            model.addRow(pemesanan);
        }
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
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
                String[] newPemesanan = {
                        kodeField.getText(),
                        namaField.getText(),
                        nomorKamarField.getText(),
                        checkInField.getText(),
                        checkOutField.getText(),
                        statusField.getText()
                };
                model.addRow(newPemesanan);
                pemesananList.add(newPemesanan);
                saveDataToFile();
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                JTextField kodeField = new JTextField((String) model.getValueAt(selectedRow, 0));
                JTextField namaField = new JTextField((String) model.getValueAt(selectedRow, 1));
                JTextField nomorKamarField = new JTextField((String) model.getValueAt(selectedRow, 2));
                JTextField checkInField = new JTextField((String) model.getValueAt(selectedRow, 3));
                JTextField checkOutField = new JTextField((String) model.getValueAt(selectedRow, 4));
                JTextField statusField = new JTextField((String) model.getValueAt(selectedRow, 5));

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

                int result = JOptionPane.showConfirmDialog(pemesananFrame, inputPanel, "Edit Pemesanan", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    model.setValueAt(kodeField.getText(), selectedRow, 0);
                    model.setValueAt(namaField.getText(), selectedRow, 1);
                    model.setValueAt(nomorKamarField.getText(), selectedRow, 2);
                    model.setValueAt(checkInField.getText(), selectedRow, 3);
                    model.setValueAt(checkOutField.getText(), selectedRow, 4);
                    model.setValueAt(statusField.getText(), selectedRow, 5);
                    pemesananList.set(selectedRow, new String[]{
                            kodeField.getText(),
                            namaField.getText(),
                            nomorKamarField.getText(),
                            checkInField.getText(),
                            checkOutField.getText(),
                            statusField.getText()
                    });
                    saveDataToFile();
                }
            } else {
                JOptionPane.showMessageDialog(pemesananFrame, "Pilih baris yang ingin diedit");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                pemesananList.remove(selectedRow);
            } else {
                JOptionPane.showMessageDialog(pemesananFrame, "Pilih baris yang ingin dihapus");
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        pemesananFrame.add(scrollPane, BorderLayout.CENTER);
        pemesananFrame.add(buttonPanel, BorderLayout.SOUTH);
        pemesananFrame.setVisible(true);
    }
}