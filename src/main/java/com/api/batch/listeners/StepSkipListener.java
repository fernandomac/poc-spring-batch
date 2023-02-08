package com.api.batch.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.domain.repository.ClienteRepository;
import com.api.model.ClienteModel;
@Component
public class StepSkipListener implements SkipListener<ClienteModel, ClienteModel> {
	private static final Logger log = LoggerFactory.getLogger(ClienteModel.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void onSkipInWrite(ClienteModel item, Throwable t) {
		log.info("Error writting {} |", item);

	}

	@Override
	public void onSkipInProcess(ClienteModel item, Throwable t) {
		log.info("Error processing {}", item);
		SkipListener.super.onSkipInProcess(item, t);
	}

	@Override
	public void onSkipInRead(Throwable t) {
		log.info("Error reading {}", t.getMessage());
		SkipListener.super.onSkipInRead(t);
	}
}
