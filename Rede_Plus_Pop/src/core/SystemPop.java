package core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemPop {
	
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	
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
			throw new Exception("Nao foi possivel realizar login. Um usuarix com email " + email + " nao esta cadastradx.");
		else{
			if(usuario.getSenha().equals(senha)){
				if (usuarioLogado == null){
					usuarioLogado = usuario;
					return true;
				}
				else {
					throw new Exception("Nao foi possivel realizar login. Um usuarix ja esta logadx: " + usuarioLogado.getNome());
				}	
			}
			else{
				throw new Exception("Nao foi possivel realizar login. Senha invalida.");
			}		
		}
	}
	
	
	public boolean logout() throws Exception {
		if(usuarioLogado == null)
			throw new Exception("Nao eh possivel realizar logout. Nenhum usuarix esta logadx no +pop.");
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
	
	public void postar(String mensagem) throws Exception {
		if(getUsuarioLogado() == null)
			throw new Exception("Nao eh possivel postar no mural, pois nao ha nenhum usuario logado.");
		if(mensagem == null || mensagem.length() == 0)
			throw new Exception("A mensagem nao pode ser nula ou vazia.");
		if(mensagem.length() > 400)
			throw new Exception("A mensagem nao pode ter mais de 400 caracteres.");
		
		Post novoPost = new Post(mensagem, new Date(System.currentTimeMillis()));
		getUsuarioLogado().postar(novoPost);
	}
	
	
	public void interagirComPost(int idPost, String emailAmigo, String opcao) throws Exception {
		Usuario amigoDoUsuarioLogado = buscaUsuario(emailAmigo);
		if(usuarioLogado == null)
			throw new Exception("Nao eh possivel realizar interacao com o post, nao ha nenhum usuario logado.");
		if(usuarioLogado.getEmail().equals(emailAmigo))
			throw new Exception("Nao eh possivel realizar interacao com o post, voce precisa escolher um amigo para interagir com os posts.");
		if(amigoDoUsuarioLogado == null)
			throw new Exception("Nao existe nenhum usuario com o email fornecido.");
		if(amigoDoUsuarioLogado.getPosts().size() == 0 || idPost > amigoDoUsuarioLogado.getPosts().size())
			throw new Exception("Nao existe nenhum post no mural com esse indice.");
		
		if(opcao == CURTIR)
			amigoDoUsuarioLogado.interagirPost(idPost, CURTIR);
		if(opcao == REJEITAR)
			amigoDoUsuarioLogado.interagirPost(idPost, REJEITAR);
	}

	public void iniciaSistema() {
		//iniciar sistema		
	}

	public void fechaSistema() throws Exception {
		//fechar sistema
		if (usuarioLogado == null){
			
		}else{
			throw new Exception("Nao foi possivel fechar o sistema. Um usuarix ainda esta logadx.");
		}
	}

	public String getInfoUsuario(String atributo) {
		if (atributo.equals("Nome")){
			return usuarioLogado.getNome();
		} else if (atributo.equals("Data de Nascimento")){
			return usuarioLogado.getDataNasc();
		} else if (atributo.equals("Foto")){
			return usuarioLogado.getImagem();
		} else if (atributo.equals("Senha")){
			return usuarioLogado.getSenha();
		}
		return null;
	}

	public void atualizaPerfil(String atributo, String valor) throws Exception {
		//falta os outros atributos
		if (usuarioLogado == null){
			throw new Exception("Nao eh possivel atualizar um perfil. Nenhum usuarix esta logadx no +pop.");
		}else{
		if (atributo.equals("Nome")){
			usuarioLogado.setNome(valor);
		}
		}
	}
}
