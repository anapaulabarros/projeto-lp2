
import org.junit.Assert;
import org.junit.Test;

import treatmentsExceptions.SystemPopExceptions;
import core.Usuario;


public class TestUsuario {

	@Test
	public void TestConstrututorUsuarioComImagem()  {
		try{
			Usuario id1 = new Usuario("Fulaninho", "alguem@email.com","senha_besta", "25/01/1990", "resources/foto.jpg");
			Assert.assertEquals("Fulaninho", id1.getNome());
			Assert.assertEquals("resources/foto.jpg", id1.getImagem());
			Assert.assertEquals("1990-01-25", id1.getDataNasc());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void TestConstrututorUsuarioSemFoto() {
		try {
			Usuario id2 = new Usuario("Madona", "madonna@email.com","iamawesome", "16/08/1958", "resources/default.jpg");

			Assert.assertEquals("Madona", id2.getNome());
			Assert.assertEquals("resources/default.jpg", id2.getImagem());
		}  catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}

}
