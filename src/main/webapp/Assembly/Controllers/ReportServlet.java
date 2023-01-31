package Assembly.Controllers;

import Assembly.Database.AccessTransactions;
import Assembly.Database.AssemblyDB;
import Assembly.Images.ExtractImage;
import Assembly.WebAppFonts.Fonts;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ReportServlet extends HttpServlet
{
    private static Document doc;
    private static String pdfName;

    private static Font tableFont, text;
    
    private static String query, user;
    
    private static PreparedStatement ps;
    private static HttpSession session;
    private static PdfWriter writePdf;
    private static ResultSet rs;
    
    protected void reportMaker(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException
    {
        try(
                Connection con = AssemblyDB.connect();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
           )
        {
            AccessTransactions at = new AccessTransactions(con);
            session = request.getSession();
            user = (String)session.getAttribute("admin");
           
            Rectangle rect = new Rectangle(PageSize.LETTER);          
            doc = new Document();
            doc.setMargins(40, 40, 120, 82); // (left,right,top,bottom)
            doc.setPageSize(rect);
            
            pdfName = reportName(currentDate()+ "_SalesReport.pdf");
            System.out.println(pdfName);
            
            writePdf = PdfWriter.getInstance(doc, baos);
            writePdf.setPageEvent(new ReportHeaderFooter());
            writePdf.setBoxSize("rect", rect);
            
            doc.open();
            text = new Fonts().consolas(12, false);

            // <editor-fold defaultstate="collapsed" desc="info table format">
            PdfPTable infoTable = new PdfPTable(1);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(20f); //Space before table
            infoTable.setSpacingAfter(10f); //Space after table
            float[] widthsInfo = {1f};
            infoTable.setWidths(widthsInfo);

            infoTable.addCell(new PdfPCell(new Phrase("Date of report: " + currentDateAndTime(), text)))
                    .setBorder(PdfPCell.TOP | PdfPCell.LEFT | PdfPCell.RIGHT); 
            infoTable.addCell(new PdfPCell(new Phrase(" ")))
                    .setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
            infoTable.addCell(new PdfPCell(new Phrase("Printed by Admin: " + user, text)))
                    .setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.BOTTOM);

            //</editor-fold>

            // <editor-fold defaultstate="collapsed" desc="purchase table format">
            float[] widths = {0.1f, 0.4f, 0.4f, 0.8f, 0.3f, 0.6f};
            String[] header = {"No.","Date","Order Number","Model_Name","No. Sold","Amount"};
            PdfPTable purchaseTable = purchaseTable(widths.length, widths, header);

            purchaseTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            int num = 1, totalSold = 0;
            double totalPrice = 0.0;

            query = "SELECT DATE_OF_REPORT, MODEL_NAME, Order_ID, QUANTITY, PRICE FROM DAILY_REPORT";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                String modelName = rs.getString("Model_Name"); 
                int sold = rs.getInt("Quantity");
                double amount = (double)(rs.getInt("Quantity") * rs.getDouble("Price"));
                int orderNum = at.getOrderNum(rs.getInt("Order_ID"));
                String date = rs.getDate("DATE_OF_REPORT").toString();
                
                purchaseTable.addCell(new Phrase(String.valueOf(num),text));
                purchaseTable.addCell(new Phrase(date,text));
                purchaseTable.addCell(new Phrase(String.valueOf(orderNum),text));
                purchaseTable.addCell(new Phrase(modelName,text));
                purchaseTable.addCell(new Phrase(String.valueOf(sold),text));
                purchaseTable.addCell(new Phrase(deciFormat(amount),text));
                totalSold += sold;
                totalPrice += amount;
                num++;               
            }

            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="total table format">
            PdfPTable totalTable = new PdfPTable(4);

            totalTable.addCell(new PdfPCell(new Phrase("Total Sold: ", text))).setBorder(PdfPCell.NO_BORDER);
            totalTable.addCell(new PdfPCell(new Phrase(totalSold + "", text))).setBorder(PdfPCell.NO_BORDER);
            totalTable.addCell(new PdfPCell(new Phrase("Total Amount: ", text))).setBorder(PdfPCell.NO_BORDER);
            totalTable.addCell(new PdfPCell(new Phrase(deciFormat(totalPrice), text))).setBorder(PdfPCell.NO_BORDER);

            // </editor-fold>

            doc.add(infoTable);
            doc.add(purchaseTable);
            doc.add(totalTable);
            doc.close();
            
            // <editor-fold defaultstate="collapsed" desc="headers">
            ServletOutputStream outSt = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=\""+ pdfName +"\"");
            //response.setHeader("Content-disposition", "inline; filename=\""+ currentDateAndTime() +"\"");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");  
            response.setContentLength(baos.size());
            baos.writeTo(outSt);
            
            outSt.close();
            rs.close();
            writePdf.flush();
            con.close();
            //</editor-fold>
            at.close();
        }
        catch (SQLException | DocumentException ex) 
        {
            ex.printStackTrace();
        }
    } 
    
    // <editor-fold defaultstate="collapsed" desc="PDFLayout methods">
    // <editor-fold defaultstate="collapsed" desc="Purchase table">
    public static PdfPTable purchaseTable(int cols, float[] widths, String[] header)
    {
        PdfPTable table = new PdfPTable(cols);
        
        try
        {
            tableFont = new Fonts().consolas(10, true); // table font(optional)
            table.setWidthPercentage(100); //Width in percentage
            table.setSpacingBefore(20f); //Space before table
            table.setSpacingAfter(20f); //Space after table
            table.setWidths(widths);

            for(String heads : header)
                table.addCell(new Phrase(heads, tableFont));
            
        }
        catch (DocumentException ex) 
        {
            ex.printStackTrace();
        }
        return table;
    }
    // </editor-fold>
    
    public static String deciFormat(int num){
        String pattern = "###,##0.00";
        DecimalFormat df = new DecimalFormat(pattern); 
        return (String)df.format(num);
    }
    
    public static String deciFormat(double num){
        String pattern = "###,##0.00";
        DecimalFormat df = new DecimalFormat(pattern);
        return (String)df.format(num);
    }
    
    public static String currentDateAndTime()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now);  
    }
    
    public static String currentDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now);  
    }
    
    public static String reportName(String time)
    {
        String name = "";
        
        if (!time.endsWith(".pdf"))
        {
            StringBuffer sb = new StringBuffer(time);
                sb.append(".pdf");
            name = sb.toString().replaceAll("[\\s+|:|/]", "");
        }
        
        else
        {
            name = time.replaceAll("[\\s+|:|/]", "");
        }
        
        return name;
    }
    
     // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    { reportMaker(request, response);}
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

// <editor-fold defaultstate="collapsed" desc="HeaderFooter Class">
class ReportHeaderFooter extends PdfPageEventHelper{
    Font consolas = new Fonts().consolas(11, false);
    ExtractImage image;
   
    @Override
    public void onStartPage(PdfWriter writer, Document document) 
    {}
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), 
        Element.ALIGN_CENTER, new Phrase("http://www.TheAssemblyPCParts.com/", consolas), 120, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), 
        Element.ALIGN_CENTER, new Phrase("Store Number: " + "0982-475-7938", consolas), 400, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(),
        Element.ALIGN_CENTER, new Phrase("Page [" + document.getPageNumber() +"]", consolas), 550, 30, 0);
        
        try
        {
            image = new ExtractImage(writer);          
            image.whiteLogo(173, 690, 80);
        } catch (IOException | DocumentException e) 
        {e.printStackTrace();}
    }
    
    
}
// </editor-fold>