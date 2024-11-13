package store;

import store.controller.ConvenienceStoreController;
import store.controller.ExceptionHandler;
import store.domain.ConvenienceStore;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ConvenienceStore store = new ConvenienceStore();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ExceptionHandler exceptionHandler = new ExceptionHandler(outputView);

        new ConvenienceStoreController(store, inputView, outputView, exceptionHandler).run();
    }
}

