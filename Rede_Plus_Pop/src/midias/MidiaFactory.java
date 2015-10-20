package midias;

import core.UtilPost;

public class MidiaFactory {
	
	public MidiaFactory(){
		
	}

	public ConteudoMidia criaMidia(String caminho) {
		if (caminho.startsWith("<audio>")) {
			return criaAudio(caminho);
		} else if (caminho.startsWith("<imagem>")) {
			return criaImagem(caminho);
		} else {
			return criaVideo(caminho);
		}
	}

	private ConteudoMidia criaVideo(String caminho) {
		caminho = UtilPost.cortaCaminhoMidia(caminho);
		ConteudoMidia novaMidia = new Video(caminho);
		return novaMidia;
	}

	private ConteudoMidia criaImagem(String caminho) {
		caminho = UtilPost.cortaCaminhoMidia(caminho);
		ConteudoMidia novaMidia = new Imagem(caminho);
		return novaMidia;
	}

	private ConteudoMidia criaAudio(String caminho) {
		caminho = UtilPost.cortaCaminhoMidia(caminho);
		ConteudoMidia novaMidia = new Audio(caminho);
		return novaMidia;
	}
}
