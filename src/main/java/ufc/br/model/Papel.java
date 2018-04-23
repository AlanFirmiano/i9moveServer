package ufc.br.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Papel implements GrantedAuthority {

	@Id
	@GeneratedValue
	private Integer id;
	private String descricao;
	
	public Papel() {
		
	}
	
	public Papel(String descricao) {
		this.descricao = descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getAuthority() {
		return descricao;
	}
	
	
}
