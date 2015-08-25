package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Notificacoes {
	
	private List<String> notificacoes;
	private Iterator iterator;
	
	public Notificacoes(){
		this.notificacoes = new ArrayList<String>();
		this.iterator = notificacoes.iterator();
	}

	public int getNotificacoes() {
		return this.notificacoes.size();
	}

	public String getNextNotificacao() throws Exception {
		if (!iterator.hasNext()){
			throw new Exception("Nao ha mais notificacoes.");
		} 
		return (String) iterator.next();
	}

}
