package repository;

import exceptions.PatronNotFoundException;
import models.Patron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPatronRepository implements PatronRepository {
    final private Map<String, Patron> patrons;

    public InMemoryPatronRepository() {
        patrons = new HashMap<>();
    }

    @Override
    public void addPatron(Patron patron) {
        Patron newPatron = new Patron(patron);
        patrons.put(patron.getPatronId(), newPatron);
    }

    @Override
    public void updatePatron(Patron patron) throws PatronNotFoundException {
        Patron matchingPatron = patrons.get(patron.getPatronId());
        if(matchingPatron == null) {
            throw new PatronNotFoundException("Patron with id " + patron.getPatronId() + " not found");
        }
        matchingPatron.setPatronName(patron.getPatronName());
        matchingPatron.setPatronContact(patron.getPatronContact());
        matchingPatron.setPatronAddress(patron.getPatronAddress());
    }

    @Override
    public Patron findById(String id) throws PatronNotFoundException {
        Patron matchingPatron = patrons.get(id);
        if(matchingPatron == null) {
            throw new PatronNotFoundException("Patron with id " + id + " not found");
        }
        return new Patron(matchingPatron);
    }

    @Override
    public List<Patron> findAllPatrons() {
        List<Patron> patronsList = new ArrayList<>();
        for(Patron patron : patrons.values()) {
            patronsList.add(new Patron(patron));
        }
        return patronsList;
    }
}
