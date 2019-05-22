package fileIO;

import java.io.*;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.*;

public class ReadXlsClass{
    private boolean show;
    private int rowNum;
    private int columnNum;
    private String fileName;
    private ArrayList<ArrayList<String>> xlsFileValues = new ArrayList<ArrayList<String>>();

    public ReadXlsClass(String fileName, boolean show){
        // show : 파일 읽기 완료후 데이터 출력 유무
        this.show = show;
        this.rowNum = -1;
        this.columnNum = -1;
        this.fileName = fileName;
    }

    public boolean readFile(){
        FileInputStream fis;
        HSSFWorkbook workbook;
        try{
            fis = new FileInputStream(this.fileName);
            workbook = new HSSFWorkbook(fis);
        }
        catch(IOException e){
            System.out.println("파일 입출력 오류 " + e);
            return false;
        }
        
        HSSFSheet sheet = workbook.getSheetAt(0);
        this.rowNum = sheet.getPhysicalNumberOfRows();
        for(int rowIndex = 1; rowIndex < this.rowNum; rowIndex++){
            HSSFRow row = sheet.getRow(rowIndex);
            if(row != null){
                this.columnNum = row.getPhysicalNumberOfCells();
                ArrayList<String> cellInfo = new ArrayList<String>();
                for(int columnIndex = 0; columnIndex < this.columnNum; columnIndex++){
                    HSSFCell cell = row.getCell(columnIndex);
                    String value = "";
                    if(cell == null){
                        // cell 이 빈값
                    }
                    else {
                        switch(cell.getCellType()){
                        case FORMULA:
                            value=cell.getCellFormula();
                            break;
                        case NUMERIC:
                            value=cell.getNumericCellValue()+"";
                            break;
                        case STRING:
                            value=cell.getStringCellValue()+"";
                            break;
                        case BOOLEAN:
                            value=cell.getBooleanCellValue()+"";
                            break;
                        case ERROR:
                            value=cell.getErrorCellValue()+"";
                            break;
                        case BLANK:
                            break;
                        case _NONE:
                            break;
                        }
                    }
                    // 파일 정보 저장
                    cellInfo.add(value);
                }
                this.xlsFileValues.add(cellInfo);
            }   
        }
        try{
            workbook.close();
        }
        catch(IOException e){
            System.out.println("파일 입출력 오류 " + e);
        }
        if(this.show){
            //읽은엑셀파일 읽는부분 주석처리
            //this.showAllData();
        }
        else {
            System.out.println("파일 읽기 완료");
        }

        return true;
    }

    public void showAllData(){
        for(int i=0; i<10; i++){
            for(int j=0; j<this.xlsFileValues.get(i).size(); j++){
                System.out.print(xlsFileValues.get(i).get(j) + " ");
            }
            System.out.println("");
        }
    }
    
    public ArrayList<ArrayList<String>> getXlsData(){
        return this.xlsFileValues;
    }
}