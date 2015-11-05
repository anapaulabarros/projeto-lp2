package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrendingTopics {
	
	private List<Marcador> listaMarcadores;
	
	public TrendingTopics(){
		this.listaMarcadores = new ArrayList<Marcador>();
	}

	public void adicionaMarcador(Marcador marcador){
		this.listaMarcadores.add(marcador);
	}
	
	public void ordenaTrends(){
		Collections.sort(this.listaMarcadores);
	}
	
	public List<Marcador> getLista(){
		return this.listaMarcadores;
	}
	
	public String getRanking(){
		String retorno = "Trending Topics:  " + getRankingMais();
		return retorno;
	}

	private String getRankingMais() {
		if (listaMarcadores.size() != 0) {
			ordenaTrends();
			Marcador primeiro = listaMarcadores.get(0);
			Marcador segundo = listaMarcadores.get(1);
			Marcador terceiro = listaMarcadores.get(2);
			
			return "(1) " + primeiro.toString() 
					+ "; (2) " + segundo.toString()
					+ "; (3) " + terceiro.toString() + ";";
		}
		return "";
	}

	private String getRankingMenos() {
		if (listaMarcadores.size() != 0) {
			ordenaTrends();
			Marcador primeiro = listaMarcadores.get(listaMarcadores.size() - 1);
			Marcador segundo = listaMarcadores.get(listaMarcadores.size() - 2);
			Marcador terceiro = listaMarcadores.get(listaMarcadores.size() - 3);
			
			return "(1) " + primeiro.toString()
					+ "; (2) " + segundo.toString()
					+ "; (3) " + terceiro.toString() + ";";
		}
		return "";
	}
}

