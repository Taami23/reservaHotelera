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

  Scenario: Editar Reserva
    Given existe una reserva; fechaInicio "2020/01/01", fechaTermino "2020/02/02", montoFinal 20000, idCliente 1, idHabitacion 1
    When deseo editar la fecha de termino a "2020/03/03" de la reserva
    Then me aseguro que los campos tengan datos correctos y obtengo el estado "ok"
