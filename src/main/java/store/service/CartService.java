package store.service;

import static store.constant.Answer.NO;
import static store.constant.Answer.YES;
import static store.exception.message.CartExceptionMessage.EXCEEDS_STOCK_QUANTITY;
import static store.exception.message.CartExceptionMessage.NOT_FOUND_PRODUCT;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import store.constant.Answer;
import store.domain.Cart;
import store.domain.ConvenienceStore;
import store.domain.Product;
import store.exception.CustomException.ExceedStockQuantityException;
import store.exception.CustomException.NotFoundProductException;

public class CartService {

    private final ConvenienceStore store;

    public CartService(ConvenienceStore store) {
        this.store = store;
    }

    public void addItemToCart(
            Cart cart, Map<String, Integer> itemsToBuy,
            BiFunction<String, Integer, Answer> askToApplyPromotion,
            BiFunction<String, Integer, Answer> askToProceedWithoutPromotion
    ) {
        for (Entry<String, Integer> entry : itemsToBuy.entrySet()) {
            String name = entry.getKey();
            Integer quantityToPurchase = entry.getValue();

            checkProductExists(name);
            checkSufficientStockForPurchase(name, quantityToPurchase);

            List<Product> products = store.getProductsByName(name);
            int finalQuantity = determinePurchaseQuantityWithPromotion(products, quantityToPurchase, name,
                    askToApplyPromotion, askToProceedWithoutPromotion);

            addProductToCart(cart, finalQuantity, products);
        }
    }

    private int determinePurchaseQuantityWithPromotion(
            List<Product> products, Integer requestedQuantity, String name,
            BiFunction<String, Integer, Answer> askToApplyPromotion,
            BiFunction<String, Integer, Answer> askToProceedWithoutPromotion
    ) {
        for (Product product : products) {
            if (!product.hasActivePromotion(LocalDate.now())) {
                continue;
            }

            int maxPromotionQuantity = product.calculateMaxPromotionQuantity(requestedQuantity);
            if (maxPromotionQuantity <= 0) {
                continue;
            }

            if (requestedQuantity < maxPromotionQuantity
                    && askToApplyPromotion.apply(name, maxPromotionQuantity - requestedQuantity) == YES) {
                return maxPromotionQuantity;
            }
            if (requestedQuantity > maxPromotionQuantity
                    && askToProceedWithoutPromotion.apply(name, requestedQuantity - maxPromotionQuantity) == NO) {
                return maxPromotionQuantity;
            }
        }
        return requestedQuantity;
    }

    private static void addProductToCart(Cart cart, int finalQuantity, List<Product> products) {
        int remainingQuantity = finalQuantity;
        for (Product product : products) {
            if (remainingQuantity <= 0) {
                break;
            }

            int quantityToAdd = Math.min(remainingQuantity, product.getQuantity());
            cart.addCart(product, quantityToAdd);
            remainingQuantity -= quantityToAdd;
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
