public class KelolaPembayaran {

    // --- ADMIN: Tambah Metode ---
    public static void menuAdminMetode() {
        System.out.println("\n--- METODE PEMBAYARAN ---");
        for(int i=0; i<DataGlobal.paymentMethodCount; i++) {
            System.out.println((i+1) + ". " + DataGlobal.paymentMethods[i]);
        }
        System.out.println("Ketik 'tambah' untuk baru, atau Enter kembali.");
        if(DataGlobal.scanner.nextLine().equalsIgnoreCase("tambah")) {
            System.out.print("Nama Metode: ");
            DataGlobal.paymentMethods[DataGlobal.paymentMethodCount++] = DataGlobal.scanner.nextLine();
            System.out.println("Tersimpan.");
        }
    }

    // --- CUSTOMER: Bayar ---
    public static void statusDanBayarCustomer() {
        System.out.println("\n--- TAGIHAN SAYA ---");
        boolean adaTagihan = false;
        
        for (int i = 0; i < DataGlobal.orderCount; i++) {
            if (DataGlobal.orderUsernames[i].equals(DataGlobal.currentUser)) {
                System.out.println("ID: " + DataGlobal.orderIds[i] + 
                    " | " + DataGlobal.orderMenuNames[i] + " (x" + DataGlobal.orderQuantities[i] + ")" +
                    " | Total: Rp" + DataGlobal.orderTotalPrices[i] + 
                    " | Status: " + DataGlobal.orderStatuses[i]);
                
                // Tampilkan alasan jika statusnya Ditolak
                if (DataGlobal.orderStatuses[i].equals("Ditolak")) {
                    System.out.println("   [!] Alasan Penolakan: " + DataGlobal.orderRejectionReasons[i]);
                }
                    
                if(DataGlobal.orderStatuses[i].equals("Menunggu Pembayaran")) adaTagihan = true;
            }
        }

        if (adaTagihan) {
            System.out.print("\nMasukkan ID untuk bayar (0 batal): ");
            try {
                String input = DataGlobal.scanner.nextLine();
                if (input.isEmpty()) return;
                
                int id = Integer.parseInt(input);
                if(id==0) return;
                
                int idx = KelolaOrder.cariIndexOrder(id);
                if(idx != -1 && DataGlobal.orderUsernames[idx].equals(DataGlobal.currentUser)) {
                    pilihMetode(idx);
                } else {
                    System.out.println("ID Salah atau bukan pesanan Anda.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka.");
            }
        } else {
            System.out.println("\n(Tidak ada tagihan aktif)");
        }
    }

    private static void pilihMetode(int orderIdx) {
        System.out.println("\nPilih Metode Pembayaran:");
        for(int i=0; i<DataGlobal.paymentMethodCount; i++)
            System.out.println((i+1) + ". " + DataGlobal.paymentMethods[i]);
        
        System.out.print("Pilih nomor metode: ");
        try {
            int m = Integer.parseInt(DataGlobal.scanner.nextLine()) - 1;
            
            if(m >= 0 && m < DataGlobal.paymentMethodCount) {
                DataGlobal.orderPaymentMethods[orderIdx] = DataGlobal.paymentMethods[m];
                
                if(DataGlobal.paymentMethods[m].contains("Kasir")) {
                    DataGlobal.orderPaymentProofs[orderIdx] = "TUNAI";
                    System.out.println("Silakan bayar di kasir dengan menyebutkan ID Pesanan.");
                } else {
                    System.out.print("Masukkan No Referensi/Bukti Transfer: ");
                    DataGlobal.orderPaymentProofs[orderIdx] = DataGlobal.scanner.nextLine();
                }
                
                DataGlobal.orderStatuses[orderIdx] = "Menunggu Konfirmasi";
                System.out.println("Pembayaran berhasil dikirim! Menunggu konfirmasi Admin.");
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid.");
        }
    }
}