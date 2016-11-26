/* 
 * The MIT License
 *
 * Copyright 2016 L. Arthur Lewis II.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.lal872k.console.ui.preferences;

import io.github.lal872k.console.Message;
import io.github.lal872k.console.MessageType;
import io.github.lal872k.console.ui.ColorScrollBarUI;
import io.github.lal872k.console.ui.Frame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The <code>BasicDrawPreferences</code> let's you customize the main colors and fonts of the 
 * console. This class has a default class with preset values called 
 * <code>{@link lal.console.ui.preferences.DefaultDrawPreferences}</code>.
 * @author L. Arthur Lewis II
 */
public class BasicDrawPreferences implements DrawPreference {
    
    private String sendText;
    
    private Font font;
    private Color textColor;
    private Color errorTextColor;
    
    private Color background;
    private Color borders;
    
    private Color scrollTrack;
    private Color scrollThumb;
    private Color scrollThumbSlide;
    
    private String windowText;
    private ImageIcon windowIcon;
    
    private Color selectedAC;
    
    public BasicDrawPreferences(Font font, String sendText, Color textColor, Color errorTextColor, 
            Color background, Color borders, Color scrollTrack, Color scrollThumb, 
            Color scrollThumbSlide, String windowTitle, ImageIcon windowIcon, Color selectedAC){
        setFont(font);
        setSendText(sendText);
        setErrorTextColor(errorTextColor);
        setTextColor(textColor);
        setBackground(background);
        setBorders(borders);
        setScrollTrack(scrollTrack);
        setScrollThumb(scrollThumb);
        setScrollThumbSlide(scrollThumbSlide);
        setWindowTitle(windowTitle);
        setWindowIcon(windowIcon);
        setSelectedAutoComplete(selectedAC);
    }
    
    @Override
    public void styleConsole(Frame frame) {
        frame.getSendButton().setText(getSendText());
        frame.getSendButton().setFont(getFont());
        frame.getSendButton().setBorderPainted(false);
        frame.getSendButton().setFocusPainted(false);
        frame.getSendButton().setBackground(getBackground());
        frame.getSendButton().setForeground(getTextColor());
        
        frame.getTextPane().setFont(getFont());
        frame.getTextPane().setEditable(false);
        frame.getTextPane().setBackground(getBackground());
        frame.getTextPane().setSelectedTextColor(getBackground());
        frame.getTextPane().setSelectionColor(getTextColor());
        
        frame.getScroll().setBorder(null);
        frame.getScroll().getVerticalScrollBar().setUI(new ColorScrollBarUI(getScrollThumb(), 
                getScrollThumbSlide(), getScrollTrack(), getBackground(), 5));
        
        frame.getInputField().setFont(getFont());
        frame.getInputField().setBackground(getBackground());
        frame.getInputField().setForeground(getTextColor());
        frame.getInputField().setBorder(null);
        frame.getInputField().setCaretColor(getTextColor());
        
        frame.getAutoComplete().setBackground(getBackground());
        frame.getAutoComplete().setPredictionBorders(getBorders());
        frame.getAutoComplete().setTextIndent(10);
        frame.getAutoComplete().setFont(getFont());
        frame.getAutoComplete().setForeground(getTextColor());
        frame.getAutoComplete().setSelectedBackground(getSelectedAutoComplete());
        
        frame.getFrame().setTitle(windowText);
        frame.getFrame().setIconImage(windowIcon.getImage());
        
        Container main = frame.getMainContainer();
        for (Component comp : main.getComponents()){
            JPanel panel = (JPanel) comp;
            panel.setBorder(BorderFactory.createLineBorder(getBorders()));
        }
    }
    
    @Override
    public Message styleText(String text, MessageType type){
        Color back = null, fore = null;
        if (type.equals(MessageType.NORMAL)){
            back =(background);
            fore = (textColor);
        } else if (type.equals(MessageType.ERROR)){
            fore = (errorTextColor);
            back = (background);
        }
        Message msg = new Message(text, type, fore, back);
        return msg;
    }
    
    @Override
    public boolean isValid(){
        if (sendText==null){
            return false;
        }
        if (font==null){
            return false;
        }
        if (textColor==null){
            return false;
        }
        if (errorTextColor==null){
            return false;
        }
        if (background==null){
            return false;
        }
        if (borders==null){
            return false;
        }
        if (scrollTrack==null){
            return false;
        }
        if (scrollThumb==null){
            return false;
        }
        if (scrollThumbSlide==null){
            return false;
        }
        if (windowText==null){
            return false;
        }
        if (windowIcon==null){
            return false;
        }
        return true;
    }
    
    /**
     * The text on <code>sendButton</code> from the <code>{@link lal.console.ui.Frame}</code>.
     * @param sendText text value.
     */
    public final void setSendText(String sendText){
        this.sendText = sendText;
    }
    
    /**
     * The font of the <code>sendButton</code>, <code>inputField</code>, and <code>textPane</code>.
     * @param font font value.
     */
    public final void setFont(Font font){
        this.font = font;
    }
    
    /**
     * The default foreground color of the output (<code>JTextPane</code>).
     * @param color color value.
     */
    public final void setTextColor(Color color){
        this.textColor = color;
    }
    
    /**
     * The default background color of the output (<code>JTextPane</code>) for errors.
     * @param errorColor errorColor value.
     */
    public final void setErrorTextColor(Color errorColor){
        this.errorTextColor = errorColor;
    }
    
    /**
     * The background color of all the components and the text in the output (<code>JTextPane</code>).
     * @param background background value.
     */
    public final void setBackground(Color background){
        this.background = background;
    }
    
    /**
     * The Color of the borders around all of the components.
     * @param borders border value.
     */
    public final void setBorders(Color borders){
        this.borders = borders;
    }
    
    /**
     * The background color of the scroll bar.
     * @param scrollTrack scrollTrack value.
     */
    public final void setScrollTrack(Color scrollTrack){
        this.scrollTrack = scrollTrack;
    }
    
    /**
     * The color of the thumb on the scroll bar.
     * @param scrollThumb scrollThumb value.
     */
    public final void setScrollThumb(Color scrollThumb){
        this.scrollThumb = scrollThumb;
    }
    
    /**
     * The color of the thumb on the scroll bar while it is being moved.
     * @param scrollThumbSlide scrollThumbSlide value.
     */
    public final void setScrollThumbSlide(Color scrollThumbSlide){
        this.scrollThumbSlide = scrollThumbSlide;
    }
    
    /**
     * The title of the <code>JFrame</code>.
     * @param windowTitle windowTitle value.
     */
    public final void setWindowTitle(String windowTitle){
        this.windowText = windowTitle;
    }
    
    /**
     * The Icon for the <code>JFrame</code>.
     * @param windowIcon windowIcon value.
     */
    public final void setWindowIcon(ImageIcon windowIcon){
        this.windowIcon = windowIcon;
    }
    
    /**
     * The color of the background of a selected auto complete. Unselected auto complete options 
     * backgrounds is set to the background value.
     * @param selectedAC selectedAC value.
     */
    public final void setSelectedAutoComplete(Color selectedAC){
        this.selectedAC = selectedAC;
    }
    
    /**
     * The text on <code>sendButton</code> from the <code>{@link lal.console.ui.Frame}</code>.
     * @return send
     */
    public final String getSendText(){
        return sendText;
    }
    
    /**
     * The font of the <code>sendButton</code>, <code>inputField</code>, and <code>textPane</code>.
     * @return font value.
     */
    public final Font getFont(){
        return font;
    }
    
    /**
     * The default foreground color of the output (<code>JTextPane</code>).
     * @return color value.
     */
    public final Color getTextColor(){
        return textColor;
    }
    
    /**
     * The default background color of the output (<code>JTextPane</code>) for errors.
     * @return errorColor value.
     */
    public final Color getErrorColor(){
        return errorTextColor;
    }
    
    /**
     * The background color of all the components and the text in the output (<code>JTextPane</code>).
     * @return background value.
     */
    public final Color getBackground(){
        return background;
    }
    
    /**
     * The Color of the borders around all of the components.
     * @return border value.
     */
    public final Color getBorders(){
        return borders;
    }
    
    /**
     * The background color of the scroll bar.
     * @return scrollTrack value.
     */
    public final Color getScrollTrack(){
        return scrollTrack;
    }
    
    /**
     * The color of the thumb on the scroll bar.
     * @return scrollThumb value.
     */
    public final Color getScrollThumb(){
        return scrollThumb;
    }
    
    /**
     * The color of the thumb on the scroll bar while it is being moved.
     * @return scrollThumbSlide value.
     */
    public final Color getScrollThumbSlide(){
        return scrollThumbSlide;
    }
    
    /**
     * The title of the <code>JFrame</code>.
     * @return windowTitle value.
     */
    public final String getWindowTitle(){
        return windowText;
    }
    
    /**
     * The Icon for the <code>JFrame</code>.
     * @return windowIcon value.
     */
    public final ImageIcon getWindowIcon(){
        return windowIcon;
    }
    
    /**
     * The color of the background of a selected auto complete. Unselected auto complete options 
     * backgrounds is set to the background value.
     * @return selectedAC value.
     */
    public final Color getSelectedAutoComplete(){
        return selectedAC;
    }
    
    @Override
    public String toString(){
        return "sendText="+sendText+",font="+font+",textColor="+textColor+",errorTextColor="
                +errorTextColor+",background="+background+",borders="+borders+",scrollTrack="
                +scrollTrack+",scrollThumb="+scrollThumb+",scrollThumbSlide="+scrollThumbSlide
                +",windowTitle="+windowText+",windowIcon="+windowIcon+",selectedAC="+selectedAC;
    }
    
}
