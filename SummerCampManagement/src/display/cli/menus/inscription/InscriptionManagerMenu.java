package display.cli.menus.inscription;

import java.util.List;
import java.util.Scanner;

import business.dtos.AssistantDTO;
import business.dtos.CampDTO;
import business.dtos.InscriptionDTO;
import business.exceptions.inscription.AfterLateTimeException;
import business.exceptions.inscription.AfterStartTimeException;
import business.exceptions.inscription.AssistantAlreadyEnrolledException;
import business.exceptions.inscription.MaxAssistantExcededException;
import business.exceptions.inscription.NeedToAddAnSpecialMonitorException;
import business.exceptions.inscription.WrongEducativeLevelException;
import business.managers.AssistantsManager;
import business.managers.InscriptionManager;
import business.utilities.Utils;
import display.cli.menus.Common;
import display.cli.menus.inscription.usecases.EnrollUseCase;
import display.cli.menus.inscription.usecases.ListAvailableCampsUseCase;

public class InscriptionManagerMenu {
	public static void execute(Scanner sc, InscriptionManager inscriptionManager, AssistantsManager assistantsManager) {
		int optionInscriptionManager;
        do {
			
			System.out.println("1. Inscribir a un asistente");
			System.out.println("2. Listar campamentos disponibles");
			System.out.println("3. Volver");
			optionInscriptionManager = sc.nextInt();
			
			Common.clearConsole();
			switch (optionInscriptionManager) {
				case 1: {
					EnrollUseCase.execute(sc, inscriptionManager, assistantsManager);
					break;
				}case 2: {
					ListAvailableCampsUseCase.execute(sc, inscriptionManager);
					break;
				}
				case 3:
					Common.clearConsole();
				break;
				default:
				Common.clearConsole();
				System.out.println("Opción no válida");
			}
		} while (optionInscriptionManager != 3);
	}
}
