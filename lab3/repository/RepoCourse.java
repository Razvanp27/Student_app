package lab3.repository;

import lab3.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RepoCourse implements ICrudRepository <Course>{
    private List<Course> repoCourse = new ArrayList<>();
    public void addCourse(Course newCourse){this.repoCourse.add(newCourse);};
    public void removeCourse(Course oldCourse){this.repoCourse.remove(oldCourse);};
    public void addAllCourses(List<Course> newCourses){this.repoCourse.addAll(newCourses);};
    /**
     * setter and getter for the Course Repository
     * @return returns the Repository
     * set the Repository with the new given list of Courses;
     */
    public List<Course> getRepoCourse(){return this.repoCourse;};
    public void setRepoCourse(List<Course> newCourses){this.repoCourse = newCourses;};

    /**
     * @param id -the id of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    @Override
    public  Course findOne(long id){
        if(repoCourse.stream().anyMatch(course -> course.getId() == id)) {
            return repoCourse.stream().filter(course -> course.getId() == id).findAny().get();
        }
        return null;
    }

    /**
     * @return all Courses
     */
    @Override
    public Iterable<Course> findAll() {
        return repoCourse;
    }

    /**
     * @param entity Course must be not null
     * @return null- if the given Course is saved otherwise returns the Course (id already exists)
     */
    @Override
    public Course save(Course entity) {
        if (repoCourse.contains(entity)){
            return null;
        }else {
            repoCourse.add(entity);
            return entity;
        }
    }

    /**
     * removes the Course with the specified id
     * @param id id must be not null
     * @return the removed Course or null if there is no Course with the given id
     */
    @Override
    public Course delete(long id){
        if(repoCourse.stream().anyMatch(course -> course.getId() == id)){
            Course course = repoCourse.stream().filter(course1 -> course1.getId() == id).findAny().get();
            repoCourse.remove(course);
            return course;
        }
        return null;
    }

    /**
     * @param entity Course must not be null
     * @return null - if the Course is updated, otherwise returns the Course - (e.g id does not exist).
     */
    @Override
    public Course update(Course entity){
        if(repoCourse.stream().anyMatch(course -> course == entity)) {
            Course course = repoCourse.stream().filter(course1 -> course1 == entity).findAny().get();
            course.setId(entity.getId());
            course.setName(entity.getName());
            course.setTeacher(entity.getTeacher());
            course.setMaxEnrollment(entity.getMaxEnrollment());
            course.setStudentsEnrolled(entity.getStudentsEnrolled());
            course.setCredits(entity.getCredits());
            return null;
                }
        return entity;
    }
    //helper function
    public Course getCourse(long id){
        if(repoCourse.stream().anyMatch(course -> course.getId() == id)) {
            return repoCourse.stream().filter(course -> course.getId() == id).findAny().get();
        }
        return null;
    }
}
