package com.steeka.model;


import java.math.BigDecimal;
import java.util.Objects;

public class ToolCharge {

    private String toolType;
    private BigDecimal dailyCharge;
    private boolean hasWeekdayCharge;
    private boolean hasWeekendCharge;
    private boolean hasHolidayCharge;

    public ToolCharge() {
        this.hasWeekdayCharge = false;
        this.hasWeekendCharge = false;
        this.hasHolidayCharge = false;
    }

    public ToolCharge(String toolType, BigDecimal dailyCharge, boolean hasWeekdayCharge, boolean hasWeekendCharge, boolean hasHolidayCharge) {
        this.toolType = toolType;
        this.dailyCharge = dailyCharge;
        this.hasWeekdayCharge = hasWeekdayCharge;
        this.hasWeekendCharge = hasWeekendCharge;
        this.hasHolidayCharge = hasHolidayCharge;
    }

    public ToolCharge(ToolCharge charge) {
        super();
        this.toolType = charge.getToolType();
        this.dailyCharge = charge.getDailyCharge();
        this.hasWeekdayCharge = charge.isHasWeekdayCharge();
        this.hasWeekendCharge = charge.isHasWeekendCharge();
        this.hasHolidayCharge = charge.isHasHolidayCharge();
    }


    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean isHasWeekdayCharge() {
        return hasWeekdayCharge;
    }

    public void setHasWeekdayCharge(boolean hasWeekdayCharge) {
        this.hasWeekdayCharge = hasWeekdayCharge;
    }

    public boolean isHasWeekendCharge() {
        return hasWeekendCharge;
    }

    public void setHasWeekendCharge(boolean hasWeekendCharge) {
        this.hasWeekendCharge = hasWeekendCharge;
    }

    public boolean isHasHolidayCharge() {
        return hasHolidayCharge;
    }

    public void setHasHolidayCharge(boolean hasHolidayCharge) {
        this.hasHolidayCharge = hasHolidayCharge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolCharge that = (ToolCharge) o;
        return hasWeekdayCharge == that.hasWeekdayCharge && hasWeekendCharge == that.hasWeekendCharge && hasHolidayCharge == that.hasHolidayCharge && Objects.equals(toolType, that.toolType) && Objects.equals(dailyCharge, that.dailyCharge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toolType, dailyCharge, hasWeekdayCharge, hasWeekendCharge, hasHolidayCharge);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ToolCharge{");
        sb.append("toolType='").append(toolType).append('\'');
        sb.append(", dailyCharge=").append(dailyCharge);
        sb.append(", hasWeekdayCharge=").append(hasWeekdayCharge);
        sb.append(", hasWeekendCharge=").append(hasWeekendCharge);
        sb.append(", hasHolidayCharge=").append(hasHolidayCharge);
        sb.append('}');
        return sb.toString();
    }
}
