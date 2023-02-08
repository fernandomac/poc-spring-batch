package com.api.batch.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.api.batch.processor.validator.ClienteBatchStatus;
import com.api.batch.processor.validator.DataValidation;
import com.api.domain.repository.BatchRepository;
import com.api.model.ClienteModel;
import com.api.model.dto.ClienteBatchDTO;

import jakarta.validation.Valid;

public class ClienteProcessor extends DataValidation implements ItemProcessor<ClienteModel, ClienteModel>,
		ItemWriteListener<ClienteModel>, ItemProcessListener<ClienteModel, ClienteModel> {

	@Autowired
	private BatchRepository batchRepository;

	private static final Logger log = LoggerFactory.getLogger(ClienteModel.class);

	private static String key;

	private List<ClienteModel> validados = new ArrayList<ClienteModel>();

	private List<ClienteBatchDTO> status = new ArrayList<ClienteBatchDTO>();

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		key = (String) stepExecution.getJobExecution().getJobParameters().getParameter("batchId").getValue();

	}

	@AfterStep
	public void limparLista() {
		log.info("Lista: " + status.toString());
		status.forEach(cliente -> batchRepository.save(cliente));
		status.clear();
		log.info("Lista Clear " + status.toString());
	}

	@Override
	public ClienteModel process(@Valid ClienteModel item) throws Exception {
		item = removerMascaras(item);

		if (validados.contains(item)) {
			log.info("!X! JÁ PROCESSADO !X! >> " + item);
			return null;
		} else {
			log.info("!!! PROCESSANDO !!! >>  " + item + " | " + key);
			validados.add(item);
			ClienteBatchDTO result = converter(item, key);
			result.setErrorMessage(verificarCliente(item));
			item.setTipoCadNacional(result.getTipoCadNacional());
			if (result.getErrorMessage().length() != 0) {
				log.info("!X! ERRO AO PROCESSAR !X! >> {} | {}", result, result.getErrorMessage());		
				status.add(result);
				return null;
			} else {
				result.setErrorMessage(ClienteBatchStatus.SUCESSO.getValue());
				log.info("!!! PROCESSADO : " + result + " | " + key);
				batchRepository.save(result);
				return item;

			}

		}

	}

	@Override
	public void onWriteError(Exception exception, Chunk<? extends ClienteModel> items) {
		ClienteBatchDTO error = converter(items.getItems().get(0), key);
		if (exception instanceof DuplicateKeyException) {
			error.setErrorMessage(" {" + ClienteBatchStatus.CADASTRADO.getValue() + "} ");
			status.add(error);

			log.info("!X! ERRO AO SALVAR NO BANCO | CLIENTE JÁ CADASTRADO  > " + error + " | " + exception);

		} else {
			log.info("!X! ERRO AO SALVAR NO BANCO | UNDEFINED > " + error + " | " + exception);
			error.setErrorMessage(" {" + ClienteBatchStatus.Undefined.getValue() + "} ");
			status.add(error);

		}
	}

	@Override
	public void onProcessError(ClienteModel item, Exception e) {
		log.info("!X! ERRO AO PROCESSAR | {} > {}", e, item);		
		ClienteBatchDTO error = converter(item, key);
		error.setErrorMessage(e.getMessage());


	}

}
