package lab3.controller;

public class CourseIsFullException extends Exception {
    public CourseIsFullException(String message){
        super(message);
    }
}
