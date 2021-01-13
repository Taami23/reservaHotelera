
Feature: Servicios asociados a una habitacion

  Scenario: Agregar nueva Habitacion a un Hotel
    Given existe una nueva habitacion; nroHabitacion "H12", precio 20000, pisoHabitacion 3, enUso 0
    When deseo agregar una habitacion
    Then obtengo el estado "created" y la habitacion agregada tiene como numero de Habitacion "H12"
