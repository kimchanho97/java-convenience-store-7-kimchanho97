package store.service;

import static store.constant.Answer.YES;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Map;
import store.constant.Answer;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Receipt;

public class PaymentService {

    private final Answer isMembershipDiscount;
    private final Cart cart;

    public PaymentService(Answer isMembershipDiscount, Cart cart) {
        this.isMembershipDiscount = isMembershipDiscount;
        this.cart = cart;
    }

    public Receipt checkout() {
        int totalAmount = 0;
        int nonPromotionAmount = 0;
        int promotionDiscount = 0;
        int membershipDiscount = 0;

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            product.reduceQuantity(quantity);
            int productTotalAmount = quantity * product.getPrice();
            totalAmount += productTotalAmount;

            if (product.hasActivePromotion(DateTimes.now())) {
                promotionDiscount += product.getFreeQuantity(quantity) * product.getPrice();
            } else {
                nonPromotionAmount += productTotalAmount;
            }
        }

        if (isMembershipDiscount == YES) {
            membershipDiscount = calculateMembershipDiscount(nonPromotionAmount);
        }

        int finalAmount = totalAmount - promotionDiscount - membershipDiscount;

        return new Receipt(cart.getItems(), totalAmount, promotionDiscount, membershipDiscount, finalAmount);
    }

    private int calculateMembershipDiscount(int nonPromotionAmount) {
        int membershipDiscount = (int) (nonPromotionAmount * 0.3);
        return Math.min(membershipDiscount, 8000);
    }

}
