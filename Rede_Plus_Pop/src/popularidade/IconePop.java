package popularidade;

import core.Post;

public class IconePop implements TipoPopularidade {

	@Override
	public void curtir(Post post) {
		post.setPopularidade(50);
		post.adicionaHashtag("#epicwin");
	}

	@Override
	public void rejeitar(Post post) {
		post.setPopularidade(-50);
		post.adicionaHashtag("#epicfail");
	}

	public String toString(){
		return "IconePop";
	}
	
	@Override
	public int qtdPosts() {
		return 6;
	}
}