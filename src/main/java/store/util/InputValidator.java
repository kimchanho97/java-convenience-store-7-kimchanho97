package store.util;

import static store.exception.message.InputExceptionMessage.EMPTY_INPUT;

import store.exception.CustomException.InputException;

public class InputValidator {

    private InputValidator() {
    }

    public static void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new InputException(EMPTY_INPUT);
        }
    }
}

