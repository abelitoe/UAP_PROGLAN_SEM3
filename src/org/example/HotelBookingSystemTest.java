package org.example;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelBookingSystemTest {

    @BeforeEach
    void setUp() {
        HotelBookingSystem.kamarList.clear();
        HotelBookingSystem.tamuList.clear();
        HotelBookingSystem.pemesananList.clear();
    }

    @Test
    void testSaveDataToFile() throws IOException {
        HotelBookingSystem.kamarList.add(new String[]{"101", "1", "01", "Ekonomi", "200000", "Tidak ada gambar"});
        HotelBookingSystem.tamuList.add(new String[]{"John Doe", "123456789012", "Alamat", "Indonesia", "Laki-Laki", "081234567890"});
        HotelBookingSystem.pemesananList.add(new String[]{"KODE1234", "John Doe", "01", "2024-01-01", "2024-01-05", "Confirmed"});

        HotelBookingSystem.saveDataToFile();

        File file = new File("hotel_data.txt");
        assertTrue(file.exists(), "File should exist after saving data");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            assertTrue(reader.lines().anyMatch(line -> line.contains("101,1,01,Ekonomi,200000")), "File should contain kamar data");
            assertTrue(reader.lines().anyMatch(line -> line.contains("John Doe,123456789012,Alamat,Indonesia,Laki-Laki,081234567890")), "File should contain tamu data");
            assertTrue(reader.lines().anyMatch(line -> line.contains("KODE1234,John Doe,01,2024-01-01,2024-01-05,Confirmed")), "File should contain pemesanan data");
        }
    }

    @Test
    void testLoadDataFromFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("hotel_data.txt"))) {
            writer.println("Data Kamar:");
            writer.println("101,1,01,Ekonomi,200000");
            writer.println("\nData Tamu:");
            writer.println("John Doe,123456789012,Alamat,Indonesia,Laki-Laki,081234567890");
            writer.println("\nData Pemesanan:");
            writer.println("KODE1234,John Doe,01,2024-01-01,2024-01-05,Confirmed");
        }

        HotelBookingSystem.loadDataFromFile();

        assertEquals(1, HotelBookingSystem.kamarList.size());
        assertEquals(1, HotelBookingSystem.tamuList.size());
        assertEquals(1, HotelBookingSystem.pemesananList.size());
    }

    @Test
    void testShowTamuList() {
        HotelBookingSystem.tamuList.add(new String[]{"John Doe", "123456789012", "Alamat", "Indonesia", "Laki-Laki", "081234567890"});
        JTable mockTable = mock(JTable.class);

        assertDoesNotThrow(HotelBookingSystem::showTamuList);
    }

    @Test
    void testShowKamarList() {
        HotelBookingSystem.kamarList.add(new String[]{"101", "1", "01", "Ekonomi", "200000", "Tidak ada gambar"});
        JTable mockTable = mock(JTable.class);

        assertDoesNotThrow(HotelBookingSystem::showKamarList);
    }

    @Test
    void testShowPemesananList() {
        HotelBookingSystem.pemesananList.add(new String[]{"KODE1234", "John Doe", "01", "2024-01-01", "2024-01-05", "Confirmed"});
        JTable mockTable = mock(JTable.class);

        assertDoesNotThrow(HotelBookingSystem::showPemesananList);
    }
}
