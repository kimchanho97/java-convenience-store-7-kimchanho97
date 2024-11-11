package store.controller;

import static store.constant.Answer.YES;
import static store.exception.message.CartExceptionMessage.INSUFFICIENT_QUANTITY;
import static store.exception.message.CartExceptionMessage.NOT_FOUND_PRODUCT;

import java.util.Map;
import store.constant.Answer;
import store.domain.Cart;
import store.domain.ConvenienceStore;
import store.domain.Receipt;
import store.exception.CustomException.InsufficientQuantityException;
import store.exception.CustomException.NotFoundProductException;
import store.service.CartService;
import store.service.PaymentService;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {

    private final ConvenienceStore store;
    private final InputView inputView;
    private final OutputView outputView;
    private final ExceptionHandler exceptionHandler;
    private final CartService cartService;

    public ConvenienceStoreController(ConvenienceStore store, InputView inputView, OutputView outputView,
                                      ExceptionHandler exceptionHandler) {
        this.store = store;
        this.inputView = inputView;
        this.outputView = outputView;
        this.exceptionHandler = exceptionHandler;
        this.cartService = new CartService(store);
    }

    public void run() {
        do {
            outputView.displayConvenienceStore(store);
            Map<String, Integer> itemsToPurchase = getValidatedItemsToPurchase();

            Cart cart = addItemsToCart(itemsToPurchase);
            Receipt receipt = processCheckout(cart);

            outputView.displayReceipt(receipt);
        } while (hasAdditionalPurchase());
    }

    private Map<String, Integer> getValidatedItemsToPurchase() {
        return exceptionHandler.retry(() -> {
            Map<String, Integer> items = inputView.inputProductNameAndQuantity();
            validateItemsToPurchase(items);
            return items;
        });
    }

    private void validateItemsToPurchase(Map<String, Integer> itemsToPurchase) {
        for (Map.Entry<String, Integer> entry : itemsToPurchase.entrySet()) {
            checkProductExists(entry.getKey());
            checkSufficientQuantityForPurchase(entry.getKey(), entry.getValue());
        }
    }

    private void checkProductExists(String name) {
        if (!store.hasProduct(name)) {
            throw new NotFoundProductException(NOT_FOUND_PRODUCT);
        }
    }

    private void checkSufficientQuantityForPurchase(String name, Integer quantityToPurchase) {
        if (!store.hasSufficientQuantity(name, quantityToPurchase)) {
            throw new InsufficientQuantityException(INSUFFICIENT_QUANTITY);
        }
    }

    private Cart addItemsToCart(Map<String, Integer> itemsToPurchase) {
        Cart cart = new Cart();
        cartService.addItemsToCart(cart, itemsToPurchase,
                exceptionHandler.retry(inputView::askToApplyPromotion),
                exceptionHandler.retry(inputView::askToProceedWithoutPromotion));
        return cart;
    }

    private Receipt processCheckout(Cart cart) {
        Answer isMembershipDiscountApplied = exceptionHandler.retry(inputView::askToApplyMembership);
        PaymentService paymentService = new PaymentService(isMembershipDiscountApplied, cart);
        return exceptionHandler.process(paymentService::checkout);
    }

    private boolean hasAdditionalPurchase() {
        return exceptionHandler.retry(inputView::askAdditionalPurchase) == YES;
    }

}
