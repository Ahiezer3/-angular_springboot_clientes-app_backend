package com.apirestangular.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.apirestangular.models.entity.Cliente;

public interface IClienteDAO extends JpaRepository<Cliente,Long>, JpaSpecificationExecutor<Cliente>{

}
