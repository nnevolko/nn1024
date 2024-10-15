package com.steeka.service;

import com.steeka.io.ToolsChargesFileLoader;
import com.steeka.io.ToolsFileLoader;
import com.steeka.model.ToolCharge;
import com.steeka.model.ToolsChargeRegistry;
import com.steeka.model.ToolsRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolsService {

    public static final String toolsFile = "tools.txt";
    public static final String toolsChargesFile = "toolscharges.txt";

    private static final Logger logger = LoggerFactory.getLogger(ToolsService.class);

    public ToolsService() {

    }

    public ToolsRegistry getTools() {

        logger.info("Entering ToolsService getTools()");
        //1. get tools from file
        //2. get charges from file
        //3. map name to code
        //4. update tools with the tools charges
        ToolsFileLoader toolsLoader = new ToolsFileLoader();
        ToolsRegistry toolsRegistry = toolsLoader.loadFromFile(toolsFile);

        ToolsChargesFileLoader toolsChargesLoader = new ToolsChargesFileLoader();
        ToolsChargeRegistry toolsChargeRegistry = toolsChargesLoader.loadFromFile(toolsChargesFile);

        toolsRegistry.getToolsRegistry().forEach((typeCode, toolToUpdate) -> {
            ToolCharge charge = toolsChargeRegistry.get(toolToUpdate.getType());
            if (charge != null) {
                toolToUpdate.setToolCharge(charge);
            }
        });

        logger.info("Exiting ToolsService getTools()");
        return toolsRegistry;
    }
}
