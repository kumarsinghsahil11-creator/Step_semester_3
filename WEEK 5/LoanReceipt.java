import java.util.Scanner;

class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    // One-time shared state
    static {
        System.out.println("Loan Receipt System Initialized");
    }

    // Constructor
    public LoanReceipt(String memberId, String[] bookIds) {

        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (bookIds == null) {
            throw new IllegalArgumentException("Book ID array cannot be null");
        }

        // Validate every book ID before creating the receipt
        for (int i = 0; i < bookIds.length; i++) {

            if (!isValidBookId(bookIds[i])) {
                throw new IllegalArgumentException(
                        "Invalid book ID: " + bookIds[i]
                );
            }
        }

        this.memberId = memberId.trim();

        // Defensive copy
        this.bookIds = bookIds.clone();
    }

    // Check format BK- followed by exactly 3 digits
    private static boolean isValidBookId(String id) {

        if (id == null || id.length() != 6) {
            return false;
        }

        if (id.charAt(0) != 'B' ||
            id.charAt(1) != 'K' ||
            id.charAt(2) != '-') {
            return false;
        }

        for (int i = 3; i < 6; i++) {

            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public String getMemberId() {
        return memberId;
    }

    // Defensive copy on the way out
    public String[] getBookIds() {
        return bookIds.clone();
    }

    // Wither method - returns a NEW object
    public LoanReceipt withCorrectedBookId(
            int index, String newId) {

        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException(
                    "Invalid book ID index"
            );
        }

        if (!isValidBookId(newId)) {
            throw new IllegalArgumentException(
                    "Invalid book ID: " + newId
            );
        }

        String[] correctedIds = bookIds.clone();
        correctedIds[index] = newId;

        return new LoanReceipt(memberId, correctedIds);
    }

    // Reference-only receipt
    public static final class ReferenceOnlyLoanReceipt
            extends LoanReceipt {

        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(
                String memberId,
                String[] bookIds,
                String roomNumber) {

            super(memberId, bookIds);

            if (roomNumber == null ||
                roomNumber.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Invalid room number"
                );
            }

            this.roomNumber = roomNumber.trim();
        }

        public String getRoomNumber() {
            return roomNumber;
        }
    }

    // Nightly processor
    public static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        if (receipts == null) {
            return "0 processed | 0 null skipped | "
                    + "0 reference-only | 0 regular";
        }

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (int i = 0; i < receipts.length; i++) {

            if (receipts[i] == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipts[i]
                    instanceof ReferenceOnlyLoanReceipt) {

                referenceOnly++;

            } else {

                regular++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            System.out.print("Enter Member ID: ");
            String memberId = sc.nextLine();

            System.out.print("Enter number of books: ");
            int n = Integer.parseInt(sc.nextLine());

            String[] books = new String[n];

            for (int i = 0; i < n; i++) {
                System.out.print(
                        "Enter Book ID " + (i + 1) + ": "
                );
                books[i] = sc.nextLine();
            }

            LoanReceipt receipt =
                    new LoanReceipt(memberId, books);

            System.out.println(
                    "Receipt created successfully."
            );

            System.out.println(
                    "Book IDs: "
                    + String.join(", ",
                    receipt.getBookIds())
            );

            // Test defensive copying
            String[] ids = receipt.getBookIds();

            if (ids.length > 0) {
                ids[0] = "HACKED";
            }

            System.out.println(
                    "After external modification: "
                    + receipt.getBookIds()[0]
            );

            // Test nightly processing
            LoanReceipt[] batch = {
                    new ReferenceOnlyLoanReceipt(
                            "LIB-001",
                            new String[]{"BK-200"},
                            "Reading Room 3"
                    ),
                    null,
                    new LoanReceipt(
                            "LIB-002",
                            new String[]{"BK-201"}
                    )
            };

            System.out.println(
                    processNightlyCirculation(batch)
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Construction rejected: "
                    + e.getMessage()
            );

        } finally {
            sc.close();
        }
    }
}