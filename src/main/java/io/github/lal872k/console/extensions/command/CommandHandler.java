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

import io.github.lal872k.console.Console;
import io.github.lal872k.console.extensions.ConsoleExtension;
import io.github.lal872k.console.input.InputListener;
import io.github.lal872k.console.ui.AutoComplete;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CommandHandler is a <code>ConsoleExtension</code> that lets you add <code>Command</code>s. Whenever 
 * the <code>Command</code>'s keyword is typed in the console that <code>Command</code>'s 
 * <code>Executable</code> will be called with any arguments trailing after the keyword. 
 * <p>
 * You can either make a <code>Command</code> instance or you can subclass <code>CommandExtension</code>. 
 * It's recommended that if you are making a separate class don't use <code>Command</code> 
 * as the parent, instead use <code>CommandExtension</code>. This is because if you extend 
 * <code>Command</code> you will not be forced to set a <code>Executable</code> unlike
 * <code>CommandExtension</code> which is abstract and implements <code>Exectable</code>.
 * <p>
 * Some samples are located in lal.console.extensions.command.samples
 * @author L. Arthur Lewis II
 */
public class CommandHandler implements ConsoleExtension, InputListener, AutoComplete {
    
    private ArrayList<Command> commands;
    
    public CommandHandler(){
        init();
    }
    
    /**
     * Initial method called in the constructor.
     */
    private void init(){
        commands = new ArrayList<Command>();
    }
    
    /**
     * Adds a <code>Command</code> to the list of <code>Command</code>s.
     * @param command New command.
     */
    public synchronized void addCommand(Command command) {
        commands.add(command);
    }
    
    /**
     * Removes a <code>Command</code> to the list of <code>Command</code>s.
     * @param command Command to be removed.
     */
    public synchronized void removeCommand(Command command) {
        commands.remove(command);
    }
    
    /**
     * Retrieves the <code>Command</code> with the matching <code>keyword</code>. If no 
     * <code>Command</code>s match it will return <code>null</code>.
     * @param keyword keyword to search for.
     * @return <code>Command</code> the matches the keyword.
     */
    public synchronized Command getCommand(String keyword){
        for (Command command : commands){
            if (command.getKeyword().toLowerCase().equals(keyword.toLowerCase())){
                return command;
            }
        }
        return null;
    }
    
    @Override
    public synchronized void addToConsole(Console console) {
        console.addInputListener(this);
        console.addAutoComplete(this);
    }
    
    @Override
    public synchronized void removeFromConsole(Console console) {
        console.removeInputListener(this);
        console.removeAutoComplete(this);
    }
    
    @Override
    public void input(String text, Console console) {
        if (text.length()>0){
            // first word is the keyword
            String keyword = text.split("\\s")[0];
            // rest are arguments
            String[] arguments = decodeCommand(text.substring(keyword.length()));
            // get command based off of keyword
            Command command = getCommand(keyword);
            
            // check if command exists
            if (command!=null){
                // run command
                command.getExecuter().execute(console, arguments);
            } else {
                console.printlnError("A command with that keyword could not be found.");
            }
        }
    }
    
    /**
     * The regex used inside <code>decodeCommand(String command)</code>.
     */
    public static final String COMMAND_DECODE_REGEX = "\\\"([^\\\"]*)\\\"|(?:\\s|^)([^\\\"\\s]+)";
    
    /**
     * Decodes any command and turns it into a array of strings. It splits it by spaces or anything 
     * inside a pair of quotation marks.
     * @param command the line to decode.
     * @return the arguments to the command.
     */
    public static String[] decodeCommand(String command){
        Pattern p = Pattern.compile(COMMAND_DECODE_REGEX);
        Matcher m = p.matcher(command);
        ArrayList<String> contents = new ArrayList();
        while (m.find()){
            for (int q = 0; q < m.groupCount(); q++){
                String group;
                // find first valid group
                if ((group = m.group(q+1)) != null){
                    contents.add(group);
                }
            }
        }
        return contents.toArray(new String[0]);
    }

    @Override
    public String[] complete(String text) {
        ArrayList<String> results = new ArrayList();
        for (Command command : commands){
            
            if ((command.getKeyword()+" "+command.getFields()).toLowerCase().startsWith(text.toLowerCase())){
                if (command.getFields().length()>0){
                    results.add(command.getKeyword()+" "+command.getFields());
                } else {
                    results.add(command.getKeyword());
                }
            }
        }
        return results.toArray(new String[0]);
    }
    
}
