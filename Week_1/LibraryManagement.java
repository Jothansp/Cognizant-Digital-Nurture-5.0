import java.util.Arrays;
import java.util.Comparator;

public class LibraryManagement {

    // ---- Book Class ----
    static class Book {
        int bookId;
        String title;
        String author;

        public Book(int bookId, String title, String author) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
        }

        @Override
        public String toString() {
            return "Book [id=" + bookId + ", title=" + title + ", author=" + author + "]";
        }
    }

    // ---- Linear Search (by title) ----
    // Time: O(n), Space: O(1). Works on unsorted arrays.
    public static Book linearSearch(Book[] books, String targetTitle) {
        for (int i = 0; i < books.length; i++) {
            if (books[i].title.equalsIgnoreCase(targetTitle)) {
                System.out.println("Linear Search: found at index " + i
                        + " after " + (i + 1) + " comparison(s).");
                return books[i];
            }
        }
        System.out.println("Linear Search: not found after "
                + books.length + " comparison(s).");
        return null;
    }

    // ---- Binary Search (by title, array must be sorted by title) ----
    // Time: O(log n), Space: O(1).
    public static Book binarySearch(Book[] sortedBooks, String targetTitle) {
        int low = 0, high = sortedBooks.length - 1, comparisons = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            comparisons++;
            int cmp = sortedBooks[mid].title.compareToIgnoreCase(targetTitle);

            if (cmp == 0) {
                System.out.println("Binary Search: found at index " + mid
                        + " after " + comparisons + " comparison(s).");
                return sortedBooks[mid];
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
        // Unsorted book list
        Book[] books = {
            new Book(101, "The Alchemist",         "Paulo Coelho"),
            new Book(102, "Wings of Fire",         "A.P.J. Abdul Kalam"),
            new Book(103, "Rich Dad Poor Dad",     "Robert Kiyosaki"),
            new Book(104, "Atomic Habits",         "James Clear"),
            new Book(105, "The Monk Who Sold His Ferrari", "Robin Sharma"),
            new Book(106, "Sapiens",               "Yuval Noah Harari"),
            new Book(107, "Ikigai",                "Hector Garcia")
        };

        // Sorted copy for binary search
        Book[] sortedBooks = Arrays.copyOf(books, books.length);
        Arrays.sort(sortedBooks, Comparator.comparing(b -> b.title.toLowerCase()));

        System.out.println("---- Sorted array (by title, for binary search) ----");
        for (Book b : sortedBooks) System.out.println(b);

        // Test 1: Existing title
        String target = "Atomic Habits";
        System.out.println("\n---- Searching for: " + target + " ----");
        System.out.println("Result: " + linearSearch(books, target));
        System.out.println("Result: " + binarySearch(sortedBooks, target));

        // Test 2: Missing title
        String missing = "The Great Gatsby";
        System.out.println("\n---- Searching for: " + missing + " ----");
        linearSearch(books, missing);
        binarySearch(sortedBooks, missing);
    }
}