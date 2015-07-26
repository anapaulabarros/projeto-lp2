import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import core.Usuario;


public class TestUsuario {

	@Test
	public void TestConstrututorUsuarioComImagem() {
		Usuario id1 = new Usuario("Fatima Bernades", "fatima@mail.com","123456", "25/01/1990", "resources/foto.jpg", "(55) 5362-2654");
		
		Assert.assertEquals("Fatima Bernades", id1.getNome());
		Assert.assertEquals("resources/foto.jpg", id1.getImagem());
		Assert.assertEquals("25/01/1990", id1.getDataNasc());
	}
	
	@Test
	public void TestConstrututorUsuarioSemFoto() {
		Usuario id2 = new Usuario("Madona", "madonna@email.com","iamawesome", "16/08/1958", "(55) 5362-2654");
		
		Assert.assertEquals("Madona", id2.getNome());
		Assert.assertEquals("resources/defaultImage.png", id2.getImagem());
	}

}
