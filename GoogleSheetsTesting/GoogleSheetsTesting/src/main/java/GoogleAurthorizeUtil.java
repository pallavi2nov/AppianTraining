import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

class GoogleAuthorizeUtil {
    public static Credential authorize() throws IOException, GeneralSecurityException {

        // build GoogleClientSecrets from JSON file

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        // build Credential object: already existing in Sheets method.
        return authorize();
    }
}