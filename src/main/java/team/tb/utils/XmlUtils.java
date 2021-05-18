package team.tb.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.List;

/**
 * 用于解析和读取表单xml文件
 * 只能获取当前节点下的子节点
 */
public class XmlUtils {

    /**
     * 读取xml文件
     * @param path
     * @return
     * @throws DocumentException
     */
    public static Document readDocument(String path) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        return saxReader.read(new File(path));
    }

    /**
     * 将文档写进文件
     * @param document
     * @param filePath
     * @param isPrettyPrint
     */
    public static void writeToFile(Document document, String filePath, Boolean isPrettyPrint) throws IOException {
        FileOutputStream out = null;
        OutputFormat format = null;
        XMLWriter writer = null;
        try {
            out = new FileOutputStream(filePath);
            if(isPrettyPrint){
                format = OutputFormat.createPrettyPrint(); // 带空格的格式打印
            }else{
                format = OutputFormat.createCompactFormat(); // 不带空格的紧凑打印
            }
            format.setEncoding("utf-8");
            writer = new XMLWriter(out, format);
            writer.write(document);
        } finally {
            try {
                if(out != null){
                    out.close();
                }
                if(writer != null){
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
