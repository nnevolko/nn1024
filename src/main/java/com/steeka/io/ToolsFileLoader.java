package com.steeka.io;

import com.steeka.model.Tool;
import com.steeka.model.ToolsRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ToolsFileLoader implements FileLoader<ToolsRegistry> {

    @Override
    public ToolsRegistry processInputLines(@NotNull List<String> inputLines) {

        ToolsRegistry toolsRegistry = new ToolsRegistry();
        inputLines.stream()
                .skip(1)
                .forEach(
                        line -> {
                            Tool tool = new Tool();
                            String[] toolString = line.split("\\s+");
                            tool.setCode(toolString[0]);
                            tool.setType(toolString[1]);
                            tool.setBrand(toolString[2]);
                            toolsRegistry.register(tool);
                        }
                );

        return toolsRegistry;
    }

}
