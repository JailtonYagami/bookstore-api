package com.jailton.os.resources;

import com.jailton.os.domain.Tecnico;
import com.jailton.os.dtos.TecnicoDTO;
import com.jailton.os.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

    //localhost:8080/tecnicos/1

    @Autowired
    private TecnicoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Long id){
        Tecnico obj = service.findByid(id);
        TecnicoDTO objDTO = new TecnicoDTO(obj);
        return ResponseEntity.ok().body(objDTO);
    }

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll(){

        List<TecnicoDTO> listDTO = service.findAll()
                .stream().map(obj-> new TecnicoDTO(obj)).collect(Collectors.toList());
//        List<Tecnico> list = service.findAll();
//        List<TecnicoDTO> dtoList = new ArrayList<>();
//
//        for (Tecnico obj: list) {
//            dtoList.add(new TecnicoDTO(obj));
//        }
//
//        list.forEach(obj -> dtoList.add(new TecnicoDTO(obj)));
        return ResponseEntity.ok().body(listDTO);
    }
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@RequestBody TecnicoDTO objTDO){
        Tecnico newObj = service.create(objTDO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newObj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
