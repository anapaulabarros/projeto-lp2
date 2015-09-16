package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	/**
	 * Atualiza os posts por ordem de popularidade
	 * 
	 * @param void
	 * @return void
	 */
	public void atualizaPorPopularidade() {
		Collections.sort(posts, new Comparator<Post>() {

			@Override
			public int compare(Post post1, Post post2) {
				if(post1.getPopularidade() == post2.getPopularidade())
					return 0;
				else
					return post1.getPopularidade() > post2.getPopularidade() ? 1 : -1;
			}
		});
	}
}
