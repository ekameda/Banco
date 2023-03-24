package com.banco.controller;

import java.math.BigDecimal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import com.banco.domain.Conta;
import com.banco.dto.ContaRequestDto;
import com.banco.dto.ContaResponseDto;
import com.banco.dto.PixDto;
import com.banco.repository.ContaRepository;
import com.banco.service.ContaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/contas")
public class ContaController {

    private final ContaRepository contaRepository;
    private final ContaService contaService;

    
    // Serviço de Criar Conta
    // Ex. Postman Post localhost:8080/v1/contas
    // Body raw Json
	//    {
	//        "agencia": "1234",
	//        "nome": "Edu",
	//        "cpf": "76345",
	//        "codigo": "110",
	//        "chave": "{{$randomUUID}}"
	//    

    @PostMapping
    public ContaResponseDto criarConta(@RequestBody ContaRequestDto requestDto) {
        Conta conta = contaService.criarConta(requestDto);
        return conta.toContaDto();
    }

    
    // Serviço de Recuperar Contas
    // Ex. Postman http://localhost:8080/v1/contas
    
    @GetMapping()
    public List<Conta> procuraContas() {
        return contaService.procuraContas();
    }

//    @GetMapping("/{id}")
////    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<ContaDto> procuraContaPorId(@PathVariable Long id) {
//
//        try {
//            Optional<Conta> contaOptional = contaService.procuraConta(id);
//            Conta conta = contaOptional.get();
//            //return ResponseEntity.status(HttpStatus.OK).body(conta.toContaDto());
//            //return ResponseEntity.ok().body(conta.toContaDto());
//            return ResponseEntity.ok(conta.toContaDto());
//        } catch (ContaInexistenteException exception) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    
    // Serviço de Recuperar Conta po Id
    //Ex. Postman Get http://localhost:8080/v1/contas/1
    
    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDto> procuraContaPorIdSemTry(@PathVariable Long id) {
        Conta conta = contaService.procuraConta(id);
        return ResponseEntity.ok(conta.toContaDto());
    }
    
    
    // Serviço Credito por Id
    // Ex. Postman Post http://localhost:8080/v1/contas/2/credito/2000

    @PostMapping("/{idConta}/credito/{valor}")
    public ResponseEntity<ContaResponseDto> creditarConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
        Conta conta = contaService.creditarConta(idConta, valor);
        return ResponseEntity.ok(conta.toContaDto());
    }

    
    // Serviço de Debito por Id
    // Ex. Postman Post http://localhost:8080/v1/contas/1/debito/1.5
    
    @PostMapping("/{idConta}/debito/{valor}")
    public ResponseEntity<ContaResponseDto> debitaConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
        Conta conta = contaService.debitaConta(idConta, valor);
        return ResponseEntity.ok(conta.toContaDto());
    }


    // Serviço Debita e Credita de Contas Por Id 
    // Ex. Postman Post http://localhost:8080/v1/contas/1/2/5
    
    @PostMapping("/{idContaDebitada}/{idContaCreditada}/{valor}")
    public ResponseEntity debitaConta(@PathVariable Long idContaDebitada, @PathVariable Long idContaCreditada, @PathVariable BigDecimal valor) {
        contaService.transferencia(idContaDebitada, idContaCreditada, valor);
        return ResponseEntity.ok("Transferencia realizada com sucesso");
    }
    
    
    // Serviço de Debita por Id Debitada , ChavePix Creditada Body Json valor
    // Ex. Postman http://localhost:8080/v1/contas/1/2/5
    // Body Raw Jason
	//    {
	//        "valor": 10
	//    }

    @PostMapping("/{idContaDebitada}/{chavePix}")
    public ResponseEntity debitaConta(@PathVariable Long idContaDebitada,
                                      @PathVariable String chavePix,
                                      @RequestBody PixDto pixDto) {
        contaService.pix(idContaDebitada, chavePix, pixDto.getValor());
        return ResponseEntity.ok("Pix realizada com sucesso");
    }
}
