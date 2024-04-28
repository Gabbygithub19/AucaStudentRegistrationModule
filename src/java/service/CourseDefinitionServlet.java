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

@WebServlet("/course-definition")
public class CourseDefinitionServlet extends HttpServlet {
    private CourseDefinitionDao courseDefinitionDao;
    private CourseDao courseDao;

    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        courseDefinitionDao = new CourseDefinitionDao(em); // Initialize courseDefinitionDao
        courseDao = new CourseDao(em);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Course def = courseDao.findById(id);

        CourseDefinition courseDefinition = new CourseDefinition(def, name, description);

        // Save the CourseDefinition
        courseDefinitionDao.save(courseDefinition);

        // Redirect to a success page or perform other actions as needed
        response.sendRedirect("Home.jsp");
    }
}
