package lab3.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Student extends Subject<Student> {
    private long studentID;
    private int totalCredits;
    private List<Course> enrolledCourses;
    public Student(String firstName, String lastName, long studentID, int totalCredits, List<Course> enrolledCourses){
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.totalCredits = totalCredits;
        this.enrolledCourses = enrolledCourses;
    }
    public Student(long studentID, String firstName, String lastName, int totalCredits){
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalCredits = totalCredits;
        this.enrolledCourses = new ArrayList<>();
    }
    public Student(long studentID){
        this.studentID = studentID;
        this.enrolledCourses = new ArrayList<>();

    }
    public Student(){
        this.enrolledCourses = new ArrayList<>();

    };

    /**
     * getters for the class Course
     * @return the id, the number of credits and the list of courses in which the Student is enrolled.
     */
    public long getStudentID(){return studentID;};
    public int getTotalCredits(){return totalCredits;};
    public List<Course> getEnrolledCourses(){return enrolledCourses;};
    /**
     * setters for the class Student
     * @param newStudentID, the student id and the total number of credits and the list of courses to
     *                      set or update the Student details.
     */
    public void setStudentID(long newStudentID){this.studentID = newStudentID;};
    public void setTotalCredits(int newTotalCredits){this.totalCredits = newTotalCredits;};
    public void setEnrolledCourses(List<Course> newEnrolledCourses){this.enrolledCourses = newEnrolledCourses;};

    public String toString(){
        return "[ StudentID: " + studentID  + ", First Name: " + firstName +
                ", Last Name: " + lastName + ", totalCredits: " + totalCredits + "]";
    }

    public static Comparator<Student> studentFirstNameComparator = new Comparator<Student>(){
        public int compare(Student s1, Student s2){
            String studentName1 = s1.getFirstName().toUpperCase();
            String studentName2 = s2.getFirstName().toLowerCase();
            //for Ascending order
            return studentName1.compareTo(studentName2);
            //for Descending order
            //return studentName2.compareTo(studentName1);
        }
    };
    public static Comparator<Student> studentCreditsComparator = new Comparator<Student>() {
        public int compare(Student s1, Student s2) {
            int studentCredits1 = s1.getTotalCredits();
            int studentCredits2 = s2.getTotalCredits();
            //for Ascending order
            return studentCredits1 - studentCredits2;
            //for Descending order
            //return studentCredits2 - studentCredits1;
        }
    };
    public static Comparator<Student> studentEnrolledCoursesNumber = new Comparator<Student>() {
        public int compare(Student s1, Student s2) {
            int studentSize1 = s1.getEnrolledCourses().size();
            int studentSize2 = s2.getEnrolledCourses().size();
            //for Ascending order
            return studentSize1 - studentSize2;
            //for Descending order
            //return studentSize2 - studentSize1;
        }
    };
    public void addCourse(Course newCourse){
        registerObserver(newCourse);
        enrolledCourses.add(newCourse);
        notifyObservers(this);

    };
    public void removeCourse(Course oldCourse){
        enrolledCourses.remove(oldCourse);
        notifyObservers(this);
        unregisterObserver(oldCourse);
    };

}
