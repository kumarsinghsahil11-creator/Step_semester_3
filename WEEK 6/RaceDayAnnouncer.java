class RaceEntry {
    private String bibNumber;
    private double entryFee;
    private double amountPaid;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public void announce() {
        System.out.print("Race Entry | Bib: " + bibNumber +
                " | Balance: " + getBalanceDue());
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public void announce() {
        System.out.print("Runner Entry | Bib: " + getBibNumber() +
                " | Category: " + category +
                " | Balance: " + getBalanceDue());
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public void announce() {
        System.out.print("Relay Team | Bib: " + getBibNumber() +
                " | Team Size: " + teamSize +
                " | Balance: " + getBalanceDue());
    }
}

public class RaceDayAnnouncer {

    public static String announceAll(RaceEntry[] entries) {

        StringBuilder report = new StringBuilder();

        for (RaceEntry entry : entries) {

            java.io.ByteArrayOutputStream output =
                    new java.io.ByteArrayOutputStream();

            java.io.PrintStream oldOut = System.out;

            System.setOut(new java.io.PrintStream(output));

            entry.announce();

            System.out.flush();
            System.setOut(oldOut);

            report.append(output.toString());

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;

                report.append(" [Team size via downcast: ")
                      .append(relay.getTeamSize())
                      .append("] | ");
            } else {
                report.append(" | ");
            }
        }

        return report.toString();
    }

    public static void main(String[] args) {

        RunnerEntry runnerEntry =
                new RunnerEntry("BIB2001", 80, "Open 10K");

        runnerEntry.pay(-10);

        RelayTeamEntry relayEntry =
                new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = {
                runnerEntry,
                relayEntry
        };

        System.out.println(announceAll(fleet));
    }
}