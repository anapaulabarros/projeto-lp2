package core;

public class Audio extends ConteudoMidia {

	public Audio(String caminho) {
		super(caminho);
	}
	public String toString(){
		return "$arquivo_audio:" + caminho.substring(7, caminho.length() - 8);
	}
}
