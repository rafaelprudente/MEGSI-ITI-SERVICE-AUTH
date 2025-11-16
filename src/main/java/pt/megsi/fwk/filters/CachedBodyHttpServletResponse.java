package pt.megsi.fwk.filters;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream cachedBytes = new ByteArrayOutputStream();
    private ServletOutputStream outputStream;
    private PrintWriter writer;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {

        if (this.outputStream == null) {
            ServletOutputStream original = super.getOutputStream();

            this.outputStream = new ServletOutputStream() {

                @Override
                public boolean isReady() {
                    return original.isReady();
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                    original.setWriteListener(writeListener);
                }

                @Override
                public void write(int b) throws IOException {
                    cachedBytes.write(b);   // capture
                    original.write(b);      // pass-through
                }
            };
        }

        return this.outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.writer == null) {
            this.writer = new PrintWriter(
                    new OutputStreamWriter(getOutputStream(), getCharacterEncodingOrUtf8()), true
            );
        }
        return this.writer;
    }

    private String getCharacterEncodingOrUtf8() {
        return getCharacterEncoding() != null ? getCharacterEncoding() : "UTF-8";
    }

    public byte[] getBody() {
        return cachedBytes.toByteArray();
    }

    public String getBodyString() throws UnsupportedEncodingException {
        return new String(getBody(), getCharacterEncodingOrUtf8());
    }
}
