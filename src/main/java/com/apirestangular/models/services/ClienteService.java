package com.apirestangular.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.apirestangular.filters.DinamicFilter;
import com.apirestangular.models.entity.Cliente;

public interface ClienteService {
	
	List<Cliente>findAll();
	
	Page<Cliente>findAll(Pageable pageable);
	
	Cliente findById(Long id);
	
	Cliente save(Cliente cliente);
	
	void delete(Long id);
	
	List<Cliente> search(DinamicFilter dinamic);

}
