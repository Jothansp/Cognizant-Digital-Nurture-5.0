public class Ex5 {

    // ---- Component Interface ----
    interface Notifier {
        void send(String message);
    }

    // ---- Concrete Component ----
    static class EmailNotifier implements Notifier {
        public void send(String message) {
            System.out.println("Email  -> " + message);
        }
    }

    // ---- Abstract Decorator ----
    abstract static class NotifierDecorator implements Notifier {
        protected final Notifier wrapped;

        public NotifierDecorator(Notifier wrapped) {
            this.wrapped = wrapped;
        }

        public void send(String message) {
            wrapped.send(message);
        }
    }

    // ---- Concrete Decorators ----
    static class SMSNotifierDecorator extends NotifierDecorator {
        public SMSNotifierDecorator(Notifier wrapped) { super(wrapped); }

        @Override
        public void send(String message) {
            super.send(message);
            System.out.println("SMS    -> " + message);
        }
    }

    static class SlackNotifierDecorator extends NotifierDecorator {
        public SlackNotifierDecorator(Notifier wrapped) { super(wrapped); }

        @Override
        public void send(String message) {
            super.send(message);
            System.out.println("Slack  -> " + message);
        }
    }

    // ---- Test ----
    public static void main(String[] args) {
        System.out.println("== Email only ==");
        Notifier n1 = new EmailNotifier();
        n1.send("Server is down");

        System.out.println("\n== Email + SMS ==");
        Notifier n2 = new SMSNotifierDecorator(new EmailNotifier());
        n2.send("Server is down");

        System.out.println("\n== Email + SMS + Slack ==");
        Notifier n3 = new SlackNotifierDecorator(new SMSNotifierDecorator(new EmailNotifier()));
        n3.send("Server is down");
    }
}