package lab3.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JfxUI extends Application implements EventHandler<ActionEvent> {
    RegistrationSystem controller;
     ComboBox<String> comboBox;
     ComboBox<String> comboBoxS;
     ListView<String> listOfStudents;


    @Override
    public void start(Stage primaryStage) {
        controller = new RegistrationSystem();
        controller.getJdbcC().load();
        primaryStage.setTitle("Academic Info");
        ////
        Group groupFirst = new Group();
        Scene scene = new Scene(groupFirst, 1000, 500);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(500);
        primaryStage.setScene(scene);

        /*start**/

        Label labelStart = new Label("Academic Info");
        labelStart.setMinSize(60, 60);
        labelStart.setStyle("-fx-font-size:50");
        labelStart.setLayoutX(350);
        labelStart.setLayoutY(150);
        Button loginStudent = new Button("Login Student");
        loginStudent.setStyle("-fx-font-size:30;");
        loginStudent.setMinSize(20, 20);
        loginStudent.setLayoutX(150);
        loginStudent.setLayoutY(300);
        Button loginTeacher = new Button("Login Teacher");
        loginTeacher.setStyle("-fx-font-size:30;");
        loginTeacher.setMinSize(20, 20);
        loginTeacher.setLayoutX(650);
        loginTeacher.setLayoutY(300);
        Group groupSecond = new Group();
        Scene sceneSecond = new Scene(groupSecond, 1000, 500);
        groupSecond.getChildren().addAll(labelStart, loginStudent, loginTeacher);
        primaryStage.setScene(sceneSecond);
        primaryStage.show();
        /////////////////Student stage
        Button enter = new Button("Enter");
        TextField textField = new TextField();
        Label studentName = new Label("Student Name: ");
        studentName.setStyle("-fx-font-size:30");
        studentName.setLayoutX(10);
        studentName.setLayoutY(50);
        textField.setStyle("-fx-font-size:20");
        textField.setLayoutX(200);
        textField.setLayoutY(50);
        enter.setStyle("fx-font-size:70px");
        enter.setLayoutX(220);
        enter.setLayoutY(150);
        Group studentGroup = new Group();
        Scene studentScene = new Scene(studentGroup, 500, 250);
        studentGroup.getChildren().addAll(studentName, textField, enter);
        //////////////////Teacher stage
        Label teacherName = new Label("Teacher Name: ");
        teacherName.setStyle("-fx-font-size:30");
        teacherName.setLayoutX(5);
        teacherName.setLayoutY(50);
        TextField textField1 = new TextField();
        Button enter1 = new Button("Enter");
        textField1.setStyle("-fx-font-size:20px");
        textField1.setLayoutX(200);
        textField1.setLayoutY(50);
        enter1.setStyle("fx-font-size:70px");
        enter1.setLayoutX(220);
        enter1.setLayoutY(150);
        Group teacherGroup = new Group();
        Scene teacherScene = new Scene(teacherGroup, 500, 250);
        teacherGroup.getChildren().addAll(teacherName, textField1, enter1);
        /////////////////Student login button push
        loginStudent.setOnAction(e -> loginStudent(studentScene, enter, textField)
        );
        ////////////////////////Teacher login button push
        loginTeacher.setOnAction(e -> loginTeacher(teacherScene, enter1, textField1)
        );
    }

    public void loginStudent(Scene studentScene, Button enter, TextField textField){
        Stage studentStage = new Stage();
        studentStage.setTitle("Student Menu");
        studentStage.setWidth(500);
        studentStage.setHeight(250);
        studentStage.setScene(studentScene);
        studentStage.show();
        enter.setOnAction(ent -> {
            String name = textField.getText();
            Student student = controller.getJdbcC().findStudent(name);
            if (student != null) {
                Group group = new Group();
                Scene studentFinal = new Scene(group, 500, 250);
                Label credits = new Label("Credits: " + student.getTotalCredits());
                credits.setStyle("-fx-font-size:20");
                credits.setMinSize(20, 20);
                credits.setLayoutX(50);
                credits.setLayoutY(150);
                Label sName = new Label(student.getFirstName() + " " + student.getLastName());
                sName.setStyle("-fx-font-size:20");
                sName.setMinSize(20, 20);
                sName.setLayoutX(50);
                sName.setLayoutY(100);
                comboBoxS = new ComboBox<>();
                comboBoxS.getItems().addAll(getCourseNames(student));
                comboBoxS.setPromptText("Select Course to register");
                comboBoxS.setLayoutX(250);
                comboBoxS.setLayoutY(100);
                comboBoxS.setOnAction(choice ->register(comboBoxS.getValue(), student));
                group.getChildren().addAll(sName, comboBoxS, credits);
                studentStage.setScene(studentFinal);
                studentStage.show();
            }
        });
    }
    public ObservableList<String> getCourseNames(Student student){
        ArrayList<String> studentCourse = new ArrayList<>();
        student.getEnrolledCourses().forEach(course -> studentCourse.add(course.getName()));
        ObservableList<String> courseNames = FXCollections.observableArrayList();
        controller.getJdbcC().getCourseNames().stream()
                .filter(course -> !studentCourse.contains(course)).forEach(courseNames::add);
        return courseNames;
    }

    public void register(String choice, Student student) {
        Course course = controller.getJdbcC().findCourse(choice);
        try {
            controller.register(course.getId(), student.getStudentID());
        }catch(CourseIsFullException | CourseNotFoundException  | MaxCreditsException e){
            e.getMessage();
        }
    }

    public void loginTeacher(Scene teacherScene, Button enter, TextField textField){
        Stage teacherStage = new Stage();
        teacherStage.setTitle("Teacher Menu");
        teacherStage.setWidth(500);
        teacherStage.setHeight(250);
        teacherStage.setScene(teacherScene);
        teacherStage.show();
        enter.setOnAction(ent -> {
            String name = textField.getText();
            Teacher teacher = controller.getJdbcC().findTeacher(name);
            if (teacher != null) {
                Group group = new Group();
                Scene teacherFinal = new Scene(group, 500, 250);
                Label fullName = new Label(teacher.getFirstName() + " " + teacher.getLastName());
                fullName.setStyle("-fx-font-size:30");
                fullName.setMinSize(30, 30);
                fullName.setLayoutX(50);
                fullName.setLayoutY(100);
                comboBox = new ComboBox<>();
                comboBox.getItems().addAll(controller.getJdbcC().getCourseNames());
                comboBox.setPromptText("Check Course students list");
                comboBox.setLayoutX(250);
                comboBox.setLayoutY(100);
                listOfStudents = new ListView<>();
                listOfStudents.setLayoutX(350);
                listOfStudents.setLayoutY(200);
                comboBox.setOnAction(choice -> {
                    getChoice(comboBox.getValue());
                    teacherStage.setScene(teacherFinal);
                    listOfStudents.refresh();
                    teacherStage.show();
                });
                group.getChildren().addAll(fullName, comboBox, listOfStudents);
                teacherStage.setScene(teacherFinal);
                teacherStage.show();
            }
            else{
                System.out.println("Teacher not found");
            }
        });
    }
    public void getChoice(String choice){
        System.out.println(choice);
        Course course = controller.getJdbcC().findCourse(choice);

        listOfStudents.getItems().clear();
        course.getStudentsEnrolled().forEach(student -> listOfStudents
                .getItems().add(student.getStudentID() + student.getFirstName() + " " + student.getLastName()));
    }
    public void menu(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
