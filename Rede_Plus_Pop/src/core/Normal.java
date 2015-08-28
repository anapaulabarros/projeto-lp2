package core;

public class Normal implements TipoPopularidade {

	@Override
	public void curtir(Post post) {
		post.setPopularidade(10);
		post.setCurtidas();
	}

	@Override
	public void rejeitar(Post post) {
		post.setPopularidade(-10);
		post.setRejeitadas();
	}

	public String toString(){
		return "Normal";
	}
}
