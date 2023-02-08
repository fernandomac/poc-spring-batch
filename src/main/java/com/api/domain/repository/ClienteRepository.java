package com.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.model.ClienteModel;
import com.api.model.dto.ClienteBatchDTO;
public interface ClienteRepository extends JpaRepository<ClienteModel, String> {

	@Modifying
	@Query(value = "INSERT INTO batch_job_lists (error_id, error_message, job_id, cliente_nome, cliente_cpf) VALUES (:errorId, :errorMessage, :jobId, :clienteNome, :clienteCpf);", nativeQuery = true)
	void saveError(@Param("errorId") String errorId, @Param("errorMessage") String errorMessage,
			@Param("jobId") String jobId, @Param("clienteNome") String clienteNome, @Param("clienteCpf") String clienteCpf);



}
