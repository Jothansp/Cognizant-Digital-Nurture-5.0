import java.util.Arrays;
import java.util.Comparator;

public class EcommerceSearch {

    // ---- Product Class ----
    static class Product {
        int productId;
        String productName;
        String category;

        public Product(int productId, String productName, String category) {
            this.productId = productId;
            this.productName = productName;
            this.category = category;
        }

        @Override
        public String toString() {
            return "Product [id=" + productId + ", name=" + productName
                    + ", category=" + category + "]";
        }
    }

    // ---- Linear Search (by product name) ----
    // Time: O(n), Space: O(1). Works on unsorted arrays.
    public static Product linearSearch(Product[] products, String targetName) {
        for (int i = 0; i < products.length; i++) {
            if (products[i].productName.equalsIgnoreCase(targetName)) {
                System.out.println("Linear Search: found at index " + i
                        + " after " + (i + 1) + " comparison(s).");
                return products[i];
            }
        }
        System.out.println("Linear Search: not found after "
                + products.length + " comparison(s).");
        return null;
    }

    // ---- Binary Search (by product name, array must be sorted by name) ----
    // Time: O(log n), Space: O(1).
    public static Product binarySearch(Product[] sortedProducts, String targetName) {
        int low = 0, high = sortedProducts.length - 1, comparisons = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            comparisons++;
            int cmp = sortedProducts[mid].productName.compareToIgnoreCase(targetName);

            if (cmp == 0) {
                System.out.println("Binary Search: found at index " + mid
                        + " after " + comparisons + " comparison(s).");
                return sortedProducts[mid];
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Binary Search: not found after "
                + comparisons + " comparison(s).");
        return null;
    }

    // ---- Main / Test ----
    public static void main(String[] args) {
        // Unsorted list — used for linear search
        Product[] products = {
            new Product(101, "Laptop",     "Electronics"),
            new Product(102, "Shoes",      "Footwear"),
            new Product(103, "Book",       "Stationery"),
            new Product(104, "Headphones", "Electronics"),
            new Product(105, "Watch",      "Accessories"),
            new Product(106, "Backpack",   "Bags"),
            new Product(107, "Camera",     "Electronics")
        };

        // Sorted copy — used for binary search
        Product[] sortedProducts = Arrays.copyOf(products, products.length);
        Arrays.sort(sortedProducts, Comparator.comparing(p -> p.productName.toLowerCase()));

        System.out.println("---- Sorted array (for binary search) ----");
        for (Product p : sortedProducts) System.out.println(p);

        String target = "Headphones";
        System.out.println("\n---- Searching for: " + target + " ----");

        Product r1 = linearSearch(products, target);
        System.out.println("Result: " + r1);

        Product r2 = binarySearch(sortedProducts, target);
        System.out.println("Result: " + r2);

        // Test a missing product
        String missing = "Television";
        System.out.println("\n---- Searching for: " + missing + " ----");
        linearSearch(products, missing);
        binarySearch(sortedProducts, missing);
    }
}