package br.com.unifor.testesoftware;

import br.com.unifor.testesoftware.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class Banco {

  private List<Conta> contas = new ArrayList<Conta>();

  public Banco() {
  }

  public Banco(List<Conta> contas) {
    this.contas = contas;
  }

  public void cadastrarConta(Conta conta) throws NumeroContaInvalidoException, ClienteJaExistenteException, ContaJaExistenteException {

    if (conta.getNumeroConta() <= 0 || conta.getNumeroConta() > 999999999) {
      throw new NumeroContaInvalidoException();
    }

    for (Conta c : contas) {
      boolean isNomeClienteIgual = c.getCliente().getNome().equals(conta.getCliente().getNome());
      boolean isNumeroContaIgual = c.getNumeroConta() == conta.getNumeroConta();

      if (isNomeClienteIgual) {
        throw new ClienteJaExistenteException();
      }

      if (isNumeroContaIgual) {
        throw new ContaJaExistenteException();
      }
    }

    this.contas.add(conta);
  }

  public void efetuarTransferencia(int numeroContaOrigem, int numeroContaDestino, int valor)
      throws TransferenciaValorNegativoException, ContaNaoExistenteException, ContaSemSaldoException {
    if (valor <= 0) {
      throw new TransferenciaValorNegativoException();
    }

    Conta contaOrigem = this.obterContaPorNumero(numeroContaOrigem);
    Conta contaDestino = this.obterContaPorNumero(numeroContaDestino);

    boolean isContaOrigemExistente = contaOrigem != null;
    boolean isContaDestinoExistente = contaDestino != null;

    if (isContaOrigemExistente && isContaDestinoExistente) {

      boolean isContaOrigemPoupança = contaOrigem.getTipoConta().equals(TipoConta.POUPANCA);
      boolean isSaldoContaOrigemNegativo = contaOrigem.getSaldo() - valor < 0;

      if (isContaOrigemPoupança && isSaldoContaOrigemNegativo) {
        throw new ContaSemSaldoException();
      }

      contaOrigem.debitar(valor);
      contaDestino.creditar(valor);

    } else {
      throw new ContaNaoExistenteException();
    }
  }

  public Conta obterContaPorNumero(int numeroConta) {

    for (Conta c : contas) {
      if (c.getNumeroConta() == numeroConta) {
        return c;
      }
    }

    return null;
  }

  public List<Conta> obterContas() {
    return this.contas;
  }
}
