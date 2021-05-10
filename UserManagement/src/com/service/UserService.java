package com.service;

import java.sql.SQLException;
import java.util.List;

import com.model.Users;

/**
 * @author Jaanvi.S.C.H IT19801100
 *
 */
public interface UserService {

	/**
	 * Inserts new user
	 *
	 * @param username
	 * @param password
	 * @param email
	 * @param phone
	 * @param gender
	 * @param designation
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public String createUser(String username, String password, String email, String phone, String gender,
			String designation,String firstName,String lastName);

	/**
	 * Delete user respectively
	 *
	 * @return the deleted message.
	 * @throws SQLException
	 */
	public String deleteUser(String userId) throws SQLException;

	/**
	 * Upates details of an existing user
	 *
	 * @param userId
	 * @param username
	 * @param password
	 * @param email
	 * @param phone
	 * @param gender
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public String updateUser(String userId, String username, String password, String email, String phone, String gender,
			String firstName, String lastName);

	/**
	 * Gets all user details
	 *
	 * @return the user details.
	 */
	public String getAllUsersInfo();

	/**
	 * Gets all user details from per role
	 *
	 * @param role
	 * @return the user details.
	 */
	public List<Users> getAllUsersPerRole(String role);

	/**
	 * Gets all user details per id
	 *
	 * @param id
	 * @return the user details.
	 */
	public Users getUserByID(String id);

	/**
	 * Intersecrvice communication
	 * Gets all projects available
	 *
	 * @return the projects.
	 */
	public String getAllProjects();
}
