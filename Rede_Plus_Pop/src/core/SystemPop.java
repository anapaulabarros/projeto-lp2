package core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import treatmentsExceptions.AtualizaPerfilException;
import treatmentsExceptions.EntradaException;
import treatmentsExceptions.LogicaException;

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
	
	/**
	 * construtor da classe SystemPop, ela é o controller do projeto
	 * entao cabe a ela toda gerencia e administracao de dados em que
	 * o sistema manipula ao longo do seu funcionamento
	 * 
	 * @author Ana Paula Barros e Gabriela Mota - UFCG : Computacao
	 */
	public SystemPop() {
		usuarios = new ArrayList<Usuario>();
		usuarioLogado = null;
		dicionarioHashtags = new HashMap<String, ArrayList<String>>();
		contadorDeHastags = new HashMap<String, Integer>();
	}
	
	/**
	 * Metodo para cadastrar um novo usuario no +Pop
	 * 
	 * @param String - nome
	 * @param String - email
	 * @param String - senha
	 * @param String - dataNasc
	 * @param String - imagem
	 * @return String - email
	 * @throws Exception
	 */
	public String cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws Exception {
		Usuario novoUsuario = new Usuario(nome, email, senha, dataNasc, imagem);
		if (getUsuarios().contains(novoUsuario))
			throw new EntradaException("Usuario ja esta cadastrado no +Pop.");
		usuarios.add(novoUsuario);
		return email;
	}

	/**
	 * Metodo para um usuario ja cadastrado, realizar login na rede social
	 * @param email
	 * @param senha
	 * @return boolean - true para sucesso e excecao para o nao sucesso ao logar
	 * @throws Exception
	 */
	public boolean login(String email, String senha) throws Exception {
		Usuario usuario = buscaUsuario(email);
		if(usuario == null)
			throw new LogicaException("Nao foi possivel realizar login. Um usuarix com email " + email + " nao esta cadastradx.");
		else{
			if(usuario.getSenha().equals(senha)){
				if (usuarioLogado == null){
					usuarioLogado = usuario;
					return true;
				}
				else {
					throw new LogicaException("Nao foi possivel realizar login. Um usuarix ja esta logadx: " + usuarioLogado.getNome() + ".");
				}	
			}
			else{
				throw new EntradaException("Nao foi possivel realizar login. Senha invalida.");
			}		
		}
	}
	
	/**
	 * Metodo para remover um usuario do sistema, caso o usuario esteja 
	 * cadastro a remocao eh realizada com sucesso senao lanca uma excecao
	 * com mensagem exibindo o motivo
	 * @param emailDoUsuario
	 * @throws LogicaException
	 * @return void
	 */
	public void removeUsuario(String emailDoUsuario) throws LogicaException {
		Usuario usuarioParaRemover = buscaUsuario(emailDoUsuario);
		if(usuarioParaRemover == null)
			throw new LogicaException("Erro ao remover usuario. Um usuarix com email " + emailDoUsuario + " nao esta cadastradx.");
		usuarios.remove(usuarioParaRemover);
	}
	
	public boolean logout() throws LogicaException {
		if(usuarioLogado == null)
			throw new LogicaException("Nao eh possivel realizar logout. Nenhum usuarix esta logadx no +pop.");
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
	
	/**
	 * Metodo para interagri com o post, onde o usuario pode curtir
	 * ou rejeitar um post de um amigo. Caso o usuario tente interagir
	 * com um post nao existente ou que nao seja amigo do dono do post
	 * entao uma excecao sera lancada.
	 * 
	 * @param int - idPost
	 * @param String - emailAmigo
	 * @param String - opcao - CURTIR ou REJEITAR
	 * @throws LogicaException
	 * @return void
	 */
	public void interagirComPost(int idPost, String emailAmigo, String opcao) throws LogicaException {
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
			Usuario amigoDoUsuarioLogado) throws LogicaException {
		if(usuarioLogado == null)
			throw new LogicaException("Nao eh possivel realizar interacao com o post, nao ha nenhum usuario logado.");
		if(usuarioLogado.getEmail().equals(emailAmigo))
			throw new LogicaException("Nao eh possivel realizar interacao com o post, voce precisa escolher um amigo para interagir com os posts.");
		if(amigoDoUsuarioLogado == null)
			throw new LogicaException("Nao existe nenhum usuario com o email fornecido.");
		if(amigoDoUsuarioLogado.getPosts().size() == 0 || idPost > amigoDoUsuarioLogado.getPosts().size())
			throw new LogicaException("Nao existe nenhum post no mural com esse indice.");
	}

	public void iniciaSistema() {
		//iniciar sistema		
	}

	public void fechaSistema() throws LogicaException {
		if (usuarioLogado == null){
			//fechar sistema
		}else{
			throw new LogicaException("Nao foi possivel fechar o sistema. Um usuarix ainda esta logadx.");
		}
	}

	/**
	 * Metodo para obter uma informacao especifica do usuario logado.
	 * Ex: obter nome ou data de nascimento
	 * @param atributo
	 * @return String - resultado
	 * @throws LogicaException
	 */
	public String getInfoUsuario(String atributo) throws LogicaException {
		String retorno = "";
		if (atributo.equals(NOME)){
			retorno = usuarioLogado.getNome();
		} else if (atributo.equals(DATA_DE_NASCIMENTO)){
			retorno = usuarioLogado.getDataNasc();
		} else if (atributo.equals(FOTO)){
			retorno = usuarioLogado.getImagem();
		} else if (atributo.equals(SENHA)){
			throw new LogicaException("A senha dx usuarix eh protegida.");
		}
		return retorno;
	}
	
	/**
	 * Metodo para obter uma informacao especifica de um usuario cadastrado no 
	 * sistema.
	 * Ex: obter nome ou email
	 * @param String - atributo
	 * @param String - email
	 * @return String - resultado
	 * @throws LogicaException
	 */
	public String getInfoUsuario(String atributo, String email) throws LogicaException {
		Usuario usuario = buscaUsuario(email);
		String retorno = "";
		if (usuario == null){
			throw new LogicaException("Um usuarix com email " + email + " nao esta cadastradx.");
		}
		if (atributo.equals(NOME)){
			retorno = usuario.getNome();
		} else if (atributo.equals(DATA_DE_NASCIMENTO)){
			retorno = usuario.getDataNasc();
		} else if (atributo.equals(FOTO)){
			retorno = usuario.getImagem();
		} else if (atributo.equals(SENHA)){
			throw new LogicaException("A senha dx usuarix eh protegida.");
		}
		return retorno;
	}

	/**
	 * Metodo para atualizar uma determinada informacao de perfil do usuario
	 * cadastrado e logado no sistema.
	 * Ex.: Atualizar senha ou email
	 * @param atributo
	 * @param valor
	 * @throws Exception
	 * @return void
	 */
	public void atualizaPerfil(String atributo, String valor) throws Exception {
		if (usuarioLogado == null){
			throw new AtualizaPerfilException("Nao eh possivel atualizar um perfil. Nenhum usuarix esta logadx no +pop.");
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

	public void atualizaPerfil(String atributo, String valor, String velhaSenha) throws EntradaException{
		if (velhaSenha.equals(usuarioLogado.getSenha())){
			usuarioLogado.setSenha(valor);
		}else{
			throw new AtualizaPerfilException("Erro na atualizacao de perfil. A senha fornecida esta incorreta.");
		}
	}
	
	public String getPost(int post) {
		return usuarioLogado.getPosts().get(post).getPostString();
	}
	
	/**
	 * Metodo para obter uma determinada informacao de um post especifico
	 * do usuario logado no sistema.
	 * 
	 * @param String - atributo
	 * @param int - post
	 * @return String - resultado
	 */
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

	public String getConteudoPost(int indice, int post) throws LogicaException {
		Post postAtual = usuarioLogado.getPosts().get(post);
		if (indice < 0){
			throw new LogicaException("Requisicao invalida. O indice deve ser maior ou igual a zero.");
		}
		if (indice >= postAtual.getQuantidadeItens()){
			throw new LogicaException("Item #" + indice + " nao existe nesse post, ele possui apenas 3 itens distintos.");
		}
		return postAtual.getConteudo(indice);
	}
	
	public List<String> getConteudoMidiaAudioPost(int post){
		Post postAtual = usuarioLogado.getPosts().get(post);
		return postAtual.getMidiaAudio();
	}
	
	public List<String> getConteudoMidiaVideoPost(int post){
		Post postAtual = usuarioLogado.getPosts().get(post);
		return postAtual.getMidiaVideo();
	}

	public Map<String, ArrayList<String>> getDicionarioHashtags() {
		return dicionarioHashtags;
	}
	
	/**
	 * Metodo para um uusario criar um novo post, o post eh adicionado na
	 * sua lista de posts.
	 * @param String - mensagem
	 * @param String - data
	 * @throws Exception
	 * @return void
	 */
	public void criaPost(String mensagem, String data) throws Exception {
		if(getUsuarioLogado() == null){
			throw new LogicaException("Nao eh possivel postar no mural, pois nao ha nenhum usuarix logadx.");		
		}
		Post novoPost = new Post(mensagem, data);
		usuarioLogado.postar(novoPost);
		
		if(mensagem.contains("#")){
			if(UtilPost.filtraHashtags(mensagem) != null || UtilPost.filtraHashtags(mensagem) != "")
				HastagsMaisPop(UtilPost.filtraHashtags(mensagem));
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
	
	/**
	 * Metodo responsavel por enviar um convite para o futuro amigo,
	 * 
	 * @param String - emailFuturoAmigo
	 * @throws LogicaException
	 * @return void
	 */
	public void adicionaAmigo(String emailFuturoAmigo) throws LogicaException {
		if (buscaUsuario(emailFuturoAmigo) == null)
			throw new LogicaException("O usuario " + emailFuturoAmigo + " nao esta cadastrado no +pop.");
		if (usuarioLogado.buscaAmigo(emailFuturoAmigo) != null)
			throw new LogicaException("O usuario " + emailFuturoAmigo + " ja possui uma amizade com voce.");
		
		Usuario candidatoAmigo = buscaUsuario(emailFuturoAmigo);
		candidatoAmigo.adicionaNotificacao(usuarioLogado.getNome() + " quer sua amizade."); // Envia notificacao para o Futuro Amigo
		candidatoAmigo.adicionaEmailNotificacao(usuarioLogado.getEmail()); //sempre que houver uma notificacao, armazene o email do usuario quem enviou a notificacao
		
	}
	/**
	 * Metodo responsavel criar a amizade entre os dois usuarios, usando as listas de 
	 * amigos e as notificacoes para gerenciar a relacao de amizade que sera criada.
	 * @param emailNovoAmigo
	 * @throws LogicaException
	 * @return void
	 */
	public void aceitaAmizade(String emailNovoAmigo) throws LogicaException{
		if (buscaUsuario(emailNovoAmigo) == null)
			throw new LogicaException("O usuario " + emailNovoAmigo + " nao esta cadastrado no +pop.");
		
		Usuario novoAmigo = buscaUsuario(emailNovoAmigo);
		usuarioLogado.aceitaAmizade(novoAmigo);
		novoAmigo.aceitaAmizade(usuarioLogado);
		
		novoAmigo.adicionaNotificacao(usuarioLogado.getNome() + " aceitou sua amizade."); // envia notificacao para o usuario que mandou o convite
		
	}

	/**
	 * Metodo responsavel por enviar uma notificacao de rejeicao de amizade,
	 * quando o usuario que recebeu o convite decide rejeitar a solicitacao
	 * de amizade.
	 * 
	 * @param emailRejeitado
	 * @throws LogicaException
	 * @return void
	 */
	public void rejeitaAmizade(String emailRejeitado) throws LogicaException {
		if (buscaUsuario(emailRejeitado) == null)
			throw new LogicaException("O usuario " + emailRejeitado + " nao esta cadastrado no +pop.");
		
		Usuario candidatoAmigo = buscaUsuario(emailRejeitado);
		if (!usuarioLogado.getEmailsNotificacao().contains(emailRejeitado)) // verifica se usuario logado tem alguma notificacao
			throw new LogicaException(candidatoAmigo.getNome() + " nao lhe enviou solicitacoes de amizade.");
		
		candidatoAmigo.adicionaNotificacao(usuarioLogado.getNome() + " rejeitou sua amizade."); // envia notificacao para o usuario que mandou o convite
	}
	
	/**
	 * Metodo que realiza a remocao da amizade entre dois usuarios,
	 * apos a remocao eh enviada uma notificacao para o usuario que foi excluido da lista 
	 * de amigos do usuario logado.
	 * 
	 * @param emailExAmigo
	 * @throws LogicaException
	 * @return void
	 */
	public void removeAmigo(String emailExAmigo) throws LogicaException {
		if (buscaUsuario(emailExAmigo) == null)
			throw new LogicaException("O usuario " + emailExAmigo + " nao esta cadastrado no +pop.");
		if (usuarioLogado.buscaAmigo(emailExAmigo) == null)
			throw new LogicaException("O usuario " + emailExAmigo + " nao possui uma amizade com voce.");
		
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
	
	/**
	 * Metodo para obter a informacao sobre a popularidade que o 
	 * usuario cadastrado no sistema possui.
	 * ex: Normal ou IconePop
	 * @param String - email
	 * @return String - tipoPopularidade
	 */
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
	
	/**
	 * Metodo que retorna a quantidade de curtidas que um post
	 * especifico do usuario logado recebeu.
	 * 
	 * @param int - post
	 * @return int - qtdCurtidas
	 * @throws Exception
	 */
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
	
	/**
	 * Metodo que recupera os
	 * nomes dos 3 usuarios mais 
	 * populares da rede social
	 * 
	 * @param void
	 * @return String - tresPessasMaisPopulares
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

	/**
	 * Metodo que recupera os
	 * nomes dos 3 usuarios menos 
	 * populares da rede social
	 * 
	 * @param void
	 * @return String - tresPessasMenosPopulares
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
	
	/**
	 * Metodo para atualizar o feed de noticias
	 * do usuario logado
	 * @return void
	 */
	public void atualizaFeed() {
		usuarioLogado.atualizaFeed();
	}
	
	public void atualizaFeedPopularidade() {
		usuarioLogado.atualizaFeedPopularidade();
	}
	
	/**
	 * Metodo responsavel por salvar em arquivo
	 * o historico de todos os posts de todos os
	 * usuarios no sistema.
	 * 
	 * @return void
	 * @throws IOException
	 */
	public void salvaHistoricoPosts() throws IOException {
		List<String> postString = new ArrayList<String>();
		
		for(int i = 0; i < usuarios.size(); i++) {
			Usuario usuarioAtual = usuarios.get(i);
			postString.add(usuarioAtual.getNome());
			for(int j = 0; j < usuarioAtual.getPosts().size(); j++) {
				Post postAtual = usuarioAtual.getPosts().get(j);
				postString.add("Post #" + (j + 1) + " " + postAtual.toString());
			}
			postString.add("--------------------------");
		}
		
		FileOutputStream  meuArquivo = new FileOutputStream("./arquivos/historicoDePosts.txt");
		ObjectOutputStream oos = new ObjectOutputStream(meuArquivo);
		oos.writeObject(postString);
		oos.flush();
		oos.close();
		meuArquivo.flush();
		meuArquivo.close();
		
	}
	
	/**
	 * Metodo responsavel por salvar em arquivo os dados
	 * dos perfis de todos os usuarios cadastrados no sitema.
	 * 
	 * @return void
	 * @throws IOException
	 */
	public void salvaDadosUsuarios() throws IOException{
		List<String> dadosString = new ArrayList<String>();
		
		for(int i = 0; i < usuarios.size(); i++) {
			dadosString.add("Usuario #" + i + SystemPop.QUEBRA_DE_LINHA);
			dadosString.add(usuarios.get(i).toString() + SystemPop.QUEBRA_DE_LINHA + "--------------------------");
		}
		
		FileOutputStream  meuArquivo = new FileOutputStream("./arquivos/DadosUsuarios.txt");
		ObjectOutputStream oos = new ObjectOutputStream(meuArquivo);
		oos.writeObject(dadosString);
		oos.flush();
		oos.close();
		meuArquivo.flush();
		meuArquivo.close();
	}
}
