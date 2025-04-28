import view.LoginView;
import view.ReceptionDashboard;
import util.Session;

public class Main {
    public static void main(String[] args) {
        // If a user is already logged in, show the dashboard
        if (Session.isUserLoggedIn()) {
            new ReceptionDashboard(); // Show Reception Dashboard
        } else {
            new LoginView(); // Show Login View
        }
    }
}
