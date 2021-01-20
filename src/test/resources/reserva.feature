
  Feature: Servicios asociados a una reserva

    Scenario: Editar Reserva
      Given existe una reserva; fechaInicio "2020/01/01", fechaTermino "2020/02/02", montoFinal 20000, idCliente 1, idHabitacion 1
      When deseo editar la fecha de termino a "2020/03/03" de la reserva
      Then me aseguro que los campos tengan datos correctos y obtengo el estado "created"

    Scenario: Agregar nueva Habitacion a un Hotel
      Given existe una nueva habitacion; nroHabitacion "H12", precio 20000, pisoHabitacion 3, enUso 0
      When deseo agregar una habitacion
      Then obtengo el estado "created" y la habitacion agregada tiene como numero de Habitacion "H12"

    Scenario: filtrar habitación
      Given una lista de habitaciones
      When deseo filtrar habitaciones segun su precio entre 10000 y 90000
      Then obtengo el estado "ok" y una lista de habitaciones dentro del rango

