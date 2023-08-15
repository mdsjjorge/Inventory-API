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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/inventory", produces = {"application/json"})
@Tag(name = "InventoryAPI")
public class InventoryController {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Operation(summary = "Busca todos os itens de inventario cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens encontrados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição invalida"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de itens"),
    })
	@GetMapping
	public List<Inventory> getAll(){		
		return inventoryRepository.findAll();		
	}

	@Operation(summary = "Busca item de inventario por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisicao invalida"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de item"),
    })
	@GetMapping("id")
	public ResponseEntity<?> getById(@PathVariable Long id) {
	    Optional<Inventory> inventory = inventoryRepository.findById(id);
	    if (inventory.isPresent()) {
	        return ResponseEntity.ok(inventory.get());
	    } else {
	        String errorMessage = "Inventory item with ID " + id + " not found";
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	    }
	}
	
	@Operation(summary = "Retorna a quantidade de itens cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens encontrados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição invalido"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de itens"),
    })
	@Parameter(name = "complete", description = "Filtrar por completude dos itens (true, false ou all)", in = ParameterIn.QUERY)
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
	
	@Operation(summary = "Realiza o cadastro de um item de inventario", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição invalida"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public String create(@RequestBody Inventory item ) {
		inventoryRepository.save(item);
		return "Inventory item created!";
	}

	@Operation(summary = "Realiza o cadastro de uma lista de itens de inventario", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição invalida"),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
	@PostMapping("/createList")
	public String createList(@RequestBody List<Inventory> itens) {
		inventoryRepository.saveAll(itens);
		return "Inventory itens created!";
	}
	
	@Operation(summary = "Atualiza um item do inventario por ID", method = "PUT")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
	        @ApiResponse(responseCode = "404", description = "Item nao encontrado"),
	        @ApiResponse(responseCode = "400", description = "Dados invalidos"),
	        @ApiResponse(responseCode = "500", description = "Erro ao atualizar o item"),
	})
	@PutMapping("/id")
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
	
	@Operation(summary = "Atualiza uma lista de itens", method = "PUT")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
	        @ApiResponse(responseCode = "404", description = "Item nao encontrado"),
	        @ApiResponse(responseCode = "400", description = "Dados invalidos"),
	        @ApiResponse(responseCode = "500", description = "Erro ao atualizar o item"),
	})
	@PutMapping("/updateList")
	public String updateList(@RequestBody List<Inventory> itens) {
		if(itens.size() > 0) {
			inventoryRepository.saveAll(itens);
		}
		return "Inventory itens has been updated!";
	}
	
	@Operation(summary = "Exclui um item do inventario por ID", method = "DELETE")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "204", description = "Item excluido com sucesso"),
	        @ApiResponse(responseCode = "404", description = "Item nao encontrado"),
	        @ApiResponse(responseCode = "500", description = "Erro ao excluir o item"),
	})
	@DeleteMapping("id")
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
