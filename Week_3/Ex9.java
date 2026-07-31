import java.util.ArrayDeque;
import java.util.Deque;

public class Ex9 {

    // ---- Command Interface ----
    interface Command {
        void execute();
        void undo();
    }

    // ---- Receiver ----
    static class Light {
        private final String location;

        public Light(String location) {
            this.location = location;
        }

        public void turnOn()  { System.out.println(location + " light is ON"); }
        public void turnOff() { System.out.println(location + " light is OFF"); }
    }

    // ---- Concrete Commands ----
    static class LightOnCommand implements Command {
        private final Light light;
        public LightOnCommand(Light light) { this.light = light; }
        public void execute() { light.turnOn(); }
        public void undo()    { light.turnOff(); }
    }

    static class LightOffCommand implements Command {
        private final Light light;
        public LightOffCommand(Light light) { this.light = light; }
        public void execute() { light.turnOff(); }
        public void undo()    { light.turnOn(); }
    }

    // ---- Invoker ----
    static class RemoteControl {
        private Command command;
        private final Deque<Command> history = new ArrayDeque<>();

        public void setCommand(Command command) {
            this.command = command;
        }

        public void pressButton() {
            if (command == null) {
                System.out.println("No command assigned.");
                return;
            }
            command.execute();
            history.push(command);
        }

        public void pressUndo() {
            if (history.isEmpty()) {
                System.out.println("Nothing to undo.");
                return;
            }
            System.out.print("Undo -> ");
            history.pop().undo();
        }
    }

    // ---- Test ----
    public static void main(String[] args) {
        Light livingRoom = new Light("Living Room");
        Light kitchen = new Light("Kitchen");

        RemoteControl remote = new RemoteControl();

        remote.setCommand(new LightOnCommand(livingRoom));
        remote.pressButton();

        remote.setCommand(new LightOnCommand(kitchen));
        remote.pressButton();

        remote.setCommand(new LightOffCommand(livingRoom));
        remote.pressButton();

        remote.pressUndo();
        remote.pressUndo();
    }
}