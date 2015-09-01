package core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CelebridadePop implements TipoPopularidade {

	@Override
	public void curtir(Post post) {
		int pontos = +25;
		if (comparaDatas(post.getDataPostFormatada()) == true) {
			pontos =  pontos + 10;
		}
		post.setPopularidade(pontos);
	}

	@Override
	public void rejeitar(Post post) {
		int pontos = -25;
		if (comparaDatas(post.getDataPublicacao()) == true) {
			pontos =  pontos - 10;
		}
		post.setPopularidade(pontos);
	}
	
	private boolean comparaDatas(String umaData) {
		Date dataAtual = new Date();
		SimpleDateFormat formataDataAtual = new SimpleDateFormat("yyyy-MM-dd");
		String dataCorrente = formataDataAtual.format(dataAtual);
		if(dataCorrente.equals(umaData.substring(0, 10)))
			return true;
		return false;
	}
	
	public String toString(){
		return "CelebridadePop";
	}

}
