package lab3.controller;

import lab3.JdbcRepository.CourseJdbcRepository;
import lab3.JdbcRepository.StudentJdbcRepository;
import lab3.JdbcRepository.TeacherJdbcRepository;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.*;

import java.awt.image.RGBImageFilter;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import lab3.controller.CourseIsFullException;

public class RegistrationSystem {
    private RepoCourse repoCourseV1 = new RepoCourse();
    private RepoStudent repoStudentV1 = new RepoStudent();
    private RepoTeacher repoTeacherV1 = new RepoTeacher();
    //FileRepository used after line 300
    private CourseFileRepository courseFileRepository = new CourseFileRepository(repoCourseV1);
    private StudentFileRepository studentFileRepository = new StudentFileRepository(repoStudentV1);
    private TeacherFileRepository teacherFileRepository = new TeacherFileRepository(repoTeacherV1);
    //JDBC Repository
    private JDBCController jdbcController = new JDBCController();
    public JDBCController getJdbcC(){return this.jdbcController;};
    private CourseJdbcRepository repoCourse = jdbcController.getCJdbc();
    private StudentJdbcRepository repoStudent = jdbcController.getSJdbc();
    private TeacherJdbcRepository repoTeacher = jdbcController.getTJdbc();

    CourseJdbcRepository getRepoCourse() {
        return this.repoCourse;
    }

    StudentJdbcRepository getRepoStudent() {
        return this.repoStudent;
    }

    TeacherJdbcRepository getRepoTeacher() {
        return this.repoTeacher;
    }

    public void register(long idCourse, long idStudent) throws CourseIsFullException, CourseNotFoundException, MaxCreditsException {
        Course course = getRepoCourse().getCourse(idCourse);
        Student student = getRepoStudent().getStudent(idStudent);
        if (!checkCredits(student, course.getCredits()))
            throw new MaxCreditsException("Credits over the limit of 30");
        if (!student.getEnrolledCourses().contains(course)) {
            if (course.getMaxEnrollment() > course.getStudentsEnrolled().size()) {
                course.addStudent(student);
                student.addCourse(course);
                student.setTotalCredits(student.getTotalCredits() + course.getCredits());
                course.setMaxEnrollment(course.getMaxEnrollment() + 1);
                return;
            } else {
                throw new CourseIsFullException("Student already enrolled to this course");
            }
        }
        throw new CourseNotFoundException("Course not found");
    }

    //+
    List<Course> retrieveCoursesWithFreePlaces() {
        List<Course> courseList = new ArrayList<>();
        repoCourse.getCourseList().forEach(course -> {
            if (course.getMaxEnrollment() > course.getStudentsEnrolled().size()) {
                courseList.add(course);
            }
        });
        if (courseList.size() > 0) {
            return courseList;
        }
        System.out.println("No More courses with free places left");
        return null;
    }

    //+
    List<Student> retrieveStudentsEnrolledForACourse(Course course) {
        Course courseResult = repoCourse.getCourseList().stream().filter(course1 -> course1 == course)
                .filter(course1 -> course1.getStudentsEnrolled().size() > 0).findAny().get();
        if (courseResult.getStudentsEnrolled().isEmpty()) {
            System.out.println("No Students enrolled in the given course");
            return null;
        } else {
            return courseResult.getStudentsEnrolled();
        }
    }

    //+
    List<Course> getAllCourses() {
        return repoCourse.getCourseList();
    }

    boolean checkCredits(Student student, int credits) {
        return student.getTotalCredits() + credits <= 30;
    }

    void changeCredits(Course course, int newCredits) {
        repoCourse.getCourseList().stream().
                filter(course1 -> course1 == course).findAny().get().setCredits(newCredits);
    }

    //+
    void deleteCourse(Course course, Teacher teacher) {
        if( repoTeacher.getTeacherList().stream().anyMatch(teacher1 -> teacher1 == teacher)) {
            Teacher teacherFound = repoTeacher.getTeacherList().stream().
                    filter(teacher1 -> teacher1 == teacher).findAny().get();
            teacherFound.getCourses().stream().filter(course1 -> course1 == course).forEach(course1 -> {
                teacherFound.removeCourse(course);
                course.removeAllStudents();
                repoCourse.delete(course.getId());
            });
        }
    }

    //helper function
    void updateCourses(Course course) {
        repoStudent.getStudentList().forEach(student -> {
            student.getEnrolledCourses().remove(course);
        });
    }

    //helper function
    void updateCredits() {
        repoStudent.getStudentList().forEach(student ->
        {
            int sum = student.getEnrolledCourses().stream().mapToInt(Course::getCredits).sum();
            if (sum > 30) {
                student.getEnrolledCourses().remove(student.getEnrolledCourses().size() - 1);
            }
        });
    }

    //+
    public boolean addCourse(long id, String name, long teacherID, int maxEn, int credits) throws IOException {
        Teacher teacher = repoTeacher.getTeacher(teacherID);
        if (teacher != null) {
            List<Student> list = new ArrayList<>();
            Course newCourse = new Course(id, name, teacher, maxEn, list, credits);
            repoCourse.save(newCourse);
            return true;
        }
        return false;
    }

    //+
    public void addStudent(long id, String firstName, String lastName) throws IOException {
        List<Course> list = new ArrayList<>();
        Student newStudent = new Student(firstName, lastName, id, 0, list);
        repoStudent.save(newStudent);
    }

    //+
    public void addTeacher(long id, String firstName, String lastName) throws IOException {
        List<Course> list = new ArrayList<>();
        Teacher newTeacher = new Teacher(id, firstName, lastName, list);
        repoTeacher.save(newTeacher);

    }

    //+
    boolean deleteCourse(long id) {
        Course course = repoCourse.getCourse(id);
        if (course != null) {
            repoCourse.delete(course.getId());
            return true;
        }
        return false;
    }

    //+
    boolean deleteStudent(long id) {
        Student student = repoStudent.getStudent(id);
        if (student != null) {
            repoStudent.delete(student.getStudentID());
            return true;
        }
        return false;
    }

    //+
    boolean deleteTeacher(long id) {
        Teacher teacher = repoTeacher.getTeacher(id);
        if (teacher != null) {
            repoTeacher.delete(teacher.getId());
            return true;
        }
        return false;
    }

    //+
    void retrieveCourse(long id) throws InvalidIdException {
        Course course = repoCourse.getCourse(id);
        if (course != null) {
            System.out.println(course.toString());
        } else {
            throw new InvalidIdException("Invalid Course ID");
        }
    }

    //+
    void retrieveStudent(long id) throws InvalidIdException {
        Student student = repoStudent.getStudent(id);
        if (student != null) {
            System.out.println(student.toString());
        } else {
            throw new InvalidIdException("Invalid student ID");
        }
    }

    //+
    void retrieveTeacher(long id) throws InvalidIdException {
        Teacher teacher = repoTeacher.getTeacher(id);
        if (teacher != null) {
            System.out.println(teacher.toString());
        } else {
            throw new InvalidIdException("Invalid teacher ID");
        }
    }

    //Sort student functions
    List<Student> sortStudentsByName() {
        List<Student> studentList = getRepoStudent().getStudentList();
        studentList.sort(Student.studentFirstNameComparator);
        return studentList;
    }

    List<Student> sortStudentsByCredits() {
        List<Student> studentList = getRepoStudent().getStudentList();
        studentList.sort(Student.studentCreditsComparator);
        return studentList;
    }

    List<Student> sortStudentsByEnrolledCourses() {
        List<Student> studentList = getRepoStudent().getStudentList();
        studentList.sort(Student.studentEnrolledCoursesNumber);
        return studentList;
    }

    //Sort course functions
    List<Course> sortCoursesByName() {
        List<Course> courseList = getAllCourses();
        courseList.sort(Course.CourseNameComparator);
        return courseList;
    }

    List<Course> sortCoursesByCredits() {
        List<Course> courseList = getAllCourses();
        courseList.sort(Course.courseCreditsComparator);
        return courseList;
    }

    List<Course> sortCoursesByEnrolledStudents() {
        List<Course> courseList = getAllCourses();
        courseList.sort(Course.courseEnrolledStudentsNumber);
        return courseList;
    }

    List<Student> filterStudentsWithAtLeastXNumberOfCredits(int credits) {
        List<Student> filteredList = new ArrayList<>();
        List<Student> originalList = getRepoStudent().getStudentList();
        originalList.stream().filter(student -> student.getTotalCredits() >= credits).forEach(
                filteredList::add);
        return filteredList;
    }

    List<Student> filterStudents(List<String> nameFilterList) {
        List<Student> filteredList;
        List<Student> originalList = getRepoStudent().getStudentList();
        Set<String> nameFilterSet = new HashSet<>(nameFilterList);
        filteredList = originalList.stream().filter(student -> nameFilterSet.contains(student.getFirstName()))
                .collect(Collectors.toList());
        return filteredList;

    }

    List<Course> filterCoursesWithAtLeastXNumberOfStudentsEnrolled(int students) {
        List<Course> filteredList = new ArrayList<>();
        List<Course> originalList = getRepoCourse().getCourseList();
        originalList.stream().filter(course -> course.getStudentsEnrolled().size() >= students)
                .forEach(filteredList::add);
        return filteredList;
    }

    List<Course> filterCourses(List<String> nameFilterList) {
        List<Course> filteredList;
        List<Course> originalList = getRepoCourse().getCourseList();
        Set<String> nameFilterSet = new HashSet<>(nameFilterList);
        filteredList = originalList.stream().filter(course -> nameFilterSet.contains(course.getName()))
                .collect(Collectors.toList());
        return filteredList;

    }

    List<Teacher> filterTeachers(List<String> nameFilterList) {
        List<Teacher> filteredList;
        List<Teacher> originalList = getRepoTeacher().getTeacherList();
        Set<String> nameFilterSet = new HashSet<>(nameFilterList);
        filteredList = originalList.stream().filter(teacher -> nameFilterSet.contains(teacher.getFirstName()))
                .collect(Collectors.toList());
        return filteredList;
    }

    boolean findCourse(long id) {
        if (courseFileRepository.findOne(id) == null) {
            return false;
        } else {
            System.out.println(courseFileRepository.findOne(id).toString());
            return true;
        }
    }

    boolean findStudent(long id) {
        if (studentFileRepository.findOne(id) == null) {
            return false;
        } else {
            System.out.println(studentFileRepository.findOne(id).toString());
            return true;
        }
    }

    boolean findTeacher(long id) {
        if (teacherFileRepository.findOne(id) == null) {
            return false;
        } else {
            System.out.println(teacherFileRepository.findOne(id).toString());
            return true;
        }
    }

    boolean readAllFromCourseFile() {
        Iterable<Course> courseList = courseFileRepository.findAll();
        if (courseList != null) {
            courseList.forEach(course ->{
                System.out.println(course.toString());
            });
            return true;
        } else {
            return false;
        }
    }

    boolean readAllFromStudentFile() {
        Iterable<Student> studentList = studentFileRepository.findAll();
        if (studentList != null) {
            studentList.forEach(student ->{
                System.out.println(student.toString());
            });
            return true;
        } else {
            return false;
        }
    }

    boolean readAllFromTeacherFile() {
        Iterable<Teacher> teacherList = teacherFileRepository.findAll();
        if (teacherList != null) {
            teacherList.forEach(teacher ->{
                System.out.println(teacher.toString());
            });
            return true;
        } else {
            return false;
        }
    }

    void saveCourseInFile(Course course) throws IOException {
        courseFileRepository.save(course);
    }

    void saveStudentInFile(Student student) throws IOException {
        studentFileRepository.save(student);

    }

    void saveTeacherInFile(Teacher teacher) throws IOException {
        teacherFileRepository.save(teacher);
    }

    boolean deleteCourseFromFile(long id) throws IOException {
        Course entity = courseFileRepository.delete(id);
        return entity != null;
    }

    boolean deleteStudentFromFile(long id) throws IOException {
        Student entity = studentFileRepository.delete(id);
        return entity != null;
    }

    boolean deleteTeacherFromFile(long id) throws IOException {
        Teacher entity = teacherFileRepository.delete(id);
        return entity != null;
    }

    void updateCourseInFile(Course course) throws IOException {
        courseFileRepository.update(course);
    }

    void updateStudentInFile(Student student) throws IOException {
        studentFileRepository.update(student);
    }

    void updateTeacherInFile(Teacher teacher) throws IOException {
        teacherFileRepository.update(teacher);
    }
}
