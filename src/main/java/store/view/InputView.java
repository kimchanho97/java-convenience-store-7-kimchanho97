package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;
import store.constant.Answer;
import store.util.ProductInputParser;

public class InputView {

    public Map<String, Integer> inputProductNameAndQuantity() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return ProductInputParser.parse(Console.readLine());
    }

    public Answer askToProceedWithoutPromotion(String name, int quantity) {
        System.out.printf("현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", name, quantity);
        return Answer.findByAnswer(Console.readLine());
    }

    public Answer askToApplyPromotion(String name, int quantity) {
        System.out.printf("현재 %s은(는) %,d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", name, quantity);
        return Answer.findByAnswer(Console.readLine());
    }

}
