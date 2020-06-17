package lab3.repository;

import lab3.model.Teacher;
import java.io.*;
import java.util.Scanner;

public class TeacherFileRepository implements ICrudRepository<Teacher>{
    private RepoTeacher teacherFileRepository;
    private Scanner scanner;

    public TeacherFileRepository(RepoTeacher repoTeacher){
        this.teacherFileRepository = repoTeacher;
    }
    private void openFile() {
        try{
            scanner = new Scanner(new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/teacherTest"));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("File not found");
        }
    }

    private void readFile() {
        while (scanner.hasNext()) {
            long id = scanner.nextLong();
            String firstName = scanner.next();
            String lastName = scanner.next();
            Teacher teacher = new Teacher(id, firstName, lastName);
            teacherFileRepository.addTeacher(teacher);
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
    public Teacher findOne(long id) {
        openFile();
        while(scanner.hasNextLine()){
            if(scanner.next().equals(String.valueOf(id))){
                String firstName = scanner.next();
                String lastName = scanner.next();
                closeFile();
                return new Teacher(id, firstName, lastName);
            }else{
                scanner.nextLine();
            }
        }
        closeFile();
        return null;
    }

    /**
     * @return all Teachers
     */
    @Override
    public Iterable<Teacher> findAll() {
        openFile();
        readFile();
        if(teacherFileRepository.getRepoTeacher().size() != 0) {
            closeFile();
            return teacherFileRepository.getRepoTeacher();
        }
        closeFile();
        return null;
    }
    /**
     * @param entity Teacher must be not null
     * @return null- if the given Course is saved otherwise returns the Course (id already exists)
     */

    @Override
    public Teacher save(Teacher entity) throws IOException {
        if (findOne(entity.getId()) == entity) {
            return null;
        } else {
            openFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter
                    (new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/teacherTest"), true));
            writer.write((int) entity.getId() + " ");
            writer.write(entity.getFirstName() + " ");
            writer.write(entity.getLastName() + "");
            writer.newLine();
            writer.flush();
            writer.close();
            closeFile();
            return entity;
        }
    }
    /**
     * removes the Teacher with the specified id
     *
     * @param id id must be not null
     * @return the removed Course or null if there is no Teacher with the given id
     */

    @Override
    public Teacher delete(long id) throws IOException {
        openFile();
        Teacher teacher = new Teacher();
        boolean deleted = false;
        File oldFile = new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/teacherTest");
        File newFile = new File(oldFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(oldFile));
        PrintWriter printWriter = new PrintWriter(new FileWriter(newFile));
        String currentLine;
        String teacherID = String.valueOf(id);
        while((currentLine = reader.readLine()) != null){
            String[] values = currentLine.split(" ", 3);
            if(!values[0].equals(teacherID)){
                printWriter.println(currentLine);
                printWriter.flush();
            }else{
                teacher = new Teacher(Long.parseLong(values[0]), values[1], values[2]);
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
            return teacher;
        else
            return null;
    }
    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g id does not exist).
     */

    @Override
    public Teacher update(Teacher entity) throws IOException {
        openFile();
        boolean updated = false;
        File oldFile = new File("/Users/purdearazvan/IdeaProjects/Lab3/src/lab3/files/teacherTest");
        File newFile = new File(oldFile.getAbsolutePath() + ".tmp");
        BufferedReader reader = new BufferedReader(new FileReader(oldFile));
        PrintWriter printWriter = new PrintWriter(new FileWriter(newFile));
        String currentLine;
        String teacherID = String.valueOf(entity.getId());
        while((currentLine = reader.readLine()) != null){
            String[] values = currentLine.split(" ", 3);
            if(!values[0].equals(teacherID)){
                printWriter.println(currentLine);
                printWriter.flush();
            }else{
                printWriter.write((int) entity.getId() + " ");
                printWriter.write(entity.getFirstName() + " ");
                printWriter.write(entity.getLastName() + "");
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
