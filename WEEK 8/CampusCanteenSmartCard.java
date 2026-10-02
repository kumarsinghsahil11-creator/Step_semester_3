import java.util.*;

public class CampusCanteenSmartCard {

    interface PricingPlan {
        double getPrice(double originalPrice);
        String getName();
    }

    static class DayScholarPlan implements PricingPlan {
        public double getPrice(double originalPrice) {
            return originalPrice;
        }

        public String getName() {
            return "Day Scholar";
        }
    }

    static class HostellerPlan implements PricingPlan {
        public double getPrice(double originalPrice) {
            return originalPrice * 0.90;
        }

        public String getName() {
            return "Hosteller";
        }
    }

    static class StaffPlan implements PricingPlan {
        public double getPrice(double originalPrice) {
            return originalPrice * 0.80;
        }

        public String getName() {
            return "Staff";
        }
    }

    static class Transaction {
        String description;
        double amount;

        Transaction(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }
    }

    static class Purchase {
        String itemName;
        double chargedAmount;
        boolean refunded;

        Purchase(String itemName, double chargedAmount) {
            this.itemName = itemName;
            this.chargedAmount = chargedAmount;
            this.refunded = false;
        }
    }

    static class SmartCard {
        private String cardNumber;
        private PricingPlan plan;
        private double balance;
        private boolean blocked;

        private List<Transaction> transactions;
        private List<Purchase> purchases;

        SmartCard(String cardNumber, PricingPlan plan) {
            this.cardNumber = cardNumber;
            this.plan = plan;
            this.balance = 0;
            this.blocked = false;
            this.transactions = new ArrayList<>();
            this.purchases = new ArrayList<>();
        }

        void topUp(double amount) {
            if (blocked) {
                System.out.println("Top-up rejected: Card is blocked.");
                return;
            }

            if (amount < 100) {
                System.out.println("Top-up failed: Minimum top-up is ₹100.00.");
                return;
            }

            if (balance + amount > 5000) {
                System.out.println("Top-up failed: Maximum balance is ₹5000.00.");
                return;
            }

            balance += amount;
            transactions.add(new Transaction("Top-up", amount));

            System.out.printf(
                "Canteen card '%s' topped up with ₹%.2f. Balance: ₹%.2f.%n",
                cardNumber, amount, balance
            );
        }

        void purchase(String itemName, double originalPrice) {
            if (blocked) {
                System.out.println("Purchase failed: Card is blocked.");
                return;
            }

            double chargedPrice = plan.getPrice(originalPrice);
            chargedPrice = Math.round(chargedPrice * 100.0) / 100.0;

            if (balance < chargedPrice) {
                System.out.printf(
                    "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    chargedPrice, balance
                );
                return;
            }

            balance -= chargedPrice;

            transactions.add(
                new Transaction(itemName, -chargedPrice)
            );

            purchases.add(
                new Purchase(itemName, chargedPrice)
            );

            System.out.printf(
                "%s purchased for ₹%.2f. Balance: ₹%.2f.%n",
                itemName, chargedPrice, balance
            );
        }

        void refund(String itemName) {
            for (Purchase purchase : purchases) {
                if (purchase.itemName.equals(itemName)) {

                    if (purchase.refunded) {
                        System.out.println(
                            "Refund rejected: " + itemName +
                            " has already been refunded."
                        );
                        return;
                    }

                    purchase.refunded = true;
                    balance += purchase.chargedAmount;

                    transactions.add(
                        new Transaction("Refund " + itemName,
                                        purchase.chargedAmount)
                    );

                    System.out.printf(
                        "Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
                        purchase.chargedAmount,
                        itemName,
                        balance
                    );

                    return;
                }
            }

            System.out.println(
                "Refund rejected: Purchase not found for " + itemName + "."
            );
        }

        void block() {
            blocked = true;
            System.out.println("Card " + cardNumber + " is blocked.");
        }

        void unblock() {
            blocked = false;
            System.out.println("Card " + cardNumber + " is unblocked.");
        }

        void miniStatement() {
            System.out.print("Mini-statement for " + cardNumber + ": ");

            for (int i = 0; i < transactions.size(); i++) {
                double amount = transactions.get(i).amount;

                if (amount >= 0) {
                    System.out.printf("+%.2f", amount);
                } else {
                    System.out.printf("%.2f", amount);
                }

                if (i < transactions.size() - 1) {
                    System.out.print(", ");
                }
            }

            System.out.printf(" = ₹%.2f.%n", balance);
        }
    }

    public static void main(String[] args) {

        SmartCard card =
            new SmartCard("C-2045", new HostellerPlan());

        card.topUp(500);

        card.purchase("Veg Thali", 120);

        card.purchase("Cold Coffee", 60);

        card.purchase("Food Items", 400);

        card.refund("Veg Thali");

        card.refund("Veg Thali");

        card.miniStatement();
    }
}