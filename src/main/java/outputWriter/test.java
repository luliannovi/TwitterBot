package outputWriter;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

public class test {
    public static void main(String[] args) {
        JavaWriter jvwr=new JavaWriter(new String[]{"Impianto di analisi" , "Testi", "Output"});

        jvwr.setNumberOfColumns(5);
        jvwr.setColumnsName(new String[]{"LINK", "TESTI", "PORZIONI DI TESTO" , "REPERTORI" , "CONTENUTI"});
        jvwr.setNumberOfRows(20);
        jvwr.setHeaderBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        jvwr.setHeaderFillPatternType(FillPatternType.SOLID_FOREGROUND);
        jvwr.setHeaderAlignment(HorizontalAlignment.CENTER);

        jvwr.headerSettings();
        jvwr.dataCellsSettings();
        jvwr.fillWithData(new String[][] {  {"Uno" , "Due" , "Tre" ," quattro" , "cinque"} ,
                                            { "1" , "2" , "3", "5" , "4"}  });
        jvwr.writeInFile("prova");

    }
}
