package dev.fernando.spring_pro_ch03.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.fernando.spring_pro_ch03.dto.ClientDTO;
import dev.fernando.spring_pro_ch03.entities.Client;
import dev.fernando.spring_pro_ch03.repositories.ClientRepository;
import dev.fernando.spring_pro_ch03.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(final ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        return new ClientDTO(findByIdOrFail(id));
    }

    private Client findByIdOrFail(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente com id = %d não encontrado".formatted(id)));
    }

    @Transactional
    public ClientDTO store(ClientDTO dto) {
        Client client = new Client();
        copyDtoToEntity(dto, client);
        client.setId(null);
        return new ClientDTO(repository.save(client));
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        Client client = findByIdOrFail(id);
        copyDtoToEntity(dto, client);
        return new ClientDTO(repository.save(client));
    }

    @Transactional
    public void delete(Long id) {
        findByIdOrFail(id);
        repository.deleteById(id);
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.name());
        entity.setCpf(dto.cpf());
        entity.setIncome(dto.income());
        entity.setBirthDate(dto.birthDate());
        entity.setChildren(dto.children());
    }
    
}
