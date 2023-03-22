package com.apirestangular.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apirestangular.models.entity.Cliente;
import com.apirestangular.models.services.ClienteService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired ClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente>index(){
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/pagina/{page}")
	public Page<Cliente> index(@PathVariable Integer page){
		
		Pageable pageable = PageRequest.of(page, 2);
		
		return clienteService.findAll(pageable);
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		 Cliente cliente = null;
		 Map<String,Object> responseMap = new HashMap<>();;
		 
		 try {
			 cliente = clienteService.findById(id);
			 
		 } catch(DataAccessException e ) {
			 responseMap.put("mensaje", "Error al realizar la consulta.");
			 responseMap.put("error",e.getMessage()+", "+e.getMostSpecificCause().getMessage());
			 return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 
		 if( cliente == null ) {
			 responseMap.put("error", "El usuario con el id: "+id+" no existe.");
			 return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.NOT_FOUND);
		 }
		 
		 return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		 
	}
	
	@PostMapping("/clientes")
	public ResponseEntity<?>  create(@Valid @RequestBody Cliente cliente, BindingResult result){
		
		Cliente clienteNew = null;
		Map<String,Object> responseMap = new HashMap<>();;
		 
		if( result.hasErrors() ) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo: '"+err.getField()+"' tiene errores: "+err.getDefaultMessage())
					.collect(Collectors.toList());
			
			responseMap.put("errors", errors);
			
			return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.BAD_GATEWAY);
		}
		

		try {
			clienteNew = clienteService.save(cliente);
		} catch(DataAccessException e) {
			responseMap.put("mensaje", "Error al insertar el cliente");
			responseMap.put("error",e.getMessage()+", "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		responseMap.put("cliente", clienteNew);
		responseMap.put("mensaje", "Cliente creado con éxito.");
		
		return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.OK);
	}
	
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
		
		Cliente clienteUpdated = null;
	    Map<String,Object> responseMap = new HashMap<>();
		
	    if( result.hasErrors() ) {
	    	
	    	List<String> errors = result.getFieldErrors()
	    			.stream()
	    			.map(err -> "El campo: '"+err.getField()+"' tiene errores: "+err.getDefaultMessage())
	    			.collect(Collectors.toList());
	    	
	    	responseMap.put("errors", errors);
	    	
	    	return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.BAD_GATEWAY);
	    }
		
		try {
			Cliente clienteActual = clienteService.findById(id);
			
			if( clienteActual == null ) {
				 responseMap.put("error", "El usuario con el id: "+id+" no existe.");
				 return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.NOT_FOUND);
			} else {
				clienteActual.setApellido(cliente.getApellido());
				clienteActual.setEmail(cliente.getEmail());
				clienteActual.setNombre(cliente.getNombre());
				
				clienteUpdated = clienteService.save(clienteActual);
				
				responseMap.put("cliente", clienteUpdated);
				responseMap.put("mensaje", "Cliente actualizado con éxito.");

			}
	
		} catch(DataAccessException e) {
			responseMap.put("mensaje", "Error al realizar la actualización");
			responseMap.put("error", e.getMessage()+", "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.OK);
	}
	
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
	    Map<String,Object> responseMap = new HashMap<>();
		
		try {
			clienteService.delete(id);
			responseMap.put("mensaje", "Cliente eliminado con éxito.");
			
		} catch(DataAccessException e) {
			responseMap.put("error", "Error al realizar la eliminación: "+e.getMessage()+", "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
	}
}
