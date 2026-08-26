import java.util.Scanner;

class Employee {
    private int empId;
    private String empName;
    private double salary;

    Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    InternEmployee(int empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

public class Emp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Plain Employee details:");
        System.out.print("ID: ");
        int id1 = sc.nextInt();
        System.out.print("Name: ");
        String name1 = sc.next();
        System.out.print("Salary: ");
        double salary1 = sc.nextDouble();

        System.out.println("\nEnter Manager details:");
        System.out.print("ID: ");
        int id2 = sc.nextInt();
        System.out.print("Name: ");
        String name2 = sc.next();
        System.out.print("Salary: ");
        double salary2 = sc.nextDouble();
        System.out.print("Team Bonus: ");
        double bonus = sc.nextDouble();

        System.out.println("\nEnter Intern details:");
        System.out.print("ID: ");
        int id3 = sc.nextInt();
        System.out.print("Name: ");
        String name3 = sc.next();
        System.out.print("Salary: ");
        double salary3 = sc.nextDouble();
        System.out.print("Stipend Cap: ");
        double cap = sc.nextDouble();

        Employee e1 = new Employee(id1, name1, salary1);
        Employee e2 = new ManagerEmployee(id2, name2, salary2, bonus);
        Employee e3 = new InternEmployee(id3, name3, salary3, cap);

        System.out.println("\n--- Employee Pay ---");

        if (e1 instanceof ManagerEmployee) {
            System.out.println("Manager effective pay: Rs " +
                    ((ManagerEmployee) e1).effectiveSalary());
        } else if (e1 instanceof InternEmployee) {
            System.out.println("Intern effective pay: Rs " +
                    ((InternEmployee) e1).effectiveSalary());
        } else {
            System.out.println("Plain employee pay: Rs " + e1.getSalary());
        }

        if (e2 instanceof ManagerEmployee) {
            System.out.println("Manager effective pay: Rs " +
                    ((ManagerEmployee) e2).effectiveSalary());
        } else if (e2 instanceof InternEmployee) {
            System.out.println("Intern effective pay: Rs " +
                    ((InternEmployee) e2).effectiveSalary());
        } else {
            System.out.println("Plain employee pay: Rs " + e2.getSalary());
        }

        if (e3 instanceof ManagerEmployee) {
            System.out.println("Manager effective pay: Rs " +
                    ((ManagerEmployee) e3).effectiveSalary());
        } else if (e3 instanceof InternEmployee) {
            System.out.println("Intern effective pay: Rs " +
                    ((InternEmployee) e3).effectiveSalary());
        } else {
            System.out.println("Plain employee pay: Rs " + e3.getSalary());
        }

        sc.close();
    }
}