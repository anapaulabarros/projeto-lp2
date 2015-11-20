package popularidade;

import java.io.Serializable;

import core.Post;

public interface TipoPopularidade extends Serializable{
	
	public void curtir(Post post);
	
	public void rejeitar(Post post);
	
	public int qtdPosts();
}
