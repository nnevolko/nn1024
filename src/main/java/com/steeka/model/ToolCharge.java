package com.steeka.model;


public class ToolCharge {

    private String toolCode;
    private String toolType;
    private double dailyCharge;
    private boolean hasWeekdayCharge;
    private boolean hasWeekendCharge;
    private boolean hasHolidayCharge;

    public ToolCharge() {

    }

    public ToolCharge(String toolCode, double dailyCharge) {
        this.toolCode = toolCode;
        this.dailyCharge = dailyCharge;
        hasWeekdayCharge = false;
        hasWeekendCharge = false;
        hasHolidayCharge = false;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(double dailyCharge) {
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
    public String toString() {
        final StringBuilder sb = new StringBuilder("ToolCharge{");
        sb.append("toolCode='").append(toolCode).append('\'');
        sb.append(", toolType='").append(toolType).append('\'');
        sb.append(", dailyCharge=").append(dailyCharge);
        sb.append(", hasWeekdayCharge=").append(hasWeekdayCharge);
        sb.append(", hasWeekendCharge=").append(hasWeekendCharge);
        sb.append(", hasHolidayCharge=").append(hasHolidayCharge);
        sb.append('}');
        return sb.toString();
    }

}
