import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoogleSheetEnvironment {

    private static com.google.api.services.sheets.v4.Sheets sheetService;
    private static String SPREADSHEET_ID = "1H8DJXyWboFrJqmm9P51LXG40jHg1OdxQVF_zK6I0A6s";

    /**Creation of a spreadsheet**/
public static Spreadsheet createSpreadsheet() throws IOException{
    Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle("Testing RPA Spreadsheet"));
    Spreadsheet result  = sheetService.spreadsheets().create(spreadsheet).execute();
    // check with assert if needed. result can be used any where in main class to create sheet.
    return result;

}


    /****
       * retrive multiple range **/

public static BatchUpdateValuesResponse multipleRange() throws IOException {

    List<ValueRange> data = new ArrayList<ValueRange>();
    data.add(new ValueRange().setRange("D1").setValues(Arrays.<List<Object>>asList(new List[]{Arrays.asList("Jan total", "=B2+B3")})));
    data.add(new ValueRange().setRange("D4").setValues(Arrays.asList(Arrays.asList())));

    BatchUpdateValuesRequest batchBody  = new BatchUpdateValuesRequest().setValueInputOption("User Entered").setData(data);
    BatchUpdateValuesResponse batchResult = sheetService.spreadsheets().values().batchUpdate(SPREADSHEET_ID, batchBody).execute();
    return batchResult;
}



}
