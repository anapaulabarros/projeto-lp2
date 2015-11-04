package popularidade;

import core.Post;

public interface TipoPopularidade {
	
	public void curtir(Post post);
	
	public void rejeitar(Post post);
	
	public int qtdPosts();
	
	public boolean isEpic(); 
}
