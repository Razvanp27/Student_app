package lab3.model;

public interface Observer<T> {
    void notify(T value);
}
