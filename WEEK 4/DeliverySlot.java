import java.util.Scanner;

public class DeliverySlot {

    private String orderId;
    private String timeSlot;

    // Main constructor
    public DeliverySlot(String orderId, String timeSlot) {
        this.orderId = orderId;
        this.timeSlot = timeSlot;
    }

    // Constructor chaining
    public DeliverySlot(String orderId) {
        this(orderId, "ASAP");
    }

    // Check peak hour
    public boolean isPeakHour() {

        if (timeSlot.equals("12:00-13:00") ||
            timeSlot.equals("13:00-14:00") ||
            timeSlot.equals("19:00-20:00") ||
            timeSlot.equals("20:00-21:00")) {

            return true;
        }

        return false;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Order ID: ");
        String orderId = sc.nextLine();

        System.out.print("Do you want to enter a time slot? (yes/no): ");
        String choice = sc.nextLine();

        DeliverySlot slot;

        if (choice.equalsIgnoreCase("yes")) {

            System.out.print("Enter Time Slot: ");
            String timeSlot = sc.nextLine();

            slot = new DeliverySlot(orderId, timeSlot);

        } else {

            slot = new DeliverySlot(orderId);
        }

        System.out.println("Peak Hour: " + slot.isPeakHour());

        sc.close();
    }
}

