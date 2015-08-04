package core;

public class FactoryUsuario {

	public Usuario cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws Exception {
		Usuario novoUsuario = new Usuario(nome, email, senha, dataNasc, imagem);
		return novoUsuario;
	}
	
	public Usuario cadastraUsuario(String nome, String email, String senha, String dataNasc) throws Exception {
		Usuario novoUsuario = new Usuario(nome, email, senha, dataNasc, "resources/defaultImage.png");
		return novoUsuario;
	}
}
