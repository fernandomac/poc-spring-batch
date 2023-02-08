package com.api.batch.processor.validator;

import lombok.Getter;

@Getter

public enum ClienteBatchStatus {
	
	CADASTRADO(0, "Cliente já cadastrado"),
	SUCESSO(1, "Sucesso"),
	
	CAD_INVALIDO(2,"CPF/CNPJ inválido"),	
	TELEFONE_INVALIDO(3, "Telefone informado inválido"),
	EMAIL_INVALIDO(4, "Email inválido"),
	CELULAR_INVALIDO(5, "Celular Inválido"),
	
	CAD_VAZIO(6,"CPF/CNPJ não preenchido"),
	NOME_VAZIO(7, "Razão Social/Nome não preenchido"),
	
	
	Undefined(404, "Undefined");

	
	private final Short ordinal;
	private final String value;
	
	private ClienteBatchStatus(Integer ordinal, String value) {
		this.ordinal = ordinal.shortValue();
		this.value = value;
	}



}

