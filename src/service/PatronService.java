package service;

import exceptions.DuplicatePatronException;
import exceptions.PatronNotFoundException;
import models.Patron;
import repository.PatronRepository;

import java.util.List;
import java.util.logging.Logger;

public class PatronService {
    private final PatronRepository patronRepository;
    private final static Logger LOGGER = Logger.getLogger(PatronService.class.getName());

    public  PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public void addPatron(Patron patron) throws DuplicatePatronException {
        // check if patron already exists
        try {
            patronRepository.findById(patron.getPatronId());
            throw new DuplicatePatronException("Duplicate patron found");
        } catch (PatronNotFoundException e) {
            // not found; add patron
            patronRepository.addPatron(patron);
            LOGGER.info("Patron with ID " + patron.getPatronId() + " was added successfully");
        }
    }

    public void updatePatron(Patron patron) throws PatronNotFoundException {
        patronRepository.updatePatron(patron);
        LOGGER.info("Patron with ID " + patron.getPatronId() + " updated successfully");
    }

    public Patron findByPatronId(String patronId) throws PatronNotFoundException {
        return patronRepository.findById(patronId);
    }

    public List<Patron> findAllPatrons() {
        return patronRepository.findAllPatrons();
    }
}
