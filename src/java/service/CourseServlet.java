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
import java.util.List;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {
    private CourseDao courseDao;
    private SemesterDao semesterDao;
    private AcademicUnitDao academicDao;

    public void init() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        courseDao = new CourseDao(em);
        semesterDao = new SemesterDao(em);
        academicDao = new AcademicUnitDao(em);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Display a list of courses (read operation)
            List<Course> courses = courseDao.findAll();
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("/semester.jsp").forward(request, response);
        } else if (action.equals("new")) {
            // Show the form to create a new course
            request.getRequestDispatcher("/semester.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // Display the form to edit an existing course
            String id = request.getParameter("id");
            Course course = courseDao.findById(id);
            request.setAttribute("course", course);
            request.getRequestDispatcher("/semester.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            // Delete a course
            String id = request.getParameter("id");
            courseDao.delete(id);
            response.sendRedirect(request.getContextPath() + "/courses");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("create")) {
            // Create a new course
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String semesterId = request.getParameter("semesterId");
            String academicUnitId = request.getParameter("academicUnitId");
            System.out.println("Academic Unit ID: " + academicUnitId);
            
            // Check if semesterId and academicUnitId are not empty
            if (semesterId.isEmpty() || academicUnitId.isEmpty()) {
                // Handle the error appropriately, e.g., show an error message or redirect to an error page.
                // For now, let's assume you show an error message.
                request.setAttribute("error", "Invalid Semester or Academic Unit ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } else {
                // Load Semester and AcademicUnit objects
                Semester sem = semesterDao.findById(semesterId);
                AcademicUnit academic = academicDao.findById(academicUnitId);

                // Check if the Semester and AcademicUnit objects are found
                if (sem != null && academic != null) {
                    // Retrieve other course attributes as needed
                    Course course = new Course(id, name, sem, academic, null);
                    courseDao.save(course);
                    response.sendRedirect("Home.jsp");
                } else {
                    // Handle the error appropriately, e.g., show an error message or redirect to an error page.
                    // For now, let's assume you show an error message.
                    request.setAttribute("error", "Semester or Academic Unit not found");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            }
        } else if (action.equals("update")) {
            // Update an existing course
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            // Retrieve other course attributes as needed
            Course course = courseDao.findById(id);
            course.setName(name);
            courseDao.update(course);
            response.sendRedirect(request.getContextPath() + "/courses");
        }
    }
}
