package repository;

import exceptions.PatronNotFoundException;
import models.Patron;

import java.util.List;

public interface PatronRepository {
    void addPatron(Patron patron);
    void updatePatron(Patron patron) throws PatronNotFoundException;
    Patron findById(String id) throws PatronNotFoundException;
    List<Patron> findAllPatrons();
}
