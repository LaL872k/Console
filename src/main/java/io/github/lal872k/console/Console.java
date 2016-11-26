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

import io.github.lal872k.console.input.InputHandler;
import io.github.lal872k.console.input.InputListener;
import io.github.lal872k.console.output.OutputHandler;
import io.github.lal872k.console.output.OutputListener;
import io.github.lal872k.console.ui.AutoComplete;
import io.github.lal872k.console.ui.Frame;
import io.github.lal872k.console.ui.preferences.DefaultDrawPreferences;
import io.github.lal872k.console.ui.preferences.DrawPreference;
import io.github.lal872k.console.ui.preferences.exceptions.InvalidDrawPreferences;
import java.awt.Color;

/**
 * This is the main class for this whole project. It contains a 
 * <code>{@link lal.console.ui.Frame}</code> that handles all GUI related stuff. 
 * It has its own <code>{@link lal.console.ui.preferences.DrawPreference}</code> that it uses 
 * to style the <code>Frame</code>. The default value for this <code>DrawPreference</code> 
 * is <code>{@link lal.console.ui.preferences.DefaultDrawPreferences}</code>. 
 * <p>
 * This also manages all of 
 * the <code>{@link lal.console.input.InputListener}s</code> and 
 * <code>{@link lal.console.output.OutputListener}s</code>. The <code>JFrame</code> can be 
 * temporarily hidden and then show again using the methods <code>{@link #hide()}</code> and 
 * <code>{@link #show()}</code>.
 * <p>
 * To make a class that handles input, output, and/or auto complete for any console 
 * use <code>{@link lal.console.extensions.ConsoleExtension}</code> which makes it easy to add 
 * and remove that extension from any console using its 
 * <code>{@link lal.console.extensions.ConsoleExtension#addToConsole(Console)}</code> and 
 * <code>{@link lal.console.extensions.ConsoleExtension#removeFromConsole(Console)}</code> methods.
 * @author L. Arthur Lewis II
 */
public class Console {
    
    private DrawPreference dp;
    private final Frame output;
    private final InputHandler ih;
    private final OutputHandler oh;
    
    public Console(){
        dp = new DefaultDrawPreferences();
        output = new Frame(this, dp);
        ih = new InputHandler(output, this);
        oh = new OutputHandler(this);
    }
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the specified colors. If the 
     * <code>JScrollPane</code>'s vertical scroll bar is at the bottom then it will stay at 
     * the bottom - otherwise it will stay at the current value. All the 
     * <code>{@link lal.console.output.OutputListener}s</code> 
     * will be notified of the message.
     * @param text text value.
     * @param foreground the foreground color of the text.
     * @param background the background color of the text.
     */
    public void print(String text, Color foreground, Color background) {
        boolean moveToBot = false;
        if (output.atBottomOfOutput()){
            moveToBot = true;
        }
        output.appendToOutput(text, foreground, background);
        oh.alertListeners(new Message(text.replaceAll("\n", ""), MessageType.NORMAL, foreground, background));
        if (moveToBot){
            output.moveToBottomOfOutput();
        }
    }
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the specified foreground and 
     * default background (specified by the <code>DrawPreference</code>). If the 
     * <code>JScrollPane</code>'s vertical scroll bar is at the bottom then it will stay at 
     * the bottom - otherwise it will stay at the current value. All the 
     * <code>{@link lal.console.output.OutputListener}s</code>
     * will be notified of the message.
     * @param text text value.
     * @param foreground the foreground color of the text.
     */
    public void print(String text, Color foreground) {
        Message msg = dp.styleText(text, MessageType.NORMAL);
        print(text, foreground, msg.getBackground());
    }
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the specified background and 
     * default foreground (specified by the <code>DrawPreference</code>). If the 
     * <code>JScrollPane</code>'s vertical scroll bar is at the bottom then it will stay at 
     * the bottom - otherwise it will stay at the current value. All the <code>OutputListener</code>s 
     * will be notified of the message.
     * @param text text value.
     * @param background the background color of the text.
     */
    public void printWithBackground(String text, Color background) {
        Message msg = dp.styleText(text, MessageType.NORMAL);
        print(text, msg.getTextColor(), background);
    }
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the default background and foreground
     * (specified by the <code>DrawPreference</code>). If the <code>JScrollPane</code>'s vertical scroll 
     * bar is at the bottom then it will stay at the bottom - otherwise it will stay at the current 
     * value. All the <code>{@link lal.console.output.OutputListener}s</code>
     * will be notified of the message.
     * @param text text value.
     */
    public void print(String text) {
        Message msg = dp.styleText(text, MessageType.NORMAL);
        print(text, msg.getTextColor(), msg.getBackground());
    }
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the specified colors. If the 
     * <code>JScrollPane</code>'s vertical scroll bar is at the bottom then it will stay at 
     * the bottom - otherwise it will stay at the current value. All the 
     * <code>{@link lal.console.output.OutputListener}s</code>
     * will be notified of the message. The text value will have "\n" added to the end of it.
     * @param text text value.
     * @param foreground the foreground for the text.
     * @param background the background for the text.
     */
    public void println(String text, Color foreground, Color background) {
        print(text+"\n", foreground, background);
    }
    
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the specified foreground and 
     * default background (specified by the <code>DrawPreference</code>). If the 
     * <code>JScrollPane</code>'s vertical scroll bar is at the bottom then it will stay at 
     * the bottom - otherwise it will stay at the current value. All the 
     * <code>{@link lal.console.output.OutputListener}s</code>
     * will be notified of the message. The text value will have "\n" added to the end of it.
     * @param text text value.
     * @param foreground the foreground for the text.
     */
    public void println(String text, Color foreground) {
        print(text+"\n", foreground);
    }
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the specified background and 
     * default foreground (specified by the <code>DrawPreference</code>). If the 
     * <code>JScrollPane</code>'s vertical scroll bar is at the bottom then it will stay at 
     * the bottom - otherwise it will stay at the current value. All the 
     * <code>{@link lal.console.output.OutputListener}s</code>
     * will be notified of the message. The text value will have "\n" added to the end of it.
     * @param text text value.
     * @param background the background for the text.
     */
    public void printlnWithBackground(String text, Color background) {
        printWithBackground(text+"\n", background);
    }
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the default background and foreground
     * (specified by the <code>DrawPreference</code>). If the <code>JScrollPane</code>'s vertical scroll 
     * bar is at the bottom then it will stay at the bottom - otherwise it will stay at the current 
     * value. All the <code>{@link lal.console.output.OutputListener}s</code>
     * will be notified of the message. The text value 
     * will have "\n" added to the end of it.
     * @param text text value.
     */
    public void println(String text) {
        print(text+"\n");
    }
    
    /**
     * Appends "\n" to the end of the <code>JTextPane</code>. If the <code>JScrollPane</code>'s 
     * vertical scroll bar is at the bottom then it will stay at the bottom - otherwise it will 
     * stay at the current value. All the <code>{@link lal.console.output.OutputListener}s</code>
     * will be notified of the message.
     */
    public void println() {
        print("\n");
    }
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the error colors (specified by 
     * the <code>DrawPreference</code>). If the <code>JScrollPane</code>'s vertical scroll bar is 
     * at the bottom then it will stay at the bottom - otherwise it will stay at the current value. 
     * All the <code>{@link lal.console.output.OutputListener}s</code> will be notified of the message.
     * @param text text value.
     */
    public void printError(String text) {
        boolean moveToBot = false;
        if (output.atBottomOfOutput()){
            moveToBot = true;
        }
        Message msg = dp.styleText(text.replaceAll("\n", ""), MessageType.ERROR);
        output.appendToOutput(text, msg.getTextColor(), msg.getBackground());
        oh.alertListeners(msg);
        if (moveToBot){
            output.moveToBottomOfOutput();
        }
    }
    
    /**
     * Appends text to the end of the <code>JTextPane</code> with the error colors (specified by 
     * the <code>DrawPreference</code>). If the <code>JScrollPane</code>'s vertical scroll bar is 
     * at the bottom then it will stay at the bottom - otherwise it will stay at the current value. 
     * All the <code>{@link lal.console.output.OutputListener}s</code>
     * will be notified of the message. The text value will 
     * have "\n" added to the end of it.
     * @param text text value.
     */
    public void printlnError(String text) {
        printError(text+"\n");
    }
    
    /**
     * Adds this <code>{@link lal.console.input.InputListener}</code> to the handler. 
     * <p>
     * This <code>InputListener</code> will be notified 
     * whenever the user inputs something in the input field (<code>JTextField</code>).
     * @param in input listener.
     */
    public void addInputListener(InputListener in){
        ih.addInputListener(in);
    }
    
    /**
     * Removes this <code>{@link lal.console.input.InputListener}</code> from the handler.
     * <p>
     * This <code>InputListener</code> will be notified 
     * whenever the user inputs something in the input field (<code>JTextField</code>).
     * @param in input listener.
     */
    public void removeInputListener(InputListener in){
        ih.removeInputListener(in);
    }
    
    /**
     * Adds this <code>{@link lal.console.output.OutputListener}</code> to the handler.
     * <p>
     * The <code>OutputListener</code> will be notified whenever something is added to the output 
     * using the any of the <code>print()</code> methods.
     * @param out output listener
     */
    public void addOutputListener(OutputListener out) {
        oh.addListener(out);
    }
    
    /**
     * Removes this <code>{@link lal.console.output.OutputListener}</code> from the handler.
     * <p>
     * The <code>OutputListener</code> will be notified whenever something is added to the output 
     * using the any of the <code>print()</code> methods.
     * @param out output listener
     */
    public void removeOutputListener(OutputListener out) {
        oh.removeListener(out);
    }
    
    /**
     * Adds this <code>{@link lal.console.ui.AutoComplete}</code> to the frame's <code>ACManager</code>.
     * <p>
     * A <code>AutoComplete</code> is called whenever the user starts typing in the input field 
     * (<code>JTextField</code>).
     * @param ac <code>AutoComplete</code> to add.
     */
    public void addAutoComplete(AutoComplete ac){
        output.getACManager().addAutoCompleter(ac);
    }
    
    /**
     * Removes this <code>{@link lal.console.ui.AutoComplete}</code> from the frame's <code>ACManager</code>.
     * <p>
     * A <code>AutoComplete</code> is called whenever the user starts typing in the input field 
     * (<code>JTextField</code>).
     * @param ac <code>AutoComplete</code> to remove.
     */
    public void removeAutoComplete(AutoComplete ac){
        output.getACManager().removeAutoCompleter(ac);
    }
    
    /**
     * Hides the console from view by calling the <code>setVisible(false)</code> method on the frame.
     */
    public void hide() {
        output.hide();
    }
    
    /**
     * Makes sure that the frame is visible and the main focus.
     */
    public void show() {
        output.show();
    }
    
    /**
     * @return true if the console is visible and false otherwise.
     */
    public boolean isShowing(){
        return output.getFrame().isVisible();
    }
    
    /**
     * Removes data and the <code>JFrame</code> by calling the <code>dispose()</code> method on it. 
     * Once this method is called this instance will be useless.
     */
    public void kill() {
        output.trash();
    }
    
    /**
     * Styles the console with the current <code>DrawPreference</code>.
     * <p>
     * Note: changing the <code>DrawPreference</code> will not sync with the console. This method 
     * must be called after any changes are made to the <code>DrawPreference</code>.
     */
    public void restyle(){
        try {
            output.styleComponents(dp);
        } catch (InvalidDrawPreferences ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Handles the text in the input field.
     */
    public void handleTextFromInput(){
        ih.handleTextFromInput();
    }
    
    /**
     * Sets the close operation for the <code>JFrame</code> by calling the 
     * <code>setDefaultCloseOperation()</code> on the <code>JFrame</code>.
     * @param operation the new close operation for the <code>JFrame</code>.
     */
    public void setCloseOperation(int operation){
        output.setCloseOperation(operation);
    }
    
    /**
     * sets the title of the <code>JFrame</code>. If the title was set in the 
     * <code>{@link lal.console.ui.preferences.DrawPreference}</code> then this will override it.
     * @param title new title for the window, <code>JFrame</code>.
     */
    public void setWindowTitle(String title){
        output.setWindowTitle(title);
    }
    
    // gets
    
    /**
     * The <code>DrawPreference</code> is used when the <code>{@link #restyle()}</code> method is called. 
     * The <code>DrawPreference</code> sets the default text colors and the look and feel for the 
     * whole Window.
     * @param dp <code>DrawPreferences</code> used to style the console.
     */
    public void setDrawPreference(DrawPreference dp){
        this.dp = dp;
    }
    
    /**
     * The <code>DrawPreference</code> is used when the <code>{@link #restyle()}</code> method is called. 
     * The <code>DrawPreference</code> sets the default text colors and the look and feel for the 
     * whole Window.
     * @return <code>DrawPreferences</code> used to style the console.
     */
    public DrawPreference getDrawPreference(){
        return dp;
    }
    
}
