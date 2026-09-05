import java.util.Scanner;

public class WarehouseInventoryBalancer {

    static void analyzeInventory(int[] sectionA, int[] sectionB) {

        int totalA = 0;
        int totalB = 0;

        int highestQuantity = sectionA[0];
        String highestSection = "Section A";
        int highestIndex = 0;

        // Calculate totals and check Section A
        for (int i = 0; i < sectionA.length; i++) {

            totalA = totalA + sectionA[i];

            if (sectionA[i] > highestQuantity) {
                highestQuantity = sectionA[i];
                highestSection = "Section A";
                highestIndex = i;
            }
        }

        // Calculate totals and check Section B
        for (int i = 0; i < sectionB.length; i++) {

            totalB = totalB + sectionB[i];

            if (sectionB[i] > highestQuantity) {
                highestQuantity = sectionB[i];
                highestSection = "Section B";
                highestIndex = i;
            }
        }

        // Print totals and balance status
        System.out.println("\nSection A Total: " + totalA);
        System.out.println("Section B Total: " + totalB);

        if (totalA == totalB) {
            System.out.println("Status: Balanced");
        } else {
            System.out.println("Status: Not Balanced");
        }

        // Index + 1 because users count items from 1
        System.out.println(
            "Highest Quantity: " + highestQuantity +
            " (" + highestSection +
            ", Item " + (highestIndex + 1) + ")"
        );
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        int[] sectionA = new int[n];
        int[] sectionB = new int[n];

        System.out.println("\nEnter quantities for Section A:");

        for (int i = 0; i < n; i++) {
            System.out.print("Item " + (i + 1) + ": ");
            sectionA[i] = sc.nextInt();
        }

        System.out.println("\nEnter quantities for Section B:");

        for (int i = 0; i < n; i++) {
            System.out.print("Item " + (i + 1) + ": ");
            sectionB[i] = sc.nextInt();
        }

        analyzeInventory(sectionA, sectionB);

        sc.close();
    }
}