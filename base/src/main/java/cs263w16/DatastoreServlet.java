package cs263w16;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.memcache.*;

@SuppressWarnings("serial")
public class DatastoreServlet extends HttpServlet {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String keyName = req.getParameter("keyname");
        String value = req.getParameter("value");
        if (keyName != null && value != null) {
            Entity entity = new Entity("TaskData", keyName);
            entity.setProperty("value", value);
            entity.setProperty("date", new Date());
            datastore.put(entity);
            
            syncCache.put(entity.getKey().getName(), entity);
            
            resp.setContentType("text/html");
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<h2>Stored" + keyName + " and " + value + " in datastore and memcache</h2>"); // remove this
            resp.getWriter().println("</body></html>");
        }
        //Get one key, memcache version
        else if (keyName != null && value == null) {
            // Check memcache
            resp.setContentType("text/html");
            resp.getWriter().println("<html><body>");

            // In memcache, has to be in datastore
            Entity e = (Entity) syncCache.get(keyName);
            if (e != null) {
                resp.getWriter().println("<h2> Both </h2>"); // remove this line
                resp.getWriter().println("<h2> " + e.getProperty("value") + " " + e.getProperty("date") + "</h2>");
            }
            // Not in memcache
            else {
                Key key = KeyFactory.createKey("TaskData", keyName);
                Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, key);
                Query q = new Query().setFilter(keyFilter);
                List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
                // Not in datastore
                if (results.isEmpty()) {
                    resp.getWriter().println("<h2> None </h2>"); // remove this line
                }
                // In datastore
                else {
                    resp.getWriter().println("<h2> Datastore </h2>"); // remove this line
                    for (Entity entity : results) {
                        syncCache.put(entity.getKey().getName(), entity);
                        resp.getWriter().println(
                                "<h2>" + entity.getProperty("value") + " " + entity.getProperty("date") + "</h2>"); 
                    }
                }
            }
            resp.getWriter().println("</body></html>");
        }
        // List all ,Memcache version
        else if (!req.getParameterNames().hasMoreElements()) {
            Query allQuery = new Query("TaskData");
            List<Entity> results = datastore.prepare(allQuery).asList(FetchOptions.Builder.withDefaults());
            resp.setContentType("text/html");
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<h1>Datastore Storage:</h1>");
            List<String> keyNames = new LinkedList<String>();
            for (Entity e : results) {
                resp.getWriter().println(
                        "<h2>" + e.getKey().getName() + " " + e.getProperty("value") + " " + e.getProperty("date") + "</h2>");
                keyNames.add(e.getKey().getName());
            }
            resp.getWriter().println("<h1>Memcache Storage:</h1>");

            syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
            Map<String, Object> memcacheResult = syncCache.getAll(keyNames);
            for (String key : memcacheResult.keySet()) {
                Entity e = (Entity) memcacheResult.get(key);
                resp.getWriter().println(
                        "<h2>" + e.getKey().getName() + " " + e.getProperty("value") + " " + e.getProperty("date") + "</h2>");
            }

            resp.getWriter().println("</body></html>");
        }
        else {
            resp.setContentType("text/html");
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<h2>Wrong usage<h2>");
            resp.getWriter().println("</body></html>");
        }
    }
}