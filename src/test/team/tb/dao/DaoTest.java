package team.tb.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

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
        targetObject.forEach((k, v) -> System.out.println(k+" : "+v));
        System.out.println("表单信息-----------------");
        formData.forEach(data -> {
            Map map1 = (Map) data;
            map1.forEach((k, v) -> System.out.println(k+" : "+v));
        });
    }
}
