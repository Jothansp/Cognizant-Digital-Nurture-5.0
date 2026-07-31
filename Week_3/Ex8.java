public class Ex8 {

    // ---- Strategy Interface ----
    interface PaymentStrategy {
        void pay(double amount);
    }

    // ---- Concrete Strategies ----
    static class CreditCardPayment implements PaymentStrategy {
        private final String cardNumber;
        private final String holderName;

        public CreditCardPayment(String cardNumber, String holderName) {
            this.cardNumber = cardNumber;
            this.holderName = holderName;
        }

        public void pay(double amount) {
            String masked = "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
            System.out.println("Paid " + amount + " using Credit Card " + masked + " (" + holderName + ")");
        }
    }

    static class PayPalPayment implements PaymentStrategy {
        private final String email;

        public PayPalPayment(String email) {
            this.email = email;
        }

        public void pay(double amount) {
            System.out.println("Paid " + amount + " using PayPal account " + email);
        }
    }

    static class UpiPayment implements PaymentStrategy {
        private final String upiId;

        public UpiPayment(String upiId) {
            this.upiId = upiId;
        }

        public void pay(double amount) {
            System.out.println("Paid " + amount + " using UPI ID " + upiId);
        }
    }

    // ---- Context ----
    static class PaymentContext {
        private PaymentStrategy strategy;

        public void setPaymentStrategy(PaymentStrategy strategy) {
            this.strategy = strategy;
        }

        public void executePayment(double amount) {
            if (strategy == null) {
                System.out.println("No payment strategy selected.");
                return;
            }
            strategy.pay(amount);
        }
    }

    // ---- Test ----
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();

        context.setPaymentStrategy(new CreditCardPayment("4532789012345678", "Arjun Menon"));
        context.executePayment(2500.00);

        context.setPaymentStrategy(new PayPalPayment("arjun@example.com"));
        context.executePayment(1200.50);

        context.setPaymentStrategy(new UpiPayment("arjun@okhdfc"));
        context.executePayment(750.00);
    }
}