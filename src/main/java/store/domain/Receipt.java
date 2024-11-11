package store.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.dto.ProductSummary;
import store.dto.PromotionSummary;

public class Receipt {

    private final Map<Product, Integer> items;
    private final int totalAmount;
    private final int promotionDiscount;
    private final int membershipDiscount;
    private final int finalAmount;


    public Receipt(Map<Product, Integer> items, int totalAmount, int promotionDiscount,
                   int membershipDiscount,
                   int finalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.finalAmount = finalAmount;
    }

    public List<ProductSummary> getProductSummaries() {
        Map<String, ProductSummary> summaryMap = new LinkedHashMap<>();

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            int totalPrice = product.getPrice() * quantity;

            summaryMap.merge(
                    product.getName(),
                    new ProductSummary(product.getName(), quantity, totalPrice),
                    (existing, newEntry) -> new ProductSummary(
                            existing.name(),
                            existing.quantity() + newEntry.quantity(),
                            existing.totalPrice() + newEntry.totalPrice()
                    )
            );
        }
        return new ArrayList<>(summaryMap.values());
    }


    public List<PromotionSummary> getPromotionSummaries() {
        Map<String, Integer> promotionMap = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getPromotion() != null) {
                int freeQuantity = product.getPromotionQuantity(quantity);
                promotionMap.merge(product.getName(), freeQuantity, Integer::sum);
            }
        }

        return promotionMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> new PromotionSummary(entry.getKey(), entry.getValue()))
                .toList();
    }

    public int getTotalQuantity() {
        return items.values().stream().mapToInt(Integer::valueOf).sum();
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }
}
