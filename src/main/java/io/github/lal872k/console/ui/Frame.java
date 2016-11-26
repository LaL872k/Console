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
package io.github.lal872k.console.ui;

import io.github.lal872k.console.Console;
import io.github.lal872k.console.ui.preferences.DrawPreference;
import io.github.lal872k.console.ui.preferences.exceptions.InvalidDrawPreferences;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * This is the main GUI class that uses many <code>Swing</code> components to make the console. 
 * All of the components can be accessed through the getters. This class contains its own 
 * <code>ACManager</code> for its <code>AutoCompleteGUI</code>.
 * @author L. Arthur Lewis II
 */
public class Frame implements ActionListener, KeyListener{
    
    private final Console console;
    
    private JFrame frame;
    
    private JTextPane output;
    private JScrollPane outScroll;
    private AutoCompleteGUI autoComplete;
    private JLayeredPane outputArea;
    
    private JTextField input;
    
    private JButton send;
    
    private ACManager acmanager;
    
    public static final String WINDOW_TITLE = "Console";
    
    // TODO : may move this to DrawPref
    public static final int SEND_KEY = KeyEvent.VK_ENTER;
    
    private boolean sendKeyEnabled = true;
    
    private final Dimension initialSize = new Dimension(1000, 540);
    
    public Frame(Console console, DrawPreference dp){
        this.console = console;
        init(dp);
    }
    
    private void init(DrawPreference dp){
        acmanager = new ACManager();
        setupFrame();
        setupComponents();
        try {
            styleComponents(dp);
        } catch (InvalidDrawPreferences ex) {
            ex.printStackTrace();
        }
        finalizeFrame();
    }
    
    private void setupFrame(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(initialSize);
        frame.setTitle(WINDOW_TITLE);
    }
    
    /**
     * sets up and adds all the components used on the console including the "send" button, input field, and
     * output area.
     */
    private void setupComponents(){
        Container main = frame.getContentPane();
        main.setLayout(new GridBagLayout());
        
        GridBagConstraints bag = new GridBagConstraints();
        
        input = new JTextField();
        input.addKeyListener(this);
        
        output = new JTextPane();
        outScroll = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        autoComplete = new AutoCompleteGUI(this, acmanager);
        
        outputArea = new JLayeredPane();
        
        // resize cause jlayeredpane doesnt have layout manager
        // http://stackoverflow.com/questions/20162151/java-swing-jlayeredpane-not-showing-up
        outputArea.addComponentListener(new ComponentAdapter() {
            private void resizeLayers() {
                outScroll.setBounds(new Rectangle(new Point(0,0), outputArea.getSize()));
                autoComplete.setBounds(new Rectangle(new Point(0,0), outputArea.getSize()));
            }

            @Override
            public void componentShown(ComponentEvent e) {
                resizeLayers();
            }

            @Override
            public void componentResized(ComponentEvent e) {
                resizeLayers();
            }
         });
        
        outputArea.add(outScroll, new Integer(0));
        outputArea.add(autoComplete, new Integer(1));
        
        send = new JButton();
        send.addActionListener(this);
        
        main.add(addComponent(outputArea, 0, 0, 2, 1, 1.0, 1.0, bag), bag);
        
        main.add(addComponent(input, 0, 1, 1, 1, 1.0, 0, bag), bag);
        
        main.add(addComponent(send, 1, 1, 1, 1, 0, 0, bag), bag);
    }
    
    /**
     * styles the console using the <code>DrawPreference</code> used.
     * @param pref <code>DrawPreference</code> to use to style the frame.
     * @throws InvalidDrawPreferences 
     */
    public void styleComponents(DrawPreference pref) throws InvalidDrawPreferences{
        if (!pref.isValid()){
            throw new InvalidDrawPreferences("Invalid Draw Preference: " + pref.toString());
        }
        pref.styleConsole(this);
    }
    
    /**
     * last thing called in the initialization of this class.
     */
    private void finalizeFrame(){
        frame.setLocationRelativeTo(null);
    }
    
    private JPanel addComponent(Component comp, int x, int y, int width, int height, 
            double weightx, double weighty, GridBagConstraints bag){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        bag.gridx = x;
        bag.gridy = y;
        bag.gridwidth = width;
        bag.gridheight = height;
        bag.fill = GridBagConstraints.BOTH;
        bag.weightx = weightx;
        bag.weighty = weighty;
        panel.add(comp);
        return panel;
    }
    
    /**
     * hides the <code>JFrame</code> from view.
     */
    public void hide() {
        frame.setVisible(false);
    }
    
    /**
     * dispose of the <code>JFrame</code> making this console useless.
     */
    public void trash() {
        frame.dispose();
    }
    
    /**
     * if this console is currently not visible it will make it visible and request the focus.
     * Then the input will request the focus.
     */
    public void show() {
        if (!frame.isVisible()){
            frame.setVisible(true);
        } else {
            frame.requestFocus();
        }
        input.requestFocus();
    }
    
    /**
     * adds text to the end of the output while ensuring that the placement of the caret is not
     * changed.
     * @param msg message to add to end.
     * @param f color to add it in.
     * @param b background color of text.
     */
    public void appendToOutput(String msg, Color f, Color b){
        // make doc from output
        StyledDocument document = output.getStyledDocument();
        StyleContext context = new StyleContext();
        // build a style
        Style style = context.addStyle("test", null);
        // set some style properties
        StyleConstants.setForeground(style, f);
        StyleConstants.setBackground(style, b);
        
        // get the position of the caret before it is changed
        int caretPos = output.getCaretPosition();
        
        // add the text
        try {
            document.insertString(document.getLength(), msg, style);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        
        // set the position of the caret to before text was added
        output.setCaretPosition(caretPos);
    }
    
    /**
     * sets the title of the <code>JFrame</code>. If the title was set in the 
     * <code>{@link lal.console.ui.preferences.DrawPreference}</code> then this will override it.
     * @param title new title for the window, <code>JFrame</code>.
     */
    public void setWindowTitle(String title){
        frame.setTitle(title);
    }
    
    /**
     * checks to see if the view of the output is at the bottom of it.
     * @return true if the view of the output is at the bottom, false otherwise.
     */
    public boolean atBottomOfOutput(){
        int[] values = getScrollBarValues();
        return values[0]+values[1]==values[2];
    }
    
    /**
     * moves the view of the output to the bottom of the output
     */
    public void moveToBottomOfOutput(){
        // make sure that you have the right values
        frame.validate();
        // move value to bottom (max-view)
        int[] values = getScrollBarValues();
        outScroll.getVerticalScrollBar().setValue(values[2]-values[0]);
    }
    
    /**
     * returns a array in of values that correspond to the scroll bar.
     * <p>
     * view, distance, max
     * @return look above for return.
     */
    private int[] getScrollBarValues(){
        int view = outScroll.getVerticalScrollBar().getVisibleAmount();
        int distance = outScroll.getVerticalScrollBar().getValue();
        int max = outScroll.getVerticalScrollBar().getMaximum();
        return new int[]{view, distance, max};
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        console.handleTextFromInput();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==SEND_KEY && sendKeyEnabled){
            console.handleTextFromInput();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
    /**
     * calls <code>setDefaultCloseOperation()</code> on the <code>JFrame</code>.
     * @param operation the closing operation for the <code>JFrame</code>.
     */
    public void setCloseOperation(int operation){
        frame.setDefaultCloseOperation(operation);
    }
    
    /**
     * <code>sendKeyEnabled</code> effects whether or not pressing the <code>SEND_KEY</code> will 
     * handle the text in the input field. Note that this is not the <code>sendButton</code>.
     * @param sendKeyEnabled sendKeyEnabled value.
     */
    public void setSendKeyEnabled(boolean sendKeyEnabled){
        this.sendKeyEnabled = sendKeyEnabled;
    }
    
    /**
     * <code>sendKeyEnabled</code> effects whether or not pressing the <code>SEND_KEY</code> will 
     * handle the text in the input field. Note that this is not the <code>sendButton</code>.
     * @return sendKeyEnabled value.
     */
    public boolean sendKeyEnabled(){
        return sendKeyEnabled;
    }
    
    /**
     * @return The window, <code>JFrame</code>.
     */
    public JFrame getFrame(){
        return frame;
    }
    
    /**
     * @return The output (where all the text goes), <code>JTextPane</code>.
     */
    public JTextPane getTextPane(){
        return output;
    }
    
    /**
     * @return The scroll pane that contains the output.
     */
    public JScrollPane getScroll(){
        return outScroll;
    }
    
    /**
     * @return The input field where the user can type.
     */
    public JTextField getInputField(){
        return input;
    }
    
    /**
     * @return The send button, <code>JButton</code>.
     */
    public JButton getSendButton(){
        return send;
    }
    
    /**
     * @return This is the <code>JPanel</code> where all of the auto complete options go.
     */
    public AutoCompleteGUI getAutoComplete(){
        return autoComplete;
    }
    
    /**
     * @return This is where both the <code>autoComplete</code> and <code>output</code> goes.
     */
    public JLayeredPane getOutputArea(){
        return outputArea;
    }
    
    /**
     * @return This is the <code>ACManager</code> that contains all of the 
     * <code>{@link lal.console.ui.AutoComplete}s</code>.
     */
    public ACManager getACManager(){
        return acmanager;
    }
    
    /**
     * @return The <code>JFrame.getContentPane()</code>.
     */
    public Container getMainContainer(){
        return frame.getContentPane();
    }
    
}
