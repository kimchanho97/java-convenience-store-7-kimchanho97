package store.exception.message;

public enum CartExceptionMessage implements ExceptionMessage {
    NOT_FOUND_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INSUFFICIENT_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    ;

    private final String message;

    CartExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
