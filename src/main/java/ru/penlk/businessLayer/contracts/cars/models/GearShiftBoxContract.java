package ru.penlk.businessLayer.contracts.cars.models;

import ru.penlk.dataAcessLayer.entities.cars.GearShiftBox;

public enum GearShiftBoxContract {
    MANUAL,
    AUTOMATIC;

    public static GearShiftBoxContract mapToContract(GearShiftBox gearShiftBox) {
        return switch (gearShiftBox) {
            case GearShiftBox.MANUAL -> GearShiftBoxContract.MANUAL;
            case GearShiftBox.AUTOMATIC -> GearShiftBoxContract.AUTOMATIC;
        };
    }

    public static GearShiftBox mapToGearShiftBox(GearShiftBoxContract gearShiftBoxContract) {
        return switch (gearShiftBoxContract) {
            case GearShiftBoxContract.MANUAL -> GearShiftBox.MANUAL;
            case GearShiftBoxContract.AUTOMATIC -> GearShiftBox.AUTOMATIC;
        };
    }
}
