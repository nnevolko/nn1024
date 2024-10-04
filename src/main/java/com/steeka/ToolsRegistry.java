package com.steeka;

// TODO perhaps specify the quantity of each tool available at the moment supply is infinity

import java.util.HashMap;
import java.util.Map;

public class ToolsRegistry {
    public static final Map<String, Tool> toolsRegistry = new HashMap<>();
    public static final Map<String, String> toolsNameToCode = new HashMap<>();

    public static void register(Tool tool) {
        toolsRegistry.put(tool.getCode(), tool);
        toolsNameToCode.put(tool.getName(), tool.getCode());
    }

    public static Tool getToolByCode(String toolCode) {
        return toolsRegistry.get(toolCode);
    }

    public static Tool getToolByName(String toolName) {
        String toolCode = toolsNameToCode.get(toolName);
        return toolsRegistry.get(toolCode);
    }
    public static String getToolCodeByName(String toolName){
        return toolsNameToCode.get(toolName.trim());
    }

    public static boolean contains(String toolCode) {
        return toolsRegistry.containsKey(toolCode);
    }

    public static void remove(String toolCode) {
        Tool tool = toolsRegistry.remove(toolCode);
        if (tool != null) {
            toolsNameToCode.remove(tool.getName());
        }
    }

    public static void print(){
        toolsRegistry.entrySet().forEach(System.out::println);
    }
}
