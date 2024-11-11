package store.view;

import java.util.List;
import java.util.Map;
import store.domain.ConvenienceStore;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Receipt;
import store.dto.ProductSummary;
import store.dto.PromotionSummary;

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

        System.out.printf("- %s %,d원 %s %s%n", product.getName(), product.getPrice(), stockInfo, promotionInfo);
    }

    private String getStockInfo(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }

    private String getPromotionInfo(Promotion promotion) {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }

    public void displayReceipt(Receipt receipt) {
        System.out.println("==============W 편의점================");
        System.out.printf("%-18s%s%10s%n", "상품명", "수량", "금액");
        displayProductSummaries(receipt.getProductSummaries());

        System.out.println("=============증     정===============");
        displayPromotionSummaries(receipt.getPromotionSummaries());

        System.out.println("====================================");
        System.out.printf("%-19s%d%,14d%n", "총구매액", receipt.getTotalQuantity(), receipt.getTotalAmount());
        System.out.printf("%-28s-%,d%n", "행사할인", receipt.getPromotionDiscount());
        System.out.printf("%-28s-%,d%n", "멤버십할인", receipt.getMembershipDiscount());
        System.out.printf("%-30s%,d%n", "내실돈", receipt.getFinalAmount());
    }

    private void displayProductSummaries(List<ProductSummary> productSummaries) {
        for (ProductSummary summary : productSummaries) {
            System.out.printf("%-19s%,d%,14d%n", summary.name(), summary.quantity(), summary.totalPrice());
        }
    }

    private void displayPromotionSummaries(List<PromotionSummary> promotionSummaries) {
        for (PromotionSummary promotion : promotionSummaries) {
            System.out.printf("%-19s%,d%n", promotion.name(), promotion.quantity());
        }
    }

}
