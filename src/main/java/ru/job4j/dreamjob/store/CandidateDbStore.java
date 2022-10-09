package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@ThreadSafe
public class CandidateDbStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(Post.class.getName());

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }


    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("created", Timestamp.class).toLocalDateTime().toLocalDate()));


                }
            }
        } catch (SQLException throwables) {
            LOG.error("Error", throwables);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name, description, created) "
                             + "VALUES ( ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate where id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(it.getInt("candidate.id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("created", Timestamp.class).toLocalDateTime().toLocalDate());

                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return null;
    }

    public boolean updateCandidate(Candidate candidate) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE candidate set name = ?,"
                     + "description = ?,"
                     + "created = now(),"
                     + " where id = ? ")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setInt(4, candidate.getId());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return result;
    }
}
