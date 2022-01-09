package com.jailton.os.services;

import com.jailton.os.domain.Cliente;
import com.jailton.os.domain.OS;
import com.jailton.os.domain.Tecnico;
import com.jailton.os.enuns.Prioridade;
import com.jailton.os.enuns.Status;
import com.jailton.os.repositories.ClienteRepository;
import com.jailton.os.repositories.OSRepository;
import com.jailton.os.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private OSRepository osRepository;


    public void instaciaDB() {
        Tecnico t1 = new Tecnico(null, "Jailton Gilberto", "856.730.830-50", "(81)" +
                "98888-8888");
        Tecnico t2 = new Tecnico(null, "Tom", "804.663.170-18", "(81)" +
                "98456-8568");

        Cliente c1 = new Cliente(null, "Betina Campos", "993.461.650-57", "(81)" +
                "98567-7890");

        OS os1 = new OS(null, Prioridade.ALTA, "Teste create OS", Status.ANDAMENTO, t1, c1);

        t1.getList().add(os1);
        c1.getList().add(os1);

        tecnicoRepository.saveAll(Arrays.asList(t1,t2));
        clienteRepository.saveAll(Arrays.asList(c1));
        osRepository.saveAll(Arrays.asList(os1));

    }
}
