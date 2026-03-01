package ru.penlk.businessLayer.contracts.cars.models;

import ru.penlk.dataAcessLayer.entities.cars.GearShiftBox;

public enum GearShiftBoxContract {
    Manual,
    Automatic;

    public static GearShiftBoxContract mapToContract(GearShiftBox gearShiftBox) {
        return switch (gearShiftBox) {
            case GearShiftBox.Manual -> GearShiftBoxContract.Manual;
            case GearShiftBox.Automatic -> GearShiftBoxContract.Automatic;
        };
    }

    public static GearShiftBox mapToGearShiftBox(GearShiftBoxContract gearShiftBoxContract) {
        return switch (gearShiftBoxContract) {
            case GearShiftBoxContract.Manual -> GearShiftBox.Manual;
            case GearShiftBoxContract.Automatic -> GearShiftBox.Automatic;
        };
    }
}
