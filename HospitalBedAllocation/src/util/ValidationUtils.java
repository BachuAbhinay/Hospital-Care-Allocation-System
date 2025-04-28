package util;

public class ValidationUtils {

    // Check if a string is not empty or null
    public static boolean isValid(String str) {
        return str != null && !str.trim().isEmpty();
    }

    // Check if an integer is greater than zero
    public static boolean isValidAge(int age) {
        return age > 0;
    }

    // Validate phone number format (simple check, can be enhanced)
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }
}
