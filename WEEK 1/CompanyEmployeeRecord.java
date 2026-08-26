import java.util.Scanner;

class Employee {
    String name, empId;
    double pay;

    Employee(String name, String empId, double pay) {
        this.name = name;
        this.empId = empId;
        this.pay = pay;
    }

    double getPay() {
        return pay;
    }
}

class ManagerEmployee extends Employee {
    double bonus;

    ManagerEmployee(String name, String empId, double pay, double bonus) {
        super(name, empId, pay);
        this.bonus = bonus;
    }

    double getPay() {
        return pay + bonus;
    }
}

class ParkingSlot {
    String slotNo;
    int capacity, occupiedCount;

    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    void allot(String vehicleNo) {
        if (occupiedCount < capacity)
            occupiedCount++;
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot s : slots)
            if (s != null && s.occupiedCount < s.capacity)
                return s;
        return null;
    }

    static ParkingSlot safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot s = findAvailableSlot(slots);

        if (s != null) {
            s.allot(vehicleNo);
            return s;
        }

        return null;
    }
}

public class CompanyEmployeeRecord {
    String name, empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    String fullProfile() {
        return name + " | Pay: Rs " + employee.getPay() +
                " | Slot: " +
                (slot != null ? slot.slotNo : "no parking assigned");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ParkingSlot[] slots = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };

        CompanyEmployeeRecord[] records = new CompanyEmployeeRecord[3];

        for (int i = 0; i < 3; i++) {
            System.out.print("Enter employee name: ");
            String name = sc.nextLine();

            System.out.print("Enter employee ID: ");
            String id = sc.nextLine();

            System.out.print("Enter pay: ");
            double pay = sc.nextDouble();

            System.out.print("Is manager? (yes/no): ");
            String type = sc.next();

            double bonus = 0;

            if (type.equalsIgnoreCase("yes")) {
                System.out.print("Enter bonus: ");
                bonus = sc.nextDouble();
            }

            sc.nextLine();

            Employee e;

            if (type.equalsIgnoreCase("yes"))
                e = new ManagerEmployee(name, id, pay, bonus);
            else
                e = new Employee(name, id, pay);

            ParkingSlot slot = null;

            if (i < 2)
                slot = ParkingSlot.safeAllot(slots, id);

            records[i] = new CompanyEmployeeRecord(name, id, e, slot);
        }

        System.out.println("\nEmployee Records:");

        for (CompanyEmployeeRecord r : records)
            System.out.println(r.fullProfile());

        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);

        sc.close();
    }
}