package team.tb.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXContentHandler;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoTest {
    @Test
    public void test01() throws IOException {
        String s = "{\"condition\":{\"targetObject\":{\"grades\":[],\"departments\":[],\"majors\":[],\"classes\":[],\"students\":[]},\"timeRange\":[\"2021-04-28 00:00:00\",\"2021-04-29 00:00:00\"]},\"formData\":[{\"id\":0,\"kid\":6,\"type\":\"text\",\"label\":\"班级\",\"req\":false,\"canEdit\":false,\"isNew\":false,\"hasOption\":false}]}";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(s, Map.class);
        System.out.println(map);
//        System.out.println(map.getClass().toString()); // 默认是生成LinkedHashMap
        Map targetObject = (Map)((Map) map.get("condition")).get("targetObject");
        List formData = (List) map.get("formData");
        System.out.println("条件信息-----------------");
        targetObject.forEach((k, v) -> {
            if(v instanceof List){
                System.out.println(k + "->列表长度：" + ((List) v).size());
            }else{
                System.out.println(k + "->非列表数据：" + v + " typeof " + v.getClass().toString());
            }
        });
        System.out.println("表单信息-----------------");
        formData.forEach(data -> {
            Map map1 = (Map) data;
            for(Object key : map1.keySet()){
                Object obj = null;
                if((obj = map1.getOrDefault(key,null)) != null){
                    if(obj instanceof List){
                        System.out.println(key + "->列表长度：" + ((List) obj).size());
                    }else{
                        System.out.println(key + "->非列表数据：" + obj + " typeof " + obj.getClass().toString());
                    }
                }
            }
        });
    }

    @Test
    public void test02(){
        System.out.println(this.getClass().getClassLoader().getResource("/db.properties"));
        System.out.println(this.getClass().getResource("/db.properties"));
        System.out.println(ClassLoader.getSystemResource("/db.properties"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("/db.properties"));
    }


}
