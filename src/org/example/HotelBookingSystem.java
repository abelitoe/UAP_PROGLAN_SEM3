package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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
        loginPanel.setBackground(new Color(75, 74, 103)); // Warna Independence (#4B4A67)

        GridBagConstraints c = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Sistem Booking Hotel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE); // Teks jadi putih agar kontras

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE); // Teks jadi putih agar kontras

        JTextField usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE); // Teks jadi putih agar kontras

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

        loginPanel.add(loginButton, c);
        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }

    private static void showMainMenu() {
        JFrame mainMenuFrame = new JFrame("Menu Utama");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(400, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(75, 74, 103)); // Warna Independence (#4B4A67)

        JLabel titleLabel = new JLabel("Sistem Booking Hotel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE); // Teks jadi putih untuk kontras

        JPanel buttonPanel = getjPanel();
        buttonPanel.setBackground(new Color(75, 74, 103)); // Pastikan warna panel tombol sama

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

        // Panel tombol
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
                try {
                    String nama = namaField.getText();
                    if (!nama.matches("[a-zA-Z ]+")) {
                        throw new IllegalArgumentException("Nama hanya boleh berisi huruf.");
                    }

                    String kartuKredit = kartuKreditField.getText().trim(); // Menghapus spasi ekstra di awal/akhir
                    if (!kartuKredit.matches("^\\d{12}$")) { // Menambahkan validasi null
                        throw new IllegalArgumentException("Kartu Kredit harus terdiri dari 12 digit angka.");
                    }

                    String jenisKelamin = jenisKelaminField.getText();
                    if (!jenisKelamin.equalsIgnoreCase("Laki-Laki") && !jenisKelamin.equalsIgnoreCase("Perempuan")) {
                        throw new IllegalArgumentException("Jenis Kelamin harus Laki-Laki atau Perempuan.");
                    }

                    String nomorTelepon = nomorTeleponField.getText();
                    if (!nomorTelepon.matches("\\d{10,12}")) {
                        throw new IllegalArgumentException("Nomor Telepon harus terdiri dari 10 hingga 12 digit angka.");
                    }

                    String[] newTamu = {
                            nama,
                            kartuKredit,
                            alamatField.getText(),
                            negaraField.getText(),
                            jenisKelamin,
                            nomorTelepon
                    };
                    model.addRow(newTamu);
                    tamuList.add(newTamu);
                    saveDataToFile();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(tamuFrame, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                }
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
                    try {
                        String nama = namaField.getText();
                        if (!nama.matches("[a-zA-Z ]+")) {
                            throw new IllegalArgumentException("Nama hanya boleh berisi huruf.");
                        }

                        String kartuKredit = kartuKreditField.getText();
                        if (!kartuKredit.matches("\\d{12}")) {
                            throw new IllegalArgumentException("Kartu Kredit harus terdiri dari 12 digit angka.");
                        }

                        String jenisKelamin = jenisKelaminField.getText();
                        if (!jenisKelamin.equalsIgnoreCase("Laki-Laki") && !jenisKelamin.equalsIgnoreCase("Perempuan")) {
                            throw new IllegalArgumentException("Jenis Kelamin harus Laki-Laki atau Perempuan.");
                        }

                        String nomorTelepon = nomorTeleponField.getText();
                        if (!nomorTelepon.matches("\\d{10,12}")) {
                            throw new IllegalArgumentException("Nomor Telepon harus terdiri dari 10 hingga 12 digit angka.");
                        }

                        model.setValueAt(nama, selectedRow, 0);
                        model.setValueAt(kartuKredit, selectedRow, 1);
                        model.setValueAt(alamatField.getText(), selectedRow, 2);
                        model.setValueAt(negaraField.getText(), selectedRow, 3);
                        model.setValueAt(jenisKelamin, selectedRow, 4);
                        model.setValueAt(nomorTelepon, selectedRow, 5);

                        tamuList.set(selectedRow, new String[]{
                                nama,
                                kartuKredit,
                                alamatField.getText(),
                                negaraField.getText(),
                                jenisKelamin,
                                nomorTelepon
                        });
                        saveDataToFile();
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(tamuFrame, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
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

        String[] columnNames = {"Kode Kamar", "Lantai", "Nomor Kamar", "Tipe Kamar", "Harga per Malam", "Gambar"};
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
        JButton viewImageButton = new JButton("Lihat Gambar");

        addButton.addActionListener(e -> {
            JTextField kodeField = new JTextField();
            JTextField lantaiField = new JTextField();
            JTextField nomorField = new JTextField();
            JComboBox<String> tipeField = new JComboBox<>(new String[]{"Ekonomi", "Bisnis", "VIP"});

            JLabel hargaLabel = new JLabel("Harga per Malam:");
            JTextField hargaField = new JTextField();
            hargaField.setEditable(false);

            JButton uploadButton = new JButton("Upload Gambar");
            JLabel imagePathLabel = new JLabel("Belum ada gambar");

            final String[] selectedImagePath = {null};

            uploadButton.addActionListener(uploadEvent -> {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedImagePath[0] = fileChooser.getSelectedFile().getAbsolutePath();
                    imagePathLabel.setText(fileChooser.getSelectedFile().getName());
                }
            });

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

            JPanel inputPanel = new JPanel(new GridLayout(6, 2));
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
            inputPanel.add(uploadButton);
            inputPanel.add(imagePathLabel);

            int result = JOptionPane.showConfirmDialog(null, inputPanel, "Tambah Kamar", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Validasi Kode Kamar
                    String kodeKamar = kodeField.getText().trim();
                    if (!kodeKamar.matches("\\d{3}") || Integer.parseInt(kodeKamar) < 101 || Integer.parseInt(kodeKamar) > 410) {
                        throw new IllegalArgumentException("Kode Kamar harus berupa angka 3 digit (101-410).");
                    }

                    // Validasi Lantai
                    String lantai = lantaiField.getText().trim();
                    if (!lantai.matches("\\d") || Integer.parseInt(lantai) < 1 || Integer.parseInt(lantai) > 4) {
                        throw new IllegalArgumentException("Lantai harus berupa angka (1-4).");
                    }

                    // Validasi Nomor Kamar
                    String nomorKamar = nomorField.getText().trim();
                    if (!nomorKamar.matches("\\d{2}") || Integer.parseInt(nomorKamar) < 1 || Integer.parseInt(nomorKamar) > 10) {
                        throw new IllegalArgumentException("Nomor Kamar harus berupa angka 2 digit (01-10).");
                    }

                    // Periksa apakah Kode Kamar sudah ada
                    boolean isDuplicate = kamarList.stream().anyMatch(kamar ->
                            kamar[0].equals(kodeKamar) ||
                                    (kamar[1].equals(lantai) && kamar[2].equals(nomorKamar))
                    );

                    if (isDuplicate) {
                        throw new IllegalArgumentException("Kode Kamar, Lantai, atau Nomor Kamar sudah terisi sebelumnya.");
                    }

                    // Jika validasi berhasil, tambahkan data baru ke tabel
                    String[] newKamar = {
                            kodeKamar,
                            lantai,
                            nomorKamar,
                            (String) tipeField.getSelectedItem(),
                            hargaField.getText(),
                            selectedImagePath[0] != null ? selectedImagePath[0] : "Tidak ada gambar"
                    };
                    model.addRow(newKamar);
                    kamarList.add(newKamar);
                    saveDataToFile();

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
                }
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
                String gambar = (String) model.getValueAt(selectedRow, 5);

                JTextField kodeField = new JTextField(kodeKamar);
                JTextField lantaiField = new JTextField(lantai);
                JTextField nomorField = new JTextField(nomorKamar);
                JComboBox<String> tipeField = new JComboBox<>(new String[]{"Ekonomi", "Bisnis", "VIP"});
                tipeField.setSelectedItem(tipeKamar);

                JLabel hargaLabel = new JLabel("Harga per Malam:");
                JTextField hargaField = new JTextField(harga);
                hargaField.setEditable(false);

                JButton uploadButton = new JButton("Upload Gambar");
                JLabel imagePathLabel = new JLabel(gambar.equals("Tidak ada gambar") ? "Belum ada gambar" : gambar);

                final String[] selectedImagePath = {gambar.equals("Tidak ada gambar") ? null : gambar};

                uploadButton.addActionListener(uploadEvent -> {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        selectedImagePath[0] = fileChooser.getSelectedFile().getAbsolutePath();
                        imagePathLabel.setText(fileChooser.getSelectedFile().getName());
                    }
                });

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

                JPanel inputPanel = new JPanel(new GridLayout(6, 2));
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
                inputPanel.add(uploadButton);
                inputPanel.add(imagePathLabel);

                int result = JOptionPane.showConfirmDialog(kamarFrame, inputPanel, "Edit Kamar", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        // Validasi Kode Kamar
                        String newKodeKamar = kodeField.getText().trim();
                        if (!newKodeKamar.matches("\\d{3}") || Integer.parseInt(newKodeKamar) < 101 || Integer.parseInt(newKodeKamar) > 410) {
                            throw new IllegalArgumentException("Kode Kamar harus berupa angka 3 digit (101-410).");
                        }

                        // Validasi Lantai
                        String newLantai = lantaiField.getText().trim();
                        if (!newLantai.matches("\\d") || Integer.parseInt(newLantai) < 1 || Integer.parseInt(newLantai) > 4) {
                            throw new IllegalArgumentException("Lantai harus berupa angka (1-4).");
                        }

                        // Validasi Nomor Kamar
                        String newNomorKamar = nomorField.getText().trim();
                        if (!newNomorKamar.matches("\\d{2}") || Integer.parseInt(newNomorKamar) < 1 || Integer.parseInt(newNomorKamar) > 10) {
                            throw new IllegalArgumentException("Nomor Kamar harus berupa angka 2 digit (01-10).");
                        }

                        // Periksa apakah Kode Kamar atau kombinasi Lantai dan Nomor Kamar sudah ada (kecuali baris yang sedang diedit)
                        boolean isDuplicate = kamarList.stream().anyMatch(kamar ->
                                !Arrays.equals(kamarList.get(selectedRow), kamar) &&
                                        (kamar[0].equals(newKodeKamar) || (kamar[1].equals(newLantai) && kamar[2].equals(newNomorKamar)))
                        );

                        if (isDuplicate) {
                            throw new IllegalArgumentException("Kode Kamar, Lantai, atau Nomor Kamar sudah terisi sebelumnya.");
                        }

                        // Jika validasi berhasil, perbarui data di tabel
                        model.setValueAt(newKodeKamar, selectedRow, 0);
                        model.setValueAt(newLantai, selectedRow, 1);
                        model.setValueAt(newNomorKamar, selectedRow, 2);
                        model.setValueAt(tipeField.getSelectedItem(), selectedRow, 3);
                        model.setValueAt(hargaField.getText(), selectedRow, 4);
                        model.setValueAt(selectedImagePath[0] != null ? selectedImagePath[0] : "Tidak ada gambar", selectedRow, 5);

                        kamarList.set(selectedRow, new String[]{
                                newKodeKamar,
                                newLantai,
                                newNomorKamar,
                                (String) tipeField.getSelectedItem(),
                                hargaField.getText(),
                                selectedImagePath[0] != null ? selectedImagePath[0] : "Tidak ada gambar"
                        });
                        saveDataToFile();

                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(kamarFrame, ex.getMessage(), "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
                    }
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

        viewImageButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String imagePath = (String) model.getValueAt(selectedRow, 5);
                if (imagePath != null && !imagePath.equals("Tidak ada gambar")) {
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(image);

                    JLabel imageLabel = new JLabel(scaledIcon);
                    JOptionPane.showMessageDialog(kamarFrame, imageLabel, "Gambar Kamar", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(kamarFrame, "Tidak ada gambar untuk kamar ini");
                }
            } else {
                JOptionPane.showMessageDialog(kamarFrame, "Pilih baris untuk melihat gambar");
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewImageButton);

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
                try {
                    // Validasi Kode Pemesanan
                    String kodePemesanan = kodeField.getText().trim();
                    if (!kodePemesanan.matches("([A-Z0-9]{4} ){3}[A-Z0-9]{4}")) {
                        throw new IllegalArgumentException("Kode Pemesanan harus terdiri dari huruf kapital dan angka dengan format 4-4-4-4, contoh: HJ3W O1P4 CZMA Q1L9.");
                    }

                    // Validasi duplikasi Kode Pemesanan
                    boolean kodeExists = pemesananList.stream().anyMatch(p -> p[0].equals(kodePemesanan));
                    if (kodeExists) {
                        throw new IllegalArgumentException("Kode Pemesanan sudah ada sebelumnya.");
                    }

                    // Validasi Nomor Kamar
                    String nomorKamar = nomorKamarField.getText().trim();
                    if (!nomorKamar.matches("\\d{1,2}") || Integer.parseInt(nomorKamar) < 1 || Integer.parseInt(nomorKamar) > 10) {
                        throw new IllegalArgumentException("Nomor Kamar harus berupa angka dari 1 hingga 10.");
                    }

                    // Validasi duplikasi Nomor Kamar
                    boolean nomorExists = pemesananList.stream().anyMatch(p -> p[2].equals(nomorKamar));
                    if (nomorExists) {
                        throw new IllegalArgumentException("Nomor Kamar sudah dipesan sebelumnya.");
                    }

                    // Validasi Check-in
                    String checkIn = checkInField.getText().trim();
                    if (!checkIn.matches("[0-9/\\-.: ]+")) {
                        throw new IllegalArgumentException("Check-in hanya boleh mengandung angka dan simbol (contoh: 2023-12-31 14:00).");
                    }

                    // Validasi Check-out
                    String checkOut = checkOutField.getText().trim();
                    if (!checkOut.matches("[0-9/\\-.: ]+")) {
                        throw new IllegalArgumentException("Check-out hanya boleh mengandung angka dan simbol (contoh: 2024-01-02 12:00).");
                    }

                    // Jika semua validasi lolos, tambahkan data ke tabel
                    String[] newPemesanan = {
                            kodePemesanan,
                            namaField.getText().trim(),
                            nomorKamar,
                            checkIn,
                            checkOut,
                            statusField.getText().trim()
                    };
                    model.addRow(newPemesanan);
                    pemesananList.add(newPemesanan);
                    saveDataToFile();

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(pemesananFrame, ex.getMessage(), "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
                }
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
                    try {
                        // Validasi Kode Pemesanan
                        String kodePemesanan = kodeField.getText().trim();
                        if (!kodePemesanan.matches("([A-Z0-9]{4} ){3}[A-Z0-9]{4}")) {
                            throw new IllegalArgumentException("Kode Pemesanan harus terdiri dari huruf kapital dan angka dengan format 4-4-4-4, contoh: HJ3W O1P4 CZMA Q1L9.");
                        }

                        // Validasi duplikasi Kode Pemesanan (kecuali baris yang sedang diedit)
                        boolean kodeExists = pemesananList.stream().anyMatch(p ->
                                !Arrays.equals(pemesananList.get(selectedRow), p) && p[0].equals(kodePemesanan));
                        if (kodeExists) {
                            throw new IllegalArgumentException("Kode Pemesanan sudah ada sebelumnya.");
                        }

                        // Validasi Nomor Kamar
                        String nomorKamar = nomorKamarField.getText().trim();
                        if (!nomorKamar.matches("\\d{1,2}") || Integer.parseInt(nomorKamar) < 1 || Integer.parseInt(nomorKamar) > 10) {
                            throw new IllegalArgumentException("Nomor Kamar harus berupa angka dari 1 hingga 10.");
                        }

                        // Validasi duplikasi Nomor Kamar (kecuali baris yang sedang diedit)
                        boolean nomorExists = pemesananList.stream().anyMatch(p ->
                                !Arrays.equals(pemesananList.get(selectedRow), p) && p[2].equals(nomorKamar));
                        if (nomorExists) {
                            throw new IllegalArgumentException("Nomor Kamar sudah dipesan sebelumnya.");
                        }

                        // Validasi Check-in
                        String checkIn = checkInField.getText().trim();
                        if (!checkIn.matches("[0-9/\\-.: ]+")) {
                            throw new IllegalArgumentException("Check-in hanya boleh mengandung angka dan simbol (contoh: 2023-12-31 14:00).");
                        }

                        // Validasi Check-out
                        String checkOut = checkOutField.getText().trim();
                        if (!checkOut.matches("[0-9/\\-.: ]+")) {
                            throw new IllegalArgumentException("Check-out hanya boleh mengandung angka dan simbol (contoh: 2024-01-02 12:00).");
                        }

                        // Jika semua validasi lolos, perbarui data di tabel
                        model.setValueAt(kodePemesanan, selectedRow, 0);
                        model.setValueAt(namaField.getText().trim(), selectedRow, 1);
                        model.setValueAt(nomorKamar, selectedRow, 2);
                        model.setValueAt(checkIn, selectedRow, 3);
                        model.setValueAt(checkOut, selectedRow, 4);
                        model.setValueAt(statusField.getText().trim(), selectedRow, 5);

                        pemesananList.set(selectedRow, new String[]{
                                kodePemesanan,
                                namaField.getText().trim(),
                                nomorKamar,
                                checkIn,
                                checkOut,
                                statusField.getText().trim()
                        });
                        saveDataToFile();

                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(pemesananFrame, ex.getMessage(), "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
                    }
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
    private static void calculateTotal() {
        int total = 0;
        for (String[] kamar : kamarList) {
            total += Integer.parseInt(kamar[4]);
        }
        JOptionPane.showMessageDialog(null, "Total Harga Kamar: Rp " + total);
    }
}