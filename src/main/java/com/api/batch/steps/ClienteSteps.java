package com.api.batch.steps;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.api.batch.listeners.ClienteWriteListener;
import com.api.batch.listeners.StepSkipListener;
import com.api.batch.policy.ExceptionSkipPolicy;
import com.api.batch.processor.ClienteProcessor;
import com.api.model.ClienteModel;

@Configuration
public class ClienteSteps {

	private StepBuilder stepBuilder;

	@Bean
	public StepSkipListener skipListener() {
		return new StepSkipListener();
	}

	@Bean
	public ClienteWriteListener clienteWriteListener() {
		return new ClienteWriteListener();
	}

	@Bean
	public ExceptionSkipPolicy skipPolicy() {
		return new ExceptionSkipPolicy();
	}

	@Bean
	public ClienteProcessor processor() {
		return new ClienteProcessor();
	}

	@Bean
	public ExecutionContext executionContext() {
		return new ExecutionContext();
	}
	

	@Bean
	public Step cadastrarCliente(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			JdbcBatchItemWriter<ClienteModel> writerCliente, ItemReader<ClienteModel> readerCliente) {
		return new StepBuilder("step1", jobRepository).<ClienteModel, ClienteModel>chunk(1, transactionManager)
				.reader(readerCliente).processor(processor()).writer(writerCliente).listener(clienteWriteListener())
				.faultTolerant().skipPolicy(skipPolicy()).build();
	}

}
