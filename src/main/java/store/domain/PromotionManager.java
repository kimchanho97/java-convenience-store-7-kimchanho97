package store.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PromotionManager {

    private final Map<String, Promotion> promotions = new HashMap<>();

    private static class PromotionManagerHolder {
        private static final PromotionManager INSTANCE = new PromotionManager();
    }

    private PromotionManager() {
        loadPromotionsFromFile();
    }

    public static PromotionManager getInstance() {
        return PromotionManagerHolder.INSTANCE;
    }

    public Promotion findPromotionByName(String name) {
        return promotions.getOrDefault(name, null);
    }

    private void loadPromotionsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/promotions.md"))) {
            br.lines().skip(1).forEach(this::parseAndAddPromotion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseAndAddPromotion(String line) {
        String[] fields = line.split(",");

        String name = fields[0];
        int buy = Integer.parseInt(fields[1]);
        int get = Integer.parseInt(fields[2]);
        LocalDateTime startDate = LocalDate.parse(fields[3]).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(fields[4]).atStartOfDay();

        promotions.put(name, new Promotion(name, buy, get, startDate, endDate));
    }

}
