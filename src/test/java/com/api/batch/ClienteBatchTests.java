package com.api.batch;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.UUID;

import javax.sql.DataSource;

import org.dozer.Mapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.api.domain.repository.BatchRepository;
import com.api.model.dto.ClienteBatchDTO;
import com.api.model.dto.ClienteBatchResultDTO;

@ExtendWith(MockitoExtension.class)
@SpringBatchTest
@SpringBootTest
public class ClienteBatchTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@Autowired
	private BatchRepository batchRepository;
	
	private JdbcTemplate jdbcTemplate;

	private String batchId;
	@Autowired
	private Mapper mapper;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@AfterEach
	public void cleanUp() {
		jobRepositoryTestUtils.removeJobExecutions();
		jdbcTemplate.update("DELETE FROM dbbatch.cliente WHERE (cliente_cad = '08462249767');");
		jdbcTemplate.update("DELETE FROM dbbatch.cliente WHERE (cliente_cad = '15508435000119');");
		jdbcTemplate.update("DELETE FROM dbbatch.cliente WHERE (cliente_cad = '44235172808');");
		jdbcTemplate.update("DELETE FROM dbbatch.cliente WHERE (cliente_cad = '48848934684');");
		jdbcTemplate.update("DELETE FROM dbbatch.cliente WHERE (cliente_cad = '49331219000162');");
		jdbcTemplate.update("DELETE FROM dbbatch.cliente WHERE (cliente_cad = '123343');");
		jdbcTemplate.update("DELETE FROM dbbatch.cliente WHERE (cliente_cad = '14286975000132');");
		jdbcTemplate.update("DELETE FROM dbbatch.cliente WHERE (cliente_cad = '43663457000199');");
	}

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		batchId = "mock-id-test " + UUID.randomUUID().toString();

	}

	@Test
	public void deveCriarClientes(@Autowired Job job) throws Exception {
		ClienteBatchResultDTO result = new ClienteBatchResultDTO();
		ArrayList<ClienteBatchDTO> falhas = new ArrayList<ClienteBatchDTO>();
		ArrayList<ClienteBatchDTO> sucessos = new ArrayList<ClienteBatchDTO>();
		JobParametersBuilder paramsBuilder = new JobParametersBuilder();
		paramsBuilder.addString("file.input", "planilhas_teste\\sample-cadastro-cliente.csv");
		paramsBuilder.addString("batchId", batchId);

		// when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(paramsBuilder.toJobParameters());

		
		
		
		// then
		result.setFalha(batchRepository.findErrorById(batchId));
		result.setSuccesso(batchRepository.findSuccessById(batchId));
		result.getFalha().sort((o1, o2) -> o1.getNome().compareTo(o2.getNome()));
		result.getSuccesso().sort((o1, o2) -> o1.getNome().compareTo(o2.getNome()));

		System.out.println(result.getFalha().toString());
		System.out.println(result.getSuccesso().toString());

		assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		assertEquals(5, result.getSuccesso().size());
		assertEquals(4, result.getFalha().size());		
		assertEquals("48848934684", result.getSuccesso().get(0).getCadastroNacional());
		assertEquals("14286975000132", result.getFalha().get(0).getCadastroNacional());
	}

}
