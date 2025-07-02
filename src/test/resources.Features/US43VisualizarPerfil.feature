Feature: US43 Visualizar perfil de usuario
  Como usuario
  quiero visualizar mi perfil
  para conocer y gestionar mi información personal.

  Scenario Outline: Visualización exitosa del perfil
    Given el usuario <usuario> ha iniciado sesión
    When solicita ver su perfil
    Then el sistema muestra la <información del perfil> del usuario

    Examples:
      | usuario | información del perfil                      |
      | Juan    | nombre: Juan, apellido: Pérez, correo: ... |

  Scenario Outline: Fallo al visualizar perfil por no autenticado
    Given el usuario no ha iniciado sesión
    When solicita ver su perfil
    Then el sistema retorna un <mensaje de error>

    Examples:
      | mensaje de error                |
      | "Usuario no autenticado"        |