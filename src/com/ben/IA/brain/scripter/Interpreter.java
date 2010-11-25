/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain.scripter;

import com.ben.IA.ErrorLogger.ScriptErrorException;
import com.ben.IA.brain.scripter.Keys.KeyWord;
import com.ben.IA.brain.scripter.view.Scripter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author benoitdaccache
 */
public class Interpreter extends Thread implements Runnable {

    static synchronized void putInBuffer(String a, Object o) throws ScriptErrorException {
        checkIsKeyWordOrTrueFalse(a);
        buffer.put(a, o);
    }
//faire la lecture des vecteurs et aussi la compairason boolean et a=b[i] et liste??
    private static HashMap buffer;
    private static KeyWord key = KeyWord.getInstance();
    private Scripter console;
    static String[] sym = new String[]{"+", "-", "*", "/", "%"};
    static String[] opB = new String[]{"==", "<=", ">=", "<", ">", "!="};
    static String[] keyWord = new String[]{key.ifCond, key.elseCond, key.end, key.end + key.ifCond, key.whileLoop,
                                           key.end + key.whileLoop, key.thread, key.end + key.thread, key.function,
                                           key.end + key.function, key.returnStatement, key.begin};
    private Reader reader;
    private ArrayList<Thread> threadList;
    private Object returnVal = null;

    public Interpreter(Reader reader, Scripter frame) {
        super();
        buffer = new HashMap();
        threadList = new ArrayList<Thread>();
        this.console = frame;
        this.reader = reader;
        Interpreter.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

            public void uncaughtException(Thread t, Throwable e) {
                console.appendToConsole(e.getMessage());
            }
        });
    }

    @Override
    public void run() {
        startInterpret(reader);
    }

    /**
     *
     * @param pC
     * @return la valeur de la variable apres evaluation , cad incrementation decrementation ect...
     */
    private Object EvaluateVar(String pC, HashMap buffer) throws ScriptErrorException {
        if (isOpBool(pC))
            return opBool(pC, buffer);
        else if (pC.endsWith("++"))
            return getPostFixIncrementation(pC, buffer);
        else if (pC.endsWith("--"))
            return getPostFixDecrementation(pC, buffer);
        else if (isOpBin(pC)) {
            int a = 0;
            int i = 0;
            do {
                i = pC.indexOf(sym[a]);
                a++;
            } while (i == -1);
            String op = sym[a - 1];
            String tmp = pC;
            pC = pC.substring(0, i).trim();
            String var3 = tmp.substring(i + 1).trim();
            //aditionner var2 et var3 puis renvoyer la valeur
            long z1, z2;
            z1 = getOperandOrVariableValue(pC, buffer);
            z2 = getOperandOrVariableValue(var3, buffer);
            return operate(z1, z2, op);
        } else
            return getVar(pC, buffer);
    }

    private void declareFunction(Reader reader) throws ScriptErrorException {
        String readLine = reader.readLine().trim();
        String[] header = parseHeader(readLine);
        readLine = reader.readLine();
        StringBuilder code = new StringBuilder();
        while (!readLine.equals(key.end + key.function) && reader.canRead()) {
            code.append(readLine + "\n");
            readLine = reader.readLine();
        }
        if (readLine.equals(key.end + key.function)) {
            code.append(readLine);
            if (buffer.containsKey("function " + header[0]))
                throw new ScriptErrorException("<Error: function " + header[0] + " already exists>");
            buffer.put("function " + header[0], new String[]{header[1], code.toString()});
        } else
            throw new ScriptErrorException("<Error: expected endfunction>");
    }

    private void declareFunctions(Reader reader) throws ScriptErrorException {
        if (reader.nextLine().equals(key.begin))
            return;
        while (!reader.nextLine().equals(key.begin) && reader.canRead()) {
            if (reader.nextLine().startsWith(key.function))
                declareFunction(reader);
            else if (isComment(reader.nextLine()))
                reader.readLine();
            else
                throw new ScriptErrorException("<Error: expected function declaration");
            skipBlankOrComment(reader);
        }
    }

    private boolean isBoolean(String s) {
        return s.equals("true") || s.equals("false");
    }

    private boolean isIncrementation(String readLine) {
        return readLine.endsWith("++");
    }

    private boolean isDecrementation(String readLine) {
        return readLine.endsWith("--");
    }

    private boolean isReturn(String readLine) {
        return readLine.startsWith(key.returnStatement);
    }

    private void performReturn(String readLine, HashMap buffer, Reader reader) throws ScriptErrorException {
        if (!reader.nextLine().equals(key.end + key.function) && !reader.nextLine().equals(key.elseCond) && !reader.nextLine().equals(key.end + key.ifCond))
            throw new ScriptErrorException("<Error unexpected instruction after return>");
        String s = readLine.substring(key.returnStatement.length()).trim();
        if (!isFunctionCall(s))
            returnVal = getVar(s, buffer);
        else
            executeFunction(s, buffer);
    }

    private void printString(String toString) {
        while (toString.indexOf("\\n") != -1) {
            console.appendToConsole(toString.substring(0, toString.indexOf("\\n")) + "\n");
            toString = toString.substring(toString.indexOf("\\n") + 2);
        }
        console.appendToConsole(toString);
    }

    private void readImport(Reader reader) throws ScriptErrorException {
        StringBuilder str = null;
        if (reader.nextLine().startsWith("include")) {
            String path = reader.readLine().substring("include".length()).trim();
            if (new File(path).exists()) {
                BufferedReader r = null;
                try {
                    r = new BufferedReader(new FileReader(path));
                    str = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null)
                        str.append(line + "\n");
                } catch (IOException ex) {
                    Logger.getLogger(Interpreter.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        r.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Interpreter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Reader temp = new Reader(str.toString());
                readImport(temp);
                declareFunctions(temp);
            } else
                throw new ScriptErrorException("<Error: import " + path + " cannot be resolved");
        }
    }

    private void seqInstFunction(Reader reader, HashMap buffer) throws ScriptErrorException {
        do {
            String r = reader.readLine().trim();
            if (!r.equals(""))
                inst(r, reader, buffer);
        } while (!reader.nextLine().equals(key.end + key.function) && reader.canRead());
    }

    private void executeFunction(String readLine, HashMap buffer) throws ScriptErrorException {
        String funName = readLine.substring(0, readLine.indexOf("(")).trim();
        String param = readLine.substring(readLine.indexOf("(") + 1, readLine.indexOf(")"));
        if (Interpreter.buffer.containsKey("function " + funName)) {
            String p = ((String[]) Interpreter.buffer.get("function " + funName))[0].trim();
            StringTokenizer pCaller = new StringTokenizer(param);
            StringTokenizer pCalled = new StringTokenizer(p);
            if (pCalled.countTokens() == pCaller.countTokens()) {
                HashMap tempBuffer = new HashMap();
                while (pCalled.hasMoreTokens()) {
                    String pC = pCaller.nextToken();
                    Object val = EvaluateVar(pC, buffer);
                    tempBuffer.put(pCalled.nextToken(), val);
                }
                Reader tempReader = new Reader(((String[]) Interpreter.buffer.get("function " + funName))[1]);
                seqInstFunction(tempReader, tempBuffer);
            } else
                throw new ScriptErrorException("<Error: function " + funName + " has unmatching number of args>");
        } else
            throw new ScriptErrorException("<Error: function " + funName + " is not declared>");
    }

    private boolean isComment(String readLine) {
        return readLine.startsWith("#");
    }

    private boolean isFunctionCall(String readLine) {
        return readLine.contains("(") && readLine.contains(")");
    }

    private String[] parseHeader(String readLine) {
        String name = readLine.substring(key.function.length() + 1, readLine.indexOf("(")).trim();
        String param = readLine.substring(readLine.indexOf("(") + 1, readLine.length() - 1);
        return new String[]{name, param};
    }

    private void skipBlankOrComment(Reader read) {
        while ((read.nextLine().equals("") || read.nextLine().startsWith("//")) && read.canRead())
            read.readLine();
    }

    private void startInterpret(Reader reader) {
        program(reader);
        if (threadList.size() == 0)
            console.markstop();
    }

    private void program(Reader reader) {
        try {
            skipBlankOrComment(reader);
            readImport(reader);
            skipBlankOrComment(reader);
            declareFunctions(reader);
            skipBlankOrComment(reader);
            checkBegin(reader.readLine());
            seqInst(reader, buffer);
            checkEnd(reader.readLine());
        } catch (ScriptErrorException e) {
            console.appendToConsole(e.getMessage() + "\n");
        }
    }

    private void seqInst(Reader reader, HashMap buffer) throws ScriptErrorException {
        do {
            String r = reader.readLine().trim();
            if (!r.equals(""))
                inst(r, reader, buffer);
        } while (!reader.nextLine().equals(key.end) && reader.canRead());
    }

    private void inst(String readLine, Reader reader, HashMap buffer) throws ScriptErrorException {
        if (isReturn(readLine))
            performReturn(readLine, buffer, reader);
        else if (isComment(readLine))
            return;
        else if (isCondition(readLine))
            condition(readLine, reader, buffer);
        else if (isVectAffectation(readLine))
            VectAffectation(readLine, reader, buffer);
        else if (isRead(readLine))
            read(readLine, buffer);
        else if (isWrite(readLine))
            write(readLine, buffer);
        else if (Functor.isSleep(readLine))
            Functor.sleep(readLine);
        else if (Functor.isOsTime(readLine))
            Functor.osTime(readLine);
        else if (Files.isFileOperation(readLine))
            Files.performFileOperation(readLine, buffer);
        else if (isThread(readLine))
            thread(reader, buffer);
        else if (isLoop(readLine))
            loop(readLine, reader, buffer);
        else if (isAffectation(readLine))
            affectation(readLine, buffer);
        else if (isFunctionCall(readLine))
            executeFunction(readLine, buffer);
        else if (isIncrementation(readLine))
            postFixIncrementation(readLine, buffer);
        else if (isDecrementation(readLine))
            postFixDecrementation(readLine, buffer);
        else if (isDeclaration(readLine))
            declaration(readLine, buffer);
        else
            throw new ScriptErrorException("<Syntax error: expected instruction>");
    }

    private void VectAffectation(String readLine, Reader reader, HashMap buffer) throws ScriptErrorException {
        String var = readLine.substring(0, readLine.indexOf("["));//nom de la variable
        String item = readLine.substring(readLine.indexOf("[") + 1, readLine.indexOf("]")).trim();
        String e = readLine.substring(readLine.indexOf("=") + 1).trim();//nouvellevaleur
        if (isFunctionCall(readLine)) {
            executeFunction(e, buffer);
            try {
                if (returnVal == null)
                    throw new ScriptErrorException("<Error: missing return>");
                else if (buffer.get(var) instanceof Object[]) {
                    Object[] o = (Object[]) buffer.get(var);
                    long i = (Long) getVar(item, buffer);
                    try {
                        o[(int) i] = returnVal;
                        returnVal = null;
                    } catch (IndexOutOfBoundsException ez) {
                        throw new ScriptErrorException("<Error: Array out of bounds>");
                    }
                } else
                    throw new ScriptErrorException("<Error: variable " + var + " is not a vector>");
            } catch (IndexOutOfBoundsException ez) {
                throw new ScriptErrorException("<Error: Array out of bounds>");
            }
        } else if (buffer.containsKey(var))
            if (buffer.get(var) instanceof Object[]) {
                Object[] o = (Object[]) buffer.get(var);
                long i = (Long) getVar(item, buffer);
                try {
                    Object o1 = getVar(e, buffer);
                    o[(int) i] = o1;
                } catch (IndexOutOfBoundsException ez) {
                    throw new ScriptErrorException("<Error: Array out of bounds>");
                }
            } else
                throw new ScriptErrorException("<Error: variable " + var + " is not a vector>");
        else
            throw new ScriptErrorException("<Error: variable " + var + " does not exist>");
    }

    /**
     *
     * @return la valeur du parametre ou la valeur de la variable si c'est une variable
     */
    private Object getVar(String s, HashMap buffer) throws ScriptErrorException {
        String varName;
        if (s.contains("[") && s.contains("]")) {
            varName = s.substring(0, s.indexOf("["));
            String v = s.substring(s.indexOf("[") + 1, s.indexOf("]")).trim();
            long item;
            try {
                if (buffer.containsKey(v))
                    item = ((Long) buffer.get(v));
                else
                    item = Long.parseLong(v);
                if (buffer.get(varName) instanceof Object[])
                    return ((Object[]) buffer.get(varName))[(int) item];
                else
                    throw new ScriptErrorException("<Error expected vector>");
            } catch (NumberFormatException e) {
                throw new ScriptErrorException("<Error: expected int>");
            } catch (ArrayIndexOutOfBoundsException e2) {
                throw new ScriptErrorException("<Error: Array out of bounds>");
            }

        } else if (buffer.containsKey(s)) {
            Object o = buffer.get(s);
            return o;
        } else if (isString(s))
            return s.substring(1, s.length() - 1);
        else if (isNumber(s))
            return Long.parseLong(s);
        else if (isBoolean(s))
            return Boolean.parseBoolean(s);
        throw new ScriptErrorException("<Error: expected String or value>");
    }

    private void condition(String readLine, Reader reader, HashMap buffer) throws ScriptErrorException {
        if (!isOpBool(readLine))
            throw new ScriptErrorException("<Error: expected boolean>");
        int i = readLine.indexOf(key.ifCond + " ");
        String opBool = readLine.substring(i + 3).trim();
        if (!opBool(opBool, buffer))
            skipToElseOrEndIf(reader, buffer);
        else
            executeCondition(reader, buffer);
    }

    private boolean evaluateBoolean(Object var1, Object var2, String op) throws ScriptErrorException {
        try {
            if (var1 instanceof Long) {
                if (op.equals("=="))
                    return var1 == var2;
                else if (op.equals("<="))
                    return (Long) var1 <= (Long) var2;
                else if (op.equals(">="))
                    return (Long) var1 >= (Long) var2;
                else if (op.equals("<"))
                    return (Long) var1 < (Long) var2;
                else if (op.equals(">"))
                    return (Long) var1 > (Long) var2;
                else if (op.equals("!="))
                    return (Long) var1 != (Long) var2;
            } else if (var1 instanceof String) {
                if (op.equals("=="))
                    return var1.equals(var2);
                else if (op.equals("<="))
                    return ((String) var1).compareTo((String) var2) <= 0;
                else if (op.equals(">="))
                    return ((String) var1).compareTo((String) var2) >= 0;
                else if (op.equals("<"))
                    return ((String) var1).compareTo((String) var2) < 0;
                else if (op.equals(">"))
                    return ((String) var1).compareTo((String) var2) > 0;
                else if (op.equals("!="))
                    return !((String) var1).equals(var2);
            } else if (var1 instanceof Boolean)
                return var1.toString().equals(var2.toString());
            throw new ScriptErrorException("<Error: expected boolean condition>");
        } catch (ClassCastException e) {
            throw new ScriptErrorException("<Error: cannot compare>");
        }
    }

    private void executeCondition(Reader reader, HashMap buffer) throws ScriptErrorException {
        do {
            String r = reader.readLine().trim();
            if (!r.equals(""))
                inst(r, reader, buffer);
        } while ((!reader.nextLine().equals(key.end + key.ifCond) && !reader.nextLine().equals(key.elseCond)) && reader.canRead());
        checkEndOrElse(reader.readLine(), reader, buffer);
    }

    private void executeElse(Reader reader, HashMap buffer) throws ScriptErrorException {
        do {
            String r = reader.readLine().trim();
            if (!r.equals(""))
                inst(r, reader, buffer);
        } while (!reader.nextLine().equals(key.end + key.ifCond) && reader.canRead());
        if (!reader.readLine().equals(key.end + key.ifCond))
            throw new ScriptErrorException("<Error: expected endif>");
    }

    private void executeLoop(Reader reader, HashMap buffer) throws ScriptErrorException {
        do {
            String r = reader.readLine().trim();
            if (!r.equals(""))
                inst(r, reader, buffer);
        } while (!reader.nextLine().equals(key.end + key.whileLoop) && reader.canRead());
        checkEndWhile(reader.readLine());
    }

    private String getThreadedInst(Reader reader) {
        StringBuilder b = new StringBuilder();
        while (!reader.nextLine().equals(key.end + key.thread))
            b.append(reader.readLine() + "\n");
        b.append(reader.readLine());
        return b.toString();
    }

    private void loop(String readLine, Reader reader, HashMap buffer) throws ScriptErrorException {
        //<loop>:= while <opBool> <seqInst> end
        //stocker les instructions dans le loop
        String tmp = getLoopInst(reader);
        int i = readLine.indexOf(key.whileLoop) + key.whileLoop.length();
        String opBool = readLine.substring(i).trim();
        while (opBool(opBool, buffer)) {
            Reader tmpReader = new Reader(tmp);
            executeLoop(tmpReader, buffer);
        }
    }

    private boolean opBool(String exp, HashMap buffer) throws ScriptErrorException {
        String op = getOpBool(exp);
        int i = exp.indexOf(op);
        String var1 = exp.substring(0, i).trim();
        String var2 = exp.substring(i + op.length()).trim();
        Object o = getVar(var1, buffer);
        Object o2 = getVar(var2, buffer);
        return evaluateBoolean(o, o2, op);
    }

    private Long operate(long z1, long z2, String op) {
        switch (op.charAt(0)) {
            case '+':
                return z1 + z2;
            case '-':
                return z1 - z2;
            case '/':
                return z1 / z2;
            case '*':
                return z1 * z2;
            case '%':
                return z1 % z2;
        }
        return null;
    }

    private void seqInstThread(Reader reader, HashMap buffer) throws ScriptErrorException {
        do {
            String r = reader.readLine().trim();
            if (!r.equals(""))
                inst(r, reader, buffer);
        } while (!reader.nextLine().equals(key.end + key.thread) && reader.canRead());
        checkEndThread(reader.readLine());
    }

    private void skipToElseOrEndIf(Reader reader, HashMap buffer) throws ScriptErrorException {
        String r = reader.readLine().trim();
        while (!r.equals(key.end + key.ifCond) && !r.equals(key.elseCond) && reader.canRead())
            r = reader.readLine().trim();
        if (r.equals(key.elseCond))
            executeElse(reader, buffer);
        else if (!r.equals(key.end + key.ifCond))
            throw new ScriptErrorException("<Error: expected endif>");

    }

    private void declaration(String readLine, HashMap buffer) throws ScriptErrorException {
        String tmp = readLine.trim();
        checkIsKeyWordOrTrueFalse(tmp);
        if (tmp.contains(" "))
            throw new ScriptErrorException("<Syntax Error: expected variable>");
        else if (buffer.get(tmp) != null)
            throw new ScriptErrorException("<Error: variable " + tmp + " already exists>");
        else if (readLine.contains("[") && readLine.contains("]")) {
            String var = readLine.substring(0, readLine.indexOf("["));
            String length = readLine.substring(readLine.indexOf("[") + 1, readLine.indexOf("]"));
            if (isNumber(length))
                buffer.put(var, new Object[(int) Long.parseLong(length)]);
            else if (buffer.containsKey(length)) {
                long l = (Long) buffer.get(length);
                buffer.put(var, new Object[(int) l]);
            } else
                throw new ScriptErrorException("<Error: expected digit");
        } else
            buffer.put(tmp, null);
    }

    private boolean isOpBin(String s) {
        for (int i = 0; i < sym.length; i++)
            if (s.contains(sym[i]))
                return true;
        return false;
    }

    private void postFixIncrementation(String readLine, HashMap buffer) throws ScriptErrorException {
        int index = readLine.indexOf("++");
        String s = readLine.substring(0, index).trim();
        try {
            if (!buffer.containsKey(s))
                throw new ScriptErrorException("<Syntax error: expected variable");
            else {
                long val = ((Long) buffer.get(s));
                buffer.put(s, ++val);
                return;
            }
        } catch (ClassCastException e) {
            throw new ScriptErrorException("<Error: expected int>");
        }
    }

    private long getPostFixIncrementation(String readLine, HashMap buffer) throws ScriptErrorException {
        int index = readLine.indexOf("++");
        String s = readLine.substring(0, index).trim();
        try {
            if (!buffer.containsKey(s))
                throw new ScriptErrorException("<Syntax error: expected variable");
            else {
                long val = ((Long) buffer.get(s));
                return ++val;
            }
        } catch (ClassCastException e) {
            throw new ScriptErrorException("<Error: expected int>");
        }
    }

    private long getPostFixDecrementation(String readLine, HashMap buffer) throws ScriptErrorException {
        int index = readLine.indexOf("--");
        String s = readLine.substring(0, index).trim();
        try {
            if (!buffer.containsKey(s))
                throw new ScriptErrorException("<Syntax error: expected variable");
            else {
                long val = ((Long) buffer.get(s));
                return --val;
            }
        } catch (ClassCastException e) {
            throw new ScriptErrorException("<Error: expected int>");
        }
    }

    private void postFixDecrementation(String readLine, HashMap buffer) throws ScriptErrorException {
        int index = readLine.indexOf("--");
        String s = readLine.substring(0, index).trim();
        try {
            if (!buffer.containsKey(s))
                throw new ScriptErrorException("<Syntax error: expected variable");
            else {
                long val = ((Long) buffer.get(s));
                buffer.put(s, --val);
                return;
            }
        } catch (ClassCastException e) {
            throw new ScriptErrorException("<Error: expected int>");
        }
    }

    public String removeSpaces(String s) {
        StringTokenizer st = new StringTokenizer(s, " ", false);
        String t = "";
        while (st.hasMoreElements())
            t += st.nextElement();
        return t;
    }

    private void affectation(String readLine, HashMap buffer) throws ScriptErrorException {
        if (isFunctionCall(readLine)) {
            String funName = readLine.substring(readLine.indexOf("=") + 1).trim();
            String var = readLine.substring(0, readLine.indexOf("=")).trim();

            executeFunction(funName, buffer);
            if (returnVal == null)
                throw new ScriptErrorException("<Error: missing return>");
            else {
                buffer.put(var, returnVal);
                returnVal = null;
                return;
            }
        } else if (readLine.contains("[") && readLine.contains("]")) {
            String var = readLine.substring(0, readLine.indexOf("="));
        } else if (readLine.contains("newfile(")) {
            Files.newFile(readLine, buffer);
            return;
        }
        int i = readLine.indexOf("=");
        String var1 = readLine.substring(0, i).trim();
        String var2 = readLine.substring(i + 1).trim();
        checkIsKeyWordOrTrueFalse(var1);
        checkIsKeyWord(var2);
        if (isOpBool(readLine)) {
            int in = readLine.indexOf("=");
            boolean res = opBool(readLine.substring(in + 1).trim(), buffer);
            var1 = readLine.substring(0, in).trim();
            buffer.put(var1, res);
            return;
        } else if (removeSpaces(readLine).contains("=-")) {
            String nospace = removeSpaces(readLine);
            var1 = nospace.substring(0, nospace.indexOf("=-"));
            var2 = nospace.substring(nospace.indexOf("=-") + 2);
            checkIsKeyWordOrTrueFalse(var1);
            checkIsKeyWord(var2);
            try {
                long varv = (Long) getVar(var2, buffer);
                buffer.put(var1, -varv);
            } catch (ClassCastException cx) {
                throw new ScriptErrorException("<Error: expected number>");
            }
            return;
        } else if (isOpBin(readLine)) {
            int a = 0;
            do {
                i = var2.indexOf(sym[a]);
                a++;
            } while (i == -1);
            String op = sym[a - 1];
            String tmp = var2;
            var2 = var2.substring(0, i).trim();
            String var3 = tmp.substring(i + 1).trim();
            var1 = var1.trim();
            //aditionner var2 et var3 puis mettre la valeur dans var1
            long z1, z2;
            z1 = getOperandOrVariableValue(var2, buffer);
            z2 = getOperandOrVariableValue(var3, buffer);
            long res = operate(z1, z2, op);
            buffer.put(var1, res);
            return;
        } else {
            var1 = var1.trim();
            var2 = var2.trim();
            if (isNumber(var2))
                buffer.put(var1, Long.parseLong(var2));
            else if (isString(var2))
                buffer.put(var1, var2.substring(1, var2.length() - 1));
            else if (var2.equals("true") || var2.equals("false"))
                buffer.put(var1, Boolean.parseBoolean(var2));
            else if (buffer.get(var2) != null)
                buffer.put(var1, buffer.get(var2));
            else
                throw new ScriptErrorException("<Error: variable " + var2 + " not initialized>");
        }

    }

    private long getOperandOrVariableValue(String var, HashMap buffer) throws ScriptErrorException {
        if (isNumber(var))
            return Long.parseLong(var);
        else if (!buffer.containsKey(var))
            throw new ScriptErrorException("<Error: variable " + var + " doesen't exits>");
        else
            try {
                return ((Long) buffer.get(var));
            } catch (ClassCastException e) {
                throw new ScriptErrorException("<Error: Incompatible types>");
            }
    }

    private void thread(Reader reader, final HashMap buffer) throws ScriptErrorException {
        final Reader r = new Reader(getThreadedInst(reader));
        Runnable run = new Runnable() {
            public void run() {
                try {
                    seqInstThread(r, buffer);
                    if (threadList.size() == 0)
                        console.markstop();
                } catch (ScriptErrorException ex) {
                    console.appendToConsole(ex.getMessage());
                    stopInterpreter();
                }
            }
        };
        Thread t = new Thread(run);
        threadList.add(t);
        t.start();
    }

    public void stopInterpreter() {
        for (int i = 0; i < threadList.size(); i++)
            threadList.get(i).interrupt();
        this.interrupt();
    }

    private void write(String readLine, HashMap buffer) throws ScriptErrorException {
        int i = readLine.indexOf("(");
        int j = readLine.lastIndexOf(")");
        String temp = readLine.substring(i + 1, j).trim();
        if (isOpBool(readLine)) {
            boolean res = opBool(temp, buffer);
            console.appendToConsole(Boolean.toString(res));
        } else if (isFunctionCall(temp)) {
            executeFunction(temp, buffer);
            if (returnVal != null)
                printString(returnVal.toString());
            else
                throw new ScriptErrorException("<Error: no return statement>");
        } else {
            Object c = EvaluateVar(temp, buffer);
            if (c == null)
                console.appendToConsole("null");
            else if (c.toString().contains("\\n"))
                printString(c.toString());
            else
                console.appendToConsole(c.toString());
        }
    }

    private void read(String readLine, HashMap buffer) throws ScriptErrorException {
        int i = readLine.indexOf("(");
        int j = readLine.indexOf(")");
        String var = readLine.substring(i + 1, j);
        var = var.trim();
        if (var.contains(" ") || isFirstDigit(var))
            throw new ScriptErrorException("<Syntax Error: expected variable>");
        checkIsKeyWordOrTrueFalse(var);
        String r = JOptionPane.showInputDialog("enter value:");
        if (isNumber(r))//[TODO] check if its a vector
            buffer.put(var, Long.parseLong(r));
        else
            buffer.put(var, r);
    }

    private boolean isFirstDigit(String s) {
        return Character.isDigit(s.charAt(0));
    }

    private boolean isAffectation(String readLine) {
        return !Character.isDigit(readLine.charAt(0)) && ((readLine.contains("=") && readLine.length() > 2));
    }

    private boolean isRead(String readLine) {
        return readLine.startsWith(key.read + "(");
    }

    private boolean isWrite(String readLine) {
        return readLine.startsWith(key.write + "(");
    }

    private boolean isDeclaration(String readLine) {
        return !Character.isDigit(readLine.charAt(0)) && !readLine.contains("=");
    }

    private boolean isCondition(String readLine) {
        return readLine.startsWith(key.ifCond + " ");
    }

    private boolean isLoop(String readLine) {
        return readLine.startsWith(key.whileLoop + " ");
    }

    private boolean isNumber(String var2) {
        try {
            Long.parseLong(var2.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isOpBool(String readLine) {
        for (int i = 0; i < opB.length; i++)
            if (readLine.contains(opB[i]))
                return true;
        return false;
    }

    private boolean isString(String var2) {
        return var2.contains("\"");
    }

    private boolean isThread(String readLine) {
        return readLine.equals(key.thread);
    }

    private boolean isVectAffectation(String readLine) {
        return readLine.contains("[") && readLine.contains("]") && readLine.contains("=") && (readLine.indexOf("=") > readLine.indexOf("["));
    }

    private void checkIsKeyWord(String s) throws ScriptErrorException {
        for (int i = 0; i < keyWord.length; i++)
            if (s.startsWith(keyWord[i]))
                throw new ScriptErrorException("<Error: unexpected " + keyWord[i] + ">");
    }

    public static void checkIsKeyWordOrTrueFalse(String s) throws ScriptErrorException {
        for (int i = 0; i < keyWord.length; i++)
            if (s.startsWith(keyWord[i]))
                throw new ScriptErrorException("<Error: unexpected " + keyWord[i] + ">");
        if (s.equals("true") || s.equals("false"))
            throw new ScriptErrorException("<Error: unexpected true or false >");

    }

    private void checkBegin(String readLine) throws ScriptErrorException {
        if (!readLine.equals(key.begin))
            throw new ScriptErrorException("<Syntax error: No begin statement>");
    }

    private void checkEnd(String readLine) throws ScriptErrorException {
        if (!readLine.equals(key.end))
            throw new ScriptErrorException("<Syntax error: No end statement>");
    }

    private void checkEndOrElse(String readLine, Reader reader, HashMap buffer) throws ScriptErrorException {
        if (readLine.contains(key.elseCond))
            skipToElseOrEndIf(reader, buffer);
        else if (!readLine.equals(key.end + key.ifCond))
            throw new ScriptErrorException("<Error expected endif>");

    }

    private void checkEndThread(String readLine) throws ScriptErrorException {
        if (!readLine.equals(key.end + key.thread))
            throw new ScriptErrorException("<Syntax error: No endthread statement>");
    }

    private void checkEndWhile(String readLine) throws ScriptErrorException {
        if (!readLine.equals(key.end + key.whileLoop))
            throw new ScriptErrorException("<Syntax error: expected endwhile>");
    }

    private String getLoopInst(Reader reader) {
        StringBuilder s = new StringBuilder();
        int w = 0;
        while (!reader.nextLine().equals(key.end + key.whileLoop) || w > 0) {
            if (reader.nextLine().contains(key.whileLoop + " "))
                w++;
            if (reader.nextLine().equals(key.end + key.whileLoop))
                w--;
            s.append(reader.readLine() + "\n");
        }
        s.append(reader.readLine() + "\n");
        return s.toString();
    }

    private String getOpBool(String exp) throws ScriptErrorException {
        String op = null;
        for (int i = 0; i < opB.length; i++)
            if (exp.contains(opB[i])) {
                op = opB[i];
                break;
            }
        if (op == null)
            throw new ScriptErrorException("<Syntax Error: expected boolean>");
        return op;
    }
    /*Grammar:
     * <Program>:= begin <seqInst> end
     * <seqInst>:= <inst> {<inst>}
     * <Inst>:= <affectation>|<read>|<write>|<cond>|<loop>|<thread>
     * <cond>:= if <opbool> <seqInst> endif
     * <thread>:= thread <seqInst> endthread
     * <loop>:= while <opBool> <seqInst> endwhile
     * <affectation>:= <var> = <expression>
     * <read>:= read(<variable>)
     * <write>:= write(<variable>|<expression>)
     *
     */
}
