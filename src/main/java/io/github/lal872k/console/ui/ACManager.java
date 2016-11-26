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

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author L. Arthur Lewis II
 */
public class ACManager {
    
    private final ArrayList<AutoComplete> ac;
    
    public ACManager(){
        ac = new ArrayList();
    }
    
    public void addAutoCompleter(AutoComplete ac){
        this.ac.add(ac);
    }
    
    public void removeAutoCompleter(AutoComplete ac){
        this.ac.remove(ac);
    }
    
    public String[] getPredictions(String text){
        LinkedList<String> results = new LinkedList();
        for (AutoComplete auto : ac){
            String[] autoResults = auto.complete(text);
            for (String currentAutoResult : autoResults){
                results.add(currentAutoResult);
            }
        }
        return results.toArray(new String[0]);
    }
    
}
