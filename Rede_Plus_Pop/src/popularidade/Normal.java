package popularidade;

import core.Post;

public class Normal implements TipoPopularidade {

	@Override
	public void curtir(Post post) {
		post.setPopularidade(10);
	}

	@Override
	public void rejeitar(Post post) {
		post.setPopularidade(-10);
	}

	public String toString(){
		return "Normal Pop";
	}
	@Override
	public int qtdPosts() {
		return 2;
	}

	@Override
	public boolean isEpic() {
		return false;
	}
}
