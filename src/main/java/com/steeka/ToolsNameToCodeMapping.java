package com.steeka;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ToolsNameToCodeMapping {
    private  Map<String, String> toolNameToCodeMapper;

    ToolsNameToCodeMapping(ToolsRegistry tools){
        map(tools.getToolsRegistry());
    }

    //TODO contact Dave for clarification
    // There is duplicate data in the file for the "ladder"
    // At this point skip the second line

    public void map(Map<String, Tool> tools){
        toolNameToCodeMapper = tools.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getValue().getName(),
                        entry -> entry.getValue().getCode(),
                        (existing, replacement) -> existing
                ));
    }

    public Map<String, String> getMapper(){
        return toolNameToCodeMapper;
    }

    public String get(String name){
        return toolNameToCodeMapper.get(name);
    }

    public void print() {
        this.toolNameToCodeMapper.entrySet().forEach(System.out::println);
    }
}