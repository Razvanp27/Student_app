package lab3.controller;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    private RegistrationSystem regSystem = new RegistrationSystem();

    public RegistrationSystem getRegSystem() {
        return regSystem;
    }

    //validator
    private long getLongInput(String entity, Scanner scanner) {
        long id;
        do {
            System.out.print("Please enter the " + entity + " positive id: ");
            while (!scanner.hasNextLong()) {
                System.out.print("Invalid input, please enter a positive long number: ");
                scanner.next();
            }
            id = scanner.nextLong();
        } while (id <= 0);
        return id;
    }

    private int getIntInput(String entity, Scanner scanner) {
        int index;
        do {
            System.out.print("Please enter the " + entity + " positive integer: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a positive number: ");
                scanner.next();
            }
            index = scanner.nextInt();
        } while (index <= 0);
        return index;
    }

    public void register(Scanner scanner) {
        long idCourse, idStudent;
        idCourse = getLongInput("Course", scanner);
        Course course = new Course();
        try {
            course = regSystem.getRepoCourse().getCourse(idCourse);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        if (course != null) {
            idStudent = getLongInput("Student", scanner);
            Student student = new Student();
            try {
                student = regSystem.getRepoStudent().getStudent(idStudent);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            if (student != null) {
                try {
                    regSystem.register(idCourse, idStudent);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Student not found");
            }
        }
    }

    public void retrieveCoursesWithFreePlaces() {
        List<Course> list = new ArrayList<>();
        try {
            list = regSystem.retrieveCoursesWithFreePlaces();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (list.size() > 0) {
            System.out.println("Courses with free places: ");
            list.forEach(course -> {
                System.out.println(course.getId() + " " + course.getName());
            });
        } else {
            System.out.println("No Courses with free places left.");
        }
    }

    public void retrieveStudentsEnrolledForACourse(Scanner scanner) {
        long id;
        Course course = new Course();
        List<Student> list = new ArrayList<>();
        id = getLongInput("Course", scanner);
        try {
            course = regSystem.getRepoCourse().getCourse(id);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        if (course == null) {
            System.out.println("Course not found.");
        } else {
            try {
                list = regSystem.retrieveStudentsEnrolledForACourse(course);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            if (list != null) {
                System.out.println("Students enrolled: ");
                list.forEach(student -> {
                    System.out.println(student.getStudentID() + " " + student.getFirstName()
                            + " " + student.getLastName());
                });
            } else {
                System.out.println("No students enrolled in this course.");
            }
        }
    }

    public void getAllCourses() {
        List<Course> list = new ArrayList<>();
        try {
            list = regSystem.getAllCourses();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        if (list != null) {
            list.forEach(course -> {
                System.out.println(course.getId() + " " + course.getName());
            });
        } else {
            System.out.println("No Courses found.");
        }
    }

    public void deleteCourseFromTeacher(Scanner scanner) {
        Course course = new Course();
        long idCourse, idTeacher;
        idCourse = getLongInput("Course", scanner);
        try {
            course = regSystem.getRepoCourse().getCourse(idCourse);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        if (course != null) {
            idTeacher = getLongInput("Teacher", scanner);
            Teacher teacher = new Teacher();
            try {
                teacher = regSystem.getRepoTeacher().getTeacher(idTeacher);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            if (teacher != null) {
                try {
                    regSystem.deleteCourse(course, teacher);
                    regSystem.updateCourses(course);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Teacher is no more teaching at this Course.");
            } else {
                System.out.println("Teacher not found");
            }
        } else {
            System.out.println("Course not found");
        }


    }

    public void changeCredits(Scanner scanner) {
        long id;
        int credits;
        id = getLongInput("Course", scanner);
        do {
            System.out.print("Please enter the new credits number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter an integer");
                scanner.next();
            }
            credits = scanner.nextInt();
        } while (credits <= 0);
        Course course = new Course();
        try {
            course = regSystem.getRepoCourse().getCourse(id);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        if (course != null) {
            regSystem.changeCredits(course, credits);
            regSystem.updateCredits();
        } else {
            System.out.println("Course not found.");
        }
    }

    public void addCourse(Scanner scanner) {
        long id, teacherID;
        int maxEn, credits;
        String name;
        id = getLongInput("Course", scanner);
        try {
            do {
                System.out.print("Please enter new Course name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                name = scanner.next();
            } while (name == null);
            do {
                System.out.print("Please enter new Course MaxEnrollment number: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter an integer: ");
                    scanner.next();
                }
                maxEn = scanner.nextInt();
            } while (maxEn <= 0);
            do {
                System.out.print("Please enter new Course credits: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter an integer: ");
                    scanner.next();
                }
                credits = scanner.nextInt();
            } while (credits <= 0);
            do {
                teacherID = getLongInput("Teacher", scanner);
            } while (!regSystem.addCourse(id, name, teacherID, maxEn, credits));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addStudent(Scanner scanner) {
        long id;
        String first, last;
        id = getLongInput("Student", scanner);
        do {
            System.out.print("Please enter new Student first name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            first = scanner.next();
        } while (first == null);
        do {
            System.out.print("Please enter new Student last name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            last = scanner.next();
        } while (last == null);
        try {
            regSystem.addStudent(id, first, last);
        } catch (Exception e) {
            System.out.println("Exception thrown:" + e);
        }
    }

    public void addTeacher(Scanner scanner) {
        long id;
        String first, last;
        id = getLongInput("Teacher", scanner);
        do {
            System.out.print("Please enter new Teacher first name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            first = scanner.next();
        } while (first == null);
        do {
            System.out.print("Please enter new Teacher last name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            last = scanner.next();
        } while (last == null);
        try {
            regSystem.addTeacher(id, first, last);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("new Teacher added: " + id + " " + first + " " + last);
    }

    public void deleteCourse(Scanner scanner) {
        long id;
        do {
            id = getLongInput("Course", scanner);
        } while (!regSystem.deleteCourse(id));
        Course course = new Course();
        try {
            course = regSystem.getRepoCourse().getCourse(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        regSystem.deleteCourse(id);
        regSystem.updateCourses(course);
    }

    public void deleteStudent(Scanner scanner) {
        long id;
        do {
            id = getLongInput("Student", scanner);
        } while (!regSystem.deleteStudent(id));
        regSystem.deleteStudent(id);
    }

    public void deleteTeacher(Scanner scanner) {
        long id;
        do {
            id = getLongInput("Teacher", scanner);
        } while (!regSystem.deleteTeacher(id));
        regSystem.deleteTeacher(id);
    }

    public void retrieveCourse(Scanner scanner) {
        long id;
        id = getLongInput("Course", scanner);
        try {
            regSystem.retrieveCourse(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void retrieveStudent(Scanner scanner) {
        long id;
        id = getLongInput("Student", scanner);
        try {
            regSystem.retrieveStudent(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void retrieveTeacher(Scanner scanner) {
        long id;
        id = getLongInput("Teacher", scanner);
        try {
            regSystem.retrieveTeacher(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Sort students functions
    public void sortStudentsByName() {
        List<Student> sortedList = regSystem.sortStudentsByName();
        sortedList.forEach(student -> {
            System.out.println(student.toString());
        });
    }

    public void sortStudentsByCredits() {
        List<Student> sortedList = regSystem.sortStudentsByCredits();
        sortedList.forEach(student -> {
            System.out.println(student.toString());
        });
    }

    public void sortStudentsByEnrolledCourses() {
        List<Student> sortedList = regSystem.sortStudentsByEnrolledCourses();
        sortedList.forEach(student -> {
            System.out.println(student.toString());
        });
    }

    //Sort courses functions
    public void sortCoursesByName() {
        List<Course> originalList = regSystem.sortCoursesByName();
        originalList.forEach(course-> {
            System.out.println(course.toString());
        });
    }

    public void sortCoursesByCredits() {
        List<Course> originalList = regSystem.sortCoursesByCredits();
        originalList.forEach(course -> {
            System.out.println(course.toString());
        });
    }

    public void sortCoursesByEnrolledStudents() {
        List<Course> originalList = regSystem.sortCoursesByEnrolledStudents();
        originalList.forEach(course ->{
            System.out.println(course.toString());
        });
    }

    //Filter students functions
    public void filterStudentsByCredits(Scanner scanner) {
        int credits = getIntInput("min Credits", scanner);
        List<Student> originalList = regSystem.filterStudentsWithAtLeastXNumberOfCredits(credits);
        originalList.forEach(student -> {
            System.out.println(student.toString());
        });
    }

    public void filterStudentsByName(Scanner scanner) {
        List<String> listOfNames = new ArrayList<>();
        String input;
        do {
            System.out.print("Please enter the name of a student or 'exit' to quit this operation: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string");
                scanner.next();
            }
            input = scanner.next();
            if (!input.equals("exit"))
                listOfNames.add(input);
        }
        while (!input.equals("exit"));
        List<Student> originalList = regSystem.filterStudents(listOfNames);
        originalList.forEach(student -> {
            System.out.println(student.toString());
        });
    }

    //Filter courses functions
    public void filterCoursesByFreePlaces(Scanner scanner) {
        int places = getIntInput("min Places", scanner);
        List<Course> originalList = regSystem.filterCoursesWithAtLeastXNumberOfStudentsEnrolled(places);
        originalList.forEach(course -> {
            System.out.println(course.toString());
        });
    }

    public void filterCoursesByName(Scanner scanner) {
        List<String> listOfNames = new ArrayList<>();
        String input;
        do {
            System.out.print("Please enter the name of the course or 'exit' to quit this operation: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            input = scanner.next();
            if (!input.equals("exit"))
                listOfNames.add(input);
        }
        while (!input.equals("exit"));
        List<Course> originalList = regSystem.filterCourses(listOfNames);
        originalList.forEach(course -> {
            System.out.println(course.toString());
        });
    }

    //Filter teachers function
    public void filterTeachersByName(Scanner scanner) {
        List<String> listOfNames = new ArrayList<>();
        String input;
        do {
            System.out.print("Please enter the name of a teacher or 'exit' to quit this operation: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            input = scanner.next();
            if (!input.equals("exit"))
                listOfNames.add(input);
        }
        while (!input.equals("exit"));
        List<Teacher> originalList = regSystem.filterTeachers(listOfNames);
        originalList.forEach(teacher -> {
            System.out.println(teacher.toString());
        });
    }

    //File methods
    public void findCourse(Scanner scanner) {
        long id = getIntInput("Course", scanner);
        if (!regSystem.findCourse(id)) {
            System.out.println("Course not found in File");
        } else {
            System.out.println("Course found");
        }
    }

    public void findStudent(Scanner scanner) {
        long id = getIntInput("Student", scanner);
        if (!regSystem.findCourse(id)) {
            System.out.println("Student not found in File");
        } else {
            System.out.println("Student found");
        }
    }

    public void findTeacher(Scanner scanner) {
        long id = getIntInput("Teacher", scanner);
        if (!regSystem.findTeacher(id)) {
            System.out.println("Teacher not found in File");
        } else {
            System.out.println("Teacher found");
        }
    }

    public void readAllFromCourseFile() {
        boolean check = regSystem.readAllFromCourseFile();
        if (!check) {
            System.out.println("Course file is empty");
        }
    }

    public void readAllFromStudentFile() {
        boolean check = regSystem.readAllFromStudentFile();
        if (!check) {
            System.out.println("Student file is empty");
        }
    }

    public void readAllFromTeacherFile() {
        boolean check = regSystem.readAllFromTeacherFile();
        if (!check) {
            System.out.println("Teacher file is empty");
        }
    }

    public void saveCourseInFile(Scanner scanner) {
        int maxEn, credits;
        String name;
        long id = getLongInput("Course", scanner);
        try {
            do {
                System.out.print("Please enter new Course name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                name = scanner.next();
            } while (name == null);
            do {
                System.out.print("Please enter new Course MaxEnrollment number: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter an integer: ");
                    scanner.next();
                }
                maxEn = scanner.nextInt();
            } while (maxEn <= 0);
            do {
                System.out.print("Please enter new Course credits: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter an integer: ");
                    scanner.next();
                }
                credits = scanner.nextInt();
            } while (credits <= 0);
            regSystem.saveCourseInFile(new Course(id, name, maxEn, credits));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveStudentInFile(Scanner scanner) {
        String first, last;
        long id = getLongInput("Student", scanner);
        do {
            System.out.print("Please enter new Student first name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            first = scanner.next();
        } while (first == null);
        do {
            System.out.print("Please enter new Student last name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            last = scanner.next();
        } while (last == null);
        try {
            regSystem.saveStudentInFile(new Student(id, first, last, 0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveTeacherInFile(Scanner scanner) {
        String first, last;
        long id = getLongInput("Teacher", scanner);
        do {
            System.out.print("Please enter new Teacher first name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            first = scanner.next();
        } while (first == null);
        do {
            System.out.print("Please enter new Teacher last name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            last = scanner.next();
        } while (last == null);
        try {
            regSystem.saveTeacherInFile(new Teacher(id, first, last));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCourseFromFile(Scanner scanner) {
        long id = getLongInput("Course", scanner);
        try {
            regSystem.deleteCourseFromFile(id);
            System.out.println("Course deleted from file");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteStudentFromFile(Scanner scanner) {
        long id = getLongInput("Student", scanner);
        try {
            regSystem.deleteStudentFromFile(id);
            System.out.println("Student deleted from file");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTeacherFromFile(Scanner scanner) {
        long id = getLongInput("Teacher", scanner);
        try {
            regSystem.deleteTeacherFromFile(id);
            System.out.println("Teacher deleted from file");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCourseInFile(Scanner scanner) {
        String name;
        int maxEn, credits;
        long id = getLongInput("Course", scanner);
        if (regSystem.findCourse(id)) {
            try {
                do {
                    System.out.print("Please enter new Course name: ");
                    while (scanner.hasNextInt()) {
                        System.out.print("Invalid input, please enter a string: ");
                        scanner.next();
                    }
                    name = scanner.next();
                } while (name == null);
                do {
                    System.out.print("Please enter new Course MaxEnrollment number: ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid input, please enter an integer: ");
                        scanner.next();
                    }
                    maxEn = scanner.nextInt();
                } while (maxEn <= 0);
                do {
                    System.out.print("Please enter new Course credits: ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid input, please enter an integer: ");
                        scanner.next();
                    }
                    credits = scanner.nextInt();
                } while (credits <= 0);
                regSystem.updateCourseInFile(new Course(id, name, maxEn, credits));
                System.out.println("Course updated");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Course Id not found in file");
        }
    }

    public void updateStudentInFile(Scanner scanner) {
        String first, last;
        long id = getLongInput("Student", scanner);
        if (regSystem.findStudent(id)) {
            do {
                System.out.print("Please enter new Student first name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                first = scanner.next();
            } while (first == null);
            do {
                System.out.print("Please enter new Student last name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                last = scanner.next();
            } while (last == null);
            try {
                regSystem.updateStudentInFile(new Student(id, first, last, 0));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Student Id not found in file");
        }
    }

    public void updateTeacherInFile(Scanner scanner) {
        String first, last;
        long id = getLongInput("Teacher", scanner);
        if (regSystem.findTeacher(id)) {
            do {
                System.out.print("Please enter new Teacher first name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                first = scanner.next();
            } while (first == null);
            do {
                System.out.print("Please enter new Teacher last name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                last = scanner.next();
            } while (last == null);
            try {
                regSystem.updateTeacherInFile(new Teacher(id, first, last));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Teacher Id not found in file");
        }
    }

    ////////////////////////////////////////////////////////////////
    //JDBC Controller
    ////////////////////////////////////////////////////////////////
    public void loadAllData() {
        regSystem.getJdbcC().load();
    }

    public void findCourseInDb(Scanner scanner) {
        long id = getLongInput("Course", scanner);
        if (regSystem.getJdbcC().findCourse(id) == null) {
            System.out.println("Course not found in database.");
        } else {
            System.out.println("Course found: " + regSystem.getJdbcC().findCourse(id).toString());
        }
    }

    public void findStudentInDb(Scanner scanner) {
        long id = getLongInput("Student", scanner);
        if (regSystem.getJdbcC().findStudent(id) == null) {
            System.out.println("Student not found in database.");
        } else {
            System.out.println("Student found: " + regSystem.getJdbcC().findStudent(id).toString());
        }
    }

    public void findTeacherInDb(Scanner scanner) {
        long id = getLongInput("Teacher", scanner);
        if (regSystem.getJdbcC().findTeacher(id) == null) {
            System.out.println("Teacher not found in database.");
        } else {
            System.out.println("Teacher found: " + regSystem.getJdbcC().findTeacher(id).toString());
        }
    }

    public void deleteCourseInDb(Scanner scanner) {
        long id = getIntInput("Course", scanner);
        if (regSystem.getJdbcC().deleteCourse(id) == null) {
            System.out.println("Course not found in database.");
        } else {
            System.out.println("Course deleted: " + regSystem.getJdbcC().findCourse(id).toString());
        }
    }

    public void deleteStudentInDb(Scanner scanner) {
        long id = getIntInput("Student", scanner);
        if (regSystem.getJdbcC().deleteStudent(id) == null) {
            System.out.println("Student not found in database.");
        } else {
            System.out.println("Student deleted: " + regSystem.getJdbcC().findStudent(id).toString());
        }
    }

    public void deleteTeacherInDb(Scanner scanner) {
        long id = getIntInput("Teacher", scanner);
        if (regSystem.getJdbcC().deleteTeacher(id) == null) {
            System.out.println("Teacher not found in database.");
        } else {
            System.out.println("Teacher deleted: " + regSystem.getJdbcC().findTeacher(id).toString());
        }
    }

    public void insertCourseInDb(Scanner scanner) {
        long id;
        int maxEn, credits;
        String name;
        id = getLongInput("Course", scanner);
        try {
            do {
                System.out.print("Please enter new Course name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                name = scanner.next();
            } while (name == null);
            do {
                System.out.print("Please enter new Course MaxEnrollment number: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter an integer: ");
                    scanner.next();
                }
                maxEn = scanner.nextInt();
            } while (maxEn <= 0);
            do {
                System.out.print("Please enter new Course credits: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter an integer: ");
                    scanner.next();
                }
                credits = scanner.nextInt();
            } while (credits <= 0);
            Course course = regSystem.getJdbcC().insertCourse(new Course(id, name, maxEn, credits));
            if (course != null) {
                System.out.println("Course inserted: " + course.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertStudentInDb(Scanner scanner) {
        long id;
        String first, last;
        int credits;
        id = getLongInput("Student", scanner);
        do {
            System.out.print("Please enter new Student first name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            first = scanner.next();
        } while (first == null);
        do {
            System.out.print("Please enter new Student last name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            last = scanner.next();
        } while (last == null);
        do {
            System.out.print("Please enter new Student credits: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter an integer: ");
                scanner.next();
            }
            credits = scanner.nextInt();
        } while (credits <= 0);
        Student student = regSystem.getJdbcC().insertStudent(new Student(id, first, last, credits));
        if (student != null) {
            System.out.println("Student inserted:" + student.toString());
        }
    }

    public void insertTeacherInDb(Scanner scanner) {
        long id;
        String first, last;
        id = getLongInput("Teacher", scanner);
        do {
            System.out.print("Please enter new Teacher first name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            first = scanner.next();
        } while (first == null);
        do {
            System.out.print("Please enter new Teacher last name: ");
            while (scanner.hasNextInt()) {
                System.out.print("Invalid input, please enter a string: ");
                scanner.next();
            }
            last = scanner.next();
        } while (last == null);
        Teacher teacher = regSystem.getJdbcC().insertTeacher(new Teacher(id, first, last));
        if (teacher != null) {
            System.out.println("Teacher inserted: " + teacher.toString());
        }
    }

    public void updateCourseInDb(Scanner scanner) {
        String name;
        int maxEn, credits;
        long id = getLongInput("Course", scanner);
        if (regSystem.getJdbcC().findCourse(id) != null) {
            do {
                System.out.print("Please enter new Course name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                name = scanner.next();
            } while (name == null);
            do {
                System.out.print("Please enter new Course MaxEnrollment number: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter an integer: ");
                    scanner.next();
                }
                maxEn = scanner.nextInt();
            } while (maxEn <= 0);
            do {
                System.out.print("Please enter new Course credits: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter an integer: ");
                    scanner.next();
                }
                credits = scanner.nextInt();
            } while (credits <= 0);
            Course course = new Course(id, name, maxEn, credits);
            if(regSystem.getJdbcC().updateCourse(course) == null) {
                System.out.println("Course updated successfully: " + course.toString());
            }
        }else {
            System.out.println("Course Id not found in database");
        }
    }
    public void updateStudentInDb(Scanner scanner){
        String first, last;
        int credits;
        long id = getLongInput("Student", scanner);
        if (regSystem.findStudent(id)) {
            do {
                System.out.print("Please enter new Student first name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                first = scanner.next();
            } while (first == null);
            do {
                System.out.print("Please enter new Student last name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                last = scanner.next();
            } while (last == null);
            do {
                System.out.print("Please enter new Student credits: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter an integer: ");
                    scanner.next();
                }
                credits = scanner.nextInt();
            } while (credits <= 0);
            Student student = new Student(id, first, last, credits);
            if(regSystem.getJdbcC().updateStudent(student) == null){
                System.out.println("Student updated successfully: " + student);
            }
        } else {
            System.out.println("Student Id not found in file");
        }
    }
    public void updateTeacherInDb(Scanner scanner){
        String first, last;
        long id = getLongInput("Teacher", scanner);
        if (regSystem.findTeacher(id)) {
            do {
                System.out.print("Please enter new Teacher first name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                first = scanner.next();
            } while (first == null);
            do {
                System.out.print("Please enter new Teacher last name: ");
                while (scanner.hasNextInt()) {
                    System.out.print("Invalid input, please enter a string: ");
                    scanner.next();
                }
                last = scanner.next();
            } while (last == null);
            Teacher teacher = new Teacher(id, first, last);
            if(regSystem.getJdbcC().updateTeacher(teacher) == null){
                System.out.println("Teacher updated successfully: " + teacher.toString());
            }
        } else {
            System.out.println("Teacher Id not found in file");
        }
    }

    public void saveAllInDB() {
        regSystem.getJdbcC().saveAllCourses();
        regSystem.getJdbcC().saveAllStudents();
        regSystem.getJdbcC().saveAllTeachers();
    }
}

