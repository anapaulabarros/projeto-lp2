package core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post {
	public static final String CURTIR = "Curtir";
	public static final String REJETIAR = "Rejeitar";
	
	private String mensagem;
	private int likes;
	private int popularidade;
	private int unlikes;
	private Date dataPublicacao;
	private Map<String, List<String>> listaHastag;
	
	public Post(String mensagem, Date dataPublicao) throws Exception {
		if(mensagem == null || mensagem.equals(""))
			throw new Exception("A mensagem nao pode ser nula ou vazia.");
		this.mensagem = mensagem;
		this.dataPublicacao = dataPublicao;
		popularidade = 0;
		likes = 0;
		unlikes = 0;
		listaHastag = new HashMap<String, List<String>>();		
	}
	
	public Map<String, List<String>> getListaHastag() {
		return listaHastag;
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

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setPopularidade(String opcao) {
		if(opcao == CURTIR) {
			this.popularidade += 1;
			setLike();
		}
		if(opcao == REJETIAR) {
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
}
