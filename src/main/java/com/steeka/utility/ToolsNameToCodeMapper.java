package com.steeka.utility;

import com.steeka.model.Tool;
import com.steeka.model.ToolsRegistry;

import java.util.Map;
import java.util.stream.Collectors;

public class ToolsNameToCodeMapper {
    private Map<String, String> toolNameToCodeMapper;

    public ToolsNameToCodeMapper(ToolsRegistry tools) {
        map(tools.getToolsRegistry());
    }

    public void map(Map<String, Tool> tools) {
        toolNameToCodeMapper = tools.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getValue().getName(),
                        entry -> entry.getValue().getCode(),
                        (existing, replacement) -> existing
                ));
    }

    public Map<String, String> getMapper() {
        return toolNameToCodeMapper;
    }

    public String get(String name) {
        return toolNameToCodeMapper.get(name);
    }

    public void print() {
        this.toolNameToCodeMapper.entrySet().forEach(System.out::println);
    }
}