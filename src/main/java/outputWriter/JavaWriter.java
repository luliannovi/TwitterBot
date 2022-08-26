package outputWriter;

import BBS.TweetForExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class JavaWriter {
    private short headerBackgroundColor = IndexedColors.GREEN.getIndex();
    private HorizontalAlignment headerAlignment = HorizontalAlignment.CENTER;
    private FillPatternType headerFillPatternType = FillPatternType.SOLID_FOREGROUND;
    private String headerFontName = "Arial";
    private short headerFontColor = IndexedColors.BLACK.getIndex();
    private short headerFontHeight = 16;
    private boolean headerFontBold = true;

    private String[][] arrayData;
    private XSSFWorkbook mainWorkbook;
    private XSSFSheet mainSheet;
    private Row header;
    private CellStyle headerStyle;
    private XSSFFont headerFont;
    private Cell headerCell;
    private int numberOfColumns=1;
    private int[] columnsWidth = null;
    private String[] columnsName = null;
    private CellStyle dataCellStyle;
    private Cell dataCells[][];
    private int numberOfRows=2;
    private int rowsHeight;
    private Row[] rows;
    //CONSTRUCTORS

    public JavaWriter(String[] sheets) {
        this.mainWorkbook = new XSSFWorkbook();
        if(sheets.length > 0) {
            for(String name : sheets)
                this.mainWorkbook.createSheet(name);
        }
        this.mainSheet = this.mainWorkbook.getSheet("Testi");
        this.header=mainSheet.createRow(0);
        this.headerStyle=mainWorkbook.createCellStyle();
        this.headerFont=mainWorkbook.createFont();

    }

    public JavaWriter(){
        this.mainWorkbook=new XSSFWorkbook();
        this.mainSheet = this.mainWorkbook.createSheet("sheet_1");
        this.header=mainSheet.createRow(0);
        this.headerStyle=mainWorkbook.createCellStyle();
        this.headerFont=mainWorkbook.createFont();
    }

    //GETTER AND SETTER

    public XSSFSheet getMainSheet() {
        return mainSheet;
    }

    public Row getHeader() {
        return header;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int[] getColumnsWidth() {
        return columnsWidth;
    }

    public void setColumnsWidth(int[] columnsWidth) {
        this.columnsWidth = columnsWidth;
    }

    public String[] getColumnsName() {
        return columnsName;
    }

    public void setColumnsName(String[] columnsName) {
        this.columnsName = columnsName;
    }

    public String[][] getArrayData() {
        return arrayData;
    }

    public void setArrayData(String[][] arrayData) {
        this.arrayData = arrayData;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void setHeaderBackgroundColor(short headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
    }

    public void setHeaderAlignment(HorizontalAlignment headerAlignment) {
        this.headerAlignment = headerAlignment;
    }

    public void setHeaderFillPatternType(FillPatternType headerFillPatternType) {
        this.headerFillPatternType = headerFillPatternType;
    }

    public void setHeaderFontName(String headerFontName) {
        this.headerFontName = headerFontName;
    }

    public void setHeaderFontColor(short headerFontColor) {
        this.headerFontColor = headerFontColor;
    }

    public void setHeaderFontHeight(short headerFontHeight) {
        this.headerFontHeight = headerFontHeight;
    }

    public void setHeaderFontBold(boolean headerFontBold) {
        this.headerFontBold = headerFontBold;
    }

    //WORKBOOK SETTINGS METHODS

    public void headerSettings(){
        if(this.columnsName == null)
            this.columnsName=new String[this.numberOfColumns];
        if(this.columnsWidth == null)
            this.columnsWidth= new int[this.numberOfColumns];
        this.headerStyle.setFillForegroundColor(headerBackgroundColor);
        this.headerStyle.setFillPattern(headerFillPatternType);
        this.headerStyle.setAlignment(headerAlignment);
        this.headerFont.setFontName(headerFontName);
        this.headerFont.setColor(headerFontColor);
        this.headerFont.setFontHeightInPoints(headerFontHeight);
        this.headerFont.setBold(headerFontBold);
        this.headerStyle.setFont(this.headerFont);
        for(int i=0;i<this.numberOfColumns;i++){
            headerCell=this.header.createCell(i);
            headerCell.setCellValue(this.columnsName[i]);
            headerCell.setCellStyle(headerStyle);
            mainSheet.setColumnWidth(1,15000);
        }
    }


    public void dataCellsSettings(){
        this.dataCellStyle = mainWorkbook.createCellStyle();
        this.dataCellStyle.setWrapText(true);
        this.rows=new Row[this.numberOfRows];
        this.dataCells=new Cell[this.numberOfRows][this.numberOfColumns];
        for(int i=0;i<this.numberOfRows;i++){
            rows[i] = mainSheet.createRow(i+1);
            for(int k=0;k<this.numberOfColumns;k++){
                this.dataCells[i][k]=rows[i].createCell(k);
                this.dataCells[i][k].setCellStyle(this.dataCellStyle);
            }
        }
    }
    public void fillWithData(String[][] data){
        this.arrayData=data;
        for(int i=0;i<this.arrayData.length;i++){
            for(int k=0;k<this.arrayData[i].length;k++){
                this.dataCells[i][k].setCellValue(data[i][k]);
            }
        }
    }

    public void writerFromTweets(List<TweetForExcel> tweetForExcels,String filename){
        int rowsForReplies=0;
        for(TweetForExcel t : tweetForExcels)
            rowsForReplies += t.getNumberOfReplies();
        setNumberOfRows(rowsForReplies + tweetForExcels.size());
        setNumberOfColumns(5);
        setColumnsName(new String[]{"LINK", "TESTI", "PORZIONI DI TESTO" , "REPERTORI" , "CONTENUTI"});
        setHeaderBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        setHeaderFillPatternType(FillPatternType.SOLID_FOREGROUND);
        setHeaderAlignment(HorizontalAlignment.CENTER);
        headerSettings();
        dataCellsSettings();
        int row = 2;
        for(TweetForExcel t : tweetForExcels){
            this.dataCells[row][2].setCellValue(t.getTweet());
            this.dataCells[row][1].setCellValue(t.getLinkToTweet());
            CellStyle c = this.dataCellStyle;
            c.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            this.dataCells[row][2].setCellStyle(c);
            for(String tw : t.getReplies()){
                row++;
                this.dataCells[row][2].setCellValue(tw);
            }
            row++;
        }
        writeInFile(filename);
    }

    public void writeInFile(String filename){
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + filename + ".xlsx";

        try {
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            mainWorkbook.write(outputStream);
            mainWorkbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
