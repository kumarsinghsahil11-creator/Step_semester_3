import java.util.Scanner;

public class FoodOrder {

    private String studentName;
    private String dishName;
    private boolean delivered;

    // Parameterized constructor
    public FoodOrder(String studentName, String dishName) {

        // Validate student name
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student name");
        }

        // Validate dish name
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid dish name");
        }

        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }

    // Mark order as delivered
    public void markDelivered() {

        if (delivered) {
            System.out.println("Order already delivered for " + studentName);
        } else {
            delivered = true;
            System.out.println("Order delivered for " + studentName);
        }
    }

    // Process all orders
    public static void processBatch(String[][] rawOrders) {

        int valid = 0;
        int rejected = 0;

        for (int i = 0; i < rawOrders.length; i++) {

            try {

                FoodOrder order = new FoodOrder(
                        rawOrders[i][0],
                        rawOrders[i][1]
                );

                valid++;

            } catch (IllegalArgumentException e) {

                rejected++;
            }
        }

        System.out.println("Valid: " + valid
                + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of orders: ");
        int n = Integer.parseInt(sc.nextLine());

        String[][] rawOrders = new String[n][2];

        for (int i = 0; i < n; i++) {

            System.out.println("\nOrder " + (i + 1));

            System.out.print("Enter student name: ");
            rawOrders[i][0] = sc.nextLine();

            System.out.print("Enter dish name: ");
            rawOrders[i][1] = sc.nextLine();
        }

        System.out.println("\nBatch Result:");
        processBatch(rawOrders);

        sc.close();
    }
}