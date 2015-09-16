package core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import treatmentsExceptions.AtualizaPerfilException;
import treatmentsExceptions.ErroCadastroException;
import treatmentsExceptions.PostException;
import treatmentsExceptions.PostNaoEncontradoException;

public class Usuario implements Comparable<Usuario>, Serializable {

	private final long serialVersionUID = 42L;
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
	 * @throws Exception
	 */
	public Usuario(String nome, String email, String senha, String dataNasc,
			String imagem) throws Exception {

		validaNome(nome);
		validaEmailUsuario(email);
		validaSenha(senha);
		validaDia(dataNasc);
		validaDataCompleta(dataNasc);

		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNasc = formataData(dataNasc);
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

	public void validaDataCompleta(String dataNasc) throws ErroCadastroException {
		if (dataNasc == null || dataNasc.equals("")
				|| validaIntervalosDeData(dataNasc) == false)
			throw new ErroCadastroException(
					"Erro no cadastro de Usuarios. Data nao existe.");
	}

	public void validaDia(String dataNasc) throws ErroCadastroException {
		if (validaDiaDaData(dataNasc) == true)
			throw new ErroCadastroException(
					"Erro no cadastro de Usuarios. Formato de data esta invalida.");
	}

	public void validaSenha(String senha) throws ErroCadastroException {
		if (senha == null || senha.equals("") || senha.length() < 3)
			throw new ErroCadastroException(
					"A senha nao pode ser nula, vazia ou menor que 3 caracteres.");
	}

	public void validaEmailUsuario(String email) throws ErroCadastroException {
		if (email == null || email.equals("") || validaEmail(email) == false)
			throw new ErroCadastroException(
					"Erro no cadastro de Usuarios. Formato de e-mail esta invalido.");
	}

	public void validaNome(String nome) throws ErroCadastroException {
		if (nome == null || nome.equals("") || nome.trim().equals(""))
			throw new ErroCadastroException(
					"Erro no cadastro de Usuarios. Nome dx usuarix nao pode ser vazio.");
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
		if (validaEmail(email) == false)
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
		if (validaDiaDaData(novaDataNasc) == true
				|| validaIntervalosDeData(novaDataNasc) == false)
			throw new AtualizaPerfilException(
					"Erro na atualizacao de perfil. Formato de data esta invalida.");
		if (isDateValid(novaDataNasc) == false)
			throw new AtualizaPerfilException(
					"Erro na atualizacao de perfil. Data nao existe.");
		this.dataNasc = formataData(novaDataNasc);
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
		if (pops <= 500) {
			this.popularidade = new Normal();
		} else if (pops > 500 && pops <= 1000) {
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

	/**
	 * Converte uma String para um objeto Date. Caso a String seja vazia ou
	 * nula, retorna null.
	 * 
	 * @param data
	 *            String no formato dd/MM/yyyy a ser formatada
	 * @return Date Objeto Date ou null caso receba uma String vazia ou nula
	 * @throws ParseException
	 * @throws Exception
	 *             Caso a String esteja no formato errado
	 */
	private Date formataData(String data) throws ParseException {
		if (data == null || data.equals(""))
			return null;

		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (java.util.Date) formatter.parse(data);
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}

	/**
	 * Valida um email fornecido pelo usuario ao criar um novo usuario retorna
	 * boolean Ex: alguem@mail - retorna false..
	 * 
	 * @param String - email
	 *         
	 * @return boolean true para emails validos e false para emails invalidos
	 */
	public boolean validaEmail(String email) {
		Pattern p = Pattern
				.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher emailFiltrado = p.matcher(email);
		if (emailFiltrado.find()) {
			return true;
		}
		return false;
	}

	/**
	 * Valida um intervalo data fornecida pelo usuario ao criar um novo usuario
	 * retorna boolean Ex: 32/10/2010 - retorna false..
	 * 
	 * @param String - data
	 *            
	 * @return boolean true - para datas validas e false para datas invalidas
	 */
	public boolean validaIntervalosDeData(String data) {
		String[] valores = data.split("/");

		if (!data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")
				|| Integer.parseInt(valores[0]) < 1
				|| Integer.parseInt(valores[0]) > 31)
			return false;
		if (!data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")
				|| Integer.parseInt(valores[1]) < 1
				|| Integer.parseInt(valores[1]) > 12)
			return false;
		if (!data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")
				|| Integer.parseInt(valores[2]) < 1)
			return false;
		return true;
	}

	/**
	 * Valida o dia da data fornecida pelo usuario ao criar um novo usuario
	 * retorna boolean Ex: 1510/10/2010 - retorna false.
	 * 
	 * @param String - data
	 *            
	 * @return boolean - true para dias validos e false para dias invalidos
	 */
	public boolean validaDiaDaData(String data) {
		String[] dia = data.split("/");
		if (dia[0].length() > 2)
			return true;
		return false;
	}

	public boolean isDateValid(String strDate) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		formatter.setLenient(false);
		try {
			Date date = formatter.parse(strDate);
			return true;
		} catch (ParseException e) {
			return false;
		}
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
			if (amigo.getStringPopularidade().equals("Normal")) {
				postsAdicionar = amigo.getPostsRecentes(2);
			} else if (amigo.getStringPopularidade().equals("CelebridadePop")) {
				postsAdicionar = amigo.getPostsRecentes(4);
			} else if (amigo.getStringPopularidade().equals("IconePop")) {
				postsAdicionar = amigo.getPostsRecentes(6);
			}
			for (Post post : postsAdicionar) {
				this.feed.adicionaPost(post);
			}
		}
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
