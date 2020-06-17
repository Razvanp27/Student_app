package lab3.model;

public abstract class Person {
    String firstName;
    String lastName;

    /**
     * getters and setters for the class Person
     * @return the first Name and the last Name.
     */
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
}
