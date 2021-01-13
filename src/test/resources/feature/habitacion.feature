#Author: your.email@your.domain.com

Feature: Servicios asociados a una habitacion

#  Hotel id int, nombre, numeroHa int, direccion, telefono, correo, contrasena
#  Cliente id int, nombre, rut, nacimiento date, telefono, correo, contrasena
#  Habitacion id int, nroHabitacion, precio int, pisoHabitacion int, enUso int

#  Scenario: Guardar un hotel como favorito
##    Given existe un hotel; id 1, nombre "La cabañita", nroHabitaciones 10, direccion "su casa", telefono "9120877499",correo "lacabañita@gmail.com", contrasena "cabaña123"
#    Given existe un cliente; nombre "Juan Lopez", rut "19415903-k", nacimiento "19-01-97", telefono "93427499",correo "jlopez@gmail.com", contrasena "lopezito"
#    When solicito buscar habitaciones con precio menor a  100000
#    Then obtengo el estado Ok y una lista con las habitaciones

  Scenario: Agregar nueva Habitacion a un Hotel
#    Given existe un hotel; nombre "La cabañita", nroHabitaciones 10, direccion "su casa", telefono "9120877499",correo "lacabañita@gmail.com", contrasena "cabaña123"
    Given existe una nueva habitacion; nroHabitacion "H12", precio 20000, pisoHabitacion 3, enUso 0
    When deseo agregar una habitacion
    Then obtengo el estado "created" y la habitacion agregada tiene como numero de Habitacion "H12"
#

  Scenario: filtrar Habitacion
    Given se tiene una lista de habitaciones
    When solicito filtrar las habitaciones por precio entre 10000 y 50000 pesos
    Then obtengo el estado "ok" y la lista con las habitaciones dentro del rango















#  @tag2
#  Scenario Outline: Title of your scenario outline
#    Given I want to write a step with <name>
#    When I check for the <value> in step
#    Then I verify the <status> in step
#
#    Examples:
#      | name  | value | status  |
#      | name1 |     5 | success |
#      | name2 |     7 | Fail    |
