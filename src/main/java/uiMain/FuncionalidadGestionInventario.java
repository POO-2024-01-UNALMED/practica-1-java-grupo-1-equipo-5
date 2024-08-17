package uiMain;

import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;

public class FuncionalidadGestionInventario {
	
    public static void main(String[] args) {
        // Inicializa las funerarias y otros datos necesarios aquí
        Funeraria[] funerarias = inicializarFunerarias();
        Scanner scanner = new Scanner(System.in);

        // Paso 1: Seleccionar y mostrar información de la funeraria
        Funeraria funerariaSeleccionada = seleccionarFuneraria(funerarias, scanner);
        imprimirInformacion(funerariaSeleccionada);

        // Paso 2: Realizar intercambio entre funerarias si es posible
        if (analizarIntercambios(funerarias, funerariaSeleccionada, scanner)) {
            // Si el intercambio es posible, procede con la asignación de recursos
            asignarRecursos(funerariaSeleccionada, scanner);
        }

        // Paso 3: Comprar productos faltantes o contratar empleados
        realizarCompras(funerariaSeleccionada, scanner);

        // Paso 4: Realizar encuesta sobre el desempeño de empleados y establecimientos
        realizarEncuesta(funerariaSeleccionada, scanner);

        System.out.println("Proceso completado.");
    }

    private static Funeraria seleccionarFuneraria(Funeraria[] funerarias, Scanner scanner) {
        while (true) {
            System.out.println("Seleccione una funeraria:");
            for (int i = 0; i < funerarias.length; i++) {
                System.out.println((i + 1) + ". " + funerarias[i].getNombre());
                System.out.println("   ____");
                System.out.println("  /    \\");
                System.out.println(" /______\\");
                System.out.println(" |      |  " + funerarias[i].getNombre());
                System.out.println(" |______|");
                System.out.println();
            }
            System.out.println((funerarias.length + 1) + ". Cancelar");

            int seleccion = scanner.nextInt() - 1;
            if (seleccion >= 0 && seleccion < funerarias.length) {
                return funerarias[seleccion];
            } else if (seleccion == funerarias.length) {
                System.out.println("Proceso cancelado.");
                System.exit(0);
            } else {
                System.out.println("Selección inválida. Intente de nuevo.");
            }
        }
    }

    public static void imprimirInformacion(Funeraria funeraria) {
        System.out.println("Nombre: " + funeraria.getNombre());
        System.out.println("Calificación: " + funeraria.getCalificacion());
        System.out.println("Cantidad de empleados: " + funeraria.getEmpleados().size());

        Producto[] productosVendidos = Funeraria.calcularProductosVendidos(funeraria);

        System.out.println("Productos más vendidos:");
        for (Producto producto : productosVendidos) {
            System.out.println("- " + producto.getNombre() + ": " + producto.getCantidadVendida());
            System.out.println("- " + producto.getNombre() + ": " + producto.getCantidadVendida());
            System.out.println("    ____");
            System.out.println("   |    |");
            System.out.println("   | " + producto.getNombre().charAt(0) + "  | " + producto.getCantidadVendida());
            System.out.println("   |____|");
            System.out.println();
        }

        System.out.println("------------------------");
    }

    private static boolean analizarIntercambios(Funeraria[] funerarias, Funeraria seleccionada, Scanner scanner) {
        for (Funeraria otraFuneraria : funerarias) {
            if (!otraFuneraria.equals(seleccionada)) {
                Producto[] productosSeleccionada = Funeraria.calcularProductosVendidos(seleccionada);
                Producto[] productosOtra = Funeraria.calcularProductosVendidos(otraFuneraria);

                for (Producto productoA : productosSeleccionada) {
                    for (Producto productoB : productosOtra) {
                        if (!productoA.getNombre().equals(productoB.getNombre()) && productoA.getCantidad() > 10 && productoB.getCantidad() > 10) {
                            System.out.println("Intercambio posible entre " + seleccionada.getNombre() + " y " + otraFuneraria.getNombre());
                            System.out.println("- " + seleccionada.getNombre() + " puede intercambiar " + productoA.getNombre() + " con " + otraFuneraria.getNombre());
                            System.out.println("- " + otraFuneraria.getNombre() + " puede intercambiar " + productoB.getNombre() + " con " + seleccionada.getNombre());

                            System.out.println("¿Desea realizar este intercambio? (S/N)");
                            String respuesta = scanner.next();
                            if (respuesta.equalsIgnoreCase("S")) {
                                realizarIntercambio(seleccionada, otraFuneraria, productoA, productoB, scanner);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static void realizarIntercambio(Funeraria funA, Funeraria funB, Producto productoA, Producto productoB, Scanner scanner) {
        // Mostrar empleados disponibles y vehículos
        System.out.println("Seleccione los empleados para realizar el intercambio:");
        List<Empleado> empleadosSeleccionados = seleccionarEmpleados(funA, scanner);

        System.out.println("Seleccione los vehículos para realizar el intercambio:");
        List<Vehiculo> vehiculosSeleccionados = seleccionarVehiculos(funA, scanner);

        // Mostrar productos y realizar intercambio
        System.out.println("Seleccione los productos para intercambiar:");
        int cantidadIntercambio = seleccionarCantidadProducto(productoA, scanner);
        realizarIntercambioProductos(funA, funB, productoA, productoB, cantidadIntercambio, empleadosSeleccionados, vehiculosSeleccionados);
    }

    private static List<Empleado> seleccionarEmpleados(Funeraria funeraria, Scanner scanner) {
        List<Empleado> empleadosSeleccionados = new ArrayList<>();
        for (Empleado empleado : funeraria.getEmpleados()) {
            System.out.println(empleado.getNombre() + " (" + empleado.getCargo() + ") - Jornada: " + empleado.getJornada());
            System.out.println("    O ");
            System.out.println("   /|\\");
            System.out.println("   / \\");
            System.out.println("¿Seleccionar este empleado? (S/N)");
            String respuesta = scanner.next();
            if (respuesta.equalsIgnoreCase("S")) {
                empleadosSeleccionados.add(empleado);
            }
        }
        return empleadosSeleccionados;
    }

    private static List<Vehiculo> seleccionarVehiculos(Funeraria funeraria, Scanner scanner) {
        List<Vehiculo> vehiculosSeleccionados = new ArrayList<>();
        for (Vehiculo vehiculo : funeraria.getVehiculos()) {
            System.out.println(vehiculo.getTipoVehiculo() + " - Capacidad: " + vehiculo.getCapacidad() + " - Conductor: " + vehiculo.getConductor().getNombre());
            System.out.println("   ______");
            System.out.println("  /|_||_\\`.__");
            System.out.println(" (   _    _ _\\");
            System.out.println(" =`-(_)--(_)-'");
            System.out.println("¿Seleccionar este vehículo? (S/N)");
            String respuesta = scanner.next();
            if (respuesta.equalsIgnoreCase("S")) {
                vehiculosSeleccionados.add(vehiculo);
            }
        }
        return vehiculosSeleccionados;
    }

    private static int seleccionarCantidadProducto(Producto producto, Scanner scanner) {
        System.out.println("Ingrese la cantidad de " + producto.getNombre() + " para intercambiar:");
        return scanner.nextInt();
    }

    private static void realizarIntercambioProductos(Funeraria funA, Funeraria funB, Producto productoA, Producto productoB, int cantidad, List<Empleado> empleados, List<Vehiculo> vehiculos) {
        // Lógica para realizar el intercambio, actualizar inventarios, etc.
        System.out.println("Intercambio realizado exitosamente.");
    }

    private static void realizarCompras(Funeraria funeraria, Scanner scanner) {
        Producto[] productosFaltantes = Funeraria.identificarProductosFaltantes(funeraria);
        if (productosFaltantes.length > 0) {
            System.out.println("Productos con menos de 10 existencias:");
            for (Producto producto : productosFaltantes) {
                System.out.println("- " + producto.getNombre() + ": " + producto.getCantidad());
            }

            // Comprar productos, contratar empleados, o comprar vehículos
            System.out.println("Seleccione una opción:");
            System.out.println("1. Comprar productos");
            System.out.println("2. Contratar empleados");
            System.out.println("3. Comprar vehículos");
            System.out.println("4. Cancelar");

            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    comprarProductos(funeraria, productosFaltantes, scanner);
                    break;
                case 2:
                    contratarEmpleados(funeraria, scanner);
                    break;
                case 3:
                    comprarVehiculos(funeraria, scanner);
                    break;
                case 4:
                    System.out.println("Operación cancelada.");
                    return;
                default:
                    System.out.println("Opción inválida.");
                    realizarCompras(funeraria, scanner);
            }
        } else {
            System.out.println("No hay productos faltantes.");
        }
    }

    private static void comprarProductos(Funeraria funeraria, Producto[] productosFaltantes, Scanner scanner) {
        System.out.println("Establecimientos que pueden vender los productos faltantes:");
        for (int i = 0; i < funeraria.getListadoProveedores().size(); i++) {
            Establecimiento est = funeraria.getListadoProveedores().get(i);
            System.out.println((i + 1) + ". " + est.getNombre() + " (Calificación: " + est.getCalificacion() + ")");
        }

        // Seleccionar establecimiento
        System.out.println("Seleccione el establecimiento para realizar la compra:");
        int seleccionEstablecimiento = scanner.nextInt() - 1;

        if (seleccionEstablecimiento >= 0 && seleccionEstablecimiento < funeraria.getListadoProveedores().size()) {
            Establecimiento proveedorSeleccionado = funeraria.getListadoProveedores().get(seleccionEstablecimiento);

            // Mostrar productos faltantes y realizar la compra
            for (Producto productoFaltante : productosFaltantes) {
                if (proveedorSeleccionado.tieneProducto(productoFaltante.getNombre())) {
                    System.out.println("¿Desea comprar " + productoFaltante.getNombre() + "? (S/N)");
                    String respuesta = scanner.next();

                    if (respuesta.equalsIgnoreCase("S")) {
                        System.out.println("Ingrese la cantidad a comprar:");
                        int cantidadCompra = scanner.nextInt();

                        if (cantidadCompra > 0) {
                            // Actualizar inventario
                            productoFaltante.setCantidad(productoFaltante.getCantidad() + cantidadCompra);

                            // Crear y almacenar la factura
                            Producto productoComprado = new Producto(productoFaltante.getNombre(), productoFaltante.getPrecio(), cantidadCompra);
                            ArrayList<Producto> productosComprados = new ArrayList<>();
                            productosComprados.add(productoComprado);
                            Factura nuevaFactura = new Factura(productosComprados, "Compra de productos");
                            
                            funeraria.agregarFactura(nuevaFactura);

                            System.out.println("Compra realizada exitosamente. Factura generada y almacenada.");
                        }
                    }
                }
            }
        } else {
            System.out.println("Selección inválida.");
        }
    }

    private static void contratarEmpleados(Funeraria funeraria, Scanner scanner) {
        System.out.println("Empleados disponibles para contratación:");
        for (Establecimiento est : funeraria.getListadoProveedoresEmpleados()) {
            for (Empleado empleado : est.getEmpleados()) {
                System.out.println("Establecimiento: " + est.getNombre() + ", Calificación: " + est.getCalificacion());
                System.out.println("  Empleado: " + empleado.getNombre() + ", Cargo: " + empleado.getCargo() + ", Experiencia: " + empleado.getExperiencia() + " años, Edad: " + empleado.getEdad());
            }
        }

        // Seleccionar empleados y realizar contratación
    }

    private static void comprarVehiculos(Funeraria funeraria, Scanner scanner) {
        System.out.println("Vehículos disponibles para la compra:");

        // Aquí asumimos que cada establecimiento puede tener vehículos a la venta
        for (Establecimiento est : funeraria.getListadoProveedoresVehiculos()) {
            for (Vehiculo vehiculo : est.getVehiculosEnVenta()) { 
                System.out.println("Establecimiento: " + est.getNombre() + ", Calificación: " + est.getCalificacion());
                System.out.println("  Vehículo: " + vehiculo.getTipoVehiculo() + ", Capacidad: " + vehiculo.getCapacidad() + ", Precio: " + vehiculo.getPrecio());
                
                // vehículo de forma visual
                System.out.println("   ______");
                System.out.println("  /|_||_\\`.__");
                System.out.println(" (   _    _ _\\");
                System.out.println(" =`-(_)--(_)-'");
                
                System.out.println("¿Desea comprar este vehículo? (S/N)");
                String respuesta = scanner.next();
                if (respuesta.equalsIgnoreCase("S")) {
                    // Aquí se realizaría la compra y la actualización de la lista de vehículos de la funeraria
                    funeraria.agregarVehiculo(vehiculo); 
                    est.removerVehiculoEnVenta(vehiculo); 
                    System.out.println("Vehículo comprado exitosamente.");
                }
            }
        }
    }
    
    private static void realizarEncuesta(Funeraria funeraria, Scanner scanner) {
        System.out.println("Realizando encuesta de desempeño...");

        // Encuesta para empleados
        for (Empleado empleado : funeraria.getEmpleados()) {
            System.out.println("Empleado: " + empleado.getNombre());
            System.out.println("Califique el desempeño del empleado (1-5):");
            int calificacion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            System.out.println("Ingrese una descripción opcional sobre el desempeño del empleado:");
            String descripcion = scanner.nextLine();

            empleado.setCalificacion(calificacion);
            empleado.setDescripcionCalificacion(descripcion);

            System.out.println("Calificación guardada: " + calificacion);
            if (!descripcion.isEmpty()) {
                System.out.println("Descripción: " + descripcion);
            }
        }

        // Encuesta para proveedores
        for (Establecimiento est : funeraria.getListadoProveedores()) {
            System.out.println("Proveedor: " + est.getNombre());
            System.out.println("Califique el desempeño del proveedor (1-5):");
            int calificacion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            System.out.println("Ingrese una descripción opcional sobre el desempeño del proveedor:");
            String descripcion = scanner.nextLine();

            est.setCalificacion(calificacion);
            est.setDescripcionCalificacion(descripcion);

            System.out.println("Calificación guardada: " + calificacion);
            if (!descripcion.isEmpty()) {
                System.out.println("Descripción: " + descripcion);
            }
        }

        System.out.println("Encuesta completada. Gracias por su retroalimentación.");
    }

    
    private static void asignarRecursos(Funeraria funeraria, Scanner scanner) {
        System.out.println("Asignación de recursos para la funeraria: " + funeraria.getNombre());

        // Selección de empleados
        System.out.println("Seleccione los empleados para realizar la tarea:");
        List<Empleado> empleadosSeleccionados = seleccionarEmpleados(funeraria, scanner);

        // Selección de vehículos
        System.out.println("Seleccione los vehículos para realizar la tarea:");
        List<Vehiculo> vehiculosSeleccionados = seleccionarVehiculos(funeraria, scanner);

        // Selección de productos
        System.out.println("Seleccione los productos a enviar:");
        List<Producto> productosSeleccionados = new ArrayList<>();
        for (Producto producto : funeraria.getProductos()) {
            System.out.println(producto.getNombre() + " - Cantidad disponible: " + producto.getCantidad());
            System.out.println("¿Cuántos desea enviar?");
            int cantidad = scanner.nextInt();
            if (cantidad > 0 && cantidad <= producto.getCantidad()) {
                Producto productoSeleccionado = new Producto(producto.getNombre(), producto.getPrecio(), cantidad);
                productosSeleccionados.add(productoSeleccionado);
            }
        }

        // Mostrar resumen de recursos seleccionados
        System.out.println("Resumen de recursos asignados:");
        System.out.println("Empleados:");
        for (Empleado empleado : empleadosSeleccionados) {
            System.out.println("- " + empleado.getNombre() + " (" + empleado.getCargo() + ")");
        }
        System.out.println("Vehículos:");
        for (Vehiculo vehiculo : vehiculosSeleccionados) {
            System.out.println("- " + vehiculo.getTipoVehiculo() + " - Conductor: " + vehiculo.getConductor().getNombre());
        }
        System.out.println("Productos:");
        for (Producto producto : productosSeleccionados) {
            System.out.println("- " + producto.getNombre() + ": " + producto.getCantidad() + " unidades");
        }

        
    }


    private static Funeraria[] inicializarFunerarias() {
    	// Ejemplo:
        //Cuentas Funerarias.
    	//public CuentaBancaria(long numeroCuenta, String titular, double saldoInicial, String banco)
    	CuentaBancaria cuentaFun1 = new CuentaBancaria(123, "Funeraria 1", 1000000, "Ala");
    	CuentaBancaria cuentaFun2 = new CuentaBancaria(1234, "Funeraria 2", 1000000, "Ala");
    	CuentaBancaria cuentaFun3 = new CuentaBancaria(12345, "Funeraria 3", 1000000, "Ala");
    	
    	//cuenta bancaria establecimientos
    	CuentaBancaria cuentalocal0 = new CuentaBancaria(123456, "local1 productos", 100000000, "Ala");
    	CuentaBancaria cuentalocal1= new CuentaBancaria(123457L, "local2 productos", 100000000.00, "Ala");
    	CuentaBancaria cuentalocal2= new CuentaBancaria(123458L, "local3 productos", 100000000.00, "Ala");
    	CuentaBancaria cuentalocal3= new CuentaBancaria(123459L, "local4 productos", 100000000.00, "Ala");
    	CuentaBancaria cuentalocal4= new CuentaBancaria(123460L, "local5 Vehiculos", 100000000.00, "Ala");
    	CuentaBancaria cuentalocal5= new CuentaBancaria(123461L, "local6 Vehiculos", 100000000.00, "Ala");
    	CuentaBancaria cuentalocal6= new CuentaBancaria(123462L, "local7 Vehiculos", 100000000.00, "Ala");
    	CuentaBancaria cuentalocal7= new CuentaBancaria(123463L, "local8 Empleados", 100000000.00, "Ala");
    	CuentaBancaria cuentalocal8= new CuentaBancaria(123464L, "local9 Empleados", 100000000.00, "Ala");
    	CuentaBancaria cuentalocal9= new CuentaBancaria(123465L, "local10 Empleados", 100000000.00, "Ala");

    	
    	//Cuenta jefe
    	CuentaBancaria cuentajefe = new CuentaBancaria(123456, "jefe1", 100000000, "Ala");
    	
    	// Funearias
    	//public Funeraria(String nombre, CuentaBancaria cuentaCorriente, CuentaBancaria cuentaAhorros)
    	Funeraria Fun1 = new Funeraria("Fun1",cuentaFun1,cuentaFun1);
    	Funeraria Fun2 = new Funeraria("Fun2",cuentaFun2,cuentaFun2);
    	Funeraria Fun3 = new Funeraria("Fun3",cuentaFun3,cuentaFun3);
    	
    	//Jefes establecimientos
    	//public Empleado(String nombre, long CC, CuentaBancaria cuentaBancaria,String jornada, String cargo, double salario
    	Empleado jefe1 = new Empleado("Fernando López", 123456789L,cuentajefe , "mañana", "JEFE1", 5500.00);
    	Empleado jefe2 = new Empleado("María García", 987654321L, cuentajefe, "tarde", "JEFE2", 5600.00);
    	Empleado jefe3 = new Empleado("Carlos Pérez", 112233445L, cuentajefe, "noche", "JEFE3", 5700.00);
    	Empleado jefe4 = new Empleado("Ana Martínez", 556677889L, cuentajefe, "mañana", "JEFE4", 5800.00);
    	Empleado jefe5 = new Empleado("Luis Rodríguez", 998877665L, cuentajefe, "tarde", "JEFE5", 5900.00);
    	Empleado jefe6 = new Empleado("Elena Fernández", 443322110L, cuentajefe, "noche", "JEFE6", 6000.00);
    	Empleado jefe7 = new Empleado("Jorge Gómez", 667788990L, cuentajefe, "mañana", "JEFE7", 6100.00);
    	Empleado jefe8 = new Empleado("Laura Díaz", 334455667L, cuentajefe, "tarde", "JEFE8", 6200.00);
    	Empleado jefe9 = new Empleado("Pedro Ramírez", 776655443L, cuentajefe, "noche", "JEFE9", 6300.00);
    	Empleado jefe10 = new Empleado("Sofía Torres", 221133445L, cuentajefe, "mañana", "JEFE10", 6400.00);
    	
    	
    	//Establecimientos
    	//public Establecimiento(String nombre, int capacidad, CuentaBancaria cuentaCorriente, Empleado jefe,double calificacion)
    	Establecimiento local1 = new Establecimiento("local1 productos",500,cuentalocal0,jefe1,5);
    	Establecimiento local2 = new Establecimiento("local2 productos",500,cuentalocal1,jefe2,5);
    	Establecimiento local3 = new Establecimiento("local3 productos",500,cuentalocal2,jefe3,5);
    	Establecimiento local4 = new Establecimiento("local4 productos",500,cuentalocal3,jefe4,5);
    	Establecimiento local5 = new Establecimiento("local5 Vehiculos",500,cuentalocal4,jefe5,5);
    	Establecimiento local6 = new Establecimiento("local6 Vehiculos",500,cuentalocal5,jefe6,5);
    	Establecimiento local7 = new Establecimiento("local7 Vehiculos",500,cuentalocal6,jefe7,5);
    	Establecimiento local8 = new Establecimiento("local8 Empleados",500,cuentalocal7,jefe8,5);
    	Establecimiento local9 = new Establecimiento("local9 Empleados",500,cuentalocal8,jefe9,5);
    	Establecimiento local10 = new Establecimiento("local110 Empleados",500,cuentalocal9,jefe10,5);
    	
    	//Empleados para funeraria
    	// Creación de las cuentas bancarias para los empleados
    	CuentaBancaria cuenta1 = new CuentaBancaria(6060606060L, "Gabriel Soto", 4800.00, "BBVA");
    	CuentaBancaria cuenta2 = new CuentaBancaria(7070707070L, "Lucía Rivas", 5300.00, "BANCOLOMBIA");
    	CuentaBancaria cuenta3 = new CuentaBancaria(8080808080L, "Carlos Méndez", 4600.00, "BANCO DE BOGOTÁ");
    	CuentaBancaria cuenta4 = new CuentaBancaria(9090909090L, "Ana Vega", 5000.00, "BANCO DE OCCIDENTE");
    	CuentaBancaria cuenta5 = new CuentaBancaria(1010101010L, "Felipe Cruz", 5100.00, "DAVIVIENDA");
    	CuentaBancaria cuenta6 = new CuentaBancaria(1111111111L, "Marta Herrera", 5200.00, "BBVA");
    	CuentaBancaria cuenta7 = new CuentaBancaria(1212121212L, "Luis Morales", 4700.00, "BANCOLOMBIA");
    	CuentaBancaria cuenta8 = new CuentaBancaria(1313131313L, "Elena Castillo", 5400.00, "BANCO DE BOGOTÁ");
    	CuentaBancaria cuenta9 = new CuentaBancaria(1414141414L, "Diego Torres", 4800.00, "BANCO DE OCCIDENTE");
    	CuentaBancaria cuenta10 = new CuentaBancaria(1515151515L, "Isabel Sánchez", 5000.00, "DAVIVIENDA");
    	CuentaBancaria cuenta11 = new CuentaBancaria(1616161616L, "Miguel Ortega", 5300.00, "BBVA");
    	CuentaBancaria cuenta12 = new CuentaBancaria(1717171717L, "Claudia Jiménez", 5100.00, "BANCOLOMBIA");
    	CuentaBancaria cuenta13 = new CuentaBancaria(1818181818L, "Javier López", 5200.00, "BANCO DE BOGOTÁ");
    	CuentaBancaria cuenta14 = new CuentaBancaria(1919191919L, "Rosa Pérez", 4700.00, "BANCO DE OCCIDENTE");
    	CuentaBancaria cuenta15 = new CuentaBancaria(2020202020L, "Alberto Ramírez", 5400.00, "DAVIVIENDA");

        // Creación de los empleados funeraria
       
        Empleado empleado1 = new Empleado("Gabriel Soto", 123456789L, cuenta1, "mañana", "sepulturero", 3500.00);
        Empleado empleado2 = new Empleado("Lucía Rivas", 234567891L, cuenta2, "tarde", "cremador", 3700.00);
        Empleado empleado3 = new Empleado("Carlos Méndez", 345678912L, cuenta3, "noche", "padre", 3600.00);
        Empleado empleado4 = new Empleado("Ana Vega", 456789123L, cuenta4, "mañana", "sepulturero", 3400.00);
        Empleado empleado5 = new Empleado("Felipe Cruz", 567891234L, cuenta5, "tarde", "cremador", 3500.00);
        Empleado empleado6 = new Empleado("Marta Herrera", 678912345L, cuenta6, "noche", "padre", 3700.00);
        Empleado empleado7 = new Empleado("Luis Morales", 789123456L, cuenta7, "mañana", "sepulturero", 3600.00);
        Empleado empleado8 = new Empleado("Elena Castillo", 891234567L, cuenta8, "tarde", "cremador", 3800.00);
        Empleado empleado9 = new Empleado("Diego Torres", 912345678L, cuenta9, "noche", "padre", 3500.00);
        Empleado empleado10 = new Empleado("Isabel Sánchez", 123987654L, cuenta10, "mañana", "sepulturero", 3400.00);
        Empleado empleado11 = new Empleado("Miguel Ortega", 234876543L, cuenta11, "tarde", "cremador", 3700.00);
        Empleado empleado12 = new Empleado("Claudia Jiménez", 345765432L, cuenta12, "noche", "padre", 3600.00);
        Empleado empleado13 = new Empleado("Javier López", 456654321L, cuenta13, "mañana", "sepulturero", 3800.00);
        Empleado empleado14 = new Empleado("Rosa Pérez", 567543210L, cuenta14, "tarde", "cremador", 3500.00);
        Empleado empleado15 = new Empleado("Alberto Ramírez", 678432109L, cuenta15, "noche", "padre", 3900.00);
        
        //vehiculos funerarias.
        //public Vehiculo(TipoVehiculo tipoVehiculo,Funeraria funeraria,String color, String placa, int Precio, int Capacidad)
     // Creación de 24 vehículos, 3 por cada tipo
        Vehiculo berlina1 = new Vehiculo(TipoVehiculo.BERLINA, Fun1, "Negro", "ABC123", 70000, 4);
        Vehiculo berlina2 = new Vehiculo(TipoVehiculo.BERLINA, Fun2, "Gris", "DEF456", 72000, 4);
        Vehiculo berlina3 = new Vehiculo(TipoVehiculo.BERLINA, Fun3, "Azul", "GHI789", 71000, 4);

        Vehiculo carroza1 = new Vehiculo(TipoVehiculo.CARROZA, Fun1, "Negro", "JKL012", 150000, 1);
        Vehiculo carroza2 = new Vehiculo(TipoVehiculo.CARROZA,Fun2, "Blanco", "MNO345", 152000, 1);
        Vehiculo carroza3 = new Vehiculo(TipoVehiculo.CARROZA, Fun3, "Plata", "PQR678", 151000, 1);

        Vehiculo faeton1 = new Vehiculo(TipoVehiculo.FAETON, Fun1, "Negro", "STU901", 120000, 4);
        Vehiculo faeton2 = new Vehiculo(TipoVehiculo.FAETON, Fun2, "Blanco", "VWX234", 122000, 4);
        Vehiculo faeton3 = new Vehiculo(TipoVehiculo.FAETON, Fun3, "Rojo", "YZA567", 121000, 4);

        Vehiculo cocheFunebre1 = new Vehiculo(TipoVehiculo.COCHERESPETO, Fun1, "Negro", "BCD890", 75000, 8);
        Vehiculo cocheFunebre2 = new Vehiculo(TipoVehiculo.COCHERESPETO, Fun2, "Blanco", "EFG123", 76000, 8);
        Vehiculo cocheFunebre3 = new Vehiculo(TipoVehiculo.COCHERESPETO, Fun3, "Plata", "HIJ456", 75500, 8);

        Vehiculo bus1 = new Vehiculo(TipoVehiculo.BUS, Fun1, "Amarillo", "KLM789", 50000, 6);
        Vehiculo bus2 = new Vehiculo(TipoVehiculo.BUS, Fun2, "Verde", "NOP012", 51000, 6);
        Vehiculo bus3 = new Vehiculo(TipoVehiculo.BUS, Fun3, "Azul", "QRS345", 50500, 6);

        Vehiculo cocheRespeto1 = new Vehiculo(TipoVehiculo.COCHEFUNEBRE, Fun1, "Negro", "TUV678", 80000, 1);
        Vehiculo cocheRespeto2 = new Vehiculo(TipoVehiculo.COCHEFUNEBRE, Fun2, "Gris", "WXY901", 82000, 1);
        Vehiculo cocheRespeto3 = new Vehiculo(TipoVehiculo.COCHEFUNEBRE, Fun3, "Blanco", "ZAB234", 81000, 1);

        Vehiculo cupe1 = new Vehiculo(TipoVehiculo.CUPE, Fun1, "Rojo", "CDE567", 65000, 2);
        Vehiculo cupe2 = new Vehiculo(TipoVehiculo.CUPE, Fun2, "Negro", "FGH890", 66000, 2);
        Vehiculo cupe3 = new Vehiculo(TipoVehiculo.CUPE, Fun3, "Azul", "IJK123", 65500, 2);

        Vehiculo camion1 = new Vehiculo(TipoVehiculo.CAMION, Fun1, "Blanco", "LMN456", 69000, 5);
        Vehiculo camion2 = new Vehiculo(TipoVehiculo.CAMION, Fun2, "Azul", "OPQ789", 70000, 5);
        Vehiculo camion3 = new Vehiculo(TipoVehiculo.CAMION, Fun3, "Negro", "RST012", 69500, 5);
        
        //productos que tiene la Funeraria 1
        //public Producto(String nombre, double precio, int cantidad, int cantidadVendida)
        Producto trajesCaballeroF1 = new Producto("Trajes de caballero", 998.0, 22, 10);
        Producto vestidosDamaF1 = new Producto("Vestidos de dama", 1200.0, 6, 20);
        Producto recuerdoF1 = new Producto("Medalla conmemorativa", 100.0, 5, 12); // Menos de 10 unidades
        Producto recuerdo2F1 = new Producto("Joyas conmemorativas", 250.0, 0, 15); // Sin stock
        Producto recuerdo3F1 = new Producto("Álbumes de fotos", 300.0, 30, 18); // Más stock
        Producto recuerdo4F1 = new Producto("Portarretratos digitales", 120.0, 0, 45); // Sin stock
        Producto velasRojasF1 = new Producto("Velas rojas", 300.0, 7, 5); // Menos de 10 unidades
        Producto velasBlancasF1 = new Producto("Velas blancas", 300.0, 20, 30); // Más stock
        
      //productos que tiene la Funeraria 2
        //public Producto(String nombre, double precio, int cantidad, int cantidadVendida)
        Producto trajesCaballeroF2 = new Producto("Trajes de caballero", 998.0, 6, 25); // Menos de 10 unidades
        Producto vestidosDamaF2 = new Producto("Vestidos de dama", 1200.0, 18, 9); 
        Producto recuerdoF2 = new Producto("Medalla conmemorativa", 100.0, 10, 20);
        Producto recuerdo2F2 = new Producto("Joyas conmemorativas", 250.0, 25, 10); // Más stock
        Producto recuerdo3F2 = new Producto("Álbumes de fotos", 300.0, 9, 12); // Menos de 10 unidades
        Producto recuerdo4F2 = new Producto("Portarretratos digitales", 120.0, 15, 45);
        Producto velasRojasF2 = new Producto("Velas rojas", 300.0, 7, 20); // Menos de 10 unidades
        Producto velasBlancasF2 = new Producto("Velas blancas", 300.0, 0, 40); // Sin stock
        
      //productos que tiene la Funeraria 3
        //public Producto(String nombre, double precio, int cantidad, int cantidadVendida)
        Producto trajesCaballeroF3 = new Producto("Trajes de caballero", 998.0, 6, 30); // Menos de 10 unidades
        Producto vestidosDamaF3 = new Producto("Vestidos de dama", 1200.0, 8, 12); // Menos de 10 unidades
        Producto recuerdoF3 = new Producto("Medalla conmemorativa", 100.0, 10, 25);
        Producto recuerdo2F3 = new Producto("Joyas conmemorativas", 250.0, 0, 18); // Sin stock
        Producto recuerdo3F3 = new Producto("Álbumes de fotos", 300.0, 30, 15);
        Producto recuerdo4F3 = new Producto("Portarretratos digitales", 120.0, 35, 35); // Más stock
        Producto velasRojasF3 = new Producto("Velas rojas", 300.0, 7, 10); // Menos de 10 unidades
        Producto velasBlancasF3 = new Producto("Velas blancas", 300.0, 0, 50); // Sin stock
        
        //Facturas para funeraria 1
        Factura facturaF1_1 = new Factura("Trajes de caballero");
        Factura facturaF1_2 = new Factura("Vestidos de dama");
        Factura facturaF1_3 = new Factura("Medalla conmemorativa");
        Factura facturaF1_4 = new Factura("Joyas conmemorativas");
        Factura facturaF1_5 = new Factura("Álbumes de fotos");
        Factura facturaF1_6 = new Factura("Portarretratos digitales");
        Factura facturaF1_7 = new Factura("Velas rojas");
        Factura facturaF1_8 = new Factura("Velas blancas");

        
        // facturas para funeraria 2
        Factura facturaF2_1 = new Factura("Trajes de caballero");
        Factura facturaF2_2 = new Factura("Vestidos de dama");
        Factura facturaF2_3 = new Factura("Medalla conmemorativa");
        Factura facturaF2_4 = new Factura("Joyas conmemorativas");
        Factura facturaF2_5 = new Factura("Álbumes de fotos");
        Factura facturaF2_6 = new Factura("Portarretratos digitales");
        Factura facturaF2_7 = new Factura("Velas rojas");
        Factura facturaF2_8 = new Factura("Velas blancas");

        
        // facturas para funerarias 3
        Factura facturaF3_1 = new Factura("Trajes de caballero");
        Factura facturaF3_2 = new Factura("Vestidos de dama");
        Factura facturaF3_3 = new Factura("Medalla conmemorativa");
        Factura facturaF3_4 = new Factura("Joyas conmemorativas");
        Factura facturaF3_5 = new Factura("Álbumes de fotos");
        Factura facturaF3_6 = new Factura("Portarretratos digitales");
        Factura facturaF3_7 = new Factura("Velas rojas");
        Factura facturaF3_8 = new Factura("Velas blancas");

 
        
        // productos para las facturas de la funeraria 1
      
        
        Producto trajesCaballeroVendidosF1 = new Producto("Trajes de caballero", 998.0, 10, 10);
        Producto vestidosDamaVendidosF1 = new Producto("Vestidos de dama", 1200.0, 20, 20);
        Producto recuerdoVendidosF1 = new Producto("Medalla conmemorativa", 100.0, 12, 12); 
        Producto recuerdo2VendidosF1 = new Producto("Joyas conmemorativas", 250.0, 15, 15); 
        Producto recuerdo3VendidosF1 = new Producto("Álbumes de fotos", 300.0, 18, 18); 
        Producto recuerdo4VendidosF1 = new Producto("Portarretratos digitales", 120.0, 45, 45); 
        Producto velasRojasVendidosF1 = new Producto("Velas rojas", 300.0, 5, 5); 
        Producto velasBlancasVendidosF1 = new Producto("Velas blancas", 300.0, 30, 30); 
    
        // productos para las facturas de la funeraria 2
        Producto trajesCaballeroVendidosF2 = new Producto("Trajes de caballero", 998.0, 25, 25); 
        Producto vestidosDamaVendidosF2 = new Producto("Vestidos de dama", 1200.0, 9, 9); 
        Producto recuerdoVendidosF2 = new Producto("Medalla conmemorativa", 100.0, 20, 20);
        Producto recuerdo2VendidosF2 = new Producto("Joyas conmemorativas", 250.0, 10, 10); 
        Producto recuerdo3VendidosF2 = new Producto("Álbumes de fotos", 300.0, 12, 12); 
        Producto recuerdo4VendidosF2 = new Producto("Portarretratos digitales", 120.0, 45, 45);
        Producto velasRojasVendidosF2 = new Producto("Velas rojas", 300.0, 20, 20); 
        Producto velasBlancasVendidosF2 = new Producto("Velas blancas", 300.0, 40, 40); 
        
        // productos para las facturas de la funeraria 3

        Producto trajesCaballeroVendidosF3 = new Producto("Trajes de caballero", 998.0, 30, 30); 
        Producto vestidosDamaVendidosF3 = new Producto("Vestidos de dama", 1200.0, 12, 12); 
        Producto recuerdoVendidosF3 = new Producto("Medalla conmemorativa", 100.0, 25, 25);
        Producto recuerdo2VendidosF3 = new Producto("Joyas conmemorativas", 250.0, 18, 18);
        Producto recuerdo4VendidosF3 = new Producto("Portarretratos digitales", 120.0, 35, 35); 
        Producto velasRojasVendidosF3 = new Producto("Velas rojas", 300.0, 10, 10); 
        Producto velasBlancasVendidosF3 = new Producto("Velas blancas", 300.0, 50, 50); 
        
        
        
    	
    	
    	
    	
    	

        return new Funeraria[]{funeraria1};
    }
    
}
