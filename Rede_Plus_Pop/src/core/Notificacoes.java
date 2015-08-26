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

	public void addNotificacoes(String novaNotificacao) {
		this.notificacoes.add(novaNotificacao);
	}
	public void removeNotificacao() {
		this.notificacoes.remove(this.notificacoes.size() - 1);
	}
	
	public String getNextNotificacao() throws Exception {
		if (this.notificacoes.size() == 0){
			throw new Exception("Nao ha mais notificacoes.");
		} 
		String mensagem = this.notificacoes.get(0);
		this.notificacoes.remove(0);
		return mensagem;
	}

}
