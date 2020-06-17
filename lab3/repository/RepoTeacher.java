package lab3.repository;

import lab3.model.Teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepoTeacher implements ICrudRepository<Teacher>{
    private List<Teacher> repoTeacher = new ArrayList<>();
    public void addTeacher(Teacher newTeacher){this.repoTeacher.add(newTeacher);};
    public void removeTeacher(Teacher oldTeacher){this.repoTeacher.remove(oldTeacher);};
    void removeAllTeachers(){
        repoTeacher.forEach(teacher -> repoTeacher.remove(teacher));
    }
    public void addAllTeachers(List<Teacher> newTeachers){this.repoTeacher.addAll(newTeachers);};

    /**
     * setter and getter for the Teacher Repository
     * @return returns the Repository
     * set the Repository with the new given list of Teachers;
     */
    public List<Teacher> getRepoTeacher(){return repoTeacher;};
    public void setRepoTeacher(List<Teacher> newTeachers){this.repoTeacher = newTeachers;};

    /**
     * @param id -the id of the Teacher to be returned, id must not be null
     * @return the Teacher with the specified id or null - if there is no Teacher with the given id
     */
    @Override
    public Teacher findOne(long id) {
        if(repoTeacher.stream().anyMatch(teacher -> teacher.getId() == id)) {
            return repoTeacher.stream().filter(teacher -> teacher.getId() == id).findAny().get();
        }
        return null;
    }

    /**
     * @return all Teachers
     */
    @Override
    public Iterable<Teacher> findAll() {
        return repoTeacher;
    }

    /**
     * @param entity Teacher must be not null
     * @return null- if the given Teacher is saved otherwise returns the Teacher (id already exists)
     */
    @Override
    public Teacher save(Teacher entity){
        if (repoTeacher.contains(entity)) {
            return null;
        }else {
            repoTeacher.add(entity);
            return entity;
        }
    }

    /**
     * removes the Teacher with the specified id
     * @param id id must be not null
     * @return the removed Teacher or null if there is no Teacher with the given id
     */
    @Override
    public Teacher delete(long id){
        if(repoTeacher.stream().anyMatch(teacher -> teacher.getId() == id)) {
            Teacher teacher = repoTeacher.stream().filter(teacher1 -> teacher1.getId() == id).findAny().get();
            removeTeacher(teacher);
            return teacher;
        }
        return null;
    }

    /**
     * @param entity Teacher must not be null
     * @return null - if the Teacher is updated, otherwise returns the Teacher - (e.g id does not exist).
     */
    @Override
    public Teacher update(Teacher entity){
        if(repoTeacher.stream().anyMatch(teacher -> teacher == entity)) {
            Teacher teacher = repoTeacher.stream().filter(teacher1 -> teacher1 == entity).findAny().get();
            teacher.setId(entity.getId());
            teacher.setFirstName(entity.getLastName());
            teacher.setLastName(entity.getFirstName());
            teacher.setCourses(entity.getCourses());
            return null;
        }
        return entity;
    }
    //helper function
    public Teacher getTeacher(long id){
        if(repoTeacher.stream().anyMatch(teacher -> teacher.getId() == id)) {
            return repoTeacher.stream().filter(teacher -> teacher.getId() == id).findAny().get();
        }
        return null;
    }
}
