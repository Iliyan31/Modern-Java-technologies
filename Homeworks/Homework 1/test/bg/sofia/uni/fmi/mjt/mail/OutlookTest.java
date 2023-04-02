package bg.sofia.uni.fmi.mjt.mail;

import bg.sofia.uni.fmi.mjt.mail.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OutlookTest {
    Outlook outlook = new Outlook();

    @Test
    void testAddNewAccountForAccountNameNull() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addNewAccount(null, "a"),
            "Account name cannot be null!");
    }

    @Test
    void testAddNewAccountForAccountNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addNewAccount("", "a"),
            "Account name cannot be empty!");
    }

    @Test
    void testAddNewAccountForAccountNameBlank() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addNewAccount("       ", "a"),
            "Account name cannot be blank!");
    }

    @Test
    void testAddNewAccountForEmailNull() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addNewAccount("a", null),
            "Email cannot be null!");
    }

    @Test
    void testAddNewAccountForEmailEmpty() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addNewAccount("a", ""),
            "Email cannot be empty!");
    }

    @Test
    void testAddNewAccountForEmailBlank() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addNewAccount("a", "         "),
            "Email cannot be blank!");
    }

    @Test
    void testAddNewAccountWithExistingSuch() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(AccountAlreadyExistsException.class, () -> outlook.addNewAccount("Iliyan", "iliyan@outlook.com"),
            "Cannot enter an existing account to the system!");
    }

    @Test
    void testAddNewAccountWithExistingEmail() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(EmailAlreadyExistsException.class, () -> outlook.addNewAccount("Ivan", "iliyan@outlook.com"),
            "Cannot enter an account with existing email to the system!");
    }

    @Test
    void testAddNewAccountCorrectlyReturnsAccount() {
        Account account = Account.of("iliyan@outlook.com", "Iliyan");
        assertEquals(outlook.addNewAccount("Iliyan", "iliyan@outlook.com"), account,
            "Account should be correctly created!");
    }

    @Test
    void testAddNewAccountCorrectlyReturnsForFalsePassedAccount() {
        Account account = Account.of("hi@outlook.com", "Iliyan");
        assertNotEquals(outlook.addNewAccount("Iliyan", "iliyan@outlook.com"), account,
            "Account should be correctly created!");
    }

    @Test
    void testCreateFolderForAccountNameNull() {
        assertThrows(IllegalArgumentException.class, () -> outlook.createFolder(null, "a"),
            "Account name cannot be null!");
    }

    @Test
    void testCreateFolderForAccountNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> outlook.createFolder("", "a"),
            "Account name cannot be empty!");
    }

    @Test
    void testCreateFolderForAccountNameBlank() {
        assertThrows(IllegalArgumentException.class, () -> outlook.createFolder("        ", "a"),
            "Account name cannot be blank!");
    }

    @Test
    void testCreateFolderForPathNull() {
        assertThrows(IllegalArgumentException.class, () -> outlook.createFolder("a", null),
            "Path cannot be null!");
    }

    @Test
    void testCreateFolderForPathEmpty() {
        assertThrows(IllegalArgumentException.class, () -> outlook.createFolder("a", ""),
            "Path cannot be empty!");
    }

    @Test
    void testCreateFolderForPathBlank() {
        assertThrows(IllegalArgumentException.class, () -> outlook.createFolder("a", "      "),
            "Path cannot be blank!");
    }

    @Test
    void testCreateFolderNoSuchName() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(AccountNotFoundException.class, () -> outlook.createFolder("a", "/inbox"),
            "Cannot create folder for non-existing account!");
    }

    @Test
    void testCreateFolderWrongStaringPointLessThanSixCharacters() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(InvalidPathException.class, () -> outlook.createFolder("Iliyan", "/inbo"),
            "Cannot create folder with wrong starting point!");
    }

    @Test
    void testCreateFolderWrongStaringPoint() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(InvalidPathException.class, () -> outlook.createFolder("Iliyan", "/sentMails"),
            "Cannot create folder with wrong starting point!");
    }

    @Test
    void testCreateFolderDoesNotContainAllSubFoldersWithOneMissing() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(InvalidPathException.class, () -> outlook.createFolder("Iliyan", "/inbox/one/two"),
            "Cannot create folder when its parents are not created!");
    }

    @Test
    void testCreateFolderDoesNotContainAllSubFoldersWithTwoMissing() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(InvalidPathException.class, () -> outlook.createFolder("Iliyan", "/inbox/one/two/three"),
            "Cannot create folder when its parents are not created!");
    }

    @Test
    void testCreateFolderContainsSuchFolder() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.createFolder("Iliyan", "/inbox/one");
        assertThrows(FolderAlreadyExistsException.class, () -> outlook.createFolder("Iliyan", "/inbox/one"),
            "Cannot create folder when its parents are not created!");
    }

    @Test
    void testAddRuleForAccountNameInvalidAccountName() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addRule(null, "a", "a", 1),
            "Account name cannot be null!");
    }

    @Test
    void testAddRuleForPathInvalidPathName() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addRule("a", null, "a", 1),
            "Path cannot be null!");
    }

    @Test
    void testAddRuleForRuleDefNull() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addRule("a", "a", null, 1),
            "Rule definition cannot be null!");
    }

    @Test
    void testAddRuleForRuleDefEmpty() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addRule("a", "a", "", 1),
            "Rule definition cannot be empty!");
    }

    @Test
    void testAddRuleForRuleDefBlank() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addRule("a", "a", "      ", 1),
            "Rule definition cannot be blank!");
    }

    @Test
    void testAddRuleForOutOfBoundsPriorityZero() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addRule("a", "a", "a", 0),
            "Priority cannot be zero!");
    }

    @Test
    void testAddRuleForOutOfBoundsPriorityNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addRule("a", "a", "a", -1),
            "Priority cannot be negative number!");
    }

    @Test
    void testAddRuleForOutOfBoundsPriorityGreaterThanTen() {
        assertThrows(IllegalArgumentException.class, () -> outlook.addRule("a", "a", "a", 11),
            "Priority cannot be greater than 10!");
    }

    @Test
    void testAddRuleForAbsentAccount() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(AccountNotFoundException.class, () -> outlook.addRule("a", "/inbox", "a", 7),
            "Cannot add rule to absent account!");
    }

    @Test
    void testAddRuleForRuleAlreadyDefinedSubjectIncludes() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        String ruleMetadata =
            "subject-includes: mjt, izpit, 2022" + System.lineSeparator() +
                "subject-includes: mjt, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit 2022" + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        assertThrows(RuleAlreadyDefinedException.class, () -> outlook.addRule("Iliyan", "/inbox", ruleMetadata, 7),
            "Subject-includes was already defined!");
    }

    @Test
    void testAddRuleForRuleAlreadyDefinedSubjectOrBodyIncludes() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        String ruleMetadata =
            "subject-includes: mjt, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit 2022" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        assertThrows(RuleAlreadyDefinedException.class, () -> outlook.addRule("Iliyan", "/inbox", ruleMetadata, 7),
            "Subject-or-body-includes was already defined!");
    }

    @Test
    void testAddRuleForRuleAlreadyDefinedRecipientsIncludes() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        String ruleMetadata =
            "subject-includes: mjt, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit 2022" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        assertThrows(RuleAlreadyDefinedException.class, () -> outlook.addRule("Iliyan", "/inbox", ruleMetadata, 7),
            "Recipients-includes was already defined!");
    }

    @Test
    void testAddRuleForRuleAlreadyDefinedFrom() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        String ruleMetadata =
            "subject-includes: mjt, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit 2022" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg" + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        assertThrows(RuleAlreadyDefinedException.class, () -> outlook.addRule("Iliyan", "/inbox", ruleMetadata, 7),
            "From was already defined!");
    }


    @Test
    void testAddRuleForNoSuchFolder() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");
        String ruleMetadata =
            "subject-includes: mjt, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit 2022" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";


        assertThrows(FolderNotFoundException.class, () -> outlook.addRule("Iliyan", "/inbox/three", ruleMetadata, 7),
            "Non-existing folder passed as parameter!");
    }

    @Test
    void testReceiveMailForNullAccountName() {
        assertThrows(IllegalArgumentException.class, () -> outlook.receiveMail(null, "a", "a"),
            "Account name cannot be null!");
    }

    @Test
    void testReceiveMailForNullMailMetadata() {
        assertThrows(IllegalArgumentException.class, () -> outlook.receiveMail("a", null, "a"),
            "Mail metadata cannot be null!");
    }

    @Test
    void testReceiveMailForEmptyMailMetadata() {
        assertThrows(IllegalArgumentException.class, () -> outlook.receiveMail("a", "", "a"),
            "Mail metadata cannot be empty!");
    }

    @Test
    void testReceiveMailForBlankMailMetadata() {
        assertThrows(IllegalArgumentException.class, () -> outlook.receiveMail("a", "           ", "a"),
            "Mail metadata name cannot be blank!");
    }

    @Test
    void testReceiveMailForNullMailContent() {
        assertThrows(IllegalArgumentException.class, () -> outlook.receiveMail("a", "a", null),
            "Mail content cannot be null!");
    }

    @Test
    void testReceiveMailForEmptyMailContent() {
        assertThrows(IllegalArgumentException.class, () -> outlook.receiveMail("a", "a", ""),
            "Mail content cannot be empty!");
    }

    @Test
    void testReceiveMailForBlankMailContent() {
        assertThrows(IllegalArgumentException.class, () -> outlook.receiveMail("a", "a", "          "),
            "Mail content name cannot be blank!");
    }

    @Test
    void testReceiveMailNoSuchAccount() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(AccountNotFoundException.class, () -> outlook.receiveMail("a", "a", "a"),
            "Cannot receive email for non-existing account!");
    }

    @Test
    void testGetMailsFromFolderForNullAccountName() {
        assertThrows(IllegalArgumentException.class, () -> outlook.getMailsFromFolder(null, "a"),
            "Account name cannot be null!");
    }

    @Test
    void testGetMailsFromFolderForNullFolderPath() {
        assertThrows(IllegalArgumentException.class, () -> outlook.getMailsFromFolder("a", null),
            "Account name cannot be null!");
    }

    @Test
    void testGetMailsFromFolderNoFolder() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");
        String ruleMetadata =
            "subject-includes: mjt, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit 2022" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";


        assertThrows(FolderNotFoundException.class, () -> outlook.addRule("Iliyan", "/inbox/three", ruleMetadata, 7),
            "Non-existing folder passed as parameter!");
    }

    @Test
    void testGetMailsFromFolderNoSuchAccount() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        assertThrows(AccountNotFoundException.class, () -> outlook.getMailsFromFolder("a", "a"),
            "Cannot receive emails for non-existing account!");
    }

    @Test
    void testGetMailsFromFolderNoMails() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");
        assertEquals(0, outlook.getMailsFromFolder("Iliyan", "/inbox").size(),
            "Should return 0 mails in inbox");
    }

    @Test
    void testReceiveMailWrongSenderData() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String mailMetadata =
            "sender: stoyo" + System.lineSeparator() +
                "subject: Hello, MJT for izpit!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        assertThrows(NoSuchEmailException.class, () -> outlook.receiveMail("Iliyan", mailMetadata, "Something"),
            "Throws NoSuchEmailException when senders data is wrong or non-existent in the dataset!");
    }

    @Test
    void testReceiveMailInInboxWithoutRules() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        String mailMetadata2 =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.receiveMail("Iliyan", mailMetadata, "Something");
        outlook.receiveMail("Iliyan", mailMetadata2, "Something");
        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox"));
        assertEquals(2, mails.size(),
            "Should return correctly the size of Set<Mails> from /inbox, when no rules are applied!");
    }

    @Test
    void testReceiveMailInInboxWithOneRuleMatching() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String ruleMetadata =
            "subject-includes: FMI, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, FMI for izpit 2022!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        String mailMetadata2 =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", ruleMetadata, 2);

        outlook.receiveMail("Iliyan", mailMetadata, "Something");
        outlook.receiveMail("Iliyan", mailMetadata2, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox/one/two"));
        assertEquals(1, mails.size(),
            "Should return correctly the size of Set<Mails> from pathFolder, when one rule is applied!");
    }

    @Test
    void testReceiveMailInInboxWithOneRuleMatchingNoFrom() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String ruleMetadata =
            "subject-includes: FMI, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator();

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, FMI for izpit 2022!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        String mailMetadata2 =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", ruleMetadata, 2);

        outlook.receiveMail("Iliyan", mailMetadata, "Something");
        outlook.receiveMail("Iliyan", mailMetadata2, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox/one/two"));
        assertEquals(1, mails.size(),
            "Should return correctly the size of Set<Mails> from pathFolder, when one rule is applied when no from!");
    }

    @Test
    void testReceiveMailInInboxWithOneRuleMatchingNoRecipients() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String ruleMetadata =
            "subject-includes: FMI, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit" + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, FMI for izpit 2022!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        String mailMetadata2 =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", ruleMetadata, 2);

        outlook.receiveMail("Iliyan", mailMetadata, "Something");
        outlook.receiveMail("Iliyan", mailMetadata2, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox/one/two"));
        assertEquals(1, mails.size(),
            "Should return correctly the size of Set<Mails> from pathFolder, when one rule is applied when no recipient list!");
    }

    @Test
    void testReceiveMailInInboxWithOneRuleMatchingSubjectOrBodyMissing() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String ruleMetadata =
            "subject-includes: FMI, izpit, 2022" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, FMI for izpit 2022!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        String mailMetadata2 =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", ruleMetadata, 2);

        outlook.receiveMail("Iliyan", mailMetadata, "Something");
        outlook.receiveMail("Iliyan", mailMetadata2, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox/one/two"));
        assertEquals(1, mails.size(),
            "Should return correctly the size of Set<Mails> from pathFolder, when one rule is applied when no subject or body!");
    }

    @Test
    void testReceiveMailInInboxWithOneRuleMatchingWithoutSubjectIncludes() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String ruleMetadata =
                "subject-or-body-includes: izpit" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, FMI for izpit 2022!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        String mailMetadata2 =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", ruleMetadata, 2);

        outlook.receiveMail("Iliyan", mailMetadata, "Something");
        outlook.receiveMail("Iliyan", mailMetadata2, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox/one/two"));
        assertEquals(2, mails.size(),
            "Should return correctly the size of Set<Mails> from pathFolder, when one rule is applied when no subject!");
    }

    @Test
    void testReceiveMailInInboxWithMoreRuleMatching() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String ruleMetadata =
            "subject-includes: MJT, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit 2022!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        String mailMetadata2 =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit 2022!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", ruleMetadata, 2);
        outlook.addRule("Iliyan", "/inbox/one/four", ruleMetadata, 1);

        outlook.receiveMail("Iliyan", mailMetadata, "Something");
        outlook.receiveMail("Iliyan", mailMetadata2, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox/one/four"));
        assertEquals(2, mails.size(), "Mail content name cannot be blank!");
    }

    @Test
    void testReceiveMailInInboxWithMoreRuleMatchingForDifferentFolders() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String r1 =
            "subject-includes: hi, hello" + System.lineSeparator() +
                "subject-or-body-includes: just" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello is just like hi!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", r1, 5);

        String r2 =
            "subject-includes: MJT, izpit, 2022" + System.lineSeparator() +
                "subject-or-body-includes: izpit" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata2 =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit 2022!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/four", r2, 7);

        outlook.receiveMail("Iliyan", mailMetadata, "Something");
        outlook.receiveMail("Iliyan", mailMetadata2, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox/one/four"));
        assertEquals(1, mails.size(), "Mail content name cannot be blank!");
    }

    @Test
    void testReceiveMailInInboxWithOneRuleDoesntMatch() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");
        outlook.createFolder("Iliyan", "/inbox/one/four");

        String r1 =
            "subject-includes: hi, hello" + System.lineSeparator() +
                "subject-or-body-includes: just" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello is just like hi!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", r1, 5);

        String mailMetadata2 =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello, MJT for izpit 2022!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.receiveMail("Iliyan", mailMetadata, "Something");
        outlook.receiveMail("Iliyan", mailMetadata2, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox"));
        assertEquals(1, mails.size(), "Mail content name cannot be blank!");
    }

    @Test
    void testReceiveMailInInboxSubjectIncludesDontMatch() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");

        String r1 =
            "subject-includes: hi, hello, here" + System.lineSeparator() +
                "subject-or-body-includes: just" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello is just like hi!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", r1, 5);
        outlook.receiveMail("Iliyan", mailMetadata, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox"));
        assertEquals(1, mails.size(), "Mail content name cannot be blank!");
    }

    @Test
    void testReceiveMailInInboxSubjectOrBodyIncludesDontMatch() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");

        String r1 =
            "subject-includes: hi, hello" + System.lineSeparator() +
                "subject-or-body-includes: just" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", r1, 5);
        outlook.receiveMail("Iliyan", mailMetadata, "Something");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox"));
        assertEquals(1, mails.size(), "Mail content name cannot be blank!");
    }

    @Test
    void testReceiveMailInInboxBodyIncludesMatch() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");

        String r1 =
            "subject-includes: hi, hello" + System.lineSeparator() +
                "subject-or-body-includes: just" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", r1, 5);
        outlook.receiveMail("Iliyan", mailMetadata, "just");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox/one/two"));
        assertEquals(1, mails.size(), "Mail content name cannot be blank!");
    }

    @Test
    void testReceiveMailInRecipientsDoesntIncludesMatch() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");

        String r1 =
            "subject-includes: hi, hello" + System.lineSeparator() +
                "subject-or-body-includes: just" + System.lineSeparator() +
                "recipients-includes: iliyan@outlook.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", r1, 5);
        outlook.receiveMail("Iliyan", mailMetadata, "just");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox"));
        assertEquals(1, mails.size(), "Mail content name cannot be blank!");
    }

    @Test
    void testReceiveMailFromDontMatch() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");

        String r1 =
            "subject-includes: hi, hello" + System.lineSeparator() +
                "subject-or-body-includes: just" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fm.bg";

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.addRule("Iliyan", "/inbox/one/two", r1, 5);
        outlook.receiveMail("Iliyan", mailMetadata, "just");

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox"));
        assertEquals(1, mails.size(), "Mail content name cannot be blank!");
    }

    @Test
    void testAddRuleExecNewRuleOnMailsInInbox() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("stoyo", "stoyo@fmi.bg");
        outlook.createFolder("Iliyan", "/inbox/one");
        outlook.createFolder("Iliyan", "/inbox/one/two");

        String mailMetadata =
            "sender: stoyo@fmi.bg" + System.lineSeparator() +
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients: pesho@gmail.com" + System.lineSeparator() +
                "received: 2022-12-08 16:14" + System.lineSeparator();

        String mailMetadata2 =
            "sender: iliyan@outlook.com" + System.lineSeparator() +
                "subject: For upcoming homework!" + System.lineSeparator() +
                "recipients: stoyo@fmi.bg" + System.lineSeparator() +
                "received: 2022-12-08 15:14" + System.lineSeparator();

        String mailMetadata3 =
            "sender: iliyan@outlook.com" + System.lineSeparator() +
                "subject: Homework for MJT!" + System.lineSeparator() +
                "recipients: stoyo@fmi.bg" + System.lineSeparator() +
                "received: 2022-12-08 14:14" + System.lineSeparator();

        outlook.receiveMail("Iliyan", mailMetadata, "just");
        outlook.receiveMail("Iliyan", mailMetadata2, "just");
        outlook.receiveMail("Iliyan", mailMetadata3, "just");

        String r1 =
            "subject-includes: hi, hello" + System.lineSeparator() +
                "subject-or-body-includes: just" + System.lineSeparator() +
                "recipients-includes: pesho@gmail.com, gosho@gmail.com," + System.lineSeparator() +
                "from: stoyo@fmi.bg";

        outlook.addRule("Iliyan", "/inbox/one/two", r1, 5);

        Set<Mail> mails = Set.copyOf(outlook.getMailsFromFolder("Iliyan", "/inbox/one/two"));
        assertEquals(1, mails.size(), "Mail content name cannot be blank!");
    }

    @Test
    void testSendMailForNullAccountName() {
        assertThrows(IllegalArgumentException.class, () -> outlook.sendMail(null, "a", "a"),
            "Null account name exception!");
    }

    @Test
    void testSendMailForNullMailMetadata() {
        assertThrows(IllegalArgumentException.class, () -> outlook.sendMail("a", null, "a"),
            "Null mail metadata exception!");
    }

    @Test
    void testSendMailForNullMailContent() {
        assertThrows(IllegalArgumentException.class, () -> outlook.sendMail("a", "a", null),
            "Null mail content exception!");
    }

    @Test
    void testSendMailSentToOneRecipientCorrectly() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("Ivan", "ivan@gmail.com");

        String mailMetadata =
            "sender: " + System.lineSeparator() +
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients: ivan@gmail.com, " + System.lineSeparator() +
                "received: 2022-12-08 16:14" + System.lineSeparator();

        outlook.sendMail("Iliyan", mailMetadata, "Hello!");
        assertEquals(1, outlook.getMailsFromFolder("Ivan", "/inbox").size(), "Should correctly send the mail to the recipient");
    }

    @Test
    void testSendMailSentToMoreRecipientsCorrectly() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("Ivan", "ivan@gmail.com");
        outlook.addNewAccount("Mark", "mark@gmail.com");

        String mailMetadata =
            "sender: " + System.lineSeparator() +
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients: ivan@gmail.com, mark@gmail.com, " + System.lineSeparator() +
                "received: 2022-12-08 16:14" + System.lineSeparator();

        outlook.sendMail("Iliyan", mailMetadata, "Hello!");
        assertEquals(1, outlook.getMailsFromFolder("Mark", "/inbox").size(), "Should correctly send the mail to the recipient");
    }

    @Test
    void testSendMailSentToZeroRecipientsCorrectly() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("Ivan", "ivan@gmail.com");
        outlook.addNewAccount("Mark", "mark@gmail.com");

        String mailMetadata =
            "sender: " + System.lineSeparator() +
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients: " + System.lineSeparator() +
                "received: 2022-12-08 16:14" + System.lineSeparator();

        outlook.sendMail("Iliyan", mailMetadata, "Hello!");
        assertEquals(0, outlook.getMailsFromFolder("Mark", "/inbox").size(), "Should correctly send the mail to the recipient");
    }

    @Test
    void testSendMailAddSenderCorrectlyEndsWithLineSeparator() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("Mark", "mark@gmail.com");

        String mailMetadata =
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients:mark@gmail.com, " + System.lineSeparator() +
                "received: 2022-12-08 16:14" + System.lineSeparator();

        outlook.sendMail("Iliyan", mailMetadata, "Hello!");
        List<Mail> list = List.copyOf(outlook.getMailsFromFolder("Mark", "/inbox"));
        assertEquals("iliyan@outlook.com", list.get(0).sender().emailAddress(), "Should correctly add sender to mail metadata!");
    }

    @Test
    void testSendMailAddSenderCorrectlyEndsWithoutLineSeparator() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("Mark", "mark@gmail.com");

        String mailMetadata =
            "subject: Hello like hi!" + System.lineSeparator() +
                "recipients:mark@gmail.com, " + System.lineSeparator() +
                "received: 2022-12-08 16:14";

        outlook.sendMail("Iliyan", mailMetadata, "Hello!");
        List<Mail> list = List.copyOf(outlook.getMailsFromFolder("Mark", "/inbox"));
        assertEquals("iliyan@outlook.com", list.get(0).sender().emailAddress(), "Should correctly add sender to mail metadata!");
    }

    @Test
    void testSendMailAddSenderCorrectlyWithNotFullSender() {
        outlook.addNewAccount("Iliyan", "iliyan@outlook.com");
        outlook.addNewAccount("Mark", "mark@gmail.com");

        String mailMetadata =
            "sender: " + System.lineSeparator() +
                "subject: Hello like hi!" + System.lineSeparator() +
                "recipients: mark@gmail.com, " + System.lineSeparator() +
                "received: 2022-12-08 16:14" + System.lineSeparator();

        outlook.sendMail("Iliyan", mailMetadata, "Hello!");
        List<Mail> list = List.copyOf(outlook.getMailsFromFolder("Mark", "/inbox"));
        assertEquals("iliyan@outlook.com", list.get(0).sender().emailAddress(), "Should correctly add sender to mail metadata!");
    }


}
