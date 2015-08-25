package core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import treatmentsExceptions.PostExceptions;
import treatmentsExceptions.UsuarioExceptions;

public class Usuario {
	
	private String nome;
	private String email;
	private String senha;
	private String imagem;
	private Date dataNasc;
	private List<Post> posts;
	private List<Usuario> amigos;
	private Notificacoes notificacoes;
	
	public Usuario(String nome, String email, String senha, String dataNasc, String imagem) throws UsuarioExceptions, Exception {
		
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
	}

	public void validaDataCompleta(String dataNasc) throws UsuarioExceptions {
		if(dataNasc == null || dataNasc.equals("") || validaIntervalosDeData(dataNasc) == false)
			throw new UsuarioExceptions("Erro no cadastro de Usuarios. Data nao existe.");
	}

	public void validaDia(String dataNasc) throws UsuarioExceptions {
		if(validaDiaDaData(dataNasc) == true)
			throw new UsuarioExceptions("Erro no cadastro de Usuarios. Formato de data esta invalida.");
	}

	public void validaSenha(String senha) throws UsuarioExceptions {
		if(senha == null || senha.equals("") || senha.length() < 3)
			throw new UsuarioExceptions("A senha nao pode ser nula, vazia ou menor que 3 caracteres.");
	}

	public void validaEmailUsuario(String email) throws UsuarioExceptions {
		if(email == null || email.equals("") || validaEmail(email) == false)
			throw new UsuarioExceptions("Erro no cadastro de Usuarios. Formato de e-mail esta invalido.");
	}

	public void validaNome(String nome) throws UsuarioExceptions {
		if(nome == null || nome.equals("") || nome.trim().equals(""))
			throw new UsuarioExceptions("Erro no cadastro de Usuarios. Nome dx usuarix nao pode ser vazio.");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws UsuarioExceptions {
		if(nome == null || nome.equals(""))
			throw new UsuarioExceptions("Erro na atualizacao de perfil. Nome dx usuarix nao pode ser vazio.");
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws UsuarioExceptions {
		if(validaEmail(email) == false)
			throw new UsuarioExceptions("Erro na atualizacao de perfil. Formato de e-mail esta invalido.");
		if(email == null || email.equals(""))
			throw new UsuarioExceptions("Email nao pode ser nulo ou vazio.");
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

	public void setImagem(String imagem) throws UsuarioExceptions {
		if(nome == null || nome.equals(""))
			throw new UsuarioExceptions("Imagem nao pode ser nula ou vazia.");
		this.imagem = imagem;
	}

	public String getDataNasc() {
		DateFormat dataFormat = new SimpleDateFormat("yyy-MM-dd"); 
		return dataFormat.format(dataNasc);
	}

	public void setDataNasc(String novaDataNasc) throws UsuarioExceptions, Exception {
		if(validaDiaDaData(novaDataNasc) == true || validaIntervalosDeData(novaDataNasc) == false)
			throw new UsuarioExceptions("Erro na atualizacao de perfil. Formato de data esta invalida.");
		if( isDateValid(novaDataNasc) == false)
			throw new UsuarioExceptions("Erro na atualizacao de perfil. Data nao existe.");
		this.dataNasc = formataData(novaDataNasc);
	}

	public List<Post> getPosts() {
		return posts;
	}
	
	public List<Usuario> getAmigos() {
		return amigos;
	}

	public void postar(Post novoPost) throws PostExceptions {
		posts.add(novoPost);
	}
	
	public void interagirPost(int indexPost, String opcao) {
		if(opcao.equals(SystemPop.CURTIR))
			posts.get(indexPost).setPopularidade(SystemPop.CURTIR);
		if(opcao.equals(SystemPop.REJEITAR))
			posts.get(indexPost).setPopularidade(SystemPop.REJEITAR);
	}
	
	/** 
     * Converte uma String para um objeto Date. Caso a String seja vazia ou nula,  
     * retorna null.
     * @param data String no formato dd/MM/yyyy a ser formatada 
     * @return Date Objeto Date ou null caso receba uma String vazia ou nula 
	 * @throws ParseException 
     * @throws Exception Caso a String esteja no formato errado 
     */  
    private Date formataData(String data) throws UsuarioExceptions, ParseException {   
        if (data == null || data.equals(""))  
            return null;  
          
        Date date = null;  
        try {  
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            date = (java.util.Date)formatter.parse(data);  
        } catch (ParseException e) {              
            throw e;  
        }  
        return date;  
    } 
	
    /** 
     * Valida um email fornecido pelo usuario ao criar um novo usuario  
     * retorna boolean Ex: alguem@mail - retorna false..
     * @param email String 
     * @return boolean true para emails validos e false para emails invalidos 
     */
    public boolean validaEmail(String email) {
	    Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"); 
	    Matcher emailFiltrado = p.matcher(email); 
	    if (emailFiltrado.find()){
	      return true;
	    }
	    return false;  
	 }
    
    
    /** 
     * Valida um intervalo data fornecida pelo usuario ao criar um novo usuario  
     * retorna boolean Ex: 32/10/2010 - retorna false..
     * @param data String 
     * @return boolean true para datas validas e false para datas invalidas 
     */
    public boolean validaIntervalosDeData(String data) {
		String[] valores = data.split("/");
		
		if(!data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") || Integer.parseInt(valores[0]) < 1 || Integer.parseInt(valores[0]) > 31)
			return false;
		if(!data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") || Integer.parseInt(valores[1]) < 1 || Integer.parseInt(valores[1]) > 12)
			return false;
		if(!data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") || Integer.parseInt(valores[2]) < 1)
			return false;
    	return true;
	}
    
    /** 
     * Valida o dia da data fornecida pelo usuario ao criar um novo usuario  
     * retorna boolean Ex: 1510/10/2010 - retorna false.
     * @param data String 
     * @return boolean true para dias validos e false para dias invalidos 
     */
    public boolean validaDiaDaData(String data) {
    	String[] dia = data.split("/");
    	if(dia[0].length() > 2)
			return true;
    	return false;
    }
    
    public boolean isDateValid(String strDate) {
    	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	formatter.setLenient(false);
    	try {
    	    Date date= formatter.parse(strDate);
    	    return true;
    	} catch (ParseException e) {
    	    return false;
    	}
    }
    
	@Override
	public String toString() {
		return "Nome: " + this.nome + SystemPop.QUEBRA_DE_LINHA
				+ "Email:  " + this.email + SystemPop.QUEBRA_DE_LINHA
				+ "Senha:  *******" +  SystemPop.QUEBRA_DE_LINHA 
				+ "Data de Nascimento: " + getDataNasc() + SystemPop.QUEBRA_DE_LINHA
				+ "Posts: " + this.posts;
	}

	public int getNotificacoes() {
		return this.notificacoes.getNotificacoes();
	}

	public String getNextNotificacao() throws Exception {
		return this.notificacoes.getNextNotificacao();
	}

	public void rejeitaAmizade(String usuario) {
		
	}

	public void adicionaAmigo(String usuario) {
		
	}
}
