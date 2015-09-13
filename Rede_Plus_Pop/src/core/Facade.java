package core;

import java.util.Map;

import treatmentsExceptions.SystemPopExceptions;
import easyaccept.EasyAccept;

public class Facade {
	
	private SystemPop sistemaPop;
	
	public Facade(){
		sistemaPop = new SystemPop();
	}
	
	public void iniciaSistema(){
		sistemaPop.iniciaSistema();
	}
	public void fechaSistema() throws Exception {
		sistemaPop.fechaSistema();
	}
	
	public void login(String email, String senha) throws Exception{
		sistemaPop.login(email, senha);
	}
	
	public void logout() throws Exception{
		sistemaPop.logout();
	}
	
	public String cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws Exception{
		return sistemaPop.cadastraUsuario(nome, email, senha, dataNasc, imagem);
	}
	
	public String cadastraUsuario(String nome, String email, String senha, String dataNasc) throws Exception{
		return this.cadastraUsuario(nome, email, senha, dataNasc, "resources/default.jpg");
	}
	
	public String getInfoUsuario(String atributo) throws Exception{
		return sistemaPop.getInfoUsuario(atributo);
	}
	
	public String getInfoUsuario(String atributo, String usuario) throws Exception{
		return sistemaPop.getInfoUsuario(atributo, usuario);
	}
	
	public void removeUsuario(String email) throws Exception{
		sistemaPop.removeUsuario(email);
	}
	
	public void atualizaPerfil(String atributo, String valor) throws Exception{
		sistemaPop.atualizaPerfil(atributo, valor);
	}
	
	public void atualizaPerfil(String atributo, String valor, String velhaSenha) throws Exception{
		sistemaPop.atualizaPerfil(atributo, valor, velhaSenha);
	}
	
	public void criaPost(String mensagem, String data) throws Exception{
		sistemaPop.criaPost(mensagem, data);
	}
	
	public String getPost(int post){
		return sistemaPop.getPost(post);
	}
	
	public String getPost(String atributo, int post){
		return sistemaPop.getPost(atributo, post);
	}
	
	public String getConteudoPost(int indice, int post) throws Exception{
		return sistemaPop.getConteudoPost(indice, post);
	}
	
	public void curtirPost(String email,int indexPost) throws SystemPopExceptions {
		sistemaPop.interagirComPost(indexPost, email, sistemaPop.CURTIR);
	}
	
	public void rejeitarPost(String email, int indexPost) throws SystemPopExceptions {
		sistemaPop.interagirComPost(indexPost, email, sistemaPop.REJEITAR);
	}
	
	public void adicionaAmigo(String usuario) throws SystemPopExceptions {
		sistemaPop.adicionaAmigo(usuario);
	}
	
	public void removeAmigo(String emailUsuario) throws SystemPopExceptions {
		sistemaPop.removeAmigo(emailUsuario);
	}

	public int getNotificacoes(){
		return sistemaPop.getNotificacoes();
	}
	
	public String getNextNotificacao() throws Exception{
		return sistemaPop.getNextNotificacao();
	}
	
	public void rejeitaAmizade(String usuario) throws SystemPopExceptions {
		sistemaPop.rejeitaAmizade(usuario);
	}
	
	public void aceitaAmizade(String usuario) throws SystemPopExceptions {
		sistemaPop.aceitaAmizade(usuario);
	}
	
	public int getQtdAmigos() {
		return sistemaPop.getQtdAmigos();
	}
	
	public void setPops(int pops){
		sistemaPop.setPops(pops);
	}
	
	public String getTipoPopularidade(){
		return sistemaPop.getTipoPopularidade();
	}
	
	public String getTipoPopularidade(String email){
		return sistemaPop.getTipoPopularidade(email);
	}
	
	public int getPopsPost(int post) throws Exception{
		return sistemaPop.getPopsPost(post);
	}
	
	public int getPopsPost(int post, String usuario) throws Exception{
		return sistemaPop.getPopsPost(post, usuario);
	}
	
	public int getCurtidaPorPost(int post) throws Exception{
		return sistemaPop.getCurtidaPorPost(post);
	}
	
	public int getCurtidaPorPost(int post, String usuario) throws Exception{
		return sistemaPop.getCurtidaPorPost(post, usuario);
	}
	
	public int getRejeicaoPorPost(int post) throws Exception{
		return sistemaPop.getRejeicaoPorPost(post);
	}
	
	public int getRejeicaoPorPost(int post, String usuario) throws Exception{
		return sistemaPop.getRejeicaoPorPost(post, usuario);
	}
	
	public int getPopsUsuario(){
		return sistemaPop.getPopsUsuario();
	}
	
	public int getPopsUsuario(String usuario){
		return sistemaPop.getPopsUsuario(usuario);
	}
	
	public String getRankingMais() {
		return sistemaPop.getRankingMais();
	}
	
	public String getRankingMenos() {
		return sistemaPop.getRankingMenos();
	}
	
	public void atualizaRankings() {
		sistemaPop.getRankingMenos();
		sistemaPop.getRankingMais();
	}
	
	public Map<String, Integer> getMaisPopHastags() {
		return sistemaPop.getRankigHastagsMais();
	}

	public static void main(String[] args) {
		args = new String[] {"core.Facade", "teste_aceitacao/usecase_1", "teste_aceitacao/usecase_2", "teste_aceitacao/usecase_3", "teste_aceitacao/usecase_4", "teste_aceitacao/usecase_5"};
		EasyAccept.main(args);
	}
	
}
