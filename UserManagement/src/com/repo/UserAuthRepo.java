package com.repo;

import com.dbutil.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

/**
 * to check user allowed to access resource
 *
 * @author Jaanvi.S.C.H IT19801100
 */
public class UserAuthRepo {

    private static Connection conn;

    public static boolean isUserAllowed(String username, String password, Set<String> rolesSet) {
        boolean isAllowed = false;
        String role = "", user = "", psw = "";
        String sql = null;

        try {
            conn = DBConn.getConnection();

            // create a prepared statement
            sql = "SELECT * FROM users where username='" + username + "' and password='" + password + "'";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.execute();
            //execute the statement
            ResultSet rs = preparedStatement.executeQuery(sql);

            if (rs.next()) {
                user = rs.getString("username");
                psw = rs.getString("password");
                role = rs.getString("role");

            }
            conn.close();


        } catch (Exception e) {

            System.err.println(e.getMessage());
        }

        if (username.equals(user) && password.equals(psw)) {

            //Step 2. Verify user role
            if (rolesSet.contains(role)) {
                isAllowed = true;
                System.out.println(username + " " + password);
            }
        }
        return isAllowed;

    }
}
