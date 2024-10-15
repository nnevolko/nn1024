package com.steeka.service;

import com.steeka.model.ToolsChargeRegistry;
import com.steeka.io.ToolsFileLoader;
import com.steeka.utility.ToolsNameToCodeMapper;
import com.steeka.model.ToolsRegistry;
import com.steeka.io.ToolsChargesFileLoader;
import com.steeka.model.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolsService {

    public static final String toolsFile = "tools.txt";
    public static final String toolsChargesFile = "toolscharges.txt";

    private static final Logger logger = LoggerFactory.getLogger(ToolsService.class);

    public ToolsService() {

    }

    public ToolsRegistry getTools() {

        //1. get tools from file
        //2. get charges from file
        //3. map name to code
        //4. update tools with the tools charges
        ToolsFileLoader toolsLoader = new ToolsFileLoader();
        ToolsRegistry toolsRegistry = toolsLoader.loadFromFile(toolsFile);

        ToolsChargesFileLoader toolsChargesLoader = new ToolsChargesFileLoader();
        ToolsChargeRegistry toolChargeRegistry = toolsChargesLoader.loadFromFile(toolsChargesFile);

        ToolsNameToCodeMapper nameToCodeMapper = new ToolsNameToCodeMapper(toolsRegistry);

        toolChargeRegistry.getCharges().forEach((name, charge) -> {
            String code = nameToCodeMapper.get(name);
            if (code != null) {
                Tool tool = toolsRegistry.get(code);
                if (tool != null) {
                    tool.setToolCharge(charge);
                }
            }
        });

        logger.info("Exiting ToolsService getTools()");
        return toolsRegistry;
    }
}
