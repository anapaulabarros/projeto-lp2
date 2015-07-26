package core;

import java.text.DateFormat;
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
	
	
	public Usuario(String nome, String email, String senha, Date dataNasc, String imagem, String telefone) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNasc = dataNasc;
		this.imagem = imagem;
		this.telefone = telefone;
		posts = new ArrayList<Post>();
	}
	
	public Usuario(String nome, String email, String senha, Date dataNasc, String telefone) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNasc = dataNasc;
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
