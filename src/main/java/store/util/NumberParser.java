package store.util;

import static store.exception.message.InputExceptionMessage.INVALID_INPUT_FORMAT;
import static store.exception.message.InputExceptionMessage.ONLY_POSITIVE_INTEGERS_ALLOWED;

import store.exception.CustomException.InputException;

public class NumberParser {

    private NumberParser() {
    }

    public static int parsePositiveInteger(String input) {
        try {
            int number = Integer.parseInt(input);
            if (number <= 0) {
                throw new InputException(ONLY_POSITIVE_INTEGERS_ALLOWED);
            }
            return number;
        } catch (NumberFormatException e) {
            throw new InputException(INVALID_INPUT_FORMAT);
        }
    }

}
