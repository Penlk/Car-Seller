package ru.penlk.dataAcessLayer.repositories.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.penlk.dataAcessLayer.repositories.interfaces.RepositoryContext;
import ru.penlk.dataAcessLayer.repositories.interfaces.carParts.CarPartRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.cars.CarRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.nodes.NodeRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonConfigurations.CommonConfigurationRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.commonOrder.CommonOrderRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialConfigurations.SpecialConfigurationRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders.SpecialOrderRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.orders.specialOrders.nodeSets.NodeSetRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.requireNodes.RequireNodeRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.clients.ClientRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.compositionAdmins.CompositionAdminRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.managers.ManagerRepository;
import ru.penlk.dataAcessLayer.repositories.interfaces.users.systemAdmins.SystemAdminRepository;

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
