package core;


public class Controller {
	
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	
	private SystemPop sistemaPop;
	
	
	public Controller() {
		sistemaPop = new SystemPop();
	}
	
	
	public Usuario cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws Exception {
		Usuario novo;
		if(imagem == null || imagem.equals("")){
			novo = new Usuario(nome, email, senha, dataNasc);
			sistemaPop.cadastraUsuario(novo);
			return novo;
		}else{
			novo = new Usuario(nome, email, senha, dataNasc, imagem);
			sistemaPop.cadastraUsuario(novo);
			return novo;
		}
	}
	
	public void login(String email, String senha) throws Exception {
		sistemaPop.login(email, senha);
	}
	
	public void logout() throws Exception  {
		sistemaPop.logout();
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

	public void fechaSistema() throws Exception {
		sistemaPop.fechaSistema();
	}

	public String getInfoUsuario(String atributo) throws Exception {
		return sistemaPop.getInfoUsuario(atributo);
	}

	public void atualizaPerfil(String atributo, String valor) throws Exception {
		sistemaPop.atualizaPerfil(atributo, valor);		
	}

	public void atualizaPerfil(String atributo, String valor, String velhaSenha) throws Exception {
		sistemaPop.atualizaPerfil(atributo, valor, velhaSenha);
	}

	public String getPost(int post) {
		return sistemaPop.getPost(post);
	}

	public String getPost(String atributo, int post) {
		return sistemaPop.getPost(atributo, post);
	}
	
	public String getConteudoPost(int indice, int post) throws Exception {
		return sistemaPop.getConteudoPost(indice, post);
	}


	public void criaPost(String mensagem, String data) throws Exception {
		sistemaPop.criaPost(mensagem, data);
	}

}
