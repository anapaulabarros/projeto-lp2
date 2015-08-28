package core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CelebridadePop implements TipoPopularidade {

	@Override
	public void curtir(Post post) {
		int pontos = 25;
		if (comparaDatas(post.getDataPublicacao())) {
			pontos =  pontos + 10;
		}
		post.setPopularidade(pontos);
		post.setCurtidas();
	}

	@Override
	public void rejeitar(Post post) {
		int pontos = -25;
		if (comparaDatas(post.getDataPublicacao())) {
			pontos =  pontos - 10;
		}
		post.setPopularidade(pontos);
		post.setRejeitadas();
	}
	
	private boolean comparaDatas(String umaData) {
		Date dataAtual = new Date();
		SimpleDateFormat formataDataAtual = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		String dataCorrente = formataDataAtual.format(dataAtual);
		if(dataCorrente.equals(umaData))
			return true;
		return false;
	}
	
	public String toString(){
		return "CelebridadePop";
	}

}
