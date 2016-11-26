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
package io.github.lal872k.console.extensions.command;

/**
 * Used to handle input form the <code>CommandHandler</code>. If you want to make a child of this class 
 * use <code>CommandExtension</code> this class works better for making quick commands.
 * @author L. Arthur Lewis II
 */
public class Command {
    
    private final String keyword;
    private final String fields;
    private Executable executer;
    
    protected Command(String keyword){
        this.keyword = keyword;
        fields = "";
    }
    
    protected Command(String keyword, String fields){
        this.keyword = keyword;
        this.fields = fields;
    }
    
    public Command(String keyword, String fields, Executable executer){
        this.keyword = keyword;
        this.executer = executer;
        this.fields = fields;
    }
    
    public Command(String keyword, Executable executer){
        this.keyword = keyword;
        this.executer = executer;
        fields = "";
    }
    
    protected void setExecuter(Executable executer){
        this.executer = executer;
    }
    
    public final String getKeyword(){
        return keyword;
    }
    
    public final Executable getExecuter(){
        return executer;
    }
    
    public final String getFields(){
        return fields;
    }
    
}
