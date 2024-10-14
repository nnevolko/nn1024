package com.steeka;

import java.util.HashMap;
import java.util.Map;
//service that will return a list of tools
//this coudl be later refactored to call the database and etc
public class ToolsService {

    //TODO refactor to properties file
    public static final String toolsFile = "tools.txt";
    public static final String toolsChargesFile = "toolscharges.txt";

    public ToolsService(){

    }

    public ToolsRegistry getTools(){

        //1. get tools from file
        //2. get charges from file
        //3. map name to code
        //4. update tools with the tools charges
        ToolsFileLoader toolsLoader = new ToolsFileLoader();
        ToolsRegistry toolsRegistry=  toolsLoader.loadFromFile(toolsFile);

        ToolsChargesFileLoader toolsChargesLoader = new ToolsChargesFileLoader();
        ToolsChargeRegistry toolChargeRegistry = toolsChargesLoader.loadFromFile(toolsChargesFile);

        ToolsNameToCodeMapping nameToCodeMapper = new ToolsNameToCodeMapping(toolsRegistry);

        toolChargeRegistry.getCharges().forEach((name, charge) -> {
            String code = nameToCodeMapper.get(name);
            if (code != null) {
                Tool tool = toolsRegistry.get(code);
                if (tool != null) {
                    tool.setToolCharge(charge);
                }
            }
        });

        return toolsRegistry;
    }
}
