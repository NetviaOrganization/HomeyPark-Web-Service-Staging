Feature: US42 Eliminar vehículo
  Como usuario
  quiero eliminar mi vehículo en la aplicación
  para gestionar mis vehículos registrados.

  Scenario Outline: Eliminación exitosa de vehículo
    Given el usuario <usuario> ha iniciado sesión
    And tiene un vehículo registrado con <placa>
    When solicita eliminar el vehículo
    Then el sistema elimina el vehículo y confirma la eliminación

    Examples:
      | usuario | placa   |
      | Juan    | ABC123  |

  Scenario Outline: Fallo al eliminar vehículo inexistente
    Given el usuario <usuario> ha iniciado sesión
    And no tiene un vehículo registrado con <placa>
    When solicita eliminar el vehículo
    Then el sistema retorna un <mensaje de error>

    Examples:
      | usuario | placa   | mensaje de error                |
      | Juan    | XYZ999  | "Vehículo no encontrado"        |