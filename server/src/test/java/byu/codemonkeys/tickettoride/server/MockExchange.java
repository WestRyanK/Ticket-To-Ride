package byu.codemonkeys.tickettoride.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;

/**
 * MockExchange provides simple functionality to build an HttpExchange object for testing.
 * Control and creation of the HttpExchange is given completely to the user to allow complete
 * flexibility in its usage
 */
public class MockExchange extends HttpExchange {
    private String requestMethod, protocol;
    private Headers requestHeaders, responseHeaders;
    private URI requestURI;
    private HttpContext httpContext;
    private InputStream requestBody;
    private OutputStream responseBody;
    private HttpPrincipal principal;
    private Map<String, Object> attributes;
    private InetSocketAddress localAddress, remoteAddress;
    private int responseCode;

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setRequestHeaders(Headers headers) {
        this.requestHeaders = headers;
    }

    public void setResponseHeaders(Headers headers) {
        this.responseHeaders = headers;
    }

    public void setRequestURI(URI requestURI) {
        this.requestURI = requestURI;
    }

    public void setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
    }

    public void setRequestBody(InputStream requestBody) {
        this.requestBody = requestBody;
    }

    public void setResponseBody(OutputStream responseBody) {
        this.responseBody = responseBody;
    }

    public void setPrincipal(HttpPrincipal principal) {
        this.principal = principal;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
    }

    public void setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }


    @Override
    public Headers getRequestHeaders() {
        return requestHeaders;
    }

    @Override
    public Headers getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public URI getRequestURI() {
        return requestURI;
    }

    @Override
    public String getRequestMethod() {
        return requestMethod;
    }

    @Override
    public HttpContext getHttpContext() {
        return httpContext;
    }

    @Override
    public void close() {
        if (requestBody != null) {
            try {
                requestBody.close();
            } catch (IOException ignore){}
        }
        if (responseBody != null) {
            try {
                responseBody.close();
            } catch (IOException ignore) {}
        }
    }

    @Override
    public InputStream getRequestBody() {
        return requestBody;
    }

    @Override
    public OutputStream getResponseBody() {
        return responseBody;
    }

    @Override
    public void sendResponseHeaders(int i, long l) throws IOException {
        responseCode = i;
    }

    @Override
    public HttpPrincipal getPrincipal() {
        return principal;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public Object getAttribute(String s) {
        return attributes.get(s);
    }

    @Override
    public void setAttribute(String s, Object o) {
        attributes.put(s, o);
    }

    @Override
    public void setStreams(InputStream inputStream, OutputStream outputStream) {
        requestBody = inputStream;
        responseBody = outputStream;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }
}
