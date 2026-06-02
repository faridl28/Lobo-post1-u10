# Lobo-post1-u10 — Suite de Pruebas con JUnit 5, Mockito y JaCoCo

## Ejecución

```bash
# pulido-post1-u10 — Suite de Pruebas con JUnit 5, Mockito y JaCoCo

## Ejecución

```bash
mvn clean test
```

## Descripción de los tests implementados

### TareaServiceTest
Pruebas unitarias sobre la lógica de negocio usando `@ExtendWith(MockitoExtension.class)`. El repositorio se simula con `@Mock` y el servicio se inyecta con `@InjectMocks`.

- `crear_conTituloValido_guardaYRetorna` — verifica que al crear una tarea con título válido se invoca `repo.save()` y se retorna la tarea.
- `crear_conTituloVacio_lanzaIllegalArgumentException` — verifica que un título en blanco lanza excepción y nunca llama al repositorio.
- `crear_conTituloNull_lanzaIllegalArgumentException` — verifica que un título null lanza excepción sin invocar el repositorio.
- `buscarPorId_noExiste_lanzaEntityNotFoundException` — verifica que buscar un id inexistente lanza `EntityNotFoundException`.
- `buscarPorId_existe_retornaTarea` — verifica que buscar un id existente retorna la tarea correcta.
- `completar_tareaExistente_marcaComoCompletada` — verifica que al completar una tarea se establece `completada = true` y se guarda.

### TareaControllerTest
Pruebas de la capa web en aislamiento usando `@WebMvcTest`. El servicio se simula con `@MockBean` y las peticiones HTTP se ejecutan con `MockMvc`.

- `get_tareaExiste_retorna200` — verifica que `GET /api/tareas/{id}` retorna 200 con el JSON de la tarea.
- `get_noExiste_retorna404` — verifica que `GET /api/tareas/{id}` retorna 4xx cuando la tarea no existe.
- `post_tareaValida_retorna201` — verifica que `POST /api/tareas` con JSON válido retorna 201 con la tarea creada.
- `get_listarTodas_retorna200` — verifica que `GET /api/tareas` retorna 200 con la lista de tareas.
- `patch_completar_retorna200` — verifica que `PATCH /api/tareas/{id}/completar` retorna 200 con `completada: true`.

### TareaRepositoryTest
Pruebas de la capa de persistencia usando `@DataJpaTest` con base de datos H2 en memoria. Los cambios se revierten automáticamente entre tests.

- `findByCompletada_false_retornaUnaTarea` — verifica que el query personalizado retorna solo las tareas pendientes.
- `findByCompletada_true_retornaListaVacia` — verifica que no hay tareas completadas en un contexto limpio.
- `save_nuevaTarea_asignaId` — verifica que al guardar una tarea se le asigna un id autogenerado.

## Evidencia de cobertura JaCoCo

![Reporte JaCoCo](capturas/reporte.png)

| Paquete | Instrucciones | Branches | Líneas |
|---|---|---|---|
| com.ufps.tareas.service | 80% | 100% | 80% |
| com.ufps.tareas.controller | 81% | n/a | 81% |
| com.ufps.tareas.entity | 80% | n/a | 80% |
| com.ufps.tareas.exception | 60% | n/a | 60% |
| **Total** | **76%** | **100%** | **76%** |

> Umbral mínimo configurado: 70% ✅ — `mvn jacoco:check` finaliza sin errores.
