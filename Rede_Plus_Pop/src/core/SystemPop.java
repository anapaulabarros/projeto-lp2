package core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import treatmentsExceptions.PostExceptions;
import treatmentsExceptions.SystemPopExceptions;
import treatmentsExceptions.UsuarioExceptions;

public class SystemPop {
	
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	
	private List<Usuario> usuarios;
	private Usuario usuarioLogado;
	private Map<String, ArrayList<String>> dicionarioHashtags;
	
	public SystemPop() {
		usuarios = new ArrayList<Usuario>();
		usuarioLogado = null;
		dicionarioHashtags = new HashMap<String, ArrayList<String>>();
	}
	
	public String cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws UsuarioExceptions, Exception {
		Usuario novoUsuario = new Usuario(nome, email, senha, dataNasc, imagem);
		if (getUsuarios().contains(novoUsuario))
			throw new SystemPopExceptions("Usuario ja esta cadastrado no +Pop.");
		usuarios.add(novoUsuario);
		return email;
	}

	public boolean login(String email, String senha) throws SystemPopExceptions {
		Usuario usuario = buscaUsuario(email);
		if(usuario == null)
			throw new SystemPopExceptions("Nao foi possivel realizar login. Um usuarix com email " + email + " nao esta cadastradx.");
		else{
			if(usuario.getSenha().equals(senha)){
				if (usuarioLogado == null){
					usuarioLogado = usuario;
					return true;
				}
				else {
					throw new SystemPopExceptions("Nao foi possivel realizar login. Um usuarix ja esta logadx: " + usuarioLogado.getNome() + ".");
				}	
			}
			else{
				throw new SystemPopExceptions("Nao foi possivel realizar login. Senha invalida.");
			}		
		}
	}
	
	public void removeUsuario(String emailDoUsuario) throws SystemPopExceptions {
		Usuario usuarioParaRemover = buscaUsuario(emailDoUsuario);
		if(usuarioParaRemover == null)
			throw new SystemPopExceptions("Erro ao remover usuario. Um usuarix com email " + emailDoUsuario + " nao esta cadastradx.");
		usuarios.remove(usuarioParaRemover);
	}
	
	public boolean logout() throws SystemPopExceptions {
		if(usuarioLogado == null)
			throw new SystemPopExceptions("Nao eh possivel realizar logout. Nenhum usuarix esta logadx no +pop.");
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
	
	
	public void curtir(int indexPost, String email) throws SystemPopExceptions {
		interagirComPost(indexPost, email, CURTIR);
	}
	
	public void rejeitar(int indexPost, String email) throws SystemPopExceptions {
		interagirComPost(indexPost, email, REJEITAR);
	}
	
	public void interagirComPost(int idPost, String emailAmigo, String opcao) throws SystemPopExceptions {
		Usuario amigoDoUsuarioLogado = buscaUsuario(emailAmigo);
		if(usuarioLogado == null)
			throw new SystemPopExceptions("Nao eh possivel realizar interacao com o post, nao ha nenhum usuario logado.");
		if(usuarioLogado.getEmail().equals(emailAmigo))
			throw new SystemPopExceptions("Nao eh possivel realizar interacao com o post, voce precisa escolher um amigo para interagir com os posts.");
		if(amigoDoUsuarioLogado == null)
			throw new SystemPopExceptions("Nao existe nenhum usuario com o email fornecido.");
		if(amigoDoUsuarioLogado.getPosts().size() == 0 || idPost > amigoDoUsuarioLogado.getPosts().size())
			throw new SystemPopExceptions("Nao existe nenhum post no mural com esse indice.");
		
		if(opcao == CURTIR)
			amigoDoUsuarioLogado.interagirPost(idPost, CURTIR);
		if(opcao == REJEITAR)
			amigoDoUsuarioLogado.interagirPost(idPost, REJEITAR);
	}

	public void iniciaSistema() {
		//iniciar sistema		
	}

	public void fechaSistema() throws SystemPopExceptions {
		if (usuarioLogado == null){
			//fechar sistema
		}else{
			throw new SystemPopExceptions("Nao foi possivel fechar o sistema. Um usuarix ainda esta logadx.");
		}
	}

	public String getInfoUsuario(String atributo) throws SystemPopExceptions {
		String retorno = "";
		if (atributo.equals("Nome")){
			retorno = usuarioLogado.getNome();
		} else if (atributo.equals("Data de Nascimento")){
			retorno = usuarioLogado.getDataNasc();
		} else if (atributo.equals("Foto")){
			retorno = usuarioLogado.getImagem();
		} else if (atributo.equals("Senha")){
			throw new SystemPopExceptions("A senha dx usuarix eh protegida.");
		}
		return retorno;
	}
	
	public String getInfoUsuario(String atributo, String email) throws Exception {
		Usuario usuario = buscaUsuario(email);
		String retorno = "";
		if (usuario == null){
			throw new Exception("Um usuarix com email " + email + " nao esta cadastradx.");
		}
		if (atributo.equals("Nome")){
			retorno = usuario.getNome();
		} else if (atributo.equals("Data de Nascimento")){
			retorno = usuario.getDataNasc();
		} else if (atributo.equals("Foto")){
			retorno = usuario.getImagem();
		} else if (atributo.equals("Senha")){
			throw new SystemPopExceptions("A senha dx usuarix eh protegida.");
		}
		return retorno;
	}

	public void atualizaPerfil(String atributo, String valor) throws UsuarioExceptions, Exception {
		if (usuarioLogado == null){
			throw new SystemPopExceptions("Nao eh possivel atualizar um perfil. Nenhum usuarix esta logadx no +pop.");
		}
		if (atributo.equals("Nome")){
			usuarioLogado.setNome(valor);
		}
		if (atributo.equals("E-mail")){
			usuarioLogado.setEmail(valor);
		}
		if (atributo.equals("Senha")){
			usuarioLogado.setSenha(valor);
		}
		if (atributo.equals("Data de Nascimento")){
			usuarioLogado.setDataNasc(valor);
		}
		if (atributo.equals("Foto")){
			usuarioLogado.setImagem(valor);
		}
	}

	public void atualizaPerfil(String atributo, String valor, String velhaSenha) throws SystemPopExceptions, UsuarioExceptions {
		if (velhaSenha.equals(usuarioLogado.getSenha())){
			usuarioLogado.setSenha(valor);
		}else{
			throw new SystemPopExceptions("Erro na atualizacao de perfil. A senha fornecida esta incorreta.");
		}
	}
	
	public String getPost(int post) {
		return usuarioLogado.getPosts().get(post).toString();
	}
	
	public String getPost(String atributo, int post) {
		Post postAtual = usuarioLogado.getPosts().get(post);
		if (atributo.equals("Conteudo")){
			return postAtual.getMensagem();
		} else if(atributo.equals("Data")){
			return postAtual.getDataPublicacao();
		} else if (atributo.equals("Hashtags")){
			return postAtual.getListaHashtag();
		}
		return null;
	}

	public String getConteudoPost(int indice, int post) throws SystemPopExceptions {
		Post postAtual = usuarioLogado.getPosts().get(post);
		if (indice < 0){
			throw new SystemPopExceptions("Requisicao invalida. O indice deve ser maior ou igual a zero.");
		}
		if (indice >= postAtual.getQuantidadeItens()){
			throw new SystemPopExceptions("Item #" + indice + " nao existe nesse post, ele possui apenas 3 itens distintos.");
		}
		return postAtual.getConteudo(indice);
	}

	public Map<String, ArrayList<String>> getDicionarioHashtags() {
		return dicionarioHashtags;
	}

	public void criaPost(String mensagem, String data) throws SystemPopExceptions,PostExceptions, ParseException {
		if(getUsuarioLogado() == null){
			throw new SystemPopExceptions("Nao eh possivel postar no mural, pois nao ha nenhum usuarix logadx.");		
		}
		Post novoPost = new Post(mensagem, data);
		if(!novoPost.filtraHashtags(mensagem).isEmpty())
			dicionarioHashtags = dicionarioDeHashtags(novoPost.filtraHashtags(mensagem), novoPost.filtraMensagem(mensagem), mensagem);
		usuarioLogado.postar(novoPost);
	}
	
	
	/*
	 * Método para armazenar um dicionario de hastags e suas mensagens associadas
	 * Ex: [#frio={faz muito frio hoje}]
     * @param mensgem String 
     * @return Map<String, ArrayList<String>>  
	 * */
	public Map<String, ArrayList<String>> dicionarioDeHashtags(List<String> listaDeHastags, String textoFiltrado, String mensagem) {
		Map<String, ArrayList<String>> hastags = new HashMap<String, ArrayList<String>>();
		
		for(int i = 0; i < listaDeHastags.size(); i++) {
			 if(!hastags.keySet().contains(listaDeHastags.get(i)))
				 hastags.put(listaDeHastags.get(i), new ArrayList<String>());
			 hastags.get(listaDeHastags.get(i)).add(textoFiltrado);
		 }
		
		return hastags;
	}
}
