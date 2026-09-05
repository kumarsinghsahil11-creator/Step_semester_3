import java.util.Scanner;

public final class SurgeFeeCalculator {

    // Configured minimum surge floor
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {

        // Validate at calculation time
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException(
                "orderValue and delayMinutes cannot be negative"
            );
        }

        // No delay = no surge fee
        if (delayMinutes == 0) {
            return 0.0;
        }

        double tieredPercent = 0.0;

        // Minutes 1-5 at 0.5%
        int firstTier = Math.min(delayMinutes, 5);
        tieredPercent += firstTier * 0.5;

        // Minutes 6-15 at 1%
        if (delayMinutes > 5) {
            int secondTier = Math.min(delayMinutes - 5, 10);
            tieredPercent += secondTier * 1.0;
        }

        // Minute 16 onward at 2%
        if (delayMinutes > 15) {
            int thirdTier = delayMinutes - 15;
            tieredPercent += thirdTier * 2.0;
        }

        // Calculate tiered fee
        double tieredFee = orderValue * tieredPercent / 100.0;

        // Minimum surge floor
        double minimumFee =
                orderValue * minimumSurgePercent / 100.0;

        // Charge the higher amount
        return Math.max(tieredFee, minimumFee);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter order value: ");
        double orderValue = sc.nextDouble();

        System.out.print("Enter delay minutes: ");
        int delayMinutes = sc.nextInt();

        System.out.print("Enter minimum surge percent: ");
        double minimumSurgePercent = sc.nextDouble();

        SurgeFeeCalculator calculator =
                new SurgeFeeCalculator(minimumSurgePercent);

        try {
            double fee =
                    calculator.calculateSurgeFee(orderValue, delayMinutes);

            System.out.println("Surge Fee: Rs " + fee);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }

        sc.close();
    }
}