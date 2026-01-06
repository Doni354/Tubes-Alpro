public class MenuCustomer {
    public static void tampilkan() {
        System.out.println("\n--- MENU PELANGGAN (" + DataGlobal.currentUser + ") ---");
        System.out.println("1. Lihat Menu Makanan");
        System.out.println("2. Buat Pesanan Baru");
        System.out.println("3. Bayar Pesanan / Cek Status");
        System.out.println("4. Konfirmasi Pengambilan Pesanan");
        System.out.println("5. Logout");
        System.out.print("Pilih: ");
        String choice = DataGlobal.scanner.nextLine();

        switch (choice) {
            case "1": KelolaMenu.tampilkanMenuDenganSort(); break;
            case "2": KelolaOrder.buatPesanan(); break;
            case "3": KelolaPembayaran.statusDanBayarCustomer(); break;
            case "4": KelolaOrder.konfirmasiPengambilan(); break;
            case "5": FiturAuth.logout(); break;
            default: System.out.println("Pilihan tidak valid.");
        }
    }
}