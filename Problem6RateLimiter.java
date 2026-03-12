import java.util.*;

public class Problem6RateLimiter {

    // TokenBucket class
    static class TokenBucket {

        int tokens;
        long lastRefillTime;

        int maxTokens = 1000;
        long refillInterval = 3600000; // 1 hour in milliseconds

        TokenBucket() {
            this.tokens = maxTokens;
            this.lastRefillTime = System.currentTimeMillis();
        }

        // refill tokens every hour
        void refill() {

            long now = System.currentTimeMillis();

            if (now - lastRefillTime >= refillInterval) {
                tokens = maxTokens;
                lastRefillTime = now;
            }
        }
    }

    // clientId -> TokenBucket
    static HashMap<String, TokenBucket> clientBuckets = new HashMap<>();


    // Check rate limit
    public static synchronized void checkRateLimit(String clientId) {

        TokenBucket bucket = clientBuckets.get(clientId);

        if (bucket == null) {
            bucket = new TokenBucket();
            clientBuckets.put(clientId, bucket);
        }

        bucket.refill();

        if (bucket.tokens > 0) {

            bucket.tokens--;

            System.out.println("Allowed (" + bucket.tokens + " requests remaining)");

        } else {

            long retryTime = (bucket.lastRefillTime + bucket.refillInterval - System.currentTimeMillis()) / 1000;

            System.out.println("Denied (0 requests remaining, retry after " + retryTime + "s)");
        }
    }


    // Show client status
    public static void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clientBuckets.get(clientId);

        if (bucket == null) {
            System.out.println("Client not found");
            return;
        }

        int used = bucket.maxTokens - bucket.tokens;

        long resetTime = bucket.lastRefillTime + bucket.refillInterval;

        System.out.println("{used: " + used +
                ", limit: " + bucket.maxTokens +
                ", reset: " + resetTime + "}");
    }


    public static void main(String[] args) {

        String client = "abc123";

        checkRateLimit(client);
        checkRateLimit(client);
        checkRateLimit(client);

        getRateLimitStatus(client);
    }
}