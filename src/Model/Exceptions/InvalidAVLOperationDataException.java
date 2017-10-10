package Model.Exceptions;

/**
 * This class represents a particular type of an {@code Exception} designed
 * for {@code Invalid AVLOperationData}.
 */
public class InvalidAVLOperationDataException extends Exception {

    public InvalidAVLOperationDataException(String s) {
        super(s);
    }

    public InvalidAVLOperationDataException() {
        super();
    }
}
