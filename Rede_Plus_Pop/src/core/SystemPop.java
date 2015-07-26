package core;

import java.util.ArrayList;
import java.util.List;

public class SystemPop {

	private List<Usuario> usuarios;
	
	public SystemPop() {
		usuarios = new ArrayList<Usuario>();
	}
	
	public void cadastraUsuario(Usuario novoUsuario) throws Exception {
		if (getUsuarios().contains(novoUsuario))
			throw new Exception("Usuário ja esta cadastrado no +Pop.");
		usuarios.add(novoUsuario);
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
}
