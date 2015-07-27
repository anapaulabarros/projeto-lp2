package core;

import java.util.ArrayList;
import java.util.List;

public class SystemPop {

	private List<Usuario> usuarios;
	private Usuario usuarioLogado;
	
	public SystemPop() {
		usuarios = new ArrayList<Usuario>();
		usuarioLogado = null;
	}
	
	public void cadastraUsuario(Usuario novoUsuario) throws Exception {
		if (getUsuarios().contains(novoUsuario))
			throw new Exception("Usu�rio ja esta cadastrado no +Pop.");
		usuarios.add(novoUsuario);
	}

	public boolean login(String email, String senha) throws Exception {
		Usuario usuario = buscaUsuario(email);
		if(usuario == null)
			throw new Exception("Nao foi possivel realizar login. Um usuario com email: " + email + " nao existe.");
		else{
			if(usuario.getSenha().equals(senha)){
				if (usuarioLogado != null){
					usuarioLogado = usuario;
					return true;
				}
				else {
					throw new Exception("Nao foi possivel realizar login. "
							+ "Um usuario ja esta logado: " + usuario.getNome() + " login email="+ usuario.getEmail() + " senha= " + usuario.getSenha());
				}	
			}
			else{
				throw new Exception("Senha do usuario esta incorreta");
			}		
		}
	}
	
	
	public Usuario buscaUsuario(String email) {
		for (Usuario usuario : usuarios) {
			if (usuario.getEmail().equals(email)) {
				return usuario;
			}
		}
		return null;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
}
