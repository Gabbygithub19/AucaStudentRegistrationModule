package model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private EQualification qualification;

    
    @ManyToMany
    @JoinTable(
        name = "teacher_academicunit",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "academicunit_id")
    )
    private List<AcademicUnit> academicUnits;

    public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teacher(String id, String name, EQualification qualification, List<AcademicUnit> academicUnits) {
		super();
		this.id = id;
		this.name = name;
		this.qualification = qualification;
		this.academicUnits = academicUnits;
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

	public EQualification getQualification() {
		return qualification;
	}

	public void setQualification(EQualification qualification) {
		this.qualification = qualification;
	}

	public List<AcademicUnit> getAcademicUnits() {
		return academicUnits;
	}

	public void setAcademicUnits(List<AcademicUnit> academicUnits) {
		this.academicUnits = academicUnits;
	}
    
}
