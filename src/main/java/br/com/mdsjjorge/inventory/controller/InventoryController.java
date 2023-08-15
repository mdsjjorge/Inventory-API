package br.com.mdsjjorge.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mdsjjorge.inventory.model.Inventory;
import br.com.mdsjjorge.inventory.repository.InventoryRepository;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@GetMapping
	public List<Inventory> getAll(){		
		return inventoryRepository.findAll();		
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
	    Optional<Inventory> inventory = inventoryRepository.findById(id);
	    if (inventory.isPresent()) {
	        return ResponseEntity.ok(inventory.get());
	    } else {
	        String errorMessage = "Inventory item with ID " + id + " not found";
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	    }
	}
	
	@GetMapping("/howmany")
	public ResponseEntity<Long> getHowMany(@RequestParam(name = "complete", required = false) String completeParam) {
	    if (completeParam != null) {
	        if (completeParam.equals("true")) {
	            long trueCount = inventoryRepository.countByComplete(true);
	            return ResponseEntity.ok(trueCount);
	        } else if (completeParam.equals("false")) {
	            long falseCount = inventoryRepository.countByComplete(false);
	            return ResponseEntity.ok(falseCount);
	        }
	    }
	    long totalCount = inventoryRepository.count();
	    return ResponseEntity.ok(totalCount);
	}
	
	@PostMapping
	public String create(@RequestBody Inventory item ) {
		inventoryRepository.save(item);
		return "Inventory item created!";
	}

	@PostMapping("/createList")
	public String createList(@RequestBody List<Inventory> itens) {
		inventoryRepository.saveAll(itens);
		return "Inventory itens created!";
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Inventory item) {
		Optional<Inventory> inventory = inventoryRepository.findById(id);
		if (inventory.isPresent()) {
			if(id == item.getId()) {
				inventoryRepository.save(item);
		    	String successMessage = "Inventory item with ID " + id + " updated";
		        return ResponseEntity.ok(successMessage);
			} else {
				String errorMessage = "json body id and url id are different. Please include a corresponding id in the json body!";
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
			}
		} else {
	        String errorMessage = "Inventory item with ID " + id + " not found";
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	    }
	}
	
	@PutMapping("/updateList")
	public String updateList(@RequestBody List<Inventory> itens) {
		if(itens.size() > 0) {
			inventoryRepository.saveAll(itens);
		}
		return "Inventory itens has been updated!";
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Inventory> inventory = inventoryRepository.findById(id);
	    if (inventory.isPresent()) {
	    	inventoryRepository.deleteById(id);
	    	String successMessage = "Inventory item with ID " + id + " deleted";
	        return ResponseEntity.ok(successMessage);
	    } else {
	        String errorMessage = "Inventory item with ID " + id + " not found";
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	    }
	}
}
