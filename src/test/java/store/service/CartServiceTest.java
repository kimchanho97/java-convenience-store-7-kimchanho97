package store.service;

import static store.constant.Answer.NO;
import static store.constant.Answer.YES;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.Cart;
import store.domain.ConvenienceStore;

class CartServiceTest {

    private CartService cartService;
    private ConvenienceStore store;
    private Cart cart;

    @BeforeEach
    void setUp() {
        store = new ConvenienceStore();
        cartService = new CartService(store);
        cart = new Cart();
    }

    @DisplayName("프로모션 추가 수량을 허용하고 프로모션 없이 구매")
    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 3",
            "3, 3",
            "4, 4",
            "5, 6",
            "6, 6",
            "7, 7",
            "8, 9",
            "9, 9",
            "10, 10",
            "11, 11",
    })
    void testAddItemToCart1(int before, int after) {
        // Given
        Map<String, Integer> itemsToBuy = new HashMap<>();
        itemsToBuy.put("콜라", before);

        // When
        cartService.addItemToCart(
                cart,
                itemsToBuy,
                (name, quantity) -> YES,  // 프로모션 추가 허용
                (name, quantity) -> YES   // 프로모션 없이 구매
        );

        // Then
        int totalQuantityInCart = cart.getItems().values().stream().mapToInt(Integer::intValue).sum();
        Assertions.assertThat(totalQuantityInCart).isEqualTo(after);
    }

    @DisplayName("프로모션 추가 수량을 거부하는 경우 프로모션 없이 구매")
    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 2",
            "3, 3",
            "4, 4",
            "5, 5",
            "6, 6",
            "7, 7",
            "8, 8",
            "9, 9",
            "10, 10",
            "11, 11",
    })
    void testAddItemToCart2(int before, int after) {
        // Given
        Map<String, Integer> itemsToBuy = new HashMap<>();
        itemsToBuy.put("콜라", before);

        // When
        cartService.addItemToCart(
                cart,
                itemsToBuy,
                (name, quantity) -> NO,  // 프로모션 추가 허용
                (name, quantity) -> YES   // 프로모션 없이 구매
        );

        // Then
        int totalQuantityInCart = cart.getItems().values().stream().mapToInt(Integer::intValue).sum();
        Assertions.assertThat(totalQuantityInCart).isEqualTo(after);
    }

    @DisplayName("프로모션 추가 수량을 허용하고 프로모션 없이 구매")
    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 3",
            "3, 3",
            "4, 3",
            "5, 6",
            "6, 6",
            "7, 6",
            "8, 9",
            "9, 9",
            "10, 9",
            "11, 9",
    })
    void testAddItemToCart3(int before, int after) {
        // Given
        Map<String, Integer> itemsToBuy = new HashMap<>();
        itemsToBuy.put("콜라", before);

        // When
        cartService.addItemToCart(
                cart,
                itemsToBuy,
                (name, quantity) -> YES,  // 프로모션 추가 허용
                (name, quantity) -> NO   // 프로모션 없이 구매
        );

        // Then
        int totalQuantityInCart = cart.getItems().values().stream().mapToInt(Integer::intValue).sum();
        Assertions.assertThat(totalQuantityInCart).isEqualTo(after);
    }

    @DisplayName("프로모션 추가 수량을 거부하는 경우 프로모션 없이 구매")
    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 2",
            "3, 3",
            "4, 3",
            "5, 5",
            "6, 6",
            "7, 6",
            "8, 8",
            "9, 9",
            "10, 9",
            "11, 9",
    })
    void testAddItemToCart4(int before, int after) {
        // Given
        Map<String, Integer> itemsToBuy = new HashMap<>();
        itemsToBuy.put("콜라", before);

        // When
        cartService.addItemToCart(
                cart,
                itemsToBuy,
                (name, quantity) -> NO,  // 프로모션 추가 허용
                (name, quantity) -> NO   // 프로모션 없이 구매
        );

        // Then
        int totalQuantityInCart = cart.getItems().values().stream().mapToInt(Integer::intValue).sum();
        Assertions.assertThat(totalQuantityInCart).isEqualTo(after);
    }

}