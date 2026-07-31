public class EmployeeManagement {

    // ---- Employee Class ----
    static class Employee {
        int employeeId;
        String name;
        String position;
        double salary;

        public Employee(int employeeId, String name, String position, double salary) {
            this.employeeId = employeeId;
            this.name = name;
            this.position = position;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee [id=" + employeeId + ", name=" + name
                    + ", position=" + position + ", salary=Rs." + salary + "]";
        }
    }

    // ---- Storage ----
    private Employee[] employees;
    private int size;      // current count
    private int capacity;  // fixed max

    public EmployeeManagement(int capacity) {
        this.capacity = capacity;
        this.employees = new Employee[capacity];
        this.size = 0;
    }

    // ---- Add ----  Time: O(1) at end
    public boolean addEmployee(Employee emp) {
        if (size >= capacity) {
            System.out.println("Cannot add. Array is full.");
            return false;
        }
        employees[size++] = emp;
        System.out.println("Added: " + emp.name);
        return true;
    }

    // ---- Search by ID ----  Time: O(n)
    public Employee searchEmployee(int empId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].employeeId == empId) {
                System.out.println("Found at index " + i);
                return employees[i];
            }
        }
        System.out.println("Employee ID " + empId + " not found.");
        return null;
    }

    // ---- Traverse ----  Time: O(n)
    public void traverseEmployees() {
        System.out.println("---- Employee Records (" + size + ") ----");
        if (size == 0) {
            System.out.println("(No records)");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(employees[i]);
        }
    }

    // ---- Delete by ID ----  Time: O(n)
    public boolean deleteEmployee(int empId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].employeeId == empId) {
                // Shift left to fill the gap
                for (int j = i; j < size - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[--size] = null;
                System.out.println("Deleted employee ID " + empId);
                return true;
            }
        }
        System.out.println("Employee ID " + empId + " not found for deletion.");
        return false;
    }

    // ---- Main / Test ----
    public static void main(String[] args) {
        EmployeeManagement ems = new EmployeeManagement(5);

        ems.addEmployee(new Employee(101, "Rahul",  "Manager",  85000));
        ems.addEmployee(new Employee(102, "Priya",  "Analyst",  55000));
        ems.addEmployee(new Employee(103, "Suresh", "Developer",70000));
        ems.addEmployee(new Employee(104, "Meera",  "Designer", 60000));

        ems.traverseEmployees();

        System.out.println("\n---- Search ID 103 ----");
        System.out.println(ems.searchEmployee(103));

        System.out.println("\n---- Delete ID 102 ----");
        ems.deleteEmployee(102);

        ems.traverseEmployees();

        System.out.println("\n---- Search deleted ID 102 ----");
        ems.searchEmployee(102);
    }
}