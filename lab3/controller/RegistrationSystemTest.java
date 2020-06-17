package lab3.controller;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class RegistrationSystemTest {
    private RegistrationSystem registrationSystem = new RegistrationSystem();

    /**
     * Testing the functionality of the controller
     */
    @Test
    public void register() {
        try{
        registrationSystem.addTeacher(32, "Henry", "Ford");
        registrationSystem.addCourse(1, "English",32, 25, 5);
        registrationSystem.addStudent(1,"John", "Ion");
        registrationSystem.register(1, 1);
        }catch(Exception e){
            e.printStackTrace();
        }
        Course course = registrationSystem.getRepoCourse().getCourse(1);
        Student student = registrationSystem.getRepoStudent().getStudent(1);
        assert(student.getEnrolledCourses().size() == 1);
        assert(course.getStudentsEnrolled().size() == 1);
    }
    @Test
    public void retrieveCoursesWithFreePlaces() {
        try {
            registrationSystem.addTeacher(1, "Michael", "Jackson");
            registrationSystem.addCourse(1, "English", 1, 25, 5);
            registrationSystem.addCourse(2, "Math", 1, 30, 6);
            List<Course> courses = registrationSystem.retrieveCoursesWithFreePlaces();
            assert (courses.size() == 2);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void retrieveStudentsEnrolledForACourse() {
        try{
        registrationSystem.addTeacher(32, "Henry", "Ford");
        registrationSystem.addCourse(1, "English",32, 25, 5);
        registrationSystem.addStudent(1,"John", "Ion");
        registrationSystem.register(1, 1);
        }catch(Exception e){
            e.printStackTrace();
        }
        Course course = registrationSystem.getRepoCourse().getCourse(1);
        List<Student> studentsEnrolled = registrationSystem.retrieveStudentsEnrolledForACourse(course);
        assert(studentsEnrolled.size() == 1);

    }
    @Test
    public void getAllCourses(){
        List<Course> courses = registrationSystem.getAllCourses();
        assert(courses.size() == 0);

    }
    @Test
    public void checkCredits()  {
        try {
            registrationSystem.addStudent(1, "John", "Henry");
            Student student = registrationSystem.getRepoStudent().getStudent(1);
            assert (registrationSystem.checkCredits(student, 5));
        }catch(Exception e){
            e.printStackTrace();
            }
    }
    @Test
    public void addCourse()  {
        try {
            registrationSystem.addTeacher(32, "Henry", "Ford");
            registrationSystem.addCourse(11, "Math", 32, 30, 5);
            assert (registrationSystem.getRepoCourse().getCourseList().size() == 1);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void addStudent()  {
        try {
            registrationSystem.addStudent(22, "Michael", "Jackson");
            assert (registrationSystem.getRepoStudent().getStudentList().size() == 1);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void addTeacher()  {
        try {
            registrationSystem.addTeacher(32, "Henry", "Ford");
            assert (registrationSystem.getRepoTeacher().getTeacherList().size() == 1);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void deleteCourse() {
        try {
            registrationSystem.addCourse(1, "English", 21, 30, 5);
            registrationSystem.deleteCourse(1);
            assert (registrationSystem.getRepoCourse().getCourseList().size() == 0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void deleteStudent() {
        try {
            registrationSystem.addStudent(22, "Michael", "Jackson");
            registrationSystem.deleteStudent(22);
            assert (registrationSystem.getRepoStudent().getStudentList().size() == 0);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void deleteTeacher() {
        try {
            registrationSystem.addTeacher(32, "Henry", "Ford");
            registrationSystem.deleteTeacher(32);
            assert (registrationSystem.getRepoTeacher().getTeacherList().size() == 0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void filterStudentsByCredits(){
        List<Student> filteredList;
        List<Student> originalList = Arrays.asList(new Student(1, "John", "Michael", 20),
                new Student(2, "Henry", "Ford", 10),
                new Student(3, "Michael", "Mentz", 30));
        registrationSystem.getRepoStudent().setStudentList(originalList);
        filteredList = registrationSystem.filterStudentsWithAtLeastXNumberOfCredits(15);
        assert(filteredList.size() == 2);
    }
    @Test
    public void filterCoursesByFreePlaces(){
        List<Course> filteredList;
        try{
        registrationSystem.addTeacher(1, "Michael", "Franz");
        registrationSystem.addStudent(1, "Henry", "Ford");
        registrationSystem.addCourse(1, "English", 1, 30, 5);
        registrationSystem.addCourse(2, "Math", 1, 30, 6);
        registrationSystem.addStudent(2, "John", "Ion");
        registrationSystem.addStudent(3, "Elon", "Musk");
        registrationSystem.register(1, 1);
        registrationSystem.register(2, 2);
        registrationSystem.register(2, 3);
        }catch(Exception e){
            e.printStackTrace();
        }
        filteredList = registrationSystem.filterCoursesWithAtLeastXNumberOfStudentsEnrolled(1);
        assert(filteredList.size() == 2);
    }
    @Test
    public void filterStudentsByName(){
        List<Student> filteredList;
        List<Student> originalList = Arrays.asList(new Student(1, "John", "Michael", 20),
                new Student(2, "Henry", "Ford", 10),
                new Student(3, "Michael", "Mentz", 30));
        registrationSystem.getRepoStudent().setStudentList(originalList);
        List<String> namesList = Arrays.asList("Henry", "John");
        filteredList = registrationSystem.filterStudents(namesList);
        assert(filteredList.size() == 2);
    }
    @Test
    public void filterCoursesByName(){
        List<Course> filteredList;
        List<Course> originalList = Arrays.asList(new Course(1, "English", 30, 6),
                new Course(2, "Math", 30, 5),
                new Course(3, "Logic", 30, 4));
        registrationSystem.getRepoCourse().setCourseList(originalList);
        List<String> namesList = Arrays.asList("Math", "English");
        filteredList = registrationSystem.filterCourses(namesList);
        assert(filteredList.size() == 2);
    }
    @Test
    public void filterTeachersByName(){
        List<Teacher> filteredList;
        List<Teacher> originalList = Arrays.asList(new Teacher(1, "John", "Michael"),
                new Teacher(2, "Henry", "Ford"),
                new Teacher(3, "Elon", "Musk"));
        registrationSystem.getRepoTeacher().setTeacherList(originalList);
        List<String> namesList = Arrays.asList("Elon", "Henry");
        filteredList = registrationSystem.filterTeachers(namesList);
        assert(filteredList.size() == 2);
    }
    //Tests for file methods
    @Test
    public void findCourse(){
        try {
            registrationSystem.saveCourseInFile(new Course(2, "English", 30, 6));
            assert (registrationSystem.findCourse(2));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void findStudent(){
        try {
            registrationSystem.saveStudentInFile(new Student(6, "John", "Michael", 20));
            assert (registrationSystem.findStudent(6));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void findTeacher(){
        try {
            registrationSystem.saveTeacherInFile(new Teacher(3, "John", "Michael"));
            assert (registrationSystem.findTeacher(3));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCourseFromFile(){
        try {
            registrationSystem.saveCourseInFile(new Course(4, "English", 30, 6));
            assert (registrationSystem.deleteCourseFromFile(4));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void deleteStudentFromFile(){
        try {
            registrationSystem.saveStudentInFile(new Student(6, "John", "Michael", 20));
            assert (registrationSystem.deleteStudentFromFile(6));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void deleteTeacherFromFile() {
        try {
            registrationSystem.saveTeacherInFile(new Teacher(4, "John", "Michael"));
            assert (registrationSystem.deleteTeacherFromFile(4));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void updateCourseInFile(){
        try {
            registrationSystem.saveCourseInFile(new Course(4, "Ceva", 30, 6));
            Course course = new Course(4, "altceva", 25, 5);
            registrationSystem.updateCourseInFile(course);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void updateStudentInFile(){
        try {
            registrationSystem.saveStudentInFile(new Student(6, "John", "Michael", 20));
            Student student = new Student(6, "John", "Michael", 15);
            registrationSystem.updateStudentInFile(student);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void updateTeacherInFile(){
        try {
            registrationSystem.saveTeacherInFile(new Teacher(5, "John", "Michael"));
            Teacher teacher = new Teacher(5, "Alexandra", "Bursi");
            registrationSystem.updateTeacherInFile(teacher);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
