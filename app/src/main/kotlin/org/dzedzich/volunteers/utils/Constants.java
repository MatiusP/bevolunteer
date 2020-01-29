package org.dzedzich.volunteers.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by aleksejskrobot on 22.03.17.
 */

public final class Constants {

    public static final String OKHTTP_CACHE = "okhttp_cache";
    public static final String LOGTAG = "psyonic_log_post";
    public static final long CACHE_SIZE = 10 * 1000 * 1000; //10MB
    public static final String TEST_RESULT_INSTANCE = "psy_test";
    public static final String TEST_RESULT_SCORE = "score";
    public static final String EMPTY = "";
    public static final String ADD_NEW_ACCOUNT = "add-new";
    public static final String PREF_FILE_NAME = "shared_preferences";

    public static class LOCALIZATION {
        public static final String RUSSIA = "RU";
        public static final String BELARUS = "BY";

        public static class ISO {
            public static final String RUSSIA = "ru_RU";
            public static final String BELARUS = "be_rBY";
        }
    }

    public static class VIEWS {

    }

    public static class ROUTER {
        public static class MAIN {
            public static final String FEED = "feed";
            public static final String ACCOUNT = "account";
            public static final String TASKS = "tasks";
            public static final String RATING = "rating";
        }
    }

    public static final class HEADERS {
        public static final String TOKEN = "X-APPLICATION-AUTH-TOKEN";
        public static final String REQUEST_FROM_MOBILE_PLATFORM = "REQUEST-FROM-MOBILE-PLATFORM";
    }

    public static final class API {
        public static final String URL = "https://haipclick.com/";
    }

    public static class ACCOUNTS {
        public static final String VK = "vkontakte";
        public static final String TW = "twitter";
        public static final String INST = "instagram";
    }

    public static class FORM {
        public static class AUTH {
            public static final String PHONE = "phone";
            public static final String FIRST_NAME = "firstname";
            public static final String LAST_NAME = "lastname";
        }
    }

    public static class BUNDLES {
        public static final String FROM_CONFIRM = "from_registration_confirm";
        public static final String COMPANY_ID = "company_id";
        public static final String COMPANY_POSITION = "company_position";
        public static final String USER_ID = "user_id";
        public static final String USER_POSITION = "user_position";
        public static final String TASK_ID = "task_id";
        public static final String TOKEN = "token";
    }

    public static class REMOTE_CONFIG {
        public static final String BACK_URL = "back_url";
    }

    public static class COMPANY_VOTING_ID {
        public static final String ATMOSPHERE = "atmosphere";
        public static final String CONFORMITY = "conformity";
        public static final String ORGANIZATION = "organization_level";
    }

    public static class SETTINGS {
        public static final String PUSH = "push";
        public static final String VIBRATE = "vibrate";
    }
}
