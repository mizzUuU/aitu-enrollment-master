package kz.edy.astanait.product.exception.domain;

public class ExceededLimitPostRequestException extends RuntimeException {
    public ExceededLimitPostRequestException(String message) {
        super(message);
    }
}
