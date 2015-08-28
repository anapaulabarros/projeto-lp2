package core;

public class IconePop implements TipoPopularidade {

	@Override
	public void curtir(Post post) {
		post.setPopularidade(50);
		post.setCurtidas();
	}

	@Override
	public void rejeitar(Post post) {
		post.setPopularidade(-50);
		post.setRejeitadas();
	}

	public String toString(){
		return "IconePop";
	}
}
