Feature: US10 Iniciar sesión
  Como usuario
  quiero iniciar sesión con correo y contraseña
  para acceder a mi cuenta.

  Scenario Outline: Inicio de sesión exitoso
    Given el usuario ingresa su <correo> y <contraseña> válidos
    When envía la solicitud de inicio de sesión
    Then el sistema autentica al usuario y retorna un <token de acceso>

    Examples:
      | correo         | contraseña | token de acceso      |
      | user@email.com | pass123    | "eyJhbGciOi..."      |

  Scenario Outline: Fallo al iniciar sesión por credenciales inválidas
    Given el usuario ingresa un <correo> o <contraseña> inválidos
    When envía la solicitud de inicio de sesión
    Then el sistema retorna un <mensaje de error>

    Examples:
      | correo         | contraseña | mensaje de error                |
      | user@email.com | wrongpass  | "Credenciales inválidas"        |
      | bad@email.com  | pass123    | "Usuario no encontrado"         |