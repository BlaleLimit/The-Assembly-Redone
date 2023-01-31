/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.Images;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;

public class ExtractImage {
    
    Image image;
    //String LogoWhiteName = "D:\\Haffiny-nardrei\\Documents\\NetBeansProjects\\2CSC_MP4_BENITEZ_LOYOLA_TADEO\\web\\Logos\\LogoWhite.png";
    String LogoWhiteName = this.getClass().getResource("LogoWhite.png").toString();
    String imgName = "";
    PdfWriter writer;
    
    public ExtractImage()
    {}
    
    public ExtractImage(PdfWriter writer)
    {this.writer = writer;}
    
    public ExtractImage(PdfWriter writer, String filePath){
        this.writer = writer;
        imgName = filePath;
    }
    
    public Image whiteLogo()
    throws BadElementException, IOException, DocumentException{
        writer.getDirectContent().addImage(image, true);
        return image;
    }

    public Image whiteLogo(PdfWriter writer, int x, int y, int percent)
    throws BadElementException, IOException, DocumentException{
        image = Image.getInstance(LogoWhiteName);
        image.setAlignment(Element.ALIGN_CENTER);
        image.setAbsolutePosition(x, y); //20,560
        image.scalePercent(percent);             //80
        writer.getDirectContent().addImage(image, true);
        return image;
    }

    public Image whiteLogo(int x, int y, int percent) 
    throws BadElementException, IOException, DocumentException{

        image = Image.getInstance(LogoWhiteName);
        image.setAlignment(Element.ALIGN_CENTER);
        image.setAbsolutePosition(x, y);
        image.scalePercent(percent);
        writer.getDirectContent().addImage(image, true);
        return image;
    }
    
    public Image otherImage(int x, int y, int percent)
    throws BadElementException, IOException, DocumentException{
        image = Image.getInstance(LogoWhiteName);
        image.setAlignment(Element.ALIGN_CENTER);
        image.setAbsolutePosition(x, y);
        image.scalePercent(percent);
        writer.getDirectContent().addImage(image, true);
        return image;
    }
}
