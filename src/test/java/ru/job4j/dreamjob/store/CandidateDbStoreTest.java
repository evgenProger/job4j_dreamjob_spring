package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CandidateDbStoreTest {
    BasicDataSource basicDataSource;

    @Before
    public void inition() throws SQLException {
        basicDataSource = new Main().loadPool();
        basicDataSource.getConnection().prepareStatement("delete from candidate").execute();
    }

    @Test
    public void whenCreateCandidate() {
        CandidateDbStore store = new CandidateDbStore(basicDataSource);
        Candidate candidate = new Candidate(0, "Test", "Test", LocalDate.now());
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertEquals(candidateInDb.getName(), (candidateInDb.getName()));
    }

    @Test
    public void whenFindByIdThenGetPost() {
        CandidateDbStore store = new CandidateDbStore(basicDataSource);
        CityDbStore cityDbStore = new CityDbStore(basicDataSource);
        Candidate candidate = new Candidate(0, "Test", "Test", LocalDate.now());
        Candidate candidateTwo = new Candidate(1, "TestTwo", "TestTwo", LocalDate.now());
        store.add(candidate);
        store.add(candidateTwo);
        assertEquals(store.findById(candidate.getId()), candidate);
        assertEquals(store.findById(candidateTwo.getId()), candidateTwo);
        assertNull(store.findById(-1));
    }

    @Test
    public void whenFindAllPostsThenList() {
        CandidateDbStore store = new CandidateDbStore(basicDataSource);
        Candidate candidate = new Candidate(0, "Test", "Test", LocalDate.now());
        Candidate candidateTwo = new Candidate(1, "TestTwo", "TestTwo", LocalDate.now());
        store.add(candidate);
        store.add(candidateTwo);
        Collection<Candidate> candidates = store.findAllCandidates();
        assertTrue(candidates.containsAll(List.of(candidate, candidateTwo)));
        assertEquals(2, candidates.size());
    }

    @Test
    public void whenUpdateCandidateThenNewCandidate() {
        CandidateDbStore store = new CandidateDbStore(basicDataSource);
        Candidate candidateTwo = new Candidate(1, "TestTwo", "TestTwo", LocalDate.now());
        store.add(candidateTwo);
        Candidate candidate = new Candidate(candidateTwo.getId(), "Test", "Test", LocalDate.now());
        store.updateCandidate(candidate);
        assertTrue(store.updateCandidate(candidate));
        assertEquals(store.findById(candidateTwo.getId()), candidate);
        assertNotEquals("TestTwo", store.findById(candidateTwo.getId()).getName());
    }
}