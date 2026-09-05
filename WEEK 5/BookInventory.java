public class BookInventory {

    // Private fields
    private int copiesTotal;
    private int copiesAvailable;

    // Constructor with validation
    public BookInventory(int copiesTotal) {

        if (copiesTotal <= 0) {
            throw new IllegalArgumentException(
                "Construction rejected: copiesTotal must be greater than 0."
            );
        }

        this.copiesTotal = copiesTotal;

        // Initially, all copies are available
        this.copiesAvailable = copiesTotal;
    }

    // Checkout a book
    public void checkOut() {

        // Only checkout if copies are available
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    // Check in a book
    public void checkIn() {

        // Only check in if inventory is not already full
        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    // Getter method
    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public static void main(String[] args) {

        try {

            BookInventory b = new BookInventory(3);

            System.out.println("Starting copies available: "
                    + b.getCopiesAvailable());

            // Checkout 4 times
            b.checkOut();
            b.checkOut();
            b.checkOut();
            b.checkOut(); // Silently rejected

            System.out.println("After 4 checkouts: "
                    + b.getCopiesAvailable());

            // Check in 4 times
            b.checkIn();
            b.checkIn();
            b.checkIn();
            b.checkIn(); // Silently rejected

            System.out.println("After 4 check-ins: "
                    + b.getCopiesAvailable());

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }
}