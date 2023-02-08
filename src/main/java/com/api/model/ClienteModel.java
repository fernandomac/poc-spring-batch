package com.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Data
@Entity
@Table(name = "cliente")
public class ClienteModel {
	
	@Id
	@Column(name = "cliente_cad")
	private String cadastroNacional;
	@Column(name = "cliente_cad_tipo")
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

	public ClienteModel(String cadastroNacional, String nome) {
		this.cadastroNacional = cadastroNacional;
		this.nome = nome;
	}

}
