/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
import java.util.Scanner;

public class SistemPemesanan {
    // --- KONFIGURASI BATAS DATA (ARRAY) ---
    static final int MAX_USERS = 50;
    static final int MAX_MENU = 50;
    static final int MAX_ORDERS = 100;
    static final int MAX_PAYMENT_METHODS = 10;

    // --- PENYIMPANAN DATA (ARRAY) ---
    
    // Data User
    static String[] usernames = new String[MAX_USERS];
    static String[] passwords = new String[MAX_USERS];
    static String[] roles = new String[MAX_USERS]; // "admin" atau "customer"
    static int userCount = 0;

    // Data Menu
    static String[] menuNames = new String[MAX_MENU];
    static int[] menuPrices = new int[MAX_MENU];
    static int[] menuStocks = new int[MAX_MENU];
    static int menuCount = 0;

    // Data Pesanan (Transaksi)
    static int[] orderIds = new int[MAX_ORDERS];
    static String[] orderUsernames = new String[MAX_ORDERS];
    static String[] orderMenuNames = new String[MAX_ORDERS];
    static int[] orderQuantities = new int[MAX_ORDERS];
    static int[] orderTotalPrices = new int[MAX_ORDERS];
    static String[] orderPaymentMethods = new String[MAX_ORDERS];
    static String[] orderPaymentProofs = new String[MAX_ORDERS]; // ID Transaksi dari user
    static String[] orderStatuses = new String[MAX_ORDERS]; 
    // Status: "Menunggu Pembayaran", "Menunggu Konfirmasi", "Siap Diambil", "Ditolak"
    static int orderCount = 0;

    // Data Metode Pembayaran
    static String[] paymentMethods = new String[MAX_PAYMENT_METHODS];
    static int paymentMethodCount = 0;

    // Utilitas
    static Scanner scanner = new Scanner(System.in);
    static String currentUser = null;
    static String currentRole = null;

    public static void main(String[] args) {
        initData(); // Isi data dummy awal
        boolean running = true;
/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
        while (running) {
            System.out.println("\n========================================");
            System.out.println("   SISTEM PEMESANAN MAKANAN (CTE) CLI   ");
            System.out.println("========================================");
            
            if (currentUser == null) {
                System.out.println("1. Login");
                System.out.println("2. Registrasi (Customer)");
                System.out.println("3. Keluar");
                System.out.print("Pilih menu: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1": login(); break;
                    case "2": register(); break;
                    case "3": running = false; break;
                    default: System.out.println("Pilihan tidak valid!");
                }
            } else {
                // Menu berdasarkan Role
                if (currentRole.equals("admin")) {
                    menuAdmin();
                } else {
                    menuCustomer();
                }
            }
        }
        System.out.println("Terima kasih telah menggunakan aplikasi ini.");
    }

    // --- INISIALISASI DATA AWAL ---
    static void initData() {
        // Akun Admin Default
        usernames[0] = "admin";
        passwords[0] = "admin123";
        roles[0] = "admin";
        userCount++;

        // Akun Customer Contoh
        usernames[1] = "user";
        passwords[1] = "user123";
        roles[1] = "customer";
        userCount++;

/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
        // Menu Makanan Default
        tambahMenuKeArray("Nasi Goreng", 15000, 20);
        tambahMenuKeArray("Ayam Bakar", 20000, 15);
        tambahMenuKeArray("Es Teh Manis", 5000, 50);

        // Metode Pembayaran Default
        paymentMethods[0] = "Bayar di Kasir";
        paymentMethodCount++;
        paymentMethods[1] = "Transfer Bank (BCA/Mandiri)";
        paymentMethodCount++;
    }

    static void tambahMenuKeArray(String nama, int harga, int stok) {
        if (menuCount < MAX_MENU) {
            menuNames[menuCount] = nama;
            menuPrices[menuCount] = harga;
            menuStocks[menuCount] = stok;
            menuCount++;
        }
    }

    // --- FITUR AUTENTIKASI ---
    static void register() {
        if (userCount >= MAX_USERS) {
            System.out.println("Database user penuh!");
            return;
        }

        System.out.print("Masukkan Username baru: ");
        String u = scanner.nextLine();
        
        // Cek username unik
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equalsIgnoreCase(u)) {
                System.out.println("Username sudah terpakai!");
                return;
            }
        }

/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
        System.out.print("Masukkan Password baru: ");
        String p = scanner.nextLine();

        usernames[userCount] = u;
        passwords[userCount] = p;
        roles[userCount] = "customer"; // Default role
        userCount++;
        System.out.println("Registrasi berhasil! Silakan login.");
    }

    static void login() {
        System.out.print("Username: ");
        String u = scanner.nextLine();
        System.out.print("Password: ");
        String p = scanner.nextLine();

        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(u) && passwords[i].equals(p)) {
                currentUser = u;
                currentRole = roles[i];
                System.out.println("Login berhasil sebagai " + currentRole + "!");
                return;
            }
        }
        System.out.println("Username atau password salah.");
    }

    static void logout() {
        currentUser = null;
        currentRole = null;
        System.out.println("Anda telah logout.");
    }

    // --- MENU ADMIN ---
    static void menuAdmin() {
        System.out.println("\n--- DASHBOARD ADMIN ---");
        System.out.println("1. Kelola Menu (Tambah/Edit/Hapus)");
        System.out.println("2. Kelola Metode Pembayaran");
        System.out.println("3. Konfirmasi Pesanan Masuk");
        System.out.println("4. Lihat Semua Pesanan");
        System.out.println("5. Logout");
        System.out.print("Pilih: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": kelolaMenu(); break;
            case "2": kelolaMetodePembayaran(); break;
            case "3": konfirmasiPesanan(); break;
            case "4": lihatSemuaPesanan(); break;
            case "5": logout(); break;
            default: System.out.println("Pilihan tidak valid.");
        }
    }

/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
    static void kelolaMenu() {
        System.out.println("\n--- KELOLA MENU ---");
        System.out.println("1. Tambah Menu Baru");
        System.out.println("2. Ubah Stok/Harga");
        System.out.println("3. Hapus Menu");
        System.out.println("4. Lihat Daftar Menu");
        System.out.println("5. Kembali");
        System.out.print("Pilih: ");
        String c = scanner.nextLine();

        if (c.equals("1")) {
            System.out.print("Nama Makanan: ");
            String nama = scanner.nextLine();
            System.out.print("Harga: ");
            int harga = Integer.parseInt(scanner.nextLine());
            System.out.print("Stok Awal: ");
            int stok = Integer.parseInt(scanner.nextLine());
            tambahMenuKeArray(nama, harga, stok);
            System.out.println("Menu berhasil ditambahkan.");

        } else if (c.equals("2")) {
            lihatMenuTabel();
            System.out.print("Masukkan Nama Menu yang ingin diubah: ");
            String nama = scanner.nextLine();
            int index = cariIndexMenu(nama);
            if (index != -1) {
                System.out.print("Harga Baru (Input 0 jika tidak ubah): ");
                int h = Integer.parseInt(scanner.nextLine());
                System.out.print("Stok Baru (Input -1 jika tidak ubah): ");
                int s = Integer.parseInt(scanner.nextLine());
                
                if(h > 0) menuPrices[index] = h;
                if(s >= 0) menuStocks[index] = s;
                System.out.println("Menu diperbarui.");
            } else {
                System.out.println("Menu tidak ditemukan.");
            }

        } else if (c.equals("3")) {
            // Hapus menu (Shift array)
            lihatMenuTabel();
            System.out.print("Masukkan Nama Menu yang dihapus: ");
            String nama = scanner.nextLine();
            int index = cariIndexMenu(nama);
            if (index != -1) {
                for (int i = index; i < menuCount - 1; i++) {
                    menuNames[i] = menuNames[i+1];
                    menuPrices[i] = menuPrices[i+1];
                    menuStocks[i] = menuStocks[i+1];
                }
                menuCount--;
                System.out.println("Menu dihapus.");
            } else {
                System.out.println("Menu tidak ditemukan.");
            }
        } else if (c.equals("4")) {
            lihatMenuTabel();
        }
    }
/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
    static void kelolaMetodePembayaran() {
        System.out.println("\n--- METODE PEMBAYARAN ---");
        for(int i=0; i<paymentMethodCount; i++) {
            System.out.println((i+1) + ". " + paymentMethods[i]);
        }
        System.out.println("Ketik 'tambah' untuk menambah baru, atau tekan Enter untuk kembali.");
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("tambah")) {
            if(paymentMethodCount < MAX_PAYMENT_METHODS) {
                System.out.print("Nama Metode Baru: ");
                paymentMethods[paymentMethodCount] = scanner.nextLine();
                paymentMethodCount++;
                System.out.println("Metode ditambahkan.");
            } else {
                System.out.println("Slot metode penuh.");
            }
        }
    }

    static void konfirmasiPesanan() {
        System.out.println("\n--- KONFIRMASI PEMBAYARAN ---");
        boolean found = false;
        for (int i = 0; i < orderCount; i++) {
            if (orderStatuses[i].equals("Menunggu Konfirmasi")) {
                System.out.println("Order ID: " + orderIds[i] + 
                                 " | User: " + orderUsernames[i] +
                                 " | Item: " + orderMenuNames[i] +
                                 " | Total: Rp" + orderTotalPrices[i] +
                                 " | Bukti/Ref: " + orderPaymentProofs[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Tidak ada pesanan yang perlu dikonfirmasi.");
            return;
        }

        System.out.print("Masukkan ID Order untuk diproses: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            int idx = cariIndexOrder(id);

            if (idx != -1 && orderStatuses[idx].equals("Menunggu Konfirmasi")) {
                System.out.print("Tindakan (1: Terima, 2: Tolak): ");
                String act = scanner.nextLine();
                if (act.equals("1")) {
                    orderStatuses[idx] = "Siap Diambil";
                    System.out.println("Pesanan disetujui! Status: Siap Diambil.");
                } else if (act.equals("2")) {
                    orderStatuses[idx] = "Ditolak";
                    // Kembalikan stok jika ditolak
                    int menuIdx = cariIndexMenu(orderMenuNames[idx]);
                    if (menuIdx != -1) {
                        menuStocks[menuIdx] += orderQuantities[idx];
                    }
                    System.out.println("Pesanan ditolak.");
                }
            } else {
                System.out.println("ID tidak valid atau status salah.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Input harus angka.");
        }
    }
/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
    static void lihatSemuaPesanan() {
        System.out.println("\n--- SEMUA PESANAN ---");
        System.out.printf("%-5s %-10s %-15s %-5s %-10s %-15s\n", "ID", "User", "Menu", "Qty", "Total", "Status");
        for (int i = 0; i < orderCount; i++) {
            System.out.printf("%-5d %-10s %-15s %-5d %-10d %-15s\n", 
                orderIds[i], orderUsernames[i], orderMenuNames[i], 
                orderQuantities[i], orderTotalPrices[i], orderStatuses[i]);
        }
        System.out.println("Tekan Enter untuk kembali...");
        scanner.nextLine();
    }

    // --- MENU CUSTOMER ---
    static void menuCustomer() {
        System.out.println("\n--- MENU PELANGGAN (" + currentUser + ") ---");
        System.out.println("1. Lihat Menu Makanan");
        System.out.println("2. Buat Pesanan Baru");
        System.out.println("3. Bayar Pesanan / Cek Status");
        System.out.println("4. Logout");
        System.out.print("Pilih: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": lihatMenuTabel(); break;
            case "2": buatPesanan(); break;
            case "3": statusDanBayar(); break;
            case "4": logout(); break;
            default: System.out.println("Pilihan tidak valid.");
        }
    }
/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
    static void buatPesanan() {
        lihatMenuTabel();
        System.out.print("Masukkan Nama Menu yang ingin dipesan: ");
        String namaMenu = scanner.nextLine();
        int idx = cariIndexMenu(namaMenu);

        if (idx != -1) {
            if (menuStocks[idx] > 0) {
                System.out.print("Jumlah pesanan: ");
                int qty = Integer.parseInt(scanner.nextLine());

                if (qty > 0 && qty <= menuStocks[idx]) {
                    // Kurangi stok sementara
                    menuStocks[idx] -= qty;

                    // Simpan ke array Order
                    orderIds[orderCount] = orderCount + 101; // ID mulai dari 101
                    orderUsernames[orderCount] = currentUser;
                    orderMenuNames[orderCount] = menuNames[idx];
                    orderQuantities[orderCount] = qty;
                    orderTotalPrices[orderCount] = qty * menuPrices[idx];
                    orderStatuses[orderCount] = "Menunggu Pembayaran";
                    orderPaymentMethods[orderCount] = "-";
                    orderPaymentProofs[orderCount] = "-";
                    
                    System.out.println("Pesanan dibuat! ID Pesanan: " + orderIds[orderCount]);
                    System.out.println("Silakan ke menu Pembayaran untuk menyelesaikan.");
                    orderCount++;
                } else {
                    System.out.println("Stok tidak mencukupi atau jumlah tidak valid.");
                }
            } else {
                System.out.println("Stok habis!");
            }
        } else {
            System.out.println("Menu tidak ditemukan.");
        }
    }
/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
    static void statusDanBayar() {
        System.out.println("\n--- PESANAN SAYA ---");
        boolean adaTagihan = false;
        
        for (int i = 0; i < orderCount; i++) {
            if (orderUsernames[i].equals(currentUser)) {
                System.out.println("ID: " + orderIds[i] + 
                                   " | " + orderMenuNames[i] + " (x" + orderQuantities[i] + ")" +
                                   " | Total: Rp" + orderTotalPrices[i] +
                                   " | Status: " + orderStatuses[i]);
                if (orderStatuses[i].equals("Menunggu Pembayaran")) {
                    adaTagihan = true;
                }
            }
        }

        if (adaTagihan) {
            System.out.print("\nMasukkan ID Pesanan untuk dibayar (atau 0 untuk kembali): ");
            int id = Integer.parseInt(scanner.nextLine());
            if (id == 0) return;

            int idx = cariIndexOrder(id);
            // Validasi: Order ada, milik user yg login, dan statusnya belum bayar
            if (idx != -1 && orderUsernames[idx].equals(currentUser) && orderStatuses[idx].equals("Menunggu Pembayaran")) {
                System.out.println("Pilih Metode Pembayaran:");
                for (int m = 0; m < paymentMethodCount; m++) {
                    System.out.println((m+1) + ". " + paymentMethods[m]);
                }
                System.out.print("Pilih (nomor): ");
                int mIdx = Integer.parseInt(scanner.nextLine()) - 1;

                if (mIdx >= 0 && mIdx < paymentMethodCount) {
                    orderPaymentMethods[idx] = paymentMethods[mIdx];
                    
                    if (paymentMethods[mIdx].equalsIgnoreCase("Bayar di Kasir")) {
                        orderPaymentProofs[idx] = "TUNAI";
                        // Kasir khusus: Jika bayar di kasir, admin nanti yang confirm setelah terima uang
                    } else {
                        System.out.print("Masukkan ID Transaksi/Bukti Transfer (String): ");
                        String bukti = scanner.nextLine();
                        orderPaymentProofs[idx] = bukti;
                    }

                    orderStatuses[idx] = "Menunggu Konfirmasi";
                    System.out.println("Pembayaran diproses. Mohon tunggu konfirmasi Admin.");
                } else {
                    System.out.println("Metode tidak valid.");
                }
            } else {
                System.out.println("ID Pesanan salah atau status bukan menunggu pembayaran.");
            }
        }
    }
/*=================================================

INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)
INI ADALAH SISTEM FULL NYA (BELUM DI PECAH)

====================================================
*/
    // --- HELPER FUNCTIONS ---
    static void lihatMenuTabel() {
        System.out.println("\n--- DAFTAR MENU ---");
        System.out.printf("%-20s %-10s %-5s\n", "Nama", "Harga", "Stok");
        for (int i = 0; i < menuCount; i++) {
            System.out.printf("%-20s %-10d %-5d\n", menuNames[i], menuPrices[i], menuStocks[i]);
        }
        System.out.println("-------------------");
    }

    static int cariIndexMenu(String nama) {
        for (int i = 0; i < menuCount; i++) {
            if (menuNames[i].equalsIgnoreCase(nama)) {
                return i;
            }
        }
        return -1;
    }

    static int cariIndexOrder(int id) {
        for (int i = 0; i < orderCount; i++) {
            if (orderIds[i] == id) {
                return i;
            }
        }
        return -1;
    }
}