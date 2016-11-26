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

import io.github.lal872k.console.extensions.command.samples.SubstitutionCipher;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author L. Arthur Lewis II
 */
public final class AutoCompleteGUI extends JPanel {
    
    private final JTextField field;
    private final ACManager manager;
    private final Frame frame;
    
    // limits
    private int predictionLimit = 5;
    
    private String[] predictions = new String[0];
    private int selected = 0;
    
    // look and feel
    private int textIndent = 10;
    private Color border = Color.BLACK;
    private Color selectedBackground = Color.GRAY;

    public AutoCompleteGUI(Frame frame, ACManager manager) {
        this.frame = frame;
        this.field = frame.getInputField();
        this.manager = manager;
        addUpdateListener(field);
        setOpaque(false);
    }
    
    public void addUpdateListener(JTextField comp){
        comp.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key==KeyEvent.VK_UP){
                    selected = SubstitutionCipher.moveWithLimits(selected, 1, 0, predictions.length);
                } else if (key==KeyEvent.VK_DOWN){
                    selected = SubstitutionCipher.moveWithLimits(selected, -1, 0, predictions.length);
                } else if (key==Frame.SEND_KEY && selected!=0){
                    field.setText(predictions[selected-1] + " ");
                }
                if (selected==0){
                    frame.setSendKeyEnabled(true);
                } else {
                    frame.setSendKeyEnabled(false);
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        comp.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                change();
            }
            public final void change(){
                refreshPredictions();
                repaint();
            }
        });
    }
    
    public void refreshPredictions(){
        selected = 0;
        int length;
        String[] pre = manager.getPredictions(field.getText());
        if (pre.length>predictionLimit){
            length = predictionLimit;
        } else {
            length = pre.length;
        }
        predictions = Arrays.copyOf(pre, length);
    }
    
    @Override
    public void paint(Graphics g){
        if (field.getText().length() > 0 && predictions.length > 0){
            Graphics2D g2 = (Graphics2D) g;
            
            int width = field.getWidth();
            int height = field.getHeight();
            int x = 0;
            int starty = getHeight()-height-1;
            
            FontMetrics met = g2.getFontMetrics(field.getFont());
            int textheight = met.getHeight()-met.getDescent();
            
            for (int q = 0; q < predictions.length; q++){
                String current = predictions[q];
                
                // background
                Color background;
                if (q+1==selected){
                    background = selectedBackground;
                } else {
                    background = getBackground();
                }
                g2.setColor(getPercentageTransparentColor(background, .85f));
                g2.fillRect(x, starty-(q*height), width, height);
                
                // border
                g2.setColor(getPercentageTransparentColor(border, .85f));
                g2.drawRect(x, starty-(q*height), width, height);
                
                // text
                g2.setColor(getForeground());
                g2.setFont(getFont());
                g2.drawString(current, x+textIndent, (starty-(q*height))+(textheight/2)+(height/2));
            }
            g2.dispose();
        }
    }
    
    public static Color getTransparentColor(Color color, int trans){
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), trans);
    }
    
    public static Color getPercentageTransparentColor(Color color, float trans){
        return getTransparentColor(color, (int) (trans*255l));
    }
    
    public void setPredictionLimit(int predictionLimit){
        this.predictionLimit = predictionLimit;
    }
    
    public void setTextIndent(int textIndent){
        this.textIndent = textIndent;
    }
    
    public void setPredictionBorders(Color border){
        this.border = border;
    }
    
    public void setSelectedBackground(Color selectedBackground){
        this.selectedBackground = selectedBackground;
    }
    
    public int getPredictionLimit(){
        return predictionLimit;
    }
    
    public int getTextIndent(){
        return textIndent;
    }
    
    public Color getPredictionBorders(){
        return border;
    }
    
    public Color getSelectedBackground(){
        return selectedBackground;
    }
    
}
