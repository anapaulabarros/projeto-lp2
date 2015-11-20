package core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import treatmentsExceptions.ErroCadastroException;

public class UtilUsuario {
	
	public UtilUsuario() {
		
	}
	
	public static void validaDataCompleta(String dataNasc) throws ErroCadastroException {
		if (dataNasc == null || dataNasc.equals("")
				|| validaIntervalosDeData(dataNasc) == false)
			throw new ErroCadastroException(
					"Data nao existe.");
	}

	public static void validaDia(String dataNasc) throws ErroCadastroException {
		if (validaDiaDaData(dataNasc) == true)
			throw new ErroCadastroException(
					"Formato de data esta invalida.");
	}

	public static void validaSenha(String senha) throws ErroCadastroException {
		if (senha == null || senha.equals("") || senha.length() < 3)
			throw new ErroCadastroException(
					"A senha nao pode ser nula, vazia ou menor que 3 caracteres.");
	}

	public static void validaEmailUsuario(String email) throws ErroCadastroException {
		if (email == null || email.equals("") || validaEmail(email) == false)
			throw new ErroCadastroException(
					"Formato de e-mail esta invalido.");
	}

	public static void validaNome(String nome) throws ErroCadastroException {
		if (nome == null || nome.equals("") || nome.trim().equals(""))
			throw new ErroCadastroException(
					"Nome dx usuarix nao pode ser vazio.");
	}
	
	/**
	 * Valida o dia da data fornecida pelo usuario ao criar um novo usuario
	 * retorna boolean Ex: 1510/10/2010 - retorna false.
	 * 
	 * @param String - data
	 *            
	 * @return boolean - true para dias validos e false para dias invalidos
	 */
	public static boolean validaDiaDaData(String data) {
		String[] dia = data.split("/");
		if (dia[0].length() > 2)
			return true;
		return false;
	}
	
	
	/**
	 * Valida um email fornecido pelo usuario ao criar um novo usuario retorna
	 * boolean Ex: alguem@mail - retorna false..
	 * 
	 * @param String - email
	 *         
	 * @return boolean true para emails validos e false para emails invalidos
	 */
	public static boolean validaEmail(String email) {
		Pattern p = Pattern
				.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher emailFiltrado = p.matcher(email);
		if (emailFiltrado.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Valida um intervalo data fornecida pelo usuario ao criar um novo usuario
	 * retorna boolean Ex: 32/10/2010 - retorna false..
	 * 
	 * @param String - data
	 *            
	 * @return boolean true - para datas validas e false para datas invalidas
	 */
	public static boolean validaIntervalosDeData(String data) {
		String[] valores = data.split("/");

		if (!data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")
				|| Integer.parseInt(valores[0]) < 1
				|| Integer.parseInt(valores[0]) > 31)
			return false;
		if (!data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")
				|| Integer.parseInt(valores[1]) < 1
				|| Integer.parseInt(valores[1]) > 12)
			return false;
		if (!data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")
				|| Integer.parseInt(valores[2]) < 1)
			return false;
		return true;
	}
	
	public static boolean isDateValid(String strDate) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		formatter.setLenient(false);
		try {
			Date date = formatter.parse(strDate);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * Converte uma String para um objeto Date. Caso a String seja vazia ou
	 * nula, retorna null.
	 * 
	 * @param data
	 *            String no formato dd/MM/yyyy a ser formatada
	 * @return Date Objeto Date ou null caso receba uma String vazia ou nula
	 * @throws ParseException
	 * @throws Exception
	 *             Caso a String esteja no formato errado
	 */
	public static Date formataData(String data) throws ParseException {
		if (data == null || data.equals(""))
			return null;

		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (java.util.Date) formatter.parse(data);
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}
	
	public static String formataEmailArquivo(String email){
		String emailToString = email.replace("@", "[at]").replace(".", "");
		emailToString = emailToString + "./arquivos/posts_" + emailToString + ".txt";
		return emailToString;
	}

}
