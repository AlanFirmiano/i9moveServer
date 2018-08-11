package ufc.br.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Responsible extends User {
	
	@NotNull
	private String name;
	
	public Responsible(){
	}
	
	public Responsible(String email, String password, String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}