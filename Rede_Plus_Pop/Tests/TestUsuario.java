
import org.junit.Assert;
import org.junit.Test;

import treatmentsExceptions.SystemPopExceptions;
import core.Facade;
import core.Usuario;


public class TestUsuario {

	@Test
	public void TestConstrututorUsuarioComImagem()  {
		/*try{
			Usuario id1 = new Usuario("Fulaninho", "alguem@email.com","senha_besta", "25/01/1990", "resources/foto.jpg");
			Assert.assertEquals("Fulaninho", id1.getNome());
			Assert.assertEquals("resources/foto.jpg", id1.getImagem());
			Assert.assertEquals("1990-01-25", id1.getDataNasc());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}*/
		
		Facade sistema = new Facade();
		try {
			sistema.cadastraUsuario("Maria", "maria@email.com", "senha_besta", "25/01/1990", "resources/foto.jpg");
			sistema.cadastraUsuario("Jose", "jose@email.com", "senha_123", "25/01/1990", "resources/foto.jpg");
			
			sistema.login("maria@email.com", "senha_besta");
			String texto = "Hoje o sol me acordou. Foi muito cansativo sair da cama pois ainda estava com muito sono. Gostaria ter"
					+ " mais tempo para dormir. Ainda bem que tinha tapioca e cuscuz no cafe da manha para dar a energia. #cafe #acorda";
			sistema.criaPost(texto, "27/08/2015 12:00:00");
			sistema.adicionaAmigo("jose@email.com");
			
			sistema.logout();
		//-------------------------------------------------------------
			sistema.login("jose@email.com", "senha_123");
			sistema.setPops(600);
			sistema.aceitaAmizade("maria@email.com");
			System.out.println(sistema.getTipoPopularidade());
			System.out.println(sistema.getPopsUsuario());
			
			sistema.curtirPost("maria@email.com", 0);
		//-------------------------------------------------------------
			sistema.logout();
			
			sistema.login("maria@email.com", "senha_besta");
			System.out.println(sistema.getTipoPopularidade());
			System.out.println(sistema.getPopsPost (0));
			
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
