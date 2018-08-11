package ufc.br.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Patient extends User {
	
	@NotNull
	private String registration;
	@NotNull
	private String name;
	@NotNull
	private String phone;
	@NotNull
	private String parent;
	@NotNull
	private String phoneParent;
	@NotNull
	private int progress;
	
	private int level;
	@ManyToOne(cascade={CascadeType.MERGE})
	private Responsible responsible;

	public Patient(){

	}
	
	public Patient(String registration, String name, String phone, String parent, String phoneParent){
		this.registration = registration;
		this.name = name;
		this.phone = phone;
		this.parent = parent;
		this.phoneParent = phoneParent;
		this.progress = 0;
		this.level = 1;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getRegistration() {
		return registration;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getPhoneParent() {
		return phoneParent;
	}

	public void setPhoneParent(String phoneParent) {
		this.phoneParent = phoneParent;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public Responsible getResponsible() {
		return responsible;
	}

	public void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}
}