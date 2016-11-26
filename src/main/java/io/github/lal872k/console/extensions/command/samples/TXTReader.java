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
package io.github.lal872k.console.extensions.command.samples;

import io.github.lal872k.console.Console;
import io.github.lal872k.console.extensions.command.CommandExtension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author L. Arthur Lewis II
 */
public final class TXTReader extends CommandExtension {
    
    public static final String KEYWORD = "txtreader";
    public static final String FIELDS = "filePath";
    
    public TXTReader(){
        super(KEYWORD, FIELDS);
    }
    
    @Override
    public void execute(Console console, String[] arguments) {
        if (arguments.length>0){
            TXTFile file = new TXTFile();
            if (file.loadFile(arguments[0], console)){
                file.printData(console);
            }
        } else {
            console.printlnError("Need to provide file path");
        }
    }
    
    private class TXTFile {
        private File file;
        private int charcount;
        private int wordcount;
        
        public boolean loadFile(String loc, Console console){
            file = new File(loc);
            if (!file.exists()){
                console.printlnError("File given does not exist.");
                return false;
            }
            if (!file.canRead()){
                console.printlnError("File given can not be read.");
                return false;
            }
            StringBuilder allText = new StringBuilder();
            BufferedReader scan;
            try {
                scan = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException ex) {
                console.printlnError("Issue while making reader: " + ex.getMessage());
                return false;
            }
            String line;
            try {
                while ((line=scan.readLine())!=null){
                    allText.append(line);
                }
            } catch (IOException ex) {
                console.printlnError("Issue while reading file: " + ex.getMessage());
                return false;
            }
            try {
                scan.close();
            } catch (IOException ex) {
                console.printlnError("Issue while closing reader: " + ex.getMessage());
                return false;
            }
            charcount = allText.length();
            wordcount = allText.toString().split(" ").length;
            return true;
        }
        
        public void printData(Console console){
            console.println("Character Count: " + charcount);
            console.println("Word Count: " + wordcount);
        }
        
        public void printDataSearch(String search, Console console){
            
        }
        
    }
    
}
