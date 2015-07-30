package core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemPop {
	
	public static final String CURTIR = "Curtir";
	public static final String REJETIAR = "Rejeitar";
	
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

	public void alteraNome(String nomeNovo) throws Exception {
		usuarioLogado.setNome(nomeNovo);
	}

	public void alteraEmail(String emailNovo) throws Exception {
		usuarioLogado.setEmail(emailNovo);
	}

	public void alteraDataNasc(String dataNova) throws Exception {
		usuarioLogado.setDataNasc(dataNova);
	}

	public void alteraImagem(String imagemNova) throws Exception {
		usuarioLogado.setImagem(imagemNova);
	}

	public void alteraSenha(String senhaAntiga, String senhaNova) {
		if (senhaAntiga.equals(usuarioLogado.getSenha())){
			usuarioLogado.setSenha(senhaNova);
		}
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
		if(opcao == REJETIAR)
			amigoDoUsuarioLogado.interagirPost(idPost, REJETIAR);
	}
}
