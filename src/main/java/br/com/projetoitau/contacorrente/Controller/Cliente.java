package br.com.projetoitau.contacorrente.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cliente")
public class Cliente {

    @GetMapping("")
    public Integer getAllClients() {
        return 1;
    }

}
