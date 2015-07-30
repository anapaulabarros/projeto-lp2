package core;

import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post {
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	
	private String mensagem;
	private int likes;
	private int popularidade;
	private int unlikes;
	private Date dataPublicacao;
	private List<String> listaHashtag;
	
	public Post(String mensagem, Date dataPublicao) throws Exception {
		if(mensagem == null || mensagem.equals(""))
			throw new Exception("A mensagem nao pode ser nula ou vazia.");
		if(mensagem.length() > 200){
			throw new Exception("Nao eh possivel criar o post. O limite maximo da mensagem sao 200 caracteres.");
		}
		this.mensagem = mensagem;
		this.dataPublicacao = dataPublicao;
		popularidade = 0;
		likes = 0;
		unlikes = 0;
		listaHashtag = new ArrayList<String>();		
	}
	
	public String getListaHashtag() {
		String retorno = "";
		for (String hashtag: listaHashtag){
			retorno = retorno + hashtag + ",";
		}
		return retorno;
	}

	public String getMensagem() {
		return mensagem;
	}

	public int getLikes() {
		return likes;
	}

	public int getPopularidade() {
		return popularidade;
	}

	public int getUnlikes() {
		return unlikes;
	}

	public String getDataPublicacao() {
		//ajeitar esse negocio
		return dataPublicacao.toString();
	}

	public void setPopularidade(String opcao) {
		if(opcao == CURTIR) {
			this.popularidade += 1;
			setLike();
		}
		if(opcao == REJEITAR) {
			this.popularidade -= 1;
			setUnlike();
		}
	}
	
	public void setLike() {
		this.likes += 1;
	}
	
	public void setUnlike() {
		this.unlikes += 1;
	}
	
	public String toString(){
		return this.mensagem + " (" + this.dataPublicacao + ")";
	}
}
