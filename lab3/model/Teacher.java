package lab3.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person implements Observer<Course> {
    private long id;
    private List<Course> courses;
    private ObservableList<String> students = FXCollections.observableArrayList();
    private ObservableList<String> courseNames = FXCollections.observableArrayList();

    public Teacher(long id, String firstName, String lastName, List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
        courses.forEach(course -> courseNames.add(course.getName()));
    }

    public Teacher(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = new ArrayList<>();
    }

    public Teacher(long id) {
        this.id = id;
        this.courses = new ArrayList<>();
    }

    public Teacher() {
        this.courses = new ArrayList<>();
    }

    ;

    /**
     * getters for the class Teacher
     *
     * @return the id and the courses which are taught by the given Teacher.
     */
    public long getId() {
        return id;
    }

    ;

    public List<Course> getCourses() {
        return courses;
    }

    ;

    public ObservableList<String> getStudents() {
        return students;
    }

    ;

    public ObservableList<String> getCourseNames() {
        return courseNames;
    }

    ;

    /**
     * setters for the class Teacher
     *
     * @param id ID and the list which are taught by the Teacher, to set or update the Teacher details.
     */
    public void setId(long id) {
        this.id = id;
    }

    ;

    public void setCourses(List<Course> newCourses) {
        this.courses = newCourses;
        courses.forEach(course -> courseNames.add(course.getName()));
    }

    ;

    public void setStudents(ObservableList<String> students) {
        this.students = students;
    }

    ;

    public void addCourse(Course newCourse) {
        newCourse.setTeacher(this);
        this.courses.add(newCourse);
        courses.forEach(course -> courseNames.add(course.getName()));

    }

    ;

    public void removeCourse(Course oldCourse) {
        this.courses.remove(oldCourse);
        oldCourse.setTeacher(null);
        courses.forEach(course -> courseNames.add(course.getName()));
    }

    ;

    public ObservableList<String> getStudentsFromCourse(Course course) {
        if (this.courses.contains(course)) {
            students.removeAll();
            course.getStudentsEnrolled().forEach(student -> students.add(student.getFirstName() + " " + student.getLastName()));
        }
        return students;
    }

    public String toString() {
        return "[ TeacherId: " + id + ", First Name: " + firstName +
                ", Last Name: " + lastName + ", Courses: " + courses + "]";
    }

    @Override
    public void notify(Course value) {
        System.out.println("New Student registered for course " + value.getName()
                + " teach by " + this.getFirstName() + " " + this.getLastName());
        getStudentsFromCourse(value);
    }
}
