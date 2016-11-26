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
package io.github.lal872k.console.input;

import io.github.lal872k.console.Console;
import io.github.lal872k.console.ui.Frame;
import java.util.ArrayList;

/**
 *
 * @author L. Arthur Lewis II
 */
public class InputHandler {
    
    private final ArrayList<InputListener> listeners;
    
    private final Frame frame;
    
    private final Console console;
    
    public InputHandler(Frame frame, Console console){
        this.console = console;
        this.frame = frame;
        listeners = new ArrayList<InputListener>();
    }
    
    /**
     * handles a message by sending it out to all the listeners.
     * @param message message sent out.
     */
    public synchronized void handle(String message) {
        for (InputListener in : listeners){
            in.input(message, console);
        }
    }
    
    /**
     * handles the text in the input field and then clears the input field.
     */
    public void handleTextFromInput() {
        handle(frame.getInputField().getText());
        frame.getInputField().setText("");
    }
    
    /**
     * adds a input listener to the list.
     * @param in input listener.
     */
    public synchronized void addInputListener(InputListener in){
        listeners.add(in);
    }
    
    /**
     * removes a input listener from the list.
     * @param in input listener.
     */
    public synchronized void removeInputListener(InputListener in){
        listeners.remove(in);
    }
    
}
