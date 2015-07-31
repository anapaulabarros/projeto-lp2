package core;

import easyaccept.EasyAccept;

public class Facade {
	
	private Controller controller;
	
	public void iniciaSistema(){
		controller.iniciaSistema();
	}
	
	public void fechaSistema() throws Exception{
		controller.fechaSistema();
	}
	
	public Usuario cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws Exception{
		return controller.cadastraUsuario(nome, email, senha, dataNasc, imagem);
	}
	
	public String getInfoUsuario(String atributo) throws Exception{
		return controller.getInfoUsuario(atributo);
	}
	
	public void login(String email, String senha) throws Exception{
		controller.login(email, senha);
	}
	
	public void logout() throws Exception{
		controller.logout();
	}
	
	public void atualizaPerfil(String atributo, String valor) throws Exception{
		controller.atualizaPerfil(atributo, valor);
	}
	
	public void atualizaPerfil(String atributo, String valor, String velhaSenha) throws Exception{
		controller.atualizaPerfil(atributo, valor, velhaSenha);
	}
	
	public String getPost(int post){
		return controller.getPost(post);
	}
	
	public String getPost(String atributo, int post){
		return controller.getPost(atributo, post);
	}
	
	public String getConteudoPost(int indice, int post) throws Exception{
		return controller.getConteudoPost(indice, post);
	}
	
	public void criaPost(String mensagem, String data) throws Exception{
		controller.criaPost(mensagem, data);
	}

	public static void main(String[] args) {
		args = new String[] {"core.Facade", "teste_aceitacao/usecase_1"};
		EasyAccept.main(args);
	}

}
