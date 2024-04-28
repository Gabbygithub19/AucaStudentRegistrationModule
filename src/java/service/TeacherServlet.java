package service;

import dao.*;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Enumerated;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/teachers")
public class TeacherServlet extends HttpServlet {
    private TeacherDao teacherDao;
    private AcademicUnitDao academicUnitDao;

    public void init() {
    	  EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnit");
          EntityManager em = emf.createEntityManager();
          teacherDao = new TeacherDao(em);
          academicUnitDao = new AcademicUnitDao(em); // Initialize academicUnitDao
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Display a list of teachers (read operation)
            List<Teacher> teachers = teacherDao.findAll();
            request.setAttribute("teachers", teachers);
            request.getRequestDispatcher("/courses.jsp").forward(request, response);
        } else if (action.equals("new")) {
            // Show the form to create a new teacher
            request.getRequestDispatcher("/courses.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // Display the form to edit an existing teacher
            String id = request.getParameter("id");
            Teacher teacher = teacherDao.findById(id);
            request.setAttribute("teacher", teacher);
            request.getRequestDispatcher("/courses.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            // Delete a teacher
            String id = request.getParameter("id");
            teacherDao.delete(id);
            response.sendRedirect(request.getContextPath() + "/teachers");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("create")) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String qualificationValue = request.getParameter("qualification");
            EQualification qualification = EQualification.valueOf(qualificationValue);

            Teacher teacher = new Teacher(id, name, qualification, null);

            teacherDao.save(teacher); 

            response.sendRedirect("Home.jsp");
        
        } else if (action.equals("update")) {
            // Update an existing teacher
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            // Retrieve other teacher attributes as needed
            Teacher teacher = teacherDao.findById(id);
            teacher.setName(name);
            teacherDao.update(teacher);
            response.sendRedirect(request.getContextPath() + "/teachers");
        }
    }
}
