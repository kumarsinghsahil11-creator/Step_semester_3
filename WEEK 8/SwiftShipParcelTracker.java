import java.util.*;

public class SwiftShipParcelTracker {

    enum Status {
        BOOKED,
        PICKED_UP,
        IN_TRANSIT,
        OUT_FOR_DELIVERY,
        DELIVERED
    }

    interface ShippingType {
        double calculateCharge(double weight);
        String getName();
    }

    static class StandardShipping implements ShippingType {
        public double calculateCharge(double weight) {
            return 40 + (10 * weight);
        }

        public String getName() {
            return "Standard";
        }
    }

    static class ExpressShipping implements ShippingType {
        public double calculateCharge(double weight) {
            return 80 + (15 * weight);
        }

        public String getName() {
            return "Express";
        }
    }

    static class FragileShipping implements ShippingType {
        private ShippingType standard = new StandardShipping();

        public double calculateCharge(double weight) {
            return standard.calculateCharge(weight) + 50;
        }

        public String getName() {
            return "Fragile";
        }
    }

    interface NotificationChannel {
        void notify(String parcelId, Status status);
    }

    static class SmsChannel implements NotificationChannel {
        public void notify(String parcelId, Status status) {
            System.out.println("[SMS] " + parcelId + " is now " + status + ".");
        }
    }

    static class EmailChannel implements NotificationChannel {
        public void notify(String parcelId, Status status) {
            System.out.println("[Email] " + parcelId + " is now " + status + ".");
        }
    }

    static class Customer {
        String name;

        Customer(String name) {
            this.name = name;
        }
    }

    static class Parcel {
        String id;
        Customer customer;
        double weight;
        ShippingType shippingType;
        Status status;
        List<NotificationChannel> channels = new ArrayList<>();

        Parcel(String id, Customer customer, double weight,
               ShippingType shippingType) {
            this.id = id;
            this.customer = customer;
            this.weight = weight;
            this.shippingType = shippingType;
            this.status = Status.BOOKED;
        }

        double calculateCharge() {
            return shippingType.calculateCharge(weight);
        }

        void subscribe(NotificationChannel channel) {
            channels.add(channel);
        }

        void notifyChannels() {
            for (NotificationChannel channel : channels) {
                channel.notify(id, status);
            }
        }

        boolean updateStatus(Status newStatus) {

            if (status == Status.BOOKED && newStatus == Status.PICKED_UP ||
                status == Status.PICKED_UP && newStatus == Status.IN_TRANSIT ||
                status == Status.IN_TRANSIT && newStatus == Status.OUT_FOR_DELIVERY ||
                status == Status.OUT_FOR_DELIVERY && newStatus == Status.DELIVERED) {

                status = newStatus;
                notifyChannels();
                return true;
            }

            System.out.println(
                "Invalid transition: " + status +
                " → " + newStatus + " is not allowed."
            );

            return false;
        }

        boolean cancel() {

            if (status != Status.BOOKED) {
                System.out.println(
                    "Cancellation failed: " + id +
                    " can be cancelled only while BOOKED."
                );
                return false;
            }

            System.out.println("Parcel " + id + " cancelled.");
            return true;
        }
    }

    static class ParcelService {

        Parcel bookParcel(String id, Customer customer,
                          double weight, ShippingType shippingType) {

            Parcel parcel =
                new Parcel(id, customer, weight, shippingType);

            System.out.printf(
                "Parcel %s booked (%s, %.0f kg).%n",
                id,
                shippingType.getName(),
                weight
            );

            System.out.printf(
                "Charge: ₹%.2f%n",
                parcel.calculateCharge()
            );

            return parcel;
        }
    }

    public static void main(String[] args) {

        ParcelService service = new ParcelService();

        Customer customer = new Customer("Arun");

        ShippingType express = new ExpressShipping();

        Parcel parcel = service.bookParcel(
            "P101",
            customer,
            2,
            express
        );

        parcel.subscribe(new SmsChannel());
        parcel.subscribe(new EmailChannel());

        parcel.notifyChannels();

        parcel.updateStatus(Status.PICKED_UP);

        parcel.cancel();

        parcel.updateStatus(Status.IN_TRANSIT);

        parcel.updateStatus(Status.DELIVERED);
    }
}