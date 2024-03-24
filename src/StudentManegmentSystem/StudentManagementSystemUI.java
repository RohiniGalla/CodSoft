package StudentManegmentSystem;

import java.io.*;

import java.util.Scanner;

public class StudentManagementSystemUI {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManagementSystem sms = new StudentManagementSystem();
    private static final String FILE_PATH = "students.txt";

    public static void main(String[] args) {
        loadStudentsFromFile();
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Remove Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    editStudent();
                    break;
                case 3:
                    removeStudent();
                    break;
                case 4:
                    searchStudent();
                    break;
                case 5:
                    displayAllStudents();
                    break;
                case 6:
                    saveStudentsToFile();
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
        scanner.close();
    }

    private static void addStudent() {
        System.out.println("\nAdding Student:");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student roll number: ");
        int rollNumber = readIntegerInput();

        System.out.print("Enter student grade: ");
        String grade = scanner.nextLine();

        sms.addStudent(new Student(name, rollNumber, grade));
        System.out.println("Student added successfully!");
    }

    private static void editStudent() {
        System.out.println("\nEditing Student:");
        System.out.print("Enter roll number of student to edit: ");
        int rollNumber = readIntegerInput();

        Student student = sms.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found: " + student);
            System.out.print("Enter new name (press Enter to keep current): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                student.setName(name);
            }

            System.out.print("Enter new grade (press Enter to keep current): ");
            String grade = scanner.nextLine();
            if (!grade.isEmpty()) {
                student.setGrade(grade);
            }
            System.out.println("Student information updated successfully!");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void removeStudent() {
        System.out.println("\nRemoving Student:");
        System.out.print("Enter roll number of student to remove: ");
        int rollNumber = readIntegerInput();
        sms.removeStudent(rollNumber);
        System.out.println("Student removed successfully!");
    }

    private static void searchStudent() {
        System.out.println("\nSearching Student:");
        System.out.print("Enter roll number of student to search: ");
        int rollNumber = readIntegerInput();
        Student student = sms.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found: " + student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayAllStudents() {
        System.out.println("\nAll Students:");
        sms.displayAllStudents();
    }

    private static int readIntegerInput() {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
        return input;
    }

    private static void loadStudentsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int rollNumber = Integer.parseInt(parts[1]);
                    String grade = parts[2];
                    sms.addStudent(new Student(name, rollNumber, grade));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
        }
    }

    private static void saveStudentsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : sms.getAllStudents()) {
                bw.write(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());
                bw.newLine();
            }
            System.out.println("Students saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }
}
