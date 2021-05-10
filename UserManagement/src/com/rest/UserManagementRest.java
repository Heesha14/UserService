package com.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.Users;
import com.service.UserService;
import com.service.UserServiceImpl;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

/**
 * Rest controller for user service
 *
 * @author Jaanvi.S.C.H IT19801100
 */
@Path("/Users")
public class UserManagementRest {

    UserService userService = new UserServiceImpl();

    @RolesAllowed({"admin"})
	@GET
	@Path("All")
	@Produces(MediaType.TEXT_HTML)
	public String readAllUsers() {

        return userService.getAllUsersInfo();
	}


    @RolesAllowed({"admin","project_manager","funding_body","buyer"})
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String createUser(@FormParam("username") String username, @FormParam("password") String password,
                             @FormParam("email") String email, @FormParam("phone") String phone, @FormParam("gender") String gender,
                             @FormParam("designation") String designation,@FormParam("firstName") String firstName,
                             @FormParam("lastName")  String lastName) {
        return userService.createUser(username, password, email, phone, gender, designation,firstName,lastName);
    }

    @RolesAllowed({"admin"})
    @GET
    @Path("/{key}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Users> getAllUsersPerRole(@PathParam("key") String role) {
        List<Users> userResponse = null;
            userResponse = userService.getAllUsersPerRole(role);
			return userResponse;
    }

    @RolesAllowed({"admin","project_manager","funding_body","buyer"})
    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(@FormParam("userId") String userId) throws SQLException {
        return userService.deleteUser(userId);
    }

    @RolesAllowed({"admin","project_manager","funding_body","buyer"})
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUser(String request) {
        // Convert the input string to a JSON object
        JsonObject userObj = new JsonParser().parse(request).getAsJsonObject();
        // Read the values from the JSON object
        String aId = userObj.get("userId").getAsString();
        String username = userObj.get("username").getAsString();
        String password = userObj.get("password").getAsString();
        String email = userObj.get("email").getAsString();
        String phone = userObj.get("phone").getAsString();
        String gender = userObj.get("gender").getAsString();
        String first_name = userObj.get("first_name").getAsString();
        String last_name = userObj.get("last_name").getAsString();

        return userService.updateUser(aId, username, password, email, phone, gender, first_name, last_name);
    }

    @RolesAllowed({"admin"})
    @GET
    @Path("/getUserById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserById(@PathParam("id") String id) {
        // users displayed in JSON format
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(userService.getUserByID(id));
    }

    @RolesAllowed({"admin"})
    @GET
    @Path("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkAdmin() {
        return true;
    }


    @RolesAllowed({"admin"})
    @GET
    @Path("viewProjects")
    @Produces(MediaType.TEXT_HTML)
    public String getAllProducts() {
        return userService.getAllProjects();
    }


}
