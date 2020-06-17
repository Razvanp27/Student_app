package lab3;

import lab3.controller.JfxUI;
import lab3.controller.UI;
import java.util.Scanner;

/**
 * Main class where program starts.
 */
public class StartApp {
    private UI ui = new UI();
    private void addRetrieveDeleteMenu(Scanner scanner) {
        int choice = 0;
        do {
            System.out.println("Add retrieve and delete menu");
            System.out.println("1.) Add Course");
            System.out.println("2.) Add Student");
            System.out.println("3.) Add Teacher");
            System.out.println("4.) Retrieve Course");
            System.out.println("5.) Retrieve Student");
            System.out.println("6.) Retrieve Teacher");
            System.out.println("7.) Delete Course");
            System.out.println("8.) Delete Student");
            System.out.println("9.) Delete Teacher");
            System.out.println(("10.) Return to previous menu."));
            System.out.print("Enter a choice: ");
            if (scanner.hasNextInt())
                choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    ui.addCourse(scanner);
                    break;
                case 2:
                    ui.addStudent(scanner);
                    break;
                case 3:
                    ui.addTeacher(scanner);
                    break;
                case 4:
                    ui.retrieveCourse(scanner);
                    break;
                case 5:
                    ui.retrieveStudent(scanner);
                    break;
                case 6:
                    ui.retrieveTeacher(scanner);
                    break;
                case 7:
                    ui.deleteCourse(scanner);
                    break;
                case 8:
                    ui.deleteStudent(scanner);
                    break;
                case 9:
                    ui.deleteTeacher(scanner);
                    break;
                case 10:
                    return;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }while (true);
    }

    private void sortFilterMenu(Scanner scanner){
        int choice = 0;
        do{
            System.out.println("Sort and filter menu");
            System.out.println("1.)Sort courses by name");
            System.out.println("2.)Sort courses by credits");
            System.out.println("3.)Sort courses by total number of enrolled students");
            System.out.println("4.)Sort students by first name");
            System.out.println("5.)Sort students by total number of credits");
            System.out.println("6.)Sort students by total number of enrolled courses");
            System.out.println("7.)Filter courses by selected names");
            System.out.println("8.)Filter courses by number of free places left");
            System.out.println("9.)Filter students by selected first names");
            System.out.println("10.)Filter students by total number of credits");
            System.out.println("11.)Filter teachers by selected first names");
            System.out.println("12.)Return to previous menu");
            System.out.print("Enter a choice: ");
            if(scanner.hasNextInt())
                choice = scanner.nextInt();
            switch(choice){
                case 1:
                    ui.sortCoursesByName();
                    break;
                case 2:
                    ui.sortCoursesByCredits();
                    break;
                case 3:
                    ui.sortCoursesByEnrolledStudents();
                    break;
                case 4:
                    ui.sortStudentsByName();
                    break;
                case 5:
                    ui.sortStudentsByCredits();
                    break;
                case 6:
                    ui.sortStudentsByEnrolledCourses();
                    break;
                case 7:
                    ui.filterCoursesByName(scanner);
                    break;
                case 8:
                    ui.filterCoursesByFreePlaces(scanner);
                    break;
                case 9:
                    ui.filterStudentsByName(scanner);
                    break;
                case 10:
                    ui.filterStudentsByCredits(scanner);
                    break;
                case 11:
                    ui.filterTeachersByName(scanner);
                    break;
                case 12:
                    return;
                default:
                    System.out.println("Invalid option selected.");
                    break;

            }
        }while(true);
    }
    private void fileMenu(Scanner scanner){
        int choice = 0;
        do{
            System.out.println("File Menu");
            System.out.println("1.)Find course in file");
            System.out.println("2.)Find student in file");
            System.out.println("3.)Find teacher in file");
            System.out.println("4.)Read all courses from file");
            System.out.println("5.)Read all students from file");
            System.out.println("6.)Read all teachers from file");
            System.out.println("7.)Save course in file");
            System.out.println("8.)Save student in file");
            System.out.println("9.)Save teacher in file");
            System.out.println("10.)Delete course from file");
            System.out.println("11.)Delete student from file");
            System.out.println("12.)Delete teacher from file");
            System.out.println("13.)Update course in file");
            System.out.println("14.)Update student in file");
            System.out.println("15.)Update teacher in file");
            System.out.println("16.)Return to previous menu");
            System.out.print("Enter a choice: ");
            if(scanner.hasNextInt())
                choice = scanner.nextInt();
            switch(choice){
                case 1:
                    ui.findCourse(scanner);
                    break;
                case 2:
                    ui.findStudent(scanner);
                    break;
                case 3:
                    ui.findTeacher(scanner);
                    break;
                case 4:
                    ui.readAllFromCourseFile();
                    break;
                case 5:
                    ui.readAllFromStudentFile();
                    break;
                case 6:
                    ui.readAllFromTeacherFile();
                    break;
                case 7:
                    ui.saveCourseInFile(scanner);
                    break;
                case 8:
                    ui.saveStudentInFile(scanner);
                    break;
                case 9:
                    ui.saveTeacherInFile(scanner);
                    break;
                case 10:
                    ui.deleteCourseFromFile(scanner);
                    break;
                case 11:
                    ui.deleteStudentFromFile(scanner);
                    break;
                case 12:
                    ui.deleteTeacherFromFile(scanner);
                    break;
                case 13:
                    ui.updateCourseInFile(scanner);
                    break;
                case 14:
                    ui.updateStudentInFile(scanner);
                    break;
                case 15:
                    ui.updateTeacherInFile(scanner);
                    break;
                case 16:
                    return;
                default:
                    System.out.println("Invalid option selected");
                    break;
            }
        }while(true);
    }

    private void jdbcMenu(Scanner scanner){
        int choice = 0;
        do{
            System.out.println("Jdbc Menu");
            System.out.println("1.)Load all data from database");
            System.out.println("2.)Find course in database");
            System.out.println("3.)Find student in database");
            System.out.println("4.)Find teacher in database");
            System.out.println("5.)Insert course in database");
            System.out.println("6.)Insert student in database");
            System.out.println("7.)Insert teacher in database");
            System.out.println("8.)Delete course from database");
            System.out.println("9.)Delete student from database");
            System.out.println("10.)Delete teacher from database");
            System.out.println("11.)Update course in database");
            System.out.println("12.)Update student in database");
            System.out.println("13.)Update teacher in database");
            System.out.println("14.)Save all data in database");
            System.out.println("15.)Return to previous menu");
            System.out.print("Enter a choice: ");
            if(scanner.hasNextInt())
                choice = scanner.nextInt();
            switch(choice){
                case 1:
                    ui.loadAllData();
                    break;
                case 2:
                    ui.findCourseInDb(scanner);
                    break;
                case 3:
                    ui.findStudentInDb(scanner);
                    break;
                case 4:
                    ui.findTeacherInDb(scanner);
                    break;
                case 5:
                    ui.insertCourseInDb(scanner);
                    break;
                case 6:
                    ui.insertStudentInDb(scanner);
                    break;
                case 7:
                    ui.insertTeacherInDb(scanner);
                    break;
                case 8:
                    ui.deleteCourseInDb(scanner);
                    break;
                case 9:
                    ui.deleteStudentInDb(scanner);
                    break;
                case 10:
                    ui.deleteTeacherInDb(scanner);
                    break;
                case 11:
                    ui.updateCourseInDb(scanner);
                    break;
                case 12:
                    ui.updateStudentInDb(scanner);
                    break;
                case 13:
                    ui.updateTeacherInDb(scanner);
                    break;
                case 14:
                    ui.saveAllInDB();
                    break;
                case 15:
                    return;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }while(true);
    }

    private void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do{
            System.out.println("Main menu");
            System.out.println("1.) Add Retrieve or Delete Menu");
            System.out.println("2.) Sort and Filter Menu");
            System.out.println("3.) Find Save Delete or Update in File Menu");
            System.out.println("4.) Register a student to a course");
            System.out.println("5.) Retrieve courses with free places");
            System.out.println("6.) Retrieve Students Enrolled for a course");
            System.out.println("7.) Retrieve all available courses");
            System.out.println("8.) Change the credits of the selected course");
            System.out.println("9.) Remove a course from the teachers teaching list");
            System.out.println("10.)NEW Jdbc Menu, save in database");
            System.out.println("11.) Exit.");
            System.out.print("Enter a choice: ");
            if (scanner.hasNextInt())
                choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addRetrieveDeleteMenu(scanner);
                    break;
                case 2:
                    sortFilterMenu(scanner);
                    break;
                case 3:
                    fileMenu(scanner);
                    break;
                case 4:
                    ui.register(scanner);
                    break;
                case 5:
                    ui.retrieveCoursesWithFreePlaces();
                    break;
                case 6:
                    ui.retrieveStudentsEnrolledForACourse(scanner);
                    break;
                case 7:
                    ui.getAllCourses();
                    break;
                case 8:
                    ui.changeCredits(scanner);
                    break;
                case 9:
                    ui.deleteCourseFromTeacher(scanner);
                    break;
                case 10:
                    jdbcMenu(scanner);
                    break;
                case 11:
                    return;
                default:
                    System.out.println("Invalid option selected.");
                    break;

            }
        }while (true);
    }

    /**
     * Start point of the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        JfxUI ui = new JfxUI();
        ui.menu(args);
        /*
        System.out.println("Start point");
        StartApp startApp = new StartApp();
        startApp.mainMenu();
        */
    }
}

