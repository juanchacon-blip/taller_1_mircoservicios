package com.unibague.webapis.endpoint;

import com.unibague.webapis.service.ProductoService;
import com.unibague.webapis.soap.gen.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Endpoint del Servicio Web SOAP de Productos.
 * Las clases ObtenerProductoRequest/Response, ListarProductosRequest/Response
 * y Producto (paquete com.unibague.webapis.soap.gen) son generadas
 * automaticamente por el plugin jaxb2-maven-plugin a partir de producto.xsd
 * al ejecutar "mvn compile" (ver Documento de Apoyo: Web Services SOAP).
 */
@Endpoint
public class ProductoEndpoint {

    private static final String NAMESPACE_URI = "http://unibague.edu.co/webapis/producto";

    private final ProductoService service;

    public ProductoEndpoint(ProductoService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerProductoRequest")
    @ResponsePayload
    public ObtenerProductoResponse obtenerProducto(@RequestPayload ObtenerProductoRequest request) {
        com.unibague.webapis.model.Producto entidad = service.obtenerPorId(request.getId());

        ObtenerProductoResponse response = new ObtenerProductoResponse();
        response.setProducto(mapearAProductoSoap(entidad));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarProductosRequest")
    @ResponsePayload
    public ListarProductosResponse listarProductos(@RequestPayload ListarProductosRequest request) {
        ListarProductosResponse response = new ListarProductosResponse();
        service.listarTodos().forEach(p -> response.getProducto().add(mapearAProductoSoap(p)));
        return response;
    }

    private Producto mapearAProductoSoap(com.unibague.webapis.model.Producto entidad) {
        Producto p = new Producto();
        p.setId(entidad.getId());
        p.setNombre(entidad.getNombre());
        p.setPrecio(java.math.BigDecimal.valueOf(entidad.getPrecio()));
        p.setCantidad(entidad.getCantidad());
        return p;
    }
}
