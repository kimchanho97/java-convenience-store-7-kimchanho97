package store.domain;

import static store.exception.message.CartExceptionMessage.EXCEEDS_STOCK_QUANTITY;

import java.time.LocalDateTime;
import java.util.Objects;
import store.exception.CustomException.ExceedStockQuantityException;

public class Product implements Comparable<Product> {

    private final String name;
    private final int price;
    private final Promotion promotion;
    private int quantity;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public boolean hasActivePromotion(LocalDateTime date) {
        return promotion != null && promotion.isActive(date);
    }

    public int calculateMaxPromotionQuantity(int requestedQuantity) {
        if (promotion == null || quantity <= 0 || requestedQuantity <= 0) {
            return 0;
        }

        int applicablePromotionSets = 0;
        int remainingRequestedQuantity = requestedQuantity;
        int promotionSetSize = promotion.getBuy() + promotion.getGet();

        while (remainingRequestedQuantity >= promotion.getBuy()) {
            applicablePromotionSets += 1;
            remainingRequestedQuantity -= promotionSetSize;
        }

        return Math.min(quantity / promotionSetSize, applicablePromotionSets) * promotionSetSize;
    }

    public int calculateTotalAmount(int requestedQuantity) {
        return price * requestedQuantity;
    }

    public void reduceStock(int requestedQuantity) {
        if (quantity < requestedQuantity) {
            throw new ExceedStockQuantityException(EXCEEDS_STOCK_QUANTITY);
        }
        quantity -= requestedQuantity;
    }

    public int getPromotionQuantity(int requestedQuantity) {
        return promotion.calculateFreeQuantity(requestedQuantity);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int compareTo(Product other) {
        if (this.promotion == null && other.promotion != null) {
            return 1;
        }
        if (this.promotion != null && other.promotion == null) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name) && Objects.equals(promotion,
                product.promotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, promotion);
    }
}
