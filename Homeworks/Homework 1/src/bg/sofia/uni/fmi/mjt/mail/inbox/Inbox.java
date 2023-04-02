package bg.sofia.uni.fmi.mjt.mail.inbox;

import bg.sofia.uni.fmi.mjt.mail.Mail;
import bg.sofia.uni.fmi.mjt.mail.exceptions.FolderAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.mail.exceptions.FolderNotFoundException;
import bg.sofia.uni.fmi.mjt.mail.exceptions.InvalidPathException;
import bg.sofia.uni.fmi.mjt.mail.folder.Folder;

import java.util.Set;

public class Inbox {
    private final Folder inbox;

    public Inbox() {
        this.inbox = new Folder("inbox");
    }

    public void addFolder(String path) {
        if (path.equals("/inbox")) {
            return;
        }

        String lastFolderName = path.substring(path.lastIndexOf("/")).replace("/", "");

        Folder parenFolder = getFolder(getParentFolderPath(path));

        parenFolder.addSubFolder(new Folder(lastFolderName));
    }

    public void addMailToFolder(String path, Mail mail) {
        getFolder(path).addMail(mail);
    }

    public Set<Mail> getMailsFromFolder(String path) {
        return getFolder(path).getMails();
    }

    public Folder getFolder(String path) {
        int indexOfFirstSlash = 1;
        String[] folderNames = path.substring(indexOfFirstSlash).split("/");

        Folder parenFolder = inbox;
        for (int i = 1; i < folderNames.length; i++) {
            Folder currentFolder = new Folder();
            for (Folder j : parenFolder.getSubFolders()) {
                if (folderNames[i].equals(j.getName())) {
                    currentFolder = j;
                    break;
                }
            }
            parenFolder = currentFolder;
        }

        return parenFolder;
    }
    public void containsAllFolders(String path) {
        if (getFolder(getParentFolderPath(path)).getName().isEmpty()) {
            throw new InvalidPathException("Invalid path entered!");
        }
    }

    public void alreadyContainsAllFolders(String path) {
        if (!getFolder(path).getName().isEmpty()) {
            throw new FolderAlreadyExistsException("There is already such folder!");
        }
    }

    public void folderNotFound(String path) {
        if (getFolder(path).getName().equals("")) {
            throw new FolderNotFoundException("There is no such folder!");
        }
    }

    private String getParentFolderPath(String path) {
        String lastFilePathToTrim = path.substring(path.lastIndexOf("/"));
        return path.replace(lastFilePathToTrim, "");
    }

}
