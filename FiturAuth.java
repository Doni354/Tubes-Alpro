public class FiturAuth {
    
    public static void login() {
        System.out.print("Username: ");
        String u = DataGlobal.scanner.nextLine();
        System.out.print("Password: ");
        String p = DataGlobal.scanner.nextLine();

        for (int i = 0; i < DataGlobal.userCount; i++) {
            if (DataGlobal.usernames[i].equals(u) && DataGlobal.passwords[i].equals(p)) {
                DataGlobal.currentUser = u;
                DataGlobal.currentRole = DataGlobal.roles[i];
                System.out.println("Login berhasil sebagai " + DataGlobal.currentRole + "!");
                return;
            }
        }
        System.out.println("Username atau password salah.");
    }

    public static void register() {
        if (DataGlobal.userCount >= DataGlobal.MAX_USERS) {
            System.out.println("Database user penuh! Tidak bisa registrasi.");
            return;
        }

        String u = "";
        String p = "";

        // --- VALIDASI USERNAME ---
        while (true) {
            System.out.print("Masukkan Username baru (min 3 huruf, tanpa spasi, '0' batal): ");
            u = DataGlobal.scanner.nextLine().trim(); // trim() menghapus spasi di awal/akhir

            // Opsi Batal
            if (u.equals("0")) {
                System.out.println("Registrasi dibatalkan.");
                return;
            }

            // Cek 1: Panjang Minimal
            if (u.length() < 3) {
                System.out.println("Username terlalu pendek! Minimal 3 karakter.");
                continue;
            }

            // Cek 2: Tidak Boleh Ada Spasi
            if (u.contains(" ")) {
                System.out.println("Username tidak boleh mengandung spasi.");
                continue;
            }

            // Cek 3: Unik (Belum pernah dipakai)
            boolean exists = false;
            for(int i=0; i<DataGlobal.userCount; i++){
                if(DataGlobal.usernames[i].equalsIgnoreCase(u)){
                    exists = true;
                    break;
                }
            }

            if(exists) {
                System.out.println("Username sudah terpakai! Silakan pilih yang lain.");
            } else {
                break; 
            }
        }

        // --- VALIDASI PASSWORD ---
        while (true) {
            System.out.print("Masukkan Password baru (min 5 karakter): ");
            p = DataGlobal.scanner.nextLine();

            if (p.length() < 5) {
                System.out.println("Password terlalu pendek! Minimal 5 karakter.");
            } else {
                break; 
            }
        }

        // Simpan Data
        DataGlobal.usernames[DataGlobal.userCount] = u;
        DataGlobal.passwords[DataGlobal.userCount] = p;
        DataGlobal.roles[DataGlobal.userCount] = "customer";
        DataGlobal.userCount++;
        
        System.out.println("Registrasi SUKSES! Silakan login dengan akun baru Anda.");
    }

    public static void logout() {
        DataGlobal.currentUser = null;
        DataGlobal.currentRole = null;
        System.out.println("Berhasil Logout.");
    }
}