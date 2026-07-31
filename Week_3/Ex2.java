public class Ex2 {

    // ---- Product ----
    interface Document {
        void open();
        void save();
    }

    // ---- Concrete Products ----
    static class WordDocument implements Document {
        public void open() { System.out.println("Opening Word document (.docx)"); }
        public void save() { System.out.println("Saving Word document (.docx)"); }
    }

    static class PdfDocument implements Document {
        public void open() { System.out.println("Opening PDF document (.pdf)"); }
        public void save() { System.out.println("Saving PDF document (.pdf)"); }
    }

    static class ExcelDocument implements Document {
        public void open() { System.out.println("Opening Excel document (.xlsx)"); }
        public void save() { System.out.println("Saving Excel document (.xlsx)"); }
    }

    // ---- Creator ----
    abstract static class DocumentFactory {
        public abstract Document createDocument();

        // Template usage of the factory method
        public void newDocument() {
            Document doc = createDocument();
            doc.open();
            doc.save();
        }
    }

    // ---- Concrete Creators ----
    static class WordDocumentFactory extends DocumentFactory {
        public Document createDocument() { return new WordDocument(); }
    }

    static class PdfDocumentFactory extends DocumentFactory {
        public Document createDocument() { return new PdfDocument(); }
    }

    static class ExcelDocumentFactory extends DocumentFactory {
        public Document createDocument() { return new ExcelDocument(); }
    }

    // ---- Test ----
    public static void main(String[] args) {
        DocumentFactory[] factories = {
            new WordDocumentFactory(),
            new PdfDocumentFactory(),
            new ExcelDocumentFactory()
        };

        for (DocumentFactory factory : factories) {
            factory.newDocument();
            System.out.println("---");
        }
    }
}