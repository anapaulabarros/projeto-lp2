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

import midias.ConteudoMidia;
import treatmentsExceptions.AtualizaPerfilException;
import treatmentsExceptions.EntradaException;
import treatmentsExceptions.LogicaException;
import treatmentsExceptions.PostException;
import treatmentsExceptions.RealizaLoginException;

public class SystemPop {

	private static final String HASHTAGS = "Hashtags";
	private static final String DATA = "Data";
	private static final String FOTO = "Foto";
	private static final String E_MAIL = "E-mail";
	private static final String SENHA = "Senha";
	private static final String DATA_DE_NASCIMENTO = "Data de Nascimento";
	private static final String NOME = "Nome";
	public static final String QUEBRA_DE_LINHA = System
			.getProperty("line.separator");
	public static final String CURTIR = "Curtir";
	public static final String REJEITAR = "Rejeitar";
	public static final String ICONE_POP = "IconePop";
	public static final String CELEBRIDADE = "CelebridadePop";
	public static final String NORMAL = "Normal";
	private static final String MENSAGEM = "Mensagem";

	private List<Usuario> usuarios;
	private Usuario usuarioLogado;
	private Map<String, ArrayList<String>> dicionarioHashtags;
	private TrendingTopics trends;

	/**
	 * construtor da classe SystemPop, ela eh o controller do projeto entao cabe
	 * a ela toda gerencia e administracao de dados em que o sistema manipula ao
	 * longo do seu funcionamento
	 * 
	 * @author Ana Paula Barros e Gabriela Motta - UFCG : Computacao
	 */
	public SystemPop() {
		usuarios = new ArrayList<Usuario>();
		usuarioLogado = null;
		dicionarioHashtags = new HashMap<String, ArrayList<String>>();
		trends = new TrendingTopics();
	}

	/**
	 * Metodo para cadastrar um novo usuario no +Pop
	 * 
	 * @param String
	 *            - nome
	 * @param String
	 *            - email
	 * @param String
	 *            - senha
	 * @param String
	 *            - dataNasc
	 * @param String
	 *            - imagem
	 * @return String - email
	 * @throws ParseException
	 * @throws EntradaException
	 */
	public String cadastraUsuario(String nome, String email, String senha,
			String dataNasc, String imagem) throws ParseException,
			EntradaException {
		UtilUsuario.validaNome(nome);
		UtilUsuario.validaEmailUsuario(email);
		UtilUsuario.validaSenha(senha);
		UtilUsuario.validaDia(dataNasc);
		UtilUsuario.validaDataCompleta(dataNasc);

		Usuario novoUsuario = new Usuario(nome, email, senha, dataNasc, imagem);
		if (getUsuarios().contains(novoUsuario))
			throw new EntradaException("Usuario ja esta cadastrado no +Pop.");
		usuarios.add(novoUsuario);
		return email;
	}

	/**
	 * Metodo para um usuario ja cadastrado, realizar login na rede social
	 * 
	 * @param email
	 * @param senha
	 * @return boolean - true para sucesso e excecao para o nao sucesso ao logar
	 * @throws Exception
	 */
	public boolean login(String email, String senha)
			throws RealizaLoginException {
		Usuario usuario = buscaUsuario(email);
		if (usuario == null)
			throw new RealizaLoginException("Um usuarix com email " + email
					+ " nao esta cadastradx.");
		else {
			if (usuario.getSenha().equals(senha)) {
				if (usuarioLogado == null) {
					usuarioLogado = usuario;
					return true;
				} else {
					throw new RealizaLoginException(
							"Um usuarix ja esta logadx: "
									+ usuarioLogado.getNome() + ".");
				}
			} else {
				throw new RealizaLoginException("Senha invalida.");
			}
		}
	}

	/**
	 * Metodo para remover um usuario do sistema, caso o usuario esteja cadastro
	 * a remocao eh realizada com sucesso senao lanca uma excecao com mensagem
	 * exibindo o motivo
	 * 
	 * @param emailDoUsuario
	 * @throws LogicaException
	 * @return void
	 */
	public void removeUsuario(String emailDoUsuario) throws LogicaException {
		Usuario usuarioParaRemover = buscaUsuario(emailDoUsuario);
		if (usuarioParaRemover == null)
			throw new LogicaException(
					"Erro ao remover usuario. Um usuarix com email "
							+ emailDoUsuario + " nao esta cadastradx.");
		usuarios.remove(usuarioParaRemover);
	}

	public boolean logout() throws LogicaException {
		if (usuarioLogado == null)
			throw new LogicaException(
					"Nao eh possivel realizar logout. Nenhum usuarix esta logadx no +pop.");
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
	 * Metodo para interagri com o post, onde o usuario pode curtir ou rejeitar
	 * um post de um amigo. Caso o usuario tente interagir com um post nao
	 * existente ou que nao seja amigo do dono do post entao uma excecao sera
	 * lancada.
	 * 
	 * @param int - idPost
	 * @param String
	 *            - emailAmigo
	 * @param String
	 *            - opcao - CURTIR ou REJEITAR
	 * @throws LogicaException
	 * @return void
	 */
	public void interagirComPost(int idPost, String emailAmigo, String opcao)
			throws LogicaException {
		Usuario amigoDoUsuarioLogado = buscaUsuario(emailAmigo);
		String palavraInteracao = "";
		List<String> hashtagEpic = new ArrayList<String>();

		validaInteracaoPost(idPost, emailAmigo, amigoDoUsuarioLogado);

		palavraInteracao = verificaPalavraDeInteracaoPost(idPost, opcao,
				amigoDoUsuarioLogado, palavraInteracao);

		// envia notificacao para o usuario que enviou o post, se curtiu ou
		// rejeitou o post
		notificacaoDeInteracaoComPost(palavraInteracao, amigoDoUsuarioLogado, idPost);
		
		addHastagEpicWin(idPost, amigoDoUsuarioLogado, hashtagEpic);
			
		addHastagsEpicFail(idPost, amigoDoUsuarioLogado, hashtagEpic);
		
		this.hastagsMaisPop(hashtagEpic);
	}

	//metodo para verificar qual a palavra de interacao com o Post de um amigo
	//capturar o tipo de popularidade
	private String verificaPalavraDeInteracaoPost(int idPost, String opcao,
			Usuario amigoDoUsuarioLogado, String palavraInteracao) {
		if (opcao == CURTIR) {
			amigoDoUsuarioLogado.interagirPost(idPost, CURTIR,
					usuarioLogado.getTipoPopularidade());
			palavraInteracao = "curtiu";
		}
		if (opcao == REJEITAR) {
			amigoDoUsuarioLogado.interagirPost(idPost, REJEITAR,
					usuarioLogado.getTipoPopularidade());
			palavraInteracao = "rejeitou";
		}
		return palavraInteracao;
	}

	//metodo para verifica e adicionar o EpicFail no Post do amigo
	private void addHastagsEpicFail(int idPost, Usuario amigoDoUsuarioLogado,
			List<String> hashtagEpic) throws LogicaException {
		if(!amigoDoUsuarioLogado.getPostEspecifico(idPost).getAdicionouEpicFail()){
			for (String epic: amigoDoUsuarioLogado.getPostEspecifico(idPost).containsEpicFail()){
				hashtagEpic.add(epic);
			}
		}
	}
	
	//metodo para verificar e adicionar o EpicWin no Post do amigo
	private void addHastagEpicWin(int idPost, Usuario amigoDoUsuarioLogado,
			List<String> hashtagEpic) throws LogicaException {
		if(!amigoDoUsuarioLogado.getPostEspecifico(idPost).getAdicionouEpicWin()){
			for (String epic: amigoDoUsuarioLogado.getPostEspecifico(idPost).containsEpicWin()){
				hashtagEpic.add(epic);
			}
		}
	}
	
	//metodo para adicionar uma notificacao na lista de notificacoes
	//do amigo do Usuario logado ao receber uma interacao com um post especifico
	private void notificacaoDeInteracaoComPost(String palavraInteracao, Usuario amigoDoUsuarioLogado, int idPost) throws LogicaException {
		amigoDoUsuarioLogado.adicionaNotificacao(usuarioLogado.getNome()
				+ " "
				+ palavraInteracao
				+ " seu post de "
				+ amigoDoUsuarioLogado.getPostEspecifico(idPost)
						.getDataPostFormatada() + ".");
	}

	// metodo para validar a interacao do usuario logado com um post de um amigo
	private void validaInteracaoPost(int idPost, String emailAmigo,
			Usuario amigoDoUsuarioLogado) throws LogicaException {
		if (usuarioLogado == null)
			throw new LogicaException(
					"Nao eh possivel realizar interacao com o post, nao ha nenhum usuario logado.");
		if (usuarioLogado.getEmail().equals(emailAmigo))
			throw new LogicaException(
					"Nao eh possivel realizar interacao com o post, voce precisa escolher um amigo para interagir com os posts.");
		if (amigoDoUsuarioLogado == null)
			throw new LogicaException(
					"Nao existe nenhum usuario com o email fornecido.");
		if (amigoDoUsuarioLogado.getPosts().size() == 0
				|| idPost > amigoDoUsuarioLogado.getPosts().size())
			throw new LogicaException(
					"Nao existe nenhum post no mural com esse indice.");
	}

	public void iniciaSistema() {
		// iniciar sistema
	}

	public void fechaSistema() throws LogicaException {
		if (usuarioLogado == null) {
			// fechar sistema
		} else {
			throw new LogicaException(
					"Nao foi possivel fechar o sistema. Um usuarix ainda esta logadx.");
		}
	}

	/**
	 * Metodo para obter uma informacao especifica do usuario logado. Ex: obter
	 * nome ou data de nascimento
	 * 
	 * @param atributo
	 * @return String - resultado
	 * @throws LogicaException
	 */
	public String getInfoUsuario(String atributo) throws LogicaException {
		String retorno = "";
		if (atributo.equals(NOME)) {
			retorno = usuarioLogado.getNome();
		} else if (atributo.equals(DATA_DE_NASCIMENTO)) {
			retorno = usuarioLogado.getDataNasc();
		} else if (atributo.equals(FOTO)) {
			retorno = usuarioLogado.getImagem();
		} else if (atributo.equals(SENHA)) {
			throw new LogicaException("A senha dx usuarix eh protegida.");
		}
		return retorno;
	}

	/**
	 * Metodo para obter uma informacao especifica de um usuario cadastrado no
	 * sistema. Ex: obter nome ou email
	 * 
	 * @param String
	 *            - atributo
	 * @param String
	 *            - email
	 * @return String - resultado
	 * @throws LogicaException
	 */
	public String getInfoUsuario(String atributo, String email)
			throws LogicaException {
		Usuario usuario = buscaUsuario(email);
		String retorno = "";
		if (usuario == null) {
			throw new LogicaException("Um usuarix com email " + email
					+ " nao esta cadastradx.");
		}
		if (atributo.equals(NOME)) {
			retorno = usuario.getNome();
		} else if (atributo.equals(DATA_DE_NASCIMENTO)) {
			retorno = usuario.getDataNasc();
		} else if (atributo.equals(FOTO)) {
			retorno = usuario.getImagem();
		} else if (atributo.equals(SENHA)) {
			throw new LogicaException("A senha dx usuarix eh protegida.");
		}
		return retorno;
	}

	/**
	 * Metodo para atualizar uma determinada informacao de perfil do usuario
	 * cadastrado e logado no sistema. Ex.: Atualizar senha ou email
	 * 
	 * @param atributo
	 * @param valor
	 * @throws Exception
	 * @throws ParseException 
	 * @return void
	 */
	public void atualizaPerfil(String atributo, String valor) throws AtualizaPerfilException, ParseException {
		if (usuarioLogado == null) {
			throw new AtualizaPerfilException(
					"Nenhum usuarix esta logadx no +pop.");
		}
		if (atributo.equals(NOME)) {
			usuarioLogado.setNome(valor);
		}
		if (atributo.equals(E_MAIL)) {
			usuarioLogado.setEmail(valor);
		}
		if (atributo.equals(SENHA)) {
			usuarioLogado.setSenha(valor);
		}
		if (atributo.equals(DATA_DE_NASCIMENTO)) {
			usuarioLogado.setDataNasc(valor);
		}
		if (atributo.equals(FOTO)) {
			usuarioLogado.setImagem(valor);
		}
	}

	public void atualizaPerfil(String atributo, String valor, String velhaSenha)
			throws EntradaException {
		if (velhaSenha.equals(usuarioLogado.getSenha())) {
			usuarioLogado.setSenha(valor);
		} else {
			throw new AtualizaPerfilException(
					"A senha fornecida esta incorreta.");
		}
	}

	public String getPost(int post) {
		return usuarioLogado.getPosts().get(post).getPostString();
	}

	/**
	 * Metodo para obter uma determinada informacao de um post especifico do
	 * usuario logado no sistema.
	 * 
	 * @param String
	 *            - atributo
	 * @param int - post
	 * @return String - resultado
	 */
	public String getPost(String atributo, int post) {
		Post postAtual = usuarioLogado.getPosts().get(post);
		if (atributo.equals(DATA)) {
			return postAtual.getDataPostFormatada();
		} else if (atributo.equals(HASHTAGS)) {
			return postAtual.getListaHashtag();
		} else if (atributo.equals(MENSAGEM)) {
			return postAtual.getMensagemSemHashtags();
		}
		return null;
	}

	public String getConteudoPost(int indice, int post) throws LogicaException {
		Post postAtual = usuarioLogado.getPosts().get(post);
		int qtdItens = postAtual.getQuantidadeItens();
		if (indice < 0) {
			throw new LogicaException(
					"Requisicao invalida. O indice deve ser maior ou igual a zero.");
		}
		if (indice >= qtdItens) {
			throw new LogicaException("Item #" + indice
					+ " nao existe nesse post, ele possui apenas " + qtdItens
					+ " itens distintos.");
		}
		return postAtual.getConteudo(indice);
	}

	public Map<String, ArrayList<String>> getDicionarioHashtags() {
		return dicionarioHashtags;
	}

	/**
	 * Metodo para um usuario criar um novo post, o post eh adicionado na sua
	 * lista de posts.
	 * 
	 * @param String
	 *            - mensagem
	 * @param String
	 *            - data
	 * @return void
	 * @throws LogicaException
	 * @throws PostException
	 */
	public void criaPost(String mensagem, String data) throws LogicaException,
			PostException {
		if (getUsuarioLogado() == null) {
			throw new LogicaException(
					"Nao eh possivel postar no mural, pois nao ha nenhum usuarix logadx.");
		}

		UtilPost.verificaConteudoDaMensagem(mensagem);
		UtilPost.verificaTamanhoDaMensagem(UtilPost.filtraTexto(mensagem));

		String texto = UtilPost.filtraTexto(mensagem);
		List<ConteudoMidia> conteudo = UtilPost.criaMidias(mensagem);
		List<String> hashtags = new ArrayList<String>();

		if (mensagem.contains("#")) {
			UtilPost.verificaValidadeDasHastags(mensagem);
			hashtags = UtilPost.filtraHashtags(mensagem);
			hastagsMaisPop(hashtags);
		}

		usuarioLogado.postar(mensagem, texto, conteudo, hashtags, data); // Quem
																			// cria
																			// o
																			// post
																			// eh
																			// o
																			// usuario
	}

	public void hastagsMaisPop(List<String> listaDeHastags) {
		for (String hashtag : listaDeHastags) {
			Marcador novo = new Marcador(hashtag);
			if (!this.trends.getLista().contains(novo)) {
				this.trends.adicionaMarcador(novo);
			} else {
				for (Marcador marcador : this.trends.getLista()) {
					if (novo.equals(marcador)) {
						marcador.aumentaOcorrencia();
					}
				}
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
	 * @param String
	 *            - emailFuturoAmigo
	 * @throws LogicaException
	 * @return void
	 */
	public void adicionaAmigo(String emailFuturoAmigo) throws LogicaException {
		if (buscaUsuario(emailFuturoAmigo) == null)
			throw new LogicaException("O usuario " + emailFuturoAmigo
					+ " nao esta cadastrado no +pop.");
		if (usuarioLogado.buscaAmigo(emailFuturoAmigo) != null)
			throw new LogicaException("O usuario " + emailFuturoAmigo
					+ " ja possui uma amizade com voce.");

		Usuario candidatoAmigo = buscaUsuario(emailFuturoAmigo);
		candidatoAmigo.adicionaNotificacao(usuarioLogado.getNome()
				+ " quer sua amizade."); // Envia notificacao para o Futuro
											// Amigo
		candidatoAmigo.adicionaEmailNotificacao(usuarioLogado.getEmail()); // sempre
																			// que
																			// houver
																			// uma
																			// notificacao,
																			// armazene
																			// o
																			// email
																			// do
																			// usuario
																			// quem
																			// enviou
																			// a
																			// notificacao

	}

	/**
	 * Metodo responsavel gerenciar a criacao de amizade entre os dois usuarios.
	 * 
	 * @param emailNovoAmigo
	 * @throws LogicaException
	 * @return void
	 */
	public void aceitaAmizade(String emailNovoAmigo) throws LogicaException {
		if (buscaUsuario(emailNovoAmigo) == null)
			throw new LogicaException("O usuario " + emailNovoAmigo
					+ " nao esta cadastrado no +pop.");

		Usuario novoAmigo = buscaUsuario(emailNovoAmigo);
		criaAmizade(novoAmigo);

	}

	/**
	 * Metodo responsavel por criar a relacao de amizade entre dois usuarios e
	 * enviar uma notificacao para o novoAmigo
	 * 
	 * @param Usuario
	 *            novoAmigo
	 * @return void
	 */
	private void criaAmizade(Usuario novoAmigo) {
		usuarioLogado.aceitaAmizade(novoAmigo);
		novoAmigo.aceitaAmizade(usuarioLogado);

		novoAmigo.adicionaNotificacao(usuarioLogado.getNome()
				+ " aceitou sua amizade."); // envia notificacao para o usuario
											// que mandou o convite
	}

	/**
	 * Metodo responsavel por enviar uma notificacao de rejeicao de amizade,
	 * quando o usuario que recebeu o convite decide rejeitar a solicitacao de
	 * amizade.
	 * 
	 * @param emailRejeitado
	 * @throws LogicaException
	 * @return void
	 */
	public void rejeitaAmizade(String emailRejeitado) throws LogicaException {
		if (buscaUsuario(emailRejeitado) == null)
			throw new LogicaException("Um usuarix com email " + emailRejeitado
					+ " nao esta cadastradx.");

		Usuario candidatoAmigo = buscaUsuario(emailRejeitado);
		if (!usuarioLogado.getEmailsNotificacao().contains(emailRejeitado)) // verifica
																			// se
																			// usuario
																			// logado
																			// tem
																			// alguma
																			// notificacao
			throw new LogicaException(candidatoAmigo.getNome()
					+ " nao lhe enviou solicitacoes de amizade.");

		candidatoAmigo.adicionaNotificacao(usuarioLogado.getNome()
				+ " rejeitou sua amizade."); // envia notificacao para o usuario
												// que mandou o convite
	}

	/**
	 * Metodo que realiza a remocao da amizade entre dois usuarios, apos a
	 * remocao eh enviada uma notificacao para o usuario que foi excluido da
	 * lista de amigos do usuario logado.
	 * 
	 * @param emailExAmigo
	 * @throws LogicaException
	 * @return void
	 */
	public void removeAmigo(String emailExAmigo) throws LogicaException {
		if (buscaUsuario(emailExAmigo) == null)
			throw new LogicaException("O usuario " + emailExAmigo
					+ " nao esta cadastrado no +pop.");
		if (usuarioLogado.buscaAmigo(emailExAmigo) == null)
			throw new LogicaException("O usuario " + emailExAmigo
					+ " nao possui uma amizade com voce.");

		// remove amizade
		Usuario exAmigo = buscaUsuario(emailExAmigo);
		usuarioLogado.removeAmizade(exAmigo);
		exAmigo.removeAmizade(usuarioLogado);

		exAmigo.adicionaNotificacao(usuarioLogado.getNome()
				+ " removeu a sua amizade."); // envia uma notificacao
	}

	public int getQtdAmigos() {
		return usuarioLogado.getQtdAmigos();
	}

	public void adicionaPops(int pops) {
		usuarioLogado.setMagica(pops);
	}

	public String getTipoPopularidade() {
		return usuarioLogado.getPopularidade();
	}

	/**
	 * Metodo para obter a informacao sobre a popularidade que o usuario
	 * cadastrado no sistema possui. ex: Normal ou IconePop
	 * 
	 * @param String
	 *            - email
	 * @return String - tipoPopularidade
	 */
	public String getTipoPopularidade(String email) {
		Usuario usuario = buscaUsuario(email);
		return usuario.getPopularidade();
	}

	public int getPopsPost(int post) throws Exception {
		return usuarioLogado.getPostEspecifico(post).getPopularidade();
	}

	public int getPopsPost(int post, String email) throws Exception {
		Usuario usuario = buscaUsuario(email);
		return usuario.getPostEspecifico(post).getPopularidade();
	}

	/**
	 * Metodo que retorna a quantidade de curtidas que um post especifico do
	 * usuario logado recebeu.
	 * 
	 * @param int - post
	 * @return int - qtdCurtidas
	 * @throws Exception
	 */
	public int qtdCurtidasDePost(int post) throws LogicaException {
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

	public int getPopsUsuario(String email) throws LogicaException {
		if (usuarioLogado != null)
			throw new LogicaException(
					"Erro na consulta de Pops. Um usuarix ainda esta logadx.");
		Usuario usuario = buscaUsuario(email);
		return usuario.getPops();
	}

	/**
	 * Metodo que recupera os nomes dos 3 usuarios mais populares da rede social
	 * 
	 * @param void
	 * @return String - tresPessasMaisPopulares
	 */
	public String getRankingMais() {
		if (usuarios.size() != 0) {
			Collections.sort(usuarios);
			Usuario primeiro = usuarios.get(usuarios.size() - 1);
			Usuario segundo = usuarios.get(usuarios.size() - 2);
			Usuario terceiro = usuarios.get(usuarios.size() - 3);

			return "(1) " + primeiro.getNome() + " " + primeiro.getPops()
					+ "; (2) " + segundo.getNome() + " " + segundo.getPops()
					+ "; (3) " + terceiro.getNome() + " " + terceiro.getPops()
					+ ";";
		}
		return "";
	}

	/**
	 * Metodo que recupera os nomes dos 3 usuarios menos populares da rede
	 * social
	 * 
	 * @param void
	 * @return String - tresPessasMenosPopulares
	 */
	public String getRankingMenos() {
		if (usuarios.size() != 0) {
			Collections.sort(usuarios);
			Usuario primeiro = usuarios.get(0);
			Usuario segundo = usuarios.get(1);
			Usuario terceiro = usuarios.get(2);

			return "(1) " + primeiro.getNome() + " " + primeiro.getPops()
					+ "; (2) " + segundo.getNome() + " " + segundo.getPops()
					+ "; (3) " + terceiro.getNome() + " " + terceiro.getPops()
					+ ";";
		}
		return "";
	}

	/**
	 * Metodo responsavel por exibir os tres usuarios mais e menos populares da
	 * rede social.
	 * 
	 * @param void
	 * @return String - mais populares e os menos populares
	 */
	public String getRanking() {
		return "Mais Populares: " + getRankingMais() + " | "
				+ "Menos Populares: " + getRankingMenos();
	}

	public String getTrendingTopics() {
		return this.trends.getRanking();
	}

	/**
	 * Metodo para atualizar o feed de noticias do usuario logado
	 * 
	 * @return void
	 */
	public void atualizaFeed() {
		usuarioLogado.atualizaFeed();
	}

	public void atualizaFeedPopularidade() {
		usuarioLogado.atualizaFeedPopularidade();
	}

	/**
	 * Metodo responsavel por salvar em arquivo o historico de todos os posts de
	 * todos os usuarios no sistema.
	 * 
	 * @return void
	 * @throws IOException
	 */
	public void salvaHistoricoPosts() throws IOException {
		List<String> postString = new ArrayList<String>();

		for (int i = 0; i < usuarios.size(); i++) {
			Usuario usuarioAtual = usuarios.get(i);
			postString.add(usuarioAtual.getNome());
			for (int j = 0; j < usuarioAtual.getPosts().size(); j++) {
				Post postAtual = usuarioAtual.getPosts().get(j);
				postString.add("Post #" + (j + 1) + " " + postAtual.toString());
			}
			postString.add("--------------------------");
		}

		FileOutputStream meuArquivo = new FileOutputStream(
				"./arquivos/historicoDePosts.txt");
		ObjectOutputStream oos = new ObjectOutputStream(meuArquivo);
		oos.writeObject(postString);
		oos.flush();
		oos.close();
		meuArquivo.flush();
		meuArquivo.close();

	}

	/**
	 * Metodo responsavel por salvar em arquivo os dados dos perfis de todos os
	 * usuarios cadastrados no sitema.
	 * 
	 * @return void
	 * @throws IOException
	 */
	public void salvaDadosUsuarios() throws IOException {
		List<String> dadosString = new ArrayList<String>();

		for (int i = 0; i < usuarios.size(); i++) {
			dadosString.add("Usuario #" + i + SystemPop.QUEBRA_DE_LINHA);
			dadosString.add(usuarios.get(i).toString()
					+ SystemPop.QUEBRA_DE_LINHA + "--------------------------");
		}

		FileOutputStream meuArquivo = new FileOutputStream(
				"./arquivos/DadosUsuarios.txt");
		ObjectOutputStream oos = new ObjectOutputStream(meuArquivo);
		oos.writeObject(dadosString);
		oos.flush();
		oos.close();
		meuArquivo.flush();
		meuArquivo.close();
	}
}
