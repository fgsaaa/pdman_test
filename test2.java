import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.annotation.JSONField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Example {
    public static void main(String[] args) {
        String json = "{\"name\":\"John\", \"age\":null}";

        try {
            Person person = JSON.parseObject(json, Person.class);
            System.out.println(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Person {
        private String name;

        @NotNullField
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface NotNullField {
    }

    public static class NotNullFieldDeserializer implements ObjectDeserializer {
        @Override
        public <T> T deserialze(DefaultJSONParser parser, java.lang.reflect.Type type, Object fieldName) {
            Object value = parser.parseObject(type);
            if (value == null && parser.getContext().getObject() != null) {
                // Check if the field has @NotNullField annotation
                try {
                    JSONField jsonField = parser.getContext().getObject().getClass().getDeclaredField((String) fieldName).getAnnotation(JSONField.class);
                    if (jsonField != null && jsonField.deserialize() && jsonField.deserializeUsing() == NotNullFieldDeserializer.class) {
                        throw new RuntimeException("Field " + fieldName + " cannot be null");
                    }
                } catch (NoSuchFieldException ignored) {
                }
            }
            return (T) value;
        }

        @Override
        public int getFastMatchToken() {
            return 0;
        }
    }
}
