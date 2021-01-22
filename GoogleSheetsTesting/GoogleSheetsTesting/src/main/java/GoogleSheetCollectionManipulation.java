import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.services.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleSheetCollectionManipulation {
    private static com.google.api.services.sheets.v4.Sheets sheetService;
    private static Spreadsheet spreadsheets;
    private static Integer spreadsheetId;
    private static GridRange range;
    private static String namedRangeId;

    public static RowData addRow() throws IOException, GeneralSecurityException{
    // sheetService = Sheets.getSheetService();
       // ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();



        return null;
    }


}
