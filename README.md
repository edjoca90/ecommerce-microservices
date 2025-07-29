# Microservicios: Productos e Inventario

Este proyecto contiene dos microservicios independientes que permiten gestionar productos y su inventario.

## Servicios

- `product-service`: Crea productos.
- `inventory-service`: Realiza compras y descuenta stock.

## Ejecución con Docker

```bash
docker-compose up --build

## Estructura de ramas en repo

main -> produccion
develop -> Versionas para pruebas
 feature/* -> rama por HU
    hotfix/* -> para arreglar bugs

## Documentacion Swagger

http://localhost:8081/swagger-ui/index.html

## Arquitectura General
Cliente REST API: Postman o cualquier cliente que consuma los endpoints.

Product Service: 
    - Expone /products y operaciones CRUD.

    - Informa al inventory-service sobre nuevos productos y consulta stock si se requiere.

Inventory Service:

    - Expone /inventory/purchase para realizar compras y descontar stock.

    - Verifica producto llamando a product-service.

Comunicación entre microservicios: REST con validación por API Key.

Bases de datos: Mysql

## Decisión de Ubicación del Endpoint de Compra

Endpoint de compra en el Microservicio Inventario.

Justificación:

Responsabilidad: El microservicio de Inventario es el encargado de la disponibilidad y actualización de stock. Gestionar la transacción de compra (validación y descuento) es una operación relacionada con la integridad del stock.

Acoplamiento:

Al mantener esta operación en el servicio de Inventario, se centraliza el control y se evita replicar lógicas de actualización en ambos servicios.

Patrones de Diseño:

Se puede implementar el patrón orquestador de transacciones que, en este caso, consulta al microservicio de Productos para obtener información detallada y confirmar la existencia del producto, para luego proceder a la actualización en inventario.

Consistencia:

Con una sola fuente de la verdad para el stock se minimizan los riesgos de inconsistencia. En caso de fallo en la comunicación, se pueden emplear reintentos o compensaciones.

## Flujo de Compra
1.Verificar el stock de Productos para vslidar que tenga la cantidad a vender
2. completa info de DTO
3. Realiza la transaccion
4. Consultar el microservicio Productos (usando API key) para obtener detalles (nombre, precio).
5. Respuesta: Retornar un JSON con el detalle de la compra.

##Herramientas IA
1. Copilot: para escribir codigo limpio y ahorrar tiempo en crear archivos
2. DeepSeek: Ayuda para escribir los yaml, gitignore y docker además de dudas con errores de dependencias