import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.util.IOUtils;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


 public class ImageInsideExcel {

 public static void main(String[] args) {
  try {

   Workbook wb = new XSSFWorkbook();
   Sheet sheet = wb.createSheet("My Sample Excel");
   Sheet sheet1=wb.createSheet("Second");
   //FileInputStream obtains input bytes from the image file
   InputStream inputStream = new FileInputStream("C:\\Users\\ravi.prakash\\Desktop\\logo1.png");
   //Get the contents of an InputStream as a byte[].
   byte[] bytes = IOUtils.toByteArray(inputStream);
   //Adds a picture to the workbook
   int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
   //close the input stream
   inputStream.close();
   //Returns an object that handles instantiating concrete classes
   CreationHelper helper = wb.getCreationHelper();
   //Creates the top-level drawing patriarch.
   

   //Create an anchor that is attached to the worksheet
   ClientAnchor anchor = helper.createClientAnchor();

   //create an anchor with upper left cell _and_ bottom right cell
   anchor.setCol1(0); 
   anchor.setCol2(1);//Column B
   anchor.setRow1(0); //Row 3 //Column C
   anchor.setRow2(1); //Row 4

   //Creates a picture
    sheet.createDrawingPatriarch().createPicture(anchor, pictureIdx);
    sheet1.createDrawingPatriarch().createPicture(anchor, pictureIdx);
   

   //Reset the image to the original size
  // pict.resize(); //don't do that. Let the anchor resize the image!
   
//rav -> size of logo got bigger when uncommented resize()
   
   //Create the Cell B3
  // Cell cell = sheet.createRow(0).createCell(0);
   //set width to n character widths = count characters * 256
  // int widthUnits = 20*256;
 //  sheet.setColumnWidth(1, widthUnits);

   //set height to n points in twips = n * 20
 //  short heightUnits = 60*20;
 //  cell.getRow().setHeight(heightUnits);
	//New header
	Font newHeaderFont=wb.createFont();
	newHeaderFont.setFontHeightInPoints((short)20);
	newHeaderFont.setColor(IndexedColors.BLACK.getIndex());
	CellStyle newHeaderStyle =wb.createCellStyle();
	
	newHeaderStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	newHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	//newHeaderStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
	newHeaderStyle.setFont(newHeaderFont);
	
	Row newHeaderRow = sheet.createRow(6);
	Row newHeaderRow1 = sheet1.createRow(6);
	
	Cell cell1=newHeaderRow.createCell(1);
	cell1.setCellValue("PROVISIONING ORDER REPORT");
	cell1.setCellStyle(newHeaderStyle);
	
	 cell1=newHeaderRow1.createCell(1);
	cell1.setCellValue("PROVISIONING ORDER REPORT");
	cell1.setCellStyle(newHeaderStyle);
	//sheet.autoSizeColumn(1);//expanded the size of 1s column
	//NewHeader
   //Write the Excel file
   FileOutputStream fileOut = null;
   fileOut = new FileOutputStream("C:\\Users\\ravi.prakash\\Desktop\\testing6.xls");
   wb.write(fileOut);
   fileOut.close();
   wb.close();
   System.out.println("writing complete");
  

  } catch (IOException ioex) {
	  System.out.println("exception"+ioex);
  }
 }
}