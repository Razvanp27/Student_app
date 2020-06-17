package lab3.repository.Test;

import lab3.model.Course;
import lab3.model.Student;
import lab3.repository.RepoStudent;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepoStudentTest {
    private RepoStudent repoStudent = new RepoStudent();
    /**
     * testing the functionality of the Repository
     */
    @Test
    public void testAddStudent(){
        Student student = null;
        repoStudent.addStudent(student);
        assert(repoStudent.getRepoStudent().size() == 1);
    }
    @Test
    public void testRemoveStudnet(){
        Student student = null;
        repoStudent.addStudent(student);
        assert(repoStudent.getRepoStudent().size() == 1);
        repoStudent.removeStudent(student);
        assert(repoStudent.getRepoStudent().size() == 0);
    }
    @Test
    public void testAddAllStudents(){
        Student student1 = null;
        Student student2 = null;
        Student student3 =null;
        List<Student> list = Arrays.asList(student1, student2, student3);
        repoStudent.addAllStudents(list);
        assert(repoStudent.getRepoStudent().size() == 3);
    }
    @Test
    public void testSetRepoStudent(){
        Student student1 = null;
        Student student2 = null;
        Student student3 =null;
        List<Student> list = Arrays.asList(student1, student2, student3);
        repoStudent.setRepoStudent(list);
        assert(repoStudent.getRepoStudent().size() == 3);
    }
    @Test
    public void testFindAll(){
        Student student1 = null;
        Student student2 = null;
        Student student3 =null;
        List<Student> list = Arrays.asList(student1, student2, student3);
        repoStudent.setRepoStudent(list);
        assert(repoStudent.findAll() == list);
    }
    @Test
    public void testSave() throws IOException {
        Student student1 = null;
        assert(repoStudent.save(student1) == null);
        assert(repoStudent.save(student1) == student1);
    }
    @Test
    public void testUpdate() throws IOException {
        Student student1 = null;
        Course course1 = null;
        Student student2 = new Student("Michael","Hank",1, 60, Arrays.asList(course1));
        repoStudent.addStudent(student2);
        assert(repoStudent.update(student2) == null);
        assert(repoStudent.update(student1) == student1);
    }
    @Test
    public void TestFindOne(){
        Course course1 = null;
        Student student1 = new Student("Michael","Hank",1, 60, Arrays.asList(course1));
        repoStudent.addStudent(student1);
        assert(repoStudent.findOne(1) == student1);
    }
    @Test
    public void testDelete() throws IOException {
        Course course1 = null;
        Student student1 = new Student("Michael","Hank",1, 60, Arrays.asList(course1));
        repoStudent.addStudent(student1);
        assert(repoStudent.delete(1).equals(student1));
    }
}

