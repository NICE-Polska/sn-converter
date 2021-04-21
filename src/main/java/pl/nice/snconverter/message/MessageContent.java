package pl.nice.snconverter.message;

public class MessageContent {

    public static final String OK = "Ok";
    public static final String ERROR = "Error";
    public static final String PAGE_IN_URL = "?page=";
    public static final String ITEMS = "Items";
    public static final String EX_LAST_PAGE = "No more pages";
    public static final String EX_PAGE_NUM_TO_HIGH = "Page number you are trying obtain too high. Last page number is ";
    public static final String EX_NO_ITEMS_IN_TABLE = "No items";
    public static final String EX_NO_RECORDS_FOUND = "No results for query";
    
    /*--------------Common Validation------------------*/
    public static final String VALID_NOT_NULL = "Cannot be null";
    public static final String VALID_NOT_BLANK = "Cannot be blank";
    public static final String VALID_MAX_SIZE = "Size cannot be larger than ";
    public static final String VALID_EMAIL = "Not valid email address";
    public static final String VALID_FIELD_VALID = "[Valid] field ";
    public static final String VALID_UNIQUE = "Must be unique";

    /*---------------- User -----------------*/
    public static final String USER_NOT_FOUND = "No user found for id ";
    public static final String USER_DELETED = "Successfully deleted user on id ";
    public static final String USER_LOGIN_SUCCESS = "Successfully login user with email ";
    public static final String USER_NO_VALID_CREDENTIAL = "Bad password or login for email ";
    public static final String USER_ACCESS_FORBIDDEN = "Access forbidden";
    public static final String USER_LOGOUT = "Successfully logout";

    /*---------------- Customer -----------------*/
    public static final String CUSTOMER_NOT_FOUND = "No customer found for id ";
    public static final String CUSTOMER_DELETED = "Successfully deleted customer on id ";
    public static final String CUSTOMER_UPDATED = "Successfully updated customer on id ";

    /*---------------- Device -----------------*/
    public static final String DEVICE_NOT_FOUND = "No device found for id ";
    public static final String DEVICE_DELETED = "Successfully deleted device on id ";
    public static final String DEVICE_UPDATED = "Successfully updated device on id ";
    public static final String DEVICE_SN_NOT_FOUND = "No device found for serial number ";

    /*---------------- Media -----------------*/
    public static final String MEDIA_NOT_FOUND = "No media found for id ";
    public static final String MEDIA_DELETED = "Successfully deleted media on id ";
    public static final String MEDIA_UPLOADED = "File has been successfully uploaded as ";
    public static final String MEDIA_NO_FILE = "There is no file";
    public static final String MEDIA_BAD_FILE = "Invalid file type sent";
    public static final String MEDIA_ACCEPTED_FILE ="Accepted file types: ";

    /*---------------- Serial Number -----------------*/
    public static final String SERIAL_NUMBER = "serialNumber";
}
