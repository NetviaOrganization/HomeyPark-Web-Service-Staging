Feature: US40 Registrar vehículo
  Como usuario
  quiero añadir mi vehículo en la aplicación
  para poder asociarlo a mis reservas.

  Scenario Outline: Registro exitoso de vehículo
    Given el usuario <usuario> ha iniciado sesión
    And proporciona los datos del vehículo: <placa>, <modelo>, <marca>
    When envía la solicitud de registro de vehículo
    Then el sistema registra el vehículo y lo asocia a su cuenta

    Examples:
      | usuario | placa   | modelo  | marca  |
      | Juan    | ABC123  | ModelX  | Tesla  |

  Scenario Outline: Registro fallido por datos inválidos
    Given el usuario <usuario> ha iniciado sesión
    And proporciona datos inválidos del vehículo: <placa>, <modelo>, <marca>
    When envía la solicitud de registro de vehículo
    Then el sistema retorna un <mensaje de error>

    Examples:
      | usuario | placa  | modelo | marca | mensaje de error                |
      | Juan    |        | ModelX | Tesla | "La placa es obligatoria"       |
      | Juan    | ABC123 |        | Tesla | "El modelo es obligatorio"      |