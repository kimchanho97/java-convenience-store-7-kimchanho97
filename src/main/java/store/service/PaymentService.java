package store.service;

import static store.constant.Answer.NO;

import camp.nextstep.edu.missionutils.DateTimes;
import store.constant.Answer;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Receipt;

public class PaymentService {

    private final Answer isMembershipDiscountApplied;
    private final Cart cart;

    public PaymentService(Answer isMembershipDiscountApplied, Cart cart) {
        this.isMembershipDiscountApplied = isMembershipDiscountApplied;
        this.cart = cart;
    }

    public Receipt checkout() {
        int totalAmount = calculateTotalAmount();
        int defaultProductTotalAmount = calculateDefaultProductTotalAmount();
        int totalPromotionDiscount = calculateTotalPromotionDiscount();
        int membershipDiscount = calculateMembershipDiscount(defaultProductTotalAmount);
        int finalAmount = totalAmount - totalPromotionDiscount - membershipDiscount;

        processPurchase();

        return new Receipt(cart.getItems(), totalAmount, totalPromotionDiscount, membershipDiscount, finalAmount);
    }

    private void processPurchase() {
        cart.getItems().forEach(Product::reduceQuantity);
    }

    private int calculateTotalAmount() {
        return cart.getItems().entrySet().stream()
                .mapToInt(entry -> calculateProductAmount(entry.getKey(), entry.getValue()))
                .sum();
    }

    private int calculateDefaultProductTotalAmount() {
        return cart.getItems().entrySet().stream()
                .filter(entry -> !entry.getKey().hasActivePromotion(DateTimes.now()))
                .mapToInt(entry -> calculateProductAmount(entry.getKey(), entry.getValue()))
                .sum();
    }

    private int calculateTotalPromotionDiscount() {
        return cart.getItems().entrySet().stream()
                .filter(entry -> entry.getKey().hasActivePromotion(DateTimes.now()))
                .mapToInt(entry -> entry.getKey().getFreeQuantity(entry.getValue()) * entry.getKey().getPrice())
                .sum();
    }

    private int calculateProductAmount(Product product, int quantity) {
        return product.getPrice() * quantity;
    }

    private int calculateMembershipDiscount(int baseAmount) {
        if (isMembershipDiscountApplied == NO) {
            return 0;
        }
        int discount = (int) (baseAmount * 0.3);
        return Math.min(discount, 8000);
    }

}
