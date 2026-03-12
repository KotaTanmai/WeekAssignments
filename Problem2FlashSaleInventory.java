import java.util.*;

public class Problem2FlashSaleInventory {

    // productId -> stock count
    static HashMap<String, Integer> stockMap = new HashMap<>();

    // waiting list (FIFO)
    static LinkedHashMap<Integer, String> waitingList = new LinkedHashMap<>();


    // Check stock availability
    public static void checkStock(String productId) {

        int stock = stockMap.getOrDefault(productId, 0);

        System.out.println(productId + " → " + stock + " units available");
    }


    // Purchase item (thread-safe)
    public synchronized static void purchaseItem(String productId, int userId) {

        int stock = stockMap.getOrDefault(productId, 0);

        if (stock > 0) {

            stockMap.put(productId, stock - 1);

            System.out.println(
                    "purchaseItem(\"" + productId + "\", userId=" + userId + ") → Success, "
                            + (stock - 1) + " units remaining"
            );

        } else {

            waitingList.put(userId, productId);

            System.out.println(
                    "purchaseItem(\"" + productId + "\", userId=" + userId +
                            ") → Added to waiting list, position #" + waitingList.size()
            );
        }
    }


    public static void main(String[] args) {

        // Initial stock
        stockMap.put("IPHONE15_256GB", 5);

        checkStock("IPHONE15_256GB");

        purchaseItem("IPHONE15_256GB", 12345);
        purchaseItem("IPHONE15_256GB", 67890);
        purchaseItem("IPHONE15_256GB", 11111);
        purchaseItem("IPHONE15_256GB", 22222);
        purchaseItem("IPHONE15_256GB", 33333);

        // Stock finished
        purchaseItem("IPHONE15_256GB", 99999);
        purchaseItem("IPHONE15_256GB", 88888);

        checkStock("IPHONE15_256GB");

        System.out.println("\nWaiting List:");
        for (Integer user : waitingList.keySet()) {
            System.out.println("User " + user + " waiting for " + waitingList.get(user));
        }
    }
}