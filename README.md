# Proyecto 1 — Web, APIs y Microservicios
### Taller 1. Avance 1. Universidad de Ibagué

Aplicación Web construida con **Spring Boot** que gestiona un catálogo de **Productos**,
cumpliendo los 4 criterios del instructivo:

| # | Criterio | Dónde está implementado |
|---|----------|--------------------------|
| 1 | Servicios Web | `config/WebServiceConfig.java` + `endpoint/ProductoEndpoint.java` + `producto.xsd` — Servicio **SOAP** en `/ws` |
| 2 | Servicios API | `controller/ProductoRestController.java` — expone la funcionalidad como API |
| 3 | Servicios API REST | Mismo controlador: recursos con URIs, verbos HTTP (GET/POST/PUT/DELETE), JSON |
| 4 | Spring Boot | Todo el proyecto (`WebApisApplication.java`, `pom.xml`) |

## Estructura del proyecto

```
src/main/java/com/unibague/webapis/
 ├── WebApisApplication.java      # Clase principal Spring Boot
 ├── model/Producto.java          # Entidad JPA
 ├── repository/ProductoRepository.java
 ├── service/ProductoService.java # Lógica de negocio compartida (REST y SOAP)
 ├── controller/ProductoRestController.java  # Servicio API REST
 ├── config/WebServiceConfig.java # Configuración del Servicio Web SOAP
 └── endpoint/ProductoEndpoint.java # Endpoint SOAP
src/main/resources/
 ├── application.properties
 └── producto.xsd                 # Contrato del servicio SOAP (genera clases Java)
```

## Requisitos

- Java 17+
- Maven 3.8+
- (Opcional) Postman, curl o SoapUI para probar los servicios

## Cómo ejecutar

```bash
mvn clean spring-boot:run
```

La app queda corriendo en `http://localhost:8080`.

> Al compilar (`mvn compile`), el plugin `jaxb2-maven-plugin` genera automáticamente
> las clases Java (`ObtenerProductoRequest`, `Producto`, etc.) a partir de
> `producto.xsd`, en el paquete `com.unibague.webapis.soap.gen`. No necesitas
> crear esas clases manualmente.

## Probar el Servicio API REST (criterios 2 y 3)

```bash
# Listar productos
curl http://localhost:8080/api/productos

# Obtener uno
curl http://localhost:8080/api/productos/1

# Crear
curl -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d "{\"nombre\":\"labubu rosa\",\"precio\":9999,\"cantidad\":1}"

# Actualizar
curl -X PUT http://localhost:8080/api/productos/1 -H "Content-Type: application/json" -d "{\"nombre\":\"Teclado mecanico RGB\",\"precio\":175000,\"cantidad\":18}"

# Eliminar
curl -X DELETE http://localhost:8080/api/productos/1
```

## Probar el Servicio Web SOAP (criterio 1)

1. Ver el contrato WSDL generado automáticamente:
   `http://localhost:8080/ws/productos.wsdl`

2. Enviar una petición SOAP (por ejemplo con `curl` o Postman → Body → raw → XML)
   a `http://localhost:8080/ws` con `Content-Type: text/xml`:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                   xmlns:prod="http://unibague.edu.co/webapis/producto">
   <soapenv:Header/>
   <soapenv:Body>
      <prod:obtenerProductoRequest>
         <prod:id>1</prod:id>
      </prod:obtenerProductoRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

Ejemplo con curl:

```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  --data '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:prod="http://unibague.edu.co/webapis/producto"><soapenv:Body><prod:obtenerProductoRequest><prod:id>1</prod:id></prod:obtenerProductoRequest></soapenv:Body></soapenv:Envelope>'
```

También puedes listar todos los productos vía SOAP enviando `listarProductosRequest`.

## Consola de base de datos (opcional)

`http://localhost:8080/h2-console` → JDBC URL: `jdbc:h2:mem:productosdb`, usuario `sa`, sin contraseña.


