package org.activiti;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GenericResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream output;
    private int contentLength;
    private String contentType;

    public GenericResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new ByteArrayOutputStream();
    }

    public byte[] getData() {
        return output.toByteArray();
    }
    @Override
    public ServletOutputStream getOutputStream() {
        return new FilterServletOutputStream(output);
    }
    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(getOutputStream(), true);
    }

    public int getContentLength() {
        return contentLength;
    }
    @Override
    public void setContentLength(int length) {
        this.contentLength = length;
        super.setContentLength(length);
    }
    @Override
    public String getContentType() {
        return contentType;
    }
    @Override
    public void setContentType(String type) {
        this.contentType = type;
        super.setContentType(type);
    }
}
