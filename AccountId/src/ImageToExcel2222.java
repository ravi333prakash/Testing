
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.util.IOUtils;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageToExcel2222 {

 public static void main(String[] args) {
  try {

   Workbook wb = new XSSFWorkbook();
   Sheet sheet = wb.createSheet("My Sample Excel");
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
   Drawing drawing = sheet.createDrawingPatriarch();

   //Create an anchor that is attached to the worksheet
   ClientAnchor anchor = helper.createClientAnchor();

   //create an anchor with upper left cell _and_ bottom right cell
   anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
   anchor.setCol1(0); //Column B
   anchor.setRow1(0); //Row 3
   anchor.setCol2(1); //Column C
   anchor.setRow2(1); //Row 4

   //Creates a picture
   Picture pict = drawing.createPicture(anchor, pictureIdx);

   //Reset the image to the original size
 // pict.resize(); //don't do that. Let the anchor resize the image!

   //Create the Cell B3
  // Cell cell = sheet.createRow(0).createCell(0);

   //set width to n character widths = count characters * 256
  // int widthUnits = 20*256;
 //  sheet.setColumnWidth(1, widthUnits);

   //set height to n points in twips = n * 20
 //  short heightUnits = 60*20;
 //  cell.getRow().setHeight(heightUnits);

   //Write the Excel file
   FileOutputStream fileOut = null;
   fileOut = new FileOutputStream("C:\\Users\\ravi.prakash\\Desktop\\testing10.xls");
   wb.write(fileOut);
   fileOut.close();

  } catch (IOException ioex) {
  }
 }
}