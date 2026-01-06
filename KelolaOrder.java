public class KelolaOrder {

    // --- FITUR CUSTOMER ---
    public static void konfirmasiPengambilan() {
        System.out.println("\n--- KONFIRMASI PENGAMBILAN ---");
        boolean adaSiap = false;
        
        for (int i = 0; i < DataGlobal.orderCount; i++) {
            if (DataGlobal.orderUsernames[i].equals(DataGlobal.currentUser) && 
                DataGlobal.orderStatuses[i].equals("Siap Diambil")) {
                
                System.out.println("ID: " + DataGlobal.orderIds[i] + 
                    " | " + DataGlobal.orderMenuNames[i] + " (x" + DataGlobal.orderQuantities[i] + ")" +
                    " | Status: " + DataGlobal.orderStatuses[i]);
                adaSiap = true;
            }
        }

        if (adaSiap) {
            System.out.print("Masukkan ID Pesanan yang sudah diambil (0 kembali): ");
            try {
                String input = DataGlobal.scanner.nextLine();
                if(input.isEmpty()) return;
                int id = Integer.parseInt(input);
                if (id == 0) return;
                
                int idx = cariIndexOrder(id);
                if (idx != -1 && 
                    DataGlobal.orderUsernames[idx].equals(DataGlobal.currentUser) &&
                    DataGlobal.orderStatuses[idx].equals("Siap Diambil")) {
                    
                    DataGlobal.orderStatuses[idx] = "Selesai";
                    System.out.println("Terima kasih! Pesanan telah selesai.");
                } else {
                    System.out.println("ID tidak valid atau pesanan belum siap diambil.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka.");
            }
        } else {
            System.out.println("Tidak ada pesanan yang perlu diambil saat ini.");
        }
    }

    public static void buatPesanan() {
        KelolaMenu.lihatTabelMenu();
        System.out.print("Nama Menu dipesan: ");
        String nama = DataGlobal.scanner.nextLine();
        int idx = KelolaMenu.cariIndexMenu(nama);

        if (idx != -1) {
            if (DataGlobal.menuStocks[idx] > 0) {
                System.out.print("Jumlah: ");
                try {
                    int qty = Integer.parseInt(DataGlobal.scanner.nextLine());
                    
                    if (qty > 0 && qty <= DataGlobal.menuStocks[idx]) {
                        DataGlobal.menuStocks[idx] -= qty; 
                        int oIdx = DataGlobal.orderCount;
                        
                        DataGlobal.orderIds[oIdx] = oIdx + 101;
                        DataGlobal.orderUsernames[oIdx] = DataGlobal.currentUser;
                        DataGlobal.orderMenuNames[oIdx] = DataGlobal.menuNames[idx];
                        DataGlobal.orderQuantities[oIdx] = qty;
                        DataGlobal.orderTotalPrices[oIdx] = qty * DataGlobal.menuPrices[idx];
                        DataGlobal.orderStatuses[oIdx] = "Menunggu Pembayaran";
                        DataGlobal.orderPaymentMethods[oIdx] = "-";
                        DataGlobal.orderPaymentProofs[oIdx] = "-";
                        DataGlobal.orderRejectionReasons[oIdx] = "-";
                        
                        DataGlobal.orderCount++;
                        System.out.println("Pesanan dibuat ID: " + DataGlobal.orderIds[oIdx]);
                    } else System.out.println("Stok/Jumlah tidak valid.");
                } catch (NumberFormatException e) {
                    System.out.println("Input jumlah harus angka.");
                }
            } else System.out.println("Stok Habis.");
        } else System.out.println("Menu tidak ada.");
    }

    // --- FITUR ADMIN ---
    public static void konfirmasiPesanan() {
        System.out.println("\n--- KONFIRMASI PEMBAYARAN ---");
        boolean ada = false;
        for (int i = 0; i < DataGlobal.orderCount; i++) {
            if (DataGlobal.orderStatuses[i].equals("Menunggu Konfirmasi")) {
                System.out.println("ID: " + DataGlobal.orderIds[i] + 
                    " | User: " + DataGlobal.orderUsernames[i] +
                    " | Menu: " + DataGlobal.orderMenuNames[i] + " (x" + DataGlobal.orderQuantities[i] + ")" +
                    " | Bukti: " + DataGlobal.orderPaymentProofs[i] + 
                    " | Total: Rp" + DataGlobal.orderTotalPrices[i]);
                ada = true;
            }
        }
        if(!ada) { System.out.println("Tidak ada antrian konfirmasi."); return; }

        System.out.print("ID Order: ");
        try {
            int id = Integer.parseInt(DataGlobal.scanner.nextLine());
            int idx = cariIndexOrder(id);

            if (idx != -1 && DataGlobal.orderStatuses[idx].equals("Menunggu Konfirmasi")) {
                System.out.print("1. Terima  2. Tolak : ");
                String act = DataGlobal.scanner.nextLine();
                
                if (act.equals("1")) {
                    DataGlobal.orderStatuses[idx] = "Siap Diambil";
                    System.out.println("Pesanan diterima.");
                } else if (act.equals("2")) {
                    System.out.print("Masukkan Alasan Penolakan: ");
                    String alasan = DataGlobal.scanner.nextLine();
                    DataGlobal.orderRejectionReasons[idx] = alasan;

                    DataGlobal.orderStatuses[idx] = "Ditolak";
                    
                    // Balikin stok
                    int mIdx = KelolaMenu.cariIndexMenu(DataGlobal.orderMenuNames[idx]);
                    if(mIdx != -1) DataGlobal.menuStocks[mIdx] += DataGlobal.orderQuantities[idx];
                    System.out.println("Pesanan ditolak, stok dikembalikan.");
                } else {
                    System.out.println("Pilihan tidak valid! Masukkan angka 1 atau 2.");
                }
            } else {
                System.out.println("ID tidak valid atau status bukan menunggu konfirmasi.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Input ID harus angka.");
        }
    }

    public static void lihatSemuaPesanan() {
        System.out.println("\n--- SEMUA PESANAN ---");
        System.out.printf("%-5s %-10s %-15s %-5s %-10s %-15s\n", "ID", "User", "Menu", "Qty", "Total", "Status");
        
        for(int i=0; i<DataGlobal.orderCount; i++){
            System.out.printf("%-5d %-10s %-15s %-5d %-10d %-15s\n", 
                DataGlobal.orderIds[i], 
                DataGlobal.orderUsernames[i], 
                DataGlobal.orderMenuNames[i], 
                DataGlobal.orderQuantities[i], 
                DataGlobal.orderTotalPrices[i], 
                DataGlobal.orderStatuses[i]);
            
            if(DataGlobal.orderStatuses[i].equals("Ditolak")) {
                System.out.println("      -> Alasan Ditolak: " + DataGlobal.orderRejectionReasons[i]);
            }
        }
        DataGlobal.scanner.nextLine(); 
    }

    public static int cariIndexOrder(int id) {
        for(int i=0; i<DataGlobal.orderCount; i++) {
            if(DataGlobal.orderIds[i] == id) return i;
        }
        return -1;
    }
}