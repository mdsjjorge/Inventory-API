package br.com.mdsjjorge.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mdsjjorge.inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
