package br.com.unifor.testesoftware;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ContaTest {

  @Test
  public void deveCreditar() {

    // Cenario
    Cliente cliente = new Cliente("João");
    Conta c = new Conta(cliente, 123, 10, TipoConta.CORRENTE);

    // Ação
    c.creditar(5);

    // Verificação
    assertEquals(15, c.getSaldo());
    assertThat(c.getSaldo(), is(15));
  }

}
