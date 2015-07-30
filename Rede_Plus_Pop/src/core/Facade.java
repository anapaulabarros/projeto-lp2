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
	
	public String getInfoUsuario(String atributo){
		return controller.getInfoUsuario(atributo);
	}
	
	public void login(String email, String senha) throws Exception{
		controller.login(email, senha);
	}
	
	public void logout() throws Exception{
		controller.logout();
	}
	
	public void atualizaPerfil(String atributo, String valor) throws Exception{
		//tive problema aqui com o atributo senha, que vai precisar da senha antiga
		controller.atualizaPerfil(atributo, valor);
	}

}
