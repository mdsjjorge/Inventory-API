package br.com.mdsjjorge.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mdsjjorge.inventory.model.Inventory;
import br.com.mdsjjorge.inventory.repository.InventoryRepository;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	
	@Autowired
	private InventoryRepository inventoryRepositoy;
	
	@GetMapping
	public List<Inventory> getAll(){		
		return inventoryRepositoy.findAll();		
	}
	
	@PostMapping
	public String create(@RequestBody Inventory item ) {
		inventoryRepositoy.save(item);
		return "Inventory item created!";
	}

	@PostMapping("/createList")
	public String createList(@RequestBody List<Inventory> itens) {
		inventoryRepositoy.saveAll(itens);
		return "Inventory itens created!";
	}
	
	@PutMapping
	public String update(@RequestBody Inventory item) {
		if (item.getId() > 0) {
			inventoryRepositoy.save(item);
			return "Invenory item updated!";
		} else {
			return "Inventory item not found!";
		}
	}
	
	@DeleteMapping("{id}")
	public String delete(@PathVariable Long id) {
		if (inventoryRepositoy.existsById(id)) {
			inventoryRepositoy.deleteById(id);
			return "Inventory item deleted!";
		} else {
			return "Inventory item not found!";
		}
	}
}
