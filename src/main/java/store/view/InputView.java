package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;
import store.util.ProductInputParser;

public class InputView {

    public Map<String, Integer> inputProductNameAndQuantity() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return ProductInputParser.parse(Console.readLine());
    }
}
