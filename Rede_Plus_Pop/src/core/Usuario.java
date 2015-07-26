package core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
	
	public static final String QUEBRA_DE_LINHA = System.getProperty("line.separator");
	
	private String nome;
	private String email;
	private String senha;
	private String imagem;
	private String telefone;
	private Date dataNasc;
	private List<Post> posts;
	
	
	public Usuario(String nome, String email, String senha, String dataNasc, String imagem, String telefone) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		try {
			this.dataNasc = formataData(dataNasc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.imagem = imagem;
		this.telefone = telefone;
		posts = new ArrayList<Post>();
	}
	
	public Usuario(String nome, String email, String senha, String dataNasc, String telefone) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		try {
			this.dataNasc = formataData(dataNasc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.telefone = telefone;
		posts = new ArrayList<Post>();
		imagem = "../img/defaultImage.jpg";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getDataNasc() {
		DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		return dataFormat.format(dataNasc);
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public List<Post> getPosts() {
		return posts;
	}
	
	/** 
     * Converte uma String para um objeto Date. Caso a String seja vazia ou nula,  
     * retorna null.
     * @param data String no formato dd/MM/yyyy a ser formatada 
     * @return Date Objeto Date ou null caso receba uma String vazia ou nula 
     * @throws Exception Caso a String esteja no formato errado 
     */  
    public static Date formataData(String data) throws Exception {   
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
				+ "Telefone: " + this.telefone + QUEBRA_DE_LINHA 
				+ "Data de Nascimento: " + getDataNasc() + QUEBRA_DE_LINHA
				+ "Posts: " + this.posts;
	}
}