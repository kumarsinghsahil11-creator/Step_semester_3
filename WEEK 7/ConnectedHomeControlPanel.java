interface RemoteControllable {
    String connect(String appId);
}

interface EnergyTrackable {
    double getConsumptionWatts();
}

abstract class HomeDevice {
    private static int counter = 1001;
    private final String serialNumber;

    public HomeDevice() {
        serialNumber = "HD-" + counter++;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public abstract String activate();
}

class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
    private double consumptionWatts;

    public WashingMachine(double consumptionWatts) {
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Washing machine " + getSerialNumber() + " started a cycle";
    }

    @Override
    public String connect(String appId) {
        return getSerialNumber() + " connected to " + appId;
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}

class Refrigerator extends HomeDevice implements EnergyTrackable {
    private double consumptionWatts;

    public Refrigerator(double consumptionWatts) {
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Refrigerator " + getSerialNumber() + " activated";
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}

class MobileApp implements RemoteControllable {
    private String appName;

    public MobileApp(String appName) {
        this.appName = appName;
    }

    @Override
    public String connect(String appId) {
        return appName + " connected to " + appId;
    }
}

public class ConnectedHomeControlPanel {

    public static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable item : items) {
            System.out.println(item.connect(appId));
        }
    }

    public static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            EnergyTrackable trackable = (EnergyTrackable) d;
            return trackable.getConsumptionWatts();
        }

        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        Refrigerator fridge = new Refrigerator(150.0);
        MobileApp app = new MobileApp("HomeConnect App");

        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));

        System.out.println(getConsumptionIfTrackable(fridge));

        System.out.println(app.connect("HomeConnect"));

        HomeDevice ref = wm;
        System.out.println(getConsumptionIfTrackable(ref));

        connectAll(new RemoteControllable[]{wm, app}, "HomeConnect");
    }
}