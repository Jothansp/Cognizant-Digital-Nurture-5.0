import java.util.Arrays;

public class OrderSorting {

    // ---- Order Class ----
    static class Order {
        int orderId;
        String customerName;
        double totalPrice;

        public Order(int orderId, String customerName, double totalPrice) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.totalPrice = totalPrice;
        }

        @Override
        public String toString() {
            return "Order [id=" + orderId + ", customer=" + customerName
                    + ", price=Rs." + totalPrice + "]";
        }
    }

    // ---- Bubble Sort (ascending by totalPrice) ----
    // Time: O(n^2) average/worst, O(n) best (already sorted). Space: O(1).
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].totalPrice > orders[j + 1].totalPrice) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // early exit if already sorted
        }
    }

    // ---- Quick Sort (ascending by totalPrice) ----
    // Time: O(n log n) average, O(n^2) worst. Space: O(log n) recursion stack.
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high);
            quickSort(orders, low, pivotIndex - 1);
            quickSort(orders, pivotIndex + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].totalPrice; // last element as pivot
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (orders[j].totalPrice <= pivot) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;
        return i + 1;
    }

    // ---- Utility ----
    private static void printOrders(String title, Order[] orders) {
        System.out.println("\n---- " + title + " ----");
        for (Order o : orders) System.out.println(o);
    }

    // ---- Main / Test ----
    public static void main(String[] args) {
        Order[] orders = {
            new Order(101, "Rahul",    2500.00),
            new Order(102, "Priya",     799.50),
            new Order(103, "Suresh",   15200.00),
            new Order(104, "Meera",     450.00),
            new Order(105, "Arjun",    9800.75),
            new Order(106, "Kavita",   3300.00)
        };

        // Bubble Sort on a copy
        Order[] bubbleCopy = Arrays.copyOf(orders, orders.length);
        bubbleSort(bubbleCopy);
        printOrders("After Bubble Sort (ascending by price)", bubbleCopy);

        // Quick Sort on a copy
        Order[] quickCopy = Arrays.copyOf(orders, orders.length);
        quickSort(quickCopy, 0, quickCopy.length - 1);
        printOrders("After Quick Sort (ascending by price)", quickCopy);
    }
}