public class Ex6 {

    // ---- Subject Interface ----
    interface Image {
        void display();
    }

    // ---- Real Subject ----
    static class RealImage implements Image {
        private final String fileName;

        public RealImage(String fileName) {
            this.fileName = fileName;
            loadFromRemoteServer();
        }

        private void loadFromRemoteServer() {
            System.out.println("Loading '" + fileName + "' from remote server... (expensive)");
            try {
                Thread.sleep(500); // simulate network latency
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void display() {
            System.out.println("Displaying '" + fileName + "'");
        }
    }

    // ---- Proxy: lazy initialization + caching ----
    static class ProxyImage implements Image {
        private final String fileName;
        private RealImage realImage; // created only on first use, then cached

        public ProxyImage(String fileName) {
            this.fileName = fileName;
        }

        public void display() {
            if (realImage == null) {
                realImage = new RealImage(fileName); // lazy init
            } else {
                System.out.println("(cache hit - no remote load)");
            }
            realImage.display();
        }
    }

    // ---- Test ----
    public static void main(String[] args) {
        Image img1 = new ProxyImage("photo1.jpg");
        Image img2 = new ProxyImage("photo2.jpg");

        System.out.println("-- First display of photo1 --");
        img1.display();

        System.out.println("\n-- Second display of photo1 (cached) --");
        img1.display();

        System.out.println("\n-- First display of photo2 --");
        img2.display();
    }
}