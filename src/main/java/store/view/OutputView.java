package store.view;

import java.util.List;
import java.util.Map;
import store.domain.ConvenienceStore;
import store.domain.Product;
import store.domain.Promotion;

public class OutputView {

    public void displayConvenienceStore(ConvenienceStore store) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
        
        Map<String, List<Product>> inventory = store.getInventory();
        inventory.values().forEach(this::displayProducts);
    }

    private void displayProducts(List<Product> products) {
        products.forEach(this::displayProduct);
    }

    private void displayProduct(Product product) {
        String stockInfo = getStockInfo(product.getQuantity());
        String promotionInfo = getPromotionInfo(product.getPromotion());

        System.out.printf("- %s %,d원 %s개 %s%n", product.getName(), product.getPrice(), stockInfo, promotionInfo);
    }

    private String getStockInfo(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.valueOf(quantity);
    }

    private String getPromotionInfo(Promotion promotion) {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }


}
