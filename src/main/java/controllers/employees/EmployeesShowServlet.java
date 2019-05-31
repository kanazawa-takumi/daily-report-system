package controllers.employees;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesShowServlet
 */
@WebServlet("/employees/show")
public class EmployeesShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmployeesShowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
        Employee l = (Employee)request.getSession().getAttribute("login_employee");
        Long followFlag = em.createNamedQuery("isFollowed", Long.class).setParameter("followee", e).setParameter("follower", l).getSingleResult();
        Long followCount = em.createNamedQuery("getEmployeeFollowsCount", Long.class).setParameter("employee", e).getSingleResult();
        em.close();

        request.setAttribute("follow_flag", followFlag);
        request.setAttribute("follow_count", followCount);
        request.setAttribute("employee", e);
        request.setAttribute("login_employee", l);
        request.setAttribute("_token", request.getSession().getId());
        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/show.jsp");
        rd.forward(request, response);
    }

}