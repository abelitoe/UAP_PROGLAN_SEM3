package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class HotelBookingSystem {
    // ArrayList untuk menyimpan data kamar, tamu, dan pemesanan
    protected static final ArrayList<String[]> kamarList = new ArrayList<>();
    protected static final ArrayList<String[]> tamuList = new ArrayList<>();
    protected static final ArrayList<String[]> pemesananList = new ArrayList<>();

    public static void main(String[] args) {
        loadDataFromFile(); // Memuat data dari file saat aplikasi dijalankan
        SwingUtilities.invokeLater(HotelBookingSystem::showLoginScreen); // Menampilkan layar login di Event Dispatch Thread
    }

    protected static void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("hotel_data.txt"))) {
            writer.println("Data Kamar:"); // Menulis header untuk data kamar
            for (String[] kamar : kamarList) {
                writer.println(String.join(",", kamar)); // Menyimpan setiap data kamar ke file
            }

            writer.println("\nData Tamu:"); // Menulis header untuk data tamu
            for (String[] tamu : tamuList) {
                writer.println(String.join(",", tamu)); // Menyimpan setiap data tamu ke file
            }

            writer.println("\nData Pemesanan:"); // Menulis header untuk data pemesanan
            for (String[] pemesanan : pemesananList) {
                writer.println(String.join(",", pemesanan)); // Menyimpan setiap data pemesanan ke file
            }

            // Memberikan konfirmasi jika data berhasil disimpan
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan ke file hotel_data.txt");
        } catch (IOException e) {
            // Menampilkan pesan kesalahan jika terjadi masalah saat menyimpan data
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data: " + e.getMessage());
        }
    }

    protected static void loadDataFromFile() {
        File file = new File("hotel_data.txt");
        if (!file.exists()) {
            return; // Jika file tidak ditemukan, hentikan proses
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<String[]> currentList = null; // Variabel untuk menentukan bagian data mana yang sedang diproses

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Data Kamar:")) {
                    currentList = kamarList; // Pindahkan fokus ke data kamar
                } else if (line.startsWith("Data Tamu:")) {
                    currentList = tamuList; // Pindahkan fokus ke data tamu
                } else if (line.startsWith("Data Pemesanan:")) {
                    currentList = pemesananList; // Pindahkan fokus ke data pemesanan
                } else if (!line.isBlank() && currentList != null) {
                    currentList.add(line.split(",")); // Menambahkan data ke daftar yang sesuai
                }
            }
        } catch (IOException e) {
            // Menampilkan pesan kesalahan jika terjadi masalah saat memuat data
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat memuat data: " + e.getMessage());
        }
    }

    private static void showLoginScreen() {
        // Membuat jendela login
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 400);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Sistem Booking Hotel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Membuat input username dan password
        JLabel usernameLabel = new JLabel("Username");
        JTextField usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");

        // Menambahkan komponen ke panel login
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

        // Menambahkan logika tombol login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.equals("dito") && password.equals("ambaruwo")) {
                // Validasi login berhasil
                JOptionPane.showMessageDialog(loginFrame, "Login Berhasil");
                loginFrame.dispose();
                showMainMenu(); // Menampilkan menu utama
            } else {
                // Menampilkan pesan kesalahan login
                JOptionPane.showMessageDialog(loginFrame, "Username atau Password salah");
            }
        });

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }

    private static void showMainMenu() {
        // Membuat jendela menu utama
        JFrame mainMenuFrame = new JFrame("Menu Utama");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(400, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Sistem Booking Hotel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel buttonPanel = getjPanel(); // Memuat panel dengan tombol-tombol fitur

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        mainMenuFrame.add(mainPanel);
        mainMenuFrame.setVisible(true);
    }

    private static JPanel getjPanel() {
        // Membuat panel dengan tombol-tombol untuk fitur
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton tamuButton = new JButton("Menu Tamu");
        JButton kamarButton = new JButton("Menu Kamar");
        JButton pemesananButton = new JButton("Menu Pemesanan");
        JButton calculateButton = new JButton("Hitung Total Kamar");

        // Menambahkan aksi tombol
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


    protected static void showTamuList() {
        // Membuat jendela baru untuk menampilkan data tamu
        JFrame tamuFrame = new JFrame("Data Tamu");
        tamuFrame.setSize(800, 400); // Menetapkan ukuran jendela

        // Menentukan nama kolom untuk tabel tamu
        String[] columnNames = {"Nama", "Kartu Kredit", "Alamat", "Negara", "Jenis Kelamin", "Nomor Telepon"};
        // Membuat model tabel untuk menampilkan data tamu
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        // Membuat tabel dengan model yang telah didefinisikan
        JTable table = new JTable(model);

        // Menambahkan data tamu yang sudah ada ke dalam model tabel
        for (String[] tamu : tamuList) {
            model.addRow(tamu);
        }

        // Membungkus tabel dalam JScrollPane untuk memungkinkan scroll jika data melebihi ukuran tampilan
        JScrollPane scrollPane = new JScrollPane(table);

        // Membuat panel untuk tombol-tombol
        JPanel buttonPanel = new JPanel();
        // Menambahkan tombol "Tambah", "Edit", dan "Hapus"
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");

        // Aksi ketika tombol "Tambah" ditekan
        addButton.addActionListener(e -> {
            // Membuat field input untuk data tamu baru
            JTextField namaField = new JTextField();
            JTextField kartuKreditField = new JTextField();
            JTextField alamatField = new JTextField();
            JTextField negaraField = new JTextField();
            JTextField jenisKelaminField = new JTextField();
            JTextField nomorTeleponField = new JTextField();

            // Membuat panel input dengan grid layout untuk menata form input
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

            // Menampilkan form input dalam dialog untuk menambah tamu baru
            int result = JOptionPane.showConfirmDialog(tamuFrame, inputPanel, "Tambah Tamu", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Validasi input untuk nama tamu
                    String nama = namaField.getText();
                    if (!nama.matches("[a-zA-Z ]+")) {
                        throw new IllegalArgumentException("Nama hanya boleh berisi huruf.");
                    }

                    // Validasi kartu kredit, hanya menerima angka 12 digit
                    String kartuKredit = kartuKreditField.getText().trim(); // Menghapus spasi ekstra di awal/akhir
                    if (!kartuKredit.matches("^\\d{12}$")) { // Validasi kartu kredit (12 digit angka)
                        throw new IllegalArgumentException("Kartu Kredit harus terdiri dari 12 digit angka.");
                    }

                    // Validasi jenis kelamin hanya boleh "Laki-Laki" atau "Perempuan"
                    String jenisKelamin = jenisKelaminField.getText();
                    if (!jenisKelamin.equalsIgnoreCase("Laki-Laki") && !jenisKelamin.equalsIgnoreCase("Perempuan")) {
                        throw new IllegalArgumentException("Jenis Kelamin harus Laki-Laki atau Perempuan.");
                    }

                    // Validasi nomor telepon, hanya menerima 10 hingga 12 digit angka
                    String nomorTelepon = nomorTeleponField.getText();
                    if (!nomorTelepon.matches("\\d{10,12}")) {
                        throw new IllegalArgumentException("Nomor Telepon harus terdiri dari 10 hingga 12 digit angka.");
                    }

                    // Jika semua input valid, menambahkan tamu baru ke dalam model tabel
                    String[] newTamu = {
                            nama,
                            kartuKredit,
                            alamatField.getText(),
                            negaraField.getText(),
                            jenisKelamin,
                            nomorTelepon
                    };
                    model.addRow(newTamu);  // Menambah baris baru ke dalam tabel
                    tamuList.add(newTamu);  // Menambah data tamu ke dalam list tamu
                    saveDataToFile();  // Menyimpan data ke file (fungsi penyimpanan belum ada di kode ini)
                } catch (IllegalArgumentException ex) {
                    // Menampilkan pesan error jika input tidak valid
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


    protected static void showKamarList() {
        // Membuat jendela (frame) baru untuk menampilkan data kamar
        JFrame kamarFrame = new JFrame("Data Kamar");
        kamarFrame.setSize(800, 400); // Menetapkan ukuran jendela

        // Mendefinisikan kolom untuk tabel data kamar
        String[] columnNames = {"Kode Kamar", "Lantai", "Nomor Kamar", "Tipe Kamar", "Harga per Malam", "Gambar"};
        // Membuat model tabel untuk menampilkan data kamar
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        // Membuat tabel dengan model yang telah didefinisikan
        JTable table = new JTable(model);

        // Menambahkan data kamar ke dalam model tabel
        for (String[] kamar : kamarList) {
            model.addRow(kamar);
        }

        // Membungkus tabel dalam JScrollPane untuk memungkinkan scroll jika data melebihi ukuran tampilan
        JScrollPane scrollPane = new JScrollPane(table);

        // Membuat panel untuk tombol-tombol
        JPanel buttonPanel = new JPanel();
        // Mendefinisikan tombol-tombol yang akan ditampilkan
        JButton editButton = new JButton("Edit");
        JButton addButton = new JButton("Tambah");
        JButton deleteButton = new JButton("Hapus");
        JButton viewImageButton = new JButton("Lihat Gambar");

        // Aksi ketika tombol "Tambah" ditekan
        addButton.addActionListener(e -> {
            // Membuat field input untuk data kamar baru
            JTextField kodeField = new JTextField();
            JTextField lantaiField = new JTextField();
            JTextField nomorField = new JTextField();
            JComboBox<String> tipeField = new JComboBox<>(new String[]{"Ekonomi", "Bisnis", "VIP"});

            // Label dan field untuk harga per malam (harga diubah berdasarkan tipe kamar)
            JLabel hargaLabel = new JLabel("Harga per Malam:");
            JTextField hargaField = new JTextField();
            hargaField.setEditable(false); // Harga tidak bisa diedit manual

            // Tombol untuk meng-upload gambar
            JButton uploadButton = new JButton("Upload Gambar");
            JLabel imagePathLabel = new JLabel("Belum ada gambar");

            // Array untuk menyimpan path gambar yang dipilih
            final String[] selectedImagePath = {null};

            // Aksi ketika tombol "Upload Gambar" ditekan
            uploadButton.addActionListener(uploadEvent -> {
                JFileChooser fileChooser = new JFileChooser(); // Menampilkan dialog pemilih file
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedImagePath[0] = fileChooser.getSelectedFile().getAbsolutePath(); // Menyimpan path gambar
                    imagePathLabel.setText(fileChooser.getSelectedFile().getName()); // Menampilkan nama file
                }
            });

            // Aksi ketika tipe kamar dipilih
            tipeField.addActionListener(event -> {
                String tipe = (String) tipeField.getSelectedItem();
                if (tipe != null) {
                    // Menentukan harga per malam berdasarkan tipe kamar yang dipilih
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
                kamarList.remove(selectedRow);// Menghapus baris yang yang dipilih
                saveDataToFile();
            } else {
                JOptionPane.showMessageDialog(kamarFrame, "Pilih baris yang ingin dihapus");
            }
        });

        viewImageButton.addActionListener(e -> {
            // Mendapatkan baris yang dipilih dalam tabel
            int selectedRow = table.getSelectedRow();

            // Memeriksa apakah ada baris yang dipilih
            if (selectedRow != -1) {
                // Mengambil path gambar dari kolom 5 (kolom gambar)
                String imagePath = (String) model.getValueAt(selectedRow, 5);

                // Memeriksa apakah gambar ada dan path gambar tidak kosong atau tidak bertuliskan "Tidak ada gambar"
                if (imagePath != null && !imagePath.equals("Tidak ada gambar")) {
                    // Membuat objek ImageIcon dari path gambar yang ditemukan
                    ImageIcon imageIcon = new ImageIcon(imagePath);

                    // Mengubah ukuran gambar agar sesuai (300x300) dengan tetap menjaga rasio
                    Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);

                    // Membuat ImageIcon baru dengan gambar yang sudah diubah ukurannya
                    ImageIcon scaledIcon = new ImageIcon(image);

                    // Menampilkan gambar dalam dialog pop-up
                    JLabel imageLabel = new JLabel(scaledIcon);
                    JOptionPane.showMessageDialog(kamarFrame, imageLabel, "Gambar Kamar", JOptionPane.PLAIN_MESSAGE);
                } else {
                    // Menampilkan pesan jika tidak ada gambar yang tersedia untuk kamar ini
                    JOptionPane.showMessageDialog(kamarFrame, "Tidak ada gambar untuk kamar ini");
                }
            } else {
                // Menampilkan pesan jika pengguna tidak memilih baris apapun dalam tabel
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

    private static void calculateTotal() {
        int total = 0;
        for (String[] kamar : kamarList) {
            total += Integer.parseInt(kamar[4]);
        }
        JOptionPane.showMessageDialog(null, "Total Harga Kamar: Rp " + total);
    }

    protected static void showPemesananList() {
        // Membuat jendela baru untuk menampilkan data pemesanan
        JFrame pemesananFrame = new JFrame("Data Pemesanan");
        pemesananFrame.setSize(800, 400); // Menetapkan ukuran jendela

        // Menentukan nama kolom untuk tabel pemesanan
        String[] columnNames = {"Kode Pemesanan", "Nama Tamu", "Nomor Kamar", "Check-in", "Check-out", "Status"};
        // Membuat model tabel untuk menampilkan data pemesanan
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        // Membuat tabel dengan model yang telah didefinisikan
        JTable table = new JTable(model);

        // Menambahkan data pemesanan ke dalam model tabel
        for (String[] pemesanan : pemesananList) {
            model.addRow(pemesanan);
        }

        // Membungkus tabel dalam JScrollPane untuk memungkinkan scroll jika data melebihi ukuran tampilan
        JScrollPane scrollPane = new JScrollPane(table);

        // Membuat panel untuk tombol-tombol
        JPanel buttonPanel = new JPanel();
        // Menambahkan tombol "Tambah", "Edit", dan "Hapus"
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");

        // Aksi ketika tombol "Tambah" ditekan
        addButton.addActionListener(e -> {
            // Membuat field input untuk data pemesanan baru
            JTextField kodeField = new JTextField();
            JTextField namaField = new JTextField();
            JTextField nomorKamarField = new JTextField();
            JTextField checkInField = new JTextField();
            JTextField checkOutField = new JTextField();
            JTextField statusField = new JTextField();

            // Membuat panel input dengan grid layout untuk menata form input
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

            // Menampilkan form input dalam dialog untuk menambah pemesanan baru
            int option = JOptionPane.showConfirmDialog(pemesananFrame, inputPanel, "Tambah Pemesanan", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Jika tombol OK ditekan, menambahkan data pemesanan baru ke tabel
                String[] pemesananBaru = {
                        kodeField.getText(),
                        namaField.getText(),
                        nomorKamarField.getText(),
                        checkInField.getText(),
                        checkOutField.getText(),
                        statusField.getText()
                };
                model.addRow(pemesananBaru);
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
}