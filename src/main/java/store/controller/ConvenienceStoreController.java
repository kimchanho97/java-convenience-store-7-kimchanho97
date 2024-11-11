package store.controller;

import static store.constant.Answer.YES;
import static store.exception.message.CartExceptionMessage.EXCEEDS_STOCK_QUANTITY;
import static store.exception.message.CartExceptionMessage.NOT_FOUND_PRODUCT;

import java.util.Map;
import store.constant.Answer;
import store.domain.Cart;
import store.domain.ConvenienceStore;
import store.domain.Receipt;
import store.exception.CustomException.ExceedStockQuantityException;
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
            Map<String, Integer> itemsToPurchase = exceptionHandler.retry(() -> {
                Map<String, Integer> items = inputView.inputProductNameAndQuantity();
                validatePurchaseItems(items);
                return items;
            });

            Cart cart = new Cart();
            cartService.addItemToCart(
                    cart,
                    itemsToPurchase,
                    exceptionHandler.retry(inputView::askToApplyPromotion),
                    exceptionHandler.retry(inputView::askToProceedWithoutPromotion)
            );

            Answer isMembershipDiscount = exceptionHandler.retry(inputView::askToApplyMembership);
            PaymentService paymentService = new PaymentService(isMembershipDiscount, cart);
            Receipt receipt = paymentService.checkout();
            
            outputView.displayReceipt(receipt);
        } while (exceptionHandler.retry(inputView::askAdditionalPurchase) == YES);
    }

    private void validatePurchaseItems(Map<String, Integer> itemsToPurchase) {
        for (Map.Entry<String, Integer> entry : itemsToPurchase.entrySet()) {
            checkProductExists(entry.getKey());
            checkSufficientStockForPurchase(entry.getKey(), entry.getValue());
        }
    }

    private void checkSufficientStockForPurchase(String name, Integer quantityToPurchase) {
        if (!store.hasAvailableQuantity(name, quantityToPurchase)) {
            throw new ExceedStockQuantityException(EXCEEDS_STOCK_QUANTITY);
        }
    }

    private void checkProductExists(String name) {
        if (!store.hasProductByName(name)) {
            throw new NotFoundProductException(NOT_FOUND_PRODUCT);
        }
    }
}
