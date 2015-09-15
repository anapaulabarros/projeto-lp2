package core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
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
	public static final String ICONE_POP = "IconePop";
	public static final String CELEBRIDADE = "CelebridadePop";
	public static final String NORMAL = "Normal";
	
	
	private List<Usuario> usuarios;
	private Usuario usuarioLogado;
	private Map<String, ArrayList<String>> dicionarioHashtags;
	private Map<String, Integer> contadorDeHastags;
	private ObjectOutputStream oos;
	
	public SystemPop() {
		usuarios = new ArrayList<Usuario>();
		usuarioLogado = null;
		dicionarioHashtags = new HashMap<String, ArrayList<String>>();
		contadorDeHastags = new HashMap<String, Integer>();
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
	
	public void interagirComPost(int idPost, String emailAmigo, String opcao) throws SystemPopExceptions {
		Usuario amigoDoUsuarioLogado = buscaUsuario(emailAmigo);
		String palavraInteracao = "";
		
		validaInteracaoPost(idPost, emailAmigo, amigoDoUsuarioLogado);
		
		if(opcao == CURTIR) {
			amigoDoUsuarioLogado.interagirPost(idPost, CURTIR, usuarioLogado.getTipoPopularidade());
			palavraInteracao = "curtiu";
		}
		if(opcao == REJEITAR) {
			amigoDoUsuarioLogado.interagirPost(idPost, REJEITAR, usuarioLogado.getTipoPopularidade());
			palavraInteracao = "rejeitou";
		}
		
		//envia notificacao para o usuario que enviou o post, se curtiu ou rejeitou o post
		amigoDoUsuarioLogado.adicionaNotificacao(usuarioLogado.getNome() + " "
				+ palavraInteracao + " seu post de " + amigoDoUsuarioLogado.getPosts().get(idPost).getDataPostFormatada() + ".");
	}

	//metodo para validar a interacao do usuario logado com um post de um amigo
	private void validaInteracaoPost(int idPost, String emailAmigo,
			Usuario amigoDoUsuarioLogado) throws SystemPopExceptions {
		if(usuarioLogado == null)
			throw new SystemPopExceptions("Nao eh possivel realizar interacao com o post, nao ha nenhum usuario logado.");
		if(usuarioLogado.getEmail().equals(emailAmigo))
			throw new SystemPopExceptions("Nao eh possivel realizar interacao com o post, voce precisa escolher um amigo para interagir com os posts.");
		if(amigoDoUsuarioLogado == null)
			throw new SystemPopExceptions("Nao existe nenhum usuario com o email fornecido.");
		if(amigoDoUsuarioLogado.getPosts().size() == 0 || idPost > amigoDoUsuarioLogado.getPosts().size())
			throw new SystemPopExceptions("Nao existe nenhum post no mural com esse indice.");
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
		return usuarioLogado.getPosts().get(post).getPostString();
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
		usuarioLogado.postar(novoPost);
		
		if(mensagem.contains("#")){
			if(novoPost.filtraHashtags(mensagem) != null || novoPost.filtraHashtags(mensagem) != "")
				HastagsMaisPop(novoPost.filtraHashtags(mensagem));
				//dicionarioHashtags = dicionarioDeHashtags(novoPost.filtraHashtags(mensagem), novoPost.filtraMensagem(mensagem), mensagem);
		}
		
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
	
	/*
	 * Metodo para armazenar no Map, a quantidade de vezes que cada hastag apareceu 
	 * em um novo post criado
	 * Ex: [#frio=1, #calo=2]
	 * @param listaDeHastags String
	 * @return Map<String, Integer> 
	 */
	public void HastagsMaisPop(String listaDeHastags) {
		int valuesCopy;
		List<String> listaHastags = new ArrayList<String>();
		for(String hashtag: listaDeHastags.split(" ")){
			listaHastags.add(hashtag);
		}
		for(int i = 0; i < listaHastags.size(); i++) {
			 if(!contadorDeHastags.keySet().contains(listaHastags.get(i)))
				 contadorDeHastags.put(listaHastags.get(i), 1);
			 else 
			 {
			 	valuesCopy = contadorDeHastags.get(listaHastags.get(i));
			    contadorDeHastags.put(listaHastags.get(i), valuesCopy + 1);
			 }
		 }
	}
	public int getNotificacoes() {
		return usuarioLogado.getNotificacoes();
	}

	public String getNextNotificacao() throws Exception {
		return usuarioLogado.getNextNotificacao();
	}
	
	public void adicionaAmigo(String emailFuturoAmigo) throws SystemPopExceptions {
		if (buscaUsuario(emailFuturoAmigo) == null)
			throw new SystemPopExceptions("O usuario " + emailFuturoAmigo + " nao esta cadastrado no +pop.");
		if (usuarioLogado.buscaAmigo(emailFuturoAmigo) != null)
			throw new SystemPopExceptions("O usuario " + emailFuturoAmigo + " ja possui uma amizade com voce.");
		
		Usuario candidatoAmigo = buscaUsuario(emailFuturoAmigo);
		candidatoAmigo.adicionaNotificacao(usuarioLogado.getNome() + " quer sua amizade."); // Envia notificacao para o Futuro Amigo
		candidatoAmigo.adicionaEmailNotificacao(usuarioLogado.getEmail()); //sempre que houver uma notificacao, armazene o email do usuario quem enviou a notificacao
		
	}
	
	public void aceitaAmizade(String emailNovoAmigo) throws SystemPopExceptions{
		if (buscaUsuario(emailNovoAmigo) == null)
			throw new SystemPopExceptions("O usuario " + emailNovoAmigo + " nao esta cadastrado no +pop.");
		
		Usuario novoAmigo = buscaUsuario(emailNovoAmigo);
		usuarioLogado.aceitaAmizade(novoAmigo);
		novoAmigo.aceitaAmizade(usuarioLogado);
		
		novoAmigo.adicionaNotificacao(usuarioLogado.getNome() + " aceitou sua amizade."); // envia notificacao para o usuario que mandou o convite
		
	}

	public void rejeitaAmizade(String emailRejeitado) throws SystemPopExceptions {
		if (buscaUsuario(emailRejeitado) == null)
			throw new SystemPopExceptions("O usuario " + emailRejeitado + " nao esta cadastrado no +pop.");
		
		Usuario candidatoAmigo = buscaUsuario(emailRejeitado);
		if (!usuarioLogado.getEmailsNotificacao().contains(emailRejeitado)) // verifica se usuario logado tem alguma notificacao
			throw new SystemPopExceptions(candidatoAmigo.getNome() + " nao lhe enviou solicitacoes de amizade.");
		
		candidatoAmigo.adicionaNotificacao(usuarioLogado.getNome() + " rejeitou sua amizade."); // envia notificacao para o usuario que mandou o convite
	}
	
	public void removeAmigo(String emailExAmigo) throws SystemPopExceptions {
		if (buscaUsuario(emailExAmigo) == null)
			throw new SystemPopExceptions("O usuario " + emailExAmigo + " nao esta cadastrado no +pop.");
		if (usuarioLogado.buscaAmigo(emailExAmigo) == null)
			throw new SystemPopExceptions("O usuario " + emailExAmigo + " nao possui uma amizade com voce.");
		
		//remove amizade
		Usuario exAmigo = buscaUsuario(emailExAmigo);
		usuarioLogado.removeAmizade(exAmigo);
		exAmigo.removeAmizade(usuarioLogado);
		
		exAmigo.adicionaNotificacao(usuarioLogado.getNome() + " removeu a sua amizade."); //envia uma notificacao
	}

	
	public int getQtdAmigos() {
		return usuarioLogado.getQtdAmigos();
	}

	public void setPops(int pops) {
		usuarioLogado.setMagica(pops);
	}

	public String getTipoPopularidade() {
		return usuarioLogado.getStringPopularidade();
	}
	
	public String getTipoPopularidade(String email) {
		Usuario usuario = buscaUsuario(email);
		return usuario.getStringPopularidade();
	}

	public int getPopsPost(int post) throws Exception {
		return usuarioLogado.getPostEspecifico(post).getPopularidade();
	}

	public int getPopsPost(int post, String email) throws Exception {
		Usuario usuario = buscaUsuario(email);
		return usuario.getPostEspecifico(post).getPopularidade();
	}

	public int getCurtidaPorPost(int post) throws Exception {
		return usuarioLogado.getPostEspecifico(post).getCurtidas();
	}

	public int getCurtidaPorPost(int post, String email) throws Exception {
		Usuario usuario = buscaUsuario(email);
		return usuario.getPostEspecifico(post).getCurtidas();
	}

	public int getRejeicaoPorPost(int post) throws Exception {
		return usuarioLogado.getPostEspecifico(post).getRejeitadas();
	}
	
	public int getRejeicaoPorPost(int post, String email) throws Exception {
		Usuario usuario = buscaUsuario(email);
		return usuario.getPostEspecifico(post).getRejeitadas();
	}

	public int getPopsUsuario() {
		return usuarioLogado.getPops();
	}

	public int getPopsUsuario(String email) {
		Usuario usuario = buscaUsuario(email);
		return usuario.getPops();
	}
	
	/*
	 * Método que recupera os
	 * nomes dos 3 usuários mais 
	 * populares da rede social
	 * 
	 */
	public String getRankingMais() {
		if (usuarios.size() != 0) {
			Collections.sort(usuarios);
			return  "(1) " + usuarios.get(0).getNome() + 
					", (2) " + usuarios.get(1).getNome() +
					", (3) " + usuarios.get(2).getNome();
		}
		return "";
	}

	/*
	 * Método que recupera os
	 * nomes dos 3 usuários menos 
	 * populares da rede social
	 * 
	 */
	public String getRankingMenos() {
		if (usuarios.size() != 0) {
			Collections.sort(usuarios);
			return  "(1) " + usuarios.get(usuarios.size() - 1).getNome() + 
					", (2) " + usuarios.get(usuarios.size() - 2).getNome() +
					", (3) " + usuarios.get(usuarios.size() - 3).getNome();
		}
		return "";
	}
	
	public Map<String, Integer> getRankigHastagsMais() {
		return contadorDeHastags;
	}
	
	public void atualizaFeed() {
		usuarioLogado.atualizaFeed();
	}
	
	public void atualizaFeedPopularidade() {
		usuarioLogado.atualizaFeedPopularidade();
	}
	
	public void salvaHistoricoPosts() {
		List<String> postString = new ArrayList<String>();
		
		for(int i = 0; i < usuarios.size(); i++) {
			Usuario usuarioAtual = usuarios.get(i);
			postString.add(usuarioAtual.getNome());
			for(int j = 0; j < usuarioAtual.getPosts().size(); j++) {
				Post postAtual = usuarioLogado.getPosts().get(j);
				postString.add("Post #" + i + " " + postAtual.toString());
			}
			postString.add("--------------------------");
		}
		
		try {
			FileOutputStream  meuArquivo = new FileOutputStream("./arquivos/historicoDePosts.txt");
			oos = new ObjectOutputStream(meuArquivo);
			oos.writeObject(postString);
			oos.flush();
			oos.close();
			meuArquivo.flush();
			meuArquivo.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
