package store.constant;

import static store.exception.message.InputExceptionMessage.INVALID_INPUT;

import java.util.Arrays;
import store.exception.CustomException.InputException;

public enum Answer {
    YES("Y"),
    NO("N"),
    ;

    private final String answer;

    Answer(String answer) {
        this.answer = answer;
    }

    public static Answer findByAnswer(String input) {
        return Arrays.stream(Answer.values())
                .filter(value -> value.getAnswer().equals(input))
                .findFirst()
                .orElseThrow(() -> new InputException(INVALID_INPUT));
    }

    private String getAnswer() {
        return answer;
    }
}
