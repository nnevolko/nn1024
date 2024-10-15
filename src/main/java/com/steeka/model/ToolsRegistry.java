package com.steeka.model;

// TODO perhaps specify the quantity of each tool available at the moment supply is infinity

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ToolsRegistry {
    public final Map<String, Tool> toolsRegistry = new HashMap<>();
    public final Map<String, String> toolsNameToCode = new HashMap<>();

    public void register(Tool tool) {
        toolsRegistry.put(tool.getCode(), tool);
        //toolsNameToCode.put(tool.getName(), tool.getCode());
    }

    public Tool get(String toolCode) {
        return toolsRegistry.get(toolCode);
    }

    public Tool getToolByCode(String toolCode) {
        return toolsRegistry.get(toolCode);
    }

    public Tool getToolByName(String toolName) {
        String toolCode = toolsNameToCode.get(toolName);
        return toolsRegistry.get(toolCode);
    }

    public String getToolCodeByName(String toolName) {
        return toolsNameToCode.get(toolName.trim());
    }

    public boolean contains(String toolCode) {
        return toolsRegistry.containsKey(toolCode);
    }


    public void remove(String toolCode) {
        Tool tool = toolsRegistry.remove(toolCode);
        if (tool != null) {
            toolsNameToCode.remove(tool.getName());
        }
    }

    public Map<String, Tool> getToolsRegistry() {
        return toolsRegistry;
    }

    @Override
    public String toString() {
        return toolsRegistry.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }
}
