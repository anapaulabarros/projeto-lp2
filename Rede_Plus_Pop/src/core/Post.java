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
		
	}
	
	/*
	 * so a mensagem do post
	 */
	public String filtraTexto(String mensagem) {
        List<String> conteudo = new ArrayList<String>();
        String[] palavras = mensagem.split(" ");
        for (String palavra : palavras) {
            if(!palavra.startsWith("#") && !palavra.startsWith("<"))
                conteudo.add(palavra);
        }
        return String.join(" ", conteudo);
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
		String[] palavras = filtraHashtags(mensagem).split(" ");
		for (String hashtag: palavras){
			retorno = retorno + hashtag + ",";
		}
		return retorno.substring(0, retorno.length() - 1);
	}

	public String getMensagem() {
		String retorno = "";
		if (filtraMidias(mensagem).isEmpty()){
			retorno = retorno + filtraTexto(mensagem);
		}else{
			retorno = retorno + filtraTexto(mensagem) + " " + filtraMidias(mensagem);
		}
		return retorno;
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

	public String getDataPostFormatada() {
		String[] data = dataPublicacao.split("[/, ]");
		return data[2]+"-"+data[1]+"-"+data[0] + dataPublicacao.substring(10, dataPublicacao.length());
	}
	
    
	@Override
	public String toString(){
		return this.mensagem + " (" + getDataPostFormatada() + ")";
	}
}
