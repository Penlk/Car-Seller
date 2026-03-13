package ru.penlk.businessLayer.contracts.cars.models;

import ru.penlk.dataAcessLayer.entities.cars.Fuel;

public enum FuelContract {
    PETROL,
    DIESEL,
    ELECTRICITY;

    public static FuelContract mapToContract(Fuel fuel) {
        return switch (fuel) {
            case Fuel.PETROL -> FuelContract.PETROL;
            case Fuel.DIESEL -> FuelContract.DIESEL;
            case Fuel.ELECTRICITY -> FuelContract.ELECTRICITY;
        };
    }

    public static Fuel mapToFuel(FuelContract fuelContract) {
        return switch (fuelContract) {
            case FuelContract.PETROL -> Fuel.PETROL;
            case FuelContract.DIESEL -> Fuel.DIESEL;
            case FuelContract.ELECTRICITY -> Fuel.ELECTRICITY;
        };
    }
}
