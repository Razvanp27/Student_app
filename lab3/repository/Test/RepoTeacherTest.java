package lab3.repository.Test;

import lab3.model.Course;
import lab3.model.Teacher;
import lab3.repository.RepoTeacher;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RepoTeacherTest {
    private RepoTeacher repoTeacher = new RepoTeacher();
    /**
     * testing the functionality of the Repository
     */
    @Test
    public void testAddTeacher(){
        Teacher teacher = null;
        repoTeacher.addTeacher(teacher);
        assert(repoTeacher.getRepoTeacher().size() == 1);
    }
    @Test
    public void testRemoveTeacher(){
        Teacher teacher = null;
        repoTeacher.addTeacher(teacher);
        assert(repoTeacher.getRepoTeacher().size() == 1);
        repoTeacher.removeTeacher(teacher);
        assert(repoTeacher.getRepoTeacher().size() == 0);
    }
    @Test
    public void testAddAllTeacher(){
        Teacher teacher1 = null;
        Teacher teacher2 = null;
        Teacher teacher3 = null;
        List<Teacher> list = Arrays.asList(teacher1, teacher2, teacher3);
        repoTeacher.addAllTeachers(list);
        assert(repoTeacher.getRepoTeacher().size() == 3);
    }
    @Test
    public void testSetRepoTeacher(){
        Teacher teacher1 = null;
        Teacher teacher2 = null;
        Teacher teacher3 = null;
        List<Teacher> list = Arrays.asList(teacher1, teacher2, teacher3);
        repoTeacher.setRepoTeacher(list);
        assert(repoTeacher.getRepoTeacher().size() == 3);
    }
    @Test
    public void testFindAll(){
        Teacher teacher1 = null;
        Teacher teacher2 = null;
        Teacher teacher3 = null;
        List<Teacher> list = Arrays.asList(teacher1, teacher2, teacher3);
        repoTeacher.setRepoTeacher(list);
        assert(repoTeacher.findAll() == list);
    }
    @Test
    public void testSave() throws IOException {
        Teacher teacher1 = null;
        assert(repoTeacher.save(teacher1) == null);
        assert(repoTeacher.save(teacher1) == teacher1);
    }
    @Test
    public void testUpdate() throws IOException {
        Teacher teacher1 = null;
        Course course1 = null;
        Teacher teacher2 = new Teacher(1,"Jack","Michael", Arrays.asList(course1));
        repoTeacher.addTeacher(teacher2);
        assert(repoTeacher.update(teacher2) == null);
        assert(repoTeacher.update(teacher1) == teacher1);
    }
    @Test
    public void TestFindOne(){
        Course course1 = null;
        Teacher teacher1 = new Teacher(1,"Jack","Michael", Arrays.asList(course1));
        repoTeacher.addTeacher(teacher1);
        assert(repoTeacher.findOne(1) == teacher1);
    }
    @Test
    public void testDelete() throws IOException {
        Course course1 = null;
        Teacher teacher1 = new Teacher(1,"Jack","Michael", Arrays.asList(course1));
        repoTeacher.addTeacher(teacher1);
        assert(repoTeacher.delete(1) == teacher1);
    }
}
