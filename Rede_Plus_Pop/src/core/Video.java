package core;

public class Video extends ConteudoMidia {

	public Video(String caminho) {
		super(caminho);
	}
	public String toString(){
		return "$arquivo_video:" + caminho;
	}
}
