package cli;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Properties;
import java.io.File;

import domain.entities.Activity;
import domain.entities.Assistant;
import domain.entities.Camp;
import domain.entities.Inscription;
import domain.entities.Monitor;
import domain.exceptions.AfterLateTimeException;
import domain.exceptions.AfterStartTimeException;
import domain.exceptions.AssistantAlreadyEnrolledException;
import domain.exceptions.IsNotAnSpecialEducator;
import domain.exceptions.MaxAssistantExcededException;
import domain.exceptions.MaxMonitorsAddedException;
import domain.exceptions.NeedToAddAnSpecialMonitorException;
import domain.exceptions.NotFoundException;
import domain.exceptions.NotTheSameLevelException;
import domain.exceptions.WrongEducativeLevelException;
import domain.interfaces.IRepository;
import domain.values.EducativeLevel;
import domain.values.TimeSlot;
import managers.AssistantsManager;
import managers.CampsManager;
import managers.InscriptionManager;
import repositories.InFileSystemActivityRepository;
import repositories.InFileSystemAssistantsRepository;
import repositories.InMemoryCampRepository;
import repositories.InFileSystemInscriptionRepository;
import repositories.InFileSystemMonitorRepository;
import utilities.Utils;


public class Main {
	 private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
	            System.out.print("\033[H\033[2J");
	            System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

	private static void showAssistants(List<Assistant> listOfAssistants, boolean returnOption) {
        for (int i = 0; i < listOfAssistants.size(); i++) {
        	Assistant iterableAssistant = listOfAssistants.get(i);
            System.out.println((i + 1) + ". DNI:" + iterableAssistant.getId() 
            							+ ", Nombre:" + iterableAssistant.getFirstName() + " " + iterableAssistant.getLastName()
            							+ ", Atención Especial: " + (iterableAssistant.isRequireSpecialAttention() ? "Si" : "No"));
        }
        if(returnOption) {
        	System.out.println((listOfAssistants.size() + 1) + ". Volver\n");
        } 
	}
	
	private static void showMonitors(List<Monitor> listOfMonitors, boolean returnOption) {
        for (int i = 0; i < listOfMonitors.size(); i++) {
        	Monitor iterableMonitor = listOfMonitors.get(i);
        	System.out.println((i + 1) + ". DNI:" + iterableMonitor.getId() 
				+ ", Nombre:" + iterableMonitor.getFirstName() + " " + iterableMonitor.getLastName()
				+ ", Educador Especial: " + (iterableMonitor.isSpecialEducator() ? "Si" : "No"));
        }
        if(returnOption) {
        	System.out.println((listOfMonitors.size() + 1) + ". Volver\n");
        } 
	}
	
	
	private static void showActivities(List<Activity> listOfActivities, boolean returnOption) {
        for (int i = 0; i < listOfActivities.size(); i++) {
        	Activity iterableActivity = listOfActivities.get(i);
            System.out.println((i + 1) + ". " + iterableActivity.getActivityName());
        }
        if(returnOption) {
        	System.out.println((listOfActivities.size() + 1) + ". Volver\n");
        }  
	}
	
	private static void showCamps(List<Camp> listOfCamps, boolean returnOption) {
        for (int i = 0; i < listOfCamps.size(); i++) {
        	Camp iterableCamp = listOfCamps.get(i);
            System.out.println((i + 1) + ". Campamento #" + iterableCamp.getCampID() 
        						+ ": Inicio " + Utils.getStringDate(iterableCamp.getStart()) 
        						+ ".\n");
        }
        if(returnOption) {
        	System.out.println((listOfCamps.size() + 1) + ". Volver\n");
        }  
	}
	
	private static Assistant getDataForAssistant(int id, Scanner sc) {
		System.out.println("Introduzca el nombre del asisitente\n");
		String firstName = sc.next();
		
		System.out.println("Introduzca el apellido del asisitente\n");
		String lastName = sc.next();
		
		
		System.out.println("Introduzca la fecha de nacimiento del asisitente en formato (dd/mm/yyyy)\n");
		Date birthDate = Utils.parseDate(sc.next());
		while (birthDate == null) {
			System.out.println("Por favor, introduzca la fecha con el formato dd/mm/yyyy \n");
			birthDate = Utils.parseDate(sc.next());									
		}
		
		System.out.println("Introduzca si el asistente necesita atención especial (s/n)\n");
		String response = sc.next();
		boolean requireSpecialAttention = response == "s" ? true : false;
		
		return new Assistant(
				id,
				firstName,
				lastName,
				birthDate,
				requireSpecialAttention
		);
	}
	
	private static Monitor getDataForMonitor(int id, Scanner sc) {
		System.out.println("Introduzca el nombre del monitor\n");
		String firstName = sc.next();
		
		System.out.println("Introduzca el apellido del monitor\n");
		String lastName = sc.next();
		
		System.out.println("¿Es un monitor de atencion especial? (s/n)\n");
		String response = sc.next();
		boolean specialAttentionMonitor = response == "s" ? true : false;
		
		return new Monitor(
				id,
				firstName,
				lastName,
				specialAttentionMonitor
		);
	}
	
	private static Camp getDataForCamp(int id, Scanner sc) {
		
		System.out.println("Introduzca la fecha inicio del campamento en formato (dd/mm/yyyy)\n");
		Date startDate = Utils.parseDate(sc.next());
		while (startDate == null) {
			System.out.println("Por favor, introduzca la fecha con el formato dd/mm/yyyy \n");
			startDate = Utils.parseDate(sc.next());									
		}
		
		System.out.println("Introduzca la fecha fin del campamento en formato (dd/mm/yyyy)\n");
		Date endDate = Utils.parseDate(sc.next());
		while (endDate == null) {
			System.out.println("Por favor, introduzca la fecha con el formato dd/mm/yyyy \n");
			endDate = Utils.parseDate(sc.next());									
		}
		
		
		System.out.println("Introduzca el nivel educativo (1: infantil, 2: juvenil, 3: adolescente)\n");
		int educativeLevelOption = sc.nextInt();
		EducativeLevel educativeLevel = educativeLevelOption == 1 
				? EducativeLevel.ELEMENTARY
				: educativeLevelOption == 2
					? EducativeLevel.PRESCHOOL
					: EducativeLevel.TEENAGER;
		
		
		System.out.println("Introduzca la capacidad del campamento\n");
		int capacity = sc.nextInt();
		
		return new Camp(
				id,
				startDate,
				endDate,
				educativeLevel,
				capacity
		);
	}
	
	private static Activity getDataForActivity(String activityName, Scanner sc) {
		
		System.out.println("Introduzca el nivel educativo (1: infantil, 2: juvenil, 3: adolescente)\n");
		int educativeLevelOption = sc.nextInt();
		EducativeLevel educativeLevel = educativeLevelOption == 1 
				? EducativeLevel.ELEMENTARY
				: educativeLevelOption == 2
					? EducativeLevel.PRESCHOOL
					: EducativeLevel.TEENAGER;
		
		System.out.println("Introduzca la franja horaria (1: mañana, 2: tarde)\n");
		int timeSlotOption = sc.nextInt();
		TimeSlot timeSlot = timeSlotOption == 1 
				? TimeSlot.MORNING
				: TimeSlot.AFTERNOON;
		
		
		System.out.println("Introduzca la capacidad de la actividad\n");
		int capacity = sc.nextInt();
		
		System.out.println("Introduzca el número de monitores necesarios\n");
		int nMonitorsNeeded = sc.nextInt();
		
		return new Activity(
				activityName,
				educativeLevel,
				timeSlot,
				capacity,
				nMonitorsNeeded
		);
	}
	
	public static void main(String[] args) {
		Properties prop = new Properties();
		String filename = "src/cli/.properties.txt";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			prop.load(reader);
			
			String pathActivityRep = prop.getProperty("pathActivityRep");
			String pathAssistantRep = prop.getProperty("pathAssistantRep");
			String pathCampRep = prop.getProperty("pathCampRep");
			String pathInscriptionRep = prop.getProperty("pathInscriptionRep");
			String pathMonitorRep = prop.getProperty("pathMonitorRep");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		int opcion;
		IRepository<Camp, Integer> campRepository = new InMemoryCampRepository();
		IRepository<Activity, String> activityRepository = new InFileSystemActivityRepository(pathActivityRep);
		IRepository<Monitor, Integer> monitorRepository = new InFileSystemMonitorRepository(pathMonitorRep);
		IRepository<Assistant, Integer> assistantRepository = new InFileSystemAssistantRepository(pathAssistantRep);
		IRepository<Inscription, String> inscriptionRepository = new InFileSystemInscriptionRepository(pathInscriptionRep);
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository, monitorRepository,
																		assistantRepository, inscriptionRepository);

		
		
        Scanner sc = new Scanner(System.in);
        
        clearConsole();
		do {
            System.out.println("1. Gestor de asistentes\n");
            System.out.println("2. Gestor de campamentos\n");
            System.out.println("3. Gestor de inscripciones\n");
            System.out.println("4. Salir del programa\n");
            
            opcion = sc.nextInt();

            switch (opcion) {
				//AssistantManager
                case 1:
					int optionAssistantsManager;
		
					do {
						System.out.println("1. Dar de alta un nuevo asistente\n");
						System.out.println("2. Modificar asistente\n");
						System.out.println("3. Listar asistentes\n");
						System.out.println("4. Volver\n");
						optionAssistantsManager = sc.nextInt();
						clearConsole();
						
						switch (optionAssistantsManager) {
							case 1: { 
								System.out.println("Introduzca su DNI (sin letra)\n");
								int assistantId = sc.nextInt();
								
								Assistant assistant = new Assistant(assistantId, "", "", null, false);
								boolean isRegistered = assistantsManager.isRegistered(assistant);
								if(isRegistered == true ){
									clearConsole();
									System.out.println("Este DNI ya ha sido registrado en nuestro sistema\n");
									break;
								}
								
								assistantsManager.registerAssistant(
										getDataForAssistant(assistantId, sc)
								);
								
								clearConsole();
								System.out.println("Asistente dado de alta correctamente\n");	
								break;
							}
							case 2: {
								List<Assistant> listOfRegisteredAssistants = assistantsManager.getListOfRegisteredAssistant();
								System.out.println("Lista de asistentes:");

								showAssistants(listOfRegisteredAssistants, true);
								
								System.out.println("Seleccione un asistente\n");
								int optionSelected = sc.nextInt();
								
								if(optionSelected == listOfRegisteredAssistants.size() + 1) {
									clearConsole();
									break;
								} else if (optionSelected > listOfRegisteredAssistants.size() + 1) {
									clearConsole();
									System.out.println("Opción invalida\n");
									break;
								}
								
								assistantsManager.updateAssistant(
										getDataForAssistant(
												listOfRegisteredAssistants.get(opcion-1).getId(), 
												sc
										)
								);
								
								clearConsole();
								System.out.println("Asistente actualizado correctamente\n");
								break;
							}
							case 3: {
								List<Assistant> listOfRegisteredAssistants = assistantsManager.getListOfRegisteredAssistant();
								System.out.println("Lista de asistentes:");

								showAssistants(listOfRegisteredAssistants, false);

					            System.out.println("Pulse enter para volver.\n");
					            sc.nextLine();
					            sc.nextLine();
								
					            clearConsole();
								break;
							}
							case 4:
								clearConsole();
							break;
							
							default:
								clearConsole();
								System.out.println("Opción no válida\n");
						}
					} while (optionAssistantsManager != 4);
					
                    break;
				//CampManager
                case 2:
					int optionCampManager;
		
					do {
						System.out.println("1. Dar de alta un nuevo campamento\n");
						System.out.println("2. Registrar nueva actividad\n");
						System.out.println("3. Asociar un monitor a una actividad\n");
						System.out.println("4. Gestionar un campamento\n");
						System.out.println("5. Volver\n");
						optionCampManager = sc.nextInt();
						clearConsole();
						switch (optionCampManager) {
							case 1: {
								int idCamp = campRepository.getAll().size() + 1;
								
								System.out.println("Creando campamento con id: #" + idCamp);
								
								campsManager.registerCamp(
										getDataForCamp(idCamp, sc)
								);
			
								clearConsole();
								System.out.println("Campamento #" + idCamp + " creado exitosamente. \n");
								break;
							}
							case 2: {
								System.out.println("Introduzca el nombre de la actividad\n");
								String activityName = sc.next();
								
								try {
									activityRepository.find(activityName);
									clearConsole();
									System.out.println("Ya existe una actividad con ese nombre\n");
									break;
								} catch (NotFoundException e) {}
								
								activityRepository.save(getDataForActivity(activityName, sc));

								clearConsole();
								System.out.println("Actividad \"" + activityName + "\" registrada correctamente \n");
								
								break;
							}
							case 3: {
								List<Activity> listOfActivities = activityRepository.getAll();
								System.out.println("Lista de actividades en el sistema:");

								showActivities(listOfActivities, true);
								
								System.out.println("Seleccione una actividad\n");
								int optionSelected = sc.nextInt();
								
								if(optionSelected == listOfActivities.size() + 1) {
									clearConsole();
									break;
								} else if (optionSelected > listOfActivities.size() + 1) {
									clearConsole();
									System.out.println("Opción invalida\n");
									break;
								}
								
								Activity selectedActivity = listOfActivities.get(optionSelected-1);
								
								System.out.println("Introduzca el DNI (sin letra) del monitor\n");
								int monitorId = sc.nextInt();
								
								try {
									monitorRepository.find(monitorId);
									clearConsole();
									System.out.println("Ya existe un monitor con ese DNI\n");
									break;
								} catch (NotFoundException e) {}
								
								Monitor monitorCreated = getDataForMonitor(monitorId, sc);
								
								try {
									selectedActivity.registerMonitor(monitorCreated);
								} catch (MaxMonitorsAddedException e) {
									clearConsole();
									System.out.println("No se pueden añadir más monitores a esta actividad.\n");
									break;
								}
								
								monitorRepository.save(monitorCreated);
								activityRepository.save(selectedActivity);
								
								clearConsole();
								System.out.println("Monitor con DNI " + monitorCreated.getId() + " creado y agregado a la actividad \"" + selectedActivity.getActivityName() + "\" correctamente.\n");
 								break;
							}
							case 4: {
								List<Camp> listAvailableCamps = campRepository.getAll();
								System.out.println("Lista de campamentos en el sistema:");

								showCamps(listAvailableCamps, true);
								
								System.out.println("Seleccione un campamento\n");
								int optionSelectedCamp = sc.nextInt();
								
								if(optionSelectedCamp == listAvailableCamps.size() + 1) {
									clearConsole();
									break;
								} else if (optionSelectedCamp > listAvailableCamps.size() + 1) {
									clearConsole();
									System.out.println("Opción invalida\n");
									break;
								}
								
								Camp selectedCamp = listAvailableCamps.get(optionSelectedCamp -1 );
								
								clearConsole();								
								
								int optionSelectedCampManager;
								do {
									System.out.println("Campamento seleccionado: \n");
									System.out.println("Campamento # " + selectedCamp.getCampID() + " \n\n");
									
									System.out.println("1. Asociar actividad al campamento.\n");
									System.out.println("2. Registrar monitor principal.\n");
									System.out.println("3. Registrar monitor secundario.\n");
									System.out.println("4. Volver\n");
									
									optionSelectedCampManager = sc.nextInt();
									
									clearConsole();
									System.out.println("Campamento seleccionado: \n");
									System.out.println("Campamento #" + selectedCamp.getCampID() + " \n\n");
									
									switch (optionSelectedCampManager) {
										case 1: { 
											List<Activity> listOfActivities = activityRepository.getAll();
											System.out.println("Lista de actividades en el sistema:");

											showActivities(listOfActivities, true);
											
											System.out.println("Seleccione una actividad\n");
											int optionSelected = sc.nextInt();
											
											if(optionSelected == listOfActivities.size() + 1) {
												clearConsole();
												break;
											} else if (optionSelected > listOfActivities.size() + 1) {
												clearConsole();
												System.out.println("Opción invalida\n");
												break;
											}
											
											Activity selectedActivity = listOfActivities.get(optionSelected -1);

											try {
												campsManager.registerActivity(selectedCamp, selectedActivity);
											} catch (NotTheSameLevelException e) {
												clearConsole();
												System.out.println("La actividad y el campamento deben de ser del mismo nivel educativo. \n");
												break;
											}
											
											clearConsole();
											System.out.println("Actividad \"" + selectedActivity.getActivityName() + "\" agregada al campamento #" + selectedCamp.getCampID() + "\n");
											break;
										}
										case 2: {
											List<Activity> listOfActivitiesOfTheSelectedCamp = selectedCamp.getActivities();
											System.out.println("Lista de actividades del campamento:");

											showActivities(listOfActivitiesOfTheSelectedCamp, true);
											
											System.out.println("Seleccione una actividad\n");
											int optionSelected = sc.nextInt();
											
											if(optionSelected == listOfActivitiesOfTheSelectedCamp.size() + 1) {
												clearConsole();
												break;
											} else if (optionSelected > listOfActivitiesOfTheSelectedCamp.size() + 1) {
												clearConsole();
												System.out.println("Opción invalida\n");
												break;
											}
											Activity selectedActivity = listOfActivitiesOfTheSelectedCamp.get(optionSelected - 1);

											clearConsole();
											System.out.println("Campamento seleccionado: \n");
											System.out.println("Campamento # " + selectedCamp.getCampID() + " \n\n");
											System.out.println("Actividad seleccionada: \n");
											System.out.println("Actividad \"" + selectedActivity.getActivityName() + "\" \n\n");
											
											List<Monitor> listOfMonitorsOfTheSelectedActivity = selectedActivity.getMonitorList();
											System.out.println("Lista de monitores de la actividad:");

											showMonitors(listOfMonitorsOfTheSelectedActivity, true);
											
											System.out.println("Seleccione un monitor\n");
											optionSelected = sc.nextInt();
											
											if(optionSelected == listOfMonitorsOfTheSelectedActivity.size() + 1) {
												clearConsole();
												break;
											} else if (optionSelected > listOfMonitorsOfTheSelectedActivity.size() + 1) {
												clearConsole();
												System.out.println("Opción invalida\n");
												break;
											}
											Monitor selectedMonitor = listOfMonitorsOfTheSelectedActivity.get(optionSelected - 1);
											
											campsManager.setPrincipalMonitor(selectedCamp, selectedMonitor);
											
											clearConsole();
											System.out.println("Monitor con DNI " + selectedMonitor.getId() + " asignado como monitor principal para el campamento #" + selectedCamp.getCampID() + " correctamente.\n");
			 								break;
										}
										case 3: {
											System.out.println("Introduzca el DNI (sin letra) del monitor\n");
											int monitorId = sc.nextInt();
											
											Monitor monitor;
											try {
												monitor = monitorRepository.find(monitorId);
												break;
											} catch (NotFoundException e) {
												System.out.println("El monitor no existe, introduzca sus datos para registrarlo \n");
												monitor = getDataForMonitor(monitorId, sc);
											}
											
											try {
												campsManager.setSpecialMonitor(selectedCamp, monitor);
											} catch (IsNotAnSpecialEducator e) {
												clearConsole();
												System.out.println("No se puede agregar como monitor especial a un monitor que no es un educador especial. \n");
												break;
											}
											
											clearConsole();
											System.out.println("Monitor con DNI " + monitor.getId() + " asignado como monitor especial para el campamento #" + selectedCamp.getCampID() + " correctamente.\n");
			 								break;
										}
										case 4:
											clearConsole();
										break;
										
										default:
											clearConsole();
											System.out.println("Opción no válida\n");
									}
								} while (optionSelectedCampManager != 4);
								
								break;
							}
							case 5:
								clearConsole();
							break;

							default:
								clearConsole();
								System.out.println("Opción no válida\n");
						}
					} while (optionCampManager != 5);
					break;

                //InscriptionManager
				case 3:
					int optionInscriptionManager;
                    do {
						
						System.out.println("1. Inscribir a un asistente\n");
						System.out.println("2. Listar campamentos disponibles\n");
						System.out.println("3. Volver\n");
						optionInscriptionManager = sc.nextInt();
						
						clearConsole();
						switch (optionInscriptionManager) {
							case 1: {
								List<Assistant> listOfAssistants = assistantRepository.getAll();
								System.out.println("Lista de asistentes en el sistema:");

								showAssistants(listOfAssistants, true);
								
								System.out.println("Seleccione un asistente\n");
								int optionSelected = sc.nextInt();
								
								if(optionSelected == listOfAssistants.size() + 1) {
									clearConsole();
									break;
								} else if (optionSelected > listOfAssistants.size() + 1) {
									clearConsole();
									System.out.println("Opción invalida\n");
									break;
								}
								
								Assistant selectedAssistant = listOfAssistants.get(optionSelected - 1);
								
								clearConsole();
								System.out.println("Asistente seleccionado: \n");
								System.out.println("DNI: " + selectedAssistant.getId() + " Nombre: " + selectedAssistant.getFirstName() + " " + selectedAssistant.getLastName() + " \n\n");
								
								List<Camp> listAvailableCamps = inscriptionManager.avaliableCamps(Utils.getCurrentDate());
								System.out.println("Lista de campamentos disponibles:");

								showCamps(listAvailableCamps, true);
								
								System.out.println("Seleccione un campamento\n");
								optionSelected = sc.nextInt();
								
								if(optionSelected == listAvailableCamps.size() + 1) {
									clearConsole();
									break;
								} else if (optionSelected > listAvailableCamps.size() + 1) {
									clearConsole();
									System.out.println("Opción invalida\n");
									break;
								}
								
								Camp selectedCamp = listAvailableCamps.get(optionSelected - 1);
								
								clearConsole();
								System.out.println("Asistente seleccionado: \n");
								System.out.println("DNI: " + selectedAssistant.getId() + " Nombre: " + selectedAssistant.getFirstName() + " " + selectedAssistant.getLastName() + "\n");
								System.out.println("Campamento seleccionado: \n");
								System.out.println("Campamento # " + selectedCamp.getCampID() + " \n\n");
								
								System.out.println("¿En que modalidad es la inscripción? (1: Parcial, 2: Completa)\n");
								int option = sc.nextInt();
								boolean isPartial = option == 1
										? true
										: false;
								
								Inscription inscription;
								try {
									inscription = inscriptionManager.enroll(
											selectedAssistant.getId(),
											selectedCamp.getCampID(), 
											Utils.getCurrentDate(), 
											isPartial, 
											selectedAssistant.isRequireSpecialAttention()
									);
								} catch (WrongEducativeLevelException e) {
									clearConsole();
									System.out.println("El nivel educativo de este campamento no es adecuado para el asistente. \n");
									break;
								} catch (NeedToAddAnSpecialMonitorException e) {
									clearConsole();
									System.out.println("Es necesario añadir un monitor de atención especial antes de inscribir al asistente. \n");
									break;
								} catch (AssistantAlreadyEnrolledException e) {
									clearConsole();
									System.out.println("Este asistente ya está inscrito en el campamento. \n");
									break;
								} catch (MaxAssistantExcededException e) {
									clearConsole();
									System.out.println("No hay plazas libres para inscribir al asistente a todas las actividades de la modalidad seleccionada del campamento. \n");
									break;
								} catch (AfterStartTimeException e) {
									clearConsole();
									System.out.println("El campamento ya ha comenzado. \n");
									break;
								} catch (AfterLateTimeException e) {
									clearConsole();
									System.out.println("No es posible inscribirse a un campamento si no se hace con al menos 48h de antelación. \n");
									break;
								}
								
								clearConsole();
								System.out.println("Inscripción realizada correctamente. Precio: " + inscription.getPrice() + " euros. \n");
								
								break;
							}case 2: {
								List<Camp> listAvailableCamps = inscriptionManager.avaliableCamps(Utils.getCurrentDate());
								System.out.println("Lista de campamentos disponibles:");

								showCamps(listAvailableCamps, false);

					            System.out.println("Pulse enter para volver.\n");
					            sc.nextLine();
					            sc.nextLine();
					            clearConsole();
								break;
							}
							case 3:
								clearConsole();
							break;
							default:
							clearConsole();
							System.out.println("Opción no válida\n");
						}
					} while (optionInscriptionManager != 3);
                    break;
                 case 4:
                	clearConsole();
                	System.out.println("Saliendo del programa. . .");
                    System.exit(0);
                    break;
                default:
                	clearConsole();
                    System.out.println("Opción no válida\n");
            }
        } while (opcion != 4);
	}
}
