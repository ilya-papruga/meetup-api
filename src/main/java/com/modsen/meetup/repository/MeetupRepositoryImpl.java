package com.modsen.meetup.repository;

import com.modsen.meetup.dto.filter.Filter;
import com.modsen.meetup.entity.Meetup;
import com.modsen.meetup.repository.api.MeetupRepository;
import com.modsen.meetup.dto.MeetupUpdate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class MeetupRepositoryImpl implements MeetupRepository {

    private final EntityManagerFactory managerFactory;

    public MeetupRepositoryImpl(EntityManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    public List<Meetup> findAll() {

        EntityManager entityManager = managerFactory.createEntityManager();
        List<Meetup> entityList = entityManager.createQuery("from Meetup", Meetup.class).getResultList();
        entityManager.close();

        return entityList;
    }

    @Override
    public List<Meetup> findAllSorted(Filter filter) {

        EntityManager entityManager = managerFactory.createEntityManager();

        String filteredQuery = buildFilteredQuery(filter);

        List<Meetup> entityList = entityManager.createQuery("from Meetup " + filteredQuery, Meetup.class).getResultList();

        entityManager.close();

        return entityList;

    }


    public Meetup findOne(UUID uuid) {

        EntityManager entityManager = managerFactory.createEntityManager();

        Meetup entity = entityManager
                .createQuery("from Meetup where uuid = :uuid", Meetup.class)
                .setParameter("uuid", uuid)
                .getSingleResult();

        entityManager.close();

        return entity;

    }

    public Meetup createNew(Meetup meetup) {

        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(meetup);
        entityManager.getTransaction().commit();
        entityManager.close();

        return meetup;
    }

    public void updateByUuid(UUID uuid, MeetupUpdate dto, LocalDateTime dtUpdate) {

        EntityManager entityManager = managerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Meetup meetup = entityManager.find(Meetup.class, uuid);

        if (!meetup.getDtUpdate().equals(dtUpdate)) {
            throw new IllegalArgumentException("the entity has already been updated");
        }

        meetup.setTopic(dto.getTopic());
        meetup.setDescription(dto.getDescription());
        meetup.setOrganizer(dto.getOrganizer());
        meetup.setDateTime(dto.getDateTime());
        meetup.setPlace(dto.getPlace());

        entityManager.persist(meetup);

        entityManager.getTransaction().commit();
        entityManager.close();

    }

    public void deleteByUuid(UUID uuid, LocalDateTime dtUpdate) {

        EntityManager entityManager = managerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Meetup meetup = entityManager.find(Meetup.class, uuid);

        if (!meetup.getDtUpdate().equals(dtUpdate)) {
            throw new IllegalArgumentException("the entity has already been deleted or updated");
        }

        entityManager.remove(meetup);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    private static String buildFilteredQuery(Filter filter) {
        StringBuilder line = new StringBuilder();

        if (filter.getTopic() != null) {
            line.append("topic = ").append("'").append(filter.getTopic()).append("'");
        }

        if (filter.getOrganizer() != null) {
            if (line.length() > 0) {
                line.append("and ");
            }
            line.append("organizer = ").append("'").append(filter.getOrganizer()).append("'");
        }

        if (filter.getDateTime() != null) {

            if (line.length() > 0) {
                line.append("and ");
            }

            line.append("date_time = ").append("'").append(filter.getDateTime()).append("'");
        }

        if (line.length() > 0) {
            line.insert(0, "where ");
        }

        if (filter.getSortingField() != null) {

            if (line.length() > 0) {
                line.append(" ");
            }

            line.append("order by ").append(filter.getSortingField());

            if (filter.getSortingType() != null) {
                line.append(" ").append(filter.getSortingType());
            }

        }
        return line.toString();
    }
}
