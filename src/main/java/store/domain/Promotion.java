package store.domain;

import java.time.LocalDateTime;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isActive(LocalDateTime date) {
        return date.isAfter(startDate) && date.isBefore(endDate);
    }

    public int getFreeQuantity(int requestedQuantity) {
        return (requestedQuantity / (buy + get)) * get;
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }
}
