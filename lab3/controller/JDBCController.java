package lab3.controller;

import lab3.JdbcRepository.CourseJdbcRepository;
import lab3.JdbcRepository.StudentJdbcRepository;
import lab3.JdbcRepository.TeacherJdbcRepository;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;

import java.util.ArrayList;

public class JDBCController {
    private CourseJdbcRepository courseJdbcRepository = new CourseJdbcRepository();
    private StudentJdbcRepository studentJdbcRepository = new StudentJdbcRepository();
    private TeacherJdbcRepository teacherJdbcRepository = new TeacherJdbcRepository();

    public CourseJdbcRepository getCJdbc(){return this.courseJdbcRepository;};
    public StudentJdbcRepository getSJdbc(){return this.studentJdbcRepository;};
    public TeacherJdbcRepository getTJdbc(){return this.teacherJdbcRepository;};
    private void loadAll() {
        teacherJdbcRepository.loadAllTeachers();
        courseJdbcRepository.loadAllCourses();
        studentJdbcRepository.loadAllStudents();
        courseJdbcRepository.loadStudentsForEachCourse();
        studentJdbcRepository.loadCoursesForEachStudent();
        teacherJdbcRepository.loadCoursesForEachTeacher();
    }

    private void registerStudentsInCourses() {
        courseJdbcRepository.getCourseList().forEach(course -> {
            course.getStudentsEnrolled().forEach(student -> {
                float id = student.getStudentID();
                if(studentJdbcRepository.getStudentList().stream().anyMatch(student1 -> student1.getStudentID() == id)) {
                    Student student2 = studentJdbcRepository.getStudentList().stream().
                            filter(student1 -> student1.getStudentID() == id).findAny().get();
                    student.setFirstName(student2.getFirstName());
                    student.setLastName(student2.getLastName());
                    student.setTotalCredits(student2.getTotalCredits());
                }
            });
        });
    }

    private void registerCoursesInStudents() {
        studentJdbcRepository.getStudentList().forEach(student -> {
            student.getEnrolledCourses().forEach(course -> {
                float id = course.getId();
                if(courseJdbcRepository.getCourseList().stream().anyMatch(course1 -> course1.getId() == id)) {
                    Course course2 = courseJdbcRepository.getCourseList().stream().
                            filter(course1 -> course1.getId() == id).findAny().get();
                    course.setName(course2.getName());
                    course.setCredits(course2.getCredits());
                    course.setMaxEnrollment(course2.getMaxEnrollment());
                }
            });
        });
    }

    private void registerCoursesInTeachers() {
        teacherJdbcRepository.getTeacherList().forEach(teacher -> {
            teacher.getCourses().forEach(course -> {
                float id = course.getId();
                if(courseJdbcRepository.getCourseList().stream().anyMatch(course1 -> course1.getId() == id)) {
                    Course course2 = courseJdbcRepository.getCourseList().stream().
                            filter(course1 -> course1.getId() == id).findAny().get();
                    course.setName(course2.getName());
                    course.setCredits(course2.getCredits());
                    course.setMaxEnrollment(course2.getMaxEnrollment());
                }
            });
        });
    }

    public void load() {
        loadAll();
        registerCoursesInStudents();
        registerStudentsInCourses();
        registerCoursesInTeachers();
    }
    public ArrayList<String> getCourseNames(){
        ArrayList<String> courseNames = new ArrayList<>();
        courseJdbcRepository.getCourseList().forEach(course -> courseNames.add(course.getName()));
        return courseNames;
    }

    Student findStudent(long id){
        return studentJdbcRepository.findOne(id);
    }
    Student findStudent(String name){
        return studentJdbcRepository.findOne(name);
    }
    Course findCourse(long id){
        return courseJdbcRepository.findOne(id);
    }
    Course findCourse(String name){
        return courseJdbcRepository.findOne(name);
    }
    Teacher findTeacher(long id) {
        return teacherJdbcRepository.findOne(id);
    }
    Teacher findTeacher(String name){
        return teacherJdbcRepository.findOne(name);
    }
    Student updateStudent(Student student) {
        return studentJdbcRepository.update(student);
    }

    Course updateCourse(Course course) {
        return courseJdbcRepository.update(course);
    }

    Teacher updateTeacher(Teacher teacher) {
        return teacherJdbcRepository.update(teacher);
    }

    Student deleteStudent(long id) {
        return studentJdbcRepository.delete(id);
    }

    Course deleteCourse(long id) {
        return courseJdbcRepository.delete(id);
    }

    Teacher deleteTeacher(long id) {
        return teacherJdbcRepository.delete(id);
    }

    Course insertCourse(Course course){
        return courseJdbcRepository.save(course);
    }

    Student insertStudent(Student student){
        return studentJdbcRepository.save(student);
    }

    Teacher insertTeacher(Teacher teacher){
        return teacherJdbcRepository.save(teacher);
    }
    void saveAllCourses(){
        courseJdbcRepository.saveAllCourses();
    }
    void saveAllStudents(){
        studentJdbcRepository.registerCourses();
        studentJdbcRepository.saveAllStudents();
    }
    void saveAllTeachers() {
        teacherJdbcRepository.registerCourses();
        teacherJdbcRepository.saveAllTeachers();
    }
}
