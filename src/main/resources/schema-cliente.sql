CREATE TABLE `cliente` (
  `cliente_cpf` bigint NOT NULL,
  `cliente_nome` varchar(255) NOT NULL,
  PRIMARY KEY (`cliente_cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
