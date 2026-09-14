package com.unibague.webapis.service;

import com.unibague.webapis.model.Producto;
import com.unibague.webapis.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Servicio central de negocio para Producto.
 * Es consumido tanto por el Servicio API REST como por el Servicio Web SOAP,
 * de forma que ambos comparten la misma logica y la misma fuente de datos.
 */
@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    // Carga datos iniciales de ejemplo al arrancar la aplicacion
    @PostConstruct
    public void cargarDatosIniciales() {
        if (repository.count() == 0) {
            repository.save(new Producto("Teclado mecanico", 150000.0, 20));
            repository.save(new Producto("Mouse inalambrico", 65000.0, 35));
            repository.save(new Producto("Monitor 24 pulgadas", 620000.0, 10));
        }
    }

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        Optional<Producto> producto = repository.findById(id);
        return producto.orElseThrow(() ->
                new NoSuchElementException("No existe un producto con id " + id));
    }

    public Producto crear(Producto producto) {
        producto.setId(null);
        return repository.save(producto);
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setPrecio(datos.getPrecio());
        existente.setCantidad(datos.getCantidad());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("No existe un producto con id " + id);
        }
        repository.deleteById(id);
    }
}
