package lab3.repository;

import lab3.model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepoStudent implements ICrudRepository<Student>{
    private List<Student> repoStudent = new ArrayList<>();
    public void addStudent(Student newStudent){this.repoStudent.add(newStudent);};
    public void removeStudent(Student oldStudent){this.repoStudent.remove(oldStudent);};
    void removeAllStudents(){
        repoStudent.forEach(student -> repoStudent.remove(student));
    }
    public void addAllStudents(List<Student> allStudents){this.repoStudent.addAll(allStudents);};
    /**
     * setter and getter for the Student Repository
     * @return returns the Repository
     * set the Repository with the new given list of Students;
     */
    public List<Student> getRepoStudent(){return repoStudent;};
    public void setRepoStudent(List<Student> students){this.repoStudent = students;};

    /**
     * @param id -the id of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    @Override
    public Student findOne(long id) {
        if(repoStudent.stream().anyMatch(student -> student.getStudentID() == id)) {
            return repoStudent.stream().filter(student -> student.getStudentID() == id).findAny().get();
        }
        return null;
    }

    /**
     * @return all Students
     */
    @Override
    public Iterable<Student> findAll() {
        return repoStudent;
    }

    /**
     * @param entity Student must be not null
     * @return null- if the given Student is saved otherwise returns the Student (id already exists)
     */
    @Override
    public Student save(Student entity){
        if(repoStudent.contains(entity)){
            return null;
        }else {
            repoStudent.add(entity);
            return entity;
        }
    }

    /**
     * removes the Student with the specified id
     * @param id id must be not null
     * @return the removed Student or null if there is no Student with the given id
     */
    @Override
    public Student delete(long id) {
        if(repoStudent.stream().anyMatch(student -> student.getStudentID() == id)) {
            Student student = repoStudent.stream().filter(student1 -> student1.getStudentID() == id).findAny().get();
                    repoStudent.remove(student);
                    return student;
        }
        return null;
    }

    /**
     * @param entity Student must not be null
     * @return null - if the Student is updated, otherwise returns the Student - (e.g id does not exist).
     */
    @Override
    public Student update(Student entity){
        if(repoStudent.stream().anyMatch(student -> student == entity)){
                Student student = repoStudent.stream().filter(student1 -> student1 == entity).findAny().get();
                student.setFirstName(entity.getFirstName());
                student.setLastName(entity.getLastName());
                student.setStudentID(entity.getStudentID());
                student.setTotalCredits(entity.getTotalCredits());
                student.setEnrolledCourses(entity.getEnrolledCourses());
                return null;
        }
        return entity;
    }
    //helper function
    public Student getStudent(long id){
        if(repoStudent.stream().anyMatch(student -> student.getStudentID() == id)) {
            return repoStudent.stream().filter(student -> student.getStudentID() == id).findAny().get();
        }
        return null;
    }
}

