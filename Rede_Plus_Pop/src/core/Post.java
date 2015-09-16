package core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import treatmentsExceptions.PostException;

public class Post implements Comparable<Post>, Comparator<Post>, Serializable{

	private final long serialVersionUID = 42L;
	private String mensagem;
	private List<String> conteudo;
	private int curtidas;
	private int popularidade;
	private int rejeitadas;
	private String dataPublicacao;

	/**
	 * construtor da classe Post e os seus atributos
	 * 
	 * @param mensagem
	 * @param dataPublicao
	 * @throws PostException
	 * @throws ParseException
	 */
	public Post(String mensagem, String dataPublicao) throws PostException,
			ParseException {
		verificaConteudoDaMensagem(mensagem);
		this.conteudo = new ArrayList<String>();
		this.conteudo.add(filtraTexto(mensagem));
		this.conteudo.add(filtraMidias(mensagem));
		verificaTamanhoDaMensagem();
		if(mensagem.contains("#")){
			verificaValidadeDasHastags(mensagem);
			this.conteudo.add(filtraHashtags(mensagem));
		}
		this.mensagem = mensagem;
		this.dataPublicacao = dataPublicao;
		this.popularidade = 0;
		curtidas = 0;
		rejeitadas = 0;

	}

	private void verificaValidadeDasHastags(String mensagem) throws PostException {
		if (validaHashtags(mensagem) != null) {
			throw new PostException("Nao eh possivel criar o post. As hashtags devem comecar com '#'. Erro na hashtag: '" + validaHashtags(mensagem) + "'.");
		}
	}

	private void verificaTamanhoDaMensagem() throws PostException {
		if (conteudo.get(0).length() > 200) {
			throw new PostException(
					"Nao eh possivel criar o post. O limite maximo da mensagem sao 200 caracteres.");
		}
	}

	private void verificaConteudoDaMensagem(String mensagem)
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
	public String filtraTexto(String mensagem) {
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
	public String filtraMidias(String mensagem) {
		List<String> conteudo = new ArrayList<String>();
		String[] palavras = mensagem.split(" ");
		for (String palavra : palavras) {
			if (palavra.startsWith("<audio>") || palavra.startsWith("<imagem>")) {
				conteudo.add(palavra);
			}
		}
		return String.join(" ", conteudo);
	}

	/**
	 * metodo para retornar apenas as hastags do post removendo conteudo de
	 * midias e texto
	 * 
	 * @param String mensagem
	 * 
	 * @return String hastags
	 */
	public String filtraHashtags(String mensagem) {
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
	public String validaHashtags(String mensagem) {

		String[] palavras = mensagem.substring(mensagem.indexOf("#"),
				mensagem.length()).split(" ");
		for (String palavra : palavras) {
			if (!palavra.startsWith("#"))
				return palavra;
		}
		return null;
	}

	public String getListaHashtag() {
		String retorno = "";
		String[] palavras = conteudo.get(2).split(" ");
		for (String hashtag : palavras) {
			retorno = retorno + hashtag + ",";
		}
		return retorno.substring(0, retorno.length() - 1);
	}

	public String getMensagem() {
		String retorno = "";
		if (filtraMidias(mensagem).isEmpty()) {
			retorno = retorno + filtraTexto(mensagem);
		} else {
			retorno = retorno + filtraTexto(mensagem) + " "
					+ filtraMidias(mensagem);
		}
		return retorno;
	}

	public String getConteudo(int indice) {
		return conteudo.get(indice);
	}

	public int getQuantidadeItens() {
		return this.conteudo.size();
	}

	public int getCurtidas() {
		return curtidas;
	}

	public int getPopularidade() {
		return popularidade;
	}

	public int getRejeitadas() {
		return rejeitadas;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}

	public void setPopularidade(int pontos) {
		this.popularidade = this.popularidade + pontos;
	}

	public void setCurtidas() {
		this.curtidas += 1;
	}

	public void setRejeitadas() {
		this.rejeitadas += 1;
	}
	
	public void adicionaHashtag(String hashtag){
		String adicionar = conteudo.get(2) + " " + hashtag;
		conteudo.set(2, adicionar);
	}

	public String getDataPostFormatada() {
		String[] data = dataPublicacao.split("[/, ]");
		return data[2] + "-" + data[1] + "-" + data[0] + " " + data[3];
	}

	/*
	 * Metodo para retornar o post em formato de String 
	 */
	public String getPostString() {
		return this.mensagem + " (" + getDataPostFormatada() + ")";
	}
	
	@Override
	public String toString() {
		String hastags = "";
		if(this.mensagem.contains("#"))
			hastags = getConteudo(2); 
		return  getDataPostFormatada() + 
				SystemPop.QUEBRA_DE_LINHA + 
				"Conteudo:" + 
				SystemPop.QUEBRA_DE_LINHA + 
				getConteudo(0) +
				SystemPop.QUEBRA_DE_LINHA +
				getConteudo(1)  +
				SystemPop.QUEBRA_DE_LINHA +
				hastags +
				SystemPop.QUEBRA_DE_LINHA +
				"+Pop: " + getPopularidade();
		
	}
	
	/**
	 * Metodo para ordernar os Posts pela data de publicacao
	 * 
	 *  @param Post - outroPost
	 *  @return int
	 */
	@Override
	public int compareTo(Post outroPost) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dataPostAtual = formatter.parse(this.dataPublicacao);
			Date dataOutroPost = formatter.parse(outroPost.dataPublicacao);
			if(dataOutroPost == dataPostAtual)
				return 0;
			else if(dataPostAtual.compareTo(dataOutroPost) == -1)
				  return -1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public int compare(Post post1, Post post2) {
		return 0;
    }
}
