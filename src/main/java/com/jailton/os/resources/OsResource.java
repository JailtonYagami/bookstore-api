package com.jailton.os.resources;

import com.jailton.os.dtos.OSDTO;
import com.jailton.os.services.OsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/os")
public class OsResource {

    @Autowired
    private OsService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OSDTO> findById(@PathVariable Long id) {
        OSDTO obj = new OSDTO((service.findById(id)));
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<OSDTO>> findAll() {
        List<OSDTO> list = service.findAll().stream().
                map(obj -> new OSDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(list);
    }
    @PostMapping
    public ResponseEntity<OSDTO> create(@RequestBody @Valid OSDTO obj){
        obj = new OSDTO(service.create(obj));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    public ResponseEntity<OSDTO> update(@RequestBody @Valid OSDTO obj){
        obj = new OSDTO(service.update(obj));
        return ResponseEntity.ok().body(obj);
    }

}
