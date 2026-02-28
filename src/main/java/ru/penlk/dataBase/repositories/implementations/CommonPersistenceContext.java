package ru.penlk.dataBase.repositories.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataBase.repositories.interfaces.RepositoryContext;
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

@Getter
@AllArgsConstructor
public class CommonPersistenceContext implements RepositoryContext {
    private CarPartRepository carPartRepository;
    private CarRepository carRepository;
    private NodeRepository nodeRepository;
    private CommonConfigurationRepository commonConfigurationRepository;
    private CommonOrderRepository commonOrderRepository;
    private SpecialConfigurationRepository specialConfigurationRepository;
    private NodeSetRepository nodeSetRepository;
    private SpecialOrderRepository specialOrderRepository;
    private RequireNodeRepository requireNodeRepository;
    private ClientRepository clientRepository;
    private CompositionAdminRepository compositionAdminRepository;
    private ManagerRepository managerRepository;
    private SystemAdminRepository systemAdminRepository;
}
