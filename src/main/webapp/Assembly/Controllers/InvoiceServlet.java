/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Controllers;

import Assembly.Database.AccessCart;
import Assembly.Database.AccessInventory;
import Assembly.Database.AccessReports;
import Assembly.Database.AccessTransactions;
import Assembly.WebAppFonts.Fonts;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import java.sql.SQLException;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import Assembly.Database.AccessUsers;
import Assembly.Database.AssemblyDB;
import Assembly.Errors.DiscountException;
import Assembly.Errors.SessionException;
import Assembly.Images.ExtractImage;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceServlet extends HttpServlet{
    
    private static ArrayList<Integer> prodInCart;
    private static ArrayList<Integer> itemQuan;
    private static ArrayList<Integer> itemStock;
    private static ArrayList<String> itemName;
    private static ArrayList<Double> itemPrice;
    private static Iterator<String> iter;
    private static Iterator<Double> iter2;
    private static Iterator<Integer>iter3;
    private static Iterator<Integer> iter4;
    private static Iterator<Integer> iter5;
    private ArrayList<Integer> itemQuan2;
    private Iterator<Integer> iter6;
    private ArrayList<String> itemName2;
    private Iterator<String> iter7;
    
    private static Document doc;
    private static String pdfName;
    private static Font tableFont, text;
    private static String user, name;
    private static HttpSession session;
    private static PdfWriter writePdf;
    
    private static String payment;
    private static double totalAmountDiscVat;
    private static java.util.Date dateToday;
    private static Date sqlDate;
    private int orderNum;
    static int itemCnt;
    private static int totalItems;
    static double totalCnt;
    private static double discountLinear = 0;
    private static double discountPercent = 0;
    static final double VAT = 1.12;
    private static int userId;
    private static String orderNumFormat;
    private static String discount;
    private static double discountVal;
    private static double totalAmountDisc;
    private static int codeId;
    
    private static int orderNumBer;
    private static AccessUsers au;
    private static AccessCart ac;
    private static AccessInventory ai;
    private static AccessReports ar;
    private static AccessTransactions at;
    
    protected void pdfMaker(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException{
        
        session = request.getSession();
        
        if(session.getAttribute("userID")!= null){
            
                try (
                        Connection con = AssemblyDB.connect();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream()
                    ){

                    user = (String)session.getAttribute("user");
                    userId = (int)session.getAttribute("userID");

                    au = new AccessUsers(con);
                    ac = new AccessCart(con);
                    ai = new AccessInventory(con);
                    ar = new AccessReports(con);
                    at = new AccessTransactions(con);
                    ac.setShopperID(userId);
                    at.setShopperID(userId);
                    name = au.getFirst_LastName(user);
                    payment = (String)request.getParameter("paymentMethod");
                    discount = (String)request.getParameter("discCode");
                    System.out.println(discount + "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                    
                    //if(at.discExists(discount) || discount.equals("")){
                        pdfName = receiptName(currentDate());
                        orderNum = at.getNewNum(at.getOrderNumber(50000, 1));
                        orderNumBer = Integer.parseInt(invoiceNum(orderNum));

                        dateToday = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate());
                        sqlDate = new Date(dateToday.getTime());

                        discountConfig(con);
                        at.addTransact(sqlDate, orderNumBer, codeId);

                        // PDF Begin

                        Rectangle rect = new Rectangle(600,650); //(x,y)
                        doc = new Document();
                        doc.setMargins(40, 40, 100, 82); //(left,right,top,bottom)
                        doc.setPageSize(rect);
                        writePdf = PdfWriter.getInstance(doc, baos);
                        writePdf.setPageEvent(new HeaderFooter(invoiceNum(orderNum)));
                        writePdf.setBoxSize("rect", rect);

                        doc.open();// edit begin 

                        text = new Fonts().consolas(12, false);
                        PdfPTable pTable = purchaseTable();

                        doc.add(infoTable());
                        doc.add(pTable);
                        doc.add(amountTable());

                        doc.close(); // edit end

                        // PDF End

                        // <editor-fold defaultstate="collapsed" desc="headers">

                        try (ServletOutputStream outSt = response.getOutputStream()){
                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "attachment; filename=\""+ pdfName +"\"");
                            response.setHeader("Expires", "0");
                            response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
                            response.setHeader("Pragma", "public");
                            response.setContentLength(baos.size());
                            baos.writeTo(outSt);
                            writePdf.flush();
                            writePdf.close();
                        }
                        
                    //}
                //else{
                //        request.setAttribute("msg", "Discount code does not exist");
                //        throw new DiscountException();
                  //  }
                    //</editor-fold>
                    
                    at.close();
                    ar.close();
                    ai.close();
                    ac.close();
                    au.close();
                }catch(SQLException | DocumentException | ParseException ex)
                {ex.printStackTrace();} 
                finally{}
        }else{
            request.setAttribute("msg", "Login right now and start shopping!");
            session.invalidate();
            throw new SessionException();
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="PDFLayout methods">
    public static PdfPTable purchaseTable(){
        PdfPTable table = new PdfPTable(5);
        float[] widths = {0.1f, 0.9f, 0.2f, 0.35f, 0.45f};
        String[] header = {"No.","Item/s purchased","Quantity","Unit Price(Php)","Subtotal Price(Php)"};
        try {
            tableFont = new Fonts().consolas(10,true); // table font(optional)
            table.setWidthPercentage(100); //Width in percentage
            table.setSpacingBefore(20f); //Space before table
            table.setSpacingAfter(20f); //Space after table
            table.setWidths(widths);
            
            for(String heads : header)
                table.addCell(new Phrase(heads, tableFont));
            
            table.setHeaderRows(1);          

            prodInCart = (ArrayList<Integer>)session.getAttribute("prodIdCart");
            itemName = (ArrayList<String>)session.getAttribute("itemName");
            itemPrice = (ArrayList<Double>)session.getAttribute("itemPrice");
            itemQuan = (ArrayList<Integer>)session.getAttribute("itemQuantity");
            
            iter = itemName.iterator();
            iter2 = itemPrice.iterator();
            iter3 = itemQuan.iterator();
            iter5 = prodInCart.iterator();
            
            itemCnt = 1;
            totalCnt = 0;
            totalItems = 0;
            while(iter.hasNext()){
                String prodName = iter.next();
                double price = iter2.next().intValue();
                int quan = iter3.next();
                double subTotal = price*quan; 
                int prodId = iter5.next();
                        
                table.addCell(new Phrase(String.valueOf(itemCnt),text));
                table.addCell(new Phrase(prodName,text));
                table.addCell(new Phrase(String.valueOf(quan),text));
                table.addCell(new Phrase(deciFormat(price),text));
                table.addCell(new Phrase(deciFormat(subTotal),text));
                itemCnt++;
                totalCnt += subTotal;
                totalItems += quan;
                
                System.out.println("ORDER NUMBER: " + orderNumBer);
                System.out.println("ORDER NUMBER: " + at.getOrderID(orderNumBer));
                
                ar.addReport(sqlDate, at.getOrderID(orderNumBer), prodId, prodName, price, quan);
                ac.deleteItem(prodId);
                ai.reduceItemStock(quan, prodName);
            }
            return table;
        }catch(DocumentException ex) 
        {ex.printStackTrace();}
        return null;
    }
    
    public static PdfPTable infoTable(){
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(80); //Width in percentage
        infoTable.setSpacingBefore(20f); //Space before table
        infoTable.setSpacingAfter(10f); //Space after table
        float[] widthsInfo = {1f, 0.7f};
        String cash = "";
        String cashLess = "";
        
        if(payment.equals("Cash")) cash = "X";
        else cashLess = "X";
        
        try {
            infoTable.setWidths(widthsInfo);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
        infoTable.addCell(new PdfPCell(new Phrase("Ordered by: " + "\nUserName: "+user+ "\nName: "+name, text)))
                .setBorder(PdfPCell.NO_BORDER);
        infoTable.addCell(new PdfPCell(new Phrase("Date and time of purchase: \n" + currentDateAndTime(), text))).setBorder(PdfPCell.NO_BORDER); 
        
        infoTable.addCell(new PdfPCell(new Phrase(" "))).setBorder(PdfPCell.NO_BORDER);
        infoTable.addCell(new PdfPCell(new Phrase(" "))).setBorder(PdfPCell.NO_BORDER); 
        
        infoTable.addCell(new PdfPCell(new Phrase("Cashless payment: [" + cashLess +"]"+ "\nCash Payment: [" + cash +"]", text))).setBorder(PdfPCell.NO_BORDER);
        infoTable.addCell(new PdfPCell(new Phrase("Discount Code: "+ discount, text))).setBorder(PdfPCell.NO_BORDER);
        return infoTable;
    }
    
    public static PdfPTable amountTable(){
        PdfPTable table = new PdfPTable(2);
        float[] widths = {0.03f, 0.02f};
        try {
            tableFont = new Fonts().consolas(10,true); // table font(optional)
            table.setWidthPercentage(50); //Width in percentage
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(20f); //Space after table
            table.setWidths(widths);
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            discountVal = (discountPercent/100.0) * totalCnt;
            totalAmountDisc = ((totalCnt - discountVal) - discountLinear);
            totalAmountDiscVat = totalAmountDisc*VAT;
            
            table.addCell(new Phrase("Total items bought", tableFont));
            table.addCell(new Phrase(String.valueOf(totalItems), text));
            table.addCell(new Phrase("Amount Due", tableFont));
            table.addCell(new Phrase(deciFormat(totalCnt), text));
            
            table.addCell(new Phrase("Discount off", tableFont));
            if(discountLinear == 0 && discountPercent != 0)
                table.addCell(new Phrase(String.valueOf(discountPercent)+"%", text));
            else if(discountLinear != 0 && discountPercent == 0)
                table.addCell(new Phrase(String.valueOf(discountLinear)+"Php", text));
            else
                table.addCell(new Phrase("N/A", tableFont));
            
            table.addCell(new Phrase("Amount due w/discount", tableFont));
            table.addCell(new Phrase(deciFormat(totalAmountDisc), text));
            
            table.addCell(new Phrase("VAT(12%)w/discount", tableFont));
            table.addCell(new Phrase(deciFormat(totalAmountDisc*0.12), text));
            table.addCell(new Phrase("Amount Due w/ VAT added", tableFont));
            table.addCell(new Phrase(deciFormat(totalAmountDisc+ (totalAmountDisc*0.12)), text));
            
            table.addCell(new Phrase("Total Amount Due", tableFont));
            table.addCell(new Phrase(deciFormat(totalAmountDiscVat)+" Php", text));
                
            return table;
        }catch(DocumentException ex) 
        {ex.printStackTrace();}
        return null;
     }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="QOL methods"> 
    
    public boolean isNull(String word){
        return word == null && word.isEmpty();
    }
    public void discountConfig(Connection con){
        String queryDisc = "select * from discounts where discount_codes = ?";
        String queryDisc2 = "update discounts set isUsed = true where discount_codes = ?";
        
        try(PreparedStatement ps2 = con.prepareStatement(queryDisc)){
            ps2.setString(1, discount);
            try(ResultSet rs = ps2.executeQuery()){
                if(rs.next()){
                    codeId = rs.getInt("CODE_ID");
                    if(rs.getBoolean("isUsed") == false){
                        if(rs.getBoolean("isPercent")){
                            discountPercent = rs.getInt("code_value");
                            System.out.println("DISCOUNT: " + discountPercent);
                        }
                        else{
                            discountLinear = rs.getInt("code_value"); 
                            System.out.println("DISCOUNT: " + discountPercent);
                        } 
                        try(PreparedStatement ps3 = con.prepareStatement(queryDisc2)){
                            ps3.setString(1, rs.getString("discount_codes"));
                            int row = ps3.executeUpdate();
                            System.out.println("AFFECTED ROW/S: "+row);
                        }
                    }else{discount = "N/A";}
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
    
    public static String currentDateAndTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now);  
    }
    
    public static String currentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now);  
    }
    
    public static String invoiceNum(int orderNum){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");  
        LocalDateTime now = LocalDateTime.now();  
        return (dtf.format(now)+orderNum).trim();  
    }
    
    public static String receiptName(String time){
        return time + "_AssemblyInvoice.pdf";
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    { pdfMaker(request, response);}
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
// <editor-fold defaultstate="collapsed" desc="HeaderFooter Class">
class HeaderFooter extends PdfPageEventHelper{
    
    Font consolas = new Fonts().consolas(12, false);
    ExtractImage image;
    private String orderNum;
    
    public HeaderFooter(){}
    
    public HeaderFooter(String orderNum)
    { this.orderNum = orderNum;}
    
    @Override
    public void onStartPage(PdfWriter writer, Document document){
        ColumnText.showTextAligned(writer.getDirectContent(), 
        Element.ALIGN_CENTER, new Phrase("Order number: " + orderNum , new Fonts().consolas(12, false)), 430, 590, 0);
    }
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), 
        Element.ALIGN_CENTER, new Phrase("http://www.TheAssemblyPC.com/", consolas), 120, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), 
        Element.ALIGN_CENTER, new Phrase("Store Number: " + "0982-475-7938", consolas), 370, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(),
        Element.ALIGN_CENTER, new Phrase("Page [" + document.getPageNumber() +"]", consolas), 550, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(),
        Element.ALIGN_CENTER, new Phrase("THIS SALES INVOICE SHALL BE VALID FOR FIVE(5) YEARS ", consolas), 300, 60, 0);
       
       try {
            image = new ExtractImage(writer);
            image.whiteLogo(20, 560, 80);
        } catch (IOException | DocumentException e) 
        {e.printStackTrace();}
    }
    
    
}
// </editor-fold>

