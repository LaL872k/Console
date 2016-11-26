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

import io.github.lal872k.console.extensions.command.CommandHandler;
import io.github.lal872k.console.extensions.command.samples.SubstitutionCipher;
import io.github.lal872k.console.extensions.command.samples.TXTReader;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import io.github.lal872k.console.extensions.jconsole.JavaConsole;
import io.github.lal872k.console.extensions.waitin.WaitInput;
import io.github.lal872k.console.ui.preferences.DefaultDrawPreferences;

/**
 *
 * @author L. Arthur Lewis II
 */
public class Sample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Console console = new Console();
        console.show();
        console.setCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        console.addInputListener((text, inConsole) -> {
            // print out the text to whatever console this comes from
            inConsole.println(text); 
        });

        console.addOutputListener((message, outConsole) -> {
            // open dialog box with message
            JOptionPane.showMessageDialog(null, "Message: "+message.getText());
        });
        // set the draw preference to default
        console.setDrawPreference(new DefaultDrawPreferences());
        // print out error text color
        console.println("Text color of error: "+console.getDrawPreference()
                .styleText("", MessageType.ERROR) // draw prefernece is tasked with styling text
                .getTextColor()); // when it styles the text it returns a message instance with text, foreground/background color, and message tye(error/normal)
        
        JavaConsole jc = new JavaConsole();
        jc.addToConsole(console);
        
        WaitInput wi = new WaitInput();
        wi.addToConsole(console);
        console.print("Age: ");
        String age = wi.read();
        console.println("Your age is "+age);
        
        CommandHandler ch = new CommandHandler();
        ch.addCommand(new TXTReader());
        ch.addCommand(new SubstitutionCipher());
        
    }
    
}
