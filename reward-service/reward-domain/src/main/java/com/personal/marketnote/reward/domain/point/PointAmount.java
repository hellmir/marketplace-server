package com.personal.marketnote.reward.domain.point;

public class PointAmount {
    private final long value;

    private PointAmount(long value) {
        this.value = value;
    }

    public static PointAmount of(Long value) {
        long validated = value != null ? value : 0L;
        if (validated < 0) {
            throw new NegativeUserPointAmountException(validated);
        }
        return new PointAmount(validated);
    }

    public long add(long delta) {
        long result = value + delta;
        if (result < 0) {
            throw new NegativeUserPointAmountException(result);
        }
        return result;
    }

    public long getValue() {
        return value;
    }
}
