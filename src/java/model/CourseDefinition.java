package model;

import java.io.Serializable;

import javax.persistence.*;



@Entity
@Table(name = "Course_Definition")

public class CourseDefinition implements Serializable{
	@Id
	@OneToOne
	@JoinColumn(name = "id")
	private Course id;
	
	private String name;
	private String description;
	
	public CourseDefinition() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseDefinition(Course id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Course getId() {
		return id;
	}

	public void setId(Course id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
