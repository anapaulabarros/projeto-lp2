package core;

import java.util.ArrayList;
import java.util.List;

import treatmentsExceptions.PostException;

public class UtilPost {

	public UtilPost() {
		
	}
	
	public static void verificaValidadeDasHastags(String mensagem) throws PostException {
		if (validaHashtags(mensagem) != null) {
			throw new PostException("Nao eh possivel criar o post. As hashtags devem comecar com '#'. Erro na hashtag: '" + validaHashtags(mensagem) + "'.");
		}
	}

	public static void verificaTamanhoDaMensagem(List<String> conteudo) throws PostException {
		if (conteudo.get(0).length() > 200) {
			throw new PostException(
					"Nao eh possivel criar o post. O limite maximo da mensagem sao 200 caracteres.");
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
		List<String> conteudo = new ArrayList<String>();
		String[] palavras = mensagem.split(" ");
		for (String palavra : palavras) {
			if (!palavra.startsWith("#") && !palavra.startsWith("<"))
				conteudo.add(palavra);
		}
		return String.join(" ", conteudo);
	}

	/**
	 * metodo para retornar apenas as midias do post removendo conteudo de
	 * textos e hastags
	 * 
	 * @param String mensagem
	 * 
	 * @return String conteudo
	 */
	public static String filtraMidias(String mensagem) {
		List<String> conteudo = new ArrayList<String>();
		String[] palavras = mensagem.split(" ");
		for (String palavra : palavras) {
			if (palavra.startsWith("<audio>") || palavra.startsWith("<imagem>") || palavra.startsWith("<video>")) {
				conteudo.add(palavra);
			}
		}
		return String.join(" ", conteudo);
	}

	/**
	 * metodo para retornar apenas os videos ou apenas os audios,
	 * se o retorno for nulo entao nao existe conteudo de midia no post
	 * 
	 * @param String mensagem, int opcao: 1 para audio e 2 para video
	 * 
	 * @return List<String> conteudoDeMidiaEspecifica
	 */
	public static List<String> filtraAudioVideo(String mensagem, int opcao) {
		List<String> conteudoFinal = new ArrayList<String>();
		String[] midias = filtraMidias(mensagem).split(" ");
		for (String string : midias) {
			if(opcao == 1 & string.startsWith("<audio>"))
				conteudoFinal.add(string);
			if(opcao == 2 & (string.startsWith("<video>")))
					conteudoFinal.add(string);
		}
		return conteudoFinal;
	}
	
	
	/**
	 * metodo para retornar apenas as hastags do post removendo conteudo de
	 * midias e texto
	 * 
	 * @param String mensagem
	 * 
	 * @return String hastags
	 */
	public static String filtraHashtags(String mensagem) {
		return mensagem.substring(mensagem.indexOf("#"), mensagem.length());
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


}
