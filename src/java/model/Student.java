package model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "student_dob")
    private LocalDate dob;
    
    @Column(name = "student_phone")
    private String telephone;    
    
    @ManyToMany
    @JoinTable(name = "student_course")
 	private List<Course> courses;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(String id, String name, LocalDate dob, String telephone, List<Course> courses) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.telephone = telephone;
		this.courses = courses;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

		
}