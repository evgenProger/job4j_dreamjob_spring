package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDbStore;

import java.util.Collection;

@ThreadSafe
@Service
public class CandidateService {

    private final CandidateDbStore store;

    public CandidateService(CandidateDbStore store) {
        this.store = store;
    }


    public Collection<Candidate> findAll() {
        return store.findAllCandidates();
    }

    public Candidate add(Candidate candidate) {
        return store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public boolean updateCandidate(Candidate candidate) {
        return store.updateCandidate(candidate);
    }
}

