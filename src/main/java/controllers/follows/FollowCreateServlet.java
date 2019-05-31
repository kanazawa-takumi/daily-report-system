package controllers.follows;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowCreateServlet
 */
@WebServlet("/follow/create")
public class FollowCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = (String)request.getParameter("_token");

        if (token != null && token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
            Employee follower = (Employee)request.getSession().getAttribute("login_employee");
            Employee followee = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
            Follow f = new Follow();
            f.setFollower(follower);
            f.setFollowee(followee);
            f.setFollowAt(new Timestamp(System.currentTimeMillis()));

            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "フォローしました");
            response.sendRedirect(request.getContextPath() + request.getParameter("url"));
        }
    }

}
