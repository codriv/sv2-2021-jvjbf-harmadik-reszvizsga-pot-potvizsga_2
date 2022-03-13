package emailservice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class User {

    private String emailAddress;
    private List<Email> incoming = new ArrayList<>();
    private List<Email> sent = new ArrayList<>();
    private boolean hasSpamFilter;

    public User(String email) {
        this.emailAddress = email;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<Email> getIncoming() {
        return incoming.stream().sorted(Comparator.comparing(Email::isImportant).reversed()).toList();
    }

    public List<Email> getSent() {
        return sent;
    }

    public boolean hasSpamFilter() {
        return hasSpamFilter;
    }

    public void spamFilterChange() {
        hasSpamFilter = !hasSpamFilter;
    }

    public void getEmail(Email email) {
        if (email.getSubject().toLowerCase().contains("spam") && hasSpamFilter) {
            throw new IllegalStateException("Be careful, tis is a spam!");
        } else {
            incoming.add(email);
        }
    }

    public void sendEmail(String subject, String content, User to) {
        Email email = new Normal(this, to, subject, content);
        this.sent.add(email);
        to.getEmail(email);
    }
}
