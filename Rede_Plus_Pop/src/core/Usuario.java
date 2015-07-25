package core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {

	private String nome;
	private String email;
	private String senha;
	private String imagem;
	private String telefone;
	private Date dataNasc;
	private List<Post> posts;
	
	
	private Usuario(String nome, String email, String senha, Date dataNasc, String imagem, String telefone) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNasc = dataNasc;
		this.imagem = imagem;
		this.telefone = telefone;
		posts = new ArrayList<Post>();
	}
	
	private Usuario(String nome, String email, String senha, Date dataNasc, String telefone) {
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

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public List<Post> getPosts() {
		return posts;
	}
	
}
