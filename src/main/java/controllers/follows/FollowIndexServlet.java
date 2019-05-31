package controllers.follows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowIndexServlet
 */
@WebServlet("/employees/follow")
public class FollowIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        }catch (Exception e) {
            page = 1;
        }
        Employee l = (Employee)request.getSession().getAttribute("login_employee");
        Employee employee = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
        List<Follow> follows = em.createNamedQuery("getEmployeeFollows", Follow.class)
                .setParameter("employee", employee)
                .setFirstResult(15*(page-1))
                .setMaxResults(15)
                .getResultList();

        Long followsCount = em.createNamedQuery("getEmployeeFollowsCount", Long.class)
                .setParameter("employee", employee)
                .getSingleResult();

        ArrayList<Long> followFlags = new ArrayList<Long>();

        for(Follow f : follows) {
            followFlags.add(em.createNamedQuery("isFollowed", Long.class)
                                                .setParameter("followee", f.getFollowee())
                                                .setParameter("follower", l)
                                                .getSingleResult());
        }

        em.close();

        request.setAttribute("follows", follows);
        request.setAttribute("followsCount", followsCount);
        request.setAttribute("page", page);
        request.setAttribute("login_employee", l);
        request.setAttribute("employee", employee);
        request.setAttribute("follow_flags", followFlags);
        request.setAttribute("_token", request.getSession().getId());
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/index.jsp");
        rd.forward(request, response);
    }
}
