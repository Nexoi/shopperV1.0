//package com.seeu.configurer.http2https;
//
//import org.eclipse.jetty.server.HttpConfiguration;
//import org.eclipse.jetty.server.HttpConnectionFactory;
//import org.eclipse.jetty.server.ServerConnector;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
//import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by neo on 27/09/2017.
// */
//@Configuration
//public class HttpToHttpsJettyCustomizer implements EmbeddedServletContainerCustomizer {
//    @Override
//    public void customize(ConfigurableEmbeddedServletContainer container) {
//        JettyEmbeddedServletContainerFactory containerFactory = (JettyEmbeddedServletContainerFactory) container;
//        //Add a plain HTTP connector and a WebAppContext config to force redirect from http->https
//        containerFactory.addConfigurations(new HttpToHttpsJettyConfiguration());
//
//        containerFactory.addServerCustomizers(server -> {
//            HttpConfiguration http = new HttpConfiguration();
//            http.setSecurePort(443);
//            http.setSecureScheme("https");
//
//            ServerConnector connector = new ServerConnector(server);
//            connector.addConnectionFactory(new HttpConnectionFactory(http));
//            connector.setPort(80);
//
//            server.addConnector(connector);
//        });
//    }
//}