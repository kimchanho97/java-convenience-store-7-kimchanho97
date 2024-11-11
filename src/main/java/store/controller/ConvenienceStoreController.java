package store.controller;

import java.util.Map;
import store.domain.Cart;
import store.domain.ConvenienceStore;
import store.service.CartService;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {

    private final ConvenienceStore store;
    private final InputView inputView;
    private final OutputView outputView;
    private final CartService cartService;

    public ConvenienceStoreController(ConvenienceStore store, InputView inputView, OutputView outputView) {
        this.store = store;
        this.inputView = inputView;
        this.outputView = outputView;
        this.cartService = new CartService(store);
    }

    public void run() {
        outputView.displayConvenienceStore(store);
        Map<String, Integer> itemsToBuy = inputView.inputProductNameAndQuantity();

        Cart cart = new Cart();




             /*
             1. 보유한 편의점 상품 진열 - Store - LinkedHashMap<String, List<Product>>
             2. 구매하려는 상품명 입력 - LinkedHashMap<String, Integer>

             ---

             3. 카트에 상품을 담기(convenienceStoreService): Cart - LinkedHashMap<Product, Integer>
                3-1. 구매 개수 > 프로모션 개수보다 초과: 현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
                3-2. 추가 프로모션 가능: 현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
                3-3. 프로모션 상품이 아니거나 수량이 딱 맞는 경우 -> 그냥 진행

             ---

             4. 멤버십 할인 진행 여부 출력
             5. 결제 진행
                5-1. 멤버십 할인 진행
                5-2. 영수증 생성
             6. 영수증 출력
             */

    }
}
