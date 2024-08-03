package uiMain;

import java.util.ArrayList;
import java.util.Scanner;

import gestorAplicacion.establecimientos.*;
import gestorAplicacion.financiero.*;
import gestorAplicacion.inventario.*;
import gestorAplicacion.personas.*;

public class FuncionalidadGestionInventario {
	
	public static void imprimirInformacion(Funeraria[] funerarias) {
	    for (Funeraria fun : funerarias) {
	        System.out.println("Nombre: " + fun.getNombre());
	        System.out.println("Calificación: " + fun.getCalificacion());
	        System.out.println("Cantidad de empleados: " + fun.getEmpleados());

	        Producto[] productosVendidos = new Producto[0];
	        String[] serviciosUsados = new String[0];
	        int[] serviciosContador = new int[0];

	        for (Factura factura : fun.getListadoFacturas()) {
	            for (Producto producto : factura.getListaProductos()) {
	                productosVendidos = Funeraria.agregarProducto(productosVendidos, producto);
	                String servicio = factura.getServicio();
	                serviciosUsados = agregarServicio(serviciosUsados, serviciosContador, servicio);
	            }
	            
	        }

	        System.out.println("Productos más vendidos:");
	        for (Producto producto : productosVendidos) {
	            System.out.println("- " + producto.getNombre() + ": " + producto.getCantidadVendida());
	        }

	        System.out.println("Servicios más usados:");
	        for (int i = 0; i < serviciosUsados.length; i++) {
	            System.out.println("- " + serviciosUsados[i] + ": " + serviciosContador[i]);
	        }

	        System.out.println("------------------------");
	    }

	    analizarIntercambios(funerarias);
	}
	
	private static String[] agregarServicio(String[] serviciosUsados, int[] serviciosContador, String nuevoServicio) {
	    for (int i = 0; i < serviciosUsados.length; i++) {
	        if (serviciosUsados[i].equals(nuevoServicio)) {
	            serviciosContador[i]++;
	            return serviciosUsados;
	        }
	    }
	    String[] nuevosServiciosUsados = new String[serviciosUsados.length + 1];
	    int[] nuevosServiciosContador = new int[serviciosContador.length + 1];
	    System.arraycopy(serviciosUsados, 0, nuevosServiciosUsados, 0, serviciosUsados.length);
	    System.arraycopy(serviciosContador, 0, nuevosServiciosContador, 0, serviciosContador.length);
	    nuevosServiciosUsados[serviciosUsados.length] = nuevoServicio;
	    nuevosServiciosContador[serviciosContador.length] = 1;
	    return nuevosServiciosUsados;
	}
	
	private static void analizarIntercambios(Funeraria[] funerarias) {
	    for (int i = 0; i < funerarias.length; i++) {
	        Funeraria funA = funerarias[i];
	        Producto[] productosVendidosA = Funeraria.calcularProductosVendidos(funA);
	        
	        for (int j = i + 1; j < funerarias.length; j++) {
	            Funeraria funB = funerarias[j];
	            Producto[] productosVendidosB = Funeraria.calcularProductosVendidos(funB);

	            // Analizar intercambio de productos
	            for (Producto productoA : productosVendidosA) {
	                if (productoA.getCantidadVendida() < 10) {
	                    continue;
	                }
	                for (Producto productoB : productosVendidosB) {
	                    if (productoB.getCantidadVendida() < 10) {
	                        continue;
	                    }
	                    if (!productoA.getNombre().equals(productoB.getNombre()) && productoA.getCantidadVendida() > 10 && productoB.getCantidadVendida() > 10) {
	                        System.out.println("Intercambio posible entre " + funA.getNombre() + " y " + funB.getNombre());
	                        System.out.println("- " + funA.getNombre() + " puede intercambiar " + productoA.getNombre() + " con " + funB.getNombre());
	                        System.out.println("- " + funB.getNombre() + " puede intercambiar " + productoB.getNombre() + " con " + funA.getNombre());
	                    }
	                }
	            }

	            // Analizar intercambio de empleados
	         // Analizar intercambio de empleados
	            if (funA.getListadoFacturas().size() > funB.getListadoFacturas().size()) {
	                System.out.println(funA.getNombre() + " puede solicitar empleados de " + funB.getNombre());
	                for (Empleado empleado : funB.getEmpleados()) {
	                    System.out.println("- " + empleado.getNombre() + " (" + empleado.getCargo() + ")");
	                }
	            } else if (funB.getListadoFacturas().size() > funA.getListadoFacturas().size()) {
	                System.out.println(funB.getNombre() + " puede solicitar empleados de " + funA.getNombre());
	                for (Empleado empleado : funA.getEmpleados()) {
	                    System.out.println("- " + empleado.getNombre() + " (" + empleado.getCargo() + ")");
	                }
	            }
	        }
	    }
	}
	

	
	public static void realizarIntercambioYCompras(Funeraria[] funerarias) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione una funeraria:");
        for (int i = 0; i < funerarias.length; i++) {
            System.out.println((i + 1) + ". " + funerarias[i].getNombre());
        }

        int seleccion = scanner.nextInt() - 1;
        if (seleccion < 0 || seleccion >= funerarias.length) {
            System.out.println("Selección inválida");
            return;
        }

        Funeraria seleccionada = funerarias[seleccion];
        System.out.println("Funeraria seleccionada: " + seleccionada.getNombre());

        Producto[] productosFaltantes = Funeraria.identificarProductosFaltantes(seleccionada);
        System.out.println("Productos con menos de 10 existencias:");
        for (Producto producto : productosFaltantes) {
            System.out.println("- " + producto.getNombre() + ": " + producto.getCantidad());
        }

        System.out.println("Establecimientos que pueden vender los productos faltantes:");
        for (Establecimiento est : seleccionada.getListadoProveedores()) {
            for (Producto producto : productosFaltantes) {
                if (est.tieneProducto(producto.getNombre())) {
                    System.out.println("Establecimiento: " + est.getNombre() + ", Calificación: " + est.getCalificacion());
                    System.out.println("  Producto: " + producto.getNombre());
                }
            }
        }

        System.out.println("Empleados disponibles para contratación:");
        for (Establecimiento est : seleccionada.getListadoProveedores()) {
            for (Empleado empleado : est.getEmpleados()) {
                System.out.println("Establecimiento: " + est.getNombre() + ", Calificación: " + est.getCalificacion());
                System.out.println("  Empleado: " + empleado.getNombre() + ", Cargo: " + empleado.getCargo() + ", Experiencia: " + empleado.getExperiencia() + " años, Edad: " + empleado.getEdad());
            }
        }
    }
		
	
}
