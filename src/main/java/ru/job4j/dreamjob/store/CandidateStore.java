package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan", "junior",
                LocalDate.of(2014, Month.APRIL, 25)));
        candidates.put(2, new Candidate(2, "Masha", "middle",
                LocalDate.of(2015, Month.MARCH, 20)));
        candidates.put(3, new Candidate(3, "Micle", "senior",
                LocalDate.of(2016, Month.JULY, 10)));

    }
    public static CandidateStore instof() {
        return INST;
    }
    public Collection<Candidate> findAll() {
        return candidates.values();
    }


}
