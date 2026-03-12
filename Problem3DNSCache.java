import java.util.*;

public class Problem3DNSCache {

    // DNS Entry class
    static class DNSEntry {

        String domain;
        String ipAddress;
        long expiryTime;

        DNSEntry(String domain, String ipAddress, long ttlSeconds) {
            this.domain = domain;
            this.ipAddress = ipAddress;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    // LRU Cache using LinkedHashMap
    static final int MAX_CACHE_SIZE = 5;

    static LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<String, DNSEntry>(MAX_CACHE_SIZE, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > MAX_CACHE_SIZE;
                }
            };

    // Cache statistics
    static int hits = 0;
    static int misses = 0;

    // Simulate upstream DNS lookup
    public static String queryUpstreamDNS(String domain) {

        // Fake IP generation
        String ip = "172.217.14." + new Random().nextInt(255);

        System.out.println("Querying upstream DNS...");

        return ip;
    }

    // Resolve domain
    public static String resolve(String domain) {

        long startTime = System.nanoTime();

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;

                long time = System.nanoTime() - startTime;

                return "Cache HIT → " + entry.ipAddress +
                        " (retrieved in " + (time / 1000000.0) + " ms)";
            }

            else {
                System.out.println("Cache EXPIRED");
                cache.remove(domain);
            }
        }

        misses++;

        String ip = queryUpstreamDNS(domain);

        cache.put(domain, new DNSEntry(domain, ip, 300));

        return "Cache MISS → " + ip + " (TTL: 300s)";
    }

    // Display cache statistics
    public static void getCacheStats() {

        int total = hits + misses;

        double hitRate = total == 0 ? 0 : ((double) hits / total) * 100;

        System.out.println("\nCache Statistics:");
        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) {

        System.out.println(resolve("google.com"));

        System.out.println(resolve("google.com"));

        System.out.println(resolve("facebook.com"));

        System.out.println(resolve("google.com"));

        getCacheStats();
    }
}