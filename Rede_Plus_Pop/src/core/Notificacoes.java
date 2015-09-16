package core;

import java.util.ArrayList;
import java.util.List;


public class Notificacoes {
	
	private List<String> notificacoes;
	
	public Notificacoes(){
		this.notificacoes = new ArrayList<String>();
	}

	public int getNotificacoes() {
		return this.notificacoes.size();
	}

	/**
	 * Metodo para adicionar na lista uma nova notificacao que o usuario recebeu
	 * @param novaNotificacao
	 * @return void
	 */
	public void addNotificacoes(String novaNotificacao) {
		this.notificacoes.add(novaNotificacao);
	}
	
	/**
	 * Metodo para remover uma notificao da lista, quando o usuario a ler.
	 * 
	 * @param void
	 * @return void
	 */
	public void removeNotificacao() {
		this.notificacoes.remove(this.notificacoes.size() - 1);
	}
	
	/**
	 * Metodo para retornar a proxima notificacao que o usuario possui
	 * @return String - mensagem
	 * @throws Exception
	 */
	public String getNextNotificacao() throws Exception {
		if (this.notificacoes.size() == 0){
			throw new Exception("Nao ha mais notificacoes.");
		} 
		String mensagem = this.notificacoes.get(0);
		this.notificacoes.remove(0);
		return mensagem;
	}

}
