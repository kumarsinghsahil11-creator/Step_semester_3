import java.util.*;

public class SmartLabControlPanel {

    interface Capability {
        String getName();
        boolean supports(String action);
        String execute(String action, int value);
    }

    static class PowerCapability implements Capability {
        private boolean on = false;

        public String getName() {
            return "Power";
        }

        public boolean supports(String action) {
            return action.equals("ON") || action.equals("OFF");
        }

        public String execute(String action, int value) {
            if (action.equals("ON")) {
                on = true;
                return "ON";
            }

            if (action.equals("OFF")) {
                on = false;
                return "OFF";
            }

            return null;
        }
    }

    static class BrightnessCapability implements Capability {
        private int brightness = 0;

        public String getName() {
            return "Brightness";
        }

        public boolean supports(String action) {
            return action.equals("BRIGHTNESS");
        }

        public String execute(String action, int value) {

            if (value < 0 || value > 100) {
                return null;
            }

            brightness = value;
            return brightness + "%";
        }
    }

    static class TemperatureCapability implements Capability {
        private int temperature = 20;

        public String getName() {
            return "Temperature";
        }

        public boolean supports(String action) {
            return action.equals("TEMPERATURE");
        }

        public String execute(String action, int value) {

            if (value < 16 || value > 30) {
                return null;
            }

            temperature = value;
            return temperature + "°C";
        }
    }

    static class Device {
        String name;
        Map<String, Capability> capabilities = new LinkedHashMap<>();

        Device(String name) {
            this.name = name;
        }

        void addCapability(Capability capability) {
            capabilities.put(capability.getName(), capability);
            System.out.println(
                name + ": " + capability.getName() +
                " capability added."
            );
        }

        boolean hasCapability(String capabilityName) {
            return capabilities.containsKey(capabilityName);
        }

        String perform(String action, int value) {

            for (Capability capability : capabilities.values()) {

                if (capability.supports(action)) {

                    if (action.equals("TEMPERATURE") &&
                        (value < 16 || value > 30)) {

                        System.out.println(
                            "Rejected: " + name +
                            " temperature must be between 16°C and 30°C."
                        );

                        return null;
                    }

                    if (action.equals("BRIGHTNESS") &&
                        (value < 0 || value > 100)) {

                        System.out.println(
                            "Rejected: " + name +
                            " brightness must be between 0% and 100%."
                        );

                        return null;
                    }

                    String result =
                        capability.execute(action, value);

                    if (action.equals("ON") || action.equals("OFF")) {
                        System.out.println(
                            name + ": " + result + "."
                        );
                    } else if (action.equals("BRIGHTNESS")) {
                        System.out.println(
                            name + ": brightness set to " +
                            result + "."
                        );
                    } else if (action.equals("TEMPERATURE")) {
                        System.out.println(
                            name + ": temperature set to " +
                            result + "."
                        );
                    }

                    return result;
                }
            }

            return null;
        }
    }

    static class SceneStep {
        String capability;
        String action;
        int value;

        SceneStep(String capability, String action, int value) {
            this.capability = capability;
            this.action = action;
            this.value = value;
        }
    }

    static class Scene {
        String name;
        List<SceneStep> steps = new ArrayList<>();

        Scene(String name) {
            this.name = name;
        }

        void addStep(SceneStep step) {
            steps.add(step);
        }

        void execute(List<Device> devices) {

            System.out.println(
                "Scene '" + name + "' started."
            );

            int actionsApplied = 0;

            for (SceneStep step : steps) {

                for (Device device : devices) {

                    if (device.hasCapability(step.capability)) {

                        String result =
                            device.perform(step.action, step.value);

                        if (result != null) {
                            actionsApplied++;
                        }
                    }
                }
            }

            System.out.println(
                "Scene '" + name +
                "' completed: " +
                actionsApplied +
                " actions applied."
            );
        }
    }

    public static void main(String[] args) {

        Device labAC = new Device("Lab AC");

        labAC.addCapability(
            new PowerCapability()
        );

        labAC.addCapability(
            new TemperatureCapability()
        );

        Device ceilingLights =
            new Device("Ceiling Lights");

        ceilingLights.addCapability(
            new PowerCapability()
        );

        ceilingLights.addCapability(
            new BrightnessCapability()
        );

        Device projector =
            new Device("Projector");

        projector.addCapability(
            new PowerCapability()
        );

        List<Device> devices =
            Arrays.asList(
                labAC,
                ceilingLights,
                projector
            );

        Scene lectureMode =
            new Scene("Lecture Mode");

        lectureMode.addStep(
            new SceneStep(
                "Power",
                "ON",
                0
            )
        );

        lectureMode.addStep(
            new SceneStep(
                "Brightness",
                "BRIGHTNESS",
                40
            )
        );

        lectureMode.addStep(
            new SceneStep(
                "Temperature",
                "TEMPERATURE",
                24
            )
        );

        lectureMode.execute(devices);

        labAC.perform(
            "TEMPERATURE",
            12
        );

        projector.addCapability(
            new BrightnessCapability()
        );

        projector.perform(
            "BRIGHTNESS",
            70
        );
    }
}