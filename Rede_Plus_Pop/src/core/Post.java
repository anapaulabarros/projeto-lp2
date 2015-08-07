package core;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import treatmentsExceptions.PostExceptions;

public class Post {
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	
	private String mensagem;
	private List<String> conteudo;
	private int likes;
	private int popularidade;
	private int unlikes;
	private String dataPublicacao;
	private List<String> listaHashtag;

	
	public Post(String mensagem, String dataPublicao) throws PostExceptions, ParseException {
		if(mensagem == null || mensagem.equals(""))
			throw new PostExceptions("A mensagem nao pode ser nula ou vazia.");
		this.conteudo = new ArrayList<String>();
		this.conteudo.add(filtraTexto(mensagem));
		this.conteudo.add(filtraMidias(mensagem));
		this.conteudo.add(filtraHashtags(mensagem));
		if(conteudo.get(0).length() > 200){
			throw new PostExceptions("Nao eh possivel criar o post. O limite maximo da mensagem sao 200 caracteres.");
		}
		if(validaHashtags(mensagem) != null){
			throw new PostExceptions("Nao eh possivel criar o post. As hashtags devem comecar com '#'. Erro na hashtag: '" + validaHashtags(mensagem) + "'.");
		}
		this.mensagem = mensagem;
		this.dataPublicacao = dataPublicao;
		popularidade = 0;
		likes = 0;
		unlikes = 0;
		listaHashtag = new ArrayList<String>();	
		
	}
	
	/*
	 * so a mensagem do post
	 */
	public String filtraTexto(String mensagem) {
        List<String> conteudo = new ArrayList<String>();
        String[] palavras = mensagem.split(" ");
        for (String palavra : palavras) {
            if(!palavra.startsWith("#"))
                conteudo.add(palavra);
        }
        return String.join(" ", conteudo);
    }
	
	
	/*
	 * mensagem + midias
	 *  Metodo para retornar a mensagem sem as hasTags de um Post
	 *  Ex: "uma mensagem de um usuario. #teste" - retrono: "Uma mensagem de um usuario." 
	 *  @param mensagem String
	 *  @return String
	 * */
	public String filtraMensagem(String mensagem) {
		return mensagem.substring(0, mensagem.indexOf("<"));
	}
	
	public  String filtraMidias(String mensagem) {
		List<String> conteudo = new ArrayList<String>();
		String[] palavras = mensagem.split(" ");
		for (String palavra : palavras) {
		    if(palavra.startsWith("<")) {
		    	conteudo.add(palavra);
		    }
		}
		return String.join(" ", conteudo);
	}
	
	public String filtraHashtags(String mensagem) {
		return mensagem.substring(mensagem.indexOf("#"), mensagem.length());
	}
	
	/*
	 *  Metodo para validar hasTags. Se a Hastag nao tiver no primeiro
	 *  caractere da palavra '#' o metodo retorna a palavra invalida
	 *  Ex: "Um teste. #teste nova_mensagem #hastag" - retorno: nova_mensagem
	 *  
	 *  @param mensagem String
	 *  @return boolean
	 * */
	public String validaHashtags(String mensagem) {
	
		String[] palavras = mensagem.substring(mensagem.indexOf("#"), mensagem.length()).split(" ");
		for (String palavra : palavras) {
			if(!palavra.startsWith("#"))
				 return palavra;
		}
		 return null;
	}
	
	public String getListaHashtag() {
		String retorno = "";
		for (String hashtag: listaHashtag){
			retorno = retorno + hashtag + ",";
		}
		return retorno.substring(0, retorno.length() - 1);
	}

	public String getMensagem() {
		return conteudo.get(0);
	}

	public String getConteudo(int indice) {
		return conteudo.get(indice);
	}
	
	public int getQuantidadeItens(){
		return this.conteudo.size();
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
		return dataPublicacao;
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


	@Override
	public String toString(){
		return this.mensagem + " (" + this.dataPublicacao + ")";
	}
}
