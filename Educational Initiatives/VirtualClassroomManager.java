import java.util.*;
import java.util.logging.*;

public class VirtualClassroomManager {
    private static final Logger logger = Logger.getLogger(VirtualClassroomManager.class.getName());

    private Map<String, Classroom> classrooms = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        VirtualClassroomManager manager = new VirtualClassroomManager();
        manager.run();
    }

    public void run() {
        logger.info("Virtual Classroom Manager started.");
        
        while (true) {
            try {
                System.out.println("Enter command: ");
                String input = scanner.nextLine().trim();
                String[] command = input.split("\\s+", 2);

                if (command.length == 0) {
                    continue;
                }

                switch (command[0].toLowerCase()) {
                    case "add_classroom":
                        handleAddClassroom(command[1]);
                        break;
                    case "add_student":
                        handleAddStudent(command[1]);
                        break;
                    case "schedule_assignment":
                        handleScheduleAssignment(command[1]);
                        break;
                    case "submit_assignment":
                        handleSubmitAssignment(command[1]);
                        break;
                    case "exit":
                        System.out.println("Exiting...");
                        logger.info("Virtual Classroom Manager stopped.");
                        return;
                    default:
                        System.out.println("Invalid command.");
                        logger.warning("Invalid command entered: " + command[0]);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                logger.severe("Exception caught: " + e.getMessage());
            }
        }
    }

    private void handleAddClassroom(String name) {
        if (classrooms.containsKey(name)) {
            System.out.println("Classroom already exists.");
            return;
        }
        Classroom classroom = new Classroom(name);
        classrooms.put(name, classroom);
        System.out.println("Classroom " + name + " has been created.");
        logger.info("Classroom added: " + name);
    }

    private void handleAddStudent(String input) {
        String[] data = input.split("\\s+");
        if (data.length < 2) {
            System.out.println("Invalid format. Use: add_student [StudentID] [ClassName]");
            return;
        }

        String studentId = data[0];
        String className = data[1];

        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            System.out.println("Classroom does not exist.");
            return;
        }

        Student student = new Student(studentId);
        classroom.addStudent(student);
        System.out.println("Student " + studentId + " has been enrolled in " + className + ".");
        logger.info("Student added: " + studentId + " to " + className);
    }

    private void handleScheduleAssignment(String input) {
        String[] data = input.split("\\s+", 2);
        if (data.length < 2) {
            System.out.println("Invalid format. Use: schedule_assignment [ClassName] [AssignmentDetails]");
            return;
        }

        String className = data[0];
        String assignmentDetails = data[1];

        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            System.out.println("Classroom does not exist.");
            return;
        }

        Assignment assignment = new Assignment(assignmentDetails);
        classroom.addAssignment(assignment);
        System.out.println("Assignment for " + className + " has been scheduled.");
        logger.info("Assignment scheduled: " + assignmentDetails + " for " + className);
    }

    private void handleSubmitAssignment(String input) {
        String[] data = input.split("\\s+", 3);
        if (data.length < 3) {
            System.out.println("Invalid format. Use: submit_assignment [StudentID] [ClassName] [AssignmentDetails]");
            return;
        }

        String studentId = data[0];
        String className = data[1];
        String assignmentDetails = data[2];

        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            System.out.println("Classroom does not exist.");
            return;
        }

        Assignment foundAssignment = null;
        for (Assignment assignment : classroom.getAssignments()) {
            if (assignment.getDetails().equals(assignmentDetails)) {
                foundAssignment = assignment;
                break;
            }
        }

        if (foundAssignment == null) {
            System.out.println("Assignment not found.");
            return;
        }

        foundAssignment.markAsSubmitted();
        System.out.println("Assignment submitted by Student " + studentId + " in " + className + ".");
        logger.info("Assignment submitted: " + assignmentDetails + " by Student " + studentId);
    }
}

