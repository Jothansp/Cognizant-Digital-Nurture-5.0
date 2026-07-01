public class Singleton {

    // Step 1: Create a single object
    private static Singleton instance;

    // Step 2: Make constructor private
    private Singleton() {
        System.out.println("Singleton Object Created");
    }

    // Step 3: Provide a public method to access the object
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void display() {
        System.out.println("Hello from Singleton!");
    }
}