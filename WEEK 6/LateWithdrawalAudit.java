class RaceEntry {
    private String bibNumber;
    private double entryFee;
    private double amountPaid;

    private double[] lateFeeHistory;
    private int lateFeeCount;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0;
        this.lateFeeHistory = new double[10];
        this.lateFeeCount = 0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    protected void applyLateFee(double amount) {
        entryFee += amount;

        if (lateFeeCount < 10) {
            lateFeeHistory[lateFeeCount] = amount;
            lateFeeCount++;
        }
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public double[] getLateFeeHistory() {
        double[] history = new double[lateFeeCount];

        for (int i = 0; i < lateFeeCount; i++) {
            history[i] = lateFeeHistory[i];
        }

        return history;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class LateWithdrawalAudit {

    public static void main(String[] args) {

        RunnerEntry r =
                new RunnerEntry("BIB2001", 80, "Open 10K");

        r.pay(30);

        r.applyLateFee(20);

        System.out.println(r.getBalanceDue());

        double[] history = r.getLateFeeHistory();

        System.out.print("[");

        for (int i = 0; i < history.length; i++) {
            System.out.print(history[i]);

            if (i < history.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");

        history[0] = 999;

        double[] newHistory = r.getLateFeeHistory();

        System.out.print("[");

        for (int i = 0; i < newHistory.length; i++) {
            System.out.print(newHistory[i]);

            if (i < newHistory.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }
}