import java.util.Scanner;

public class Canteen {

    private String canteenCode;
    private String canteenName;
    private int trustScore;

    // Main constructor
    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    // Constructor chaining with default trust score
    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    // Compare two canteens
    public int compareTo(Canteen other) {

        // Higher trust score should come first
        if (this.trustScore != other.trustScore) {
            return other.trustScore - this.trustScore;
        }

        // Compare codes ignoring letter case
        int codeComparison =
                this.canteenCode.compareToIgnoreCase(other.canteenCode);

        if (codeComparison != 0) {
            return codeComparison;
        }

        // Final tie-breaker: shorter name first
        return this.canteenName.length()
                - other.canteenName.length();
    }

    // Manual Bubble Sort
    public static Canteen[] rankCanteens(Canteen[] canteens) {

        for (int i = 0; i < canteens.length - 1; i++) {

            for (int j = 0; j < canteens.length - 1 - i; j++) {

                if (canteens[j].compareTo(canteens[j + 1]) > 0) {

                    Canteen temp = canteens[j];
                    canteens[j] = canteens[j + 1];
                    canteens[j + 1] = temp;
                }
            }
        }

        return canteens;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of canteens: ");
        int n = Integer.parseInt(sc.nextLine());

        Canteen[] canteens = new Canteen[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nCanteen " + (i + 1));

            System.out.print("Enter Canteen Code: ");
            String code = sc.nextLine();

            System.out.print("Enter Canteen Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Trust Score (-1 for default): ");
            int score = Integer.parseInt(sc.nextLine());

            if (score == -1) {
                canteens[i] = new Canteen(code, name);
            } else {
                canteens[i] = new Canteen(code, name, score);
            }
        }

        Canteen[] ranked = rankCanteens(canteens);

        System.out.println("\nRanked Canteens:");

        for (int i = 0; i < ranked.length; i++) {
            System.out.println((i + 1) + ". "
                    + ranked[i].canteenCode);
        }

        sc.close();
    }
}