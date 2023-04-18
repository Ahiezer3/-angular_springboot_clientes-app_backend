package com.apirestangular.filters;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;

import com.apirestangular.models.entity.Cliente;

public class DinamicFilter implements Specification<Cliente>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombre;
	
	private String apellido;
	
	private String email;
	
	
	public DinamicFilter(String nombre, String apellido, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
	}


	@Override
	public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if( nombre != null ) {
			 predicates.add(criteriaBuilder.equal(root.get("nombre"), nombre));
			
		}
		
		if( apellido != null ) {
			 predicates.add(criteriaBuilder.equal(root.get("apellido"), apellido));
		}

		if( email != null ) {
			 predicates.add(criteriaBuilder.equal(root.get("email"), email));
		}
		
		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
