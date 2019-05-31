package controllers.follows;

import java.io.IOException;

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
 * Servlet implementation class FollowDestroyServlet
 */
@WebServlet("/follow/destroy")
public class FollowDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowDestroyServlet() {
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
                    Follow f = em.find(Follow.class, em.createNamedQuery("getFollowId", Integer.class)
                            .setParameter("follower", follower)
                            .setParameter("followee", followee)
                            .getSingleResult());

                    em.getTransaction().begin();
                    em.remove(f);
                    em.getTransaction().commit();
                    em.close();

                    request.getSession().setAttribute("flush", "フォローを解除しました");
                    response.sendRedirect(request.getContextPath() + request.getParameter("url"));
                }
    }

}
