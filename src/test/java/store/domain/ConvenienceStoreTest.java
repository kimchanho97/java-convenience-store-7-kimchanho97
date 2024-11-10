package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConvenienceStoreTest {

    @DisplayName("ConvenienceStore를 생성하면 Product 객체들을 불러온다.")
    @Test
    void testCreateProducts() {
        ConvenienceStore convenienceStore = new ConvenienceStore();

        assertThat(convenienceStore.getInventory()).isNotEmpty();
    }
}