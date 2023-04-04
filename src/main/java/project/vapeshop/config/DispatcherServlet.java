package project.vapeshop.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return  new Class[]{SpringSecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringApplicationConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/vapeshop/v1/*"};
    }
}
