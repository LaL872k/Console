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
package io.github.lal872k.console;

import java.awt.Color;

/**
 * This class is used to give details to the <code>{@link lal.console.ui.Frame}s</code> about how to 
 * draw it on the <code>JTextPane</code>. It also uses the <code>{@link lal.console.MessageType}</code>
 * to get the default color of the text.
 * @author L. Arthur Lewis II
 */
public final class Message {
    
    private String text;
    private MessageType type;
    private Color textColor;
    private Color back;
    
    public Message(String text, MessageType type, Color textColor, Color back){
        this.text = text;
        this.type = type;
        this.textColor = textColor;
        this.back = back;
    }
    
    /**
     * The text value is the main content of the message. This is the text that is printed out to the 
     * <code>JTextPane</code>.
     * @param text text value.
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * The type of message. Can be <code>{@link lal.console.MessageType#ERROR}</code> or 
     * <code>{@link lal.console.MessageType#NORMAL}</code>.
     * @see lal.console.MessageType
     * @param type type value.
     */
    public void setType(MessageType type) {
        this.type = type;
    }
    
    /**
     * The <code>textColor</code> is the foreground color of the text. This only applies to this text 
     * in the <code>JTextPane</code> all the other messages have their own foreground color.
     * @param textColor textColor value.
     */
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    
    /**
     * The <code>back</code> is the background color of the text. This only applies to this text in the 
     * <code>JTextPane</code> all the other messages have their own background color.
     * @param back back value.
     */
    public void setBackground(Color back) {
        this.back = back;
    }
    
    /**
     * The text value is the main content of the message. This is the text that is printed out to the 
     * <code>JTextPane</code>.
     * @return text value
     */
    public String getText(){
        return text;
    }
    
    /**
     * The type of message. Can be <code>{@link lal.console.MessageType#ERROR}</code> or 
     * <code>{@link lal.console.MessageType#NORMAL}</code>.
     * @see lal.console.MessageType
     * @return type value.
     */
    public MessageType getType(){
        return type;
    }
    
    /**
     * The <code>textColor</code> is the foreground color of the text. This only applies to this text 
     * in the <code>JTextPane</code> all the other messages have their own foreground color.
     * @return textColor value.
     */
    public Color getTextColor(){
        return textColor;
    }
    
    /**
     * The <code>back</code> is the background color of the text. This only applies to this text in the 
     * <code>JTextPane</code> all the other messages have their own background color.
     * @return back value.
     */
    public Color getBackground(){
        return back;
    }
    
}
