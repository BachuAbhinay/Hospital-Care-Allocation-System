package util;

import model.User;

public class Session {
    private static User currentUser;

    // Store the current user in the session
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // Get the current user from the session
    public static User getCurrentUser() {
        return currentUser;
    }

    // Check if a user is logged in
    public static boolean isUserLoggedIn() {
        return currentUser != null;
    }

    // Log the user out
    public static void logOut() {
        currentUser = null;
    }
}
