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
#  Scenario: Agregar un nuevo libro a la biblioteca
#    Given se tiene un nuevo libro; titulo "Ingenieria de Software", anoPublic 2020, editorial "Pearson", diasPrestamo 10
#    When solicito se agregue el libro a la biblioteca
#    Then obtengo el estado "Created" y el libro agregado tiene como titulo "Ingenieria de Software" y dias de prestamo 10















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
