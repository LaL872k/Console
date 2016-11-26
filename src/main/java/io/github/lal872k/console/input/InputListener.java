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



/**
 * This <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">
 * functional interface</a> is used to listen for input from the user. When ever text is entered through
 * the input field all of the <code>{@link lal.console.Console}'s</code> <code>InputListeners</code> 
 * will be notified with the {@link #input(String, Console)} method.
 * @author L. Arthur Lewis II
 */
public interface InputListener {
    
    /**
     * This method is called whenever something is sent from the user using the input field 
     * (<code>JTextField</code>).
     * @param text text input
     * @param console console that the input came from.
     */
    void input(String text, Console console);
    
}
