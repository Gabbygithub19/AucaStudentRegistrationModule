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

@WebServlet("/semesters")
public class SemesterServlet extends HttpServlet {
    private SemesterDao semesterDao;

    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        semesterDao = new SemesterDao(em);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Display a list of semesters (read operation)
            List<Semester> semesters = semesterDao.findAll();
            request.setAttribute("semesters", semesters);
            request.getRequestDispatcher("/teacher.jsp").forward(request, response);
        } else if (action.equals("new")) {
            // Show the form to create a new semester
            request.getRequestDispatcher("/teacher.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // Display the form to edit an existing semester
            String id = request.getParameter("id");
            Semester semester = semesterDao.findById(id);
            request.setAttribute("semester", semester);
            request.getRequestDispatcher("/teacher.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            // Delete a semester
            String id = request.getParameter("id");
            semesterDao.delete(id);
            response.sendRedirect(request.getContextPath() + "/semesters");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("create")) {
            // Create a new semester
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            LocalDate date = LocalDate.parse(request.getParameter("startDate"));
            LocalDate end = LocalDate.parse(request.getParameter("endDate"));
            // Retrieve other semester attributes as needed
            Semester semester = new Semester(id, name, date, end);
            semesterDao.save(semester);
            response.sendRedirect("Home.jsp");
        } else if (action.equals("update")) {
            // Update an existing semester
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            // Retrieve other semester attributes as needed
            Semester semester = semesterDao.findById(id);
            semester.setName(name);
            semesterDao.update(semester);
            response.sendRedirect(request.getContextPath() + "/semesters");
        }
    }
}
