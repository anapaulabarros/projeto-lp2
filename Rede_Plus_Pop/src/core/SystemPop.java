package core;

import java.util.ArrayList;
import java.util.List;

public class SystemPop {

	private List<Usuario> usuarios;
	
	public SystemPop() {
		usuarios = new ArrayList<Usuario>();
	}
	
	public void cadastraUsuario(Usuario novoUsuario) {
		usuarios.add(novoUsuario);
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
}
