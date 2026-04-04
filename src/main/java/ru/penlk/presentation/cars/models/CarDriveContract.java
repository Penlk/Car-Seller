package ru.penlk.presentation.cars.models;

import ru.penlk.dao.entities.cars.CarDrive;

public enum CarDriveContract {
    FRONT,
    REAR,
    FULL;

    public static CarDriveContract mapToContract(CarDrive carDrive) {
        return switch (carDrive) {
            case CarDrive.FRONT -> CarDriveContract.FRONT;
            case CarDrive.REAR -> CarDriveContract.REAR;
            case CarDrive.FULL -> CarDriveContract.FULL;
        };
    }

    public static CarDrive mapToCarDrive(CarDriveContract carDriveContract) {
        return switch (carDriveContract) {
            case CarDriveContract.FRONT -> CarDrive.FRONT;
            case CarDriveContract.REAR -> CarDrive.REAR;
            case CarDriveContract.FULL -> CarDrive.FULL;
        };
    }
}
