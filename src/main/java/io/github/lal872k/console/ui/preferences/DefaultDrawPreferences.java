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

import io.github.lal872k.console.Console;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 * The is the default value for any <code>{@link lal.console.ui.Frame}</code>'s 
 * <code>{@link lal.console.ui.preferences.DrawPreference}</code>. It extends and uses 
 * <code>{@link lal.console.ui.preferences.BasicDrawPreferences}</code> to set all of the values.
 * @author L. Arthur Lewis II
 */
public class DefaultDrawPreferences extends BasicDrawPreferences{
    
    public static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 18);
    public static final String SEND_TEXT = "SEND";
    public static final Color TEXT_COLOR = Color.WHITE;
    public static final Color ERROR_TEXT_COLOR = Color.RED;
    public static final Color BACKGROUND = Color.DARK_GRAY;
    public static final Color BORDERS = Color.BLACK;
    public static final Color SCROLL_TRACK = Color.BLACK;
    public static final Color SCROLL_THUMB = Color.LIGHT_GRAY;
    public static final Color SCROLL_THUMB_SLIDE = Color.WHITE;
    public static final String WINDOW_TITLE = "Console";
    public static final Color SELECTED_AUTO_COMPLETED = Color.GRAY;
    public static final ImageIcon WINDOW_ICON = 
            new ImageIcon(Console.class.getResource("/icon.png"));;
    
    public DefaultDrawPreferences(){
        super(FONT, SEND_TEXT, TEXT_COLOR, ERROR_TEXT_COLOR, BACKGROUND, BORDERS, SCROLL_TRACK, 
                SCROLL_THUMB, SCROLL_THUMB_SLIDE, WINDOW_TITLE, WINDOW_ICON, SELECTED_AUTO_COMPLETED);
    }
    
}
