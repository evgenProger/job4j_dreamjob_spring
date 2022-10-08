package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Service
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger(4);

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan", "junior",
                LocalDate.of(2014, Month.APRIL, 25)));
        candidates.put(2, new Candidate(2, "Masha", "middle",
                LocalDate.of(2015, Month.MARCH, 20)));
        candidates.put(3, new Candidate(3, "Micle", "senior",
                LocalDate.of(2016, Month.JULY, 10)));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate add(Candidate candidate) {
        candidate.setId(ids.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    public Candidate findById(int id) {
       return candidates.get(id);
    }

    public Candidate updateCandidate(Candidate candidate) {
        return candidates.replace(candidate.getId(), candidate);
    }
}
