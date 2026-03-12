import java.util.*;

public class Problem5RealtimeAnalytics {

    // pageUrl -> visit count
    static HashMap<String, Integer> pageViews = new HashMap<>();

    // pageUrl -> unique users
    static HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();

    // traffic source -> count
    static HashMap<String, Integer> trafficSources = new HashMap<>();


    // Process incoming page view event
    public static void processEvent(String url, String userId, String source) {

        // update page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // update unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // update traffic source count
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }


    // Display dashboard
    public static void getDashboard() {

        System.out.println("\nTop Pages:");

        // PriorityQueue to get top pages
        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        int rank = 1;

        while (!pq.isEmpty() && rank <= 10) {

            Map.Entry<String, Integer> entry = pq.poll();

            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(rank + ". " + url +
                    " - " + views + " views (" + unique + " unique)");

            rank++;
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int count : trafficSources.values())
            total += count;

        for (String source : trafficSources.keySet()) {

            int count = trafficSources.get(source);

            double percentage = (count * 100.0) / total;

            System.out.printf("%s: %.2f%%\n", source, percentage);
        }
    }


    public static void main(String[] args) {

        // simulate events
        processEvent("/article/breaking-news", "user_123", "Google");
        processEvent("/article/breaking-news", "user_456", "Facebook");
        processEvent("/sports/championship", "user_789", "Direct");
        processEvent("/sports/championship", "user_123", "Google");
        processEvent("/article/breaking-news", "user_999", "Direct");
        processEvent("/tech/ai-future", "user_777", "Google");

        // display dashboard
        getDashboard();
    }
}