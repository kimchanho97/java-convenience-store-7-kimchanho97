package store.exception.message;

public enum InputExceptionMessage implements ExceptionMessage {
    EMPTY_INPUT("입력 값이 비어 있습니다. 값을 입력해 주세요."),
    ONLY_POSITIVE_INTEGERS_ALLOWED("양의 정수만 입력 가능합니다. 다시 입력해 주세요."),
    INVALID_INPUT_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    ;

    private final String message;

    InputExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
