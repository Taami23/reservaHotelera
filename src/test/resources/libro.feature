#Author: your.email@your.domain.com

Feature: Servicios asociados a un libro

  Scenario: Recuperar un libro mediante su id
    Given existe un libro; titulo "Ingenieria de Software", anoPublic 2020, editorial "Pearson", diasPrestamo 12
    When solicito el libro que posee el id 1
    Then obtengo el estado Ok y el libro con id 1 y titulo "Ingenieria de Software"

  Scenario: Recuperar los libros usando una palabra clave de su titulo
    Given existe un libro; titulo "Biologia Molecular", anoPublic 2016, editorial "Pearson", diasPrestamo 15
    And existe un libro; titulo "Estructuras de Datos", anoPublic 2015, editorial "Pearson", diasPrestamo 5
    When solicito los libros que inician con la keyword "Biologia"
    Then obtengo el estado Ok y 1 libro con titulo "Biologia Molecular"

  Scenario: Agregar un nuevo libro a la biblioteca
    Given se tiene un nuevo libro; titulo "Ingenieria de Software", anoPublic 2020, editorial "Pearson", diasPrestamo 10
    When solicito se agregue el libro a la biblioteca
    Then obtengo el estado "Created" y el libro agregado tiene como titulo "Ingenieria de Software" y dias de prestamo 10















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
