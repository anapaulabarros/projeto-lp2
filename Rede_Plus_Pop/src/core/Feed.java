package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import treatmentsExceptions.PostException;

public class Feed {
	
	private List<Post> posts;
	
	public Feed(){
		this.posts = new ArrayList<Post>();
	}

	public void adicionaPost(Post post){
		this.posts.add(post);
	}
	
	public void atualiza(){
		Collections.sort(posts);
	}
	
	public void ordenaPorData() {
		Collections.sort(this.posts);
	}
	
	/**
	 * Atualiza os posts por ordem de popularidade
	 * 
	 * @param void
	 * @return void
	 */
	public void atualizaPorPopularidade() {
		Collections.sort(this.posts, new Comparator<Post>() {

			@Override
			public int compare(Post post1, Post post2) {
				if (post1.getPopularidade() > post2.getPopularidade()) {
						return 1;
					} else if (post1.getPopularidade() < post2.getPopularidade()) {
						return -1;
					} else {
					return 0;
					}
				}
			
			});
	}
	public List<Post> getPosts() {
		return posts;
	}
	public int sizeFeed(){return posts.size();}
}
