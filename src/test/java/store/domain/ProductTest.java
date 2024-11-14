package store.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductTest {

    @DisplayName("calculateMaxPromotionQuantity 메서드 테스트 - buy 2개당 get 1개 증정, 충분한 재고")
    @ParameterizedTest
    @CsvSource({
            "2, 3",
            "3, 3",
            "4, 3",
            "5, 6",
            "7, 6",
            "8, 9",
    })
    void testCalculateMaxPromotionQuantityWithBuy2Get1(int requestedQuantity, int expected) {
        Promotion promotion = new Promotion("Buy2Get1", 2, 1,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59));
        Product product = new Product("콜라", 1000, 50, promotion);

        assertThat(product.calculateMaxPromotionQuantity(requestedQuantity))
                .isEqualTo(expected);
    }

    @DisplayName("calculateMaxPromotionQuantity 메서드 테스트 - buy 3개당 get 2개 증정, 재고 부족")
    @ParameterizedTest
    @CsvSource({
            "2, 0",
            "3, 5",
            "5, 5",
            "7, 5",
            "8, 5",
            "9, 5",
    })
    void testCalculateMaxPromotionQuantityWithLimitedStock(int requestedQuantity, int expected) {
        Promotion promotion = new Promotion("Buy3Get2", 3, 2,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59));
        Product product = new Product("사이다", 1000, 8, promotion);

        assertThat(product.calculateMaxPromotionQuantity(requestedQuantity))
                .isEqualTo(expected);
    }

}