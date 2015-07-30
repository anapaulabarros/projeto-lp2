package core;

public class Facade {
	
	private Controller controller;
	
	public void iniciaSistema(){
		controller.iniciaSistema();
	}
	
	public void fechaSistema() throws Exception{
		controller.fechaSistema();
	}
	
	public void cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws Exception{
		controller.cadastraUsuario(nome, email, senha, dataNasc, imagem);
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


}
