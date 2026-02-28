package ru.penlk.dataBase.repositories.interfaces;

import ru.penlk.dataBase.repositories.interfaces.carParts.CarPartRepository;
import ru.penlk.dataBase.repositories.interfaces.cars.CarRepository;
import ru.penlk.dataBase.repositories.interfaces.nodes.NodeRepository;
import ru.penlk.dataBase.repositories.interfaces.orders.commonConfigurations.CommonConfigurationRepository;
import ru.penlk.dataBase.repositories.interfaces.orders.commonOrder.CommonOrderRepository;
import ru.penlk.dataBase.repositories.interfaces.orders.specialConfigurations.SpecialConfigurationRepository;
import ru.penlk.dataBase.repositories.interfaces.orders.specialOrders.SpecialOrderRepository;
import ru.penlk.dataBase.repositories.interfaces.orders.specialOrders.nodeSets.NodeSetRepository;
import ru.penlk.dataBase.repositories.interfaces.requireNodes.RequireNodeRepository;
import ru.penlk.dataBase.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dataBase.repositories.interfaces.users.compositionAdmins.CompositionAdminRepository;
import ru.penlk.dataBase.repositories.interfaces.users.managers.ManagerRepository;
import ru.penlk.dataBase.repositories.interfaces.users.systemAdmins.SystemAdminRepository;

public interface RepositoryContext {
    CarPartRepository getCarPartRepository();

    CarRepository getCarRepository();

    NodeRepository getNodeRepository();

    CommonConfigurationRepository getCommonConfigurationRepository();

    CommonOrderRepository getCommonOrderRepository();

    SpecialConfigurationRepository getSpecialConfigurationRepository();

    NodeSetRepository getNodeSetRepository();

    SpecialOrderRepository getSpecialOrderRepository();

    RequireNodeRepository getRequireNodeRepository();

    ClientRepository getClientRepository();

    CompositionAdminRepository getCompositionAdminRepository();

    ManagerRepository getManagerRepository();

    SystemAdminRepository getSystemAdminRepository();
}
