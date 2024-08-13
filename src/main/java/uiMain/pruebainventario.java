package uiMain;
import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;


public class pruebainventario {

	public static void main(String[] args) {
		//Objetos establecimientos
		Establecimiento tiendaRopa = new Establecimiento("Tienda de Ropa", "Av. Principal 123", 50, cuenta1, "Asociación de Comerciantes", jefe1, funeraria1);
        Establecimiento tiendaElectrodomesticos = new Establecimiento("Electrodomésticos ABC", "Calle 45 #678", 80, cuenta1, "Red de Comercio", jefe1, funeraria1);
        Establecimiento concesionarioAutos = new Establecimiento("Autos del Norte", "Av. Automotriz 321", 100, cuenta1, "Asociación de Vehículos", jefe1, funeraria1);
        Establecimiento tiendaMuebles = new Establecimiento("Muebles y Decoración", "Calle Mueblera 56", 60, cuenta1, "Asociación de Muebles", jefe1, funeraria1);
        Establecimiento libreria = new Establecimiento("Librería Central", "Calle del Libro 789", 40, cuenta1, "Red de Librerías", jefe1, funeraria1);
        
        Establecimiento supermercado = new Establecimiento("Supermercado XYZ", "Avenida Comercial 101", 150, cuenta1, "Asociación de Supermercados", jefe1, funeraria1);
        Establecimiento farmacia = new Establecimiento("Farmacia Salud", "Calle Médica 202", 30, cuenta1, "Red de Farmacias", jefe1, funeraria1);
        Establecimiento tiendaMascotas = new Establecimiento("Tienda de Mascotas", "Av. Pet 303", 25, cuenta1, "Asociación de Mascotas", jefe1, funeraria1);
        Establecimiento tiendaRelojes = new Establecimiento("Relojes Finos", "Calle del Tiempo 404", 20, cuenta1, "Asociación de Relojeros", jefe1, funeraria1);
        Establecimiento tiendaJuguetes = new Establecimiento("Juguetería Alegría", "Calle de los Niños 505", 50, cuenta1, "Red de Jugueterías", jefe1, funeraria1);
        
        Establecimiento concesionarioMotos = new Establecimiento("Motos del Sur", "Av. Motorizada 606", 70, cuenta1, "Asociación de Motociclistas", jefe1, funeraria1);
        Establecimiento tiendaHerramientas = new Establecimiento("Herramientas y Más", "Calle del Trabajo 707", 60, cuenta1, "Red de Ferreterías", jefe1, funeraria1);
        Establecimiento tiendaDeportes = new Establecimiento("Deportes y Aventura", "Av. Deportiva 808", 80, cuenta1, "Asociación de Deportes", jefe1, funeraria1);
        Establecimiento tiendaElectronica = new Establecimiento("Electrónica y Tecnología", "Calle Digital 909", 90, cuenta1, "Red de Tecnología", jefe1, funeraria1);
        Establecimiento tallerMecanico = new Establecimiento("Taller Mecánico", "Calle Mecánica 1010", 35, cuenta1, "Asociación de Talleres", jefe1, funeraria1);
        
        Establecimiento centroContratacion = new Establecimiento("Centro de Contratación ABC", "Calle Laboral 111", 120, cuenta1, "Red de Recursos Humanos", jefe1, funeraria1);
        Establecimiento agenciaViajes = new Establecimiento("Agencia de Viajes XYZ", "Av. Turística 121", 50, cuenta1, "Asociación de Turismo", jefe1, funeraria1);
        Establecimiento tiendaRopaDeportiva = new Establecimiento("Ropa Deportiva", "Calle Fitness 131", 40, cuenta1, "Asociación de Deportes", jefe1, funeraria1);
        Establecimiento tiendaComputadoras = new Establecimiento("Computadoras y Más", "Calle Tecnológica 141", 60, cuenta1, "Red de Computación", jefe1, funeraria1);
        Establecimiento concesionarioBicicletas = new Establecimiento("Bicicletas del Este", "Av. Ciclística 151", 70, cuenta1, "Asociación de Ciclistas", jefe1, funeraria1);
        
        // empleados tipo jefe
        Empleado jefe1 = new Empleado("Carlos Pérez", 100100100L, 45, "Ciudad 1", cuentaEmpleado1, "mañana", "sepulturero", 3000.00, 10);
        Empleado jefe2 = new Empleado("María Gómez", 200200200L, 39, "Ciudad 2", cuentaEmpleado2, "tarde", "cremador", 3200.00, 12);
        Empleado jefe3 = new Empleado("Juan Rodríguez", 300300300L, 50, "Ciudad 3", cuentaEmpleado1, "noche", "padre", 2800.00, 15);
        Empleado jefe4 = new Empleado("Ana Fernández", 400400400L, 42, "Ciudad 4", cuentaEmpleado2, "mañana", "sepulturero", 2900.00, 8);
        Empleado jefe5 = new Empleado("Luis García", 500500500L, 37, "Ciudad 5", cuentaEmpleado1, "tarde", "cremador", 3100.00, 11);
        
        Empleado jefe6 = new Empleado("Elena Martínez", 600600600L, 44, "Ciudad 6", cuentaEmpleado2, "noche", "padre", 2950.00, 9);
        Empleado jefe7 = new Empleado("Miguel Hernández", 700700700L, 41, "Ciudad 7", cuentaEmpleado1, "mañana", "sepulturero", 3000.00, 10);
        Empleado jefe8 = new Empleado("Laura López", 800800800L, 38, "Ciudad 8", cuentaEmpleado2, "tarde", "cremador", 3050.00, 12);
        Empleado jefe9 = new Empleado("Pedro Sánchez", 900900900L, 49, "Ciudad 9", cuentaEmpleado1, "noche", "padre", 2850.00, 14);
        Empleado jefe10 = new Empleado("Lucía Ramírez", 1010101010L, 40, "Ciudad 10", cuentaEmpleado2, "mañana", "sepulturero", 3000.00, 8);
        
        Empleado jefe11 = new Empleado("Andrés Torres", 1111111111L, 43, "Ciudad 11", cuentaEmpleado1, "tarde", "cremador", 3100.00, 10);
        Empleado jefe12 = new Empleado("Marta Ruiz", 1212121212L, 36, "Ciudad 12", cuentaEmpleado2, "noche", "padre", 2950.00, 13);
        Empleado jefe13 = new Empleado("Roberto Castro", 1313131313L, 48, "Ciudad 13", cuentaEmpleado1, "mañana", "sepulturero", 2900.00, 12);
        Empleado jefe14 = new Empleado("Patricia Morales", 1414141414L, 39, "Ciudad 14", cuentaEmpleado2, "tarde", "cremador", 3000.00, 11);
        Empleado jefe15 = new Empleado("José Ortiz", 1515151515L, 47, "Ciudad 15", cuentaEmpleado1, "noche", "padre", 2800.00, 16);
        
        Empleado jefe16 = new Empleado("Clara Ibáñez", 1616161616L, 42, "Ciudad 16", cuentaEmpleado2, "mañana", "sepulturero", 3050.00, 9);
        Empleado jefe17 = new Empleado("Fernando Delgado", 1717171717L, 45, "Ciudad 17", cuentaEmpleado1, "tarde", "cremador", 3200.00, 12);
        Empleado jefe18 = new Empleado("Alicia Ramos", 1818181818L, 38, "Ciudad 18", cuentaEmpleado2, "noche", "padre", 2900.00, 10);
        Empleado jefe19 = new Empleado("Sergio Peña", 1919191919L, 50, "Ciudad 19", cuentaEmpleado1, "mañana", "sepulturero", 3100.00, 15);
        Empleado jefe20 = new Empleado("Isabel Vega", 2020202020L, 46, "Ciudad 20", cuentaEmpleado2, "tarde", "cremador", 3000.00, 11);
        
     //  cuentas bancarias para establecimientos
        CuentaBancaria cuentaEstablecimiento1 = new CuentaBancaria(1111111111L, "Tienda de Ropa", 10000.00, "Banco Nacional");
        CuentaBancaria cuentaEstablecimiento2 = new CuentaBancaria(2222222222L, "Electrodomésticos ABC", 15000.00, "Banco Internacional");
        CuentaBancaria cuentaEstablecimiento3 = new CuentaBancaria(3333333333L, "Autos del Norte", 20000.00, "Banco Popular");
        CuentaBancaria cuentaEstablecimiento4 = new CuentaBancaria(4444444444L, "Muebles y Decoración", 12000.00, "Banco Nacional");
        CuentaBancaria cuentaEstablecimiento5 = new CuentaBancaria(5555555555L, "Librería Central", 9000.00, "Banco Internacional");

        //  cuentas bancarias para empleados
        CuentaBancaria cuentaEmpleado1 = new CuentaBancaria(6666666666L, "Carlos Pérez", 5000.00, "Banco Nacional");
        CuentaBancaria cuentaEmpleado2 = new CuentaBancaria(7777777777L, "María Gómez", 5500.00, "Banco Internacional");
        CuentaBancaria cuentaEmpleado3 = new CuentaBancaria(8888888888L, "Juan Rodríguez", 4800.00, "Banco Popular");
        CuentaBancaria cuentaEmpleado4 = new CuentaBancaria(9999999999L, "Ana Fernández", 5300.00, "Banco Nacional");
        CuentaBancaria cuentaEmpleado5 = new CuentaBancaria(1010101010L, "Luis García", 5200.00, "Banco Internacional");

        // Continuar con la creación de cuentas adicionales para empleados y establecimientos según sea necesario

        // Asignar cuentas a los establecimientos
        Establecimiento tiendaRopa = new Establecimiento("Tienda de Ropa", "Av. Principal 123", 50, cuentaEstablecimiento1, "Asociación de Comerciantes", jefe1, funeraria1);
        Establecimiento tiendaElectrodomesticos = new Establecimiento("Electrodomésticos ABC", "Calle 45 #678", 80, cuentaEstablecimiento2, "Red de Comercio", jefe2, funeraria1);
        Establecimiento concesionarioAutos = new Establecimiento("Autos del Norte", "Av. Automotriz 321", 100, cuentaEstablecimiento3, "Asociación de Vehículos", jefe3, funeraria1);
        Establecimiento tiendaMuebles = new Establecimiento("Muebles y Decoración", "Calle Mueblera 56", 60, cuentaEstablecimiento4, "Asociación de Muebles", jefe4, funeraria1);
        Establecimiento libreria = new Establecimiento("Librería Central", "Calle del Libro 789", 40, cuentaEstablecimiento5, "Red de Librerías", jefe5, funeraria1);

	}

}
