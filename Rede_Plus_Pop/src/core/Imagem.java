package core;

public class Imagem extends ConteudoMidia {

	public Imagem(String caminho) {
		super(caminho);
	}
	public String toString(){
		return "$arquivo_imagem:" + caminho;
	}
}
