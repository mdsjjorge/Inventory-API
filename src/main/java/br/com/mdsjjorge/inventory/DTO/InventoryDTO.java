package br.com.mdsjjorge.inventory.DTO;

import br.com.mdsjjorge.inventory.model.Inventory;

public class InventoryDTO {
	private Long id;
	private String name;
	private String category;
	private Double value;
	private Boolean complete;
	
	public InventoryDTO() {
		super();
	}		
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Boolean getComplete() {
		return complete;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

	public InventoryDTO convertToDTO(Inventory inventory) {
	    InventoryDTO dto = new InventoryDTO();
	    dto.setId(inventory.getId());
	    dto.setName(inventory.getName());
	    dto.setCategory(inventory.getCategory());
	    dto.setValue(inventory.getValue());
	    dto.setComplete(inventory.getComplete());
	    return dto;
	}
}
