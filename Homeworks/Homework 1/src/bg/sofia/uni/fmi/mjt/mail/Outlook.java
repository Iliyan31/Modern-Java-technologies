package bg.sofia.uni.fmi.mjt.mail;

import bg.sofia.uni.fmi.mjt.mail.exceptions.NoSuchEmailException;
import bg.sofia.uni.fmi.mjt.mail.inbox.Inbox;
import bg.sofia.uni.fmi.mjt.mail.rules.Rule;
import bg.sofia.uni.fmi.mjt.mail.rules.RuleComparator;
import bg.sofia.uni.fmi.mjt.mail.rules.RulePriority;
import bg.sofia.uni.fmi.mjt.mail.sent.Sent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Outlook extends OutlookValidator implements MailClient {

    /**
     * This outlookAccountNames is used for quick search for when we need to search for existing account
     */
    private final Set<String> accountNamesRegister;
    private final Set<String> emailsRegister;
    private final Map<String, Account> nameAndAccountRegister;
    private final Map<Account, Inbox> accountInboxRegister;
    private final Map<Account, Sent> accountSentRegister;
    private final Map<Account, PriorityQueue<Rule>> rulesForAccount;
    private final Set<Rule> uniqueRules;

    public Outlook() {
        this.accountNamesRegister = new HashSet<>();
        this.accountInboxRegister = new HashMap<>();
        this.nameAndAccountRegister = new HashMap<>();
        this.rulesForAccount = new HashMap<>();
        this.emailsRegister = new HashSet<>();
        this.uniqueRules = new HashSet<>();
        this.accountSentRegister = new HashMap<>();
    }

    @Override
    public Account addNewAccount(String accountName, String email) {
        validateAccountNameForIllegalArgumentException(accountName);
        validateEmailAddressForIllegalArgumentException(email);
        validateExistingAccountForAccountAlreadyExistsException(accountName, accountNamesRegister);
        validateForExistingEmail(email, emailsRegister);

        Account account = Account.of(email, accountName);
        RuleComparator ruleComparator = new RuleComparator();

        emailsRegister.add(email);
        accountNamesRegister.add(accountName);
        nameAndAccountRegister.put(accountName, account);
        accountInboxRegister.put(account, new Inbox());
        accountSentRegister.put(account, new Sent());
        rulesForAccount.put(account, new PriorityQueue<>(ruleComparator));

        return account;
    }

    @Override
    public void createFolder(String accountName, String path) {
        validateAccountNameForIllegalArgumentException(accountName);
        validatePathForIllegalArgumentException(path);
        validateForAbsentAccountForAccountNotFoundException(accountName, accountNamesRegister);
        validateStartingFolderForInvalidPathException(path);
        validateContainsAllSubFoldersForInvalidPathException(accountName, path, accountInboxRegister,
            nameAndAccountRegister);
        validateAlreadyContainsSuchFolder(accountName, path, accountInboxRegister, nameAndAccountRegister);

        Inbox inbox = accountInboxRegister.get(nameAndAccountRegister.get(accountName));
        inbox.addFolder(path);
    }

    @Override
    public void addRule(String accountName, String folderPath, String ruleDefinition, int priority) {
        validateAccountNameForIllegalArgumentException(accountName);
        validatePathForIllegalArgumentException(folderPath);
        validateRuleDefinitionForIllegalArgumentException(ruleDefinition);
        validatePriorityForIllegalArgumentException(priority);
        validateForAbsentAccountForAccountNotFoundException(accountName, accountNamesRegister);
        validateExistingRuleAlreadyDefined(ruleDefinition);
        validateForNoSuchFolder(accountName, folderPath, accountInboxRegister, nameAndAccountRegister);

        Rule rule = createRuleByGivenData(ruleDefinition, priority, folderPath);

        if (uniqueRules.contains(rule)) {
            return;
        }

        Inbox inbox = accountInboxRegister.get(nameAndAccountRegister.get(accountName));
        execRuleOnAllMailsInInbox(rule, inbox);

        uniqueRules.add(rule);
        PriorityQueue<Rule> rules = rulesForAccount.get(nameAndAccountRegister.get(accountName));
        rules.add(rule);
    }

    @Override
    public void receiveMail(String accountName, String mailMetadata, String mailContent) {
        validateAccountNameForIllegalArgumentException(accountName);
        validateMailMetadataForIllegalArgumentException(mailMetadata);
        validateMailContentForIllegalArgumentException(mailContent);
        validateForAbsentAccountForAccountNotFoundException(accountName, accountNamesRegister);

        Mail mail = createMailByGivenData(mailMetadata, mailContent);
        Account account = this.nameAndAccountRegister.get(accountName);

        Inbox inbox = accountInboxRegister.get(nameAndAccountRegister.get(accountName));
        String path = getPathFromMatchedRule(account, mail);
        inbox.addMailToFolder(path, mail);
    }

    @Override
    public Collection<Mail> getMailsFromFolder(String account, String folderPath) {
        validateAccountNameForIllegalArgumentException(account);
        validatePathForIllegalArgumentException(folderPath);
        validateForAbsentAccountForAccountNotFoundException(account, accountNamesRegister);
        validateForNoSuchFolder(account, folderPath, accountInboxRegister, nameAndAccountRegister);

        Inbox inbox = accountInboxRegister.get(nameAndAccountRegister.get(account));
        return inbox.getMailsFromFolder(folderPath);
    }

    @Override
    public void sendMail(String accountName, String mailMetadata, String mailContent) {
        validateAccountNameForIllegalArgumentException(accountName);
        validateMailMetadataForIllegalArgumentException(mailMetadata);
        validateMailContentForIllegalArgumentException(mailContent);

        String email = nameAndAccountRegister.get(accountName).emailAddress();
        mailMetadata = addSenderToMailMetadata(email, mailMetadata);

        Mail mail = createMailByGivenData(mailMetadata, mailContent);

        Sent sentMails = accountSentRegister.get(nameAndAccountRegister.get(accountName));
        sentMails.addMail(mail);

        sendMailToAllRecipients(mail, mailMetadata, mailContent);
    }

    private Mail createMailByGivenData(String mailMetadata, String mailContent) {
        String senderName = "";
        String subject = "";
        String recipients = "";
        String date = "";
        Account account = new Account("", "");
        LocalDateTime dateTime = LocalDateTime.of(0, 1, 1, 1, 0);


        boolean containsSender = mailMetadata.contains("sender");
        boolean containsSubject = mailMetadata.contains("subject");
        boolean containsRecipients = mailMetadata.contains("recipients");
        boolean containsReceived = mailMetadata.contains("received");

        String[] lines = mailMetadata.split(System.lineSeparator());
        List<String> metadata = new ArrayList<>();

        for (String line : lines) {
            metadata.addAll(Arrays.asList(line.split(":", 2)));
        }

        for (int i = 0; i < metadata.size() - 1; i++) {
            if (containsSender && metadata.get(i).equals("sender")) {
                senderName = metadata.get(i + 1).strip();
                account = getAccountByEmail(senderName);
            }
            if (containsSubject && metadata.get(i).equals("subject")) {
                subject = metadata.get(i + 1).strip();
            }
            if (containsRecipients && metadata.get(i).equals("recipients")) {
                recipients = metadata.get(i + 1).strip().replaceAll("\\s", "");
            }
            if (containsReceived && metadata.get(i).equals("received")) {
                date = metadata.get(i + 1).strip();
                dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }
        }

        return Mail.of(account, Set.of(recipients.split(",")), subject, mailContent, dateTime);
    }

    private Account getAccountByEmail(String email) {
        Set<Account> accounts = Set.copyOf(nameAndAccountRegister.values());
        for (Account account : accounts) {
            if (account.emailAddress().equals(email)) {
                return account;
            }
        }

        throw new NoSuchEmailException("There is no such email!");
    }

    private Rule createRuleByGivenData(String ruleDefinition, int priority, String folderPath) {
        Set<String> subjectIncludes = new HashSet<>();
        Set<String> subjectOrBodyIncludes = new HashSet<>();
        Set<String> recipientsIncludes = new HashSet<>();
        String sender = "";

        boolean containsSubjectIncludes = ruleDefinition.contains("subject-includes");
        boolean containsSubjectOrBodyIncludes = ruleDefinition.contains("subject-or-body-includes");
        boolean containsRecipientsIncludes = ruleDefinition.contains("recipients-includes");
        boolean containsFrom = ruleDefinition.contains("from");

        String[] lines = ruleDefinition.split(System.lineSeparator());
        List<String> ruleData = new ArrayList<>();

        for (String line : lines) {
            ruleData.addAll(Arrays.asList(line.split(":")));
        }

        for (int i = 0; i < ruleData.size() - 1; i++) {
            if (containsSubjectIncludes && ruleData.get(i).equals("subject-includes")) {
                subjectIncludes = Set.of(ruleData.get(i + 1).strip().replaceAll("\\s", "").split(","));
            }
            if (containsSubjectOrBodyIncludes && ruleData.get(i).equals("subject-or-body-includes")) {
                subjectOrBodyIncludes = Set.of(ruleData.get(i + 1).strip().replaceAll("\\s", "").split(","));
            }
            if (containsRecipientsIncludes && ruleData.get(i).equals("recipients-includes")) {
                recipientsIncludes = Set.of(ruleData.get(i + 1).strip().replaceAll("\\s", "").split(","));
            }
            if (containsFrom && ruleData.get(i).equals("from")) {
                sender = ruleData.get(i + 1).strip();
            }
        }

        return new Rule(subjectIncludes, subjectOrBodyIncludes, recipientsIncludes, sender,
            RulePriority.values()[priority - 1], folderPath);
    }

    private String getPathFromMatchedRule(Account account, Mail mail) {
        for (Rule rule : this.rulesForAccount.get(account)) {
            if (matchRule(rule, mail)) {
                return rule.folderPath();
            }
        }

        return "/inbox";
    }

    private boolean matchRule(Rule rule, Mail mail) {
        boolean matchesRule = true;
        if (!matchSubject(rule.subjectIncludes(), mail.subject())) {
            matchesRule = false;
        }
        if (!matchSubjectOrBody(rule.subjectOrBodyIncludes(), mail.subject(), mail.body())) {
            matchesRule = false;
        }
        if (!rule.recipientsIncludes().isEmpty() &&
            !matchRecipients(rule.recipientsIncludes(), mail.recipients())) {
            matchesRule = false;
        }
        if (!rule.from().isEmpty() && !rule.from().equals(mail.sender().emailAddress())) {
            matchesRule = false;
        }
        return matchesRule;
    }

    private boolean matchSubject(Set<String> subjectIncludes, String subject) {
        boolean matchesSubject = true;
        for (String subjectInSet : subjectIncludes) {
            if (subject != null && !subject.isEmpty() && !subject.isBlank()) {
                if (!subject.toLowerCase().contains(subjectInSet.toLowerCase())) {
                    matchesSubject = false;
                    break;
                }
            }
        }

        return matchesSubject;
    }

    private boolean matchSubjectOrBody(Set<String> subjectOrBodyIncludes, String subject, String body) {
        return matchSubject(subjectOrBodyIncludes, subject) | matchSubject(subjectOrBodyIncludes, body);
    }

    private boolean matchRecipients(Set<String> recipientsIncludes, Set<String> recipients) {
        return !Collections.disjoint(recipientsIncludes, recipients);
    }

    private void execRuleOnAllMailsInInbox(Rule rule, Inbox inbox) {
        Set<Mail> mails = inbox.getFolder("/inbox").getMails();
        for (Mail mail : mails) {
            if (matchRule(rule, mail)) {
                inbox.addMailToFolder(rule.folderPath(), mail);
            }
        }
    }

    private String addSenderToMailMetadata(String email, String mailMetadata) {
        if (!mailMetadata.contains("sender")) {
            if (mailMetadata.endsWith(System.lineSeparator())) {
                return (mailMetadata += "sender: " + email + System.lineSeparator());

            } else {
                return (mailMetadata += System.lineSeparator() + "sender: " + email + System.lineSeparator());
            }
        }

        String[] lines = mailMetadata.split(System.lineSeparator());

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("sender")) {
                lines[i] = "sender: " + email;
            }
        }

        StringBuilder newMailMetadata = new StringBuilder();
        for (String line : lines) {
            newMailMetadata.append(line).append(System.lineSeparator());
        }

        return newMailMetadata.toString();
    }

    private void sendMailToAllRecipients(Mail mail, String mailMetadata, String mailContent) {
        for (String recipientEmail : mail.recipients()) {
            if (recipientEmail != null && !recipientEmail.isEmpty() && !recipientEmail.isBlank()) {
                String recipientName = getAccountByEmail(recipientEmail).name();
                receiveMail(recipientName, mailMetadata, mailContent);
            }
        }
    }

}
