package com.jailton.os.services.exceptions;

import com.jailton.os.domain.Cliente;
import com.jailton.os.domain.Pessoa;
import com.jailton.os.dtos.ClienteDTO;
import com.jailton.os.repositories.ClienteRepository;
import com.jailton.os.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Cliente findByid(Long id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {

        if (findByCPF(objDTO) != null) {
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
        }

        Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
        return repository.save(newObj);
    }

    public Cliente update(Long id, ClienteDTO objDTO) {

        Cliente oldObj = findByid(id);

        if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
        }

        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setTelefone(objDTO.getTelefone());

        return repository.save(oldObj);
    }

    public void delete(Long id) {
        Cliente obj = findByid(id);
        if (obj.getList().size() > 0) {
            throw new DataIntegratyViolationException("Pessoa possui ordens de serviço, " +
                    "não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    private Pessoa findByCPF(ClienteDTO objDTO) {
        Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
        if (obj != null) {
            return obj;
        }
        return null;
    }
}
