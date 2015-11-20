package core;

import java.io.IOException;
import java.util.List;

import treatmentsExceptions.LogicaException;
import easyaccept.EasyAccept;

public class Facade {

	private SystemPop sistemaPop;

	public Facade() {
		sistemaPop = new SystemPop();
	}

	public void iniciaSistema() {
		sistemaPop.iniciaSistema();
	}

	public void fechaSistema() throws Exception {
		sistemaPop.fechaSistema();
	}

	public void login(String email, String senha) throws Exception {
		sistemaPop.login(email, senha);
	}

	public void logout() throws Exception {
		sistemaPop.logout();
	}

	public String cadastraUsuario(String nome, String email, String senha,
			String dataNasc, String imagem) throws Exception {
		return sistemaPop.cadastraUsuario(nome, email, senha, dataNasc, imagem);
	}

	public String cadastraUsuario(String nome, String email, String senha,
			String dataNasc) throws Exception {
		return this.cadastraUsuario(nome, email, senha, dataNasc,
				"resources/default.jpg");
	}

	public String getInfoUsuario(String atributo) throws Exception {
		return sistemaPop.getInfoUsuario(atributo);
	}

	public String getInfoUsuario(String atributo, String usuario)
			throws Exception {
		return sistemaPop.getInfoUsuario(atributo, usuario);
	}

	public void removeUsuario(String email) throws Exception {
		sistemaPop.removeUsuario(email);
	}

	public void atualizaPerfil(String atributo, String valor) throws Exception {
		sistemaPop.atualizaPerfil(atributo, valor);
	}

	public void atualizaPerfil(String atributo, String valor, String velhaSenha)
			throws Exception {
		sistemaPop.atualizaPerfil(atributo, valor, velhaSenha);
	}

	public void criaPost(String mensagem, String data) throws Exception {
		sistemaPop.criaPost(mensagem, data);
	}

	public String getPost(int post) {
		return sistemaPop.getPost(post);
	}

	public String getPost(String atributo, int post) {
		return sistemaPop.getPost(atributo, post);
	}

	public String getConteudoPost(int indice, int post) throws Exception {
		return sistemaPop.getConteudoPost(indice, post);
	}

	public void curtirPost(String email, int indexPost) throws Exception {
		sistemaPop.interagirComPost(indexPost, email, sistemaPop.CURTIR);
	}

	public void rejeitarPost(String email, int indexPost) throws Exception {
		sistemaPop.interagirComPost(indexPost, email, sistemaPop.REJEITAR);
	}

	public void adicionaAmigo(String usuario) throws Exception {
		sistemaPop.adicionaAmigo(usuario);
	}

	public void removeAmigo(String emailUsuario) throws Exception {
		sistemaPop.removeAmigo(emailUsuario);
	}

	public int getNotificacoes() {
		return sistemaPop.getNotificacoes();
	}

	public String getNextNotificacao() throws Exception {
		return sistemaPop.getNextNotificacao();
	}

	public void rejeitaAmizade(String usuario) throws Exception {
		sistemaPop.rejeitaAmizade(usuario);
	}

	public void aceitaAmizade(String usuario) throws Exception {
		sistemaPop.aceitaAmizade(usuario);
	}

	public int getQtdAmigos() {
		return sistemaPop.getQtdAmigos();
	}

	public void adicionaPops(int pops) {
		sistemaPop.adicionaPops(pops);
	}

	public String getPopularidade() {
		return sistemaPop.getTipoPopularidade();
	}

	public String getTipoPopularidade(String email) {
		return sistemaPop.getTipoPopularidade(email);
	}

	public int getPopsPost(int post) throws Exception {
		return sistemaPop.getPopsPost(post);
	}

	public int getPopsPost(int post, String usuario) throws Exception {
		return sistemaPop.getPopsPost(post, usuario);
	}

	public int getCurtidaPorPost(int post, String usuario) throws Exception {
		return sistemaPop.getCurtidaPorPost(post, usuario);
	}

	public int qtdCurtidasDePost(int post) throws Exception {
		return sistemaPop.qtdCurtidasDePost(post);
	}

	public int qtdRejeicoesDePost(int post) throws Exception {
		return sistemaPop.getRejeicaoPorPost(post);
	}

	public int getRejeicaoPorPost(int post, String usuario) throws Exception {
		return sistemaPop.getRejeicaoPorPost(post, usuario);
	}

	public int getPopsUsuario() {
		return sistemaPop.getPopsUsuario();
	}

	public int getPopsUsuario(String usuario) throws Exception {
		return sistemaPop.getPopsUsuario(usuario);
	}

	public String atualizaRanking() {
		return sistemaPop.getRanking();
	}

	public String atualizaTrendingTopics() {
		return sistemaPop.getTrendingTopics();
	}

	public void atualizaFeed() {
		sistemaPop.atualizaFeed();
	}

	public void atualizaFeedPopularidade() {
		sistemaPop.atualizaFeedPopularidade();
	}
	
	public int getTotalPosts(){
		return sistemaPop.getTotalPosts();
	}
	public String getPostFeedNoticiasRecentes(int chavePost) throws Exception {
		return sistemaPop.getPostFeedNoticiasRecentes(chavePost).getPostString();
	}
	
	public String getPostFeedNoticiasMaisPopulares(int chavePost) throws Exception {
		return sistemaPop.getPostFeedNoticiasPopulares(chavePost).getPostString();
	}
	
	public void baixaPosts() throws LogicaException{
		sistemaPop.baixarPosts();
	}
	
	public static void main(String[] args) {
		args = new String[] { "core.Facade","testes/usecase_1","testes/usecase_2",
				"testes/usecase_3", "testes/usecase_4" ,"testes/usecase_5_neto",
				"testes/usecase_6","testes/usecase_7","testes/usecase_8","testes/usecase_9","testes/usecase_10"};
		EasyAccept.main(args);
	}

}
