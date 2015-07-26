import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import core.Usuario;


public class TestUsuario {

	@Test
	public void TestConstrututorUsuario() {
		Usuario id1 = new Usuario("Fatima Bernades", "fatima@mail.com","123456", "25/01/1990", "../img/foto.jpg", "(55) 5362-2654");
		System.out.println(id1.toString());
	}

}
