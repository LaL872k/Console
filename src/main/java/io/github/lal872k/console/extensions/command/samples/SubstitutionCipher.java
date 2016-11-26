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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Wikipedia: https://en.wikipedia.org/wiki/Substitution_cipher
 * @author L. Arthur Lewis II
 */
public class SubstitutionCipher extends CommandExtension{
    
    public static final String KEYWORD = "subci";
    public static final String FIELDS = "cryptType inputPath outputPath code";
    
    private enum CryptType {
        ENCRYPT, DECRYPT;
    }
    
    public SubstitutionCipher(){
        super(KEYWORD, FIELDS);
    }
    
    private static void crypt(InputStream in, OutputStream out, int offset, CryptType type) throws IOException {
        if (type.equals(CryptType.DECRYPT)){
            offset *= -1;
        }
        int currentByte;
        while ((currentByte=in.read())!=-1){
            out.write(moveWithLimits(currentByte, offset, 0, 256));
        }
        // close streams
        in.close();
        out.close();
    }
    
    private static CipherStats cryptFolder(File in, File out, int code, CryptType type) throws InvalidFileTypeException, FileNotFoundException, IOException {
        // make sure they are files
        if (isFile(in) || isFile(out)){
            throw new InvalidFileTypeException();
        }
        
        // input file doesnt exist
        if (!in.exists()){
            throw new FileNotFoundException("File path: "+in.getPath());
        }
        
        // output file doesnt exist
        if (!out.exists()){
            throw new FileNotFoundException("File path: "+out.getPath());
        }
        
        CipherStats stats = new CipherStats();
        
        stats.start();
        stats.setOldPath(in);
        stats.setNewPath(out);
        stats.setCode(code);
        stats.setMethod(type);
        
        ArrayList<PendingFile> files = new ArrayList();
        
        files = searchForFiles(in, in, files);
        
        stats.setFilesEncrypted(files.size());
        
        for (PendingFile oldFile : files){
            File newFile = oldFile.getNewFile(out);
            cryptFile(oldFile.getOldFile(), newFile, code, type);
        }
        
        stats.end();
        
        return stats;
    }
    
    private static CipherStats cryptFile(File in, File out, int code, CryptType type) throws FileNotFoundException, IOException, InvalidFileTypeException {
        // make sure they are files
        if (!isFile(in) || !isFile(out)){
            throw new InvalidFileTypeException();
        }
        
        // input file doesnt exist
        if (!in.exists()){
            throw new FileNotFoundException("File path: "+in.getPath());
        }
        
        // output file doesnt exist
        if (!out.exists()){
            out.getParentFile().mkdirs();
            out.createNewFile(); // throws io
        }
        
        CipherStats stats = new CipherStats();
        
        stats.start();
        stats.setOldPath(in);
        stats.setNewPath(out);
        stats.setCode(code);
        stats.setFilesEncrypted(1);
        stats.setMethod(type);
        
        crypt(new FileInputStream(in), new FileOutputStream(out), code, type);
        
        stats.end();
        
        return stats;
    }
    
    /**
     * Encrypts data from the input stream(<code>in</code>) and sends the encrypted data to the output
     * stream(<code>out</code>). It uses Substitution Ciphers to encrypt that data.
     * @param in raw data stream.
     * @param out encrypted data stream.
     * @param offset how much to subtract or add.
     * @throws IOException 
     */
    public static void encrypt(InputStream in, OutputStream out, int offset) throws IOException {
        crypt(in, out, offset, CryptType.ENCRYPT);
    }
    
    public static CipherStats encryptFolder(File in, File out, int code) throws InvalidFileTypeException, IOException {
        return cryptFolder(in, out, code, CryptType.ENCRYPT);
    }
    
    public static CipherStats encryptFolder(File in, File out) throws InvalidFileTypeException, IOException {
        return cryptFolder(in, out, generateCode(), CryptType.ENCRYPT);
    }
    
    public static CipherStats encryptFile(File in, File out, int code) throws IOException, InvalidFileTypeException {
        return cryptFile(in, out, code, CryptType.ENCRYPT);
    }
    
    public static CipherStats encryptFile(File in, File out) throws IOException, InvalidFileTypeException {
        return cryptFile(in, out, generateCode(), CryptType.ENCRYPT);
    }
    
    public static void decrypt(InputStream in, OutputStream out, int offset) throws IOException {
        crypt(in, out, offset, CryptType.DECRYPT);
    }
    
    public static CipherStats decryptFolder(File in, File out, int code) throws InvalidFileTypeException, IOException {
        return cryptFolder(in, out, code, CryptType.DECRYPT);
    }
    
    public static CipherStats decryptFile(File in, File out, int code) throws IOException, InvalidFileTypeException {
        return cryptFile(in, out, code, CryptType.DECRYPT);
    }
    
    public static int generateCode(){
        double range = 256.0d;
        return (int) (Math.random()*range);
    }
    
    public static boolean isFile(File file){
        return file.getPath().contains(".");
    }
    
    /**
     * searches a directory for all sub directories and files. It uses recursion on subdirectories and 
     * adds files to the list <code>files</code>. It stores everything as a <code>PendingFile</code>.
     * @param oldPath the old path that will be parsed to the <code>PendingFile</code>.
     * @param currentPath the current path to look in (this is what changes with recursion).
     * @param files the <code>ArrayList</code> of <code>PeningFile</code>s.
     * @return the <code>files</code> field given.
     */
    public static ArrayList<PendingFile> searchForFiles(File oldPath, File currentPath, ArrayList<PendingFile> files){
        File[] subFiles = currentPath.listFiles();
        for (File currentFile : subFiles){
            if (currentFile.isFile()){
                files.add(new PendingFile(oldPath, currentFile));
            }
            else if (currentFile.isDirectory()){
                files = searchForFiles(oldPath, currentFile, files);
            }
        }
        return files;
    }
    
    /**
     * adds <code>delta</code> to <code>num</code>, but whenever it reaches a limit - <code>min</code> or 
     * <code>max</code> it will reset to the opposite limit.
     * @param num starting number.
     * @param delta the number to add (negative for subtraction).
     * @param min the minumum value that <code>num</code> can be.
     * @param max the maximum value that <code>num</code> can be.
     * @return the new number after adding <code>delta</code>.
     */
    public static int moveWithLimits(int num, int delta, int min, int max){
        boolean positiveDelta = delta>0;
        while (delta!=0){
            if (positiveDelta){
                num++;
                delta--;
            } else {
                num--;
                delta++;
            }
            if (num>max){
                num=min;
            }
            if (num<min){
                num=max;
            }
        }
        return num;
    }
    
    @Override
    public void execute(Console console, String[] arguments) {
        // arg 1 = encrypt/decrypt | 2 = in | 3 = out | 4 = code
        
        if (arguments.length>0){
            
            CryptType direction;
            try {
                direction = CryptType.valueOf(arguments[0].toUpperCase());
            } catch (IllegalArgumentException e){
                console.printlnError("Need to clarify whether to (encrypt/decrypt).");
                return;
            }
            if (arguments.length==1){
                console.printlnError("Missing input path.");
                return;
            }
            if (arguments.length==2){
                console.printlnError("Missing output path.");
                return;
            }
            if (arguments.length==3 && direction.equals(CryptType.DECRYPT)){
                console.printlnError("Missing code to decrypt path with.");
                return;
            }
            
            File in = new File(arguments[1]);
            File out = new File(arguments[2]);
            int code = 0;
            if (arguments.length>=4){
                try{
                    code = Integer.parseInt(arguments[3]);
                } catch (NumberFormatException e){
                    console.printlnError("Code givin is invalid.");
                    return;
                }
            } else {
                code = generateCode();
            }
            
            // subci encrypt "D:\MyProfile\Desktop\Test.txt" "D:\MyProfile\Desktop\newFile.txt" 83
            
            // dir
            if (!isFile(in) && !isFile(out)){
                try {
                    CipherStats stats;
                    switch (direction) {
                        case DECRYPT:
                            stats = decryptFolder(in, out, code);
                            break;
                        case ENCRYPT:
                            stats = encryptFolder(in, out, code);
                            break;
                        default:
                            return;
                    }
                    console.println("Cipher Completed!");
                    stats.printData(console);
                } catch (InvalidFileTypeException e){
                    console.printlnError("Invalid File type given.");
                    e.printStackTrace();
                } catch (IOException ex) {
                    console.printlnError("An io issue occured. This could be do to file's not existing.");
                    ex.printStackTrace();
                }
            }
            // file
            else if (isFile(in) && isFile(out)){
                try {
                    CipherStats stats;
                    switch (direction) {
                        case DECRYPT:
                            stats = decryptFile(in, out, code);
                            break;
                        case ENCRYPT:
                            stats = encryptFile(in, out, code);
                            break;
                        default:
                            return;
                    }
                    console.println("Cipher Completed!");
                    stats.printData(console);
                } catch (InvalidFileTypeException e){
                    console.printlnError("Invalid File type given.");
                    e.printStackTrace();
                } catch (IOException ex) {
                    console.printlnError("An io issue occured. This could be do to file's not existing.");
                    ex.printStackTrace();
                }
            }
            // doesnt match
            else {
                console.printlnError("The input and output file type(dir or file) didn't match.");
            }
            
        } else {
            console.printlnError("Missing the (encrypt/decrypt) as first arument.");
        }
        
    }
    
    private static class PendingFile {
        
        private File oldFile, path;
        
        public PendingFile() {}
        
        public PendingFile(File path, File oldFile){
            this.path = path;
            this.oldFile = oldFile;
        }
        
        public void setFilePath(File path){
            this.path = path;
        }
        
        public void setOldFile(File oldFile){
            this.oldFile = oldFile;
        }
        
        public File getOldFile(){
            return oldFile;
        }
        
        public File getNewFile(File newpath){
            return new File(newpath.getPath()+oldFile.getPath().substring(path.getPath().length()));
        }
        
    }
    
    /**
     * stats for the cipher that can be displayed after the transfer has been completed.
     */
    public static class CipherStats {
        private File oldPath, newPath;
        private int filesEncrypted;
        private int code;
        private long startTime, endTime; // milliseconds
        private CryptType method;
        
        public CipherStats() {}
        
        public void setOldPath(File oldPath){
            this.oldPath = oldPath;
        }
        
        public void setNewPath(File newPath){
            this.newPath = newPath;
        }
        
        public void setFilesEncrypted(int filesEncrypted){
            this.filesEncrypted = filesEncrypted;
        }
        
        public void setCode(int code){
            this.code = code;
        }
        
        public void setMethod(CryptType method){
            this.method = method;
        }
        
        public void setStartTime(long startTime){
            this.startTime = startTime;
        }
        
        public void setEndTime(long endTime){
            this.endTime = endTime;
        }
        
        /**
         * sets the start time to now.
         */
        public void start(){
            startTime = System.currentTimeMillis();
        }
        
        /**
         * sets the end time to now.
         */
        public void end(){
            endTime = System.currentTimeMillis();
        }
        
        public File getOldPath(){
            return oldPath;
        }
        
        public File getNewPath(){
            return newPath;
        }
        
        public int getFilesEncrypted(){
            return filesEncrypted;
        }
        
        public int getCode(){
            return code;
        }
        
        public long getStartTime(){
            return startTime;
        }
        
        public long getEndTime(){
            return endTime;
        }
        
        public CryptType getMethod(){
            return method;
        }
        
        public long getTimeElapsed(){
            return endTime-startTime;
        }
        
        public void printData(Console console){
            console.println("Old Path: " + getOldPath());
            console.println("New Path: " + getNewPath());
            console.print("Files ");
            if (getMethod().equals(CryptType.ENCRYPT)){
                console.print("encrypted");
            } else {
                console.print("decrypted");
            }
            console.println(": " + getFilesEncrypted());
            console.println("Code: " + getCode());
            console.print("Method used: ");
            if (getMethod().equals(CryptType.ENCRYPT)){
                console.println("encryption");
            } else {
                console.println("decryption");
            }
            SimpleDateFormat format = new SimpleDateFormat("E, y-M-d 'at' h:m:s a z");
            console.println("Time started: " + format.format(getStartTime()));
            console.println("Time ended: " + format.format(new Date(getEndTime())));
            console.println("Time elapsed: " + (getTimeElapsed()*.001f));
        }
        
    }
    
    /**
     * used for when a <code>File</code> that is not the right type (directory/file).
     */
    public static class InvalidFileTypeException extends Exception{
        
        public InvalidFileTypeException() {}
        
        public InvalidFileTypeException(String msg) {
            super(msg);
        }
        
    }
    
}
