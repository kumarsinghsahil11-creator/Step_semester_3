public class DeliveryAccount {

    protected String studentId;
    protected double orderValue;

    // One-time class-level state
    static {
        System.out.println("Delivery Account Processor Initialized");
    }

    // Full constructor
    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    // Provisional constructor - constructor chaining
    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    // Final surge-fee calculation
    public final double calculateSurgeFee(int delayMinutes) {

        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Delay cannot be negative");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        double percent = 0.0;

        // Minutes 1-5
        int first = Math.min(delayMinutes, 5);
        percent += first * 0.5;

        // Minutes 6-15
        if (delayMinutes > 5) {
            int second = Math.min(delayMinutes - 5, 10);
            percent += second * 1.0;
        }

        // Minutes 16 onward
        if (delayMinutes > 15) {
            int third = delayMinutes - 15;
            percent += third * 2.0;
        }

        return orderValue * percent / 100.0;
    }

    // Premium account
    static class Premium extends DeliveryAccount {

        public Premium(String studentId, double orderValue) {
            super(studentId, orderValue);
        }
    }

    // Processor
    static class Processor {

        private double grandTotal = 0;

        void processAccount(
                DeliveryAccount account,
                double amount,
                int delayMinutes) {

            if (account == null) {
                return;
            }

            try {
                double fee = account.calculateSurgeFee(delayMinutes);

                if (account instanceof Premium) {
                    // Premium accounts get 50% of calculated surge fee
                    fee = fee * 0.5;
                    System.out.println(
                        account.studentId +
                        " -> Premium | Surge Fee: Rs " + fee
                    );
                } else {
                    System.out.println(
                        account.studentId +
                        " -> Regular | Surge Fee: Rs " + fee
                    );
                }

                grandTotal += fee;

            } catch (IllegalArgumentException e) {
                System.out.println(
                    account.studentId + " -> Invalid account data"
                );
            }
        }

        static void processBatch(
                DeliveryAccount[] accounts,
                double[] amounts,
                int[] delayMinutesArray) {

            if (accounts == null ||
                amounts == null ||
                delayMinutesArray == null) {

                System.out.println("Invalid batch: null array");
                return;
            }

            // Prevent wrong student/amount/delay mapping
            if (accounts.length != amounts.length ||
                accounts.length != delayMinutesArray.length) {

                System.out.println(
                    "Batch rejected: array lengths do not match."
                );
                return;
            }

            Processor processor = new Processor();

            int processed = 0;
            int nullSkipped = 0;
            int premium = 0;
            int regular = 0;

            for (int i = 0; i < accounts.length; i++) {

                if (accounts[i] == null) {
                    nullSkipped++;
                    continue;
                }

                processor.processAccount(
                    accounts[i],
                    amounts[i],
                    delayMinutesArray[i]
                );

                processed++;

                if (accounts[i] instanceof Premium) {
                    premium++;
                } else {
                    regular++;
                }
            }

            System.out.println("\n----- Batch Summary -----");
            System.out.println(processed +
                    " processed | " +
                    nullSkipped +
                    " null skipped | " +
                    premium +
                    " premium | " +
                    regular +
                    " regular");

            System.out.println(
                "Grand total surge fees = Rs " +
                processor.grandTotal
            );
        }
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {
            new Premium("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {
            500, 400, 300
        };

        int[] delayMinutesArray = {
            10, 5, 0
        };

        Processor.processBatch(
            accounts,
            amounts,
            delayMinutesArray
        );
    }
}