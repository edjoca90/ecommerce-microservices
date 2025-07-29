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