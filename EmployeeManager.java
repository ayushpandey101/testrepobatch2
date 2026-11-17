import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManager {
    private static final List<Employee> employees = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> displayEmployees();
                case 3 -> searchEmployees();
                case 4 -> updateEmployee();
                case 5 -> deleteEmployee();
                case 6 -> {
                    running = false;
                    System.out.println("Exiting employee manager.");
                }
                default -> System.out.println("Invalid option. Please choose 1-6.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== Employee Manager ===");
        System.out.println("1. Add employee");
        System.out.println("2. Display employees");
        System.out.println("3. Search employees");
        System.out.println("4. Update employee");
        System.out.println("5. Delete employee");
        System.out.println("6. Exit");
    }

    private static void addEmployee() {
        int id = readInt("Employee ID: ");
        if (findById(id) != null) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        String name = readRequiredText("Name: ");
        String department = readRequiredText("Department: ");
        double salary = readNonNegativeDouble("Salary: ");

        employees.add(new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.printf("%-10s %-25s %-20s %12s%n",
                "ID", "Name", "Department", "Salary");
        System.out.println("-".repeat(72));
        for (Employee employee : employees) {
            System.out.printf("%-10d %-25s %-20s %12.2f%n",
                    employee.id, employee.name, employee.department, employee.salary);
        }
    }

    private static void updateEmployee() {
        int id = readInt("Enter the ID of the employee to update: ");
        Employee employee = findById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        employee.name = readRequiredText("New name: ");
        employee.department = readRequiredText("New department: ");
        employee.salary = readNonNegativeDouble("New salary: ");
        System.out.println("Employee updated successfully.");
    }

    private static void deleteEmployee() {
        int id = readInt("Enter the ID of the employee to delete: ");
        Employee employee = findById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        employees.remove(employee);
        System.out.println("Employee deleted successfully.");
    }

    private static void searchEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        String query = readRequiredText("Search by ID, name, or department: ").toLowerCase();
        boolean found = false;

        for (Employee employee : employees) {
            if (String.valueOf(employee.id).equals(query)
                    || employee.name.toLowerCase().contains(query)
                    || employee.department.toLowerCase().contains(query)) {
                if (!found) {
                    System.out.printf("%-10s %-25s %-20s %12s%n",
                            "ID", "Name", "Department", "Salary");
                    System.out.println("-".repeat(72));
                }
                System.out.printf("%-10d %-25s %-20s %12.2f%n",
                        employee.id, employee.name, employee.department, employee.salary);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching employees found.");
        }
    }

    private static Employee findById(int id) {
        for (Employee employee : employees) {
            if (employee.id == id) {
                return employee;
            }
        }
        return null;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException exception) {
                // Show the same message for malformed and negative values.
            }
            System.out.println("Please enter a valid non-negative salary.");
        }
    }

    private static String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static class Employee {
        private final int id;
        private String name;
        private String department;
        private double salary;

        private Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
    }
}
