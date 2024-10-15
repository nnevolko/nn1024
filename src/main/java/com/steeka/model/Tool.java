package com.steeka.model;

public class Tool {

    private String code;
    private String type;
    private String brand;
    private String name;

    private ToolCharge toolCharge;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        sb.append(", name='").append(name).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
