package com.steeka;
/*

Might use a Builder Pattern here as the data is availble at once
and that would be cleaner than using a multipel argument constructor

 */

public class ToolCharge {

    private String toolCode;
    private String toolName;
    private double dailyCharge;
    private boolean hasWeekdayCharge;
    private boolean hasWeekendCharge;
    private boolean hasHolidayCharge;

    public ToolCharge(){

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

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
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
        return "ToolCharge{" +
                "toolCode='" + toolCode + '\'' +
                ", toolName='" + toolName + '\'' +
                ", dailyCharge=" + dailyCharge +
                ", hasWeekdayCharge=" + hasWeekdayCharge +
                ", hasWeekendCharge=" + hasWeekendCharge +
                ", hasHolidayCharge=" + hasHolidayCharge +
                '}';
    }

    /*
    private ToolCharge(ToolChargeBuilder builder) {
        this.dailyCharge = builder.dailyCharge;
        this.hasWeekdayCharge = builder.hasWeekdayCharge;
        this.hasWeekendCharge = builder.hasWeekendCharge;
        this.hasHolidayCharge = builder.hasHolidayCharge;

    }

    public static class ToolChargeBuilder {
        private String toolCode;
        private double dailyCharge;
        private boolean hasWeekdayCharge;
        private boolean hasWeekendCharge;
        private boolean hasHolidayCharge;

        public ToolChargeBuilder(String toolCode, double dailyCharge) {
            this.toolCode = toolCode;
            this.dailyCharge = dailyCharge;
            hasWeekdayCharge = false;
            hasWeekendCharge = false;
            hasHolidayCharge = false;
        }

        public void setHasWeekdayCharge(boolean hasWeekdayCharge) {
            this.hasWeekdayCharge = hasWeekdayCharge;
        }

        public void setHasWeekendCharge(boolean hasWeekendCharge) {
            this.hasWeekendCharge = hasWeekendCharge;
        }

        public void setHasHolidayCharge(boolean hasHolidayCharge) {
            this.hasHolidayCharge = hasHolidayCharge;
        }

        public ToolCharge build() {
            return new ToolCharge(this);
        }
    }
*/

}
