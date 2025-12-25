package com.personal.shop.common.utility;

import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Objects;

@Getter
public class YearWeek implements Comparable<YearWeek> {
    private final int year;
    private final int week;

    public YearWeek(int year, int week) {
        this.year = year;
        this.week = week;
    }

    public static YearWeek from(LocalDate date) {
        WeekFields weekFields = WeekFields.ISO; // 월요일 시작, ISO-8601 기준
        int year = date.get(weekFields.weekBasedYear());
        int week = date.get(weekFields.weekOfWeekBasedYear());
        return new YearWeek(year, week);
    }

    public YearWeek plusWeeks(int weeks) {
        LocalDate monday = LocalDate
                .now()
                .withYear(this.year)
                .with(WeekFields.ISO.weekOfWeekBasedYear(), this.week)
                .with(WeekFields.ISO.dayOfWeek(), 1); // 월요일
        LocalDate result = monday.plusWeeks(weeks);
        return from(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YearWeek)) return false;
        YearWeek yearWeek = (YearWeek) o;
        return year == yearWeek.year && week == yearWeek.week;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, week);
    }

    @Override
    public String toString() {
        return year + "-W" + String.format("%02d", week);
    }

    @Override
    public int compareTo(YearWeek other) {
        int cmp = Integer.compare(this.year, other.year);
        return cmp != 0 ? cmp : Integer.compare(this.week, other.week);
    }
}
