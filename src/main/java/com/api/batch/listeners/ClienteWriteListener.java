package com.api.batch.listeners;

import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.api.domain.repository.ClienteRepository;
import com.api.model.ClienteModel;

public class ClienteWriteListener implements ItemWriteListener<ClienteModel> {
	private static final Logger log = LoggerFactory.getLogger(ClienteModel.class);

	@Override
	public void afterWrite(Chunk<? extends ClienteModel> item) {
		log.info("! SALVO NO BANCO -> " + item.getItems().get(0));

	}

	@Override
	public void beforeWrite(Chunk<? extends ClienteModel> item) {
		log.info("! PREPARANDO PARA SALVAR! -> " + item.getItems().get(0));
	}

}
