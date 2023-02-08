package com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.dto.ClienteBatchResultDTO;
import com.api.service.BatchService;


@Scope("singleton")
@RestController
@RequestMapping("batch")
public class BatchController {

	@Autowired
	private BatchService batchService;

	@PostMapping("start")
	public ClienteBatchResultDTO importarClients(@RequestHeader("input") String input) {
		return batchService.importarCliente(input);

	}

	@GetMapping("on")
	public String online() {
		return "On";
	}

}
