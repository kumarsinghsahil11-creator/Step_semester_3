import java.util.Scanner;

class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    void allot(String vehicleNo) {
        occupiedCount++;
        System.out.println(vehicleNo + " allotted to slot " + slotNo);
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot.occupiedCount < slot.capacity)
                return slot;
        }
        return null;
    }

    static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);

        if (slot != null)
            slot.allot(vehicleNo);
        else
            System.out.println("No slots available for " + vehicleNo);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of slots: ");
        int n = sc.nextInt();

        ParkingSlot[] slots = new ParkingSlot[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter slot number: ");
            String slotNo = sc.next();

            System.out.print("Enter capacity: ");
            int capacity = sc.nextInt();

            System.out.print("Enter occupied count: ");
            int occupied = sc.nextInt();

            slots[i] = new ParkingSlot(slotNo, capacity, occupied);
        }

        System.out.print("Enter vehicle number: ");
        String vehicleNo = sc.next();

        safeAllot(slots, vehicleNo);

        sc.close();
    }
}