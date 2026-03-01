package ru.penlk.businessLayer.contracts.cars.models;

import ru.penlk.dataAcessLayer.entities.cars.Fuel;

public enum FuelContract {
    Petrol,
    Diesel,
    Electricity;

    public static FuelContract mapToContract(Fuel fuel) {
        return switch (fuel) {
            case Fuel.Petrol -> FuelContract.Petrol;
            case Fuel.Diesel -> FuelContract.Diesel;
            case Fuel.Electricity -> FuelContract.Electricity;
        };
    }

    public static Fuel mapToFuel(FuelContract fuelContract) {
        return switch (fuelContract) {
            case FuelContract.Petrol -> Fuel.Petrol;
            case FuelContract.Diesel -> Fuel.Diesel;
            case FuelContract.Electricity -> Fuel.Electricity;
        };
    }
}
