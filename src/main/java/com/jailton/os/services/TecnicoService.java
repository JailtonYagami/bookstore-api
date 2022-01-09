package com.jailton.os.services;

import com.jailton.os.domain.Tecnico;
import com.jailton.os.dtos.TecnicoDTO;
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

    public Tecnico findByid(Long id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO){
        if (findByCPF(objDTO) != null){
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
        }
        Tecnico newObj = new Tecnico(null,objDTO.getNome(),objDTO.getCpf(),objDTO.getTelefone());
        return repository.save(newObj);
    }

    private Tecnico findByCPF(TecnicoDTO objDTO){
        Tecnico obj = repository.findByCPF(objDTO.getCpf());
        if (obj != null){
            return obj;
        }
        return null;
    }
}
