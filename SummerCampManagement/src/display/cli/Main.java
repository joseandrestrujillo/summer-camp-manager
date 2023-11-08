package display.cli;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import business.dtos.ActivityDTO;
import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.dtos.MonitorDTO;
import business.enums.EducativeLevel;
import business.enums.TimeSlot;
import business.exceptions.activity.ActivityAlreadyExistException;
import business.exceptions.activity.MaxMonitorsAddedException;
import business.exceptions.activity.MonitorIsNotInActivityException;
import business.exceptions.camp.CampAlreadyRegisteredException;
import business.exceptions.camp.IsNotAnSpecialEducator;
import business.exceptions.camp.NotTheSameLevelException;
import business.exceptions.camp.SpecialMonitorAlreadyRegisterException;
import business.exceptions.inscription.AfterLateTimeException;
import business.exceptions.inscription.AfterStartTimeException;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.inscription.MaxAssistantExcededException;
import business.exceptions.inscription.NeedToAddAnSpecialMonitorException;
import business.exceptions.inscription.WrongEducativeLevelException;
import business.interfaces.IActivityDAO;
import business.interfaces.IAssistantDAO;
import business.interfaces.IDAO;
import business.interfaces.IMonitorDAO;
import business.managers.AssistantsManager;
import business.managers.CampsManager;
import business.managers.InscriptionManager;
import business.utilities.Utils;
import data.database.daos.InDatabaseActivityDAO;
import data.database.daos.InDatabaseAssistantDAO;
import data.database.daos.InDatabaseCampDAO;
import data.database.daos.InDatabaseInscriptionDAO;
import data.database.daos.InDatabaseMonitorDAO;


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

	private static void showAssistants(List<AssistantDTO> listOfAssistants, boolean returnOption) {
        for (int i = 0; i < listOfAssistants.size(); i++) {
        	AssistantDTO iterableAssistant = listOfAssistants.get(i);
            System.out.println((i + 1) + ". DNI:" + iterableAssistant.getId() 
            							+ ", Nombre:" + iterableAssistant.getFirstName() + " " + iterableAssistant.getLastName()
            							+ ", Atención Especial: " + (iterableAssistant.isRequireSpecialAttention() ? "Si" : "No"));
        }
        if(returnOption) {
        	System.out.println((listOfAssistants.size() + 1) + ". Volver\n");
        } 
	}
	
	private static void showMonitors(List<MonitorDTO> listOfMonitors, boolean returnOption) {
        for (int i = 0; i < listOfMonitors.size(); i++) {
        	MonitorDTO iterableMonitor = listOfMonitors.get(i);
        	System.out.println((i + 1) + ". DNI:" + iterableMonitor.getId() 
				+ ", Nombre:" + iterableMonitor.getFirstName() + " " + iterableMonitor.getLastName()
				+ ", Educador Especial: " + (iterableMonitor.isSpecialEducator() ? "Si" : "No"));
        }
        if(returnOption) {
        	System.out.println((listOfMonitors.size() + 1) + ". Volver\n");
        } 
	}
	
	
	private static void showActivities(List<ActivityDTO> listOfActivities, boolean returnOption) {
        for (int i = 0; i < listOfActivities.size(); i++) {
        	ActivityDTO iterableActivity = listOfActivities.get(i);
            System.out.println((i + 1) + ". " + iterableActivity.getActivityName());
        }
        if(returnOption) {
        	System.out.println((listOfActivities.size() + 1) + ". Volver\n");
        }  
	}
	
	private static void showCamps(List<CampDTO> listOfCamps, boolean returnOption) {
        for (int i = 0; i < listOfCamps.size(); i++) {
        	CampDTO iterableCamp = listOfCamps.get(i);
            System.out.println((i + 1) + ". Campamento #" + iterableCamp.getCampID() 
        						+ ": Inicio " + Utils.getStringDate(iterableCamp.getStart()) 
        						+ ".\n");
        }
        if(returnOption) {
        	System.out.println((listOfCamps.size() + 1) + ". Volver\n");
        }  
	}
	
	private static AssistantDTO getDataForAssistant(int id, Scanner sc) {
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
		boolean requireSpecialAttention = response.toLowerCase().equals("s");
		
		return new AssistantDTO(
				id,
				firstName,
				lastName,
				birthDate,
				requireSpecialAttention
		);
	}
	
	private static MonitorDTO getDataForMonitor(int id, Scanner sc) {
		System.out.println("Introduzca el nombre del monitor\n");
		String firstName = sc.next();
		
		System.out.println("Introduzca el apellido del monitor\n");
		String lastName = sc.next();
		
		System.out.println("¿Es un monitor de atencion especial? (s/n)\n");
		String response = sc.next();
		boolean specialAttentionMonitor = response.toLowerCase().equals("s");
		
		return new MonitorDTO(
				id,
				firstName,
				lastName,
				specialAttentionMonitor
		);
	}
	
	private static CampDTO getDataForCamp(int id, Scanner sc) {
		
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
		
		return new CampDTO(
				id,
				startDate,
				endDate,
				educativeLevel,
				capacity
		);
	}
	
	private static ActivityDTO getDataForActivity(String activityName, Scanner sc) {
		
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
		
		return new ActivityDTO(
				activityName,
				educativeLevel,
				timeSlot,
				capacity,
				nMonitorsNeeded
		);
	}
	
	public static void main(String[] args) {
		int opcion;
		IDAO<CampDTO, Integer> campRepository = new InDatabaseCampDAO();
		IActivityDAO activityRepository = new InDatabaseActivityDAO();
		IMonitorDAO monitorRepository = new InDatabaseMonitorDAO();
		IAssistantDAO assistantRepository = new InDatabaseAssistantDAO();
		IDAO<InscriptionDTO, String> inscriptionRepository = new InDatabaseInscriptionDAO();
		AssistantsManager assistantsManager = new AssistantsManager(assistantRepository);
		CampsManager campsManager = new CampsManager(campRepository, activityRepository, monitorRepository);
		InscriptionManager inscriptionManager = new InscriptionManager(campRepository, activityRepository,
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
								System.out.println("Introduzca el DNI (sin letra) del asistente\n");
								int assistantId = sc.nextInt();
								AssistantDTO assistant = getDataForAssistant(assistantId, sc);
								try {									
									assistantsManager.registerAssistant(assistant);
								} catch (AssistantAlreadyEnrolledException e) {
									clearConsole();
									System.out.println("Este DNI ya ha sido registrado en nuestro sistema\n");
									break;
								}
								
								clearConsole();
								System.out.println("Asistente dado de alta correctamente\n");	
								break;
							}
							case 2: {
								List<AssistantDTO> listOfRegisteredAssistants = assistantsManager.getListOfRegisteredAssistant();
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
												listOfRegisteredAssistants.get(optionSelected-1).getId(),
												sc
										)
								);
								
								clearConsole();
								System.out.println("Asistente actualizado correctamente\n");
								break;
							}
							case 3: {
								List<AssistantDTO> listOfRegisteredAssistants = assistantsManager.getListOfRegisteredAssistant();
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
						System.out.println("3. Registrar nuevo monitor\n");
						System.out.println("4. Asociar un monitor a una actividad\n");
						System.out.println("5. Gestionar un campamento\n");
						System.out.println("6. Volver\n");
						optionCampManager = sc.nextInt();
						clearConsole();
						switch (optionCampManager) {
							case 1: {
								int idCamp = campsManager.listOfCamps().size() + 1;
								
								System.out.println("Creando campamento con id: #" + idCamp);
								CampDTO camp = getDataForCamp(idCamp, sc);
								
								List<ActivityDTO> listOfActivities = campsManager.listOfActivities();
								System.out.println("Lista de actividades en el sistema:");

								showActivities(listOfActivities, true);
								
								System.out.println("Debe seleccionar alguna actividad para crear el campamento\n");
								int optionSelected = sc.nextInt();
								
								if(optionSelected == listOfActivities.size() + 1) {
									clearConsole();
									break;
								} else if (optionSelected > listOfActivities.size() + 1) {
									clearConsole();
									System.out.println("Opción invalida\n");
									break;
								}
								
								ActivityDTO selectedActivity = listOfActivities.get(optionSelected - 1);
								
								
								List<MonitorDTO> monitorsOfTheActivity = campsManager.getMonitorsOfAnActivity(selectedActivity);
								System.out.println("Lista de monitores de la actividad:");
								showMonitors(monitorsOfTheActivity, true);
								
								System.out.println("Debe seleccionar el monitor principal del campamento\n");
								optionSelected = sc.nextInt();
								
								if(optionSelected == monitorsOfTheActivity.size() + 1) {
									clearConsole();
									break;
								} else if (optionSelected > monitorsOfTheActivity.size() + 1) {
									clearConsole();
									System.out.println("Opción invalida\n");
									break;
								}
								
								MonitorDTO selectedMonitor = monitorsOfTheActivity.get(optionSelected - 1);
								
								try {
									campsManager.registerActivityInACamp(camp, selectedActivity);									
									campsManager.setPrincipalMonitor(camp, selectedMonitor);
									campsManager.registerCamp(camp);
								} catch (NotTheSameLevelException e) {
									clearConsole();
									System.out.println("La actividad y el campamento deben de ser del mismo nivel educativo. \n");
									break;								
								} catch (MonitorIsNotInActivityException e) {
									clearConsole();
									System.out.println("El monitor principal no pertenece a ninguna actividad del campamento. \n");
									break;								
								} catch (CampAlreadyRegisteredException e) {
									clearConsole();
									System.out.println("El campamento ya existe \n");
									break;								
								}
			
								clearConsole();
								System.out.println("Campamento #" + idCamp + " creado exitosamente. \n");
								break;
							}
							case 2: {
								System.out.println("Introduzca el nombre de la actividad\n");
								String activityName = sc.next();
								ActivityDTO activity = getDataForActivity(activityName, sc);
								try {
									campsManager.registerActivity(activity);
								} catch (ActivityAlreadyExistException e) {
									clearConsole();
									System.out.println("Ya existe una actividad con ese nombre\n");
									break;									
								}
								
								clearConsole();
								System.out.println("Actividad \"" + activityName + "\" registrada correctamente \n");
								
								break;
							}
							case 3: {
								System.out.println("Introduzca el DNI (sin letra) del monitor\n");
								int monitorId = sc.nextInt();
								MonitorDTO monitor = getDataForMonitor(monitorId, sc);
								try {									
									campsManager.registerMonitor(monitor);
								} catch (AssistantAlreadyEnrolledException e) {
									clearConsole();
									System.out.println("Este DNI ya ha sido registrado en nuestro sistema\n");
									break;
								}
								
								clearConsole();
								System.out.println("Asistente dado de alta correctamente\n");	
								break;
							}
							case 4: {
								List<ActivityDTO> listOfActivities = campsManager.listOfActivities();
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
								
								ActivityDTO selectedActivity = listOfActivities.get(optionSelected-1);
								
								List<MonitorDTO> monitors = campsManager.listOfMonitors();
								
								showMonitors(monitors, true);
								
								System.out.println("Seleccione un monitor\n");
								optionSelected = sc.nextInt();
								
								if(optionSelected == monitors.size() + 1) {
									clearConsole();
									break;
								} else if (optionSelected > monitors.size() + 1) {
									clearConsole();
									System.out.println("Opción invalida\n");
									break;
								}
								
								MonitorDTO monitorSelected = monitors.get(optionSelected-1);
								
								try {
									campsManager.registerMonitorInActivity(selectedActivity, monitorSelected);
								} catch (MaxMonitorsAddedException e) {
									clearConsole();
									System.out.println("No se pueden añadir más monitores a esta actividad.\n");
									break;
								}
								
								clearConsole();
								System.out.println("Monitor con DNI " + monitorSelected.getId() + " creado y agregado a la actividad \"" + selectedActivity.getActivityName() + "\" correctamente.\n");
 								break;
							}
							case 5: {
								List<CampDTO> listAvailableCamps = campsManager.listOfCamps();
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
								
								CampDTO selectedCamp = listAvailableCamps.get(optionSelectedCamp -1 );
								
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
											List<ActivityDTO> listOfActivities = campsManager.listOfActivities();
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
											
											ActivityDTO selectedActivity = listOfActivities.get(optionSelected -1);

											try {
												campsManager.registerActivityInACamp(selectedCamp, selectedActivity);
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
											List<ActivityDTO> listOfActivitiesOfTheSelectedCamp = campsManager.getActivitiesOfACamp(selectedCamp);
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
											ActivityDTO selectedActivity = listOfActivitiesOfTheSelectedCamp.get(optionSelected - 1);

											clearConsole();
											System.out.println("Campamento seleccionado: \n");
											System.out.println("Campamento # " + selectedCamp.getCampID() + " \n\n");
											System.out.println("Actividad seleccionada: \n");
											System.out.println("Actividad \"" + selectedActivity.getActivityName() + "\" \n\n");
											
											List<MonitorDTO> listOfMonitorsOfTheSelectedActivity = campsManager.getMonitorsOfAnActivity(selectedActivity);
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
											
											MonitorDTO selectedMonitor = listOfMonitorsOfTheSelectedActivity.get(optionSelected - 1);
											
											
											try {
												campsManager.setPrincipalMonitor(selectedCamp, selectedMonitor);												
											} catch (MonitorIsNotInActivityException e) {
												clearConsole();
												System.out.println("El monitor principal no pertenece a ninguna actividad del campamento. \n");
												break;
											}
											clearConsole();
											System.out.println("Monitor con DNI " + selectedMonitor.getId() + " asignado como monitor principal para el campamento #" + selectedCamp.getCampID() + " correctamente.\n");
			 								break;
										}
										case 3: {
											List<MonitorDTO> monitors = campsManager.listOfMonitors();
											System.out.println("Lista de monitores del sistema\n");
											
											showMonitors(monitors, true);
											
											System.out.println("Seleccione un monitor\n");
											int optionSelected = sc.nextInt();
											
											if(optionSelected == monitors.size() + 1) {
												clearConsole();
												break;
											} else if (optionSelected > monitors.size() + 1) {
												clearConsole();
												System.out.println("Opción invalida\n");
												break;
											}
											
											MonitorDTO monitorSelected = monitors.get(optionSelected-1);
											
											try {
												campsManager.setSpecialMonitor(selectedCamp, monitorSelected);
											} catch (IsNotAnSpecialEducator e) {
												clearConsole();
												System.out.println("No se puede agregar como monitor especial a un monitor que no es un educador especial. \n");
												break;
											} catch (SpecialMonitorAlreadyRegisterException e) {
												clearConsole();
												System.out.println("No se puede agregar como monitor especial a un monitor que ya es monitor de una actividad del campamento. \n");
												break;
											}
											
											clearConsole();
											System.out.println("Monitor con DNI " + monitorSelected.getId() + " asignado como monitor especial para el campamento #" + selectedCamp.getCampID() + " correctamente.\n");
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
							case 6:
								clearConsole();
							break;

							default:
								clearConsole();
								System.out.println("Opción no válida\n");
						}
					} while (optionCampManager != 6);
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
								List<AssistantDTO> listOfAssistants = assistantsManager.getListOfRegisteredAssistant();
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
								
								AssistantDTO selectedAssistant = listOfAssistants.get(optionSelected - 1);
								
								clearConsole();
								System.out.println("Asistente seleccionado: \n");
								System.out.println("DNI: " + selectedAssistant.getId() + " Nombre: " + selectedAssistant.getFirstName() + " " + selectedAssistant.getLastName() + " \n\n");
								
								List<CampDTO> listAvailableCamps = inscriptionManager.avaliableCamps(Utils.getCurrentDate());
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
								
								CampDTO selectedCamp = listAvailableCamps.get(optionSelected - 1);
								
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
								
								InscriptionDTO inscription;
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
								List<CampDTO> listAvailableCamps = inscriptionManager.avaliableCamps(Utils.getCurrentDate());
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
