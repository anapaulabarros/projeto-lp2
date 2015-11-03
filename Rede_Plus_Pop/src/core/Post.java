package core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import midias.ConteudoMidia;

public class Post implements Comparable<Post>, Comparator<Post>, Serializable {

	private final long serialVersionUID = 42L;
	private String mensagemCompleta;
	private String texto;
	private List<ConteudoMidia> conteudoMidias;
	private List<String> hashtags;
	private int curtidas;
	private int popularidade;
	private int rejeitadas;
	private String dataPublicacao;

	public Post(String mensagemCompleta, String texto,
			List<ConteudoMidia> conteudo, List<String> hashtags, String data) {
		this.mensagemCompleta = mensagemCompleta;
		this.texto = texto;
		this.conteudoMidias = conteudo;
		this.hashtags = hashtags;
		this.dataPublicacao = data;
		this.popularidade = 0;
		this.curtidas = 0;
		this.rejeitadas = 0;
	}

	public void adicionaHashtag(String hashtag) {
		this.mensagemCompleta = this.mensagemCompleta + " " + hashtag;
		this.hashtags.add(hashtag);		
	}

	@Override
	public String toString() {
		String hashtags = "";
		if (this.mensagemCompleta.contains("#"))
			hashtags = this.hashtags.toString();
		return getDataPostFormatada() + SystemPop.QUEBRA_DE_LINHA + "Conteudo:"
				+ SystemPop.QUEBRA_DE_LINHA + this.texto
				+ SystemPop.QUEBRA_DE_LINHA + conteudoMidias
				+ SystemPop.QUEBRA_DE_LINHA + hashtags
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

	public String getListaHashtag() {
		if (!this.hashtags.isEmpty()) {
			String retorno = String.join(",", this.hashtags);
			return retorno;
		}
		return "";
	}

	public String getMensagemSemHashtags() {
		return UtilPost.removehastags(mensagemCompleta);
	}

	public String getTexto() {
		return this.texto;
	}

	public String getConteudo(int indice) {
		if (indice == 0) {
			return getTexto();
		} else {
			return conteudoMidias.get(indice - 1).toString();
		}
	}

	public int getQuantidadeItens() {
		return this.conteudoMidias.size() + 1;
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

	public String getDataPostFormatada() {
		String[] data = dataPublicacao.split("[/, ]");
		return data[2] + "-" + data[1] + "-" + data[0] + " " + data[3];
	}

	// Metodo para retornar o post em formato de String
	public String getPostString() {
		return this.mensagemCompleta + " (" + getDataPostFormatada() + ")";
	}
}
