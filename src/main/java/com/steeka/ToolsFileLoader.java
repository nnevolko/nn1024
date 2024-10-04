package com.steeka;

import java.util.List;

//TODO add exception handling for handing input data
public class ToolsFileLoader implements FileLoader {

    public void processInputLines(List<String> inputLines) {

        inputLines.stream()
                .skip(1)
                .forEach(
                        line -> {
                            Tool tool = new Tool();
                            String[] toolString = line.split("\\s+");
                            tool.setCode(toolString[0]);
                            tool.setType(toolString[1]);
                            tool.setBrand(toolString[2]);
                            ToolsRegistry.register(tool);
                        }
                );

    }

}
