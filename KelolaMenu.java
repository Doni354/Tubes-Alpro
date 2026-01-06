public class KelolaMenu {
    
    public static void menuUtama() {
        System.out.println("\n--- KELOLA DATA MAKANAN ---");
        System.out.println("1. Tambah Menu Baru");
        System.out.println("2. Ubah Stok/Harga");
        System.out.println("3. Hapus Menu");
        System.out.println("4. Kembali");
        System.out.print("Pilih: ");
        String c = DataGlobal.scanner.nextLine();

        if (c.equals("1")) tambahMenuBaru();
        else if (c.equals("2")) ubahMenu();
        else if (c.equals("3")) hapusMenu();
    }
    private static void swapMenu(int a, int b) {
        // nama
        String tempName = DataGlobal.menuNames[a];
        DataGlobal.menuNames[a] = DataGlobal.menuNames[b];
        DataGlobal.menuNames[b] = tempName;
    
        // harga
        int tempPrice = DataGlobal.menuPrices[a];
        DataGlobal.menuPrices[a] = DataGlobal.menuPrices[b];
        DataGlobal.menuPrices[b] = tempPrice;
    
        // stok
        int tempStock = DataGlobal.menuStocks[a];
        DataGlobal.menuStocks[a] = DataGlobal.menuStocks[b];
        DataGlobal.menuStocks[b] = tempStock;
    }
    public static void tampilkanMenuDenganSort() {
        System.out.println("\n--- LIHAT MENU MAKANAN ---");
        System.out.println("1. Default");
        System.out.println("2. Harga Termurah");
        System.out.println("3. Harga Termahal");
        System.out.println("4. Nama (A-Z)");
        System.out.println("5. Kembali");
        System.out.print("Pilih: ");
    
        switch (DataGlobal.scanner.nextLine()) {
            case "1":
                lihatTabelMenu();
                break;
            case "2":
                sortHargaAsc();
                lihatTabelMenu();
                break;
            case "3":
                sortHargaDesc();
                lihatTabelMenu();
                break;
            case "4":
                sortNamaAsc();
                lihatTabelMenu();
                break;
            case "5":
                return;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
    
    public static void lihatTabelMenu() {
        System.out.println("\n--- DAFTAR MENU ---");
        System.out.printf("%-20s %-10s %-5s\n", "Nama", "Harga", "Stok");
        for (int i = 0; i < DataGlobal.menuCount; i++) {
            System.out.printf("%-20s %-10d %-5d\n", 
                DataGlobal.menuNames[i], DataGlobal.menuPrices[i], DataGlobal.menuStocks[i]);
        }
    }
    public static void sortHargaAsc() {
        for (int i = 0; i < DataGlobal.menuCount - 1; i++) {
            for (int j = 0; j < DataGlobal.menuCount - i - 1; j++) {
                if (DataGlobal.menuPrices[j] > DataGlobal.menuPrices[j + 1]) {
                    swapMenu(j, j + 1);
                }
            }
        }
    }
    public static void sortHargaDesc() {
        for (int i = 0; i < DataGlobal.menuCount - 1; i++) {
            for (int j = 0; j < DataGlobal.menuCount - i - 1; j++) {
                if (DataGlobal.menuPrices[j] < DataGlobal.menuPrices[j + 1]) {
                    swapMenu(j, j + 1);
                }
            }
        }
    }
    public static void sortNamaAsc() {
        for (int i = 0; i < DataGlobal.menuCount - 1; i++) {
            for (int j = 0; j < DataGlobal.menuCount - i - 1; j++) {
                if (DataGlobal.menuNames[j]
                        .compareToIgnoreCase(DataGlobal.menuNames[j + 1]) > 0) {
                    swapMenu(j, j + 1);
                }
            }
        }
    }
    

    private static void tambahMenuBaru() {
        System.out.print("Nama Makanan: ");
        String nama = DataGlobal.scanner.nextLine();
        System.out.print("Harga: ");
        int harga = Integer.parseInt(DataGlobal.scanner.nextLine());
        System.out.print("Stok Awal: ");
        int stok = Integer.parseInt(DataGlobal.scanner.nextLine());
        DataGlobal.tambahMenu(nama, harga, stok);
        System.out.println("Menu berhasil ditambahkan.");
    }

    private static void ubahMenu() {
        lihatTabelMenu();
        System.out.print("Nama Menu yang diubah: ");
        String nama = DataGlobal.scanner.nextLine();
        int idx = cariIndexMenu(nama);
        if (idx != -1) {
            System.out.print("Harga Baru (0 jika tdk ubah): ");
            int h = Integer.parseInt(DataGlobal.scanner.nextLine());
            System.out.print("Stok Baru (-1 jika tdk ubah): ");
            int s = Integer.parseInt(DataGlobal.scanner.nextLine());
            if(h > 0) DataGlobal.menuPrices[idx] = h;
            if(s >= 0) DataGlobal.menuStocks[idx] = s;
            System.out.println("Diperbarui.");
        } else System.out.println("Tidak ditemukan.");
    }

    private static void hapusMenu() {
        lihatTabelMenu();
        System.out.print("Nama Menu dihapus: ");
        String nama = DataGlobal.scanner.nextLine();
        int idx = cariIndexMenu(nama);
        if (idx != -1) {
            for (int i = idx; i < DataGlobal.menuCount - 1; i++) {
                DataGlobal.menuNames[i] = DataGlobal.menuNames[i+1];
                DataGlobal.menuPrices[i] = DataGlobal.menuPrices[i+1];
                DataGlobal.menuStocks[i] = DataGlobal.menuStocks[i+1];
            }
            DataGlobal.menuCount--;
            System.out.println("Dihapus.");
        } else System.out.println("Tidak ditemukan.");
    }

    public static int cariIndexMenu(String nama) {
        for (int i = 0; i < DataGlobal.menuCount; i++) {
            if (DataGlobal.menuNames[i].equalsIgnoreCase(nama)) return i;
        }
        return -1;
    }
}