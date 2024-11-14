package store.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionTest {

    @DisplayName("getFreeQuantity 메서드 테스트 - buy 2개당 get 1개 증정")
    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "2, 0",
            "3, 1",
            "4, 1",
            "5, 1",
    })
    void testGetFreeQuantityWithBuy2Get1(int requestedQuantity, int expected) {
        Promotion promotion = new Promotion("Buy2Get1", 2, 1,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59));

        assertThat(promotion.getFreeQuantity(requestedQuantity))
                .isEqualTo(expected);
    }

    @DisplayName("getFreeQuantity 메서드 테스트 - buy 3개당 get 2개 증정")
    @ParameterizedTest
    @CsvSource({
            "3, 0",
            "4, 0",
            "5, 2",
            "7, 2",
            "10, 4",
    })
    void testGetFreeQuantityWithBuy3Get2(int requestedQuantity, int expected) {
        Promotion promotion = new Promotion("Buy3Get2", 3, 2,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59));

        assertThat(promotion.getFreeQuantity(requestedQuantity))
                .isEqualTo(expected);
    }

}