public class Ex1 {

    // ---- Singleton Class ----
    static class Logger {
        private static final Logger INSTANCE = new Logger();

        private Logger() {
            System.out.println("Logger initialized (constructor called).");
        }

        public static Logger getInstance() {
            return INSTANCE;
        }

        public void log(String message) {
            System.out.println("[LOG]: " + message);
        }
    }

    // ---- Main / Test ----
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("Application started.");
        logger2.log("User logged in.");

        System.out.println("Both references point to same instance? " + (logger1 == logger2));
    }
}