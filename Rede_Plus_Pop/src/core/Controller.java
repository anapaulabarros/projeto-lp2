package core;

public class Controller {
	
	private SystemPop sistemaPop;
	
	
	public Controller() {
		sistemaPop = new SystemPop();
	}
	
	
	public Usuario cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem, String telefone) throws Exception {

		if(nome == null || nome.equals(""))
			throw new Exception("O nome nao pode ser nulo ou vazio.");
		if(email == null || email.equals(""))
			throw new Exception("O email nao pode ser nulo ou vazio.");
		if(senha == null || senha.equals("") || senha.length() < 3)
			throw new Exception("A senha nao pode ser nula, vazia ou menor que 3 caracteres.");
		if(dataNasc == null || dataNasc.equals(""))
			throw new Exception("A data de nascimento nao pode ser nula ou vazia.");
		
		Usuario usuario = new Usuario(nome,email, senha, dataNasc, imagem, telefone);
		if (sistemaPop.getUsuarios().contains(usuario))
			throw new Exception("Usuário ja esta cadastrado no +Pop.");
		
		sistemaPop.cadastraUsuario(usuario);
		return usuario;
		
	}
	
	
	public Usuario cadastraUsuario(String nome, String email, String senha, String dataNasc, String telefone) throws Exception {

		if(nome == null || nome.equals(""))
			throw new Exception("O nome nao pode ser nulo ou vazio.");
		if(email == null || email.equals(""))
			throw new Exception("O email nao pode ser nulo ou vazio.");
		if(senha == null || senha.equals("") || senha.length() < 3)
			throw new Exception("A senha nao pode ser nula, vazia ou menor que 3 caracteres.");
		if(dataNasc == null || dataNasc.equals(""))
			throw new Exception("A data de nascimento nao pode ser nula ou vazia.");
		
		Usuario usuario = new Usuario(nome,email, senha, dataNasc, telefone);
		if (sistemaPop.getUsuarios().contains(usuario))
			throw new Exception("Usuário ja esta cadastrado no +Pop.");
		
		sistemaPop.cadastraUsuario(usuario);
		return usuario;
		
	}
}
