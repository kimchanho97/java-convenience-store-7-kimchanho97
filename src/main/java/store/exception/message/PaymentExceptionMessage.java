package store.exception.message;

public enum PaymentExceptionMessage implements ExceptionMessage {
    PAYMENT_FAILED_INSUFFICIENT_QUANTITY("결제 실패: 요청한 수량이 재고를 초과했습니다. 결제를 다시 시작해 주세요.");

    private final String message;

    PaymentExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
