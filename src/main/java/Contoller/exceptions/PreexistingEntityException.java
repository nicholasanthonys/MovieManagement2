package Contoller.exceptions;
/**
 *
 * @author NICHOLAS ANTHONY SUHARTONO 1118049
 */
public class PreexistingEntityException extends Exception {
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreexistingEntityException(String message) {
        super(message);
    }
}
