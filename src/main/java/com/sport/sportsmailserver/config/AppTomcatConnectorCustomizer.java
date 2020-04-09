package com.sport.sportsmailserver.config;

import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * @author itning
 * @date 2020/4/9 18:03
 */
@Component
public class AppTomcatConnectorCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    private static final Logger log = LoggerFactory.getLogger(AppTomcatConnectorCustomizer.class);

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        if (factory instanceof TomcatServletWebServerFactory) {
            TomcatServletWebServerFactory f = (TomcatServletWebServerFactory) factory;
            f.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
            if (log.isDebugEnabled()) {
                f.addConnectorCustomizers(connector -> {
                    ProtocolHandler protocol = connector.getProtocolHandler();

                    log.debug("Tomcat({})  -- MaxConnection:{};MaxThreads:{};MinSpareThreads:{}",
                            protocol.getClass().getName(),
                            ((AbstractHttp11Protocol<?>) protocol).getMaxConnections(),
                            ((AbstractHttp11Protocol<?>) protocol).getMaxThreads(),
                            ((AbstractHttp11Protocol<?>) protocol).getMinSpareThreads());
                });
            }
        } else {
            log.warn("ConfigurableServletWebServerFactory is not instanceof TomcatServletWebServerFactory and is: {}", factory.getClass().getName());
        }
    }
}
