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

    public static class ExceedStockQuantityException extends CustomException {

        public ExceedStockQuantityException(ExceptionMessage message) {
            super(message);
        }
    }

}
