package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post {
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	
	private String mensagem;
	private List<String> conteudo;
	private int likes;
	private int popularidade;
	private int unlikes;
	private Date dataPublicacao;
	private List<String> listaHashtag;
	private Map<String, ArrayList<String>> dicionarioHasTags;
	
	public Post(String mensagem, Date dataPublicao) throws Exception {
		if(mensagem == null || mensagem.equals(""))
			throw new Exception("A mensagem nao pode ser nula ou vazia.");
		if(mensagem.length() > 200){
			throw new Exception("Nao eh possivel criar o post. O limite maximo da mensagem sao 200 caracteres.");
		}
		if(validaHastgs(mensagem) != null)
			throw new Exception("Nao eh possivel criar o post. As hashtags devem comecar com '#'.Erro na hashtag: '" + validaHastgs(mensagem) + "'.");
		this.mensagem = mensagem;
		this.conteudo = new ArrayList<String>();
		for (String item: mensagem.split(" ")){
			conteudo.add(item);
		}
		this.dataPublicacao = dataPublicao;
		popularidade = 0;
		likes = 0;
		unlikes = 0;
		listaHashtag = new ArrayList<String>();	
		dicionarioHasTags = new HashMap<String, ArrayList<String>>();
		if(!filtraHastgs(mensagem).isEmpty())
			dicionarioHasTags = dicionarioDeHastags(mensagem);
	}
	
	public String getListaHashtag() {
		String retorno = "";
		for (String hashtag: listaHashtag){
			retorno = retorno + hashtag + ",";
		}
		return retorno.substring(0, retorno.length() - 1);
	}

	public String getMensagem() {
		return mensagem;
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
		DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		return dataFormat.format(dataPublicacao);
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
	
	/*
	 *  Metodo para retornar a mensagem sem as hasTags de um Post
	 *  Ex: "uma mensagem de um usuario. #teste" - retrono: "Uma mensagem de um usuario." 
	 *  @param mensagem String
	 *  @return String
	 * */
	public String filtraMensagem(String mensagem) {
		return mensagem.substring(0, mensagem.indexOf("#"));
	}
	
	/*
	 *  Metodo para filtrar todas as hasTags que uma mensagem possui, retorna apenas uma lista de hasTags
	 *  Ex: "Uma mensagem. #teste" - retorno : ['#teste']
	 *  @param mensagem String
	 *  @return List<String>
	 * */
	public List<String> filtraHastgs(String mensagem) {
		List<String> listaDeHastags = new ArrayList<String>();
		String[] palavras = mensagem.substring(mensagem.indexOf("#"), mensagem.length()).split(" ");
		for (String palavra : palavras) {
			if(!palavra.startsWith("#"))
				listaDeHastags.add(palavra);
		}
		 return listaDeHastags;
	}
	
	/*
	 * M�todo para armazenar um dicionario de hastags e suas mensagens associadas
	 * Ex: [#frio={faz muito frio hoje}]
     * @param mensgem String 
     * @return Map<String, ArrayList<String>>  
	 * */
	public Map<String, ArrayList<String>> dicionarioDeHastags(String mensagem) {
		List<String> listaDeHastags = filtraHastgs(mensagem);
		String textoFiltrado = filtraMensagem(mensagem);
		Map<String, ArrayList<String>> hastags = new HashMap<String, ArrayList<String>>();
		
		for(int i = 0; i < listaDeHastags.size(); i++) {
			 if(!hastags.keySet().contains(listaDeHastags.get(i)))
				 hastags.put(listaDeHastags.get(i), new ArrayList<String>());
			 hastags.get(listaDeHastags.get(i)).add(textoFiltrado);
		 }
		
		return hastags;
	}
	
	/*
	 *  Metodo para validar hasTags. Se a Hastag nao tiver no primeiro
	 *  caractere da palavra '#' o metodo retorna a palavra invalida
	 *  Ex: "Um teste. #teste nova_mensagem #hastag" - retorno: nova_mensagem
	 *  
	 *  @param mensagem String
	 *  @return boolean
	 * */
	public String validaHastgs(String mensagem) {
	
		String[] palavras = mensagem.substring(mensagem.indexOf("#"), mensagem.length()).split(" ");
		for (String palavra : palavras) {
			if(!palavra.startsWith("#"))
				 return palavra;
		}
		 return null;
	}
	
	public Map<String, ArrayList<String>> getDicionarioHasTags() {
		return dicionarioHasTags;
	}

	@Override
	public String toString(){
		return this.mensagem + " (" + this.dataPublicacao + ")";
	}
}
