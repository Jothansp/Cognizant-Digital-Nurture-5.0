public class TaskManagement {

    // ---- Task Class ----
    static class Task {
        int taskId;
        String taskName;
        String status;

        public Task(int taskId, String taskName, String status) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.status = status;
        }

        @Override
        public String toString() {
            return "Task [id=" + taskId + ", name=" + taskName + ", status=" + status + "]";
        }
    }

    // ---- Node ----
    static class Node {
        Task task;
        Node next;

        public Node(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    // ---- Singly Linked List ----
    private Node head;
    private int size;

    public TaskManagement() {
        this.head = null;
        this.size = 0;
    }

    // ---- Add at End ----  Time: O(n) [O(1) if tail pointer maintained]
    public void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node curr = head;
            while (curr.next != null) curr = curr.next;
            curr.next = newNode;
        }
        size++;
        System.out.println("Added: " + task.taskName);
    }

    // ---- Search by Task ID ----  Time: O(n)
    public Task searchTask(int taskId) {
        Node curr = head;
        int index = 0;
        while (curr != null) {
            if (curr.task.taskId == taskId) {
                System.out.println("Found at position " + index);
                return curr.task;
            }
            curr = curr.next;
            index++;
        }
        System.out.println("Task ID " + taskId + " not found.");
        return null;
    }

    // ---- Traverse ----  Time: O(n)
    public void traverseTasks() {
        System.out.println("---- Task List (" + size + ") ----");
        if (head == null) {
            System.out.println("(No tasks)");
            return;
        }
        Node curr = head;
        while (curr != null) {
            System.out.println(curr.task);
            curr = curr.next;
        }
    }

    // ---- Delete by Task ID ----  Time: O(n)
    public boolean deleteTask(int taskId) {
        if (head == null) {
            System.out.println("List empty. Nothing to delete.");
            return false;
        }

        // Head node itself matches
        if (head.task.taskId == taskId) {
            head = head.next;
            size--;
            System.out.println("Deleted task ID " + taskId);
            return true;
        }

        // Search in the rest
        Node curr = head;
        while (curr.next != null && curr.next.task.taskId != taskId) {
            curr = curr.next;
        }

        if (curr.next == null) {
            System.out.println("Task ID " + taskId + " not found for deletion.");
            return false;
        }

        curr.next = curr.next.next; // bypass matching node
        size--;
        System.out.println("Deleted task ID " + taskId);
        return true;
    }

    // ---- Main / Test ----
    public static void main(String[] args) {
        TaskManagement tm = new TaskManagement();

        tm.addTask(new Task(1, "Design Login Page", "Pending"));
        tm.addTask(new Task(2, "Setup Database",    "In Progress"));
        tm.addTask(new Task(3, "Write Unit Tests",  "Pending"));
        tm.addTask(new Task(4, "Deploy to Staging", "Not Started"));

        tm.traverseTasks();

        System.out.println("\n---- Search Task ID 3 ----");
        System.out.println(tm.searchTask(3));

        System.out.println("\n---- Delete Task ID 2 ----");
        tm.deleteTask(2);

        tm.traverseTasks();

        System.out.println("\n---- Delete Head (ID 1) ----");
        tm.deleteTask(1);

        tm.traverseTasks();

        System.out.println("\n---- Search deleted ID 2 ----");
        tm.searchTask(2);
    }
}