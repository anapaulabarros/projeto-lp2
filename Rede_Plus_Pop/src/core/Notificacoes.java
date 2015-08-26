package core;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Notificacoes {
	
	private Deque<String> notificacoes;
	private Iterator<String> iterator;
	
	public Notificacoes(){
		this.notificacoes = new LinkedList<String>();
		this.iterator = notificacoes.iterator();
	}

	public int getNotificacoes() {
		return this.notificacoes.size();
	}

	public void addNotificacoes(String novaNotificacao) {
		this.notificacoes.addLast(novaNotificacao);
	}
	
	public void removeNoticicacao() {
		this.notificacoes.removeLast();
	}
	
	public String getNextNotificacao() throws Exception {
		if (!iterator.hasNext()){
			throw new Exception("Nao ha mais notificacoes.");
		} 
		
		return iterator.next();
	}

}
