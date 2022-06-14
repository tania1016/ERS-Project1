package com.revature.services;

	import java.util.Optional;

	import javax.servlet.http.HttpSession;

	import com.revature.models.User;

	public class SessionHelper {

		/**
		 * This is an example method signature for retrieving the currently logged-in
		 * user. It leverages the Optional type which is a useful interface to handle
		 * the possibility of a user being unavailable.
		 */
		public static Optional<User> retrieveCurrentUser(HttpSession session) {
			User user = null;

			if (session != null) {
				user = new User();

				String username = (String) session.getAttribute("username");
				String email = (String) session.getAttribute("email");
				Integer id = (Integer) session.getAttribute("id");

				user.setUsername(username);
				user.setEmail(email);
				user.setId(id);
			}
			return Optional.of(user);
		}

	}

