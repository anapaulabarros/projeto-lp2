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

public class Post implements Comparable<Post>, Comparator<Post>, Serializable {

	private final long serialVersionUID = 42L;
	private String mensagem;
	private String texto;
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
	public Post(String mensagem, String dataPublicao) throws PostException{
		this.conteudo = new ArrayList<String>();
		this.texto = UtilPost.filtraTexto(mensagem);
		for (String midia : UtilPost.filtraMidias(mensagem)) {
			this.conteudo.add(midia);
		}
		UtilPost.verificaTamanhoDaMensagem(texto);
		if (mensagem.contains("#")) {
			for (String hashtag : UtilPost.filtraHashtags(mensagem)) {
				this.conteudo.add(hashtag);
			}
		}
		this.mensagem = mensagem;
		this.dataPublicacao = dataPublicao;
		this.popularidade = 0;
		curtidas = 0;
		rejeitadas = 0;

	}

	public String getListaHashtag() {
		String[] hashtags = mensagem.substring(mensagem.indexOf("#"),
				mensagem.length()).split(" ");
		String retorno = String.join(",", hashtags);
		return retorno;
	}

	public String getMensagemCompleta() {
		String retorno = "";
		if (UtilPost.filtraMidias(mensagem).isEmpty()) {
			retorno = retorno + UtilPost.filtraTexto(mensagem);
		} else {
			retorno = retorno + UtilPost.filtraTexto(mensagem);
			for (String midia : UtilPost.filtraMidias(mensagem)) {
				retorno = retorno + " " + midia;
			}
		}
		return retorno;
	}

	public String getTexto() {
		return this.texto;
	}
	
	public String getConteudo(int indice) {
		if (indice == 0) {
			return getTexto();
		}
		return conteudo.get(indice - 1);
	}

	public int getQuantidadeItens() {
		return this.conteudo.size() + 1;
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

	public void adicionaHashtag(String hashtag) {
		this.mensagem = this.mensagem + " " + hashtag;
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
		if (this.mensagem.contains("#"))
			hastags = getConteudo(2);
		return getDataPostFormatada() + SystemPop.QUEBRA_DE_LINHA + "Conteudo:"
				+ SystemPop.QUEBRA_DE_LINHA + this.texto
				+ SystemPop.QUEBRA_DE_LINHA 
				+ SystemPop.QUEBRA_DE_LINHA + hastags
				+ SystemPop.QUEBRA_DE_LINHA + "+Pop: " + getPopularidade();

	}

	/**
	 * Metodo para ordernar os Posts pela data de publicacao
	 * 
	 * @param Post
	 *            - outroPost
	 * @return int
	 */
	@Override
	public int compareTo(Post outroPost) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dataPostAtual = formatter.parse(this.dataPublicacao);
			Date dataOutroPost = formatter.parse(outroPost.dataPublicacao);
			if (dataOutroPost == dataPostAtual)
				return 0;
			else if (dataPostAtual.compareTo(dataOutroPost) == -1)
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
