Feature: US44 Editar perfil de usuario
  Como usuario
  quiero editar mi perfil
  para mantener mi información personal actualizada.

  Scenario Outline: Edición exitosa de perfil
    Given el usuario <usuario> ha iniciado sesión
    When actualiza su <nombre> y <apellido>
    Then el sistema actualiza el perfil y retorna la información actualizada

    Examples:
      | usuario | nombre   | apellido |
      | Juan    | Juanito  | Pérez    |

  Scenario Outline: Fallo al editar perfil por datos inválidos
    Given el usuario <usuario> ha iniciado sesión
    When intenta actualizar su perfil con datos inválidos
    Then el sistema retorna un <mensaje de error>

    Examples:
      | usuario | mensaje de error                |
      | Juan    | "El nombre es obligatorio"      |