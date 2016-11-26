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
package io.github.lal872k.console.output;

import io.github.lal872k.console.Console;
import io.github.lal872k.console.Message;


/**
 * This <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">
 * functional interface</a> is like an <code>EventListener</code> that listens for text to be printed 
 * to the output. Anything printed out to the console using any of the <code>print()</code> methods 
 * will have all their <code>OutputListener</code>s notified using {@link #listen(Message, Console)}.
 * @author L. Arthur Lewis II
 */
public interface OutputListener {
    
    /**
     * This method is called whenever something is appended to the end of the output using any of the 
     * <code>print()</code> methods on the console.
     * @param message message printed.
     * @param console console the message came from.
     */
    void listen(Message message, Console console);
    
}
