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

/**
 * The type of message used by <code>{@link lal.console.Message}</code>. The value can be 
 * <code>{@link #NORMAL}</code> or <code>{@link #ERROR}</code>.
 * @author L. Arthur Lewis II
 */
public enum MessageType {
    /**
     * This should be the default value for any <code>{@link lal.console.Message}</code>.
     */
    NORMAL, 
    /**
     * Whenever an issue occurs you can use either 
     * <code>{@link lal.console.Console#printError(String)}</code> or 
     * <code>{@link lal.console.Console#printlnError(String)}</code>
     * from the <code>{@link lal.console.Console}</code> instance to print text in the error colors. 
     * In those cases the 
     * <code>{@link lal.console.MessageType}</code> of that 
     * <code>{@link lal.console.Message}</code> will be <code>ERROR</code>.
     */
    ERROR;
    
}
