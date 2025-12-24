package com.oponiti.shopreward.common.util.methodoverride;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class MethodOverrideHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final String wrappingMethod;

    public MethodOverrideHttpServletRequestWrapper(HttpServletRequest request, String wrappingMethod) {
        super(request);
        wrappingMethod = wrappingMethod;
    }

    @Override
    public String getMethod() {
        return this.wrappingMethod;
    }
}
