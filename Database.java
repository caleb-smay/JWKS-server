import java.io.*;
import java.util.*;

public class Database {
    public Database() {
    }

    public void AddEntry(String user, String mail) {
        Entry entry = new Entry(user, mail);
        Entries.add(entry);
    }

    public boolean EntryExists(String mail) {
        if (Entries.contains(mail)) {
            return true;
        } else {
            return false;
        }
    }

    private HashSet<Entry> Entries = new HashSet<Entry>(20);
}

class Entry {
    public Entry(String user, String mail) {
        username = user;
        email = mail;
    }
    public String username;
    public String email;
}

