import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;

class SheetsServiceUtil {
    private static final String APPLICATION_NAME = "Google Sheets Example";

    public static   String getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = GoogleAuthorizeUtil.authorize();
        return Sheets.getSheetService().spreadsheets().toString();

    }
}