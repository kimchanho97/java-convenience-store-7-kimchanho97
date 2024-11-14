package store.controller;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import store.exception.CustomException;
import store.exception.CustomException.PaymentFailedException;
import store.view.OutputView;

public class ExceptionHandler {

    private final OutputView outputView;

    public ExceptionHandler(OutputView outputView) {
        this.outputView = outputView;
    }

    public <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (CustomException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public <T, U, R> BiFunction<T, U, R> retry(BiFunction<T, U, R> biFunction) {
        return (firstArg, secondArg) -> {
            while (true) {
                try {
                    return biFunction.apply(firstArg, secondArg);
                } catch (CustomException e) {
                    outputView.printExceptionMessage(e.getMessage());
                }
            }
        };
    }

    public <T> T process(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (PaymentFailedException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
        return null;
    }

}
