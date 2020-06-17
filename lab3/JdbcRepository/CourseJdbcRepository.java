package lab3.JdbcRepository;

import lab3.controller.JDBCUtil;
import lab3.model.Course;
import lab3.model.Student;
import lab3.repository.ICrudRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CourseJdbcRepository implements ICrudRepository<Course> {
    private JDBCUtil jdbcUtil;
    private List<Course> courseList = new ArrayList<>();

    public CourseJdbcRepository() {
        jdbcUtil = new JDBCUtil();
    }

    public List<Course> getCourseList() {
        return this.courseList;
    }

    public void setCourseList(List<Course> newCourseList) {
        this.courseList = newCourseList;
    }
    public void setCourse(Course course){
        if(courseList.stream().anyMatch(course1 -> course1.getId() == course.getId())){
            courseList.stream().filter(course1 -> course.getId() == course.getId())
                    .forEach(course1 -> course1 = course);
        }
    }
    ;

    public Course getCourse(long id) {
        if(courseList.stream().anyMatch(course -> course.getId() == id)) {
            return courseList.stream().filter(course -> course.getId() == id).findAny().get();
        }
        return null;
    }

    public void loadAllCourses() {
        try {
            PreparedStatement preparedStatement = jdbcUtil
                    .getConnection().prepareStatement("select * from course");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setCredits(resultSet.getInt("credits"));
                course.setMaxEnrollment(resultSet.getInt("maxenrollment"));
                if(courseList.stream().noneMatch(course1 -> course1.getId() == course.getId())) {
                    courseList.add(course);
                }
            }
        } catch (Exception e) {
            courseList = null;
            e.getMessage();
        }
    }

    public void loadStudentsForEachCourse() {
        try {
            PreparedStatement preparedStatement = jdbcUtil
                    .getConnection().prepareStatement("select * from registercs");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> listCourses = new ArrayList<>();
            List<Integer> listStudents = new ArrayList<>();
            while (resultSet.next()) {
                listCourses.add(resultSet.getInt("courseid"));
                listStudents.add(resultSet.getInt("studentid"));
            }
            courseList.forEach(course ->{
                AtomicInteger finalIndex = new AtomicInteger(1);
                listCourses.forEach(courseId ->{
                    if (course.getId() == courseId) {
                        course.addStudent(new Student(listStudents.get(finalIndex.getAndIncrement())));
                    }
                });
            });
        } catch (SQLException se) {
            se.getMessage();
        }
    }

    public void saveAllCourses() {
        String query = "Insert into course(id, name, maxenrollment, credits) values (?, ?, ?, ?)";
        try {
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            for (Course course : courseList) {
                statement.setInt(1, (int) course.getId());
                statement.setString(2, course.getName());
                statement.setInt(3, course.getMaxEnrollment());
                statement.setInt(4, course.getCredits());
                return;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public Course findOne(long id) {
        if(courseList.stream().anyMatch(course -> course.getId() == id)) {
            return courseList.stream().filter(course -> course.getId() == id).findAny().get();
        }
        return null;
    }
    public Course findOne(String name) {
        if(courseList.stream().anyMatch(course -> course.getName().equals(name))) {
            return courseList.stream().filter(course -> course.getName().equals(name)).findAny().get();
        }
        return null;
    }

    @Override
    public Iterable<Course> findAll() {
        loadAllCourses();
        if (courseList.isEmpty()) {
            return null;
        }
        return courseList;
    }

    @Override
    public Course save(Course course) {
        String query = "Insert into course(id, name, maxenrollment, credits) values (?, ?, ?, ?)";
        try {
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            statement.setInt(1, (int) course.getId());
            statement.setString(2, course.getName());
            statement.setInt(3, course.getMaxEnrollment());
            statement.setInt(4, course.getCredits());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                courseList.add(course);
                return null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return course;
    }

    @Override
    public Course delete(long id) {
        Course course = getCourse((int) id);
        String query = "Delete from course where id=?";
        try {
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            statement.setInt(1, (int) id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0)
                courseList.remove(course);
                return course;

        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    @Override
    public Course update(Course course) {
        String query = "Update course set name=?, maxenrollment=?, credits=? where id=?";
        try {
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            statement.setInt(4, (int) course.getId());
            statement.setString(1, course.getName());
            statement.setInt(2, course.getMaxEnrollment());
            statement.setInt(3, course.getCredits());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                setCourse(course);
                return null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return course;
    }
}
