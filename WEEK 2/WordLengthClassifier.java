import java.util.Scanner;

public class WordLengthClassifier {

    static void classifyWordLengths(String review) {

        // Split the review into words
        String[] words = review.split("\\s+");

        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        // Check each word
        for (int i = 0; i < words.length; i++) {

            int length = words[i].length();

            if (length >= 1 && length <= 4) {
                shortCount++;
            }
            else if (length >= 5 && length <= 8) {
                mediumCount++;
            }
            else {
                longCount++;
            }
        }

        System.out.println("\nShort: " + shortCount
                + " | Medium: " + mediumCount
                + " | Long: " + longCount);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the movie review: ");
        String review = sc.nextLine();

        classifyWordLengths(review);

        sc.close();
    }
}