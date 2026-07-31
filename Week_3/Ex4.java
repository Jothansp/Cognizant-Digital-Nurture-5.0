public class Ex4 {

    // ---- Target Interface ----
    interface PaymentProcessor {
        void processPayment(double amount);
    }

    // ---- Adaptee 1 (incompatible interface) ----
    static class PayPalGateway {
        public void sendPayment(double amountInUsd) {
            System.out.println("PayPal: payment of USD " + amountInUsd + " sent.");
        }
    }

    // ---- Adaptee 2 (incompatible interface) ----
    static class StripeGateway {
        public void makeTransaction(String currency, double value) {
            System.out.println("Stripe: transaction of " + currency + " " + value + " completed.");
        }
    }

    // ---- Adaptee 3 (incompatible interface) ----
    static class RazorpayGateway {
        public void pay(long amountInPaise) {
            System.out.println("Razorpay: paid " + amountInPaise + " paise (INR "
                    + (amountInPaise / 100.0) + ").");
        }
    }

    // ---- Adapters ----
    static class PayPalAdapter implements PaymentProcessor {
        private final PayPalGateway gateway;
        public PayPalAdapter(PayPalGateway gateway) { this.gateway = gateway; }
        public void processPayment(double amount) { gateway.sendPayment(amount); }
    }

    static class StripeAdapter implements PaymentProcessor {
        private final StripeGateway gateway;
        public StripeAdapter(StripeGateway gateway) { this.gateway = gateway; }
        public void processPayment(double amount) { gateway.makeTransaction("USD", amount); }
    }

    static class RazorpayAdapter implements PaymentProcessor {
        private final RazorpayGateway gateway;
        public RazorpayAdapter(RazorpayGateway gateway) { this.gateway = gateway; }
        public void processPayment(double amount) { gateway.pay(Math.round(amount * 100)); }
    }

    // ---- Test ----
    public static void main(String[] args) {
        PaymentProcessor[] processors = {
            new PayPalAdapter(new PayPalGateway()),
            new StripeAdapter(new StripeGateway()),
            new RazorpayAdapter(new RazorpayGateway())
        };

        for (PaymentProcessor processor : processors) {
            processor.processPayment(1500.00);
        }
    }
}