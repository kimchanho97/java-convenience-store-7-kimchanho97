package store.service;

import static store.constant.Answer.NO;
import static store.constant.Answer.YES;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import store.constant.Answer;
import store.domain.Cart;
import store.domain.ConvenienceStore;
import store.domain.Product;

public class CartService {

    private final ConvenienceStore store;

    public CartService(ConvenienceStore store) {
        this.store = store;
    }

    public void addItemsToCart(
            Cart cart,
            Map<String, Integer> itemsToPurchase,
            BiFunction<String, Integer, Answer> askToApplyPromotion,
            BiFunction<String, Integer, Answer> askToProceedWithoutPromotion) {

        itemsToPurchase.forEach((name, quantityToPurchase) -> {
            List<Product> products = store.getProducts(name);
            int finalQuantity = calculateFinalQuantityWithPromotion(products, quantityToPurchase, name,
                    askToApplyPromotion, askToProceedWithoutPromotion);

            addProductToCart(cart, finalQuantity, products);
        });
    }

    private int calculateFinalQuantityWithPromotion(
            List<Product> products,
            Integer requestedQuantity,
            String name,
            BiFunction<String, Integer, Answer> askToApplyPromotion,
            BiFunction<String, Integer, Answer> askToProceedWithoutPromotion) {

        for (Product product : products) {
            if (!product.hasActivePromotion(DateTimes.now())) {
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

    private void addProductToCart(Cart cart, int finalQuantity, List<Product> products) {
        int remainingQuantity = finalQuantity;

        for (Product product : products) {
            if (remainingQuantity <= 0) {
                break;
            }

            int quantityToAdd = Math.min(remainingQuantity, product.getQuantity());
            remainingQuantity -= quantityToAdd;
            cart.addItem(product, quantityToAdd);
        }
    }
}
