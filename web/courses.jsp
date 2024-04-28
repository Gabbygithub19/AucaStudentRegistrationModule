<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, main.java.com.auca.model.Semester, main.java.com.auca.dao.SemesterDao, main.java.com.auca.model.Student, main.java.com.auca.dao.StudentDao, main.java.com.auca.model.AcademicUnit, main.java.com.auca.dao.AcademicUnitDao, javax.persistence.EntityManager, javax.persistence.EntityManagerFactory, javax.persistence.Persistence" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Course Creation</title>
    <link rel="stylesheet" type="text/css" href="course.css">
</head>
<body>
    <nav class="sidebar">
        
        <ul class="nav-links">
            <li><a href="Home.jsp">Home</a></li>
           
        </ul>
        <div class="user-links">
            
        </div>
    </nav>
    <div class="content-container">
        <div class="background-image">
            <!-- Your background image goes here -->
        </div>
        <div class="centered-content">
            <div class="signup-container">
                <h1>Course Creation</h1>
                <form method="post" action="${pageContext.request.contextPath}/courses">
                    <input type="hidden" name="action" value="create">
                    <label for="courseId">Course ID:</label>
                    <input type="text" name="id" id="courseId">
                    <label for="courseName">Course Name:</label>
                    <input type="text" name="name" id="courseName">
                   <label for="semId">Select Semester ID:</label>
					<select name="semesterId" id="semId">
            			<option value="" selected>Select Semester ID:</option>
            
            				<%
            					EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnit");
            					EntityManager em = emf.createEntityManager();
            					SemesterDao semesterDao = new SemesterDao(em);
            					List<Semester> semesterList = semesterDao.findAll();
            
            					for (Semester unit : semesterList) {
            					%>
                					<option value="<%= unit.getId() %>"><%= unit.getName() %></option>
            					<%
            					}
            					// Close the EntityManager here
            					em.close();
            					emf.close();
            					%>
        			</select>
                    <label for="deptId">Academic Unit ID:</label>
                    <select name="academicUnitId" id="depId">
                        <option value="" selected>Select department</option>
                        <%
                        EntityManagerFactory emg = Persistence.createEntityManagerFactory("YourPersistenceUnit");
                        EntityManager en = emg.createEntityManager();
                        AcademicUnitDao academicUnitDao = new AcademicUnitDao(en);
                        List<AcademicUnit> departments = academicUnitDao.findDepartments();
                        for (AcademicUnit unit : departments) {
                        %>
                        <option value="<%= unit.getId() %>"><%= unit.getName() %></option>
                        <%
                        }
                        // Close the EntityManager here
                        en.close();
                        emg.close();
                        %>
                    </select>
                    <input type="submit" value="Create Course">
                </form>
            </div>
        </div>
    </div>
</body>
</html>
