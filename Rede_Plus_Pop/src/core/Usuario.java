package core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
	
	public static final String QUEBRA_DE_LINHA = System.getProperty("line.separator");
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	
	private String nome;
	private String email;
	private String senha;
	private String imagem;
	private Date dataNasc;
	private List<Post> posts;
	
	
	public Usuario(String nome, String email, String senha, String dataNasc, String imagem) throws Exception {
		
		if(nome == null || nome.equals(""))
			throw new Exception("O nome nao pode ser nulo ou vazio.");
		if(email == null || email.equals(""))
			throw new Exception("O email nao pode ser nulo ou vazio.");
		if(senha == null || senha.equals("") || senha.length() < 3)
			throw new Exception("A senha nao pode ser nula, vazia ou menor que 3 caracteres.");
		if(dataNasc == null || dataNasc.equals(""))
			throw new Exception("A data de nascimento nao pode ser nula ou vazia.");
		
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		try {
			this.dataNasc = formataData(dataNasc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.imagem = imagem;
		posts = new ArrayList<Post>();
	}
	
	public Usuario(String nome, String email, String senha, String dataNasc) throws Exception {
		this(nome, email, senha, dataNasc, "resources/defaultImage.png");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws Exception {
		if(nome == null || nome.equals(""))
			throw new Exception("Nome nao pode ser nulo ou vazio.");
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception {
		if(email == null || email.equals(""))
			throw new Exception("Email nao pode ser nulo ou vazio.");
		this.email = email;
	}

	public String getSenha() {
		return "A senha do usuario eh protegida.";
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) throws Exception {
		if(nome == null || nome.equals(""))
			throw new Exception("Imagem nao pode ser nula ou vazia.");
		this.imagem = imagem;
	}

	public String getDataNasc() {
		DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		return dataFormat.format(dataNasc);
	}

	public void setDataNasc(String dataNasc) throws Exception {
		this.dataNasc = formataData(dataNasc);
	}

	public List<Post> getPosts() {
		return posts;
	}
	
	public void postar(Post novoPost) throws Exception {
		posts.add(novoPost);
	}
	
	public void interagirPost(int indexPost, String opcao) {
		if(opcao.equals(CURTIR))
			posts.get(indexPost).setPopularidade(CURTIR);
		if(opcao.equals(REJEITAR))
			posts.get(indexPost).setPopularidade(REJEITAR);
	}
	
	/** 
     * Converte uma String para um objeto Date. Caso a String seja vazia ou nula,  
     * retorna null.
     * @param data String no formato dd/MM/yyyy a ser formatada 
     * @return Date Objeto Date ou null caso receba uma String vazia ou nula 
     * @throws Exception Caso a String esteja no formato errado 
     */  
    private static Date formataData(String data) throws Exception {   
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
	public String toString() {
		return "Nome: " + this.nome + QUEBRA_DE_LINHA
				+ "Email:  " + this.email + QUEBRA_DE_LINHA
				+ "Senha:  *******" +  QUEBRA_DE_LINHA 
				+ "Data de Nascimento: " + getDataNasc() + QUEBRA_DE_LINHA
				+ "Posts: " + this.posts;
	}
}
