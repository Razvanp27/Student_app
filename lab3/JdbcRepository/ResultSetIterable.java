package lab3.JdbcRepository;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.sql.SQLException;

    interface CheckedFunction<T,R> extends Function<T,R> {

    @Override
    default R apply(T t) {

        try {
            return applyAndThrow(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    R applyAndThrow(T t) throws Exception;
}

public class ResultSetIterable<T> implements Iterable<T> {

    private final ResultSet rs;
    private final Function<ResultSet, T> onNext;

    public ResultSetIterable(ResultSet rs, CheckedFunction<ResultSet, T> onNext){
        this.rs = rs;
        //onNext is the mapper function to get the values from the resultSet
        this.onNext = onNext;
    }

    private boolean resultSetHasNext(){
        boolean hasNext;
        try {
            hasNext = rs.next();
        } catch (SQLException e) {
            //you should add proper exception handling here
            throw new RuntimeException(e);
        }
        return hasNext;
    }


    @Override
    public Iterator<T> iterator() {

        try {
            return new Iterator<T>() {

                //the iterator state is initialized by calling next() to
                //know whether there are elements to iterate
                boolean hasNext = resultSetHasNext();


                @Override
                public boolean hasNext() {
                    return hasNext;
                }

                @Override
                public T next() {

                    T result = onNext.apply(rs);
                    //after each get, we need to update the hasNext info
                    hasNext = resultSetHasNext();
                    return result;
                }
            };
        } catch (Exception e) {
            //you should add proper exception handling here
            throw new RuntimeException(e);
        }
    }

    //adding stream support based on an iteratable is easy
    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }
}