package eus.jarriaga.cycling.business.repositories;

import eus.jarriaga.cycling.business.entities.Expense;
import eus.jarriaga.cycling.business.entities.Project;
import eus.jarriaga.cycling.business.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense,Long> {

    List<Expense> findAllByDateBetween(LocalDate from, LocalDate to);

    List<Expense> findAllByDateBetweenOrderByDate(LocalDate from, LocalDate to);

    List<Expense> findAllByDateBetweenOrderByDateAscUserNameAsc(LocalDate from, LocalDate to);

    List<Expense> findAllByUserAndDateBetween(User user, LocalDate from, LocalDate to);

    List<Expense> findAllByUserAndDateBetweenOrderByDate(User user, LocalDate from, LocalDate to);

    List<Expense> findAllByProjectAndDateBetweenOrderByDate(Project project, LocalDate from, LocalDate to);

    List<Expense> findAllByUserAndProjectAndDateBetweenOrderByDate(User user, Project project, LocalDate from, LocalDate to);

}
