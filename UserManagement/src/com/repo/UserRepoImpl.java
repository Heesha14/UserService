package com.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbutil.DBConn;
import com.model.Users;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * @author Jaanvi.S.C.H IT19801100
 *
 */
public class UserRepoImpl implements UserRepo {


    private static Connection conn;
    private static final String REST_URL_PROJECTS = "http://localhost:8443/C_ProjectManagement/ProjectService/Projects";

    @Override
    public String createUser(String username, String password, String email, String phone, String gender,
                             String designation, String firstName, String lastName) {
        String sql = null;
        String output;
        try {
            conn = DBConn.getConnection();

            if (designation.equalsIgnoreCase("AD")) {
                sql = "INSERT INTO Admin(`username`,`password`,`email`,`phone`,`gender`,`designation`,`first_name`,`last_name`) " + "VALUES (?,?,?,?,?,?,?,?)";
            } else if (designation.equalsIgnoreCase("PM")) {
                sql = "INSERT INTO Project_Manager(`username`,`password`,`email`,`phone`,`gender`,`designation`,`first_name`,`last_name`) " + "VALUES (?,?,?,?,?,?,?,?)";

            } else if (designation.equalsIgnoreCase("FB")) {
                sql = "INSERT INTO Funding_Body(`username`,`password`,`email`,`phone`,`gender`,`designation`,`first_name`,`last_name`) " + "VALUES (?,?,?,?,?,?,?,?)";

            } else if (designation.equalsIgnoreCase("BY")) {
                sql = "INSERT INTO Buyer(`username`,`password`,`email`,`phone`,`gender`,`designation`,`first_name`,`last_name`) " + "VALUES (?,?,?,?,?,?,?,?)";

            } else {
                return "Designation type invalid";
            }

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, designation);
            preparedStatement.setString(7, firstName);
            preparedStatement.setString(8, lastName);

            preparedStatement.execute();

            output = "User sucessfully inserted";

            /*
             * username, password , role is stored in user table
             */
            addUsertoUserTable(username, password, designation);

            return output;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return "Error occured when inserting user to database";

        } finally {
            /*
             * database connectivity closed at the end of transaction
             */
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public String deleteUser(String userId) throws SQLException {

        String userRole = userId.substring(0, 2);
        String sql = null;
        String result;
        System.out.println(userRole);
        try {
            conn = DBConn.getConnection();

            if (userRole.equalsIgnoreCase("AD")) {
                sql = "DELETE FROM ADMIN WHERE aId = ? ";
                System.out.println(sql);
            } else if (userRole.equalsIgnoreCase("PM")) {
                sql = "DELETE FROM Project_Manager WHERE pmID = ? ";
            } else if (userRole.equalsIgnoreCase("FB")) {
                sql = "DELETE FROM Funding_Body WHERE fbId = ? ";
            } else if (userRole.equalsIgnoreCase("BY")) {
                sql = "DELETE FROM Buyer WHERE buyId = ? ";
            } else {
                return "Invalid UserId";
            }

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userId);

            preparedStatement.execute();
            /*
             * database connectivity closed at the end of transaction
             */
            conn.close();
            result = "User deleted successfully";

        } catch (Exception e) {
            result = "Error when deleting user";
            e.printStackTrace();
        }
        return result;

    }

    public void addUsertoUserTable(String username, String password, String role) {
        String sql = null;
        String output;
        try {
            conn = DBConn.getConnection();

            sql = "INSERT INTO Users(`uId`,`username`,`password`,`role`) " + "VALUES (?,?,?,?)";

            System.out.println("Queryyyyyyyyyyyyyy  " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            if (role.contains("AD"))
                preparedStatement.setString(4, "admin");
            else if (role.contains("PM"))
                preparedStatement.setString(4, "project_manager");
            else if (role.contains("FB"))
                preparedStatement.setString(4, "funding_body");
            else if (role.contains("BY"))
                preparedStatement.setString(4, "buyer");


            preparedStatement.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();

        } finally {
            /*
             * database connectivity closed at the end of transaction
             */
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public int isExistingUserName(String username) {
        String sql = null;
        int count = 0;
        try {
            conn = DBConn.getConnection();

            sql = "SELECT COUNT(*) CNT FROM Users WHERE username = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public String updateUser(String Id, String username, String password, String email, String phone, String gender, String firstName, String lastName) {
        String output = "";
        String sql = null;
        String userId = Id.substring(0, 2);
        System.out.println(userId);
        try {
            conn = DBConn.getConnection();
            if (conn == null) {
                return "Error while connecting to the database for updating.";
            }
            if (userId.contains("AD")) {
                sql = "UPDATE `Admin` SET `username`= ? ,`password`= ? ,`email`= ?,`phone`= ?,`gender`= ?," +
                        "`first_name`= ?,`last_name`= ? ,designation = 'admin' WHERE `aID` = ?";
                System.out.println(sql);
            } else if (userId.equalsIgnoreCase("PM")) {
                sql = "UPDATE `Project_Manager` SET `username`= ? ,`password`= ? ,`email`= ?,`phone`= ?,`gender`= ?," +
                        "`first_name`= ?,`last_name`= ? ,designation = 'project_manager' WHERE `pmId` = ?";
            } else if (userId.equalsIgnoreCase("FB")) {
                sql = "UPDATE `Funding_Body` SET `username`= ? ,`password`= ? ,`email`= ?,`phone`= ?,`gender`= ?," +
                        "`first_name`= ?,`last_name`= ? ,designation = 'funding_body' WHERE `fbId` = ?";
            } else if (userId.equalsIgnoreCase("BY")) {
                sql = "UPDATE `Buyer` SET `username`= ? ,`password`= ? ,`email`= ?,`phone`= ?,`gender`= ?," +
                        "`first_name`= ?,`last_name`= ? ,designation = 'buyer' WHERE `buyId` = ?";
            } else {
                return "Invalid UserId";
            }
            // create a prepared statement
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            // binding values
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, firstName);
            preparedStatement.setString(7, lastName);
            preparedStatement.setString(8, Id);
            // execute the statement
            preparedStatement.execute();
            conn.close();
            output = "Updated successfully";
        } catch (Exception e) {
            output = "Error while updating the user.";
            System.err.println(e.getMessage());
        }
        return output;
    }

    @Override
    public String getAllUsers() {
        String output;
        String sql = null;
        try {
            conn = DBConn.getConnection();
            sql = "SELECT * FROM admin \n" +
                    "UNION \n" +
                    "SELECT * FROM funding_body \n" +
                    "UNION \n" +
                    "SELECT * FROM project_manager \n" +
                    "UNION \n" +
                    "SELECT * FROM buyer\n";

            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            output = "<table border=\"1\">"
                    + "<tr><th> User ID </th>"
                    + "<th>First Name</th>"
                    + "<th>Last Name</th>"
                    + "<th>Designation</th>"
                    + "<th>Phone Number</th>"
                    + "<th>Email</th>"
                    + "<th>Gender</th>"
                    + "<th>Username</th>"
                    + "<th>Pasword</th>"
                    + "<th>Register Date</th></tr>";

            while (rs.next()) {
                String id = rs.getString("aId");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String designation = rs.getString("designation");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String registerDate = rs.getString("registerDate");

                output += "<tr><td>" + id + "</td>";
                output += "<td>" + first_name + "</td>";
                output += "<td>" + last_name + "</td>";
                output += "<td>" + designation + "</td>";
                output += "<td>" + phone + "</td>";
                output += "<td>" + email + "</td>";
                output += "<td>" + gender + "</td>";
                output += "<td>" + username + "</td>";
                output += "<td>" + password + "</td>";
                output += "<td>" + registerDate + "</td></tr>";

            }
            conn.close();
            output += "</table>";
        } catch (Exception e) {
            output = "Error while retieving the user.";
            System.err.println(e.getMessage());
        }
        return output;

    }

    @Override
    public List<Users> getUsersListPerRole(String role) {
        List<Users> userList = new ArrayList<>();
        String sql = null;
        try {
            conn = DBConn.getConnection();
            if (role.equalsIgnoreCase("admin"))
                sql = "SELECT * FROM admin";
            else if (role.equalsIgnoreCase("project_manager"))
                sql = "SELECT * FROM Buyer";
            else if (role.equalsIgnoreCase("funding_body"))
                sql = "SELECT * FROM funding_body";
            else if (role.equalsIgnoreCase("buyer"))
                sql = "SELECT * FROM Buyer";
            else
                return null;

            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Users users = new Users();
                if (role.equalsIgnoreCase("admin"))
                    users.setId(rs.getString("aId"));
                else if (role.equalsIgnoreCase("project_manager"))
                    users.setId(rs.getString("pmId"));
                else if (role.equalsIgnoreCase("funding_body"))
                    users.setId(rs.getString("fbId"));
                else if (role.equalsIgnoreCase("buyer"))
                    users.setId(rs.getString("buyId"));
                users.setFirst_name(rs.getString("first_name"));
                users.setLast_name(rs.getString("last_name"));
                users.setDesignation(rs.getString("designation"));
                users.setPhone(rs.getString("phone"));
                users.setEmail(rs.getString("email"));
                users.setGender(rs.getString("gender"));
                users.setUsername(rs.getString("username"));
                users.setPassword(rs.getString("password"));
                users.setRegisterDate(rs.getString("registerDate"));

                userList.add(users);
            }

            /*
             * database connectivity closed at the end of transaction
             */
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public Users getUserByID(String id) {
        Users users = new Users();
        String userId = id.substring(0, 2);
        String sql = null;
        try {
            conn = DBConn.getConnection();
            if (userId.contains("AD"))
                sql = "SELECT * FROM admin where aId = '" + id + "'";
            else if (userId.contains("PM"))
                sql = "SELECT * FROM Project_Manager where pmId = '" + id + "'";
            else if (userId.contains("FB"))
                sql = "SELECT * FROM Funding_Body where fbId = '" + id + "'";
            else if (userId.contains("BY"))
                sql = "SELECT * FROM Buyer where buyId = '" + id + "'";
            else
                return null;

            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (userId.contains("AD"))
                    users.setId(rs.getString("aId"));
                else if (userId.contains("PM"))
                    users.setId(rs.getString("pmId"));
                else if (userId.contains("FB"))
                    users.setId(rs.getString("fbId"));
                else if (userId.contains("BY"))
                    users.setId(rs.getString("buyId"));
                users.setFirst_name(rs.getString("first_name"));
                users.setLast_name(rs.getString("last_name"));
                users.setDesignation(rs.getString("designation"));
                users.setPhone(rs.getString("phone"));
                users.setEmail(rs.getString("email"));
                users.setGender(rs.getString("gender"));
                users.setUsername(rs.getString("username"));
                users.setPassword(rs.getString("password"));
                users.setRegisterDate(rs.getString("registerDate"));

            }

            /*
             * database connectivity closed at the end of transaction
             */
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public String getAllProject() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newBuilder().register(feature).build();
        WebTarget target = client.target(REST_URL_PROJECTS);
        Response response = target.request().get();
        String result = response.readEntity(String.class);
        response.close();
        return result;
    }
}