package core;

public class Controller {
	
	private SystemPop sistemaPop;
	
	
	public Controller() {
		sistemaPop = new SystemPop();
	}
	
	
	public boolean cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem, String telefone) throws Exception {
		
		if(imagem == null || imagem.equals(""))
			sistemaPop.cadastraUsuario(new Usuario(nome, email, senha, dataNasc, telefone));
		else
			sistemaPop.cadastraUsuario(new Usuario(nome, email, senha, dataNasc, imagem,telefone));
		return true;
		
	}
	
	
	public boolean login(String email, String senha) throws Exception {
		return sistemaPop.login(email, senha);
	}
}
