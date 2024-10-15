package com.steeka;

import com.steeka.model.Tool;
import com.steeka.model.ToolsRegistry;
import com.steeka.utility.ToolsNameToCodeMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ToolsNameToCodeMapperTest {


    @Test
    public void mapTest() {

        ToolsRegistry toolsRegistry = new ToolsRegistry();
        toolsRegistry.register(new Tool("CHNS", "Chainsaw", "Stihl"));
        toolsRegistry.register(new Tool("LADW", "Ladder", "Werner"));
        toolsRegistry.register(new Tool("JAKD", "Jackhammer", "DeWalt"));
        toolsRegistry.register( new Tool("JAKR", "Jackhammer", "Ridgid"));

        ToolsNameToCodeMapper mapper = new ToolsNameToCodeMapper(toolsRegistry);
        mapper.map(toolsRegistry);
        assertTrue(mapper.get("Ladder").equals("LADW"));
        assertTrue(mapper.get("Chainsaw").equals("CHNS"));
        //the code will be whatever is the first one that is entered
        //we can create a random algorithm to pick the code,
        //TODO perhaps refact to allow a list or code for a specific tool type
        assertTrue(mapper.get("Jackhammer").equals("JAKD"));
    }
}