package core;

public class FactoryUsuario {

	public Usuario cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem, String telefone) throws Exception {
		Usuario novoUsuario = new Usuario(nome, email, senha, dataNasc, imagem, telefone);
		return novoUsuario;
	}
	
	public Usuario cadastraUsuario(String nome, String email, String senha, String dataNasc, String telefone) throws Exception {
		Usuario novoUsuario = new Usuario(nome, email, senha, dataNasc, telefone);
		return novoUsuario;
	}
}
