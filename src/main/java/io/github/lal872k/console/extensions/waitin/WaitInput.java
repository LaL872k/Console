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
package io.github.lal872k.console.extensions.waitin;

import io.github.lal872k.console.Console;
import io.github.lal872k.console.extensions.ConsoleExtension;
import io.github.lal872k.console.input.InputListener;


/**
 * This class is designed around waiting for user input to move on. It calls <code>wait()</code> 
 * in the <code>{@link #read()}</code> and <code>{@link #input(String, Console)}</code> has the 
 * <code>notify()</code> method.
 * @author L. Arthur Lewis II
 */
public class WaitInput implements ConsoleExtension, InputListener{
    
    private String text;
    
    @Override
    public void addToConsole(Console console) {
        console.addInputListener(this);
    }

    @Override
    public void removeFromConsole(Console console) {
        console.removeInputListener(this);
    }

    @Override
    public synchronized void input(String text, Console console) {
        this.text = text;
        notify();
    }
    
    /**
     * Waits for <code>{@link #input(String, Console)}</code> to notify it that something has been 
     * sent through the input field.
     * @return text sent through the input field.
     */
    public synchronized String read() {
        try {
            wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return text;
    }
    
}
