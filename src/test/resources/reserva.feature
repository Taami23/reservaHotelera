
  Feature: Servicios asociados a una reserva

    Scenario: Editar Reserva
      Given existe una reserva; fechaInicio "2020/01/01", fechaTermino "2020/02/02", montoFinal 20000, idCliente 1, idHabitacion 1
      When deseo editar la fecha de termino a "2020/03/03" de la reserva
      Then me aseguro que los campos tengan datos correctos y obtengo el estado "created"


