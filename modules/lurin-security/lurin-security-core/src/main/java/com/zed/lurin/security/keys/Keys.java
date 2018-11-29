package com.zed.lurin.security.keys;

public enum Keys {
    KEY_SESSION_TIMEOUT_INACTIVE{
        @Override
        public String toString() {
            return "SESSION.TIMEOUT.INACTIVE";
        }
    },
    TOKEN{
        @Override
        public String toString() {
            return "TOKEN";
        }
    },
    QUARTZ_INTERVAL_TIME{
        @Override
        public String toString() {
            return "QUARTZ.INTERVAL.TIME";
        }
    },
     QUARTZ_INTERVAL_TIME_PASS{
        @Override
        public String toString() {
            return "QUARTZ.INTERVAL.TIME_PASS";
        }
    },
    AUTHENTICATION_SCHEME{
        @Override
        public String toString() {
            return "Bearer";
        }
    },
    JPA_DYNAMIC_NAME{
        @Override
        public String toString() {
            return "JPA_DYNAMIC_NAME";
        }
    },
    MENU{
        @Override
        public String toString() {
            return "MENU";
        }
    },
    I18N_APP{
        @Override
        public String toString() {
            return "I18N_APP";
        }
    },
    CARRIES_BY_USER{
        @Override
        public String toString() {
            return "CARRIES_BY_USER";
        }
    },
    LDAP_HOSTNAME {
        @Override
        public String toString() {
            return "LDAP.HOSTNAME";
        }
    },
    LDAP_PORT {
        @Override
        public String toString() {
            return "LDAP.PORT";
        }
    },
    LDAP_SECURITY_AUTHENTICATION {
        @Override
        public String toString() {
            return "LDAP.SECURITY.AUTHENTICATION";
        }
    },
    CARRIER_HEADER{
        @Override
        public String toString() {
            return "Carrier";
        }
    },
    USER_EXPIRED_PASSWORD{
        @Override
        public String toString() {
            return "USER.EXPIRED.PASSWORD";
        }
    },
    SMTP_SERVER{
        @Override
        public String toString() {
            return "SMTP.SERVER";
        }
    },
    EMAIL_USER{
        @Override
        public String toString() {
            return "EMAIL.USER";
        }
    },
    EMAIL_USER_PASS{
        @Override
        public String toString() {
            return "EMAIL.USER.PASS";
        }
    },
    SMTP_PORT{
        @Override
        public String toString() {
            return "SMTP.PORT";
        }
    },
    SSL_EMAIL_PORT{
        @Override
        public String toString() {
            return "SSL.EMAIL.PORT";
        }
    },
    REGISTRATION_MAIL_SUBJECT{
        @Override
        public String toString() {
            return "REGISTRATION.MAIL.SUBJECT";
        }
    },
    REGISTRATION_MAIL_BODY{
        @Override
        public String toString() {
            return "REGISTRATION.MAIL.BODY";
        }
    },
    UPLOAD_URL_FILE{
        @Override
        public String toString() {
            return "UPLOAD.URL.FILE";
        }
    },
    THEME{
        @Override
        public String toString() {
            return "THEME";
        }
    },
    URL_REST_SERVICES{
        @Override
        public String toString() {
            return "URL_REST_SERVICES";
        }
    },
    CODE_ACTIVE_STATUS{
        @Override
        public String toString() {
            return "CODE_ACTIVE_STATUS";
        }
    },
    WILDFLY_JBOSS_CLI_HOST {
        @Override
        public String toString() {
            return "WILDFLY.JBOSS.CLI.HOST";
        }
    },
    WILDFLY_JBOSS_CLI_PORT {
        @Override
        public String toString() {
            return "WILDFLY.JBOSS.CLI.PORT";
        }
    },
    WILDFLY_JBOSS_CLI_JNDI_PREFIX{
        @Override
        public String toString() {
            return "WILDFLY.JBOSS.CLI.JNDI.PREFIX";
        }
    },
    WILDFLY_JBOSS_CLI_IS_SECURED{
        @Override
        public String toString() {
            return "WILDFLY.JBOSS.CLI.IS.SECURED";
        }
    },
    WILDFLY_JBOSS_CLI_USERNAME{
        @Override
        public String toString() {
            return "WILDFLY.JBOSS.CLI.USERNAME";
        }
    },
    WILDFLY_JBOSS_CLI_PASSWORD{
        @Override
        public String toString() {
            return "WILDFLY.JBOSS.CLI.PASSWORD";
        }
    },
    UPLOAD_URL_IMG{
        @Override
        public String toString() {
            return "UPLOAD.URL.IMG";
        }
    },
    COMPANY_ID{
        @Override
        public String toString() {
            return "COMPANY_ID";
        }
    },
    GLOBAL_PARAMETERS_CATEGORY{
    	@Override
        public String toString() {
            return "APP_GLOBAL";
        }
    },
    POLITIC_PARAMETERS_CATEGORY{
    	@Override
        public String toString() {
            return "POLITICS_PARAMETER";
        }
    },
    QUARTZ_PARAMETERS_CATEGORY{
    	@Override
        public String toString() {
            return "QUARTZ";
        }
    },
    LDAP_SECURITY_COMMON_NAME {
        @Override
        public String toString() {
            return "LDAP.SECURITY.COMMON.NAME";
        }
    },
    LDAP_SECURITY_DOMAIN {
        @Override
        public String toString() {
            return "LDAP.SECURITY.DOMAIN";
        }
    },
    LDAP_SECURITY_USERNAME {
        @Override
        public String toString() {
            return "LDAP.SECURITY.USERNAME";
        }
    },
    LDAP_SECURITY_PASSWORD {
        @Override
        public String toString() {
            return "LDAP.SECURITY.PASSWORD";
        }
    },
    LDAP_SECURITY_ATTR_IDS {
        @Override
        public String toString() {
            return "LDAP.SECURITY.ATTR.IDS";
        }
    },
    LDAP_SECURITY_SEARCH_FILTER {
        @Override
        public String toString() {
            return "LDAP.SECURITY.SEARCH.FILTER";
        }
    },
    RECOVERPASSWORD_MAIL_BODY {
    	@Override
        public String toString() {
            return "RECOVER.PASSWORD.MAIL.BODY";
        }
    },
    CAS_NUMBER_MAX_ROWS_FILES{
        @Override
        public String toString() {
            return "CAS.NUMBER.MAX.ROWS.FILES";
        }
    },
    VALIDATE_CAMPAIGN_FINALIZED{
        @Override
        public String toString() {
            return "VALIDATE.CAMPAIGN.FINALIZED";
        }
    },
    FEE_SENDING_COMMANDS_PER_SECOND{
        @Override
        public String toString() {
            return "FEE.SENDING.COMMANDS.PER.SECOND";
        }
    },
    LDAP_SECURITY_BASE_DOMAIN {
        @Override
        public String toString() {
            return "LDAP.SECURITY.BASE.DOMAIN";
        }
    }

}
