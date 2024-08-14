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
        for (Establecimiento est : funeraria.getListadoProveedores()) {
            for (Producto producto : productosFaltantes) {
                if (est.tieneProducto(producto.getNombre())) {
                    System.out.println("Establecimiento: " + est.getNombre() + ", Calificación: " + est.getCalificacion());
                    System.out.println("  Producto: " + producto.getNombre());
                }
            }
        }

        // Seleccionar establecimiento y realizar compra
        System.out.println("Seleccione el establecimiento para realizar la compra:");
        int seleccion = scanner.nextInt();
        // Aquí se realizaría la compra y la generación de la factura
    }

    private static void contratarEmpleados(Funeraria funeraria, Scanner scanner) {
        System.out.println("Empleados disponibles para contratación:");
        for (Establecimiento est : funeraria.getListadoProveedores()) {
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
        for (Establecimiento est : funeraria.getListadoProveedores()) {
            for (Vehiculo vehiculo : est.getVehiculosEnVenta()) { // Supongamos que hay un método para obtener vehículos en venta
                System.out.println("Establecimiento: " + est.getNombre() + ", Calificación: " + est.getCalificacion());
                System.out.println("  Vehículo: " + vehiculo.getTipoVehiculo() + ", Capacidad: " + vehiculo.getCapacidad() + ", Precio: " + vehiculo.getPrecio());
                
                // Representación visual del vehículo
                System.out.println("   ______");
                System.out.println("  /|_||_\\`.__");
                System.out.println(" (   _    _ _\\");
                System.out.println(" =`-(_)--(_)-'");
                
                System.out.println("¿Desea comprar este vehículo? (S/N)");
                String respuesta = scanner.next();
                if (respuesta.equalsIgnoreCase("S")) {
                    // Aquí se realizaría la compra y la actualización de la lista de vehículos de la funeraria
                    funeraria.agregarVehiculo(vehiculo); // Método para agregar el vehículo a la funeraria
                    est.removerVehiculoEnVenta(vehiculo); // Método para remover el vehículo del establecimiento
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
        // Inicializa las funerarias y retorna el arreglo
        return new Funeraria[]{ };
    }
    
}
