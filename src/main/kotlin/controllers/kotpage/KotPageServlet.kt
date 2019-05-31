package controllers.kotpage

import java.io.IOException

import javax.servlet.RequestDispatcher
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Servlet implementation class KotPageServlet
 */
/**
 * @see HttpServlet.HttpServlet
 */
@WebServlet("/kotlin")
class KotPageServlet : HttpServlet() {

    /**
     * @see HttpServlet.doGet
     */
    @Throws(ServletException::class, IOException::class)
    internal fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val rd = request.getRequestDispatcher("/WEB-INF/views/kotpage/index.jsp")
        rd.forward(request, response)
    }

    companion object {
        private val serialVersionUID = 1L
    }

}// TODO Auto-generated constructor stub
