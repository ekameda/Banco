package com.banco.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banco.domain.Banco;
import com.banco.domain.Conta;
import com.banco.domain.Titular;
import com.banco.dto.ContaRequestDto;
import com.banco.dto.PixBacenDto;
import com.banco.exceptions.BancoInexistenteException;
import com.banco.exceptions.ContaExistenteException;
import com.banco.exceptions.ContaInexistenteException;
import com.banco.exceptions.OperacaoInvalidaException;
import com.banco.repository.BancoRepository;
import com.banco.repository.ContaRepository;
import com.banco.repository.TitularRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContaService {

	private final ContaRepository contaRepository;
	private final TitularRepository titularRepository;

	private final BancoRepository bancoRepository;

	private final PixBacenService pixBacenService;

	public Conta criarConta(ContaRequestDto requestDto) {

		int codigo = requestDto.getCodigo();
		final Banco banco = bancoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new BancoInexistenteException("Banco não encontrado: " + codigo));

		final Titular titular = new Titular();
		titular.setCpf(requestDto.getCpf());
		titular.setNome(requestDto.getNome());
		titularRepository.save(titular);

		var conta = new Conta();
		conta.setAgencia(requestDto.getAgencia());
		conta.setTitular(titular);
		conta.setBanco(banco);
		conta.setPix(requestDto.getChave());
		validaContaExistente(conta);
		Conta contaSalva = contaRepository.save(conta);
	 //	pixBacenService.cadastraPixBancoCentral(contaSalva.toBacenDto());
		return contaSalva;
	}

	private void validaContaExistente(Conta conta) {
		Optional<Conta> contaOptional = contaRepository.findByAgenciaAndNumero(conta.getAgencia(), conta.getNumero());

		if (contaOptional.isPresent()) {
			throw new ContaExistenteException();
		}
	}

	public List<Conta> procuraContas() {
		return contaRepository.findAll();
	}

	public Conta procuraConta(Long id) {
		Optional<Conta> contaOptional = contaRepository.findById(id);
		if (contaOptional.isEmpty()) {
			throw new ContaInexistenteException("Essa conta não existe!");
		}
		return contaOptional.get();
	}

	public Conta creditarConta(Long idConta, BigDecimal valor) {
		Conta conta = procuraConta(idConta);
		conta.credito(valor);
		return contaRepository.save(conta);
	}

	public Conta debitaConta(Long idConta, BigDecimal valor) {
		Conta conta = procuraConta(idConta);
		conta.debito(valor);
		return contaRepository.save(conta);
	}

	public void transferencia(Long idContaDebitada, Long idContaCreditada, BigDecimal valor) {

		Conta contaDebitada = procuraConta(idContaDebitada);
		Conta contaCreditada = procuraConta(idContaCreditada);

		validarTransferencia(contaDebitada, contaCreditada);

		contaDebitada.debito(valor);
		contaCreditada.credito(valor);
		List<Conta> contas = new ArrayList<>();
		contas.add(contaCreditada);
		contas.add(contaDebitada);
		contaRepository.saveAll(contas);
	}

	private static void validarTransferencia(Conta contaDebitada, Conta contaCreditada) {
		if (contaDebitada.getBanco().getCodigo() != contaCreditada.getBanco().getCodigo()) {
			throw new OperacaoInvalidaException();
		}
	}

	public void pix(Long idContaDebitada, String chavePix, BigDecimal valor) {
		final Conta contaDebitada = procuraConta(idContaDebitada);

		PixBacenDto pixBacenDto = pixBacenService.buscarContaBancoCentral(chavePix);

		Conta contaCreditada = contaRepository.findByAgenciaAndNumero(pixBacenDto.getAgencia(), pixBacenDto.getNumero())
				.orElseThrow(() -> new ContaInexistenteException("Conta não encontrada: " + chavePix));

		contaDebitada.debito(valor);
		contaCreditada.credito(valor);
		List<Conta> contas = new ArrayList<>();
		contas.add(contaCreditada);
		contas.add(contaDebitada);
		contaRepository.saveAll(contas);
	}
}
