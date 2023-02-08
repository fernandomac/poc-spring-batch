package com.api.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.batch.listeners.JobCompletionNotificationListener;
import com.api.batch.processor.ClienteProcessor;
import com.api.domain.repository.ClienteRepository;

@Configuration
@EnableBatchProcessing
public class ClienteJobConfig {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private JobBuilder jobBuilder;
	private ClienteRepository clienteRepository;

	@Bean
	public Job importUsuarioJob(@Qualifier("cadastrarCliente") Step cadastrarCliente, JobRepository jobRepository,
			JobCompletionNotificationListener listener) {
		return new JobBuilder("importUserJob", jobRepository).incrementer(new RunIdIncrementer()).listener(listener)
				.start(cadastrarCliente).build();
	}

}
