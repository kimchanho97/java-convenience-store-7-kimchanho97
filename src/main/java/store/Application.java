package store;

import store.controller.ConvenienceStoreController;
import store.domain.ConvenienceStore;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // new ConvenienceStoreService( new ConvenienceStore)

        ConvenienceStore store = new ConvenienceStore();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        new ConvenienceStoreController(store, inputView, outputView).run();
    }
}
