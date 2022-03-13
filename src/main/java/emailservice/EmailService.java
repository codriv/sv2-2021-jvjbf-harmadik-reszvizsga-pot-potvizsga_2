package emailservice;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class EmailService {

    private Set<User> users = new LinkedHashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void registerUser(String emailAddress) {
        if (!emailAddress.matches("[^.@]+@[^.@]+\\.[^@.]+") || !emailAddress.equals(emailAddress.toLowerCase())) {
            throw new IllegalArgumentException("Email address is not valid: " + emailAddress);
        } else if (users.stream().filter(user -> emailAddress.equals(user.getEmailAddress())).toList().size() > 0) {
            throw new IllegalArgumentException("Email address is already taken!");
        } else {
            users.add(new User(emailAddress));
        }
    }

    public void sendEmail(String from, String to, String subject, String content) {
        User userFrom = users.stream().filter(user -> from.equals(user.getEmailAddress())).toList().get(0);
        User userTo = users.stream().filter(user -> to.equals(user.getEmailAddress())).toList().get(0);
        userFrom.sendEmail(subject, content, userTo);
    }

    public void sendSpam(String subject, String content) {
        users.forEach(user -> user.getEmail(new Spam(user, subject, content)));
    }
}
