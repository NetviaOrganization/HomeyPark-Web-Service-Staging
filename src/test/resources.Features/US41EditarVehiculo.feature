Feature: US41 Editar información de vehículo
  Como usuario
  quiero editar la información de mi vehículo
  para mantener mis datos actualizados.

  Scenario Outline: Edición exitosa de vehículo
    Given el usuario <usuario> ha iniciado sesión
    And tiene un vehículo registrado con <placa>
    When actualiza la información del vehículo a <nuevo modelo> y <nueva marca>
    Then el sistema actualiza los datos y retorna la información actualizada

    Examples:
      | usuario | placa   | nuevo modelo | nueva marca |
      | Juan    | ABC123  | ModelY       | Tesla       |

  Scenario Outline: Fallo al editar vehículo por datos inválidos
    Given el usuario <usuario> ha iniciado sesión
    And tiene un vehículo registrado con <placa>
    When intenta actualizar el vehículo con datos inválidos
    Then el sistema retorna un <mensaje de error>

    Examples:
      | usuario | placa   | mensaje de error                |
      | Juan    | ABC123  | "El modelo es obligatorio"      |