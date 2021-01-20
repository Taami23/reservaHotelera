Feature:

  Scenario: Servicios asociados a una reserva
    Given una lista de reservas
    When deseo buscar las reservas entre las fechas "2020/12/23" y "2021/01/20"
    Then obtengo el estado "ok" y una lista de las habitaciones detro de las fechas ingresadas

  Scenario: Listar reservas
    Given existe un cliente; id 1, nombre "Esteban Sepulveda", rut "20.401.415-5", fechaNaci "06-01-1998", telefono "+56942587487", email "dani@gmail.com", contrasena "dani1234"
    And  una lista de reservas
    When deseo filtrar reservas segun un cliente con id 1
    Then obtengo el estado "OK" y una lista de reservas del cliente
