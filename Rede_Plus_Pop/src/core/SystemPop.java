package core;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import treatmentsExceptions.PostExceptions;
import treatmentsExceptions.SystemPopExceptions;
import treatmentsExceptions.UsuarioExceptions;

public class SystemPop {
	
	private static final String HASHTAGS = "Hashtags";
	private static final String CONTEUDO = "Conteudo";
	private static final String DATA = "Data";
	private static final String FOTO = "Foto";
	private static final String E_MAIL = "E-mail";
	private static final String SENHA = "Senha";
	private static final String DATA_DE_NASCIMENTO = "Data de Nascimento";
	private static final String NOME = "Nome";
	public static final String QUEBRA_DE_LINHA = System.getProperty("line.separator");
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
		if (atributo.equals(NOME)){
			retorno = usuarioLogado.getNome();
		} else if (atributo.equals(DATA_DE_NASCIMENTO)){
			retorno = usuarioLogado.getDataNasc();
		} else if (atributo.equals(FOTO)){
			retorno = usuarioLogado.getImagem();
		} else if (atributo.equals(SENHA)){
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
		if (atributo.equals(NOME)){
			retorno = usuario.getNome();
		} else if (atributo.equals(DATA_DE_NASCIMENTO)){
			retorno = usuario.getDataNasc();
		} else if (atributo.equals(FOTO)){
			retorno = usuario.getImagem();
		} else if (atributo.equals(SENHA)){
			throw new SystemPopExceptions("A senha dx usuarix eh protegida.");
		}
		return retorno;
	}

	public void atualizaPerfil(String atributo, String valor) throws UsuarioExceptions, Exception {
		if (usuarioLogado == null){
			throw new SystemPopExceptions("Nao eh possivel atualizar um perfil. Nenhum usuarix esta logadx no +pop.");
		}
		if (atributo.equals(NOME)){
			usuarioLogado.setNome(valor);
		}
		if (atributo.equals(E_MAIL)){
			usuarioLogado.setEmail(valor);
		}
		if (atributo.equals(SENHA)){
			usuarioLogado.setSenha(valor);
		}
		if (atributo.equals(DATA_DE_NASCIMENTO)){
			usuarioLogado.setDataNasc(valor);
		}
		if (atributo.equals(FOTO)){
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
		if (atributo.equals(CONTEUDO)){
			return postAtual.getMensagem();
		} else if(atributo.equals(DATA)){
			return postAtual.getDataPostFormatada();
		} else if (atributo.equals(HASHTAGS)){
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
		//if(!novoPost.filtraHashtags(mensagem).isEmpty())
		//	dicionarioHashtags = dicionarioDeHashtags(novoPost.filtraHashtags(mensagem), novoPost.filtraMensagem(mensagem), mensagem);
		usuarioLogado.postar(novoPost);
	}
	
	
	/*
	 * Método para armazenar um dicionario de hastags e suas mensagens associadas
	 * Ex: [#frio={faz muito frio hoje}]
     * @param mensgem String 
     * @return Map<String, ArrayList<String>>  
	 * */
	public Map<String, ArrayList<String>> dicionarioDeHashtags(String listaDeHastags, String textoFiltrado, String mensagem) {
		Map<String, ArrayList<String>> hastags = new HashMap<String, ArrayList<String>>();
		List<String> listaHastags = new ArrayList<String>();
		for(String hashtag: listaDeHastags.split(" ")){
			listaHastags.add(hashtag);
		}
		for(int i = 0; i < listaHastags.size(); i++) {
			 if(!hastags.keySet().contains(listaHastags.get(i)))
				 hastags.put(listaHastags.get(i), new ArrayList<String>());
			 hastags.get(listaHastags.get(i)).add(textoFiltrado);
		 }
		
		return hastags;
	}
	
	public void adicionaAmigo(String usuario) throws SystemPopExceptions {
		if (buscaUsuario(usuario) == null)
			throw new SystemPopExceptions("O usuario " + usuario + " nao esta cadastrado no +pop.");
		if (usuarioLogado.buscaAmigo(usuario) == true)
			throw new SystemPopExceptions("O usuario " + usuario + " ja eh seu amigo.");
		
		Usuario candidatoAmigo = buscaUsuario(usuario);
		candidatoAmigo.adicionaNotificacao(usuarioLogado.getNome() + " quer sua amizade."); // Envia notificacao para o Futuro Amigo
		
		usuarioLogado.adicionaAmigo(usuario);
	}

	public int getNotificacoes() {
		return usuarioLogado.getNotificacoes();
	}

	public String getNextNotificacao() throws Exception {
		return usuarioLogado.getNextNotificacao();
	}

	public void rejeitaAmizade(String usuario) {
		usuarioLogado.rejeitaAmizade(usuario);
	}
}
