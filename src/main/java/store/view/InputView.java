package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;
import store.constant.Answer;
import store.util.ProductInputParser;

public class InputView {

    public Map<String, Integer> inputProductNameAndQuantity() {
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return ProductInputParser.parse(Console.readLine());
    }

    public Answer askToProceedWithoutPromotion(String name, int quantity) {
        System.out.printf("\n현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", name, quantity);
        return Answer.findByAnswer(Console.readLine());
    }

    public Answer askToApplyPromotion(String name, int quantity) {
        System.out.printf("\n현재 %s은(는) %,d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", name, quantity);
        return Answer.findByAnswer(Console.readLine());
    }

    public Answer askToApplyMembership() {
        System.out.println("\n멤버십 할인을 받으시겠습니까? (Y/N)");
        return Answer.findByAnswer(Console.readLine());
    }

    public Answer askAdditionalPurchase() {
        System.out.println("\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Answer.findByAnswer(Console.readLine());
    }

}
