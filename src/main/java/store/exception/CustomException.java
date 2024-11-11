package store.exception;

import store.exception.message.ExceptionMessage;

public class CustomException extends IllegalArgumentException {

    private static final String PREFIX = "[ERROR] ";

    private CustomException(ExceptionMessage message) {
        super(PREFIX + message.getMessage());
    }

    public static class InputException extends CustomException {
        public InputException(ExceptionMessage message) {
            super(message);
        }
    }

    public static class NotFoundProductException extends CustomException {
        public NotFoundProductException(ExceptionMessage message) {
            super(message);
        }
    }

    public static class InsufficientQuantityException extends CustomException {

        public InsufficientQuantityException(ExceptionMessage message) {
            super(message);
        }
    }

    public static class PaymentFailedException extends CustomException {

        public PaymentFailedException(ExceptionMessage message) {
            super(message);
        }
    }

}
