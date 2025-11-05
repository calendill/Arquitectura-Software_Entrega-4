Implementación de la Arquitectura y Documentación del Proyecto “Veterinaria Muro”


Definición de Endpoints para Cada Recurso

En el sistema Veterinaria Muro se implementaron endpoints RESTful para cada uno de los recursos principales: Clientes, Mascotas y Veterinarios.
Cada recurso cuenta con operaciones CRUD (crear, leer, actualizar y eliminar) a través de un controlador REST que sigue las convenciones del framework Spring Boot.

Ejemplo de los endpoints implementados:

GET /api/mascotas → Obtiene todas las mascotas registradas.

GET /api/mascotas/{id} → Recupera una mascota específica por su ID.

POST /api/mascotas → Crea una nueva mascota.

PUT /api/mascotas/{id} → Actualiza los datos de una mascota existente.

DELETE /api/mascotas/{id} → Elimina una mascota por su identificador.

Cada método del controlador fue documentado y manejado con las anotaciones de Spring (@GetMapping, @PostMapping, @PutMapping, @DeleteMapping) para facilitar su comprensión y mantenimiento.

Convenciones de Nombrado

Se establecieron convenciones de nombrado coherentes con las buenas prácticas de Java y Spring Boot:

Paquetes: organizados por capas (dominio, aplicacion, infraestructura, presentacion).

Clases: en PascalCase (por ejemplo, MascotaControladorRest, ClienteServicioImpl).

Métodos y variables: en camelCase (por ejemplo, obtenerPorId, crearMascota).

Endpoints REST: en minúsculas, usando sustantivos en plural (por ejemplo, /api/mascotas, /api/clientes).

Estas convenciones permitieron mantener la coherencia y claridad en el código fuente, favoreciendo la escalabilidad del proyecto.

Diseño de DTOs de Request/Response

Se utilizaron Data Transfer Objects (DTOs) para desacoplar la capa de presentación de la capa de dominio.
Cada recurso cuenta con dos tipos de DTOs:

Request DTO: contiene los datos que el cliente envía al servidor.
Ejemplo: MascotaRequestDto incluye atributos como nombre, fecha de nacimiento, sexo, descripción y clienteId.

Response DTO: representa la información que se devuelve al cliente después de una operación.
Ejemplo: MascotaResponseDto incluye además del ID y datos básicos, el tipo de animal y la raza o color del pelaje.

El uso de DTOs mejoró la seguridad y el control sobre los datos expuestos en las API, evitando dependencias directas con las entidades del dominio.

Implementación de la Documentación con Swagger/OpenAPI

Para la documentación de la API se integró Swagger mediante las dependencias de Springdoc OpenAPI en el archivo pom.xml.
Cada controlador fue documentado utilizando anotaciones como:

@Tag para agrupar los endpoints por recurso.

@Operation para describir el propósito de cada método.

@ApiResponse y @ApiResponses para detallar los posibles resultados (éxito, error, etc.).

@ExampleObject para incluir ejemplos reales de solicitudes y respuestas JSON.

Esta documentación permite visualizar e interactuar con la API desde la interfaz Swagger UI, disponible en la ruta:

http://localhost:8080/swagger-ui/index.html


Gracias a esta integración, los desarrolladores y testers pueden comprender rápidamente las funcionalidades de cada endpoint y validar los datos esperados sin revisar el código fuente.

Ejemplos de Requests y Responses

Ejemplo de creación de una mascota (request):

{
  "nombre": "Luna",
  "fechaNacimiento": "2021-01-15",
  "sexo": "Hembra",
  "descripcion": "Gata doméstica cariñosa",
  "clienteId": 1002,
  "tipoAnimal": "Gato"
}


Ejemplo de respuesta exitosa (response):

{
  "id": 10,
  "nombre": "Luna",
  "fechaNacimiento": "2021-01-15",
  "sexo": "Hembra",
  "descripcion": "Gata doméstica cariñosa",
  "proximaFechaVacunacion": "2026-01-15",
  "tipoAnimal": "Gato",
  "clienteId": 1002,
  "colorPelaje": "Blanco"
}

Descripción de Errores

Se implementó un manejador global de excepciones mediante la clase GlobalExceptionHandler, anotada con @RestControllerAdvice.
Esta clase intercepta los errores comunes y devuelve respuestas estandarizadas, facilitando la depuración y la experiencia del usuario.

Ejemplo de error al no encontrar una mascota:

{
  "status": 404,
  "message": "Mascota con ID 99 no encontrada",
  "path": "/api/mascotas/99",
  "timestamp": "2025-11-04T19:20:00"
}
