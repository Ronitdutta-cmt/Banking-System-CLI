package util;

import exceptions.ValidationException;

@FunctionalInterface
public interface Validation<T> {
    // <..> -> to tell yeh interface kis type k values k sath kaam krega,( here string)
    // <T> -> T is a place holder , used when we are not sure of which type we are going to use .

    // A functional interface = an interface with exactly one abstract method
    // Without a functional interface → lambda(js like fn) won’t work.

    // here also , replace String with T(generic type ).

    void validate(T value) throws ValidationException ;
}
