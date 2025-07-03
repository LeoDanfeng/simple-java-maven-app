package com.mycompany.app.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestParamWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params;

    public RequestParamWrapper(HttpServletRequest request) {
        super(request);
        initParams(request);
    }

    private void initParams(HttpServletRequest request) {
        params = new HashMap<>(request.getParameterMap());
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            String[] trimVals = new String[entry.getValue().length];
            for (int i = 0; i < trimVals.length; i++) {
                trimVals[i] = values[i].trim();
            }
            params.put(key, trimVals);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(getHeader(HttpHeaders.CONTENT_TYPE))) {
            ServletInputStream inputStream = super.getInputStream();
            ObjectMapper om = new ObjectMapper();
            ObjectNode rootNode = (ObjectNode) om.readTree(inputStream);
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                JsonNode value = field.getValue();
                if (value.isTextual()) {
                    String text = value.asText();
                    text = text.trim();
                    rootNode.set(key, new TextNode(text));
                }
            }
            byte[] bytes = om.writeValueAsBytes(rootNode);
            final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            return new ServletInputStream() {

                private volatile boolean finished = false;

                @Override
                public int read() throws IOException {
                    int read = bis.read();
                    if (read == -1) {
                        finished = true;
                        bis.close();
                    }
                    return read;
                }

                @Override
                public boolean isFinished() {
                    return finished;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }
            };
        }
        return super.getInputStream();
    }

    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }
}