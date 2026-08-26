import java.util.Scanner;

class BookIssue {
    String title, borrowerName;
    int daysOverdue;

    BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    double fineAmount() {
        return daysOverdue > 0 ? daysOverdue * 5 : 0;
    }

    boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    static double totalFineCollected(BookIssue[] issues) {
        double total = 0;
        for (BookIssue b : issues)
            total += b.fineAmount();
        return total;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BookIssue[] books = new BookIssue[5];

        for (int i = 0; i < 5; i++) {
            System.out.print("Enter title: ");
            String title = sc.nextLine();

            System.out.print("Enter borrower name: ");
            String name = sc.nextLine();

            System.out.print("Enter days overdue: ");
            int days = sc.nextInt();
            sc.nextLine();

            books[i] = new BookIssue(title, name, days);
        }

        System.out.println("\nBook Details:");

        for (BookIssue b : books) {
            System.out.println(b.title + " - " + b.daysOverdue + " days - " +
                    (b.isSeverelyOverdue() ? "Severely overdue" : "OK"));
        }

        System.out.println("Total fine collected: Rs " +
                BookIssue.totalFineCollected(books));

        sc.close();
    }
}