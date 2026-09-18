# BowlTech — Taller TDD

## 1. Identificación

- Nombre completo: Juan David Munar Chaparro
- Código estudiantil: 1000103253
- Correo institucional: juan.munar-c@mail.escuelaing.edu.co

## 2. Descripción del Sistema

BowlTech es el núcleo de cálculo oficial para partidas estándar de bolos de diez marcos o frames. Su objetivo es modelar con precisión el ciclo de vida de un juego reglamentario, aislando la mutabilidad de los lanzamientos del cálculo matemático de las bonificaciones.

### Reglas de Negocio Implementadas

- Marcos regulares (Frames 1 al 9): Cada marco admite un máximo de 2 lanzamientos. Si el jugador derriba los 10 pinos en el primer tiro, se marca un Strike y el turno concluye de inmediato. Si se derriban los 10 pinos en la suma de ambos tiros, se anota un Spare. Si quedan pinos en pie, se considera un Marco Abierto (Open Frame).
- Décimo marco (Reglas especiales): Otorga un tercer tiro de bonificación únicamente si el jugador consigue un Strike en el primer tiro o un Spare en el segundo tiro. Si es un marco abierto, concluye estrictamente tras el segundo lanzamiento.
- Cálculo de bonificaciones:
  - Spare: Suma los 10 pinos del marco más los pinos derribados en el lanzamiento inmediatamente posterior.
  - Strike: Suma los 10 pinos del marco más los pinos derribados en los dos lanzamientos posteriores (contemplando la acumulación por strikes dobles o consecutivos).
  - Juego perfecto: Doce strikes consecutivos acumulan el puntaje máximo oficial de 300 puntos.
- Integridad del estado: Bloquea lanzamientos fuera del rango permitido de 0 a 10 pinos, sumas superiores a 10 pinos en marcos regulares y consultas de puntuación final mediante `score()` si la partida no ha concluido en su totalidad (IllegalStateException).

### Responsabilidades por Clase (Single Responsibility Principle)

- `BowlingGame`: Gobierna el estado mutable de la partida, la recepción secuencial de los lanzamientos (`roll`), el avance de turnos y la consulta de finalización del ciclo de vida (`isComplete`).
- `BowlingScorer`: Servicio de dominio inmutable y sin estado. Recibe la lista de marcos finalizados y ejecuta el cálculo matemático de bonificaciones proyectadas hacia adelante.
- `Frame`: Entidad de soporte que encapsula los lanzamientos pertenecientes a un marco individual, determina su tipo de jugada y valida si el marco está completo internamente.
- `FrameType`: Enumeración fuertemente tipada que cataloga el resultado de cada marco (STRIKE, SPARE, OPEN).

## 3. Evidencia TDD (Ciclo RED -> GREEN -> REFACTOR)

Se documenta el ciclo riguroso de TDD implementado durante el Caso B1 (Gutter Game — Partida con todos los lanzamientos en cero):

### Fase RED (Prueba Fallida Inicial)

Se escribió la prueba unitaria `gutterGame_scoresZero()` en `BowlingScorerTest.java` antes de codificar la lógica de cálculo en el motor. La prueba simuló una partida completa de 20 lanzamientos consecutivos derribando 0 pinos y ejecutó la aserción esperando un total acumulado de 0 puntos (`assertEquals(0, game.score())`). La ejecución falló legítimamente en la consola debido a la ausencia del método de cálculo funcional en `BowlingScorer`, confirmando que la prueba era sensible a la falta de implementación.

<img width="1112" height="601" alt="tdd-red" src="https://github.com/user-attachments/assets/5ce9a0c4-8fa2-4fda-a125-cbee4f3f2952" />

### Fase GREEN (Implementación Mínima)

Se escribió la solución más simple en `BowlingScorer.java` para satisfacer el contrato de la prueba sin sobreingeniería: inicializar el acumulador en 0, iterar sobre la lista de marcos sumando sus pinos y retornar el total. Al correr `mvn test`, la prueba unitaria compiló y pasó a verde de inmediato, arrojando BUILD SUCCESS en la terminal.

<img width="1059" height="699" alt="tdd-green" src="https://github.com/user-attachments/assets/9ab47858-0c6f-44d2-9d73-f79a0298b901" />

### Fase REFACTOR (Optimización Estructural sin Alterar Comportamiento)

Con la prueba en verde como red de seguridad, se realizaron mejoras clave de diseño y mantenibilidad:

- Extracción de helper en la suite de pruebas: Se creó el método auxiliar reutilizable `rollMany(BowlingGame game, int times, int pins)` para eliminar la duplicación de invocar 20 veces consecutivas `game.roll(0)`, haciendo el test mucho más expresivo y preparando la infraestructura de pruebas para los siguientes casos.
- Eliminación de estado mutable: Se sustituyó el ciclo imperativo y la variable acumuladora utilizando Java Streams:

<img width="1011" height="754" alt="tdd-refactor" src="https://github.com/user-attachments/assets/8e9b7347-e3c9-40cf-aec9-6f1ebba8f3e9" />


## 4. Cobertura de Código con JaCoCo

### Métricas Obtenidas

<img width="1012" height="203" alt="jacoco-antes" src="https://github.com/user-attachments/assets/1e7b491f-8899-4b4b-80ba-7971fc266392" />


### Pruebas que Incrementaron la Cobertura

El aumento significativo en la cobertura de instrucciones y ramas condicionales provino de:

- Módulo B: La inclusión de pruebas para strikes consecutivos (B5), juego con todos los marcos en spare (B6) y el juego perfecto con 12 strikes (B7), las cuales forzaron la ejecución de las ramas que resuelven los bonos provenientes de dos marcos hacia adelante.

<img width="1016" height="216" alt="jacoco-despues" src="https://github.com/user-attachments/assets/043d92a9-24ab-495b-afaf-cc08e3cbfe91" />


## 5. Análisis Estático con SonarQube

### Métricas del Dashboard Oficial

- Quality Gate: Passed (Aprobado).
- Confiabilidad (Reliability): Calificación A con 0 Bugs.
- Seguridad (Security): Calificación A con 0 Vulnerabilidades y 0 Security Hotspots.
- Mantenibilidad (Maintainability): Calificación A.
- Cobertura (Coverage): 96.5% sincronizado directamente desde JaCoCo sobre 78 líneas ejecutables.
- Duplicación de código: 0.0% en 254 líneas evaluadas.
- Deuda técnica inicial: 2 code smells detectados (7 minutos de esfuerzo estimado de remediación).
- Deuda técnica final: 0 issues tras aplicar las correcciones solicitadas por el analizador.

<img width="1537" height="739" alt="sonarQube-antes" src="https://github.com/user-attachments/assets/44f71183-7671-4332-b960-7461c3982fd0" />


## 6. Registro de Pull Requests

El ciclo de desarrollo se administró mediante ramas temáticas bajo el flujo de trabajo GitFlow, aislando las características funcionales antes de integrarlas formalmente sobre la rama base del proyecto.

- Pull Request Principal: Integración completa de motor de puntuación, pruebas unitarias TDD, JaCoCo y SonarQube.
- Enlace al PR: Pull Request #1 (disponible en la pestaña de Pull Requests del repositorio GitHub).
- Rama Origen: `feature/MunarJuan_bowling`
- Rama Destino: `develop`
- Fecha de Merge: 17 de Septiembre de 2026.
- Módulos que cubre: Módulo A (`BowlingGame.roll()`), Módulo B (`BowlingScorer.calculate()`), Módulo C (`BowlingGame.isComplete()`), JaCoCo al 85%, y reporte de SonarQube.
- Estado: Merged sin conflictos.

## 7. Reflexión

### 01. ¿Qué caso edge del Bowling fue el más difícil de implementar con TDD y por qué?

El caso más desafiante fue la resolución del décimo marco con múltiples strikes consecutivos (Casos B5, B7 y C5). En los primeros nueve marcos, cada frame con strike contiene un único lanzamiento. Sin embargo, en el marco diez esta convención se rompe: los lanzamientos de bonificación se almacenan dentro de la lista interna del mismo décimo marco. Gestionar esta excepción sin introducir excepciones por índice fuera de rango ni romper la inmutabilidad requirió pensar una solución mas robusta.

### 02. ¿Qué parte del código cambió durante REFACTOR sin modificar el comportamiento observable?

La estructura interna de la clase `BowlingScorer`. Inicialmente, el método `calculate()` acumulaba un ciclo iterativo con condicionales anidados e indexación manual directa. Durante la etapa de refactorización se extrajeron constantes descriptivas (`REGULAR_FRAMES_LIMIT = 9`, `STRIKE_BASE_SCORE = 10`, `SPARE_BASE_SCORE = 10`) y métodos privados puros (`scoreForFrame`, `calculateStrikeBonus` y `calculateSpareBonus`). Las 22 pruebas unitarias continuaron pasando en verde sin ninguna modificación en los contratos públicos, evidenciando una reducción sustancial en la complejidad cognitiva del componente.

### 03. ¿Qué casos de prueba descubriste al revisar el reporte de cobertura de JaCoCo que no habías considerado antes?

Al analizar las ramas condicionales resaltadas en amarillo dentro del reporte HTML de JaCoCo para el método `roll()`, se descubrió que la validación para impedir derribar más de 10 pinos no estaba evaluando el escenario acumulado dentro de un marco abierto estándar: un primer lanzamiento válido (por ejemplo, 6 pinos) seguido de un segundo lanzamiento ilegal (por ejemplo, 7 pinos, sumando 13).

### 04. ¿Qué hallazgo de SonarQube produjo un cambio real en el código?

El hallazgo más significativo fue la detección del campo privado no utilizado `currentFrame` en la clase `BowlingGame`. Durante el diseño preliminar se concibió como un contador manual, pero al adoptar el principio de Única Fuente de Verdad delegando el estado en el tamaño de la lista de marcos (`frames.size()`), el atributo quedó como código muerto. SonarQube lo reportó como un code smell de mantenibilidad media. Adicionalmente, la otra advertencia condujo a la optimización sintáctica de `BowlingScorerTest`, sustituyendo una expresión lambda por una referencia directa a método (`game::score`).
