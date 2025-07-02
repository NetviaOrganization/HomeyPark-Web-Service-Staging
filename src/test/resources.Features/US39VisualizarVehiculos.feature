Feature: US39 Visualizar vehículos registrados
  Como usuario
  quiero ver mis vehículos registrados
  para gestionar mis vehículos en la plataforma.

  Scenario Outline: Visualización exitosa de vehículos
    Given el usuario <usuario> ha iniciado sesión
    When solicita la lista de sus vehículos registrados
    Then el sistema muestra la <lista de vehículos> asociados a su cuenta

    Examples:
      | usuario | lista de vehículos                  |
      | Juan    | [ABC123 - Tesla ModelX, DEF456 - BMW X3] |

  Scenario Outline: Visualización sin vehículos registrados
    Given el usuario <usuario> ha iniciado sesión
    When solicita la lista de sus vehículos registrados
    And no tiene vehículos asociados
    Then el sistema muestra una <lista vacía>

    Examples:
      | usuario | lista vacía |
      | Ana     | []          |