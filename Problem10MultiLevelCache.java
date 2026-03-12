import java.util.*;

public class Problem10MultiLevelCache {

    // L1 Cache (Memory) - LRU using LinkedHashMap
    static class L1Cache extends LinkedHashMap<String, String> {
        private int capacity;

        public L1Cache(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > capacity;
        }
    }

    // L2 Cache (SSD simulated with HashMap)
    private HashMap<String, String> l2Cache = new HashMap<>();

    // L3 Database (simulated)
    private HashMap<String, String> database = new HashMap<>();

    private L1Cache l1Cache;

    private int l1Hits = 0;
    private int l2Hits = 0;
    private int l3Hits = 0;

    public Problem10MultiLevelCache() {

        l1Cache = new L1Cache(10000);

        // Sample database data
        database.put("video_123", "Video Data A");
        database.put("video_456", "Video Data B");
        database.put("video_999", "Video Data C");
    }

    public String getVideo(String videoId) {

        // L1 Cache Check
        if (l1Cache.containsKey(videoId)) {
            l1Hits++;
            System.out.println("L1 Cache HIT");
            return l1Cache.get(videoId);
        }

        System.out.println("L1 Cache MISS");

        // L2 Cache Check
        if (l2Cache.containsKey(videoId)) {
            l2Hits++;
            System.out.println("L2 Cache HIT → Promoted to L1");

            String data = l2Cache.get(videoId);
            l1Cache.put(videoId, data);
            return data;
        }

        System.out.println("L2 Cache MISS");

        // L3 Database
        if (database.containsKey(videoId)) {
            l3Hits++;
            System.out.println("L3 Database HIT → Added to L2");

            String data = database.get(videoId);
            l2Cache.put(videoId, data);
            return data;
        }

        System.out.println("Video not found");
        return null;
    }

    public void getStatistics() {

        int total = l1Hits + l2Hits + l3Hits;

        if (total == 0) {
            System.out.println("No requests yet.");
            return;
        }

        System.out.println("\nCache Statistics:");

        System.out.println("L1 Hits: " + l1Hits);
        System.out.println("L2 Hits: " + l2Hits);
        System.out.println("L3 Hits: " + l3Hits);

        double l1Rate = (l1Hits * 100.0) / total;
        double l2Rate = (l2Hits * 100.0) / total;
        double l3Rate = (l3Hits * 100.0) / total;

        System.out.println("L1 Hit Rate: " + String.format("%.2f", l1Rate) + "%");
        System.out.println("L2 Hit Rate: " + String.format("%.2f", l2Rate) + "%");
        System.out.println("L3 Hit Rate: " + String.format("%.2f", l3Rate) + "%");
    }

    public static void main(String[] args) {

        Problem10MultiLevelCache cache = new Problem10MultiLevelCache();

        System.out.println("Request 1:");
        cache.getVideo("video_123");

        System.out.println("\nRequest 2:");
        cache.getVideo("video_123");

        System.out.println("\nRequest 3:");
        cache.getVideo("video_999");

        cache.getStatistics();
    }
}