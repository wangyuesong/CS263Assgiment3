//package login;
//
//import twitter4j.Twitter;
//import twitter4j.TwitterException;
//import twitter4j.TwitterFactory;
//import twitter4j.auth.RequestToken;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@SuppressWarnings("serial")
//public class SignInServlet extends HttpServlet {
//    
//    /**
//     * Method doGet
//     * 
//     * Creates an signin url for twitter.
//     */
//    
//    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        Twitter twitter = new TwitterFactory().getInstance();
//        req.getSession().setAttribute("twitter", twitter);
//        try {
//            StringBuffer callbackURL = req.getRequestURL();
//            int index = callbackURL.lastIndexOf("/");
//            callbackURL.replace(index, callbackURL.length(), "").append("/callback");
//
//            RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
//            req.getSession().setAttribute("requestToken", requestToken);
//            res.sendRedirect(requestToken.getAuthenticationURL());
//        } catch (TwitterException e) {
//            throw new ServletException(e);
//        }
//    }
//}