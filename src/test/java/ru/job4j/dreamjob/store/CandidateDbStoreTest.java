package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CandidateDbStoreTest {
    @Test
    public void whenCreatePost() {
        BasicDataSource basicDataSource = new Main().loadPool();
        CandidateDbStore store = new CandidateDbStore(basicDataSource);
        Candidate candidate = new Candidate(0, "Test", "Test", LocalDate.now());
        store.add(candidate);
        Collection<Candidate> all = store.findAllCandidates();
        Candidate candidateInDb = store.findById(candidate.getId());
        assertEquals(candidateInDb.getName(), (candidateInDb.getName()));
    }
}