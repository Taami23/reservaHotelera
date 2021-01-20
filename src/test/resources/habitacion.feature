
Feature: Servicios asociados a una habitacion

  Scenario: filtrar habitación
    Given una lista de habitaciones
    When deseo filtrar habitaciones segun su precio entre 10000 y 90000
    Then obtengo el estado "ok" y una lista de habitaciones dentro del rango
