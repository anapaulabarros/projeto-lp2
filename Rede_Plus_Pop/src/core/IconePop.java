package core;

public class IconePop implements TipoPopularidade {

	@Override
	public void curtir(Post post) {
		post.setPopularidade(50);
	}

	@Override
	public void rejeitar(Post post) {
		post.setPopularidade(-50);
	}

	public String toString(){
		return "IconePop";
	}
}
