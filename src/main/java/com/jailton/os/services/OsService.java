package com.jailton.os.services;

import com.jailton.os.domain.Cliente;
import com.jailton.os.domain.OS;
import com.jailton.os.domain.Tecnico;
import com.jailton.os.dtos.OSDTO;
import com.jailton.os.enuns.Prioridade;
import com.jailton.os.enuns.Status;
import com.jailton.os.repositories.OSRepository;
import com.jailton.os.services.exceptions.ClienteService;
import com.jailton.os.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OsService {

    @Autowired
    private OSRepository repository;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private ClienteService clienteService;

    public OS findById(Long id) {
        Optional<OS> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado Id: " + id +
                " Tipo: " + OS.class.getName()));
    }

    public List<OS> findAll(){
        return repository.findAll();
    }

    public OS create(@Valid OSDTO obj) {
    return fromDTO(obj);
    }

    public OS update(@Valid OSDTO obj) {
        findById(obj.getId());
        return fromDTO(obj);
    }

    private OS fromDTO(OSDTO obj){
        OS newObj = new OS();
        newObj.setId(obj.getId());
        newObj.setObservacoes(obj.getObservacoes());
        newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        newObj.setStatus(Status.toEnum(obj.getStatus()));

        Tecnico tec = tecnicoService.findByid(obj.getTecnico());
        Cliente cli = clienteService.findByid(obj.getCliente());

        newObj.setTecnico(tec);
        newObj.setCliente(cli);

        if (newObj.getStatus().getCod().equals(2)){
            newObj.setDataFechamento(LocalDateTime.now());
        }

        return repository.save(newObj);

    }
}
