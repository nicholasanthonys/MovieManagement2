package Contoller.exceptions;
/**
 *
 * @author NICHOLAS ANTHONY SUHARTONO 1118049
 */
public class NonexistentEntityException extends Exception {
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}
