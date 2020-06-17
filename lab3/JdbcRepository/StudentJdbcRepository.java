package lab3.JdbcRepository;

import lab3.controller.JDBCUtil;
import lab3.model.Course;
import lab3.model.Student;
import lab3.repository.ICrudRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StudentJdbcRepository implements ICrudRepository<Student> {
    private JDBCUtil jdbcUtil;
    private List<Student> studentList = new ArrayList<>();

    public StudentJdbcRepository() {
        jdbcUtil = new JDBCUtil();
    }

    public void setStudent(Student student){
        if(studentList.stream().anyMatch(student1 -> student1.getStudentID() == student.getStudentID())){
            studentList.stream().filter(student1 -> student1.getStudentID() == student.getStudentID())
                    .forEach(student1 -> student1 = student);
        }
    }

    public List<Student> getStudentList() {
        return this.studentList;
    }
    public void setStudentList(List<Student> newStudentList){this.studentList = newStudentList;};
    public Student getStudent(long id) {
        if(studentList.stream().anyMatch(student -> student.getStudentID() == id)) {
            return studentList.stream().filter(student -> student.getStudentID() == id).findAny().get();
        }
        return null;
    }

    public void loadAllStudents() {
        try {
            PreparedStatement preparedStatement = jdbcUtil
                    .getConnection().prepareStatement("select * from student");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentID(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstname"));
                student.setLastName(resultSet.getString("lastname"));
                student.setTotalCredits(resultSet.getInt("totalcredits"));
                if(studentList.stream().noneMatch(student1 -> student1.getStudentID() == student.getStudentID())) {
                    studentList.add(student);
                }
            }
        } catch (Exception e) {
            studentList = null;
            e.getMessage();
        }
    }

    public void loadCoursesForEachStudent() {
        try {
            PreparedStatement preparedStatement = jdbcUtil
                    .getConnection().prepareStatement("select * from registercs");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> listStudents = new ArrayList<>();
            List<Integer> listCourses = new ArrayList<>();
            while (resultSet.next()) {
                listStudents.add(resultSet.getInt("studentid"));
                listCourses.add(resultSet.getInt("courseid"));
            }
            studentList.forEach(student -> {
                AtomicInteger finalIndex =  new AtomicInteger(1);
                listStudents.forEach(studentId ->{
                    if(student.getStudentID() == studentId){
                        student.addCourse(new Course(listCourses.get(finalIndex.getAndIncrement())));
                    }
                });
            });
        } catch (SQLException se) {
            se.getMessage();
        }
    }

    public void saveAllStudents() {
        String query = "Insert into student(id, firstname, lastname, totalcredits) values (?, ?, ?, ?)";
        studentList.forEach(student -> {
            try {
                PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
                statement.setInt(1, (int) student.getStudentID());
                statement.setString(2, student.getFirstName());
                statement.setString(3, student.getLastName());
                statement.setInt(4, student.getTotalCredits());
            } catch (SQLException se) {
                se.printStackTrace();
            }
        });
    }

    @Override
    public Student findOne(long id) {
        if(studentList.stream().anyMatch(student -> student.getStudentID() == id)) {
            return studentList.stream().filter(student -> student.getStudentID() == id).findAny().get();
        }
        return null;
    }
    public Student findOne(String name) {
        if(studentList.stream().anyMatch(student -> student.getFirstName().equals(name))) {
            return studentList.stream().filter(student -> student.getFirstName().equals(name)).findAny().get();
        }
        return null;
    }

    @Override
    public Iterable<Student> findAll() {
        loadAllStudents();
        if (studentList.isEmpty()) {
            return null;
        }
        return studentList;
    }

    @Override
    public Student save(Student student) {
        String query = "Insert into student(id, firstname, lastname, totalcredits) values (?, ?, ?, ?)";
        try {
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            statement.setInt(1, (int) student.getStudentID());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getTotalCredits());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                studentList.add(student);
                return null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return student;
    }

    @Override
    public Student delete(long id) {
        Student student = getStudent((int) id);
        String query = "Delete from student where id=?";
        try {
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            statement.setInt(1, (int) id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                studentList.remove(student);
                return student;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    @Override
    public Student update(Student student) {
        String query = "Update student set firstname=?, lastname=?, totalcredits=? where id=?";
        try {
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            statement.setInt(4, (int) student.getStudentID());
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getTotalCredits());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                setStudent(student);
                return null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return student;
    }

    public void registerCourses() {
        String query = "Insert into registercs(registerid, studentid, courseid)  values (?, ?, ?)";
        List<Student> studentListFiltered = studentList.stream()
                .filter(student -> !student.getEnrolledCourses().isEmpty())
                .collect(Collectors.toList());
        AtomicInteger finalIndex = new AtomicInteger(1);
        studentListFiltered.forEach(student -> student.getEnrolledCourses().forEach(course -> {
            try {
                PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
                statement.setInt(1, finalIndex.getAndIncrement());
                statement.setInt(2, (int) student.getStudentID());
                statement.setInt(3, (int) course.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
    }
}
