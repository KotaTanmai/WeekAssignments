import java.util.*;

public class Problem1UsernameChecker {

    // username -> userId
    static HashMap<String, Integer> usernameMap = new HashMap<>();

    // username -> attempt count
    static HashMap<String, Integer> attemptFrequency = new HashMap<>();


    // Check if username available
    public static boolean checkAvailability(String username) {

        // track attempts
        attemptFrequency.put(username,
                attemptFrequency.getOrDefault(username, 0) + 1);

        return !usernameMap.containsKey(username);
    }


    // Suggest alternative usernames
    public static List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        suggestions.add(username + "1");
        suggestions.add(username + "2");
        suggestions.add(username.replace("_", "."));

        return suggestions;
    }


    // Get most attempted username
    public static String getMostAttempted() {

        String most = "";
        int max = 0;

        for (String name : attemptFrequency.keySet()) {

            int count = attemptFrequency.get(name);

            if (count > max) {
                max = count;
                most = name;
            }
        }

        return most + " (" + max + " attempts)";
    }


    public static void main(String[] args) {

        // existing users
        usernameMap.put("john_doe", 101);
        usernameMap.put("admin", 1);

        System.out.println("Check john_doe: "
                + checkAvailability("john_doe"));

        System.out.println("Check jane_smith: "
                + checkAvailability("jane_smith"));

        System.out.println("Suggestions for john_doe: "
                + suggestAlternatives("john_doe"));

        // simulate attempts
        checkAvailability("admin");
        checkAvailability("admin");
        checkAvailability("admin");

        System.out.println("Most attempted username: "
                + getMostAttempted());
    }
}