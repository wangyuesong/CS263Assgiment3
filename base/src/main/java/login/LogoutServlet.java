package login;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;

@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {
    
    /**
     * Method doGet
     * 
     * Logs out of twitter
     */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+ "/");
    }
}