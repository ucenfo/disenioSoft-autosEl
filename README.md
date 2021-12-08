# Proyecto curso Diseño conceptual de software
## Universidad Cenfotec

###Desarrollo de aplicación utilizando **BMPN**.

El objetivo es permitir realizar el proceso de venta y subasta de vehículos, las características son las siguientes:

| Tipo de vehículo |            Características            | Valor [^1] | Tipo de trámite |
|------------------|:-------------------------------------:|:----------:|----------------:|
| Mono Plaza       |   Ultra pequeño, para dos personas    |  $10 mill  |   Venta directa |
| Sedan            |                clásico                |  $25 mil   |   Venta directa |
| Suburban Mini    |       máximo 5 personas, 4 x 2        |  $25 mil   |         Subasta |
| Suburban Plus    |       máximo 7 personas, 4 x 2        |  $45 mil   |         Subasta |
| Heavy Suburban   | máximo 7 personas, full extras, 4 x 4 |  $58 mill  |         Subasta |

[^1]: Valor mínimo del trámite.

## Reglas de negocio

* En cualquier trámite, sea de venta o subasta, el solicitante se guarda en la base de datos; si la identificación ya exista, realiza un update.
* Para cualquier tipo de vehículo, si monto ingresado es menor al valor publicado, el trámite no continúa y se envía un mensaje al solicitanto indicando esta situación.
* En el caso de las subasta, el valor anunciado es el piso; el usuario puede hacer su oferta por un monto mayor.
* Venta directa:
    * Si el monto ofrecido es igual o mayor al precio del catálogo se realiza la venta, retornando la diferencia, si existe.
    * Inventario:
        * Si hay inventario en existencia del tipo de vehículo elegido, se hace el rebajo y se indica al cliente que pase a retirar. Y la solicitud queda procesada (true)
        * Si no hay inventario, no se realiza cambio en este, se envía un mensaje al cliente indicando que se le estará informando la fecha para su retiro. Y la solicitud queda pendiente (false).
* Subasta:
    * La subasta se realiza únicamente cuando hay dos ofertas pendientes (false) del mismo tipo de vehículo, al ingresar la primera solicitud, se crea con estado pendiente (false).
    * Cuando ingresa la segunda solicitud, crea la nueva solicitud en pendiente (false), y analiza las dos solicitud existentes y adjudica al mejor oferente. La solicitud del ganador es modificada como procesada (true).
    * Inventario:
        * Si hay inventario, lo rebaja.
        * Si no hay no se hace modificación.
    * Al ganador del proceso se le envia un mensaje indicando que se le ha adjudicado el proceso, si hay inventario el mensaje es para que pase a retirarlo, y si no hay se le indica que se le estará informando la fecha de retiro.
    * Si hay dos ofertas por igual monto, se el adjudica al primer soliciante.
  