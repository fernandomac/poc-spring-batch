package com.api.model.dto;

import java.util.List;

import com.api.batch.processor.validator.ClienteBatchStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "batch_job_lists")
public class ClienteBatchDTO {
	@Id
	@Column(name = "error_id")
	private String errorId;
	@Column(name = "job_id")
	private String jobId;
	@Column(name = "error_message")
	private String errorMessage;
	@Column(name = "cliente_cad")
	private String cadastroNacional;
	@Column(name = "cliente_cadTipo")
	private String tipoCadNacional;
	@Column(name = "cliente_nome")
	private String nome;
	@Column(name = "cliente_email")
	private String email;
	@Column(name = "cliente_apelido")
	private String apelido;
	@Column(name = "cliente_telefone_fixo")
	private String telefoneFixo;
	@Column(name = "cliente_telefone_celular")
	private String telefoneCelular;
	@Column(name = "cliente_cep")
	private String cep;
	@Column(name = "cliente_logradouro")
	private String logradouro;
	@Column(name = "cliente_numero")
	private String numero;
	@Column(name = "cliente_complemento")
	private String complemento;
	@Column(name = "cliente_bairro")
	private String bairro;
	@Column(name = "cliente_cidade")
	private String cidade;
	@Column(name = "cliente_uf")
	private String uf;
}
