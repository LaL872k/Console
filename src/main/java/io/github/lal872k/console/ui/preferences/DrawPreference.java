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
package io.github.lal872k.console.ui.preferences;

import io.github.lal872k.console.Message;
import io.github.lal872k.console.MessageType;
import io.github.lal872k.console.ui.Frame;


/**
 * This is used to style <code>{@link lal.console.ui.Frame}s</code> and set the default text colors 
 * for normal text and error text.
 * @author L. Arthur Lewis II
 */
public interface DrawPreference {
    
    /**
     * Styles the console and all of it's components.
     * @param frame the frame that the console is using to print out data to.
     */
    void styleConsole(Frame frame);
    
    /**
     * Styles the text based on the message and the type of the message. This should return the 
     * default message colors.
     * <p>
     * Note: Colors can be overridden by the <code>{@link lal.console.Console}s</code> 
     * <code>print(background, forground)</code> methods.
     * @param text text of message.
     * @param type the type of message
     * @return the styled message with the defaults value.
     */
    Message styleText(String text, MessageType type);
    
    /**
     * Checks to see of this <code>{@link lal.console.ui.preferences.DrawPreference}</code> 
     * is valid, and that non of the values being 
     * used are invalid.
     * @return status of validity.
     */
    boolean isValid();
    
}
