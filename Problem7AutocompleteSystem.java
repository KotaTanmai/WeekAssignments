import java.util.*;

public class Problem7AutocompleteSystem {

    // Store query -> frequency
    private HashMap<String, Integer> queryFrequency = new HashMap<>();

    // Add or update query frequency
    public void updateFrequency(String query) {
        queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);
    }

    // Get top 10 suggestions for prefix
    public List<String> search(String prefix) {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                pq.add(entry);
            }
        }

        List<String> results = new ArrayList<>();
        int count = 0;

        while (!pq.isEmpty() && count < 10) {
            Map.Entry<String, Integer> entry = pq.poll();
            results.add(entry.getKey() + " (" + entry.getValue() + " searches)");
            count++;
        }

        return results;
    }

    public static void main(String[] args) {

        Problem7AutocompleteSystem system = new Problem7AutocompleteSystem();

        // Sample data
        system.updateFrequency("java tutorial");
        system.updateFrequency("javascript");
        system.updateFrequency("java download");
        system.updateFrequency("java tutorial");
        system.updateFrequency("java tutorial");
        system.updateFrequency("java 21 features");

        // Search suggestions
        List<String> suggestions = system.search("jav");

        System.out.println("Suggestions for 'jav':");

        for (String s : suggestions) {
            System.out.println(s);
        }

        // Update frequency
        system.updateFrequency("java 21 features");

        System.out.println("\nUpdated frequency for 'java 21 features'");
    }
}