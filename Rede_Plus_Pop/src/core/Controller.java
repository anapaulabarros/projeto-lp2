package core;

public class Controller {
	
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	
	private SystemPop sistemaPop;
	
	
	public Controller() {
		sistemaPop = new SystemPop();
	}
	
	
	public void cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws Exception {
		
		if(imagem == null || imagem.equals(""))
			sistemaPop.cadastraUsuario(new Usuario(nome, email, senha, dataNasc));
		else
			sistemaPop.cadastraUsuario(new Usuario(nome, email, senha, dataNasc, imagem));
			
	}
	
	public void login(String email, String senha) throws Exception {
		sistemaPop.login(email, senha);
	}
	
	public void logout() throws Exception  {
		sistemaPop.logout();
	}
	
	public void postar(String mensagem) throws Exception {
		sistemaPop.postar(mensagem);
	}
	
	public void curtir(int indexPost, String email) throws Exception {
		sistemaPop.interagirComPost(indexPost, email, CURTIR);
	}
	
	public void rejeitar(int indexPost, String email) throws Exception {
		sistemaPop.interagirComPost(indexPost, email, REJEITAR);
	}

	public void iniciaSistema() {
		sistemaPop.iniciaSistema();		
	}

	public void fechaSistema() {
		sistemaPop.fechaSistema();
	}

	public String getInfoUsuario(String atributo) {
		return sistemaPop.getInfoUsuario(atributo);
	}

	public void atualizaPerfil(String atributo, String valor) throws Exception {
		sistemaPop.atualizaPerfil(atributo, valor);		
	}
}
