package eus.jarriaga.cycling.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eus.jarriaga.cycling.business.entities.*;
import eus.jarriaga.cycling.business.exceptions.UserNotFoundException;
import eus.jarriaga.cycling.business.propertyeditors.ClientEditor;
import eus.jarriaga.cycling.business.propertyeditors.DurationEditor;
import eus.jarriaga.cycling.business.propertyeditors.ProjectEditor;
import eus.jarriaga.cycling.business.propertyeditors.RoleEditor;
import eus.jarriaga.cycling.business.repositories.*;
import eus.jarriaga.cycling.utils.ProjectDatesCalculator;
import eus.jarriaga.cycling.utils.ReportDatesCalculator;
import eus.jarriaga.cycling.utils.ReportExpenseDatesCalculator;
import eus.jarriaga.imputador.business.entities.*;
import eus.jarriaga.imputador.business.repositories.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Controller
public class WebController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private WorkPartRepository workPartRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectOperationBudgetRepository projectOperationBudgetRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GeneralConfigurationRepository generalConfigurationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void basicContext(Model model, Principal principal) {
        if (principal != null) {
            User user = userRepository.findUserByEmail(principal.getName());
            model.addAttribute("userId", user.getId());
            //model.addAttribute("username", principal.getName());
            model.addAttribute("username", user.getName());
            model.addAttribute("userrol", user.getRole().getName());
        }
    }

    private void basicContext(Model model, Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
        model.addAttribute("userId",user.getId());
        //model.addAttribute("username",user.getEmail());
        model.addAttribute("username",user.getName());
        model.addAttribute("userrol",user.getRole().getName());
    }

    @RequestMapping(value = "/login_userlist.html", method = RequestMethod.GET)
    public ModelAndView userList() {
        Map<String, Object> myModel = new HashMap<>();
        //myModel.put("users", userRepository.findAll());
        myModel.put("users", userRepository.findAllByEnabledOrderByEmail(true));

        return new ModelAndView("login_userlist", "model", myModel);
    }

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest request, Principal principal) {
        Map<String, Object> myModel = getMapWithUser(request, principal);
        return new ModelAndView("index", "model", myModel);
    }

    @RequestMapping(value="/login.html")
    public ModelAndView handleRequest(HttpServletRequest request, Model model,Principal principal) {
        Map<String, Object> myModel = new HashMap<String, Object>();
        String username = request.getParameter("username");
        User user = userRepository.findUserByEmail(username);
        model.addAttribute("user", user);
        return new ModelAndView("login", "model", myModel);
    }

    @RequestMapping(value = "/home.html", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, Principal principal) {
        Map<String, Object> myModel = getMapWithUser(request, principal);
        return new ModelAndView("home", "model", myModel);
    }

    @GetMapping(value="/console.html")
    public String console(Model model, Principal principal) {
        //model.addAttribute("project",new Project());
        /*
        User user = userRepository.findUserByEmail(principal.getName());
        model.addAttribute("username",principal.getName());
        model.addAttribute("userrol",user.getRole().getName());
        */
        basicContext(model, principal);
        return "console";
    }

    @GetMapping(value="/clients.html")
    public String clients(Model model, Principal principal) {
        basicContext(model, principal);
        //model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("clients", clientRepository.findAllByOrderByName());
        //model.addAttribute("username", principal.getName());

        return "clients";
    }

    @GetMapping(value={"/client/form", "/client/form/{id}"})
    public String clientForm(Model model, @PathVariable(name = "id", required = false) Long id, Principal principal) {
        basicContext(model, principal);
        if (id == null) {
            model.addAttribute("client", new Client());
        }
        else {
            model.addAttribute("client", clientRepository.findById(id));
        }
        return "forms/client";
    }

    @PostMapping(value="/client/submit")
    public String clientForm(@Valid @ModelAttribute("client") Client client, BindingResult result, Model model, Principal principal, RedirectAttributes ra) {
        basicContext(model, principal);
        if (! result.hasErrors()) {
            clientRepository.save(client);
        }
        model.addAttribute("clients", clientRepository.findAll());
        return "redirect:/clients.html";
    }

    @GetMapping(value="/operations.html")
    //public String operations(Model model, Principal principal) {
    public String operations(Model model, Principal principal) {
        basicContext(model, principal);
        //model.addAttribute("operations", operationRepository.findAll());
        model.addAttribute("operations", operationRepository.findAllByOrderByName());
        //model.addAttribute("username", principal.getName());

        return "operations";
    }

    @GetMapping(value={"/operation/form", "/operation/form/{id}"})
    public String operationForm(Model model, @PathVariable(name = "id", required = false) Long id, Principal principal) {
        basicContext(model, principal);
        if (id == null) {
            model.addAttribute("operation", new Operation());
        }
        else {
            model.addAttribute("operation", operationRepository.findById(id));
        }
        return "forms/operation";
    }

    @PostMapping(value="/operation/submit")
    public String operationForm(@Valid @ModelAttribute("operation") Operation operation, BindingResult result, Model model, Principal principal, RedirectAttributes ra) {
        basicContext(model, principal);
        if (! result.hasErrors()) {
            operationRepository.save(operation);
        }
        model.addAttribute("operations", operationRepository.findAll());
        return "redirect:/operations.html";
    }

    @GetMapping(value="/workparts.html")
    public String workparts(Model model, Principal principal) {
        basicContext(model, principal);
        return "workparts";
    }

    @GetMapping(value="/projects.html")
    public String projects(Model model, Principal principal) {
        basicContext(model, principal);
        //model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("projects", projectRepository.findAllByOrderByName());
        //model.addAttribute("username", principal.getName());

        return "projects";
    }

    @GetMapping(value={"/project/form", "/project/form/{id}"})
    public String projectForm(Model model, @PathVariable(name = "id", required = false) Long id, Principal principal) {
        basicContext(model, principal);
        if (id == null) {
            model.addAttribute("newProject", "S");
            model.addAttribute("project", new Project());
        }
        else {
            model.addAttribute("newProject", "N");
            //model.addAttribute("project", projectRepository.findById(id));
            model.addAttribute("project", projectRepository.findById(id).get());
        }

        Iterable<Operation> operationList = operationRepository.findAll();
        Iterable<ProjectOperationBudget> projectOperationBudgetList = projectOperationBudgetRepository.findByProjectId(id);
        //if (operationList.)
        Boolean operationAvailable = true;
        if (operationList instanceof Collection) {
            if (((Collection<?>) operationList).size() == ((Collection<?>) projectOperationBudgetList).size()) {
                operationAvailable = false;
            }
        }

        if (operationAvailable) {
            model.addAttribute("operationAvailable", "S");
        }
        else {
            model.addAttribute("operationAvailable", "N");
        }

        ProjectDatesCalculator projectCalculator = new ProjectDatesCalculator(workPartRepository, projectRepository, id);

        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("operationsBudgets", projectOperationBudgetRepository.findByProjectId(id));
        model.addAttribute("workparts", projectCalculator.getParts());

        User user = userRepository.findUserByEmail(principal.getName());
        model.addAttribute("user", user);


        //model.addAttribute("projects", projectRepository.findAll());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            model.addAttribute("projects", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projectRepository.findAll()));
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            model.addAttribute("projects",null);
        }


        return "forms/project";
    }

    @PostMapping(value="/project/submit")
    public String projectSubmit(Model model, @Valid @ModelAttribute("project") Project project, BindingResult result, Principal principal, RedirectAttributes ra) {
        basicContext(model, principal);
        if (! result.hasErrors()) {
            projectRepository.save(project);
        }
        model.addAttribute("projects", projectRepository.findAll());

        logger.info("projectDate: " + project.getDischargeDate());
        return "redirect:/projects.html";
    }

    @GetMapping(value={"/project/{projectid}/operationbudget/form", "/project/{projectid}/operationbudget/form/{operationid}"})
    public String projectOperationBudget(Model model, @PathVariable(name = "projectid", required = false) Long projectId, @PathVariable(name = "operationid", required = false) Long operationId, Principal principal, RedirectAttributes ra) {
        basicContext(model, principal);

        Iterable<Operation> operationList = operationRepository.findAll();
        Iterable<ProjectOperationBudget> projectOperationBudgetList = projectOperationBudgetRepository.findByProjectId(projectId);

        Iterator iOperationList = operationList.iterator();
        while (iOperationList.hasNext()) {
            Long iOperationId = ((Operation) iOperationList.next()).getId();
            if (iOperationId != operationId) {
                for (ProjectOperationBudget projectOperationBudget : projectOperationBudgetList) {
                    if (projectOperationBudget.getOperation().getId() == iOperationId) {
                        iOperationList.remove();
                        break;
                    }
                }
            }
        }
        model.addAttribute("operations", operationList);
        if (operationId == null) {
            ProjectOperationBudget projectOperationBudget = new ProjectOperationBudget();
            Project project = projectRepository.findById(projectId).get();
            projectOperationBudget.setProject(project);
            model.addAttribute("projectoperationbudget", projectOperationBudget);
        }
        else {
            model.addAttribute("projectoperationbudget", projectOperationBudgetRepository.findByProjectIdAndOperationId(projectId, operationId));
        }
        return "forms/projectoperationbudget";
    }

    @PostMapping(value="/projectoperationbudget/submit")
    public String projectOperationBudgetSubmit(Model model, @Valid @ModelAttribute("projectoperationbudget") ProjectOperationBudget projectOperationBudget, BindingResult result, Principal principal, RedirectAttributes ra) {
        basicContext(model, principal);
        if (! result.hasErrors()) {
            projectOperationBudgetRepository.save(projectOperationBudget);
        }
        model.addAttribute("projectOperationBudgets", projectRepository.findAll());

        //logger.info(": " + "");
        return "redirect:/project/form/" + projectOperationBudget.getProject().getId();
    }
    @GetMapping(value="/users.html")
    public String users(Model model, Principal principal) {
        basicContext(model, principal);
        //model.addAttribute("users", userRepository.findAll());
        model.addAttribute("users", userRepository.findAllByOrderByName());
        //model.addAttribute("username", principal.getName());

        return "users";
    }

    @GetMapping(value={"/user/form", "/user/form/{id}"})
    public String userForm(Model model, @PathVariable(name = "id", required = false) Long id, Principal principal) {
        basicContext(model, principal);
        if (id == null) {
            model.addAttribute("user", new User());
        }
        else {
            model.addAttribute("user", userRepository.findById(id));
        }
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("prueba", "probat bat");

        return "forms/user";
    }

    @PostMapping(value="/user/submit")
    public String userSubmit(Model model, @Valid @ModelAttribute("user") User user, BindingResult result, Principal principal, RedirectAttributes ra) {
        basicContext(model, principal);
        if (! result.hasErrors()) {
            if (user.getId() != null) {
                if ((user.getPassword() == null) || (user.getPassword() == "")) {
                    User originalUser = userRepository.findById(user.getId()).get();
                    user.setPassword(originalUser.getPassword());
                }
                else {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            }
            else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            user.setEnabled(true);
            userRepository.save(user);
        }
        model.addAttribute("users", userRepository.findAll());

        return "redirect:/users.html";
    }


    @GetMapping(value="/reports")
    public String reportsPage(Model model, Principal principal, @RequestParam(value = "type", required = false, defaultValue = ReportDatesCalculator.REPORT_TYPE_MONTH) String reportTimeType,
                              @RequestParam(value = "current", required = false) String current,
                              @RequestParam(value = "year", required = false) String year,
                              @RequestParam(value = "from", required = false) String from,
                              @RequestParam(value = "to", required = false) String to/*, Locale locale*/) {
        basicContext(model, principal);
        ReportDatesCalculator calculator = new ReportDatesCalculator(workPartRepository, reportTimeType, current, from, to, year);
        int currentNumber = 0;
        if (current != null) currentNumber = Integer.valueOf(current);
        if (current == null && reportTimeType.equals(ReportDatesCalculator.REPORT_TYPE_MONTH)) {
            current = calculator.getFrom().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            currentNumber = calculator.getFrom().getMonthValue();
            year = String.valueOf(calculator.getFrom().getYear());
        } else if (current == null && reportTimeType.equals(ReportDatesCalculator.REPORT_TYPE_YEAR)) {
            current = String.valueOf(calculator.getFrom().getYear());
            currentNumber = calculator.getFrom().getYear();
            year = null;
        } else if (current == null && reportTimeType.equals(ReportDatesCalculator.REPORT_TYPE_CUSTOM) && from != null && to != null) {
            //current = from + " / " + to;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-y");

            current = LocalDate.parse(from).format(formatter).toString() + " / " + LocalDate.parse(to).format(formatter).toString();
        }
        if (current != null && reportTimeType.equals(ReportDatesCalculator.REPORT_TYPE_MONTH)) {
            current = LocalDate.now().withMonth(currentNumber).getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            year = String.valueOf(calculator.getFrom().getYear());
        }

        model.addAttribute("parts",calculator.getJSONParts());
        model.addAttribute("current",current);
        model.addAttribute("year",year);
        model.addAttribute("currentNumber",currentNumber);
        model.addAttribute("from",from);
        model.addAttribute("to",to);
        model.addAttribute("type",reportTimeType);

        return "reports";
    }

    @GetMapping(value="/reportsExpenses")
    public String reportsExpensesPage(Model model, Principal principal, @RequestParam(value = "type", required = false, defaultValue = ReportDatesCalculator.REPORT_TYPE_MONTH) String reportTimeType,
                              @RequestParam(value = "current", required = false) String current,
                              @RequestParam(value = "year", required = false) String year,
                              @RequestParam(value = "from", required = false) String from,
                              @RequestParam(value = "to", required = false) String to,
                              @RequestParam(value = "filterUserId", required = false) String filterUserId
                                      , @RequestParam(value = "filterProjectId", required = false) String filterProjectId
            /*, Locale locale*/) {
        basicContext(model, principal);
        ReportExpenseDatesCalculator calculator = new ReportExpenseDatesCalculator(expenseRepository, userRepository, projectRepository, reportTimeType, current, from, to, year, filterUserId, filterProjectId);
        int currentNumber = 0;
        if (current != null) currentNumber = Integer.valueOf(current);
        if (current == null && reportTimeType.equals(ReportDatesCalculator.REPORT_TYPE_MONTH)) {
            current = calculator.getFrom().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            currentNumber = calculator.getFrom().getMonthValue();
            year = String.valueOf(calculator.getFrom().getYear());
        } else if (current == null && reportTimeType.equals(ReportDatesCalculator.REPORT_TYPE_YEAR)) {
            current = String.valueOf(calculator.getFrom().getYear());
            currentNumber = calculator.getFrom().getYear();
            year = null;
        } else if (current == null && reportTimeType.equals(ReportDatesCalculator.REPORT_TYPE_CUSTOM) && from != null && to != null) {
            //current = from + " / " + to;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-y");

            current = LocalDate.parse(from).format(formatter).toString() + " / " + LocalDate.parse(to).format(formatter).toString();
        }
        if (current != null && reportTimeType.equals(ReportDatesCalculator.REPORT_TYPE_MONTH)) {
            current = LocalDate.now().withMonth(currentNumber).getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            year = String.valueOf(calculator.getFrom().getYear());
        }

        model.addAttribute("expenses",calculator.getJSONExpenses());
        model.addAttribute("current",current);
        model.addAttribute("year",year);
        model.addAttribute("currentNumber",currentNumber);
        model.addAttribute("from",from);
        model.addAttribute("to",to);
        model.addAttribute("type",reportTimeType);
        model.addAttribute("filterUserId",filterUserId);
        model.addAttribute("users",userRepository.findAllByOrderByName());
        model.addAttribute("filterProjectId",filterProjectId);
        model.addAttribute("projects",projectRepository.findAllByOrderByName());

        return "reportsExpenses";
    }


    ///reports/excel
    @GetMapping(value="/reportsprueba")
    public String reportsPage() {
        return "";
    }

    @GetMapping(value = "/workparts/{userId}")
    public String workPartsForm(Model model, Principal principal, RedirectAttributes ra,@PathVariable(value = "userId") Long userId,
                            @RequestParam(value="date", required = false) String date) throws UserNotFoundException{

        basicContext(model, userId);
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));

        User userValidate = userRepository.findUserByEmail(principal.getName());

        if (userValidate.getRole().getName().equals("USER")) {
            if (user.getId() != userValidate.getId()) {
                return "redirect:/workparts/" + userValidate.getId();
            }
        }

        LocalDate localDate = LocalDate.now();
        if (date != null) {
            localDate = LocalDate.parse(date);
        }

        List<WorkPart> parts = workPartRepository.findAllByUserAndDateBetween(user,localDate,localDate);
        model.addAttribute("parts",parts);
        ObjectMapper mapper = new ObjectMapper();
        try {
            model.addAttribute("JSONparts",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(parts));
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        Iterable<Client> clients = clientRepository.findAll();
        Iterator<Client> itClient = clients.iterator();
        while (itClient.hasNext()) {
            Client client = itClient.next();
            Iterable<Project> tmpProjects = projectRepository.findAllByClientOrderByName(client);
            Iterator<Project> itTmpProjects = tmpProjects.iterator();
            if (! itTmpProjects.hasNext()) {
                itClient.remove();
            }
        }
        //model.addAttribute("clients",clientRepository.findAll());
        model.addAttribute("clients",clients);
        //model.addAttribute("projects",projectRepository.findAll());
        model.addAttribute("projects",projectRepository.findByArchivedOrderByName(false));
        Iterable<Project> projects = projectRepository.findByArchivedOrderByName(false);
        mapper = new ObjectMapper();
        try {
            model.addAttribute("JSONprojects",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projects));
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        //model.addAttribute("operations",operationRepository.findAll());
        model.addAttribute("operations",operationRepository.findAllByOrderByName());
        WorkPart workPart = new WorkPart();
        workPart.setUser(user);
        workPart.setDate(localDate);
        model.addAttribute("workpart",workPart);
        model.addAttribute("selectedDate",localDate);
        model.addAttribute("previousDate",localDate.minusDays(1));
        model.addAttribute("nextDate",localDate.plusDays(1));
        return "forms/workparts";
    }

    @PostMapping(value = "/workparts/submit")
    public String workPartsSubmit(@Valid @ModelAttribute("workpart") WorkPart workpart, BindingResult result){
        if (! result.hasErrors()) {
            workPartRepository.save(workpart);
        }
        //String redirigir = "redirect:/forms/workparts/"+workpart.getUser().getId();
        //String redirigir = "/forms/workparts/"+workpart.getUser().getId();
        String redirigir = "redirect:/workparts/"+workpart.getUser().getId()+"?date="+workpart.getDate().toString();
        return redirigir;
    }

    @GetMapping(value="/expenses.html")
    public String expenses(Model model, Principal principal) {
        basicContext(model, principal);
        return "expenses";
    }

    @GetMapping(value = "/expenses/{userId}")
    public String expensesForm(Model model, Principal principal, RedirectAttributes ra,@PathVariable(value = "userId") Long userId,
                                @RequestParam(value="date", required = false) String date) throws UserNotFoundException{

        basicContext(model, userId);
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));

        User userValidate = userRepository.findUserByEmail(principal.getName());

        if (userValidate.getRole().getName().equals("USER")) {
            if (user.getId() != userValidate.getId()) {
                return "redirect:/expenses/" + userValidate.getId();
            }
        }

        LocalDate localDate = LocalDate.now();
        if (date != null) {
            localDate = LocalDate.parse(date);
        }

        List<Expense> expenses = expenseRepository.findAllByUserAndDateBetween(user,localDate,localDate);
        model.addAttribute("expenses",expenses);
        ObjectMapper mapper = new ObjectMapper();
        try {
            model.addAttribute("JSONexpenses",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenses));
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        model.addAttribute("projects",projectRepository.findByArchivedOrderByName(false));
        model.addAttribute("operations",operationRepository.findAllByOrderByName());
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setDate(localDate);
        model.addAttribute("expense",expense);
        model.addAttribute("selectedDate",localDate);
        model.addAttribute("previousDate",localDate.minusDays(1));
        model.addAttribute("nextDate",localDate.plusDays(1));
        model.addAttribute("generalConfigurations",generalConfigurationRepository.findAll());

        Iterable<GeneralConfiguration> generalConfigurations = generalConfigurationRepository.findAll();
        model.addAttribute("generalConfigurations",generalConfigurations);
        mapper = new ObjectMapper();
        try {
            model.addAttribute("JSONgeneralConfigurations", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generalConfigurations));
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return "forms/expenses";
    }

    @PostMapping(value = "/expenses/submit")
    public String expensesSubmit(@Valid @ModelAttribute("expense") Expense expense, BindingResult result){
        if (! result.hasErrors()) {
            expenseRepository.save(expense);
        }

        String redirigir = "redirect:/expenses/"+expense.getUser().getId()+"?date="+expense.getDate().toString();
        return redirigir;
    }

    @GetMapping(value="/consoleUser.html")
    public String consoleUser(Model model, Principal principal) {
        basicContext(model, principal);
        return "console-user";
    }

    @GetMapping(value="/generalConfigurations.html")
    //public String generalConfigurations(Model model, Principal principal) {
    public String generalConfigurations(Model model, Principal principal) {
        basicContext(model, principal);

        model.addAttribute("generalConfigurations", generalConfigurationRepository.findAllByOrderById());

        return "generalConfigurations";
    }

    @GetMapping(value={"/generalConfiguration/form", "/generalConfiguration/form/{id}"})
    public String generalConfigurationForm(Model model, @PathVariable(name = "id", required = false) Long id, Principal principal) {
        basicContext(model, principal);
        if (id == null) {
            model.addAttribute("generalConfiguration", new GeneralConfiguration());
        }
        else {
            model.addAttribute("generalConfiguration", generalConfigurationRepository.findById(id));
        }
        return "forms/generalConfiguration";
    }

    @PostMapping(value="/generalConfiguration/submit")
    public String generalConfigurationForm(@Valid @ModelAttribute("generalConfiguration") GeneralConfiguration generalConfiguration, BindingResult result, Model model, Principal principal, RedirectAttributes ra) {
        basicContext(model, principal);
        if (! result.hasErrors()) {
            generalConfigurationRepository.save(generalConfiguration);
        }
        model.addAttribute("generalConfigurations", generalConfigurationRepository.findAll());
        return "redirect:/generalConfigurations.html";
    }


    private Map<String, Object> getMapWithUser(HttpServletRequest request, Principal principal) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null && principal != null) {
            user = userRepository.findUserByEmail(principal.getName());
            request.getSession().setAttribute("user", user);
        }
        Map<String, Object> myModel = new HashMap<>();
        myModel.put("user", user);
        return myModel;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(200000000);
        return multipartResolver;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Client.class, new ClientEditor(clientRepository));
        binder.registerCustomEditor(Role.class, new RoleEditor(roleRepository));
        binder.registerCustomEditor(Duration.class, new DurationEditor());
        binder.registerCustomEditor(Project.class, new ProjectEditor(projectRepository));
    }

}
