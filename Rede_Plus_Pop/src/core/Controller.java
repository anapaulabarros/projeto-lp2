package core;

import treatmentsExceptions.PostExceptions;
import treatmentsExceptions.SystemPopExceptions;
import treatmentsExceptions.UsuarioExceptions;


public class Controller {
	
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	
	private SystemPop sistemaPop;
	
	
	public Controller() {
		sistemaPop = new SystemPop();
	}
	
	
	public String cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws SystemPopExceptions,
			UsuarioExceptions, Exception {
		sistemaPop.cadastraUsuario(new Usuario(nome, email, senha, dataNasc, imagem));
		return email;
	}
	
	public void login(String email, String senha) throws SystemPopExceptions {
		sistemaPop.login(email, senha);
	}
	
	public void logout() throws SystemPopExceptions  {
		sistemaPop.logout();
	}
	
	public void fecharSistema() throws SystemPopExceptions  {
		sistemaPop.fecharSistema();
	}
	
	public void curtir(int indexPost, String email) throws SystemPopExceptions {
		sistemaPop.interagirComPost(indexPost, email, CURTIR);
	}
	
	public void rejeitar(int indexPost, String email) throws SystemPopExceptions {
		sistemaPop.interagirComPost(indexPost, email, REJEITAR);
	}

	public void iniciaSistema() {
		sistemaPop.iniciaSistema();		
	}

	public void fechaSistema() throws SystemPopExceptions {
		sistemaPop.fechaSistema();
	}
	
	public void removeUsuario(String email) throws SystemPopExceptions {
		sistemaPop.removeUsuario(email);
	}

	public String getInfoUsuario(String atributo) throws SystemPopExceptions {
		return sistemaPop.getInfoUsuario(atributo);
	}

	public void atualizaPerfil(String atributo, String valor) throws SystemPopExceptions,Exception {
		sistemaPop.atualizaPerfil(atributo, valor);		
	}

	public void atualizaPerfil(String atributo, String valor, String velhaSenha) throws SystemPopExceptions,Exception {
		sistemaPop.atualizaPerfil(atributo, valor, velhaSenha);
	}

	public String getPost(int post) {
		return sistemaPop.getPost(post);
	}

	public String getPost(String atributo, int post) {
		return sistemaPop.getPost(atributo, post);
	}
	
	public String getConteudoPost(int indice, int post) throws SystemPopExceptions, PostExceptions {
		return sistemaPop.getConteudoPost(indice, post);
	}


	public void criaPost(String mensagem, String data) throws SystemPopExceptions,PostExceptions {
		sistemaPop.criaPost(mensagem, data);
	}


	public String getInfoUsuario(String atributo, String usuario) throws Exception {
		return sistemaPop.getInfoUsuario(atributo, usuario);
	}

}
