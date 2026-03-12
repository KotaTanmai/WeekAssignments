import java.util.*;

public class Problem9FinancialTwoSum {

    static class Transaction {
        int id;
        int amount;
        String merchant;
        String account;
        long time;

        Transaction(int id, int amount, String merchant, String account, long time) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.account = account;
            this.time = time;
        }
    }

    // Classic Two Sum
    public static void findTwoSum(List<Transaction> transactions, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction other = map.get(complement);
                System.out.println("Two-Sum Pair Found: (" + other.id + ", " + t.id + ")");
                return;
            }

            map.put(t.amount, t);
        }

        System.out.println("No Two-Sum pair found.");
    }

    // Two Sum within 1 hour window
    public static void findTwoSumWithTime(List<Transaction> transactions, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction other = map.get(complement);

                if (Math.abs(t.time - other.time) <= 3600) {
                    System.out.println("Two-Sum within 1 hour: (" + other.id + ", " + t.id + ")");
                    return;
                }
            }

            map.put(t.amount, t);
        }

        System.out.println("No valid time-window pair.");
    }

    // Duplicate detection
    public static void detectDuplicates(List<Transaction> transactions) {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {

            List<Transaction> list = map.get(key);

            if (list.size() > 1) {
                System.out.println("Duplicate transactions detected:");
                for (Transaction t : list) {
                    System.out.println("ID: " + t.id + " Account: " + t.account);
                }
            }
        }
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, 500, "StoreA", "acc1", 1000));
        transactions.add(new Transaction(2, 300, "StoreB", "acc2", 1100));
        transactions.add(new Transaction(3, 200, "StoreC", "acc3", 1200));
        transactions.add(new Transaction(4, 500, "StoreA", "acc4", 1300));

        System.out.println("Classic Two Sum:");
        findTwoSum(transactions, 500);

        System.out.println("\nTwo Sum within Time Window:");
        findTwoSumWithTime(transactions, 500);

        System.out.println("\nDuplicate Detection:");
        detectDuplicates(transactions);
    }
}