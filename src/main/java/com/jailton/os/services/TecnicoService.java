package com.jailton.os.services;

import com.jailton.os.domain.Pessoa;
import com.jailton.os.domain.Tecnico;
import com.jailton.os.dtos.TecnicoDTO;
import com.jailton.os.repositories.PessoaRepository;
import com.jailton.os.repositories.TecnicoRepository;
import com.jailton.os.services.exceptions.DataIntegratyViolationException;
import com.jailton.os.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findByid(Long id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {

        if (findByCPF(objDTO) != null) {
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
        }

        Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
        return repository.save(newObj);
    }

    public Tecnico update(Long id, TecnicoDTO objDTO) {

        Tecnico oldObj = findByid(id);

        if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
        }

        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setTelefone(objDTO.getTelefone());

        return repository.save(oldObj);
    }

    public void delete(Long id) {
        Tecnico obj = findByid(id);
        if (obj.getList().size() > 0) {
            throw new DataIntegratyViolationException("Técnico possui ordens de serviço, " +
                    "não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    private Pessoa findByCPF(TecnicoDTO objDTO) {
        Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
        if (obj != null) {
            return obj;
        }
        return null;
    }
}
