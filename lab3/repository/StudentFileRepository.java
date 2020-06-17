package lab3.repository;

import lab3.model.Course;
import lab3.model.Student;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class StudentFileRepository implements ICrudRepository<Student>{
    private RepoStudent studentFileRepository;
    private Scanner scanner;

    public StudentFileRepository(RepoStudent repoStudent){
        this.studentFileRepository = repoStudent;
    }


    private void openFile() {
        try {
            scanner = new Scanner(new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/studentTest"));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("File not found");
        }
    }

    private void readFile() {
        while(scanner.hasNext()){
            long id = scanner.nextLong();
            String firstName = scanner.next();
            String lastName = scanner.next();
            int totalCredits = scanner.nextInt();
            Student student = new Student(id, firstName, lastName, totalCredits);
            studentFileRepository.addStudent(student);
        }
    }

    private void closeFile() {
        scanner.close();
    }

    /**
     * @param id -the id of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    @Override
    public Student findOne(long id) {
        openFile();
        while(scanner.hasNextLine()){
            if(scanner.next().equals(String.valueOf(id))){
                String firstName = scanner.next();
                String lastName = scanner.next();
                int credits = Integer.parseInt(scanner.next());
                closeFile();
                return new Student(id, firstName, lastName, credits);
            }else{
                scanner.nextLine();
            }
        }
        closeFile();
        return null;
    }

    /**
     * @return all Students
     */
    @Override
    public Iterable<Student> findAll() {
        openFile();
        readFile();
        if(studentFileRepository.getRepoStudent().size() != 0) {
            closeFile();
            return studentFileRepository.getRepoStudent();
        }
        closeFile();
        return null;

    }

    /**
     * @param entity Student must be not null
     * @return null- if the given Course is saved otherwise returns the Student (id already exists)
     */

    @Override
    public Student save(Student entity) throws IOException {
        if(findOne(entity.getStudentID()) == entity){
            return null;
        } else {
            openFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter
                    (new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/studentTest"), true));
            writer.write((int) entity.getStudentID() + " ");
            writer.write(entity.getFirstName() + " ");
            writer.write(entity.getLastName() + " ");
            writer.write(entity.getTotalCredits() + "");
            writer.newLine();
            writer.flush();
            writer.close();
            closeFile();
            registerEnrolledCourses(entity);
            return entity;
        }
    }

    public void registerEnrolledCourses(Student entity) {
        List<Course> registeredCourses = entity.getEnrolledCourses();
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter
                    (new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/registerTest"), true));
            writer.write((int) entity.getStudentID() + " ");
            for(Course course : registeredCourses){
                writer.write((int)course.getId() + " ");
            }
            writer.newLine();
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("File not found");
        }
    }

    /**
     * removes the Student with the specified id
     *
     * @param id id must be not null
     * @return the removed Course or null if there is no Student with the given id
     */
    @Override
    public Student delete(long id) throws IOException {
        openFile();
        Student student = new Student();
        boolean deleted = false;
        File oldFile = new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/studentTest");
        File newFile = new File(oldFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(oldFile));
        PrintWriter printWriter = new PrintWriter(new FileWriter(newFile));
        String currentLine;
        String studentID = String.valueOf(id);
        while((currentLine = reader.readLine()) != null){
            String[] values = currentLine.split(" ", 4);
            if(!values[0].equals(studentID)){
                printWriter.println(currentLine);
                printWriter.flush();
            }else{
                student = new Student(Long.parseLong(values[0]), values[1], values[2], Integer.parseInt(values[3]));
                deleted = true;
            }
        }
        printWriter.close();
        reader.close();
        if(!oldFile.delete()){
            System.out.println("Could not delete old file");
        }
        if(!newFile.renameTo(oldFile)){
            System.out.println("Could not rename new file");
        }
        closeFile();
        if(deleted)
            return student;
        else
            return null;
    }

    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g id does not exist).
     */
    @Override
    public Student update(Student entity) throws IOException {
        openFile();
        boolean updated = false;
        File oldFile = new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/studentTest");
        File newFile = new File(oldFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(oldFile));
        PrintWriter printWriter = new PrintWriter(new FileWriter(newFile));
        String currentLine;
        String studentID = String.valueOf(entity.getStudentID());
        while((currentLine = reader.readLine()) != null){
            String[] values = currentLine.split(" ", 4);
            if(!values[0].equals(studentID)){
                printWriter.println(currentLine);
                printWriter.flush();
            }else{
                printWriter.write((int) entity.getStudentID() + " ");
                printWriter.write(entity.getFirstName() + " ");
                printWriter.write(entity.getLastName() + " ");
                printWriter.write(entity.getTotalCredits() + "");
                printWriter.println();
                printWriter.flush();
                updated = true;
            }
        }
        printWriter.close();
        reader.close();
        if(!oldFile.delete()){
            System.out.println("Could not delete old file");
        }
        if(!newFile.renameTo(oldFile)){
            System.out.println("Could not rename new file");
        }
        closeFile();
        if(!updated)
            return entity;
        else
            return null;
    }
}
