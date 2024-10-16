package com.steeka.model;

import org.jetbrains.annotations.NotNull;

public class RentalAgreement {

    public static RentalAgreementDTO generateRentalAgreement(@NotNull RentalItem rentedTool, @NotNull ToolsRegistry toolsRegistry) {
        return new RentalAgreementDTO(rentedTool, toolsRegistry);
    }

    public static void printRentalAgreement(@NotNull RentalAgreementDTO agreementDTO) {
        System.out.println(agreementDTO);
    }
}
