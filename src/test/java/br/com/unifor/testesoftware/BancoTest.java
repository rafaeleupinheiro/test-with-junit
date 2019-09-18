package br.com.unifor.testesoftware;

import br.com.unifor.testesoftware.exceptions.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class BancoTest {

  @Test
  public void deveCadastrarConta() throws NumeroContaInvalidoException, ClienteJaExistenteException, ContaJaExistenteException {

    // Cenario
    Cliente cliente = new Cliente("Joao");
    Conta conta = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
    Banco banco = new Banco();

    // Ação
    banco.cadastrarConta(conta);

    // Verificação
    assertEquals(1, banco.obterContas().size());
  }

  @Test(expected = ContaJaExistenteException.class)
  public void naoDeveCadastrarContaNumeroRepetido() throws NumeroContaInvalidoException, ClienteJaExistenteException, ContaJaExistenteException {

    // Cenario
    Cliente cliente = new Cliente("Joao");
    Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

    Cliente cliente2 = new Cliente("Maria");
    Conta conta2 = new Conta(cliente2, 123, 0, TipoConta.POUPANCA);

    Banco banco = new Banco();

    // Ação
    banco.cadastrarConta(conta1);
    banco.cadastrarConta(conta2);

    Assert.fail();
  }

  // TODO: HOMEWORK
  @Test(expected = ClienteJaExistenteException.class)
  public void naoDeveCadastrarContaNomeClienteRepetido() throws NumeroContaInvalidoException, ClienteJaExistenteException, ContaJaExistenteException {
    // Cenario
    Cliente cliente = new Cliente("Rafael");
    Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

    Cliente cliente2 = new Cliente("Rafael");
    Conta conta2 = new Conta(cliente2, 456, 0, TipoConta.POUPANCA);

    Banco banco = new Banco();

    // Ação
    banco.cadastrarConta(conta1);
    banco.cadastrarConta(conta2);

    Assert.fail();
  }

  // TODO: HOMEWORK - (Alterar a implementação para tratar esse caso)
  @Test(expected = NumeroContaInvalidoException.class)
  public void naoDeveCadastrarContaComNumeroInvalido() throws NumeroContaInvalidoException, ClienteJaExistenteException, ContaJaExistenteException {
    // Cenario
    Cliente cliente = new Cliente("Rafael");
    Conta conta1 = new Conta(cliente, 0, 0, TipoConta.CORRENTE);

    Cliente cliente2 = new Cliente("Carlos");
    Conta conta2 = new Conta(cliente2, 1000000000, 0, TipoConta.POUPANCA);

    Cliente cliente3 = new Cliente("Julia");
    Conta conta3 = new Conta(cliente3, -1, 0, TipoConta.CORRENTE);

    Banco banco = new Banco();

    // Ação
    banco.cadastrarConta(conta1);
    banco.cadastrarConta(conta2);
    banco.cadastrarConta(conta3);

    Assert.fail();
  }

  @Test
  public void deveEfetuarTransferenciaContasCorrentes()
      throws TransferenciaValorNegativoException, ContaSemSaldoException, ContaNaoExistenteException {

    // Cenario
    Cliente cliente = new Cliente("Joao");
    Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

    Cliente cliente2 = new Cliente("Maria");
    Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

    Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

    // Ação
    banco.efetuarTransferencia(123, 456, 100);

    // Verificação
    assertEquals(-100, banco.obterContaPorNumero(contaOrigem.getNumeroConta()).getSaldo());
    assertEquals(100, banco.obterContaPorNumero(contaDestino.getNumeroConta()).getSaldo());
  }

  // TODO: HOMEWORK - (Alterar a implementação para tratar esse caso)
  @Test(expected = TransferenciaValorNegativoException.class)
  public void naoDeveEfetuarTransferenciaComValorNegativo()
      throws TransferenciaValorNegativoException, ContaSemSaldoException, ContaNaoExistenteException {

    // Cenario
    Cliente cliente = new Cliente("Joao");
    Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

    Cliente cliente2 = new Cliente("Maria");
    Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

    Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

    // Ação
    banco.efetuarTransferencia(123, 456, -100);

    Assert.fail();
  }

  // Verificação
  @Test(expected = ContaSemSaldoException.class)
  public void naoDeveEfetuarTransferenciaContaOrigemPoupancaSemSaldo()
      throws TransferenciaValorNegativoException, ContaSemSaldoException, ContaNaoExistenteException {

    // Cenario
    Cliente cliente = new Cliente("Joao");
    Conta contaOrigem = new Conta(cliente, 123, 99, TipoConta.POUPANCA);

    Cliente cliente2 = new Cliente("Maria");
    Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

    Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

    // Ação
    banco.efetuarTransferencia(123, 456, 100);

    Assert.fail();
  }

  // Verificação
  @Test(expected = ContaNaoExistenteException.class)
  public void naoDeveEfetuarTransferenciaContaOrigemNaoExistente()
      throws TransferenciaValorNegativoException, ContaSemSaldoException, ContaNaoExistenteException {

    // Cenario
    Cliente cliente = new Cliente("Joao");
    Conta contaOrigem = new Conta(cliente, 123, 99, TipoConta.POUPANCA);

    Cliente cliente2 = new Cliente("Maria");
    Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

    Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

    // Ação
    banco.efetuarTransferencia(111, 456, 100);

    Assert.fail();
  }

  // TODO: HOMEWORK
  @Test(expected = ContaNaoExistenteException.class)
  public void naoDeveEfetuarTransferenciaContaDestinoNaoExistente()
      throws TransferenciaValorNegativoException, ContaSemSaldoException, ContaNaoExistenteException {
    // Cenario
    Cliente cliente = new Cliente("Joao");
    Conta contaOrigem = new Conta(cliente, 123, 99, TipoConta.POUPANCA);

    Cliente cliente2 = new Cliente("Maria");
    Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

    Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

    // Ação
    banco.efetuarTransferencia(123, 111, 100);

    Assert.fail();
  }
}
