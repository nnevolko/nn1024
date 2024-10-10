package com.steeka;

import java.util.HashMap;
import java.util.Map;

public class ToolsChargeRegistry {

    private final static Map<String, ToolCharge> registry = new HashMap<>();

    public static void register(ToolCharge toolCharge){
        registry.put(toolCharge.getToolType(), toolCharge);
    }

    public static ToolCharge get(String toolCode){
        return registry.get(toolCode);
    }

    public static boolean contains(String toolCode){
        return registry.containsKey(toolCode);
    }
    public static void remove(String toolCode){
        registry.remove(toolCode);
    }

    public static void print(){
        registry.entrySet().forEach(System.out::println);
    }
}
