package service;

import dao.*;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/studentregisteration")
public class StudentRegisterationServlet extends HttpServlet {
	private StudentRegistrationDao studentregistrationDao;
    private SemesterDao semesterDao;
    private AcademicUnitDao academicDao;
    private StudentDao studentDao;

    public void init() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        studentDao = new StudentDao(em);
        studentregistrationDao = new StudentRegistrationDao(em);
        semesterDao = new SemesterDao(em);
        academicDao = new AcademicUnitDao(em);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Display a list of courses (read operation)
            List<StudentRegisteration> studentregisteration = studentregistrationDao.findAll();
            request.setAttribute("studentregisteration", studentregisteration);
            request.getRequestDispatcher("/semester.jsp").forward(request, response);
        } else if (action.equals("new")) {
            // Show the form to create a new course
            request.getRequestDispatcher("/semester.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // Display the form to edit an existing course
            String id = request.getParameter("id");
            StudentRegisteration studentregisteration = studentregistrationDao.findById(id);
            request.setAttribute("studentregisteration", studentregisteration);
            request.getRequestDispatcher("/semester.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            // Delete a course
            String id = request.getParameter("id");
            studentregistrationDao.delete(id);
            response.sendRedirect(request.getContextPath() + "/courses");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("create")) {
            // Create a new course
            String id = request.getParameter("id");
            String academicUnitId = request.getParameter("academicUnitId");
            String semesterId = request.getParameter("semesterId");
            String studentId = request.getParameter("studentId");
            LocalDate rdate = LocalDate.parse(request.getParameter("regDate"));
            System.out.println("Academic Unit ID: " + academicUnitId);
            
            if (semesterId.isEmpty() || academicUnitId.isEmpty()) {
                
                request.setAttribute("error", "Invalid Semester or Academic Unit ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } else {
            	AcademicUnit academic = academicDao.findById(academicUnitId);
            	Semester sem = semesterDao.findById(semesterId);
                Student stu = studentDao.findById(studentId);

                if (sem != null && academic != null && stu != null) {
                	StudentRegisteration studentregisteration = new StudentRegisteration(id, stu, sem, academic, null, rdate);
                	studentregistrationDao.save(studentregisteration);
                    response.sendRedirect("Home.jsp");
                } else {
                    // Handle the error appropriately, e.g., show an error message or redirect to an error page.
                    // For now, let's assume you show an error message.
                    request.setAttribute("error", "Semester or Department or Student not found");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            }
        }
    }
}
