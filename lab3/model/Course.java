package lab3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Course implements Observer<Student>{
    private long id;
    private String name;
    private Teacher Teacher;
    private Integer maxEnrollment;
    private List<Student> studentsEnrolled;
    private int credits;
    public Course(long id, String name, Teacher Teacher, int maxEnrollment, List<Student> studentsEnrolled, int credits){
        this.id = id;
        this.name = name;
        this.Teacher = Teacher;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = studentsEnrolled;
        this.credits = credits;
    }
    public Course(long id, String name, int maxEnrollment, int credits){
        this.id = id;
        this.name = name;
        this.maxEnrollment = maxEnrollment;
        this.credits = credits;
        this.Teacher = null;
        this.studentsEnrolled = new ArrayList<>();
    }
    public Course(long id){
        this.id = id;
        this.studentsEnrolled = new ArrayList<>();
    }
    public Course(){
        this.studentsEnrolled = new ArrayList<>();
    }

    /**
     * getters for the class Course
     * @return the id, the name, the Teacher who teach the Course, the max number of Students who can enroll
     * the list of Students which participate at the Course and the number of credits.
     */
    public long getId(){return id;}
    public String getName(){return name;};
    public Teacher getTeacher(){return Teacher;};
    public int getMaxEnrollment(){return maxEnrollment;};
    public List<Student> getStudentsEnrolled(){return studentsEnrolled;};
    public int getCredits(){return credits;};

    /**
     * setters for the class Course
     * @param id name, teacher, maxEnrollment, the students Enrolled and the credits to set or update the Course
     */
    public void setId(long id){this.id = id;};
    public void setName(String newName){this.name = newName;};
    public void setTeacher(Teacher newTeacher){this.Teacher = newTeacher;};
    public void setMaxEnrollment(int newMaxEnrollment){this.maxEnrollment = newMaxEnrollment;};
    public void setStudentsEnrolled(List<Student> newStudentsEnrolled){this.studentsEnrolled = newStudentsEnrolled;};
    public void setCredits(int newCredits){this.credits = newCredits;};
    public void addStudent(Student newStudent){studentsEnrolled.add(newStudent);};
    public void removeStudent(Student oldStudent){studentsEnrolled.remove(oldStudent);};
    public void removeAllStudents(){this.studentsEnrolled = null;};

    public String toString(){
        return " [CourseID: " + id + ", Name: " + name +
                ", MaxEnrollment: " + maxEnrollment + ", Credits:" + credits + "]";
    }
    public static Comparator<Course> CourseNameComparator = new Comparator<Course>(){
        public int compare(Course c1, Course c2){
            String courseName1 = c1.getName().toUpperCase();
            String courseName2 = c2.getName().toLowerCase();
            //for Ascending order
            return courseName1.compareTo(courseName2);
            //for Descending order
            //return courseName2.compareTo(courseName1);
        }
    };
    public static Comparator<Course> courseCreditsComparator = new Comparator<Course>() {
        public int compare(Course c1, Course c2) {
            int courseCredits1 = c1.getCredits();
            int courseCredits2 = c2.getCredits();
            //for Ascending order
            return courseCredits1 - courseCredits2;
            //for Descending order
            //return courseCredits2 - courseCredits1;
        }
    };
    public static Comparator<Course> courseEnrolledStudentsNumber = new Comparator<Course>() {
        public int compare(Course c1, Course c2) {
            int courseSize1 = c1.getStudentsEnrolled().size();
            int courseSize2 = c2.getStudentsEnrolled().size();
            //for Ascending order
            return courseSize1 - courseSize2;
            //for Descending order
            //return courseSize2 - courseSize1;
        }
    };

    @Override
    public void notify(Student value) {
        if(getTeacher() != null){
            getTeacher().notify(this);
        }
    }
}
