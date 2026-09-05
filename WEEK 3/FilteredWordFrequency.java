import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FilteredWordFrequency {
static void printFilteredWordFrequency(String feedback) {

    // Convert to lowercase and remove punctuation
    String cleaned = feedback.toLowerCase()
            .replace(".", "")
            .replace(",", "");

    // Split into words
    String[] words = cleaned.split("\\s+");

    // Stop words
    HashSet<String> stopWords = new HashSet<>();

    stopWords.add("the");
    stopWords.add("was");
    stopWords.add("and");
    stopWords.add("a");
    stopWords.add("is");
    stopWords.add("of");
    stopWords.add("in");

    // Store word frequencies
    HashMap<String, Integer> frequency = new HashMap<>();

    for (int i = 0; i < words.length; i++) {

        // Skip stop words
        if (!stopWords.contains(words[i])) {

            if (frequency.containsKey(words[i])) {
                frequency.put(
                    words[i],
                    frequency.get(words[i]) + 1
                );
            } else {
                frequency.put(words[i], 1);
            }
        }
    }

    // Convert HashMap entries into a list
    ArrayList<String> wordList =
            new ArrayList<>(frequency.keySet());

    // Sort by frequency in descending order
    Collections.sort(wordList, new Comparator<String>() {

        public int compare(String word1, String word2) {

            return frequency.get(word2)
                    - frequency.get(word1);
        }
    });

    // Print result
    for (String word : wordList) {
        System.out.println(
                word + ": " + frequency.get(word)
        );
    }
}

public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    System.out.println("Enter feedback:");
    String feedback = sc.nextLine();

    printFilteredWordFrequency(feedback);

    sc.close();
}

}
