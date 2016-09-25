package psn.ifplusor.core.common;

import java.util.Map;

public class CodeAndMessage {

    public static String JSONCodeAndMessage(int code, String message) {
        return "{\"code\":" + code + ", \"message\":\"" + message + "\"}";
    }

    public static String JSONCodeAndMessageAndResult(int code, String message, Object result) {
        return "{\"code\":" + code + ", \"message\":\"" + message + "\", \"result\":"
                + JSONUtil.generateJSONFromObject(result) + "}";
    }

    public static void setCodeAndMessage(Map<String, Object> model, int code, String message) {
        model.put("code", String.valueOf(code));
        model.put("message", message);
    }
}
