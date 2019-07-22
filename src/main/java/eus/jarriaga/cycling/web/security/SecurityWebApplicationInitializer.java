package eus.jarriaga.cycling.web.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    private final Log logger = LogFactory.getLog(getClass());

    public SecurityWebApplicationInitializer() {
        logger.info("Construct SecurityWebApplicationInitializer");
    }
}
