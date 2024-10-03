package com.steeka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ToolsRentalMain {
    public static void main(String[] args) {

        String fileName = "tools.txt";
        InputStream inputStream = ToolsRentalMain.class.getClassLoader().getResourceAsStream("tools.txt");

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }

        printInputStream(inputStream);
    }


    // print input stream
    private static void printInputStream(InputStream is) {

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
