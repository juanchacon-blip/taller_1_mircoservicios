package com.unibague.webapis.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Configuracion del Servicio Web SOAP.
 *
 * Criterio 1: La aplicacion cuenta con Servicios Web (SOAP), expuestos
 * en /ws con su contrato WSDL disponible en /ws/productos.wsdl
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            org.springframework.web.context.ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "productos")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema productoSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductoPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://unibague.edu.co/webapis/producto");
        wsdl11Definition.setSchema(productoSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema productoSchema() {
        return new SimpleXsdSchema(new ClassPathResource("producto.xsd"));
    }
}
