package bg.sofia.uni.fmi.mjt.mail;

import bg.sofia.uni.fmi.mjt.mail.exceptions.*;
import bg.sofia.uni.fmi.mjt.mail.inbox.Inbox;

import java.util.Map;
import java.util.Set;

public class OutlookValidator {

    void validateAccountNameForIllegalArgumentException(String accountName) {
        if (accountName == null || accountName.isEmpty() || accountName.isBlank()) {
            throw new IllegalArgumentException("Account name cannot be null, empty or blank!");
        }
    }

    void validateEmailAddressForIllegalArgumentException(String email) {
        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new IllegalArgumentException("Email address cannot be null, empty ot blank!");
        }
    }

    void validatePathForIllegalArgumentException(String path) {
        if (path == null || path.isEmpty() || path.isBlank()) {
            throw new IllegalArgumentException("Path cannot be null, empty or blank!");
        }
    }

    void validateMailMetadataForIllegalArgumentException(String mailMetadata) {
        if (mailMetadata == null || mailMetadata.isEmpty() || mailMetadata.isBlank()) {
            throw new IllegalArgumentException("Mail metadata cannot be null, empty or blank!");
        }
    }

    void validateMailContentForIllegalArgumentException(String mailContent) {
        if (mailContent == null || mailContent.isEmpty() || mailContent.isBlank()) {
            throw new IllegalArgumentException("Mail content cannot be null, empty or blank!");
        }
    }

    void validateRuleDefinitionForIllegalArgumentException(String ruleDefinition) {
        if (ruleDefinition == null || ruleDefinition.isEmpty() || ruleDefinition.isBlank()) {
            throw new IllegalArgumentException("Rule definition cannot be null, empty or blank!");
        }
    }

    void validatePriorityForIllegalArgumentException(int priority) {
        final int highestPriority = 1;
        final int lowestPriority = 10;
        if (priority < highestPriority || priority > lowestPriority) {
            throw new IllegalArgumentException("Priority must be between 1 and 10!");
        }
    }

    void validateForExistingEmail(String email, Set<String> emailsRegister) {
        if (emailsRegister.contains(email)) {
            throw new EmailAlreadyExistsException("This email is already taken!");
        }
    }

    void validateExistingRuleAlreadyDefined(String ruleDefinition) {
        if (numberOfOccurrences(ruleDefinition, "subject-includes") >= 2) {
            throw new RuleAlreadyDefinedException("Subject-includes was already defined!");
        }
        if (numberOfOccurrences(ruleDefinition, "subject-or-body-includes") >= 2) {
            throw new RuleAlreadyDefinedException("Subject-or-body-includes was already defined!");
        }
        if (numberOfOccurrences(ruleDefinition, "recipients-includes") >= 2) {
            throw new RuleAlreadyDefinedException("Recipients-includes was already defined!");
        }
        if (numberOfOccurrences(ruleDefinition, "from") >= 2) {
            throw new RuleAlreadyDefinedException("From was already defined!");
        }
    }

    private int numberOfOccurrences(String ruleDefinition, String substring) {
        int i = 0;
        int count = 0;
        int lengthOfSubstring = substring.length();

        while ((i = ruleDefinition.indexOf(substring, i)) != -1) {
            i += lengthOfSubstring;
            count++;
        }

        return count;
    }


    void validateForAbsentAccountForAccountNotFoundException(String accountName,
                                                             Set<String> accountNamesRegister) {
        if (!accountNamesRegister.contains(accountName)) {
            throw new AccountNotFoundException("There was not found account with such name!");
        }
    }

    void validateExistingAccountForAccountAlreadyExistsException(String accountName,
                                                                 Set<String> accountNamesRegister) {
        if (accountNamesRegister.contains(accountName)) {
            throw new AccountAlreadyExistsException("There is already such account!");
        }
    }

    void validateStartingFolderForInvalidPathException(String path) {
        final int startIndex = 0;
        final int endIndex = 6;

        if (path.length() < endIndex) {
            throw new InvalidPathException("The starting folder is not /inbox!");
        }

        String firstFolderName = path.substring(startIndex, endIndex);
        String startingFolderName = "/inbox";

        if (!firstFolderName.equals(startingFolderName)) {
            throw new InvalidPathException("The starting folder is not /inbox!");
        }
    }

    void validateContainsAllSubFoldersForInvalidPathException(String accountName, String path,
                                                              Map<Account, Inbox> accountInboxRegister,
                                                              Map<String, Account> nameAndAccountRegister) {
        Inbox inbox = accountInboxRegister.get(nameAndAccountRegister.get(accountName));
        inbox.containsAllFolders(path);
    }

    void validateAlreadyContainsSuchFolder(String accountName, String path,
                                           Map<Account, Inbox> accountInboxRegister,
                                           Map<String, Account> nameAndAccountRegister) {
        Inbox inbox = accountInboxRegister.get(nameAndAccountRegister.get(accountName));
        inbox.alreadyContainsAllFolders(path);
    }

    void validateForNoSuchFolder(String accountName, String path,
                                 Map<Account, Inbox> accountInboxRegister,
                                 Map<String, Account> nameAndAccountRegister) {
        Inbox inbox = accountInboxRegister.get(nameAndAccountRegister.get(accountName));
        inbox.folderNotFound(path);
    }

}
