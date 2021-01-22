import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sheets {

    private static com.google.api.services.sheets.v4.Sheets sheetService;
    private static String APPLICATION_NAME = "My Project";
    private static String SPREADSHEET_ID = "1H8DJXyWboFrJqmm9P51LXG40jHg1OdxQVF_zK6I0A6s";

    private static Credential aurthorize() throws IOException, GeneralSecurityException {
        InputStream in = Sheets.class.getResourceAsStream("/credentials.json");
        GoogleClientSecrets  clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),clientSecrets,scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");
        return credential;

    }

    public static com.google.api.services.sheets.v4.Sheets getSheetService() throws IOException, GeneralSecurityException{
        Credential credential = aurthorize();
        return new com.google.api.services.sheets.v4.Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),JacksonFactory.getDefaultInstance(),credential)
                .setApplicationName(APPLICATION_NAME).build();
    }

    public static AppendValuesResponse appendBody() throws IOException, GeneralSecurityException{
        sheetService = getSheetService();
        ValueRange appendBody = new ValueRange().setValues(Arrays.<List<Object>>asList(Arrays.<Object>asList("This","was","added","from","code")));// test data
        AppendValuesResponse appendResult = sheetService.spreadsheets().values().append(SPREADSHEET_ID,"Mednet Feature Matrix", appendBody)
                .setValueInputOption("USER_ENTERED").setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(Boolean.TRUE).execute();
        return appendResult;

    }

    public static UpdateValuesResponse updateBody() throws IOException, GeneralSecurityException{
        sheetService = getSheetService();
        ValueRange appendBody = new ValueRange().setValues(Arrays.<List<Object>>asList(Arrays.<Object>asList("updated")));// test data
        UpdateValuesResponse updatedResponse = sheetService.spreadsheets().values().update(SPREADSHEET_ID, "C543",appendBody)
                .setValueInputOption("RAW").execute();
        return updatedResponse;

    }

    public static DeleteDimensionRequest DeleteBody() throws IOException, GeneralSecurityException{
        // sheet value found from sheet URL is 405637568
        sheetService = getSheetService();
        DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
                .setRange(new DimensionRange().setSheetId(405637568)
                .setDimension("ROWS")
                .setStartIndex(542));

        List<Request> requests = new ArrayList<Request>();
        requests.add(new Request().setDeleteDimension(deleteRequest));
        BatchUpdateSpreadsheetRequest body  = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        sheetService.spreadsheets().batchUpdate(SPREADSHEET_ID, body).execute();

        return deleteRequest;

    }

    public static com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.BatchGet getRangeValue() throws IOException, GeneralSecurityException{
        sheetService = getSheetService();
        String range = "Mednet Feature Matrix!A2:F10";
        ValueRange response = sheetService.spreadsheets().values().get(SPREADSHEET_ID, range).execute();

        List<List<Object>> values = response.getValues();

        if(values==null || values.isEmpty()){
            System.out.println("No data found");

        }else{
            for (List row : values){
                System.out.printf("%s %s from %s\n", row.get(5), row.get(4), row.get(1));

            }
        }

        return null;
    }




    public static void main(String args[]) throws IOException, GeneralSecurityException{
//        sheetService = getSheetService();
//        String range = "Mednet Feature Matrix!A2:F10";
//        ValueRange response = sheetService.spreadsheets().values().get(SPREADSHEET_ID, range).execute();
//
//        List<List<Object>> values = response.getValues();
//
//        if(values==null || values.isEmpty()){
//            System.out.println("No data found");
//
//        }else{
//            for (List row : values){
//                System.out.printf("%s %s from %s\n", row.get(5), row.get(4), row.get(1));
//
//            }
//        }
    }
}
