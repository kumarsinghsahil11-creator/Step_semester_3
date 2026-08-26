import java.util.Scanner;

class LibraryMember {
    String name, memberId;
    int booksIssued;

    static String libraryName = "City Library";
    static int memberCount = 1000;

    LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        memberId = "LM-" + memberCount;
    }

    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    static void printTotalMembers() {
        System.out.println("Total members: " + (memberCount - 1000));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Broken version:");
        String name1 = sc.nextLine();
        String name2 = sc.nextLine();

        System.out.println(name2);
        System.out.println(name2);

        System.out.println("\nFixed version:");

        System.out.print("Enter first member name: ");
        String n1 = sc.nextLine();
        System.out.print("Books issued: ");
        int b1 = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter second member name: ");
        String n2 = sc.nextLine();
        System.out.print("Books issued: ");
        int b2 = sc.nextInt();

        LibraryMember m1 = new LibraryMember(n1, b1);
        LibraryMember m2 = new LibraryMember(n2, b2);

        m1.printMemberCard();
        m2.printMemberCard();
        LibraryMember.printTotalMembers();

        sc.close();
    }
}