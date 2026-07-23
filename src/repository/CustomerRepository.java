package repository;

import domain.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CustomerRepository {

    private final Map<String, Customer> customersById = new HashMap<>();

    public List<Customer> findAll() {
        return new ArrayList<>(customersById.values());
    }

    public void save(Customer c) {
        customersById.put(c.getId(), c);
    }

    // Find customer by ID
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(customersById.get(id));
    }

}