import java.util.Scanner;

public class DataGlobal {
    // --- KONFIGURASI ---
    public static final int MAX_USERS = 50;
    public static final int MAX_MENU = 50;
    public static final int MAX_ORDERS = 100;
    public static final int MAX_PAYMENT_METHODS = 10;

    // --- DATA STORE ---
    
    // Data User
    public static String[] usernames = new String[MAX_USERS];
    public static String[] passwords = new String[MAX_USERS];
    public static String[] roles = new String[MAX_USERS];
    public static int userCount = 0;

    // Data Menu
    public static String[] menuNames = new String[MAX_MENU];
    public static int[] menuPrices = new int[MAX_MENU];
    public static int[] menuStocks = new int[MAX_MENU];
    public static int menuCount = 0;

    // Data Pesanan
    public static int[] orderIds = new int[MAX_ORDERS];
    public static String[] orderUsernames = new String[MAX_ORDERS];
    public static String[] orderMenuNames = new String[MAX_ORDERS];
    public static int[] orderQuantities = new int[MAX_ORDERS];
    public static int[] orderTotalPrices = new int[MAX_ORDERS];
    public static String[] orderPaymentMethods = new String[MAX_ORDERS];
    public static String[] orderPaymentProofs = new String[MAX_ORDERS]; 
    public static String[] orderStatuses = new String[MAX_ORDERS]; 
    public static String[] orderRejectionReasons = new String[MAX_ORDERS]; 
    public static int orderCount = 0;

    // Data Pembayaran
    public static String[] paymentMethods = new String[MAX_PAYMENT_METHODS];
    public static int paymentMethodCount = 0;
    public static String currentUser = null;
    public static String currentRole = null;

    // Scanner
    public static Scanner scanner = new Scanner(System.in);

    // --- DATA DEFAULT ---
    public static void initData() {
        usernames[0] = "admin"; passwords[0] = "admin123"; roles[0] = "admin";
        userCount++;
        usernames[1] = "user"; passwords[1] = "user123"; roles[1] = "customer";
        userCount++;

        tambahMenu("Nasi Goreng", 15000, 20);
        tambahMenu("Ayam Bakar", 20000, 15);
        tambahMenu("Es Teh Manis", 5000, 50);

        paymentMethods[0] = "Bayar di Kasir"; paymentMethodCount++;
        paymentMethods[1] = "Transfer Bank"; paymentMethodCount++;
    }

    public static void tambahMenu(String nama, int harga, int stok) {
        if (menuCount < MAX_MENU) {
            menuNames[menuCount] = nama;
            menuPrices[menuCount] = harga;
            menuStocks[menuCount] = stok;
            menuCount++;
        }
    }
}