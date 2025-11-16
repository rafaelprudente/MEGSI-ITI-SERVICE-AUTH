package pt.megsi.fwk.filters;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);

        try (InputStream is = request.getInputStream()) {
            this.cachedBody = is.readAllBytes();
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // not used
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws UnsupportedEncodingException {
        return new BufferedReader(
                new InputStreamReader(getInputStream(), getCharacterEncodingOrUtf8())
        );
    }

    private String getCharacterEncodingOrUtf8() {
        return getCharacterEncoding() != null ? getCharacterEncoding() : "UTF-8";
    }

    public String getBodyString() throws UnsupportedEncodingException {
        return new String(cachedBody, getCharacterEncodingOrUtf8());
    }

    public byte[] getBody() {
        return cachedBody;
    }
}