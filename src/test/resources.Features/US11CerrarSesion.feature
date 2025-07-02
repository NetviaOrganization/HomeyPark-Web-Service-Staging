Feature: US11 Cerrar sesión
  Como usuario
  quiero poder cerrar sesión
  para proteger mi información.

  Scenario Outline: Cierre de sesión exitoso
    Given el usuario <usuario> ha iniciado sesión y tiene un <token de acceso> válido
    When solicita cerrar sesión
    Then el sistema invalida el token y confirma el cierre de sesión

    Examples:
      | usuario | token de acceso      |
      | Juan    | "eyJhbGciOi..."      |

  Scenario Outline: Fallo al cerrar sesión por token inválido
    Given el usuario <usuario> intenta cerrar sesión con un <token de acceso> inválido
    When solicita cerrar sesión
    Then el sistema retorna un <mensaje de error>

    Examples:
      | usuario | token de acceso | mensaje de error           |
      | Juan    | "token_invalido" | "Token inválido o expirado" |