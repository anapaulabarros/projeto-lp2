import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import core.Usuario;


public class TestUsuario {

	@Test
	public void TestConstrututorUsuarioComImagem() {
		Usuario id1 = new Usuario("Fatima Bernades", "fatima@mail.com","123456", "25/01/1990", "../img/foto.jpg", "(55) 5362-2654");
		
		Assert.assertEquals("Fatima Bernades", id1.getNome());
	}

}
