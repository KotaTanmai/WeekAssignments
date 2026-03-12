import java.util.*;

public class Problem4PlagiarismDetector {

    // n-gram size
    static final int N = 5;

    // ngram -> set of document IDs
    static HashMap<String, Set<String>> ngramIndex = new HashMap<>();


    // Break text into n-grams
    public static List<String> generateNGrams(String text) {

        String[] words = text.toLowerCase().split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }


    // Store document n-grams in hash table
    public static void storeDocument(String docId, String text) {

        List<String> ngrams = generateNGrams(text);

        for (String gram : ngrams) {

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }

        System.out.println(docId + " → Extracted " + ngrams.size() + " n-grams");
    }


    // Analyze document similarity
    public static void analyzeDocument(String docId, String text) {

        List<String> ngrams = generateNGrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {

            if (ngramIndex.containsKey(gram)) {

                for (String existingDoc : ngramIndex.get(gram)) {

                    if (!existingDoc.equals(docId)) {

                        matchCount.put(existingDoc,
                                matchCount.getOrDefault(existingDoc, 0) + 1);
                    }
                }
            }
        }

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);

            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Found " + matches +
                    " matching n-grams with \"" + doc + "\"");

            System.out.printf("Similarity: %.2f%%", similarity);

            if (similarity > 60)
                System.out.println(" (PLAGIARISM DETECTED)");
            else if (similarity > 10)
                System.out.println(" (suspicious)");
            else
                System.out.println();
        }
    }


    public static void main(String[] args) {

        String essay1 = "machine learning is a field of artificial intelligence that focuses on teaching computers to learn from data";

        String essay2 = "machine learning is a field of artificial intelligence that focuses on training computers using data";

        String essay3 = "sports football cricket tennis and badminton are popular games played all over the world";

        // store previous essays
        storeDocument("essay_089.txt", essay1);
        storeDocument("essay_092.txt", essay2);

        System.out.println();

        // analyze new submission
        analyzeDocument("essay_123.txt", essay1 + " using large datasets");
    }
}