package com.br.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.exception.ResourceNotFoundException;
import com.br.model.ListaTarefa;
import com.br.repository.ListaTarefaRepository;

// Endereço: http://localhost:8080/listatarefa/v1

@RequestMapping("/listatarefa/v1/")
@RestController
public class ListaTarefaController_v1 {
	
	// Criando uma instancia do repositorio JPA Hibernate
	@Autowired
	private ListaTarefaRepository lRep;
	
	// Lista todos os usúarios:
	// GET - http://localhost:8080/listatarefa/v1/lista
	@GetMapping("/lista")
	public List<ListaTarefa> listar(){
		return this.lRep.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
	
	// Consultar um usuario específico:
	// GET - http://localhost:8080/listatarefa/v1/lista/{id}
	@GetMapping("/lista/{id}")
	public ResponseEntity<ListaTarefa> consultar(@PathVariable Long id){
		ListaTarefa lista = this.lRep.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("Usuário não encontrado: " + id));
		
		return ResponseEntity.ok(lista);
	};
	
	// Inserir um novo usuário
	// POST - http://localhost:8080/listatarefa/v1/lista
	@PostMapping("/lista")
	public ListaTarefa inserir(@RequestBody ListaTarefa lista) {
		return this.lRep.save(lista);
	}
	
	// Alterar um  usuário existente
	// PUT - http://localhost:8080/listatarefa/v1/lista/{id}
	@PutMapping("/lista/{id}")
	public ResponseEntity<ListaTarefa> alterar(@PathVariable Long id,@RequestBody ListaTarefa lista){
		ListaTarefa list = this.lRep.findById(id).orElseThrow(() ->
		new ResourceNotFoundException("Usuário não encontrado: " + id));
		
		list.setNome(lista.getNome());
		list.setStatus(lista.getStatus());
		list.setTarefa(lista.getTarefa());
		
		ListaTarefa listaAtualizado = lRep.save(list);
		
		return ResponseEntity.ok(listaAtualizado);
	}
	
	// Excluir um usuário existente
	// DELETE - http://localhost:8080/listatarefa/v1/lista/{id}
	@DeleteMapping("/lista/{id}")
	public ResponseEntity<Map<String, Boolean>> excluir(@PathVariable Long id){
		
		ListaTarefa lista = this.lRep.findById(id).orElseThrow(() ->
		new ResourceNotFoundException("usuário não encontrado: " + id));
		
		this.lRep.delete(lista);
		
		Map<String, Boolean> resposta = new HashMap<>();
		resposta.put("Usuário excluído", Boolean.TRUE);
		
		return ResponseEntity.ok(resposta);
	} 
	
	
	
}
