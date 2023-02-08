package com.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.model.dto.ClienteBatchDTO;

@Repository
public interface BatchRepository extends JpaRepository<ClienteBatchDTO, String> {
	
	@Query(value = "SELECT * FROM dbbatch.batch_job_lists WHERE job_id = :id and error_message != \"Sucesso\"", nativeQuery = true)
	List<ClienteBatchDTO> findErrorById(@Param("id") String id);
	
	@Query(value = "SELECT * FROM dbbatch.batch_job_lists WHERE job_id = :id and error_message = \"Sucesso\"", nativeQuery = true)
	List<ClienteBatchDTO> findSuccessById(@Param("id") String id);

}
