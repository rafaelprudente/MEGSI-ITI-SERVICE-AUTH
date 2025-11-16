package pt.megsi.fwk.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LogFilter { //extends OncePerRequestFilter {
    //@Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
        CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            log.info("Request Uri: [{}] Request Method: [{}] Response Body: [{}] Response Status: [{}]", request.getRequestURI(), request.getMethod(), wrappedResponse.getBodyString(), wrappedResponse.getStatus());

            response.getOutputStream().write(wrappedResponse.getBody());
            response.getOutputStream().flush();
        }
    }
}
