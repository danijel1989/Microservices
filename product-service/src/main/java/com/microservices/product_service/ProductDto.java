package com.microservices.product_service;

public class ProductDto {
	
	private int id;
	private String name;
	private int memory;
	
	
	public ProductDto(int id, String name, int memory) {
		this.id = id;
		this.name = name;
		this.memory = memory;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getMemory() {
		return memory;
	}


	public void setMemory(int memory) {
		this.memory = memory;
	}
	
	
	
	

}
