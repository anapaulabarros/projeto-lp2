package core;

public class Marcador implements Comparable<Marcador>{

	private String hashtag;
	private int ocorrencia;
	
	public Marcador(String hashtag){
		this.hashtag = hashtag;
		this.ocorrencia = 1;
	}
	
	public void aumentaOcorrencia(){
		this.ocorrencia = this.ocorrencia + 1;
	}
	
	public int getOcorrencia(){
		return this.ocorrencia;
	}
	
	public String getHashtag(){
		return this.hashtag;
	}
	
	@Override
	public String toString(){
		return this.hashtag + ": " + this.ocorrencia;
	}
	
	@Override
	public int compareTo(Marcador outroMarcador) {
		if (this.ocorrencia > outroMarcador.getOcorrencia()){
			return -1;
		} else if (this.ocorrencia == outroMarcador.getOcorrencia()){
			return this.hashtag.compareTo(outroMarcador.getHashtag());
		} else{
			return 1;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashtag == null) ? 0 : hashtag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Marcador){
			Marcador outroMarcador = (Marcador) obj;
			return this.hashtag.equals(outroMarcador.getHashtag());
		}
		return false;
	}
}
