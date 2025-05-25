package com.beehopuse.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class LocaleFilter implements Filter {
    private static final String DEFAULT_LOCALE = "fr";
    private static final String LOCALE_PARAM = "lang";
    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        // Get locale from request parameter
        String localeParam = request.getParameter(LOCALE_PARAM);
        if (localeParam != null) {
            session.setAttribute(LOCALE_ATTRIBUTE, localeParam);
        }

        // Get locale from session or use default
        String locale = (String) session.getAttribute(LOCALE_ATTRIBUTE);
        if (locale == null) {
            locale = DEFAULT_LOCALE;
            session.setAttribute(LOCALE_ATTRIBUTE, locale);
        }

        // Set locale in request
        request.setAttribute("currentLocale", locale);
        request.setAttribute("messages", ((Map<String, Map<String, String>>) 
            request.getServletContext().getAttribute("messages")).get(locale));

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Clean up if needed
    }
} 