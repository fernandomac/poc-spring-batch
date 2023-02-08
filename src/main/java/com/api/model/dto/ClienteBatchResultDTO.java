package com.api.model.dto;

import java.util.List;

import com.api.model.ClienteModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteBatchResultDTO {

	private List<ClienteBatchDTO> successo;
	private List<ClienteBatchDTO> falha;

}
