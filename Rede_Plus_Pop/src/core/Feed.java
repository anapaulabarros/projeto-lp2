package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	public void atualizaPorPopularidade() {
		Collections.sort(posts, comparePorPopularidade());
	}
	
	/**
	 * Ordena a lista de post por popularidade  usando comparator
	 */
	public void comparePorPopularidade() {
		Collections.sort(posts, new Comparator<Post>() {

			@Override
			public int compare(Post post1, Post post2) {
				if (post1.getPopularidade() > post2.getPopularidade()) {
					return 1;
				} else if (post1.getPopularidade() == post2.getPopularidade()) {
					return 0;
				} else {
					return -1;
				}
			}
		});
	}
}
