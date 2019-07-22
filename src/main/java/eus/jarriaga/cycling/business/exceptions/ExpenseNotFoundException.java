package eus.jarriaga.cycling.business.exceptions;

import eus.jarriaga.cycling.business.entities.Expense;

public class ExpenseNotFoundException extends ItemNotFoundException {
    public ExpenseNotFoundException(){}

    public ExpenseNotFoundException(Long id) {
        super(Expense.class.getSimpleName(),id);
    }

}
