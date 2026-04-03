package Taller;

import java.util.Scanner;
import java.io.*;

public class taller {

    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);
        boolean sistemaActivo = true; 

        while (sistemaActivo) {
            System.out.println("\n===========================");
            System.out.print("1) Menu de Usuarios\n2) Menu de Analisis\n3) Salir\n> ");
            int opcion1 = teclado.nextInt();
            teclado.nextLine();

            switch (opcion1) {
                case 1:
                    boolean encontrado = false;
                    String userIngresado = ""; 

                    while (!encontrado) {
                        System.out.print("Ingresa tu usuario: ");
                        userIngresado = teclado.nextLine();
                        System.out.print("Ingresa tu contraseña: ");
                        String passIngresada = teclado.nextLine();

                        File usuarios = new File("Usuarios.txt");
                        Scanner lector = new Scanner(usuarios);

                        while (lector.hasNextLine()) {
                        	String linea = lector.nextLine();
                            String[] datos = linea.split(";");

                            if (datos[0].equals(userIngresado) && datos[1].equals(passIngresada)) {
                            	encontrado = true;
                                }
                            }
                            lector.close();

                        if (encontrado) {
                            System.out.println("¡Acceso correcto! Bienvenid@ " + userIngresado);

                            boolean volverAlMenuSecundario = false;

                            
                            while (!volverAlMenuSecundario) {
                                System.out.println("Que deseas realizar?");
                                System.out.println("1) Registrar actividad.");
                                System.out.println("2) Modificar actividad.");
                                System.out.println("3) Eliminar actividad.");
                                System.out.println("4) Cambiar contraseña.");
                                System.out.println("5) Salir.");
                                System.out.print("> ");

                                int opcion2 = teclado.nextInt();
                                teclado.nextLine();

                                switch (opcion2) {
                                    case 1: // REGISTRAR ACTIVIDAD
                                        System.out.println("\n--- Formulario de Registro de Actividad ---");
                                        
                                        File archLectura = new File("Registros.txt");
                                        int lineasActuales = 0;
                                        if (archLectura.exists()) {
                                            Scanner contadorScanner = new Scanner(archLectura);
                                            while (contadorScanner.hasNextLine()) {
                                                contadorScanner.nextLine();
                                                lineasActuales++;
                                            }
                                            contadorScanner.close();
                                        }

                                        if (lineasActuales >= 300) {
                                            System.out.println("Error: Se ha alcanzado el límite de 300 registros.");
                                        } else {
                                            System.out.print("Ingrese la fecha día/mes/año: ");
                                            String fecha = teclado.nextLine();
                                            System.out.print("Ingrese la cantidad de horas invertidas: ");
                                            String horas = teclado.nextLine();
                                            System.out.print("Ingrese la actividad realizada: ");
                                            String actividad = teclado.nextLine();

                                            FileWriter fw = new FileWriter("Registros.txt", true);
                                            PrintWriter escritor = new PrintWriter(fw);
                                            escritor.println(userIngresado + ";" + fecha + ";" + horas + ";" + actividad);
                                            escritor.close();
                                            
                                            System.out.println("Actividad registrada correctamente.");
                                        }
                                        break;

                                    case 2: // MODIFICAR ACTIVIDAD
                                        System.out.println("\nCual actividad deseas modificar?\n");
                                        System.out.println("0) Regresar.");

                                        String[] registros = new String[300];
                                        int[] posicionesReales = new int[300];
                                        int totalLineas = 0;
                                        int contadorUsuario = 0;

                                        File archModificar = new File("Registros.txt");
                                        if (archModificar.exists()) {
                                            Scanner lectorRegistros = new Scanner(archModificar);

                                            while (lectorRegistros.hasNextLine() && totalLineas < 300) {
                                                String linea = lectorRegistros.nextLine();
                                                registros[totalLineas] = linea;

                                                String[] partes = linea.split(";");

                                                if (partes[0].equals(userIngresado)) {
                                                    contadorUsuario++;
                                                    posicionesReales[contadorUsuario] = totalLineas;
                                                    System.out.println(contadorUsuario + ") " + linea);
                                                }
                                                totalLineas++;
                                            }
                                            lectorRegistros.close();
                                        }

                                        System.out.print("> ");
                                        int seleccionActividad = teclado.nextInt();
                                        teclado.nextLine();

                                        if (seleccionActividad == 0) {
                                            System.out.println("Regresando...");
                                        } else if (seleccionActividad > 0 && seleccionActividad <= contadorUsuario) {
                                            System.out.println("\nQue deseas modificar?\n");
                                            System.out.println("0) Regresar.");
                                            System.out.println("1) Fecha");
                                            System.out.println("2) Duracion");
                                            System.out.println("3) Tipo de actividad");
                                            System.out.print("> ");

                                            int seleccionColumna = teclado.nextInt();
                                            teclado.nextLine();

                                            if (seleccionColumna == 0) {
                                                System.out.println("Regresando...");
                                            } else if (seleccionColumna >= 1 && seleccionColumna <= 3) {
                                                System.out.print("Ingrese nuevo valor: ");
                                                String valorNuevo = teclado.nextLine();

                                                int lineaExacta = posicionesReales[seleccionActividad];
                                                String[] datosActividad = registros[lineaExacta].split(";");

                                                datosActividad[seleccionColumna] = valorNuevo;
                                                registros[lineaExacta] = datosActividad[0] + ";" + datosActividad[1] + ";" + datosActividad[2] + ";" + datosActividad[3];

                                                FileWriter fwMod = new FileWriter("Registros.txt", false);
                                                PrintWriter escritorMod = new PrintWriter(fwMod);
                                                for (int i = 0; i < totalLineas; i++) {
                                                    escritorMod.println(registros[i]);
                                                }
                                                escritorMod.close();

                                                System.out.println("\nActividad modificada con exito!\n");
                                            } else {
                                                System.out.println("Opcion invalida.");
                                            }
                                        } else {
                                            System.out.println("Opcion invalida.");
                                        }
                                        break;

                                    case 3: // ELIMINAR ACTIVIDAD
                                        System.out.println("\nCual actividad deseas eliminar?\n");
                                        System.out.println("0) Regresar.");

                                        String[] registrosBorrar = new String[300];
                                        int[] posicionesRealesBorrar = new int[300];
                                        int totalLineasBorrar = 0;
                                        int contadorUsuarioBorrar = 0;

                                        File archBorrar = new File("Registros.txt");
                                        if (archBorrar.exists()) {
                                            Scanner lectorBorrar = new Scanner(archBorrar);

                                            while (lectorBorrar.hasNextLine() && totalLineasBorrar < 300) {
                                                String linea = lectorBorrar.nextLine();
                                                registrosBorrar[totalLineasBorrar] = linea;

                                                String[] partes = linea.split(";");

                                                if (partes[0].equals(userIngresado)) {
                                                    contadorUsuarioBorrar++;
                                                    posicionesRealesBorrar[contadorUsuarioBorrar] = totalLineasBorrar;
                                                    System.out.println(contadorUsuarioBorrar + ") " + linea);
                                                }
                                                totalLineasBorrar++;
                                            }
                                            lectorBorrar.close();
                                        }

                                        System.out.print("> ");
                                        int seleccionBorrar = teclado.nextInt();
                                        teclado.nextLine();

                                        if (seleccionBorrar == 0) {
                                            System.out.println("Regresando...");
                                        } else if (seleccionBorrar > 0 && seleccionBorrar <= contadorUsuarioBorrar) {
                                            int lineaExacta = posicionesRealesBorrar[seleccionBorrar];

                                            FileWriter fwBorrar = new FileWriter("Registros.txt", false);
                                            PrintWriter escritorBorrar = new PrintWriter(fwBorrar);

                                            for (int i = 0; i < totalLineasBorrar; i++) {
                                                if (i != lineaExacta) {
                                                    escritorBorrar.println(registrosBorrar[i]);
                                                }
                                            }
                                            escritorBorrar.close();

                                            System.out.println("\nActividad eliminada con exito!\n");
                                        } else {
                                            System.out.println("Opcion invalida.");
                                        }
                                        break;

                                    case 4: // CAMBIAR CONTRASEÑA
                                        System.out.println("\n--- Cambiar Contraseña ---");
                                        System.out.println("0) Regresar.");
                                        System.out.print("Ingresa tu nueva contraseña: ");

                                        String nuevaPass = teclado.nextLine();

                                        if (nuevaPass.equals("0")) {
                                            System.out.println("Operación cancelada. Regresando...");
                                        } else {
                                            String[] listaUsuarios = new String[100];
                                            int totalUsuarios = 0;

                                            File archivoU = new File("Usuarios.txt");
                                            if (archivoU.exists()) {
                                                Scanner lectorU = new Scanner(archivoU);

                                                while (lectorU.hasNextLine() && totalUsuarios < 100) {
                                                    String lineaU = lectorU.nextLine();
                                                    String[] datosU = lineaU.split(";");

                                                    if (datosU[0].equals(userIngresado)) {
                                                        listaUsuarios[totalUsuarios] = datosU[0] + ";" + nuevaPass;
                                                    } else {
                                                        listaUsuarios[totalUsuarios] = lineaU;
                                                    }
                                                    totalUsuarios++;
                                                }
                                                lectorU.close();

                                                FileWriter fwU = new FileWriter("Usuarios.txt", false);
                                                PrintWriter escritorU = new PrintWriter(fwU);

                                                for (int i = 0; i < totalUsuarios; i++) {
                                                    escritorU.println(listaUsuarios[i]);
                                                }
                                                escritorU.close();

                                                System.out.println("\n¡Contraseña actualizada con éxito!\n");
                                            }
                                        }
                                        break;

                                    case 5: // SALIR DEL MENÚ USUARIO
                                        System.out.println("\nCerrando sesión de " + userIngresado + "...\n");
                                        volverAlMenuSecundario = true;
                                        encontrado = true;
                                        break;

                                    default:
                                        System.out.println("❌ Opción inválida.");
                                        break;
                                }
                            }
                        } else {
                            System.out.println("\nAcceso incorrecto. Intente nuevamente.\n");
                        }
                    }
                    break;

                case 2: // MENÚ DE ANÁLISIS
                    System.out.println("Bienvenido al menu de analisis!");
                    boolean salirAnalisis = false;
                    while (!salirAnalisis) {
	                    System.out.println("1) Actividad más realizada");
	                    System.out.println("2) Actividad más realizada por cada usuario");
	                    System.out.println("3) Usuario con mayor procastinacion");
	                    System.out.println("4) Ver todas las actividades");
	                    System.out.println("5) Salir");
	                    System.out.print("> ");
	                    int opcionAnalisis = teclado.nextInt();
                        teclado.nextLine(); // ¡Nuestra aspiradora de enters fantasma!
                        switch (opcionAnalisis) {
                        case 1:
                        	System.out.println("\n--- 1) Actividad más realizada ---");
                            
                            try {
                                File archRegistros = new File("Registros.txt");
                                Scanner lectorAnalisis = new Scanner(archRegistros);
                                String[] listaActividades = new String[300]; 
                                int totalRegistros = 0;

                                while (lectorAnalisis.hasNextLine() && totalRegistros < 300) {
                                    String linea = lectorAnalisis.nextLine();
                                    String[] partes = linea.split(";");
                                    listaActividades[totalRegistros] = partes[3]; 
                                    totalRegistros++;
                                }
                                lectorAnalisis.close();

                                if (totalRegistros == 0) {
                                    System.out.println("No hay datos para analizar.");
                                } else {
                                    int maxFrecuencia = 0;
                                    String actividadMasFrecuente = "";

                                    for (int i = 0; i < totalRegistros; i++) {
                                        int contadorActual = 0;
                                        
                                        for (int j = 0; j < totalRegistros; j++) {
                                            if (listaActividades[i].equalsIgnoreCase(listaActividades[j])) {
                                                contadorActual++;
                                            }
                                        }

                                        if (contadorActual > maxFrecuencia) {
                                            maxFrecuencia = contadorActual;
                                            actividadMasFrecuente = listaActividades[i];
                                        }
                                    }

                                    System.out.println("La actividad más realizada en es: *" + actividadMasFrecuente + "*");
                                    System.out.println("Se registró un total de " + maxFrecuencia + " veces.");
                                }
                                
                            } catch (IOException e) {
                                System.out.println("Error: No se encontró el archivo Registros.txt o no se pudo leer.");
                            }
                            break;
                            
                        case 2:
                        	System.out.println("\n--- 2) Actividad más realizada por cada usuario ---");
                            
                            try {
                                String[] listaNombres = new String[300];
                                int totalUsuarios = 0;
                                Scanner lectorUsuarios = new Scanner(new File("Usuarios.txt"));
                                while (lectorUsuarios.hasNextLine() && totalUsuarios < 300) {
                                    String[] datosU = lectorUsuarios.nextLine().split(";");
                                    listaNombres[totalUsuarios] = datosU[0]; 
                                    totalUsuarios++;
                                }
                                lectorUsuarios.close();

                                String[] regUsuarios = new String[300];
                                int[] regHoras = new int[300];
                                String[] regActividades = new String[300];
                                int totalRegistros = 0;

                                Scanner lectorRegistrosAnalisis = new Scanner(new File("Registros.txt"));
                                while (lectorRegistrosAnalisis.hasNextLine() && totalRegistros < 300) {
                                    String[] partes = lectorRegistrosAnalisis.nextLine().split(";");
                                    regUsuarios[totalRegistros] = partes[0];
                                    regHoras[totalRegistros] = Integer.parseInt(partes[2]); 
                                    regActividades[totalRegistros] = partes[3];
                                    totalRegistros++;
                                }
                                lectorRegistrosAnalisis.close();

                                System.out.println("\nActividades mas realizadas por cada usuario:\n");
                                
                                for (int u = 0; u < totalUsuarios; u++) {
                                    String usuarioActual = listaNombres[u];
                                    
                                    String actividadTop = "Ninguna";
                                    int maxFrecuencia = 0;
                                    int horasDelTop = 0;
                                    for (int i = 0; i < totalRegistros; i++) {
                                        if (regUsuarios[i].equals(usuarioActual)) {
                                            String actividadEvaluada = regActividades[i];
                                            int frecuenciaActual = 0;
                                            int sumaHorasActual = 0;

                                            for (int j = 0; j < totalRegistros; j++) {
                                                if (regUsuarios[j].equals(usuarioActual) && regActividades[j].equalsIgnoreCase(actividadEvaluada)) {
                                                    frecuenciaActual++;
                                                    sumaHorasActual += regHoras[j];
                                                }
                                            }

                                            if (frecuenciaActual > maxFrecuencia) {
                                                maxFrecuencia = frecuenciaActual;
                                                actividadTop = actividadEvaluada;
                                                horasDelTop = sumaHorasActual;
                                            }
                                        }
                                    }
                                    System.out.println("* " + usuarioActual + " -> " + actividadTop + " -> con " + horasDelTop + " horas registradas");
                                }

                            } catch (Exception e) {
                                System.out.println("Error: Ocurrió un problema leyendo los archivos de datos.");
                            }
                            break;
                        case 3:
                        	System.out.println("\n--- 3) Usuario con mayor procastinacion ---");
                            
                            try {
                                String[] listaUsuarios = new String[300];
                                int[] horasTotales = new int[300];
                                int cantUsuarios = 0;
                                
                                Scanner lectorU = new Scanner(new File("Usuarios.txt"));
                                while (lectorU.hasNextLine() && cantUsuarios < 300) {
                                    String[] datosU = lectorU.nextLine().split(";");
                                    listaUsuarios[cantUsuarios] = datosU[0];
                                    horasTotales[cantUsuarios] = 0;
                                    cantUsuarios++;
                                }
                                lectorU.close();
                                
                                Scanner lectorR = new Scanner(new File("Registros.txt"));
                                while (lectorR.hasNextLine()) {
                                    String[] partes = lectorR.nextLine().split(";");
                                    String usuarioActividad = partes[0];
                                    int horasInvertidas = Integer.parseInt(partes[2]);
                                    for (int i = 0; i < cantUsuarios; i++) {
                                        if (listaUsuarios[i].equals(usuarioActividad)) {
                                            horasTotales[i] += horasInvertidas;
                                            break;
                                        }
                                    }
                                }
                                lectorR.close();
                                
                                int maxHoras = -1;
                                String usuarioProcrastinador = "";
                                
                                for (int i = 0; i < cantUsuarios; i++) {
                                    if (horasTotales[i] > maxHoras) {
                                        maxHoras = horasTotales[i];
                                        usuarioProcrastinador = listaUsuarios[i];
                                    }
                                }
                                
                                if (maxHoras > 0) {
                                    System.out.println("\n El usuario con mayor procrastinación es: " + usuarioProcrastinador);
                                    System.out.println("Con un total de " + maxHoras + " procrsatinadas.");
                                } else {
                                    System.out.println("No hay registros suficientes para calcular.");
                                }
                                
                            } catch (Exception e) {
                                System.out.println("Error: Hubo un problema al procesar los archivos de datos.");
                            }
                            break;
                            
                        case 4:
                        	System.out.println("\n--- 4) Ver todas las actividades ---");
                            
                            try {
                                File archivoLectura = new File("Registros.txt");
                                
                                if (!archivoLectura.exists()) {
                                    System.out.println("Aún no hay actividades registradas en el sistema.");
                                } else {
                                    Scanner lectorVisualizar = new Scanner(archivoLectura);
                                    String[] actividadesUnicas = new String[300]; 
                                    int totalUnicas = 0;
                                    
                                    while (lectorVisualizar.hasNextLine()) {
                                        String linea = lectorVisualizar.nextLine();
                                        String[] partes = linea.split(";");
                                        
                                        if (partes.length >= 4) {
                                            String actividadActual = partes[3];
                                            boolean yaExiste = false;
                                            
                                            for (int i = 0; i < totalUnicas; i++) {
                                                if (actividadesUnicas[i].equalsIgnoreCase(actividadActual)) {
                                                    yaExiste = true;
                                                    break;
                                                }
                                            }
                                            if (!yaExiste) {
                                                actividadesUnicas[totalUnicas] = actividadActual;
                                                totalUnicas++;
                                            }
                                        }
                                    }
                                    lectorVisualizar.close();
                                    System.out.println("Catálogo de tipos de actividades registradas:\n");
                                    for (int i = 0; i < totalUnicas; i++) {
                                        System.out.println("* " + actividadesUnicas[i]);
                                    }
                                    System.out.println("\nTotal de actividades diferentes: " + totalUnicas);
                                }
                                
                            } catch (Exception e) {
                                System.out.println("Error: No se pudo leer el archivo de registros.");
                            }
                            break;
                            
                        case 5:
                            salirAnalisis = true; 
                            break;
                            
                        default:
                            System.out.println("Opción inválida. Intente nuevamente.");
                            break;
                    }
                }
                break;

                case 3: // SALIR DEL PROGRAMA
                    System.out.println("\nSaliendo del sistema. ¡Hasta pronto!");
                    sistemaActivo = false;
                    break;

                default:
                    System.out.println("Opción inválida. Seleccione 1, 2 o 3.");
                    break;
            }
        }
        teclado.close();
    }
}