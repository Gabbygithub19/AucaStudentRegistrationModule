package model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "semester")
public class Semester {
    @Id
    @Column(name = "id")
    private String id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    

   
	public Semester() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Semester(String id, String name, LocalDate startDate, LocalDate endDate) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
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




	public LocalDate getStartDate() {
		return startDate;
	}




	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}




	public LocalDate getEndDate() {
		return endDate;
	}




	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


		
}
