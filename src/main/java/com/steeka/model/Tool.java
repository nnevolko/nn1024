package com.steeka.model;

public class Tool {

    private String code;
    private String type;
    private String brand;

    private ToolCharge toolCharge;

    public Tool(){}
    public Tool(String code, String type, String brand){
        this.code = code;
        this.type = type;
        this.brand= brand;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setToolCharge(ToolCharge toolCharge) {
        this.toolCharge = toolCharge;
    }

    public ToolCharge getToolCharge() {
        return toolCharge;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tool{");
        sb.append("code='").append(code).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
