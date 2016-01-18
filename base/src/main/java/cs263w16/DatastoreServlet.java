package cs263w16;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.Filter;

import com.google.appengine.api.memcache.*;

@SuppressWarnings("serial")
public class DatastoreServlet extends HttpServlet {

  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      String keyName = req.getParameter("keyname");
      String value = req.getParameter("value");
      if(keyName != null && value != null){
          Entity entity = new Entity("TaskData",keyName);
          entity.setProperty("value", value);
          entity.setProperty("date", new Date().toString());
          datastore.put(entity);
          resp.setContentType("text/html");
          resp.getWriter().println("<html><body>");
          resp.getWriter().println("<h2>Stored" + keyName + " and " + value +" in datastore</h2>"); //remove this line
          resp.getWriter().println("</body></html>");
      }
      else if(keyName != null && value == null){
          Key key = KeyFactory.createKey("TaskData", keyName);
          Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, key);
          Query q= new Query().setFilter(keyFilter);
          List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
          
          resp.setContentType("text/html");
          resp.getWriter().println("<html><body>");
          for(Entity e : results){
              resp.getWriter().println("<h2>" + e.getProperty("value") + " " + e.getProperty("date") +"</h2>"); //remove this line
          }
         resp.getWriter().println("</body></html>");
      }
      else if(!req.getParameterNames().hasMoreElements()){
          Query allQuery = new Query("TaskData");
          List<Entity> results = datastore.prepare(allQuery).asList(FetchOptions.Builder.withDefaults());
          resp.setContentType("text/html");
          resp.getWriter().println("<html><body>");
          for(Entity e : results){
              resp.getWriter().println("<h2>" +  e.getKey() + " "  + e.getProperty("value") +  " " + e.getProperty("date") +  "</h2>"); //remove this line
          }
         resp.getWriter().println("</body></html>");
      }
      else{
          resp.setContentType("text/html");
          resp.getWriter().println("<html><body>");
          resp.getWriter().println("<h2>Wrong usage<h2>");
          
         resp.getWriter().println("</body></html>");
      }
  }
}