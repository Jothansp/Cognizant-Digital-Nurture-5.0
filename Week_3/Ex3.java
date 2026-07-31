public class Ex3 {

    // ---- Product ----
    static class Computer {
        private final String cpu;        // required
        private final String ram;        // required
        private final String storage;    // optional
        private final String graphicsCard; // optional
        private final boolean bluetooth;   // optional

        private Computer(Builder builder) {
            this.cpu = builder.cpu;
            this.ram = builder.ram;
            this.storage = builder.storage;
            this.graphicsCard = builder.graphicsCard;
            this.bluetooth = builder.bluetooth;
        }

        @Override
        public String toString() {
            return "Computer [CPU=" + cpu
                 + ", RAM=" + ram
                 + ", Storage=" + storage
                 + ", GraphicsCard=" + graphicsCard
                 + ", Bluetooth=" + bluetooth + "]";
        }

        // ---- Static Nested Builder ----
        static class Builder {
            private final String cpu;
            private final String ram;
            private String storage = "None";
            private String graphicsCard = "Integrated";
            private boolean bluetooth = false;

            public Builder(String cpu, String ram) {
                this.cpu = cpu;
                this.ram = ram;
            }

            public Builder storage(String storage) {
                this.storage = storage;
                return this;
            }

            public Builder graphicsCard(String graphicsCard) {
                this.graphicsCard = graphicsCard;
                return this;
            }

            public Builder bluetooth(boolean bluetooth) {
                this.bluetooth = bluetooth;
                return this;
            }

            public Computer build() {
                return new Computer(this);
            }
        }
    }

    // ---- Test ----
    public static void main(String[] args) {
        Computer basic = new Computer.Builder("Intel i3", "8GB").build();

        Computer gaming = new Computer.Builder("Intel i9", "32GB")
                .storage("1TB NVMe SSD")
                .graphicsCard("NVIDIA RTX 4080")
                .bluetooth(true)
                .build();

        Computer office = new Computer.Builder("AMD Ryzen 5", "16GB")
                .storage("512GB SSD")
                .build();

        System.out.println("Basic : " + basic);
        System.out.println("Gaming: " + gaming);
        System.out.println("Office: " + office);
    }
}