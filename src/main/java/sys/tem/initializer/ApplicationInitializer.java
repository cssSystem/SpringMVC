package sys.tem.initializer;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;

public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        final var context = new AnnotationConfigWebApplicationContext();
        context.scan("sys.tem");
        context.refresh();

        final var servlet = new DispatcherServlet(context);
        final var registration = servletContext.addServlet(
                "app", servlet
        );
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}