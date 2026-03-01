package ru.penlk.businessLayer.contracts.cars.models;

import ru.penlk.dataAcessLayer.entities.cars.CarDrive;

public enum CarDriveContract {
    Front,
    Rear,
    Full;

    public static CarDriveContract mapToContract(CarDrive carDrive) {
        return switch (carDrive) {
            case CarDrive.Front -> CarDriveContract.Front;
            case CarDrive.Rear -> CarDriveContract.Rear;
            case CarDrive.Full -> CarDriveContract.Full;
        };
    }

    public static CarDrive mapToCarDrive(CarDriveContract carDriveContract) {
        return switch (carDriveContract) {
            case CarDriveContract.Front -> CarDrive.Front;
            case CarDriveContract.Rear -> CarDrive.Rear;
            case CarDriveContract.Full -> CarDrive.Full;
        };
    }
}
