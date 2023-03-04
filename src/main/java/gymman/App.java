package gymman;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import gymman.auth.AuthService;
import gymman.auth.AuthServiceImpl;
import gymman.auth.PermissionImpl;
import gymman.auth.Role;
import gymman.auth.RoleRepository;
import gymman.auth.RoleRepositoryImpl;
import gymman.customers.AdditionalService;
import gymman.customers.AdditionalServiceRepository;
import gymman.customers.AdditionalServiceRepositoryImpl;
import gymman.customers.Customer;
import gymman.customers.Customer.Gender;
import gymman.customers.CustomerRepository;
import gymman.customers.CustomerRepositoryImpl;
import gymman.customers.NumberedRegistration;
import gymman.customers.RegistrationRepository;
import gymman.customers.RegistrationRepositoryImpl;
import gymman.customers.SubscriptionType;
import gymman.customers.SubscriptionTypeRepository;
import gymman.customers.SubscriptionTypeRepositoryImpl;
import gymman.customers.TermRegistration;
import gymman.employees.CalendarService;
import gymman.employees.Employee;
import gymman.employees.EmployeeRepository;
import gymman.employees.EmployeeRepositoryImpl;
import gymman.employees.WorkShift;
import gymman.employees.WorkShiftRepository;
import gymman.employees.WorkShiftRepositoryImpl;
import gymman.infoclient.BmiRepository;
import gymman.tool.Tool;
import gymman.tool.ToolRepository;
import gymman.ui.Controller;
import gymman.ui.Page;
import gymman.ui.app.AppController;
import gymman.ui.customers.AddCustomerPage;
import gymman.ui.customers.AddRegistrationPage;
import gymman.ui.customers.AddServicePage;
import gymman.ui.customers.AddSubscriptionPage;
import gymman.ui.customers.AdditionalServicePage;
import gymman.ui.customers.CreateCustomerController;
import gymman.ui.customers.CreateRegistrationController;
import gymman.ui.customers.CreateServiceController;
import gymman.ui.customers.CreateSubscriptionController;
import gymman.ui.customers.CustomerController;
import gymman.ui.customers.CustomerPage;
import gymman.ui.customers.RegistrationController;
import gymman.ui.customers.RegistrationEntryController;
import gymman.ui.customers.RegistrationEntryPage;
import gymman.ui.customers.RegistrationPage;
import gymman.ui.customers.ServiceController;
import gymman.ui.customers.SubscriptionController;
import gymman.ui.customers.SubscriptionPage;
import gymman.ui.dashboard.DashboardController;
import gymman.ui.dashboard.DashboardPage;
import gymman.ui.employees.EditEmployeeController;
import gymman.ui.employees.EditEmployeePage;
import gymman.ui.employees.EditWorkShiftController;
import gymman.ui.employees.EditWorkShiftPage;
import gymman.ui.employees.EmployeesController;
import gymman.ui.employees.EmployeesPage;
import gymman.ui.employees.ShiftCalendarController;
import gymman.ui.employees.ShiftCalendarPage;
import gymman.ui.infoclient.BMIController;
import gymman.ui.infoclient.BMIPage;
import gymman.ui.infoclient.InfoClientController;
import gymman.ui.infoclient.InfoClientPage;
import gymman.ui.infoclient.LoginClientController;
import gymman.ui.infoclient.LoginClientPage;
import gymman.ui.login.LoginController;
import gymman.ui.login.LoginPage;
import gymman.ui.navigation.NavigationService;
import gymman.ui.navigation.NavigationServiceImpl;
import gymman.ui.roles.NewRoleController;
import gymman.ui.roles.NewRolePage;
import gymman.ui.roles.RolesController;
import gymman.ui.roles.RolesPage;
import gymman.ui.tool.NewToolController;
import gymman.ui.tool.NewToolPage;
import gymman.ui.tool.ToolController;
import gymman.ui.tool.ToolPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    public static final int WINDOW_WIDTH_MIN = 800;
    public static final int WINDOW_HEIGHT_MIN = 600;
    public static final int WINDOW_WIDTH_INITIAL = 1300;
    public static final int WINDOW_HEIGHT_INITIAL = 850;

    @Override
    public void init() {

    }

    @Override
    public void start(final Stage primaryStage) {
        try {
            // Repositories
            final RoleRepository roleRepo = new RoleRepositoryImpl();
            final EmployeeRepository employeeRepo = new EmployeeRepositoryImpl();
            final CustomerRepository customerRepo = new CustomerRepositoryImpl();
            final WorkShiftRepository workshiftRepo = new WorkShiftRepositoryImpl();
            final RegistrationRepository registrationRepo = new RegistrationRepositoryImpl();
            final SubscriptionTypeRepository subscriptionTypeRepo = new SubscriptionTypeRepositoryImpl();
            final ToolRepository toolrepository = new ToolRepository();
            final AdditionalServiceRepository serviceRepo = new AdditionalServiceRepositoryImpl();
            final BmiRepository bmirepo = new BmiRepository();

		    // Services
		    final NavigationService navService = new NavigationServiceImpl();
			final AuthService authService = new AuthServiceImpl(employeeRepo, roleRepo);
			final CalendarService calendarService = new CalendarService(workshiftRepo);

			// Login
			final Page login = new LoginPage();
			final Controller loginCtrl = new LoginController(authService, navService);
			login.setController(loginCtrl);
			navService.registerPage(login);

			// Dashboard
			final Page dashboard = new DashboardPage();
			final Controller dashboardCtrl = new DashboardController(authService, employeeRepo);
			dashboard.setController(dashboardCtrl);
			navService.registerPage(dashboard);

			// Roles
		    final Page roles = new RolesPage();
		    final Controller rolesCtrl = new RolesController(roleRepo, navService, authService);
		    roles.setController(rolesCtrl);
			navService.registerPage(roles);

			// New Role
			final Page newRole = new NewRolePage();
			final Controller newRoleCtrl = new NewRoleController(authService, roleRepo, navService);
			newRole.setController(newRoleCtrl);
			navService.registerPage(newRole);

			// Employees
			final Page employees = new EmployeesPage();
			final Controller employeesCtrl = new EmployeesController(employeeRepo, navService, authService);
			employees.setController(employeesCtrl);
			navService.registerPage(employees);

			// Edit employee
			final Page editEmployee = new EditEmployeePage();
			final Controller editEmployeeCtrl = new EditEmployeeController(authService, employeeRepo, navService, roleRepo);
			editEmployee.setController(editEmployeeCtrl);
			navService.registerPage(editEmployee);

			// Shifts Calendar
			final Page shiftCalendar = new ShiftCalendarPage();
			final Controller shiftCalendarCtrl = new ShiftCalendarController(calendarService, navService, employeeRepo);
			shiftCalendar.setController(shiftCalendarCtrl);
            navService.registerPage(shiftCalendar);

            // Edit work shift
            final Page editWorkShift = new EditWorkShiftPage();
            final Controller editWorkShiftCtrl = new EditWorkShiftController(authService, navService, workshiftRepo, employeeRepo);
            editWorkShift.setController(editWorkShiftCtrl);
            navService.registerPage(editWorkShift);

		    //Customer
		    final Page customer = new CustomerPage();
            final Controller customerCtrl = new CustomerController(customerRepo, navService, authService, registrationRepo);
            customer.setController(customerCtrl);
            navService.registerPage(customer);

            // New Customer
            final Page addCustomer = new AddCustomerPage();
            final Controller createCustomerCtrl = new CreateCustomerController(customerRepo, navService);
            addCustomer.setController(createCustomerCtrl);
            navService.registerPage(addCustomer);

            // Registration
            final Page registration = new RegistrationPage();
            final Controller registrationCtrl = new RegistrationController(registrationRepo, navService, authService);
            registration.setController(registrationCtrl);
            navService.registerPage(registration);

            // New Registration
            final Page addRegistration = new AddRegistrationPage();
            final Controller createRegistrationCtrl = new CreateRegistrationController(customerRepo, subscriptionTypeRepo, registrationRepo, serviceRepo, navService);
            addRegistration.setController(createRegistrationCtrl);
            navService.registerPage(addRegistration);

            // Subscription
            final Page subscription = new SubscriptionPage();
            final Controller subscriptionCtrl = new SubscriptionController(subscriptionTypeRepo, navService, authService);
            subscription.setController(subscriptionCtrl);
            navService.registerPage(subscription);

            // New Subscription
            final Page addSubscription = new AddSubscriptionPage();
            final Controller createSubscriptionCtrl = new CreateSubscriptionController(subscriptionTypeRepo, navService);
            addSubscription.setController(createSubscriptionCtrl);
            navService.registerPage(addSubscription);

            // AdditionalService
            final Page additionalService = new AdditionalServicePage();
            final Controller addServiceCtrl = new ServiceController(serviceRepo, navService, authService);
            additionalService.setController(addServiceCtrl);
            navService.registerPage(additionalService);

            // New AdditionalService
            final Page addService = new AddServicePage();
            final Controller createServiceCtrl = new CreateServiceController(serviceRepo, navService);
            addService.setController(createServiceCtrl);
            navService.registerPage(addService);

            // New RegistrationEntry
            final Page registrationEntry = new RegistrationEntryPage();
            final Controller registrationEntryCtrl = new RegistrationEntryController(registrationRepo, customerRepo);
            registrationEntry.setController(registrationEntryCtrl);
            navService.registerPage(registrationEntry);

            // Tool
            final Page tool = new NewToolPage();
            final Controller toolCtrl = new NewToolController(toolrepository, navService);
            tool.setController(toolCtrl);
            navService.registerPage(tool);

            // SearchTools
            final Page searchtool = new ToolPage();
            final Controller searchtoolCtrl = new ToolController(toolrepository, navService, authService);
            searchtool.setController(searchtoolCtrl);
            navService.registerPage(searchtool);

            // InfoClient
            final Page infoclient = new InfoClientPage();
            final Controller infoclientCtrl = new InfoClientController(registrationRepo, bmirepo, navService);
            infoclient.setController(infoclientCtrl);
            navService.registerPage(infoclient);

            // Customer Login
            final Page loginClient = new LoginClientPage();
            final Controller loginClientCtrl = new LoginClientController(customerRepo, navService);
            loginClient.setController(loginClientCtrl);
            navService.registerPage(loginClient);

            // BMIClient
            final Page BMIClient = new BMIPage();
            final Controller BMICtrl = new BMIController(bmirepo, navService);
            BMIClient.setController(BMICtrl);
            navService.registerPage(BMIClient);

            final FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/app/App.fxml"));
			final AppController appController = new AppController(navService, authService);

            loader.setController(appController);

            final Pane root = loader.load();

            final Scene scene = new Scene(root, WINDOW_WIDTH_MIN, WINDOW_HEIGHT_MIN);

            primaryStage.setMinHeight(WINDOW_HEIGHT_MIN);
            primaryStage.setMinWidth(WINDOW_WIDTH_MIN);
            primaryStage.setHeight(WINDOW_HEIGHT_INITIAL);
            primaryStage.setWidth(WINDOW_WIDTH_INITIAL);
            primaryStage.setScene(scene);
            primaryStage.setTitle("GymMan");

            // Super admin
            roleRepo.add(Role.builder()
                .name("super_admin")
                .permissions(new HashSet<>(authService.getRegisteredPermissions()))
                .build());
			employeeRepo.add(Employee.builder()
				.firstname("Gestore")
				.lastname("Gestore")
				.username("gestore")
				.password("admin123")
				.role("super_admin")
				.fiscalCode("ABCDEF99A67E123K")
				.birthdate(LocalDate.of(1990, 1, 1))
				.address("abc")
				.phone("+39 123 456 7890")
                .build());

			// addetti cassa
			final Role roleCassa = Role.builder()
		        .name("cassa")
		        .description("creazione di iscrizioni e clienti")
		        .build();
			roleCassa.addPermission(new PermissionImpl("page_dashboard"));
			roleCassa.addPermission(new PermissionImpl("page_login"));
			roleCassa.addPermission(new PermissionImpl("page_customers_list"));
			roleCassa.addPermission(new PermissionImpl("subscription_create"));
			roleCassa.addPermission(new PermissionImpl("page_customer_info"));
			roleCassa.addPermission(new PermissionImpl("subscription_delete"));
			roleCassa.addPermission(new PermissionImpl("page_customer_creation"));
			roleCassa.addPermission(new PermissionImpl("registration_create"));
			roleCassa.addPermission(new PermissionImpl("registration_edit"));
			roleCassa.addPermission(new PermissionImpl("customer_create"));
			roleCassa.addPermission(new PermissionImpl("registration_delete"));
			roleCassa.addPermission(new PermissionImpl("page_registrations_list"));
			roleCassa.addPermission(new PermissionImpl("page_registration_entry"));
			roleCassa.addPermission(new PermissionImpl("page_subscription_creation"));
			roleCassa.addPermission(new PermissionImpl("page_shift_calendar"));

			roleRepo.add(roleCassa);

			employeeRepo.add(Employee.builder()
			    .id("idmrossi")
                .firstname("Mario")
                .lastname("Rossi")
                .username("mrossi")
                .password("mrossi123")
                .role("cassa")
                .fiscalCode("ABCDEF99A67E123A")
                .birthdate(LocalDate.of(1990, 1, 1))
                .address("abc")
                .phone("+39 123 456 7890")
                .build());

			employeeRepo.add(Employee.builder()
		        .id("idlbianchi")
                .firstname("Luca")
                .lastname("Bianchi")
                .username("lbianchi")
                .password("lbianchi123")
                .role("cassa")
                .fiscalCode("ABCDEF99A67E123B")
                .birthdate(LocalDate.of(1990, 1, 1))
                .address("abc")
                .phone("+39 123 456 7890")
                .build());

			// tecnico attrezzi
			final Role roleTecnico = Role.builder()
	                .name("tecnico")
	                .description("gestione attrezzi")
	                .build();
			roleTecnico.addPermission(new PermissionImpl("page_dashboard"));
			roleTecnico.addPermission(new PermissionImpl("page_login"));
			roleTecnico.addPermission(new PermissionImpl("page_tool_list"));
			roleTecnico.addPermission(new PermissionImpl("page_tool_edit"));
			roleTecnico.addPermission(new PermissionImpl("create_tool"));
			roleTecnico.addPermission(new PermissionImpl("delete_tool"));
			roleTecnico.addPermission(new PermissionImpl("edit_tool"));

	        roleRepo.add(roleTecnico);

            employeeRepo.add(Employee.builder()
                .firstname("Giovanni")
                .lastname("Neve")
                .username("gneve")
                .password("gneve123")
                .role("tecnico")
                .fiscalCode("ABCDEF99A67E123C")
                .birthdate(LocalDate.of(1990, 1, 1))
                .address("abc")
                .phone("+39 123 456 7890")
                .build());

            // turni di lavoro
            workshiftRepo.add(WorkShift.builder()
                    .weekDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.THURSDAY))
                    .timeStart(LocalTime.of(8, 0))
                    .timeEnd(LocalTime.of(18, 30))
                    .employeeId("idmrossi")
                    .build());

            workshiftRepo.add(WorkShift.builder()
                    .weekDays(Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY))
                    .timeStart(LocalTime.of(9, 0))
                    .timeEnd(LocalTime.of(18, 00))
                    .employeeId("idlbianchi")
                    .build());

			// customer
			final Customer manfredonia = Customer.builder()
					.firstname("Daniele")
					.lastname("Manfredonia")
					.username("dManfre")
	                .password("manfredonia123")
					.gender(Gender.M)
					.birthdate(LocalDate.of(1998, 7, 10))
					.email("danimanfre@gmail.com")
					.telephoneNumber("3476589236")
					.fiscalCode("DNLMNF10F34M455F")
					.build();

			final Customer valentini = Customer.builder()
                    .firstname("Manuel")
                    .lastname("Valentini")
                    .username("mvale")
                    .password("mvale123")
                    .gender(Gender.M)
                    .birthdate(LocalDate.of(1999, 5, 07))
                    .email("manuvale@gmail.com")
                    .telephoneNumber("3208483790")
                    .fiscalCode("MNLVLT07F67M494F")
                    .build();

			final Customer mari = Customer.builder()
					.firstname("Mattia")
					.lastname("Mari")
					.username("mMari")
	                .password("manzi123")
					.gender(Gender.M)
					.birthdate(LocalDate.of(1996, 4, 9))
					.email("mattimari@gmail.com")
					.telephoneNumber("3317538295")
					.fiscalCode("MTAMRI53F86S315I")
					.build();

			final Customer manzi = Customer.builder()
					.firstname("Matteo")
					.lastname("Manzi")
					.username("mManzi")
                    .password("manzi123")
					.gender(Gender.M)
					.birthdate(LocalDate.of(1995, 5, 22))
					.email("matteomanz@gmail.com")
					.telephoneNumber("3476589236")
					.fiscalCode("MTOMAN34F67F325F")
					.build();

			final Customer vavusotto = Customer.builder()
					.firstname("Matteo")
					.lastname("Vavusotto")
					.username("mVavu")
					.password("vavus123")
					.gender(Gender.M)
					.birthdate(LocalDate.of(1996, 10, 20))
					.email("matteovavu@gmail.com")
					.telephoneNumber("3338537491")
					.fiscalCode("MTTVVS48K42H665V")
					.build();

			final Customer morigi = Customer.builder()
					.firstname("Tommaso")
					.lastname("Morigi")
					.username("tMor")
	                .password("morigi123")
					.gender(Gender.M)
					.birthdate(LocalDate.of(1998, 2, 8))
					.email("tommasomoro@gmail.com")
					.telephoneNumber("3297583268")
					.fiscalCode("TMSMRG48D84S234D")
					.build();

			final Customer lucchi = Customer.builder()
                    .firstname("Sara")
                    .lastname("Lucchi")
                    .username("sLucchi")
                    .password("lucchi123")
                    .gender(Gender.F)
                    .birthdate(LocalDate.of(1978, 5, 26))
                    .email("saralucchi@gmail.com")
                    .telephoneNumber("3456924640")
                    .fiscalCode("SARLCC43F80T943C")
                    .build();

			final Customer zanuccoli = Customer.builder()
                    .firstname("Giorgia")
                    .lastname("Zanuccoli")
                    .username("gZanu")
                    .password("zanuccoli123")
                    .gender(Gender.F)
                    .birthdate(LocalDate.of(1985, 11, 5))
                    .email("giorgiazanu@gmail.com")
                    .telephoneNumber("3314805613")
                    .fiscalCode("GRGZNC34K43L275K")
                    .build();

			customerRepo.add(manfredonia);
			customerRepo.add(valentini);
			customerRepo.add(mari);
			customerRepo.add(manzi);
			customerRepo.add(vavusotto);
			customerRepo.add(morigi);
			customerRepo.add(lucchi);
			customerRepo.add(zanuccoli);

			// subscription
			final SubscriptionType salaPesi = SubscriptionType.builder()
					.name("Sala pesi")
					.description("Sollevare più pesi possibile per aumentare la massa muscolare.")
					.unitPrice(12)
					.build();

			final SubscriptionType yoga = SubscriptionType.builder()
					.name("Yoga")
					.description("una disciplina per il corpo, la mente e l’anima che serve a farci vivere meglio.")
					.unitPrice(6)
					.build();

			final SubscriptionType pilates = SubscriptionType.builder()
					.name("Pilates")
					.description("Metodo di allenamento che insegna a prendere coscienza del proprio corpo,"
							+ " per rafforzarlo, correggere la postura "
							+ "e migliorare la fluidità e la precisione dei movimenti.")
					.unitPrice(10)
					.build();

			final SubscriptionType spinning = SubscriptionType.builder()
					.name("Spinning")
					.description("Gli allenamenti di spinning sono finalizzati principalmente"
							+ " ad allenare la resistenza generale ma,"
							+ " basandosi sulle variazioni di ritmo e sulle ripetute,"
							+ " richiedendo quindi un dispendio energetico considerevole.")
					.unitPrice(10)
					.build();

			final SubscriptionType heat = SubscriptionType.builder()
                    .name("Heat")
                    .description("Allenamento Aerobico ad Alta Energia.  "
                            + "L’obiettivo è quello di creare un training aerobico "
                            + "mirato al consumo di grassi e al potenziamento muscolare.")
                    .unitPrice(12)
                    .build();

			final SubscriptionType workout = SubscriptionType.builder()
                    .name("Intensive workout")
                    .description("Corso ad alta intensità, mix tra aerobico e "
                            + "tonificazione con interessamento di tutti i gruppi muscolari.")
                    .unitPrice(13)
                    .build();

			final SubscriptionType freestyler = SubscriptionType.builder()
                    .name("Freestyler")
                    .description("Allenamento a 360° con l’uso di fasce di resistenza"
                            + " che si allacciano alle quattro estremità del corpo"
                            + " e di una lastra di base antisdrucciolevole."
                            + " Crea una resistenza in tutte le direzioni possibili"
                            + " in modo che ogni movimento conta! È sicuro da usare."
                            + " È divertente e raggiunge i risultati di cui hai bisogno!")
                    .unitPrice(15)
                    .build();

			final SubscriptionType wbs = SubscriptionType.builder()
                    .name("WBS (wellBackSystem)")
                    .description("Ginnastica posturale, l’attrezzo permetto all’utente di"
                            + " assumere la posizione a “V” portando, in maniera rapida,"
                            + " al rilasciamento delle tensioni muscolari,"
                            + " rilassando il corpo nella sua globalità.")
                    .unitPrice(8)
                    .build();

			final SubscriptionType piloga = SubscriptionType.builder()
                    .name("Piloga")
                    .description("L’obiettivo del Piloga è la messa a punto di un protocollo"
                            + " di lavoro che esalta le caratteristiche di concentrazione"
                            + " dello yoga fondendole insieme a quelle più spiccatamente"
                            + " fisiche del Pilates. Un mix di movimenti specifici "
                            + "per un allenamento globale del corpo, senza dimenticare"
                            + " l’aspetto mentale.")
                    .unitPrice(10)
                    .build();

			final SubscriptionType swat = SubscriptionType.builder()
                    .name("SWAT")
                    .description("Con la sua altissima versatilità e una gamma di oltre"
                            + " 350 esercizi di forza, coordinazione, flessibilità e "
                            + "stabilità del tronco vengono allenati contemporaneamente"
                            + " moltissimi muscoli del corpo. È adatto a qualsiasi "
                            + "livello di allenamento: dagli appassionati di fitness,"
                            + " agli atleti amatoriali o atleti professionisti.")
                    .unitPrice(12)
                    .build();

			final SubscriptionType addome = SubscriptionType.builder()
                    .name("Addome & Posturalgym")
                    .description("E' un allenamento della muscolatura addominale"
                            + " con l’obiettivo di salvaguardare la colonna vertebrale"
                            + " a cui si affianca una parte di stretching mirata "
                            + "all’allungamento e alla flessibilità della muscolatura"
                            + " e delle articolazioni del corpo attraverso l’esecuzione"
                            + " di specifiche sequenze.")
                    .unitPrice(11)
                    .build();

			final SubscriptionType totalBody = SubscriptionType.builder()
                    .name("Total Body")
                    .description("Allenamento completo che combina tecniche di esercizio"
                            + " finalizzate al condizionamento muscolare, unitamente "
                            + "al miglioramento dell’attività cardiovascolare e alla"
                            + " forma estetica complessiva del corpo.")
                    .unitPrice(11)
                    .build();



			subscriptionTypeRepo.add(salaPesi);
			subscriptionTypeRepo.add(yoga);
			subscriptionTypeRepo.add(pilates);
			subscriptionTypeRepo.add(spinning);
			subscriptionTypeRepo.add(heat);
			subscriptionTypeRepo.add(workout);
			subscriptionTypeRepo.add(freestyler);
			subscriptionTypeRepo.add(wbs);
			subscriptionTypeRepo.add(piloga);
			subscriptionTypeRepo.add(swat);
			subscriptionTypeRepo.add(totalBody);
			subscriptionTypeRepo.add(addome);

			// additional service
			final AdditionalService sauna = AdditionalService.builder()
					.name("sauna")
					.description("contribuisce a migliorare ricambio e metabolismo."
							+ " La sauna disintossica in profondità e, quindi,"
							+ " rende la pelle luminosa e trasparente "
							+ "ed i tessuti maggiormente elastici.")
					.price(4)
					.build();

			final AdditionalService nuotoLibero = AdditionalService.builder()
			.name("nuoto libero")
			.description("sport completo che sollecita l’insieme "
					+ "dei muscoli del corpo e che sviluppa la resistenza.")
			.price(3)
			.build();

			final AdditionalService bibiteIllimitate = AdditionalService.builder()
					.name("bibite illimitate")
					.description("servizio che offre un numero"
							+ "illimitato di bevande che sono necessarie"
							+ "a manterenere il corpo idratato")
					.price(10)
					.build();

			final AdditionalService sedutaMassaggi = AdditionalService.builder()
					.name("seduta di massaggi")
					.description("servizio che garantisce due sedute di"
							+ "massaggi a settimana nelle giornate che preferisci.")
					.price(10)
					.build();



			serviceRepo.add(sauna);
			serviceRepo.add(nuotoLibero);
			serviceRepo.add(bibiteIllimitate);
			serviceRepo.add(sedutaMassaggi);


			// tool
            final Tool panca = Tool.builder()
                    .name("Panca")
                    .desc("Usata per sollevare pesi")
                    .numseriale("ASD25ASGG")
                    .maintenance(12)
                    .build();

            final Tool tapisroulant = Tool.builder()
                    .name("Tapis Roulant")
                    .desc(" Un regolare allenamento si possono percepire miglioramenti dell’apparato cardio-circolatorio, "
                            + "della muscolatura delle gambe e dei glutei nonché un aumento della resistenza generale")
                    .numseriale("TPSR213")
                    .maintenance(12)
                    .build();

            final Tool elastici = Tool.builder()
                    .name("Elastici")
                    .desc("Finalizzati all'uso di allenamenti total body ")
                    .numseriale("LSTC23")
                    .maintenance(1)
                    .build();

            final Tool vogatore = Tool.builder()
                    .name("Vogatore")
                    .desc("Utile per l'allenamento per le braccia")
                    .numseriale("VGTR213")
                    .maintenance(4)
                    .build();

            toolrepository.add(panca);
            toolrepository.add(tapisroulant);
            toolrepository.add(elastici);
            toolrepository.add(vogatore);


			// registration
			registrationRepo.add(TermRegistration.builder()
					.idClient(manfredonia.getId())
					.duration(6)
					.type(salaPesi)
					.discount(20)
					.additionalService(Arrays.asList(sauna, bibiteIllimitate))
					.signingDate(LocalDate.of(2021, 01, 01))
					.build());

			registrationRepo.add(TermRegistration.builder()
					.idClient(manzi.getId())
					.duration(2)
					.type(freestyler)
					.discount(5)
					.additionalService(Arrays.asList(nuotoLibero, bibiteIllimitate))
					.signingDate(LocalDate.of(2021, 01, 03))
					.build());

			registrationRepo.add(TermRegistration.builder()
					.idClient(mari.getId())
					.duration(1)
					.type(salaPesi)
					.discount(0)
					.additionalService(Arrays.asList(sedutaMassaggi))
					.signingDate(LocalDate.of(2021, 01, 03))
					.build());

			registrationRepo.add(TermRegistration.builder()
					.idClient(vavusotto.getId())
					.duration(7)
					.type(heat)
					.discount(30)
					.additionalService(Arrays.asList(sauna, sedutaMassaggi))
					.signingDate(LocalDate.of(2021, 01, 03))
					.build());

			registrationRepo.add(NumberedRegistration.builder()
					.idClient(manfredonia.getId())
					.maxEntries(20)
					.type(salaPesi)
					.discount(20)
					.additionalService(Arrays.asList(sauna))
					.build());

			registrationRepo.add(NumberedRegistration.builder()
					.idClient(manzi.getId())
					.maxEntries(15)
					.type(yoga)
					.discount(20)
					.additionalService(Arrays.asList(bibiteIllimitate))
					.build());

			registrationRepo.add(NumberedRegistration.builder()
					.idClient(mari.getId())
					.maxEntries(50)
					.type(pilates)
					.discount(8)
					.additionalService(Arrays.asList(nuotoLibero))
					.build());

			registrationRepo.add(NumberedRegistration.builder()
                    .idClient(lucchi.getId())
                    .maxEntries(100)
                    .type(wbs)
                    .discount(20)
                    .additionalService(null)
                    .build());

			registrationRepo.add(TermRegistration.builder()
                    .idClient(zanuccoli.getId())
                    .duration(20)
                    .type(addome)
                    .discount(10)
                    .additionalService(null)
                    .signingDate(LocalDate.of(2021, 05, 10))
                    .build());

			registrationRepo.add(TermRegistration.builder()
                    .idClient(valentini.getId())
                    .duration(12)
                    .type(piloga)
                    .discount(15)
                    .additionalService(Arrays.asList(sauna,sedutaMassaggi))
                    .signingDate(LocalDate.of(2022, 01, 03))
                    .build());

			registrationRepo.add(TermRegistration.builder()
                    .idClient(valentini.getId())
                    .duration(12)
                    .type(totalBody)
                    .discount(15)
                    .additionalService(Arrays.asList(sauna))
                    .signingDate(LocalDate.of(2022, 01, 03))
                    .build());

			registrationRepo.add(TermRegistration.builder()
                    .idClient(zanuccoli.getId())
                    .duration(20)
                    .type(swat)
                    .discount(10)
                    .additionalService(null)
                    .signingDate(LocalDate.of(2021, 05, 10))
                    .build());



            primaryStage.show();
            appController.onDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }

    public static void main(final String[] args) {
        launch(args);
    }
}
