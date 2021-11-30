package com.highpeaksw.utils.constants;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;

import com.highpeaksw.utils.exception.DataException;

public class GeneralConstants {

    public static final String EXCEPTION = "Exception";
    public static final String INVALID_STATUS = "Invalid Status";
    public static final String AUTHORIZATION = "Authorization";
    public static final String ERROR = "Error ,";
    public static final String ERROR_STORING_SECRET = "Error occurred while saving secret in vault";
    public static final String ERROR_FETCHING_SECRET = "Error occurred while fetching secret from vault";
    public static final String ERROR_FETCHING_GOLD_PRICE = "Something wrong in while fetching current gold price";
    public static final String ERROR_RENEWING_VAULT_TOKEN = "Error on renewing vault token";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String NULL_TOKEN = "Auth Token is not present";
    public static final String ERROR_FETCHING_PAYMENT_DETAILS = "Error in fetching payment details";
    public static final String SHIFT_NOT_AUTHORIZED = "Loan officer can not create shift request";
    public static final String NULL_INPUT_ERROR = "Please provide all mandatory fields";
    public static final String USER_NOT_PRESENT = "User not present";
    public static final String CONSTRUCTOR_CREATION_ERROR = "You do not have access to create a instance of this class";
    public static final String INVALID_EMAIL_FORMAT = "Invalid Email address format";
    public static final Integer MAX_NAME_LENGTH = 225;
    public static final Integer MAX_MOBILE_NUMBER_LENGTH = 20;
    public static final Integer PAN_NUMBER_LENGTH = 10;
    public static final Integer AADHAR_NUMBER_LENGTH = 14;
    public static final Integer DRIVING_LICENSE_NUMBER_LENGTH = 16;
    public static final String INVALID_CREDENTIALS = "Invalid combination of email address and password";
    public static final String USER_NOT_REGISTERED = "User not registered";

    public static final String ROLE_CUSTOMER = "Customer";
    public static final String ROLE_LO = "LO";
    public static final String EMPTY_STRING = "";
    public static final String SINGLE_SPACE_STRING = " ";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number";
    public static final String LOAN_OFFICER_NOT_FOUND = "Loan officer for the id not found";
    public static final String LOAN_APPLICATION_NOT_FOUND = "Loan application for the id not found";
    public static final String LOAN_APPLICATION_FOR_ID_NOT_FOUND = "No loan application found for id {}";
    public static final String DIGIFI_ORGANISATION_NOT_FOUND = "Digifi organisation not found";
    public static final String CUSTOMER_APPLICATION_NOT_FOUND = "Customer application for the id not found";
    public static final String SHIFT_REQUEST_NOT_FOUND = "Shift request for the id not found";
    public static final String SLOT_NOT_FOUND = "Slot not found";
    public static final String ERROR_FETCHING_NOTES = "Error while fetching notes of an application";
    public static final String FILE_NOT_FOUND = "File not found";
    public static final String ADDRESS_MISSING = "Address not found";
    public static final String CUSTOMER_DETAILS_MISSING = "Customer details missing";
    public static final String INVALID_APPLICATION_STATUS = "Invalid loan application status";
    public static final String SLOT_NOT_AVAILABLE = "Selected home visit slot not available";
    public static final String LOAN_PRODUCT_NOT_AVAILABLE = "Selected loan product not available";
    public static final String MISSING_LOAN_ID = "Loan id is missing";
    public static final String UNAUTHORIZED_LOAN_ACCESS_BY_LO = "You do not have access to this loan application. Please call your manager";
    public static final String UNAUTHORIZED_RETURN_REQUEST_ACCESS_BY_LO = "You do not have access to this gold return request. Please call your manager";
    public static final String LOAN_STATUS_NOT_FOUND = "Selected loan status not found";
    public static final String LOAN_STATUS_NOT_UPDATED = "Status update not found";
    public static final String LOAN_SLOT_NOT_FOUND = "Selected loan slot not found";
    public static final String PAYMENT_MODE_NOT_FOUND = "Selected payment mode not found";
    public static final String MISSING_LOAN_PROD_ID = "Missing Loan product id";
    public static final String MISSING_SLOT_ID = "Missing slot id";
    public static final String MISSING_CUSTOMER_ID = "Missing customer id";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String UNABLE_TO_SAVE_KYC = "Unable to save the KYC at the moment";
    public static final String PHONE_NUMBER_MISSING = "Phone number missing";
    public static final String DOB_MISSING = "Date of birth missing";
    public static final String LENDING_PARTNER_NOT_FOUND = "Lending partner not found";
    public static final String GENDER_MISSING = "Gender value is missing";
    public static final String EMPLOYEE_ID_MISSING = "EmployeeId is missing";
    public static final String INVALID_MULTIPART_DATA = "Invalid files uploaded";
    public static final String API_BASE_SUCCESS_RETURN_MESSAGE = "success";
    public static final String NA = "Not Available";
    public static final String LINE_BREAK = "\n";
    public static final String EMAIL_ERROR = "Something wrong in sending the email";
    public static final String INVALID_LOAN_ID = "Invalid Loan Id";
    public static final String INVALID_DIGIFI_TOKEN = "Invalid auth token";
    public static final String PERCENTAGE = "percentage";
    public static final String DATA_TYPE_NUMBER = "number";
    public static final String DATA_TYPE_TEXT = "text";
    public static final BigDecimal FIVE_LAKHS = new BigDecimal("500000");
    public static final String CUSTOMER_NAME_MISSING = "Customer name is missing";
    public static final String APARTMENT_NAME_MISSING = "Apartment name is missing";
    public static final String STREET_NAME_MISSING = "Street name is missing";
    public static final String LAT_MISSING = "Latitude is missing";
    public static final String LONG_MISSING = "Longitude is missing";
    public static final String SOMETHING_WENT_WRONG_WHILE_FETCHING_UPDATED_LOAN_ENTITY = "Something went wrong while fetching the updated loan application";
    public static final String MISSING_DIGIFI_LOAN_ID = "Need the digifi application id";
    public static final String OTP_NOT_FOUND = "Invalid OTP";
    public static final String OTP_EXPIRED = "OTP Expired";

    public static final int LOAN_TENURE_DAYS = 180;

    public static final String STATUS_ID_MISSING = "Need the status to be updated to";

    public static final String CHARACTERS = " characters";
    public static final String APOSTROPHE = "'";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String REQUEST_BODY_NULL = "Request body id invalid";
    public static final String LEAD_NAME_IS_MISSING = "Lead phone number is missing";
    public static final String LEAD_PHONE_NUMBER_IS_MISSING = "Lead phone number is missing";
    public static final String LEAD_SOURCE_IS_MISSING = "Lead source is missing";
    public static final String INVALID_SLOT_ID = "Invalid slot";
    public static final String AUTH_TOKEN_MISSING = "Auth token missing";
    public static final String CUSTOMER_APPLICATION_ID_MISSING = "Customer Application request id is missing";
    public static final String FCM_TOKEN_MISSING = "FCM token is missing";
    public static final String MAC_ADDRESS_MISSING = "MAC address of the device missing";
    public static final String NULL_FCM_TOKEN = "FCM token is not valid";
    public static final String UNDERSCORE = "_";
    public static final String ADMIN_USER_NOT_FOUND = "Admin user not found";

    public static final String RETURN_REQUEST_ID_MISSING = "Request id is missing";

    public static final Integer MAX_ACCOUNT_NUMBER_SIZE = 25;
    public static final Integer MIN_ACCOUNT_NUMBER_SIZE = 5;

    public static final Integer MAX_ACCOUNT_NAME_SIZE = 70;
    public static final Integer MIN_ACCOUNT_NAME_SIZE = 5;
    public static final int IFSC_LENGTH = 11;
    public static final int MAX_IMPS_TRANSFER_LIMIT = 5;

    public static final String MISSING_LOAN_AMOUNT = "Loan amount is missing";
    public static final String OTP_GENERATED_LOGGER = "OTP generated : {}";
    public static final String MISSING_LOCATION = "Location code is missing";
    public static final String USER_NAME_MISSING = "Username to get access token is missing";
    public static final String APPLICATION_ID_MISSING = "Application Id  is missing";

    private GeneralConstants() throws DataException
    {
        throw new DataException(GeneralConstants.EXCEPTION, "Not allowed to create an instance",
                HttpStatus.BAD_REQUEST);
    }
}
