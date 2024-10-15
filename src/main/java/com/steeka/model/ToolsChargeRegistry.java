package com.steeka.model;

import java.util.HashMap;
import java.util.Map;

public class ToolsChargeRegistry {

    private Map<String, ToolCharge> charges = new HashMap<>();

    public void register(ToolCharge toolCharge) {
        charges.put(toolCharge.getToolType(), toolCharge);
    }

    public ToolCharge get(String toolCode) {
        return charges.get(toolCode);
    }

    public boolean contains(String toolCode) {
        return charges.containsKey(toolCode);
    }

    public void remove(String toolCode) {
        charges.remove(toolCode);
    }

    public Map<String, ToolCharge> getCharges() {
        return charges;
    }

    public void print() {
        charges.entrySet().forEach(System.out::println);
    }
}
