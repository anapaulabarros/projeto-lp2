package core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import treatmentsExceptions.AtualizaPerfilException;
import treatmentsExceptions.PostException;
import treatmentsExceptions.PostNaoEncontradoException;

public class Usuario implements Comparable<Usuario>, Serializable {

	private final long serialVersionUID = 42L;
	private static final int VALOR_500 = 500;
	private static final int VALOR_1000 = 1000;
	private String nome;
	private String email;
	private String senha;
	private String imagem;
	private Date dataNasc;
	private List<Post> posts;
	private List<String> emailsSolicitacoes;
	private List<Usuario> amigos;
	private Notificacoes notificacoes;
	private TipoPopularidade popularidade;
	private int pops;
	private int magica;
	private Feed feed;
	
	/**
	 *  Construtor da classe Usuario, com seus atributos.
	 *  
	 * @param nome
	 * @param email
	 * @param senha
	 * @param dataNasc
	 * @param imagem
	 * @throws ParseException 
	 * @throws Exception
	 */
	public Usuario(String nome, String email, String senha, String dataNasc,
			String imagem) throws ParseException {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNasc = UtilUsuario.formataData(dataNasc);
		this.imagem = imagem;
		posts = new ArrayList<Post>();
		this.amigos = new ArrayList<Usuario>();
		this.notificacoes = new Notificacoes();
		this.emailsSolicitacoes = new ArrayList<String>();
		this.popularidade = new Normal();
		this.pops = 0;
		this.magica = 0;
		this.feed = new Feed();
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws AtualizaPerfilException {
		if (nome == null || nome.equals(""))
			throw new AtualizaPerfilException(
					"Erro na atualizacao de perfil. Nome dx usuarix nao pode ser vazio.");
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws AtualizaPerfilException {
		if (UtilUsuario.validaEmail(email) == false)
			throw new AtualizaPerfilException(
					"Erro na atualizacao de perfil. Formato de e-mail esta invalido.");
		if (email == null || email.equals(""))
			throw new AtualizaPerfilException("Email nao pode ser nulo ou vazio.");
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) throws AtualizaPerfilException {
		if (nome == null || nome.equals(""))
			throw new AtualizaPerfilException("Imagem nao pode ser nula ou vazia.");
		this.imagem = imagem;
	}

	public String getDataNasc() {
		DateFormat dataFormat = new SimpleDateFormat("yyy-MM-dd");
		return dataFormat.format(dataNasc);
	}

	public void setDataNasc(String novaDataNasc) throws AtualizaPerfilException, ParseException{
		if (UtilUsuario.validaDiaDaData(novaDataNasc) == true
				|| UtilUsuario.validaIntervalosDeData(novaDataNasc) == false)
			throw new AtualizaPerfilException(
					"Erro na atualizacao de perfil. Formato de data esta invalida.");
		if (UtilUsuario.isDateValid(novaDataNasc) == false)
			throw new AtualizaPerfilException(
					"Erro na atualizacao de perfil. Data nao existe.");
		this.dataNasc = UtilUsuario.formataData(novaDataNasc);
	}

	public List<Post> getPosts() {
		return posts;
	}

	public Post getPostEspecifico(int indexPost) throws PostNaoEncontradoException {
		if (indexPost >= posts.size()) {
			throw new PostNaoEncontradoException();
		}
		return posts.get(indexPost);
	}

	public List<Usuario> getAmigos() {
		return amigos;
	}

	public TipoPopularidade getTipoPopularidade() {
		return this.popularidade;
	}

	public String getStringPopularidade() {
		return popularidade.toString();
	}

	public int getPops() {
		return pops;
	}

	public void setPops(int pops) {
		this.pops = pops;
	}

	public void setMagica(int pops) {
		this.magica = pops;
		atualizaPops();
	}

	public void postar(Post novoPost) throws PostException {
		posts.add(novoPost);
	}

	public void atualizaNivel() {
		if (pops <= VALOR_500) {
			this.popularidade = new Normal();
		} else if (pops > VALOR_500 && pops <= VALOR_1000) {
			this.popularidade = new CelebridadePop();
		} else {
			this.popularidade = new IconePop();
		}
	}

	public void interagirPost(int indexPost, String opcao,
			TipoPopularidade tipoPopularidade) {
		Post post = posts.get(indexPost);
		if (opcao.equals(SystemPop.CURTIR)) {
			tipoPopularidade.curtir(post);
			post.setCurtidas();
		}
		if (opcao.equals(SystemPop.REJEITAR)) {
			tipoPopularidade.rejeitar(post);
			post.setRejeitadas();
		}
		atualizaPops();
		atualizaNivel();
	}

	public void atualizaPops() {
		int pops = this.magica;
		for (Post post : posts) {
			pops = pops + post.getPopularidade();
		}
		setPops(pops);
		atualizaNivel();
	}

	
	public String dadosUsuarioString() {
		return "Nome: " + this.nome + SystemPop.QUEBRA_DE_LINHA + "Email:  "
				+ this.email + SystemPop.QUEBRA_DE_LINHA + "Senha:  *******"
				+ SystemPop.QUEBRA_DE_LINHA + "Data de Nascimento: "
				+ getDataNasc() + SystemPop.QUEBRA_DE_LINHA + "Posts: "
				+ this.posts; 
	}
	
	@Override
	public String toString() {
		return "Nome: " + this.nome + SystemPop.QUEBRA_DE_LINHA + "Email:  "
				+ this.email + SystemPop.QUEBRA_DE_LINHA + "Senha: " + this.senha
				+ SystemPop.QUEBRA_DE_LINHA + "Data de Nascimento: "
				+ getDataNasc();
	}

	public void adicionaNotificacao(String novaNotificacao) {
		notificacoes.addNotificacoes(novaNotificacao);
	}

	public void adicionaEmailNotificacao(String emailNotificacao) {
		emailsSolicitacoes.add(emailNotificacao);
	}

	public List<String> getEmailsNotificacao() {
		return emailsSolicitacoes;
	}

	public int getNotificacoes() {
		return this.notificacoes.getNotificacoes();
	}

	public String getNextNotificacao() throws Exception {
		return this.notificacoes.getNextNotificacao();
	}

	public void rejeitaAmizade() {
		notificacoes.removeNotificacao(); // apenas remove a notificacao de
											// solicitacao
	}

	public void aceitaAmizade(Usuario novoAmigo) {
		amigos.add(novoAmigo);
	}

	public void removeAmizade(Usuario exAmigo) {
		amigos.remove(exAmigo);
	}

	public int getQtdAmigos() {
		return amigos.size();
	}

	/**
	 * Pesquisa se um usuario possui amizade com o usuario logado retorna
	 * Usuario caso exista a amizade e null para falso.
	 * 
	 * @param String - emailAmgi 
	 * 
	 * @return Usuario - retorna um usuario caso encontre senao retorna null
	 */
	public Usuario buscaAmigo(String emailAmigo) {
		for (Usuario usuario : amigos) {
			if (usuario.getEmail().equals(emailAmigo))
				return usuario;
		}
		return null;
	}

	@Override
	public int compareTo(Usuario outroUsuario) {
		if (this.pops == outroUsuario.pops)
			return 0;
		else
			return this.pops > outroUsuario.pops ? -1 : 1;
	}

	public List<Post> getPostsRecentes(int quantidade) {
		List<Post> listaPost = new ArrayList<Post>();
		int tamanho = this.posts.size();
		if (tamanho <= quantidade) {
			listaPost = this.posts;
		} else {
			for (int i = tamanho; i > tamanho - quantidade; i++) {
				listaPost.add(this.posts.get(i));
			}
		}
		return listaPost;
	}
	
	/**
	 * Metodo para adicionar post no feed de noticias do usuario logado no 
	 * sistema
	 * 
	 * @param void
	 * @return void
	 */
	public void adicionaPostsNoFeed(){
		List<Post> postsAdicionar = new ArrayList<Post>();
		for (Usuario amigo : amigos) {
			postsAdicionar = amigo.getPostsRecentes(amigo.getQtdPost());
			for (Post post : postsAdicionar) {
				this.feed.adicionaPost(post);
			}
		}
	}

	public int getQtdPost(){
		return popularidade.qtdPosts();
	}
	
	public void atualizaFeed() {
		adicionaPostsNoFeed();
		this.feed.atualiza();
	}

	public void atualizaFeedPopularidade() {
		adicionaPostsNoFeed();
		this.feed.atualizaPorPopularidade();
	}
	

}
