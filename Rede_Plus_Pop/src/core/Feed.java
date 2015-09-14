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
}
