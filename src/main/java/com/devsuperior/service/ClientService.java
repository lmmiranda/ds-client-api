package com.devsuperior.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dto.ClientDTO;
import com.devsuperior.exception.ResourceNotFoundException;
import com.devsuperior.model.Client;
import com.devsuperior.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
    @Autowired
    protected ModelMapper modelMapper;
	
	public Page<ClientDTO> findAll(PageRequest pageRequest) {
		Page<Client> pageModel = clientRepository.findAll(pageRequest);
		return pageModel.map(model -> convertToDTO(model));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> optionalClient = clientRepository.findById(id);
		Client client = optionalClient.orElseThrow(() -> new ResourceNotFoundException(String.format("Resource not found for id: %s", id)));
		return convertToDTO(client);
	}

	@Transactional
	public ClientDTO createElement(ClientDTO clientDTO) {
		Client client = convertToModel(clientDTO);
		client = clientRepository.save(client);
		return convertToDTO(client);
	}
	
	public void deleteById(Long id) {
		try {
			clientRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(String.format("Resource not found for id: %s", id));
		}
	}
	
	@Transactional
	public ClientDTO updateElement(Long id, ClientDTO clientDTO) {
		try {
			clientDTO.setId(id);
			
			Client client = clientRepository.getOne(id);
			client = convertToModel(clientDTO);
			client = clientRepository.save(client);
			
			return convertToDTO(client);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(String.format("Resource not found for id: %s", id));
		}
	}
	
	private ClientDTO convertToDTO(Client client) {
		return modelMapper.map(client, ClientDTO.class);
	}
	
	private Client convertToModel(ClientDTO client) {
		return modelMapper.map(client, Client.class);
	}

}
