package com.steeka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public interface FileLoader {

    void processInputLines(List<String> inputLines);

    // load from file
    default void loadFromFile(String fileName) {

        InputStream inputStream = ToolsRentalMain.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        List<String> inputLines = processInputStream(inputStream);
        processInputLines(inputLines);

    }

    // load input stream into a string list
    default List<String> processInputStream(InputStream is) {
        List<String> inputLines = new ArrayList<>();
        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                inputLines.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputLines;
    }

}
