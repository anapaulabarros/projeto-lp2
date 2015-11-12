package popularidade;

import core.Post;
import core.SystemPop;

public class IconePop implements TipoPopularidade {

	@Override
	public void curtir(Post post) {
		post.setPopularidade(50);
		post.adicionaHashtag(SystemPop.EPICWIN);
	}

	@Override
	public void rejeitar(Post post) {
		post.setPopularidade(-50);
		post.adicionaHashtag(SystemPop.EPICFAIL);
	}

	public String toString(){
		return "Icone Pop";
	}
	
	@Override
	public int qtdPosts() {
		return 6;
	}	
}
