package com.devsuperior.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dto.ClientDTO;
import com.devsuperior.service.ClientService;

@RestController
@RequestMapping("clients")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
    @GetMapping
    public ResponseEntity findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                  @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
                                  @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
                                  @RequestParam(value = "direction", defaultValue = "DESC") Direction direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, direction,  orderBy);
        Page<ClientDTO> pageDto = clientService.findAll(pageRequest);
        return ResponseEntity.ok(pageDto);
    }
    
	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
		ClientDTO clientDTO = clientService.findById(id);
		return ResponseEntity.ok(clientDTO);
	}
	
	@PostMapping
	public ResponseEntity<ClientDTO> createElement(@RequestBody ClientDTO clientDTO) {
		clientDTO = clientService.createElement(clientDTO);
		URI uri = getURILocation(clientDTO);
		return ResponseEntity.created(uri).body(clientDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteById(@PathVariable Long id) {
		clientService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> updateElement(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
		clientDTO = clientService.updateElement(id, clientDTO);
		return ResponseEntity.ok(clientDTO);
	}

    private URI getURILocation(ClientDTO clientDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(clientDTO.getId()).toUri();
        return uri;
    }
}
