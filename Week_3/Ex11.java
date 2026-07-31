import java.util.HashMap;
import java.util.Map;

public class Ex11 {

    // ---- Repository Interface ----
    interface CustomerRepository {
        String findCustomerById(int id);
    }

    // ---- Concrete Repository ----
    static class CustomerRepositoryImpl implements CustomerRepository {
        private final Map<Integer, String> data = new HashMap<>();

        public CustomerRepositoryImpl() {
            data.put(1, "Suresh Kumar");
            data.put(2, "Priya Iyer");
            data.put(3, "Nikhil Nair");
        }

        public String findCustomerById(int id) {
            return data.getOrDefault(id, "Customer not found");
        }
    }

    // ---- Alternative Repository (shows why DI is useful) ----
    static class MockCustomerRepository implements CustomerRepository {
        public String findCustomerById(int id) {
            return "Mock Customer #" + id;
        }
    }

    // ---- Service (depends on abstraction, not implementation) ----
    static class CustomerService {
        private final CustomerRepository repository;

        // Constructor Injection
        public CustomerService(CustomerRepository repository) {
            this.repository = repository;
        }

        public void getCustomer(int id) {
            System.out.println("Customer #" + id + " -> " + repository.findCustomerById(id));
        }
    }

    // ---- Test ----
    public static void main(String[] args) {
        System.out.println("== Using real repository ==");
        CustomerService service = new CustomerService(new CustomerRepositoryImpl());
        service.getCustomer(1);
        service.getCustomer(2);
        service.getCustomer(9);

        System.out.println("\n== Using mock repository (same service, injected differently) ==");
        CustomerService mockService = new CustomerService(new MockCustomerRepository());
        mockService.getCustomer(1);
        mockService.getCustomer(9);
    }
}