package domain.entities;

import java.util.Date;
import java.util.Scanner;
import managers.AssistantsManager;
import managers.CampsManager;
import managers.InscriptionManager;

public class Main {
	public static void main(String[] args) {
		int opcion;
        Scanner sc = new Scanner(System.in);
		do {
            System.out.println("1. Gestor de asistentes\n");
            System.out.println("2. Gestor de campamentos\n");
            System.out.println("3. Gestor de inscripciones\n");
            System.out.println("4. Volver\n");

            opcion = sc.nextInt();

            switch (opcion) {
				//AssistantManager
                case 1:
					int optionAssistantsManager;
					Scanner scGesstorAsistentes = new Scanner(System.in);
		
					do {
						System.out.println("1. Dar de alta un nuevo asistente\n");
						System.out.println("2. Modificar asistente\n");
						System.out.println("3. Listar asistentes\n");
						System.out.println("4. Volver\n");
						optionAssistantsManager = scGesstorAsistentes.nextInt();
						
						switch (optionAssistantsManager) {
							case 1:
							System.out.println("Introduzca su DNI (sin letra)\n");
							Scanner scAssistantId = new Scanner(System.in);
							if(AssistantManager.isRegistered(scAssistantId )== true ){
								System.out.println("Este DNI ya ha sido registrado en nuestro sistema\n");
							}
							
							System.out.println("Introduzca el nombre del asisitente\n");
							Scanner scAssistantFirstName = new Scanner(System.in);
							
							System.out.println("Introduzca el apellido del asisitente\n");
							Scanner scAssistantLastName = new Scanner(System.in);

							System.out.println("Introduzca la fecha de nacimiento del asisitente en formato (dd/mm/yyyy)\n");
							Scanner scAsssitantBirthDate = new Scanner(System.in);
							
							System.out.println("Introduzca si el asistente necesita atención especial (true/false)\n");
							Scanner scAsssitantRequireSpecialAttention = new Scanner(System.in);
							break;

							case 2:
							System.out.println("Introduzca el asistente: \n");
							break;
					
							case 3:
							break;

							case 4:
							break;
							
							default:

							System.out.println("Opción no válida\n");
						}
					} while (optionAssistantsManager != 4);
					
                    break;
				//CampManager
                case 2:
					int optionCampManager;
					Scanner scCampManager = new Scanner(System.in);
		
					do {
						System.out.println("1. Dar de alta un nuevo campamento\n");
						System.out.println("2. Registrar actividad en el campamento\n");
						System.out.println("3. Registrar un monitor principal en el campamento\n");
						System.out.println("4. Registrar un monitor secundario en el campamento\n");
						System.out.println("5. Asociar un monitor a una actividad\n");
						System.out.println("6. Volver\n");
						optionCampManager = scCampManager.nextInt();
						
						switch (optionCampManager) {
							case 1:
								System.out.println("Introduzca la fecha de inicio del campamento en formato (dd/mm/yyyy)\n");
								Scanner scCampStartDate = new Scanner(System.in);
								
								System.out.println("Introduzca la fecha de fin del campamento en el mismo formato que la de inicio\n");
								Scanner scCampEndDate = new  Scanner(System.in);
								
								System.out.println("Introduzca el nivel educativo del campamento\n");
								Scanner scCampEducativeLevel = new Scanner(System.in);
								
								System.out.println("Introduzca la capacidad del campamento\n");
								Scanner scCampCapacitiy = new Scanner(System.in);								
							break;

							case 2:
								System.out.println("Introduzca el nombre de la nueva actividad \n");
								Scanner scActivityName = new Scanner(System.in);
								
								System.out.println("Introduzca el nivel educativo: \n");
								Scanner scActivityLevel = new Scanner(System.in);

								System.out.println("Introduzca el horario: \n");
								Scanner scActivitySchedule = new Scanner(System.in);

								System.out.println("Introduzca la capacidad máxima: \n");
								Scanner scActivityCapacity = new Scanner(System.in);

								System.out.println("Introduzca los monitores necesarios: \n");
								Scanner scActivityMonitors = new Scanner(System.in);

							break;

							case 3:
								System.out.println("Introduzca la actividad: \n");
								// Listar actividades
								// Seleccionar
								System.out.println("Seleccione un monitor: \n");
								// Listar monitores
								// Seleccionar
							break;

							case 4:
								System.out.println("Introduzca su id (dni sin letra): \n");
								Scanner scActivitySecondaryMonitorId = new Scanner(System.in);

								System.out.println("Introduzca el nombre : \n");
								Scanner scActivitySecondaryMonitorName = new Scanner(System.in);
								

								System.out.println("Introduzca el apellido: \n");
								Scanner scActivitySecondaryMonitorLastName = new Scanner(System.in);

								System.out.println("Introduzca si esta capacitado para educación especial: \n");
								Scanner scActivitySecondaryMonitorSpecialEducation = new Scanner(System.in);
							break;

							case 5:
								System.out.println("Seleccione la actividad: \n");
								Scanner scActivityId = new Scanner(System.in);
								// Listar actividades
								// Seleccionar actividad
								System.out.println("Introduzca el id (dni sin letra)id: \n");
								Scanner scActivityMonitorId  = new Scanner(System.in);
								
								System.out.println("Introduzca el nombre: \n");
								Scanner scActivityMonitorName  = new Scanner(System.in);


								System.out.println("Introduzca el apellido: \n");
								Scanner scActivityMonitorLastName = new Scanner(System.in);

								System.out.println("Introduzca si esta capacitado para educación especial: \n");
								Scanner scActivityMonitorSpecialEducation = new Scanner(System.in);
							break;

							case 6:
							break;

							default:

							System.out.println("Opción no válida\n");
						}
					} while (optionCampManager != 6);
					break;

                //InscriptionManager
				case 3:
					int optionInscriptionManager;
					Scanner scInscriptionManager = new Scanner(System.in);
                    do {
						
						System.out.println("1. Inscribir a un asistente\n");
						System.out.println("2. Listar campamentos disponibles\n");
						System.out.println("3. Volver\n");
						optionInscriptionManager = scInscriptionManager.nextInt();
						
						switch (optionInscriptionManager) {
							case 1:
								System.out.println("Introduzca el asistente: \n");
								// Mostrar asistentes
								// Seleccionar un asistente
								// Mostrar campamentos
								// Seleccionar un campamento (parcial o completo?)
							break;
							
							case 2:
							// Mostrar campamentos
							// Seleccionar un campamento (parcial o completo?)
							System.out.println("Introduzca si el campamento es parcial o completo: \n");
							break;
							
							case 3:
							break;
							default:

							System.out.println("Opción no válida\n");
						}
					} while (optionInscriptionManager != 3);
                    break;
				//Volver
                case 4:
                    System.exit(0);
                    break;
                default:
                    // Opción no válida
                    System.out.println("Opción no válida\n");
            }
        } while (opcion != 4);
	}
}
