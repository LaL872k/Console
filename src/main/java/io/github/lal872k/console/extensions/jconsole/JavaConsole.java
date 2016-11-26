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
package io.github.lal872k.console.extensions.jconsole;

import io.github.lal872k.console.Console;
import io.github.lal872k.console.Message;
import io.github.lal872k.console.MessageType;
import io.github.lal872k.console.extensions.ConsoleExtension;
import io.github.lal872k.console.output.OutputListener;


/**
 * JavaConsole is a simple class the implements <code>ConsoleExtension</code> and makes sure 
 * everything that is printed out to the console also get printed out to the java console.
 * @author L. Arthur Lewis II
 */
public class JavaConsole implements OutputListener, ConsoleExtension{
    
    @Override
    public void listen(Message message, Console console) {
        if (message.getType().equals(MessageType.NORMAL)){
            System.out.println(message.getText());
        } else if (message.getType().equals(MessageType.ERROR)){
            System.err.println(message.getText());
        }
    }

    @Override
    public void addToConsole(Console console) {
        console.addOutputListener(this);
    }

    @Override
    public void removeFromConsole(Console console) {
        console.removeOutputListener(this);
    }
    
}
