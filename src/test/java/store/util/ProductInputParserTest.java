package store.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductInputParserTest {

    @DisplayName("상품명과 수량 입력 형식이 유효한 경우 정상 파싱된다.")
    @ParameterizedTest
    @ValueSource(strings = {"[콜라-10]", "[콜라-10],[오렌지주스-5]", "[콜라-10], [오렌지주스-5]"})
    void testProductInputParser(String input) {
        Assertions.assertThatCode(() -> ProductInputParser.parse(input))
                .doesNotThrowAnyException();
    }
}