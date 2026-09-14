package com.unibague.webapis;

import com.unibague.webapis.model.Producto;
import com.unibague.webapis.repository.ProductoRepository;
import com.unibague.webapis.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductoServiceTest {

    @Autowired
    private ProductoService service;

    @Autowired
    private ProductoRepository repository;

    @BeforeEach
    void limpiar() {
        repository.deleteAll();
    }

    @Test
    void creaYObtieneProducto() {
        Producto creado = service.crear(new Producto("Audifonos", 89000.0, 15));
        assertNotNull(creado.getId());

        Producto obtenido = service.obtenerPorId(creado.getId());
        assertEquals("Audifonos", obtenido.getNombre());
    }

    @Test
    void listaProductos() {
        service.crear(new Producto("Cable USB-C", 25000.0, 50));
        assertFalse(service.listarTodos().isEmpty());
    }
}
