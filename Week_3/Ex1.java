public class Ex1 {

    // ---- Singleton Class ----
    static class Logger {
        // Eager initialization -> thread-safe by class-loader guarantee
        private static final Logger INSTANCE = new Logger();

        private Logger() {
            System.out.println("Logger initialized (private constructor called once).");
        }

        public static Logger getInstance() {
            return INSTANCE;
        }

        public void log(String message) {
            System.out.println("[LOG] " + message);
        }
    }

    // ---- Test ----
    public static void main(String[] args) {
        Logger l1 = Logger.getInstance();
        Logger l2 = Logger.getInstance();

        l1.log("Application started");
        l2.log("Performing task");

        System.out.println("l1 == l2 ? " + (l1 == l2));
        System.out.println("l1 hashCode: " + l1.hashCode());
        System.out.println("l2 hashCode: " + l2.hashCode());
    }
}