	package model;
	
	import java.util.List;
	
	import javax.persistence.*;
	
	@Entity
	@Table(name = "academic_unit")
	public class AcademicUnit {
	    @Id
	    
	    @Column(name = "academic_id")
	    private String id;
	    
	    @Column(name = "academic_name")
	    private String name;
	
	    @Enumerated(EnumType.STRING)
	    @Column(name = "type")
	    private EAcademicUnit type;
	    
	    
	    @ManyToMany
	    @JoinTable(
	        name = "teacher_academicunit",
	        joinColumns = @JoinColumn(name = "academic_id"),
	        inverseJoinColumns = @JoinColumn(name = "teacher_id")
	    )
	    private List<Teacher> teacher;
	    
	    @ManyToOne
	    @JoinColumn(name = "unit")
	    private AcademicUnit unit;

	    
	    
		public AcademicUnit() {
			super();
			// TODO Auto-generated constructor stub
		}

		public AcademicUnit(String id, String name, EAcademicUnit type, List<Teacher> teacher, AcademicUnit unit) {
			super();
			this.id = id;
			this.name = name;
			this.type = type;
			this.teacher = teacher;
			this.unit = unit;
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

		public EAcademicUnit getType() {
			return type;
		}

		public void setType(EAcademicUnit type) {
			this.type = type;
		}

		public List<Teacher> getTeachers() {
			return teacher;
		}

		public void setTeachers(List<Teacher> teachers) {
			this.teacher = teachers;
		}

		public AcademicUnit getUnit() {
			return unit;
		}

		public void setUnit(AcademicUnit unit) {
			this.unit = unit;
		}
	
		
	}