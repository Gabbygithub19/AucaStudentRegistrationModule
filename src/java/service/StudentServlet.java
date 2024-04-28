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

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private StudentDao studentDao;

    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        studentDao = new StudentDao(em);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve data from the request
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("telephone");
        LocalDate date = LocalDate.parse(request.getParameter("dob"));
        // Parse other fields like dob and telephone as needed

        // Create a Student object
        Student student = new Student(id, name, date, phone, null); // Replace null with actual values

        // Save the Student
        studentDao.save(student);

        // Redirect to a success page or perform other actions as needed
        response.sendRedirect("Home.jsp");
    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        if (action == null) {
//            // List all students
//            List<Student> students = studentDao.findAll();
//            request.setAttribute("students", students);
//            request.getRequestDispatcher("/list-students.jsp").forward(request, response);
//        } else if (action.equals("edit")) {
//            // Retrieve a student for editing
//            String id = request.getParameter("id");
//            Student student = studentDao.findById(id);
//            request.setAttribute("student", student);
//            request.getRequestDispatcher("/edit-student.jsp").forward(request, response);
//        } else if (action.equals("update")) {
//            // Update a student
//            String id = request.getParameter("id");
//            Student student = studentDao.findById(id);
//            // Update student properties as needed
//            student.setName(request.getParameter("name"));
//            studentDao.update(student);
//            response.sendRedirect(request.getContextPath() + "/success.jsp");
//        } else if (action.equals("delete")) {
//            // Delete a student
//            String id = request.getParameter("id");
//            studentDao.delete(id);
//            response.sendRedirect(request.getContextPath() + "/success.jsp");
//        }
//    }
}
