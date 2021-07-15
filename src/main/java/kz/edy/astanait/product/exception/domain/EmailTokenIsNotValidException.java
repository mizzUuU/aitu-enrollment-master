package kz.edy.astanait.product.exception.domain;

public class EmailTokenIsNotValidException extends RuntimeException {
    public EmailTokenIsNotValidException(String message) {
        super(message);
    }
}
