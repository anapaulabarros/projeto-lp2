package core;

public class Controller {
	
	private SystemPop sistemaPop;
	
	
	public Controller() {
		sistemaPop = new SystemPop();
	}
	
	
	public void cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws Exception {
		
		if(imagem == null || imagem.equals(""))
			sistemaPop.cadastraUsuario(new Usuario(nome, email, senha, dataNasc));
		else
			sistemaPop.cadastraUsuario(new Usuario(nome, email, senha, dataNasc, imagem));
			
	}
	
	public void login(String email, String senha) throws Exception {
		sistemaPop.login(email, senha);
	}
	
	public void logout() throws Exception  {
		sistemaPop.logout();
	}
	
	public void alteraNome(String nomeNovo) throws Exception{
		sistemaPop.alteraNome(nomeNovo);
	}
	
	public void alteraEmail(String emailNovo) throws Exception{
		sistemaPop.alteraEmail(emailNovo);
	}
	
	public void alteraDataNasc(String dataNova) throws Exception{
		sistemaPop.alteraDataNasc(dataNova);
	}
	
	public void alteraImagem(String imagemNova) throws Exception{
		sistemaPop.alteraImagem(imagemNova);
	}
	
	public void alteraSenha(String senhaAntiga, String senhaNova){
		sistemaPop.alteraSenha(senhaAntiga, senhaNova);
	}
}
