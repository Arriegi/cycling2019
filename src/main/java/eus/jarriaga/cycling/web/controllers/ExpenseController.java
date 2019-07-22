package eus.jarriaga.cycling.web.controllers;

import eus.jarriaga.cycling.business.entities.Expense;
import eus.jarriaga.cycling.business.exceptions.ExpenseNotFoundException;
import eus.jarriaga.cycling.business.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ExpenseController {
    @Autowired
    private ExpenseRepository expenseRepository;

    private final Log logger = LogFactory.getLog(getClass());

    @GetMapping("/expenses")
    public List<Expense> getWorkParts(Principal principal) {
        if (principal != null) {
            logger.info("PRINCIPAL: " + principal.getName());
        }
        return (List<Expense>) expenseRepository.findAll();
    }

    @PostMapping("/expenses")
    public Expense newExpense(@RequestBody Expense newExpense) throws ExpenseNotFoundException {
        return expenseRepository.save(newExpense);
    }

    @GetMapping("/expenses/{id}")
    public Expense getWorkPart(@PathVariable Long id) throws ExpenseNotFoundException {
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException(id));
    }

    @GetMapping("/expenses/from/{from}/to/{to}")
    public List<Expense> getExpensesBetweenDates(@PathVariable("from") String from, @PathVariable("to") String to) {
        return expenseRepository.findAllByDateBetween(LocalDate.parse(from),LocalDate.parse(to));
    }

    @PutMapping("/expenses/{id}")
    public Expense replaceOperation(@RequestBody Expense newExpense, @PathVariable Long id)
            throws ExpenseNotFoundException {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException(id));
        //Optional.ofNullable(newExpense.getDuration()).ifPresent((duration) -> expense.setDuration(duration));
        return expenseRepository.save(expense);
    }

    @DeleteMapping("/expenses/{id}")
    public void deleteExpense(@PathVariable Long id) {

        expenseRepository.deleteById(id);
    }

}
