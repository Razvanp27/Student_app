package lab3.repository;

import lab3.model.Course;
import java.io.*;
import java.util.Scanner;

public class CourseFileRepository implements ICrudRepository <Course>{
    private RepoCourse courseFileRepository;
    private Scanner scanner;

    public CourseFileRepository(RepoCourse repoCourse){
        this.courseFileRepository = repoCourse;
    }

    private void openFile() {
        try {
            scanner = new Scanner(new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/courseTest"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("File not found");
        }
    }

    private void readFile() {
        while (scanner.hasNext()) {
            long id = scanner.nextLong();
            String name = scanner.next();
            int maxEnrollment = scanner.nextInt();
            int credits = scanner.nextInt();
            Course course = new Course(id, name, maxEnrollment, credits);
            courseFileRepository.addCourse(course);
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
    public Course findOne(long id) {
        openFile();
        while (scanner.hasNextLine()) {
            if (scanner.next().equals(String.valueOf(id))) {
                String name = scanner.next();
                int maxEnrollment = Integer.parseInt(scanner.next());
                int credits = Integer.parseInt(scanner.next());
                closeFile();
                return new Course(id, name, maxEnrollment, credits);
            }else{
                scanner.nextLine();
            }
        }
        closeFile();
        return null;
    }
    //NoSuchElementException: throws if no line was found
    /**
     * @return all Courses
     */
    @Override
    public Iterable<Course> findAll() {
        openFile();
        readFile();
        if (courseFileRepository.getRepoCourse().size() != 0) {
            closeFile();
            return courseFileRepository.getRepoCourse();
        }
        closeFile();
        return null;
    }

    /**
     * @param entity Course must be not null
     * @return null- if the given Course is saved otherwise returns the Course (id already exists)
     */
    @Override
    public Course save(Course entity) throws IOException {
        if (findOne(entity.getId()) == entity) {
            return null;
        } else {
            openFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter
                    (new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/courseTest"), true));
            writer.write((int) entity.getId() + " ");
            writer.write(entity.getName() + " ");
            writer.write(entity.getMaxEnrollment() + " ");
            writer.write(entity.getCredits() + "");
            writer.newLine();
            writer.flush();
            writer.close();
            closeFile();
            return entity;
        }
    }

    /**
     * removes the Course with the specified id
     *
     * @param id id must be not null
     * @return the removed Course or null if there is no Course with the given id
     */
    @Override
    public Course delete(long id) throws IOException {
        openFile();
        Course course = new Course();
        boolean deleted = false;
        File oldFile = new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/courseTest");
        File newFile = new File(oldFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(oldFile));
        PrintWriter printWriter = new PrintWriter(new FileWriter(newFile));
        String currentLine;
        String courseID = String.valueOf(id);
        while((currentLine = reader.readLine()) != null){
            String[] values = currentLine.split(" ", 4);
            if(!values[0].equals(courseID)) {
                printWriter.println(currentLine);
                printWriter.flush();
            }else{
                course = new Course(Long.parseLong(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]));
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
        if(deleted)
            return course;
        else
            return null;
    }

    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g id does not exist).
     */
    @Override
    public Course update(Course entity) throws IOException {
        openFile();
        boolean updated = false;
        File oldFile = new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/courseTest");
        File newFile = new File(oldFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(oldFile));
        PrintWriter printWriter = new PrintWriter(new FileWriter(newFile));
        String currentLine;
        String courseID = String.valueOf(entity.getId());
        while((currentLine = reader.readLine()) != null){
            String[] values = currentLine.split(" ", 4);
            if(!values[0].equals(courseID)) {
                printWriter.println(currentLine);
                printWriter.flush();
            }else{
                printWriter.write((int) entity.getId() + " ");
                printWriter.write(entity.getName() + " ");
                printWriter.write(entity.getMaxEnrollment() + " ");
                printWriter.write(entity.getCredits() + "");
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
        if(!updated)
            return entity;
        else
            return null;
    }
}
