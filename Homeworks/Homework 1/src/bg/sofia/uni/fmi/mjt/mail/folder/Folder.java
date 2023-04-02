package bg.sofia.uni.fmi.mjt.mail.folder;

import bg.sofia.uni.fmi.mjt.mail.Mail;

import java.util.*;

public class Folder {
    private String name;
    private final List<Folder> subFolders;
    private final Set<Mail> mails;

    public Folder() {
        this.name = new String();
        this.subFolders = new ArrayList<>();
        this.mails = new HashSet<>();
    }

    public Folder(String name) {
        this();

        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Folder name cannot be null, empty or blank!");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public Set<Mail> getMails() {
        return mails;
    }

    public void addMail(Mail mailToAdd) {
        mails.add(mailToAdd);
    }

    public void addMails(Set<Mail> mailsToAdd) {
        mails.addAll(mailsToAdd);
    }

    public void setSubFolders(List<Folder> subFoldersToAdd) {
        subFolders.addAll(subFoldersToAdd);
    }

    public void addSubFolder(Folder subFolder) {
        subFolders.add(subFolder);
    }

}
