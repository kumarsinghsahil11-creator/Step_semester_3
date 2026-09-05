import java.util.Scanner;

public class TrafficSignalAnalyzer {

    static void findLongestStreak(String signalLog) {

        int currentStreak = 1;
        int longestStreak = 1;

        char longestColor = signalLog.charAt(0);

        for (int i = 1; i < signalLog.length(); i++) {

            // Check if current character is same as previous character
            if (signalLog.charAt(i) == signalLog.charAt(i - 1)) {

                currentStreak++;

            } else {

                currentStreak = 1;
            }

            // Check if current streak is the longest
            if (currentStreak > longestStreak) {

                longestStreak = currentStreak;
                longestColor = signalLog.charAt(i);
            }
        }

        System.out.println(
            "Longest Streak: '" + longestColor +
            "' repeated " + longestStreak + " times"
        );
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the traffic signal log: ");
        String signalLog = sc.nextLine();

        findLongestStreak(signalLog);

        sc.close();
    }
}