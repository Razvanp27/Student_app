package lab3.JdbcRepository;

import lab3.controller.JDBCUtil;
import lab3.model.Course;
import lab3.model.Teacher;
import lab3.repository.ICrudRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TeacherJdbcRepository implements ICrudRepository<Teacher> {
    private JDBCUtil jdbcUtil;
    private List<Teacher> teacherList = new ArrayList<>();

    public TeacherJdbcRepository(){jdbcUtil = new JDBCUtil();};
    public void setTeacher(Teacher teacher){
        if(teacherList.stream().anyMatch(teacher1 -> teacher1.getId() == teacher.getId())){
            teacherList.stream().filter(teacher1 -> teacher1.getId() == teacher.getId())
                    .forEach(teacher1 -> teacher1 = teacher);
        }
    }

    public List<Teacher> getTeacherList(){
        return this.teacherList;
    }
    public void setTeacherList(List<Teacher> newTeacherList){this.teacherList = newTeacherList;};
    public Teacher getTeacher(long id){
        if(teacherList.stream().anyMatch(teacher -> teacher.getId() == id)) {
            return teacherList.stream().filter(teacher -> teacher.getId() == id).findAny().get();
        }
        return null;
    }

    public void loadAllTeachers(){
        try {
            PreparedStatement preparedStatement = jdbcUtil
                    .getConnection().prepareStatement("select * from teacher");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstname"));
                teacher.setLastName(resultSet.getString("lastname"));
                if(teacherList.stream().noneMatch(teacher1 -> teacher1.getId() == teacher.getId())) {
                    teacherList.add(teacher);
                }
            }
        }catch(Exception e){
            teacherList = null;
            e.getMessage();
        }
    }
    public void loadCoursesForEachTeacher(){
        try{
            PreparedStatement preparedStatement = jdbcUtil
                    .getConnection().prepareStatement("Select * from registertc");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> listTeachers = new ArrayList<>();
            List<Integer> listCourses = new ArrayList<>();
            while(resultSet.next()) {
                listTeachers.add(resultSet.getInt("teacherid"));
                listCourses.add(resultSet.getInt("courseid"));
            }
            teacherList.forEach(teacher -> {
                AtomicInteger finalIndex = new AtomicInteger(1);
                listTeachers.forEach(teacherId -> {
                    if (teacherId == teacher.getId()) {
                        teacher.addCourse(new Course(listCourses.get(finalIndex.getAndIncrement())));
                    }
                });
            });
        }catch(SQLException e){
            e.getMessage();
        }
    }
    public void saveAllTeachers(){
        String query = "Insert into teacher(id, firstname, lastname) values (?, ?, ?)";
        teacherList.forEach(teacher ->{
            try{
                PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
                statement.setInt(1, (int) teacher.getId());
                statement.setString(2, teacher.getFirstName());
                statement.setString(3, teacher.getLastName());
            }catch (SQLException se) {
                se.printStackTrace();
            }
        });
    }

    @Override
    public Teacher findOne(long id) {
        if(teacherList.stream().anyMatch(teacher -> teacher.getId() == id)) {
            return teacherList.stream().filter(teacher -> teacher.getId() == id).findAny().get();
        }
        return null;
    }
    public Teacher findOne(String name) {
        if(teacherList.stream().anyMatch(teacher -> teacher.getFirstName().equals(name))) {
            return teacherList.stream().filter(teacher -> teacher.getFirstName().equals(name)).findAny().get();
        }
        return null;
    }

    @Override
    public Iterable<Teacher> findAll() {
        loadAllTeachers();
        if(teacherList.isEmpty()){
            return null;
        }
        return teacherList;
    }
    @Override
    public Teacher save(Teacher teacher){
        String query = "Insert into teacher(id, firstname, lastname) values (?, ?, ?)";
        try {
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            statement.setInt(1, (int) teacher.getId());
            statement.setString(2, teacher.getFirstName());
            statement.setString(3, teacher.getLastName());
            int rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0){
                teacherList.add(teacher);
                return null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return teacher;
    }
    @Override
    public Teacher delete(long id){
        Teacher teacher = getTeacher((int)id);
        String query = "Delete from teacher where id=?";
        try{
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            statement.setInt(1, (int)id);
            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0){
                teacherList.remove(teacher);
                return teacher;
            }
        }catch(SQLException se){
            se.printStackTrace();
        }
        return null;
    }
    @Override
    public Teacher update(Teacher teacher){
        String query = "Update teacher set firstname=?, lastname=? where id=?";
        try{
            PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
            statement.setInt(3, (int)teacher.getId());
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
                setTeacher(teacher);
                return null;
            }
        }catch(SQLException se){
            se.printStackTrace();
        }
        return teacher;
    }

    public void registerCourses(){
        String query = "Insert into registertc(registerid, teacherid, courseid)  values (?, ?, ?)";
        List<Teacher> teacherListFiltered = teacherList.stream()
                .filter(teacher -> !teacher.getCourses().isEmpty())
                .collect(Collectors.toList());
        AtomicInteger finalIndex = new AtomicInteger(1);
        teacherListFiltered.forEach((teacher -> teacher.getCourses().forEach(course-> {
            try {
                PreparedStatement statement = jdbcUtil.getConnection().prepareStatement(query);
                statement.setInt(1, finalIndex.getAndIncrement());
                statement.setInt(2, (int) teacher.getId());
                statement.setInt(3, (int) course.getId());
            } catch (SQLException se) {
                se.printStackTrace();
            }
        })));
    }
}


