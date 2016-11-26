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

/**
 * This is a <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html">
 * functional interface</a> that's managed by the <code>ACManager</code>. It's main purpose is to 
 * guess what the user will say based on what they have typed in the input field so far. For more 
 * information on how the method works look at the method {@link #complete(String)}.
 * @author L. Arthur Lewis II
 */
public interface AutoComplete {
    
    /**
     * Called whenever the user starts typing in the input field (<code>JTextField</code>). All 
     * <code>String</code>s returned will be shown above the input field. If a user selects any of 
     * the options it will set the text inside the input field to the auto complete text that they 
     * selected.
     * <p>
     * For ex.: If the text inside the input value is "have a" and they select the auto complete 
     * "have a nice day". The input field value will be set to "have a nice day".
     * @param text the text inside the input field.
     * @return All auto complete options.
     */
    String[] complete(String text);
    
}
