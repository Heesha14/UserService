package com.service;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import com.model.Users;
import com.repo.UserRepo;
import com.repo.UserRepoImpl;

/**
 * @author Jaanvi.S.C.H IT19801100
 *
 */
public class UserServiceImpl implements UserService {

    UserRepo userRepo = new UserRepoImpl();
    Users userObj = new Users();

    @Override
    public String createUser(String username, String password, String email, String phone, String gender,
                             String designation, String firstName, String lastName) {
        String validation = "";
        validation = validateInput(email, phone, gender, password, designation);

        //validates entered details
        if (validation.equalsIgnoreCase("Success")) {
            if (userRepo.isExistingUserName(username) > 0)
                return "Username is already existing";
            else
                return userRepo.createUser(username, password, email, phone, gender, designation, firstName, lastName);
        } else {
            return validation;
        }

    }


    @Override
    public String deleteUser(String userId) throws SQLException {
        userObj = userRepo.getUserByID(userId);
        System.out.println(userObj.getId());
        // if id is not available in the database
        if (userObj.getId() == null) {
            return "User is not an existing user";
        } else {
            return userRepo.deleteUser(userId);
        }
    }

    @Override
    public String updateUser(String userId, String username, String password, String email, String phone, String gender,
                             String firstName, String lastName) {
        String validation = "", designation = "AD";
        validation = validateInput(email, phone, gender, password, designation);
        // check whether the id is available in the database
        userObj = userRepo.getUserByID(userId);
        System.out.println(userObj.getId());
        // if id is not available in the database
        if (userObj.getId() == null) {
            return "User is not an existing user";
        } else {
            if (validation.equalsIgnoreCase("Success")) {
                return userRepo.updateUser(userId, username, password, email, phone, gender, firstName, lastName);
            } else {
                return validation;
            }
        }
    }

    @SuppressWarnings("unused")
    public String validateInput(String email, String mobile, String gender, String password, String designation) {
        String result = "Success";

        if (email == null || email.equals("")) {
            return "Email cannot be empty";
        } else {
            // check the validity of email
            String emailregex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                    + "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailregex);
            if (!pat.matcher(email).matches())
                return "Invalid Email";

        }
        if (mobile == null || mobile.equals("")) {
            return "Mobile number cannot be empty";
        } else {
            // check the validity of mobile number
            String mobileregex = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
            Pattern pat1 = Pattern.compile(mobileregex);

            if (!pat1.matcher(mobile).matches()) {
                return "Invalid mobile number";
            }
        }
        if (gender == null || gender.equals(""))
            return "Enter gender Male(M) or Female(F) ";
        else {
            if (!gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F"))
                return "Gender must be Male(M) or Female(F)";

        }
        if (designation == null || designation.equals(""))
            return "Enter designation Admin(AD) or Project Manager(PM) or Funding Body(FB) or Buyer(BY) ";
        else if (!designation.equalsIgnoreCase("AD") || designation.equalsIgnoreCase("PM")
                || designation.equalsIgnoreCase("FB") || designation.equalsIgnoreCase("BY"))
            return "Designation must be Admin(AD) or Project Manager(PM) or Funding Body(FB) or Buyer(BY)";

        if (password == null || password.equals(""))
            return "Password cannot be empty";
        else {
            //validates password
            String passwordregex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
            Pattern pat2 = Pattern.compile(passwordregex);
            if (!pat2.matcher(password).matches())
                return "Password should contain at least one numeric digit, one uppercase, one lowercase letter"
                        + ", one special character and must have at least 8 characters";
        }
        return result;

    }

    @Override
    public String getAllUsersInfo() {

        return userRepo.getAllUsers();
    }

    @Override
    public List<Users> getAllUsersPerRole(String role) {

        return userRepo.getUsersListPerRole(role);
    }

    @Override
    public Users getUserByID(String id) {
        return userRepo.getUserByID(id);
    }

    @Override
    public String getAllProjects() {
        return userRepo.getAllProject();
    }

}