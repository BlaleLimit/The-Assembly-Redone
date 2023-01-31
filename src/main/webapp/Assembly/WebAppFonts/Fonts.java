/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assembly.WebAppFonts;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import java.io.IOException;

public class Fonts {
    
    public Font consolas(int size, boolean bold){
        try {
            BaseFont baseFont3 = BaseFont.createFont(
                this.getClass().getResource("Consolas.ttf").toString(),
                BaseFont.WINANSI,
                BaseFont.NOT_EMBEDDED
            );
            
            Font font = new Font(baseFont3, size);
            
            if(bold)
                font.setStyle(Font.BOLD); 
            
            return font;
        } catch (DocumentException | IOException ex) 
        {ex.printStackTrace();}
        return null;
    }
    
    public Font consolas(boolean bold){
        try {
            BaseFont baseFont3 = BaseFont.createFont(
                this.getClass().getResource("Consolas.ttf").toString(),
                BaseFont.WINANSI,
                BaseFont.NOT_EMBEDDED
            );
            
            Font font = new Font(baseFont3);
            
            if(bold)
                font.setStyle(Font.BOLD); 
            
            return font;
        } catch (DocumentException | IOException ex) 
        {ex.printStackTrace();}
        return null;
    }
    
    public Font consolas(){
        try {
            BaseFont baseFont3 = BaseFont.createFont(
                this.getClass().getResource("Consolas.ttf").toString(),
                BaseFont.WINANSI,
                BaseFont.NOT_EMBEDDED
            );
            Font font = new Font(baseFont3);
            return font;
        } catch (DocumentException | IOException ex) 
        {ex.printStackTrace();}
        return null;
    }
}
