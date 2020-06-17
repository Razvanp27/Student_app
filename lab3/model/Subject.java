package lab3.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject<T> extends Person {
    private List<Observer<T>> observerCollection;

    Subject(){
        observerCollection = new ArrayList<>();
    }
    public void registerObserver(Observer<T> observer){observerCollection.add(observer);};
    public void unregisterObserver(Observer<T> observer){observerCollection.remove(observer);};
    void notifyObservers(T value){
        observerCollection.forEach(observer -> observer.notify(value));
    }
}
