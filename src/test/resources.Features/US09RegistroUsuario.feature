Feature: US09 Registro de usuario
  Como usuario
  quiero registrarme con correo, contraseña y datos de perfil
  para crear una cuenta.

  Scenario Outline: Registro exitoso de usuario
    Given el usuario ingresa su <correo>, <contraseña> y <datos de perfil> válidos
    When envía la solicitud de registro
    Then el sistema crea una nueva cuenta y retorna el perfil del usuario

    Examples:
      | correo              | contraseña | datos de perfil                |
      | user@email.com      | pass123    | nombre: Juan, apellido: Pérez  |

  Scenario Outline: Registro fallido por datos inválidos
    Given el usuario ingresa un <correo> o <contraseña> inválidos
    When envía la solicitud de registro
    Then el sistema retorna un <mensaje de error>

    Examples:
      | correo         | contraseña | mensaje de error                  |
      | useremail.com  | pass123    | "Correo electrónico inválido"     |
      | user@email.com |            | "La contraseña es obligatoria"    |