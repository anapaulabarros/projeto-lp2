package core;

import java.util.ArrayList;
import java.util.List;

import midias.ConteudoMidia;
import midias.MidiaFactory;
import treatmentsExceptions.PostException;

public class UtilPost {
	
	private static MidiaFactory fabricaDeMidias;

	public UtilPost() {
		
	}
	
	public static void verificaValidadeDasHastags(String mensagem) throws PostException {
		if (validaHashtags(mensagem) != null) {
			throw new PostException("As hashtags devem comecar com '#'. Erro na hashtag: '" + validaHashtags(mensagem) + "'.");
		}
	}

	public static void verificaTamanhoDaMensagem(String conteudo) throws PostException {
		if (conteudo.length() > 200) {
			throw new PostException(
					"O limite maximo da mensagem sao 200 caracteres.");
		}
	}

	public static void verificaConteudoDaMensagem(String mensagem)
			throws PostException {
		if (mensagem == null || mensagem.equals(""))
			throw new PostException("A mensagem nao pode ser nula ou vazia.");
	}

	/**
	 * metodo para retornar apenas o texto do post removendo conteudo de midias,
	 * audios e hastags
	 * 
	 * @param String mensagem
	 * 
	 * @return String conteudo
	 */
	public static String filtraTexto(String mensagem) {
		List<String> conteudoTotal = new ArrayList<String>();
		String[] palavras = mensagem.split(" ");
		for (String palavra : palavras) {
			if (!palavra.startsWith("#") && !palavra.startsWith("<"))
				conteudoTotal.add(palavra);
		}
		return String.join(" ", conteudoTotal);
	}
	
	public static String removehastags(String mensagem) {
		List<String> conteudo = new ArrayList<String>();
		String[] palavras = mensagem.split(" ");
		for (String palavra : palavras) {
			if (!palavra.startsWith("#"))
				conteudo.add(palavra);
		}
		return String.join(" ", conteudo);
	}
	
	/**
	 * metodo para retornar apenas as hastags do post removendo conteudo de
	 * midias e texto
	 * 
	 * @param String mensagem
	 * 
	 * @return List<String> hastags
	 */
	public static List<String> filtraHashtags(String mensagem) {
		List<String> retorno = new ArrayList<String>();
		String[] hashtags = mensagem.substring(mensagem.indexOf("#"), mensagem.length()).split(" ");
		for (String hashtag: hashtags){
			retorno.add(hashtag);
		}
		return retorno;
	}

	/**
	 * Metodo para validar hasTags. Se a Hastag nao tiver no primeiro caractere
	 * da palavra '#' o metodo retorna a palavra invalida Ex:
	 * "Um teste. #teste nova_mensagem #hastag" - retorno: nova_mensagem
	 * 
	 * @param mensagem String
	 * 
	 * @return boolean
	 */
	public static String validaHashtags(String mensagem) {

		String[] palavras = mensagem.substring(mensagem.indexOf("#"),
				mensagem.length()).split(" ");
		for (String palavra : palavras) {
			if (!palavra.startsWith("#"))
				return palavra;
		}
		return null;
	}

	public static String cortaCaminhoMidia(String caminho){
		return caminho.substring(caminho.indexOf(">") + 1, caminho.lastIndexOf("<"));
	}

	/**
	 * metodo para criar as midias do post removendo conteudo de
	 * textos e hastags
	 * 
	 * @param String
	 *            mensagem
	 * 
	 * @return List<ConteudoMidia> conteudoMidias
	 */
	public static List<ConteudoMidia> criaMidias(String mensagem) {
		UtilPost.fabricaDeMidias = new MidiaFactory();
		String[] palavras = mensagem.split(" ");
		List<ConteudoMidia> conteudo = new ArrayList<ConteudoMidia>();
		for (String palavra : palavras) {
			if (palavra.startsWith("<")) {
				ConteudoMidia novaMidia = fabricaDeMidias.criaMidia(palavra);
				conteudo.add(novaMidia);
			}
		}
		return conteudo;
	}
}
