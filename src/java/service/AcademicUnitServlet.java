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
import java.util.*;

@WebServlet("/academicUnit")
public class AcademicUnitServlet extends HttpServlet {
    private AcademicUnitDao academicUnitDao;
    private EntityManagerFactory emf;

    public void init() {
    	System.out.println("Servlet initialized.");
        // Create an EntityManagerFactory programmatically
        Map<String, String> persistenceProps = new HashMap<>();
        persistenceProps.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        persistenceProps.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/Alinemid23678");
        persistenceProps.put("javax.persistence.jdbc.user", "root");
        persistenceProps.put("javax.persistence.jdbc.password", "12345");

        emf = Persistence.createEntityManagerFactory("YourPersistenceUnit", persistenceProps);
        EntityManager em = emf.createEntityManager();
        academicUnitDao = new AcademicUnitDao(em);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");

        if (action == null) {
        	List<AcademicUnit> academicUnits = academicUnitDao.findAll();
            request.setAttribute("academicUnits", academicUnits);
            List<AcademicUnit> departments = academicUnitDao.findDepartments();
            request.setAttribute("departments", departments);
            List<AcademicUnit> selfReferenceUnits = academicUnitDao.findAll(); // Retrieve self-reference units
            request.setAttribute("selfReferenceUnits", selfReferenceUnits);
            request.getRequestDispatcher("/DAU.jsp").forward(request, response);
        } else if (action.equals("new")) {
            // Show the form to create a new academic unit
            request.getRequestDispatcher("/student.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // Display the form to edit an existing academic unit
            String id = request.getParameter("id");
            AcademicUnit academicUnit = academicUnitDao.findById(id);
            request.setAttribute("academicUnit", academicUnit);
            request.getRequestDispatcher("/student.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            // Delete an academic unit
            String id = request.getParameter("id");
            academicUnitDao.delete(id);
            response.sendRedirect(request.getContextPath() + "/academic-units");
        }
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String unit = request.getParameter("unit");
            EAcademicUnit academicUnitEnum = EAcademicUnit.valueOf(type);
            AcademicUnit academicId = null; // Default is set to null

            // Check if self-referenceId is empty and the unit type is PROGRAMME
            if (unit != null && !unit.isEmpty() && !"PROGRAMME".equals(type)) {
                academicId = academicUnitDao.findById(unit);
            }

            // Create your AcademicUnit entity and save it to the database
            AcademicUnit academicUnit = new AcademicUnit(id, name, academicUnitEnum, null, academicId);
            academicUnitDao.save(academicUnit);

            response.sendRedirect("Home.jsp");
        } else if ("update".equals(action)) {
            // Update an existing academic unit
            String id = request.getParameter("id");
            String type = request.getParameter("type");
            AcademicUnit academicUnit = academicUnitDao.findById(id);
            academicUnit.setType(EAcademicUnit.valueOf(type));
            academicUnitDao.update(academicUnit);
            response.sendRedirect(request.getContextPath() + "/academic-units");
        }
    }

    
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}