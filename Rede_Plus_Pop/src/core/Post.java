package core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	private Date dataPublicacao;
	private List<String> listaHashtag;

	
	public Post(String mensagem, String dataPublicao) throws PostExceptions, ParseException {
		if(mensagem == null || mensagem.equals(""))
			throw new PostExceptions("A mensagem nao pode ser nula ou vazia.");
		this.mensagem = mensagem;
		this.conteudo = new ArrayList<String>();
		for (String item: mensagem.split(" ")){
			conteudo.add(item);
		}
		if(conteudo.get(0).length() > 200){
			throw new PostExceptions("Nao eh possivel criar o post. O limite maximo da mensagem sao 200 caracteres.");
		}
		this.dataPublicacao = formataData(dataPublicao);
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
	
	/** 
     * Converte uma String para um objeto Date. Caso a String seja vazia ou nula,  
     * retorna null.
     * @param data String no formato dd/MM/yyyy a ser formatada 
     * @return Date Objeto Date ou null caso receba uma String vazia ou nula 
	 * @throws ParseException 
     * @throws Exception Caso a String esteja no formato errado 
     */  
    private Date formataData(String data) throws ParseException {   
        if (data == null || data.equals(""))  
            return null;  
          
        Date date = null;  
        try {  
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            date = (java.util.Date)formatter.parse(data);  
        } catch (ParseException e) {              
            throw e;  
        }  
        return date;  
    } 


	@Override
	public String toString(){
		return this.mensagem + " (" + this.dataPublicacao + ")";
	}
}
