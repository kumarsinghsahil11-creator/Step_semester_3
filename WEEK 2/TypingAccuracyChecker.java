import java.util.Scanner;

public class TypingAccuracyChecker {

    static void checkTypingAccuracy(String original, String typed) {

        int matchedCharacters = 0;
        int firstMismatch = -1;

        for (int i = 0; i < original.length(); i++) {

            if (original.charAt(i) == typed.charAt(i)) {
                matchedCharacters++;
            } else {

                if (firstMismatch == -1) {
                    firstMismatch = i;
                }
            }
        }

        double accuracy = ((double) matchedCharacters / original.length()) * 100;

        System.out.println("\nMatched: " + matchedCharacters + "/" + original.length());

        System.out.printf("Accuracy: %.2f%%\n", accuracy);

        if (firstMismatch == -1) {
            System.out.println("No Mismatches");
        } else {
            System.out.println(
                "First Mismatch at position " + (firstMismatch + 1) +
                " ('" + original.charAt(firstMismatch) +
                "' vs '" + typed.charAt(firstMismatch) + "')"
            );
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the original passage: ");
        String original = sc.nextLine();

        System.out.print("Enter your typed passage: ");
        String typed = sc.nextLine();

        if (original.length() != typed.length()) {
            System.out.println("Error: Both strings must have the same length.");
        } else {
            checkTypingAccuracy(original, typed);
        }

        sc.close();
    }
}