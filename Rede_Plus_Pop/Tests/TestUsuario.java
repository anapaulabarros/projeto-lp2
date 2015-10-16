
import org.junit.Assert;
import org.junit.Test;

import core.Facade;
import core.Usuario;


public class TestUsuario {
	
	private static final String FAFA = "fafa_bernardes@email.com.br";
	private static final String DILMA_BOLADA = "dilma_bolada@brasil.com";
	private static final String NAZA_FOGUETE = "naza_foguete@hotmail.com";
	private static final String MADONNA_EMAIL = "madonna@email.com";
	private Facade sistema;

	/*public void setUp(){
		sistema = new Facade();
	}*/

	/*@Test
	public void TestConstrututorUsuarioComImagem()  {
		try{
			Usuario id1 = new Usuario("Fulaninho", "alguem@email.com","senha_besta", "25/01/1990", "resources/foto.jpg");
			Assert.assertEquals("Fulaninho", id1.getNome());
			Assert.assertEquals("resources/foto.jpg", id1.getImagem());
			Assert.assertEquals("1990-01-25", id1.getDataNasc());
		} catch (Exception e) {
			Assert.fail();
		}
		

		try {
			sistema.cadastraUsuario("Maria", "maria@email.com", "senha_besta", "25/01/1990", "resources/foto.jpg");
			sistema.cadastraUsuario("Jose", "jose@email.com", "senha_123", "25/01/1990", "resources/foto.jpg");
			
			sistema.login("maria@email.com", "senha_besta");
			System.out.println("MARIA");
			String texto = "Hoje o sol me acordou. Foi muito cansativo sair da cama pois ainda estava com muito sono. Gostaria ter"
					+ " mais tempo para dormir. Ainda bem que tinha tapioca e cuscuz no cafe da manha para dar a energia. #cafe #acorda";
			sistema.criaPost(texto, "27/08/2015 12:00:00");
			sistema.criaPost(texto, "28/08/2015 12:00:00");
			sistema.adicionaAmigo("jose@email.com");

			System.out.println(sistema.getPost("Data", 1));
			sistema.logout();
		//-------------------------------------------------------------
			sistema.login("jose@email.com", "senha_123");
			System.out.println("JOSE");
			sistema.setPops(600);
			sistema.aceitaAmizade("maria@email.com");
			System.out.println(sistema.getTipoPopularidade());
			System.out.println(sistema.getPopsUsuario());
			
			sistema.curtirPost("maria@email.com", 0);
			sistema.curtirPost("maria@email.com", 1);
			
			sistema.criaPost(texto, "28/08/2015 12:00:00");
			sistema.logout();
		//-------------------------------------------------------------
			sistema.login("maria@email.com", "senha_besta");
			System.out.println("MARIA");
			System.out.println(sistema.getTipoPopularidade());
			System.out.println(sistema.getPopsUsuario());
			sistema.curtirPost("jose@email.com", 0);
			sistema.logout();
		//-------------------------------------------------------------
			sistema.login("jose@email.com", "senha_123");
			System.out.println("JOSE");
			sistema.aceitaAmizade("maria@email.com");
			System.out.println(sistema.getTipoPopularidade());
			System.out.println(sistema.getPopsUsuario());

			sistema.logout();
			
		} catch (Exception e) {
			//Assert.fail();
			System.out.println(e.getMessage());
		}
		
	}*/
	
	/*@Test
	public void TestConstrututorUsuarioSemFoto() {
		try {
			Usuario id2 = new Usuario("Madona", MADONNA_EMAIL,"iamawesome", "16/08/1958", "resources/default.jpg");

			Assert.assertEquals("Madona", id2.getNome());
			Assert.assertEquals("resources/default.jpg", id2.getImagem());
		}  catch (Exception e) {
			Assert.fail();
		}
		
		
	}*/
	
	@Test
	public void testPontos(){
		try{
			sistema = new Facade();
			
			sistema.iniciaSistema();

			sistema.cadastraUsuario("Madonna", MADONNA_EMAIL, "iamawesome", "16/08/1958", "resources/madonna.jpg");
			sistema.login(MADONNA_EMAIL, "iamawesome");
			
			sistema.criaPost("Odeio andar de elevador. <audio>resources/teste_audio.mp3</audio> #teste", "16/10/2015 14:20:00");
		
			
			/*sistema.cadastraUsuario("Fatima Bernardes Bonner", FAFA, "fafa_S2", "17/09/1962", "resources/fatima.jpg");
			sistema.cadastraUsuario("Nazare Tedesco", NAZA_FOGUETE, "belzinha", "17/09/1962", "resources/naza.jpg");
			sistema.cadastraUsuario("Dilma Rousseff", DILMA_BOLADA, "rainha", "14/12/1947", "resources/dilma.jpg");

			
			sistema.login(DILMA_BOLADA, "rainha");
			sistema.setPops(501);
			Assert.assertEquals("CelebridadePop", sistema.getTipoPopularidade());

			sistema.adicionaAmigo(NAZA_FOGUETE);
			sistema.logout(); 

			sistema.login(NAZA_FOGUETE, "belzinha");
			sistema.aceitaAmizade(DILMA_BOLADA);
			sistema.adicionaAmigo(FAFA);
			sistema.adicionaAmigo(MADONNA_EMAIL);

			Assert.assertEquals("Normal", sistema.getTipoPopularidade());

			sistema.criaPost("Odeio andar de elevador.", "27/08/2015 09:30:00");
			sistema.criaPost("Nao aguento pessoas falsianes. #falsianeemtodolugar", "27/08/2015 09:49:00");

			sistema.logout();

			sistema.login(MADONNA_EMAIL, "iamawesome");
			sistema.setPops(1001);
			Assert.assertEquals("IconePop", sistema.getTipoPopularidade());

			sistema.aceitaAmizade(NAZA_FOGUETE);
			sistema.adicionaAmigo(FAFA);

			sistema.rejeitarPost(NAZA_FOGUETE, 0);

			sistema.criaPost("Very happy with my new friends <3 #divas", "27/08/2015 10:30:00");
			sistema.criaPost("Download my new single I'm Cantora for free. <audio>musicas/im_cantora.mp3</audio>", "27/08/2015 10:40:00");
			sistema.logout();

			sistema.login(FAFA, "fafa_S2");
			sistema.setPops(501);
			Assert.assertEquals("CelebridadePop", sistema.getTipoPopularidade());

			sistema.aceitaAmizade(MADONNA_EMAIL);
			sistema.aceitaAmizade(NAZA_FOGUETE);

			sistema.curtirPost(NAZA_FOGUETE, 0);
			sistema.curtirPost(NAZA_FOGUETE, 1);
			sistema.curtirPost(MADONNA_EMAIL, 0);
			sistema.curtirPost(MADONNA_EMAIL, 1);

			sistema.logout();

			sistema.login(NAZA_FOGUETE, "belzinha");
			sistema.curtirPost(MADONNA_EMAIL, 0);
			sistema.curtirPost(MADONNA_EMAIL, 1);

			Assert.assertEquals(-25, sistema.getPopsPost(0));
			Assert.assertEquals(25, sistema.getPopsPost(1));
			Assert.assertEquals(1, sistema.getCurtidaPorPost(0));
			Assert.assertEquals(2, sistema.getCurtidaPorPost(0, MADONNA_EMAIL));
			Assert.assertEquals(1, sistema.getRejeicaoPorPost(0));
			
			Assert.assertEquals(0, sistema.getPopsUsuario());
			Assert.assertEquals(501, sistema.getPopsUsuario(DILMA_BOLADA));
			Assert.assertEquals(501, sistema.getPopsUsuario(FAFA));
			Assert.assertEquals(1071, sistema.getPopsUsuario(MADONNA_EMAIL));

			Assert.assertEquals("(1) Madonna, (2) Fatima Bernardes Bonner, (3) Dilma Rousseff", sistema.getRankingMais());
			Assert.assertEquals("(1) Nazare Tedesco, (2) Dilma Rousseff, (3) Fatima Bernardes Bonner", sistema.getRankingMenos());*/

			sistema.logout();
			
			sistema.fechaSistema();
			
		}catch(Exception e){
			//Assert.fail();
			System.out.println(e.getMessage());
		}
	}

}
