## POC - Spring Batch
<p> Estudos Referente a implementação do <strong>SPRING BATCH</strong></p>

<p> O spring batch é bem personalizável, é possível personalizar o transaction manager, reader, writter e processor. Porém não é possível retornar para o service os dados que foram procesados pelo spring batch. </p>
Considerações:
<ol>
  <strong><li>Problemas de Rollback.</li></strong>
  Referente ao Controle de Transações é possível escolher o número de dados que serão lidos por chunk, caso aconteça um erro dentro deste chunk os dados do chunk irão sofrer o rollback, a solução foi limitar o tamanho do chunk a 1.
  Assim caso aconteça um erro não irá causar Rollback de outros dados da chunk.
  
```java
	@Bean
	public Step cadastrarCliente(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			JdbcBatchItemWriter<ClienteModel> writerCliente, ItemReader<ClienteModel> readerCliente) {
		return new StepBuilder("step1", jobRepository)
				.<ClienteModel, ClienteModel>chunk(1, transactionManager)
				.reader(readerCliente)
				.processor(processor())
				.writer(writerCliente)
				.listener(clienteWriteListener())
				.faultTolerant().skipPolicy(skipPolicy()).build();
	}
```
  
  <strong><li>Retornando Dados</li></strong>
  Para que seja possível retornar os dados da operação foi feito um tratamento no processador de informações, onde os dados e seus status são salvos em uma tabela no banco de dados, após a finalização do batch esses dados são buscados no banco e retornados ao service.
  
  

</ol>
