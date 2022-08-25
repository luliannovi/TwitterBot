package outputWriter;

public class test {
    public static void main(String[] args) {
        JavaWriter jvwr=new JavaWriter("FOGLIO 1");
        jvwr.setNumberOfColumns(3);
        jvwr.setColumnsName(new String[]{"COLONNA 1", "COLONNA 2", "COLONNA 3"});
        jvwr.setNumberOfRows(2);
        jvwr.headerSettings();
        jvwr.dataCellsSettings();
        jvwr.fillWithData(new String[][] {  {"Uno" , "Due" , "Tre"} ,
                                            { "1" , "2" , "3"}  });
        jvwr.writeInFile("prova");

    }
}
