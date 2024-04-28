package model;

import java.util.List;
import javax.persistence.*;



@Entity
@Table(name = "course")

public class Course {
    @Id
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "semesterId")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private AcademicUnit department;
    
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
    
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(String id, String name, Semester semester, AcademicUnit department, List<Student> students) {
		super();
		this.id = id;
		this.name = name;
		this.semester = semester;
		this.department = department;
		this.students = students;
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

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public AcademicUnit getDepartment() {
		return department;
	}

	public void setDepartment(AcademicUnit department) {
		this.department = department;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	
}
