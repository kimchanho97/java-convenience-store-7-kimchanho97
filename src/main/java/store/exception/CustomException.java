package store.exception;

import store.exception.message.ExceptionMessage;

public class CustomException extends IllegalArgumentException {

    private static final String PREFIX = "[ERROR]";

    private CustomException(ExceptionMessage message) {
        super(PREFIX + message.getMessage());
    }

    public static class InputException extends CustomException {
        public InputException(ExceptionMessage message) {
            super(message);
        }
    }

}
