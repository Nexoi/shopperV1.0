//package com.seeu.configurer.http2https;
//
///**
// * Created by neo on 27/09/2017.
// */
//import org.eclipse.jetty.security.ConstraintMapping;
//import org.eclipse.jetty.security.ConstraintSecurityHandler;
//import org.eclipse.jetty.util.security.Constraint;
//import org.eclipse.jetty.webapp.AbstractConfiguration;
//import org.eclipse.jetty.webapp.WebAppContext;
//
//class HttpToHttpsJettyConfiguration extends AbstractConfiguration {
//    // http://wiki.eclipse.org/Jetty/Howto/Configure_SSL#Redirecting_http_requests_to_https
//    @Override
//    public void configure(WebAppContext context) throws Exception {
//        Constraint constraint = new Constraint();
//        constraint.setDataConstraint(2);
//
//        ConstraintMapping constraintMapping = new ConstraintMapping();
//        constraintMapping.setPathSpec("/*");
//        constraintMapping.setConstraint(constraint);
//
//        ConstraintSecurityHandler constraintSecurityHandler = new ConstraintSecurityHandler();
//        constraintSecurityHandler.addConstraintMapping(constraintMapping);
//
//        context.setSecurityHandler(constraintSecurityHandler);
//    }
//}