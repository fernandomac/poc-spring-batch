package com.api.batch.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.model.ClienteModel;

@Configuration
public class ClienteWriter {

	@Bean
	public JdbcBatchItemWriter<ClienteModel> writerCliente(DataSource dataSource) {

		return new JdbcBatchItemWriterBuilder<ClienteModel>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO cliente (cliente_cad_tipo, cliente_cad, cliente_nome, cliente_apelido, cliente_bairro, cliente_cep, cliente_cidade, cliente_complemento, cliente_logradouro,cliente_numero, cliente_telefone_celular, cliente_telefone_fixo, cliente_uf, cliente_email ) "
						+ "VALUES (:tipoCadNacional, :cadastroNacional, :nome, :apelido, :bairro, :cep, :cidade, :complemento, :logradouro, :numero, :telefoneCelular, :telefoneFixo, :uf, :email);")
				.dataSource(dataSource).build();
	}
}
