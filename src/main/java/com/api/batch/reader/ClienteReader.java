package com.api.batch.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.api.batch.listeners.JobCompletionNotificationListener;
import com.api.model.ClienteModel;

@Configuration
public class ClienteReader {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Bean
	@StepScope
	public FlatFileItemReader<ClienteModel> readerCliente(@Value("#{jobParameters['file.input']}") String input) {
		System.out.println(">>> " + input);
		FlatFileItemReader<ClienteModel> reader = new FlatFileItemReaderBuilder<ClienteModel>()
				.name("usuarioItemReader").resource(new ClassPathResource(input)).delimited()
				.names(new String[] { "cadastroNacional", "nome", "apelido", "email", "telefoneCelular","telefoneFixo",
						"cep", "logradouro", "numero", "complemento", "bairro", "cidade", "uf" }).strict(false)
				.fieldSetMapper(new BeanWrapperFieldSetMapper<ClienteModel>() {
					{
						setTargetType(ClienteModel.class);
					}
				}).linesToSkip(1).build();
		log.info("" + reader);
		return reader;
	}

}
