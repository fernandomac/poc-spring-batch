package com.api.batch.processor.validator;

import java.util.ArrayList;
import java.util.UUID;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.api.model.ClienteModel;
import com.api.model.dto.ClienteBatchDTO;

public class DataValidation {

	private static final Logger log = LoggerFactory.getLogger(DataValidation.class);

	@Autowired
	private DozerBeanMapper mapper;

	public String verificarCliente(ClienteModel cliente) {
		String error = "";

		if (cliente.getCadastroNacional().length() <= 10 && cliente.getCadastroNacional().length() <= 19) {
			error += " {" + ClienteBatchStatus.CAD_INVALIDO.getValue() + "} ";

		}
		if (cliente.getCadastroNacional().matches("[0-9]+") == false) {
			error += " {" + ClienteBatchStatus.CAD_INVALIDO.getValue() + "} ";
		}

		if (StringUtils.hasText(cliente.getNome()) == false) {
			error += " {" + ClienteBatchStatus.NOME_VAZIO.getValue() + "} ";

		}
		if (StringUtils.hasText(cliente.getCadastroNacional()) == false) {
			error += " {" + ClienteBatchStatus.CAD_VAZIO.getValue() + "} ";
		}

		if (isEmail(cliente.getEmail()) == false) {
			error += " {" + ClienteBatchStatus.EMAIL_INVALIDO.getValue() + "} ";

		}
	

		return error;

	}

	public ClienteModel removerMascaras(ClienteModel cliente) {
		cliente.setCadastroNacional(
				cliente.getCadastroNacional().replaceAll("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]", ""));

		cliente.setTelefoneCelular(
				cliente.getTelefoneCelular().replaceAll("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]", ""));

		cliente.setTelefoneFixo(cliente.getTelefoneFixo().replaceAll("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]", ""));
		return cliente;

	}

	public ClienteBatchDTO converter(ClienteModel cliente, String JobId) {

		ClienteBatchDTO result = mapper.map(cliente, ClienteBatchDTO.class);
		result.setJobId(JobId);
		result.setErrorId(UUID.randomUUID().toString());
		result.setTipoCadNacional((cliente.getCadastroNacional().length() == 11) ? "FÍSICA" : "JURÍDICA");

		return result;
	}

	private boolean isEmail(String email) {
		if (email.contains("@") && email.contains(".com") || email.isEmpty() == true) {
			return true;
		} else {
			return false;
		}

	}

	private boolean isFixo() {
		return true;
	}

	private boolean isCelular() {
		return true;
	}

}
