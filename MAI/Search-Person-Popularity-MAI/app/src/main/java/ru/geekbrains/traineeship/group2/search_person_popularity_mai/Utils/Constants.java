package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils;

/**
 * Created by skubatko on 04/11/17
 */

public class Constants {

    public static final String API_URL_BASE = "http://94.130.27.143/";
//    public static final String API_URL_BASE = "http://shourick.pythonanywhere.com/";

    // Имя файла базы данных
    public static final String MAIN_DATABASE_NAME = "searchPersonPopularity.db";
    public static final String SYNCHRONIZED_DATABASE_NAME = "searchPPSynced.db";

    // Версия базы данных. При изменении схемы увеличить на единицу
    public static final int DATABASE_VERSION = 3;

    public static final String TABLE_PERSONS = "persons";
    public static final String TABLE_KEYWORDS = "keywords";
    public static final String TABLE_SITES = "sites";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ADMINS = "admins";

    public static final String KEY_ID = "_id";

    public static final String TABLE_PERSONS_FIELD_NAME = "name";

    public static final String TABLE_KEYWORDS_FIELD_NAME = "name";
    public static final String TABLE_KEYWORDS_FIELD_PERSON_ID = "person_id";

    public static final String TABLE_SITES_FIELD_NAME = "name";

    public static final String TABLE_USERS_FIELD_NICKNAME = "user_nickname";
    public static final String TABLE_USERS_FIELD_LOGIN = "login";
    public static final String TABLE_USERS_FIELD_PASSWORD = "password";

    public static final String TABLE_ADMINS_FIELD_NICKNAME = "admin_nickname";
    public static final String TABLE_ADMINS_FIELD_LOGIN = "login";
    public static final String TABLE_ADMINS_FIELD_PASSWORD = "password";

    public static final int REQUEST_CODE_ADD_PERSON = 1;
    public static final int REQUEST_CODE_EDIT_PERSON = 2;

    public static final int REQUEST_CODE_ADD_KEYWORD = 1;
    public static final int REQUEST_CODE_EDIT_KEYWORD = 2;

    public static final int REQUEST_CODE_ADD_SITE = 1;
    public static final int REQUEST_CODE_EDIT_SITE = 2;

    public static final int REQUEST_CODE_ADD_USER = 1;
    public static final int REQUEST_CODE_EDIT_USER = 2;

    public static final int REQUEST_CODE_ADD_ADMIN = 1;
    public static final int REQUEST_CODE_EDIT_ADMIN = 2;

    public static final String PERSON_ID = "site_id";
    public static final String PERSON_NAME = "site_name";

    public static final String KEYWORD_ID = "keyword_id";
    public static final String KEYWORD_NAME = "keyword_name";
    public static final String PERSON_FOR_KEYWORD_ID = "person_for_keyword_id";

    public static final String SITE_ID = "site_id";
    public static final String SITE_NAME = "site_name";

    public static final String USER_ID = "user_id";
    public static final String USER_NICKNAME = "user_nickname";
    public static final String USER_LOGIN = "user_login";
    public static final String USER_PASSWORD = "user_password";

    public static final String ADMIN_ID = "admin_id";
    public static final String ADMIN_NICKNAME = "admin_nickname";
    public static final String ADMIN_LOGIN = "admin_login";
    public static final String ADMIN_PASSWORD = "admin_password";

    public static final String LOGIN_PREFERENCES = "login_data";
    public static final String PREFERENCES_AUTHORIZED_KEY = "isAuthorized";

    public static final int MAX_OF_ADMIN_AUTHORIZATION_TRIES = 3;

    public static final String MESSAGE_SYNCRONIZING = "Идет синхронизация ...";

    public static final String EMPTY_NAME = "";
    public static final int EMPTY_ID = -1;

    public static final int UPDATE_OK = 0;

    public static final int ITEM_NOT_SELECTED = -1;
}
