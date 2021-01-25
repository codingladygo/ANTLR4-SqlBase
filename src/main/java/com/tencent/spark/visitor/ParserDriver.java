package com.tencent.spark.visitor;

import com.tencent.spark.out.SqlBaseLexer;
import com.tencent.spark.out.SqlBaseParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;


public class ParserDriver {
    public static void main(String[] args) throws Exception {
//        String query = "SELECT LastName,FirstName FROM Persons;";

//        String query = "SELECT Persons.LastName, Persons.FirstName, Orders.OrderNo\n" +
//                "FROM Persons\n" +
//                "INNER JOIN Orders\n" +
//                "ON Persons.Id_P = Orders.Id_P\n" +
//                "ORDER BY Persons.LastName";


        String filePath = "/Users/duanyixuan3/Documents/adm_7fresh_d09_ship_det_1.py";
        PyObject fileInput = transP2JData(filePath, "sql");
        System.out.println("fileInput info :" + fileInput);


//        String queryUpper = query.toUpperCase();
//
//        System.out.println(queryUpper);
//
//        SqlBaseLexer lexer = new SqlBaseLexer(new ANTLRInputStream(queryUpper));
//        SqlBaseParser parser = new SqlBaseParser(new CommonTokenStream(lexer));
//        MyVisitor visitor = new MyVisitor();
//        SqlBaseParser.SingleStatementContext bb = parser.singleStatement();
//        String res = visitor.visitSingleStatement(bb);
//        System.out.println("res=" + res);

    }

    /**
     * 读取python文件
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String fileRead(String filePath) throws Exception {
        File file = new File(filePath);
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String s = "";
        while ((s = bufferedReader.readLine()) != null) {
            stringBuilder.append("\n");
            System.out.println(s);
        }
        bufferedReader.close();
        String string = stringBuilder.toString();
        System.out.println(string);
        return string;
    }

    /**
     * 获取python文件中指定变量的值
     *
     * @param filePath
     * @param sqlParams
     * @return
     * @throws IOException
     */
    public static PyObject transP2JData(String filePath, String sqlParams) throws IOException {
        Properties props = new Properties();
        props.put("python.home", "path to the Lib folder");
        props.put("python.console.encoding", "UTF-8");

        props.put("python.security.respectJavaAccessibility", "false");

        props.put("python.import.site", "false");

        Properties preprops = System.getProperties();

        PythonInterpreter.initialize(preprops, props, new String[0]);

        PythonInterpreter pythonInterpreter = new PythonInterpreter();

        InputStream inputStream = new FileInputStream(filePath);
        pythonInterpreter.execfile(inputStream);
        inputStream.close();
        PyObject po = pythonInterpreter.get(sqlParams);
        return po;
    }
}
