package cs263w16;

import java.util.Date;

import javax.management.RuntimeErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

public class TaskDataResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String keyname;

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public TaskDataResource(UriInfo uriInfo, Request request, String kname) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.keyname = kname;
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public TaskData getTaskDataHTML() {
        // add your code here (get Entity from datastore using this.keyname)
        // throw new RuntimeException("Get: TaskData with " + keyname + " not found");
        // if not found
        try {
            Entity e = datastore.get(KeyFactory.createKey("TaskData", this.keyname));
            return new TaskData(e.getKey().getName(), (String)e.getProperty("value"), (Date)e.getProperty("date"));
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Get Taskdata with: " + this.keyname + " not found");
        }

    }

    // for the application
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public TaskData getTaskData() {
        // same code as above method
        return getTaskDataHTML();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response putTaskData(String val) {
        Response res = null;
        // add your code here
        // first check if the Entity exists in the datastore
        // if it is not, create it and
        // signal that we created the entity in the datastore
        res = Response.created(uriInfo.getAbsolutePath()).build();
        // else signal that we updated the entity
        res = Response.noContent().build();

        return res;
    }

    @DELETE
    public void deleteIt() {

        // delete an entity from the datastore
        // just print a message upon exception (don't throw)
    }
}
