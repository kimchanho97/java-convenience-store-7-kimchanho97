package store.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Product implements Comparable<Product> {

    private final String name;
    private final int price;
    private final int quantity;
    private final Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public boolean hasActivePromotion(LocalDate date) {
        return promotion != null && promotion.isActivePromotion(date);
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
