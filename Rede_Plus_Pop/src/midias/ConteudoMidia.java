package midias;

import java.io.Serializable;

public class ConteudoMidia implements Serializable{

	String caminho;
	
	public ConteudoMidia(String caminho){
		this.caminho = caminho;
	}
}
