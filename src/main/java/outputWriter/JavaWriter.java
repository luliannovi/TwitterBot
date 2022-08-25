package outputWriter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public JavaWriter(String mainSheetName) {
        this.mainWorkbook = new XSSFWorkbook();
        this.mainSheet = this.mainWorkbook.createSheet(mainSheetName);
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
        this.headerStyle.setFillBackgroundColor(headerBackgroundColor);
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
            mainSheet.setColumnWidth(i,15000);
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
        for(int i=0;i<this.numberOfRows;i++){
            for(int k=0;k<this.numberOfColumns;k++){
                this.dataCells[i][k].setCellValue(data[i][k]);
            }
        }
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
