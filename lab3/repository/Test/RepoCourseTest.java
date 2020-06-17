package lab3.repository.Test;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.RepoCourse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class RepoCourseTest {
    private RepoCourse repoCourse = new RepoCourse();

    /**
     * testing the functionality of the Repository
     */
    @Test
    public void testAddCourse(){
        Course course = null;
        repoCourse.addCourse(course);
        assert(repoCourse.getRepoCourse().size() == 1);
    }
    @Test
    public void testRemoveCourse(){
        Course course = null;
        repoCourse.addCourse(course);
        assert(repoCourse.getRepoCourse().size() == 1);
        repoCourse.removeCourse(course);
        assert(repoCourse.getRepoCourse().size() == 0);
    }
    @Test
    public void testAddAllCourses(){
        Course course1 = null;
        Course course2 = null;
        Course course3 = null;
        List<Course> list = Arrays.asList(course1, course2, course3);
        repoCourse.addAllCourses(list);
        assert(repoCourse.getRepoCourse().size() == 3);
    }
    @Test
    public void testSetRepoCourse(){
        Course course1 = null;
        Course course2 = null;
        Course course3 = null;
        List<Course> list = Arrays.asList(course1, course2, course3);
        repoCourse.setRepoCourse(list);
        assert(repoCourse.getRepoCourse().size() == 3);
    }
    @Test
    public void testFindAll(){
        Course course1 = null;
        Course course2 = null;
        Course course3 = null;
        List<Course> list = Arrays.asList(course1, course2, course3);
        repoCourse.setRepoCourse(list);
        assert(repoCourse.findAll() == list);
    }
    @Test
    public void testSave() throws IOException {
        Course course1 = null;
        assert(repoCourse.save(course1) == null);
        assert(repoCourse.save(course1) == course1);
    }
    @Test
    public void testUpdate() throws IOException {
        Student student1 = null;
        Teacher teacher1 = null;
        Course course1 = null;
        Course course2 = new Course(1,"English",teacher1, 30, Arrays.asList(student1), 5);
        repoCourse.addCourse(course2);
        assert(repoCourse.update(course2) == null);
        assert(repoCourse.update(course1) == course1);
    }
    @Test
    public void TestFindOne(){
        Teacher teacher1 = null;
        Student student1 = null;
        Course course1 = new Course(1,"English",teacher1, 30, Arrays.asList(student1), 5);
        repoCourse.addCourse(course1);
        assert(repoCourse.findOne(1) == course1);
    }
    @Test
    public void testDelete() throws IOException {
        Student student1 = null;
        Teacher teacher1 = null;
        Course course1 = new Course(1,"English",teacher1, 30, Arrays.asList(student1), 5);
        repoCourse.addCourse(course1);
        assert(repoCourse.delete(1).equals(course1));
    }
}
