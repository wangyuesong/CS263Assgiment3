
package cs263w16;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**  
 * @Project: base
 * @Title: Worker.java
 * @Package cs263w16
 * @Description: TODO
 * @author YuesongWang
 * @date Jan 18, 2016 6:52:39 PM
 * @version V1.0  
 */
public class Worker extends HttpServlet {
    
    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        Entity taskData = new Entity("TaskData",key);
        
        taskData.setProperty("value", value);
        taskData.setProperty("date", new Date().toString());
        datastore.put(taskData);
        
        syncCache.put(taskData.getKey().getName(), taskData);
        // Do something with key.
    }
}