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
			throw new Exception("Usuário ja esta cadastrado no +Pop.");
		usuarios.add(novoUsuario);
	}

	public boolean login(String email, String senha) throws Exception {
		Usuario usuario = buscaUsuario(email);
		if(usuario == null)
			throw new Exception("Nao foi possivel realizar login. Um usuario com email: " + email + " nao existe.");
		else{
			if(usuario.getSenha().equals(senha)){
				if (usuarioLogado == null){
					usuarioLogado = usuario;
					return true;
				}
				else {
					throw new Exception("Nao foi possivel realizar login. Um usuario ja esta logado.");
				}	
			}
			else{
				throw new Exception("Senha do usuario esta incorreta");
			}		
		}
	}
	
	
	public boolean logout() throws Exception {
		if(usuarioLogado == null)
			throw new Exception("Nao eh possivel realizar logout. Nenhum usuario esta logado no +pop.");
		usuarioLogado = null;
		return true;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
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
