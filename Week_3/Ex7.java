import java.util.ArrayList;
import java.util.List;

public class Ex7 {

    // ---- Subject Interface ----
    interface Stock {
        void registerObserver(Observer observer);
        void deregisterObserver(Observer observer);
        void notifyObservers();
    }

    // ---- Observer Interface ----
    interface Observer {
        void update(String stockName, double price);
    }

    // ---- Concrete Subject ----
    static class StockMarket implements Stock {
        private final List<Observer> observers = new ArrayList<>();
        private String stockName;
        private double price;

        public void registerObserver(Observer observer) {
            observers.add(observer);
            System.out.println("Registered: " + observer.getClass().getSimpleName());
        }

        public void deregisterObserver(Observer observer) {
            observers.remove(observer);
            System.out.println("Deregistered: " + observer.getClass().getSimpleName());
        }

        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update(stockName, price);
            }
        }

        public void setStockPrice(String stockName, double price) {
            this.stockName = stockName;
            this.price = price;
            System.out.println("\nStockMarket: " + stockName + " price changed to " + price);
            notifyObservers();
        }
    }

    // ---- Concrete Observers ----
    static class MobileApp implements Observer {
        public void update(String stockName, double price) {
            System.out.println("  MobileApp: " + stockName + " is now " + price);
        }
    }

    static class WebApp implements Observer {
        public void update(String stockName, double price) {
            System.out.println("  WebApp   : " + stockName + " is now " + price);
        }
    }

    // ---- Test ----
    public static void main(String[] args) {
        StockMarket market = new StockMarket();

        Observer mobile = new MobileApp();
        Observer web = new WebApp();

        market.registerObserver(mobile);
        market.registerObserver(web);

        market.setStockPrice("INFY", 1520.50);
        market.setStockPrice("TCS", 3890.75);

        System.out.println();
        market.deregisterObserver(web);

        market.setStockPrice("RELIANCE", 2450.00);
    }
}