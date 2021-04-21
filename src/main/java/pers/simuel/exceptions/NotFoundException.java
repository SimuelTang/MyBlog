package pers.simuel.exceptions;

/**
 * @Author simuel_tang
 * @Date 2021/4/21
 * @Time 13:26
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
