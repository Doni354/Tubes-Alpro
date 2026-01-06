public class MenuAdmin {
    public static void tampilkan() {
        System.out.println("\n--- DASHBOARD ADMIN ---");
        System.out.println("1. Kelola Menu Makanan (Tambah/Hapus/Stok)");
        System.out.println("2. Kelola Metode Pembayaran");
        System.out.println("3. Konfirmasi Pesanan Masuk");
        System.out.println("4. Lihat Semua Pesanan");
        System.out.println("5. Logout");
        System.out.print("Pilih: ");
        String choice = DataGlobal.scanner.nextLine();

        switch (choice) {
            case "1": KelolaMenu.menuUtama(); break;
            case "2": KelolaPembayaran.menuAdminMetode(); break;
            case "3": KelolaOrder.konfirmasiPesanan(); break;
            case "4": KelolaOrder.lihatSemuaPesanan(); break;
            case "5": FiturAuth.logout(); break;
            default: System.out.println("Pilihan tidak valid.");
        }
    }
}