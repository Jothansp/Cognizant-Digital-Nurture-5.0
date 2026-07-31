public class Ex10 {

    // ---- Model ----
    static class Student {
        private String name;
        private String id;
        private String grade;

        public Student(String name, String id, String grade) {
            this.name = name;
            this.id = id;
            this.grade = grade;
        }

        public String getName()  { return name; }
        public void setName(String name) { this.name = name; }

        public String getId()    { return id; }
        public void setId(String id) { this.id = id; }

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }
    }

    // ---- View ----
    static class StudentView {
        public void displayStudentDetails(String name, String id, String grade) {
            System.out.println("---- Student Details ----");
            System.out.println("Name : " + name);
            System.out.println("ID   : " + id);
            System.out.println("Grade: " + grade);
            System.out.println();
        }
    }

    // ---- Controller ----
    static class StudentController {
        private final Student model;
        private final StudentView view;

        public StudentController(Student model, StudentView view) {
            this.model = model;
            this.view = view;
        }

        public void setStudentName(String name)  { model.setName(name); }
        public String getStudentName()           { return model.getName(); }

        public void setStudentId(String id)      { model.setId(id); }
        public String getStudentId()             { return model.getId(); }

        public void setStudentGrade(String grade){ model.setGrade(grade); }
        public String getStudentGrade()          { return model.getGrade(); }

        public void updateView() {
            view.displayStudentDetails(model.getName(), model.getId(), model.getGrade());
        }
    }

    // ---- Test ----
    public static void main(String[] args) {
        Student student = new Student("Arjun Menon", "S101", "A");
        StudentView view = new StudentView();
        StudentController controller = new StudentController(student, view);

        controller.updateView();

        System.out.println(">> Updating grade to A+ and name to Arjun K. Menon");
        controller.setStudentGrade("A+");
        controller.setStudentName("Arjun K. Menon");

        controller.updateView();
    }
}