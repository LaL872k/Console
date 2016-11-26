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
package io.github.lal872k.console.extensions;

import io.github.lal872k.console.Console;


/**
 * This interface allows for easy adding and removing of any listeners to the 
 * <code>{@link lal.console.Console}</code>.
 * 
 * @author L. Arthur Lewis II
 */
public interface ConsoleExtension {
    
    /**
     * Adds any necessary <code>{@link lal.console.input.InputListener}</code> and/or 
     * <code>{@link lal.console.output.OutputListener}</code> to the console
     * for the extension to work.
     * @param console console to be added to.
     */
    void addToConsole(Console console);
    
    /**
     * Removes any involvement with the <code>{@link lal.console.input.InputListener}</code> 
     * and/or <code>{@link lal.console.output.OutputListener}</code> 
     * from the console.
     * @param console console to be removed from.
     */
    void removeFromConsole(Console console);
    
}
