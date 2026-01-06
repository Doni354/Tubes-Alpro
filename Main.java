public class Main {
    public static void main(String[] args) {
        DataGlobal.initData(); // Load data awal
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("        SISTEM PEMESANAN MAKANAN        ");
            System.out.println("========================================");
            
            if (DataGlobal.currentUser == null) {
                // Jika belum login, tampilkan menu Auth
                System.out.println("1. Login");
                System.out.println("2. Registrasi");
                System.out.println("3. Keluar");
                System.out.print("Pilih menu: ");
                String choice = DataGlobal.scanner.nextLine();

                switch (choice) {
                    case "1": FiturAuth.login(); break;
                    case "2": FiturAuth.register(); break;
                    case "3": running = false; break;
                    default: System.out.println("Pilihan tidak valid!");
                }
            } else {
                // Jika sudah login, arahkan sesuai Role
                if (DataGlobal.currentRole.equals("admin")) {
                    MenuAdmin.tampilkan();
                } else {
                    MenuCustomer.tampilkan();
                }
            }
        }
        System.out.println("Aplikasi ditutup.");
    }
}