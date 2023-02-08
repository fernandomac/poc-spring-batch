package com.api.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.api.batch.listeners.JobCompletionNotificationListener;
import com.api.domain.repository.BatchRepository;
import com.api.model.dto.ClienteBatchResultDTO;

@Service
public class BatchService {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private BatchRepository batchRepository;
	@Autowired
	private ExecutionContext executionContext;

	public ClienteBatchResultDTO importarCliente(String input) {
		String batchId = UUID.randomUUID().toString();
		
		Job job = this.context.getBean("importUsuarioJob", Job.class);
		JobParameters jobParameters = new JobParametersBuilder().addLong("StartAt", System.currentTimeMillis())
				.addString("batchId", batchId).addString("file.input", input).toJobParameters();
		executionContext.putString("batchID", batchId);

		try {
			jobLauncher.run(job, jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}

		ClienteBatchResultDTO result = new ClienteBatchResultDTO(batchRepository.findSuccessById(batchId),batchRepository.findErrorById(batchId));
		return result;
	}

}
