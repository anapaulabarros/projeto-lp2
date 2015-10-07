package core;

public interface TipoPopularidade {
	
	public void curtir(Post post);
	
	public void rejeitar(Post post);
	
	public int qtdPosts();
}
