package com.banco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.domain.TipoTarifa;
import com.banco.repository.TipoTarifaRepository;

@RestController
@RequestMapping("/v1/tarifas")
public class TarifaController {

    @Autowired
    private TipoTarifaRepository tipoTarifaRepository;

    @GetMapping
    public TipoTarifa procuraPorNome(@RequestParam String nome) {
        return tipoTarifaRepository.findByNomeContaining(nome);
    }
}