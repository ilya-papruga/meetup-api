package com.modsen.meetup.repository;

import com.modsen.meetup.dto.filter.Filter;
import com.modsen.meetup.entity.Meetup;
import com.modsen.meetup.repository.api.MeetupRepository;
import com.modsen.meetup.dto.MeetupUpdate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;

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


    public Meetup findOne(Long id) {

        EntityManager entityManager = managerFactory.createEntityManager();

        Meetup entity = entityManager
                .createQuery("from Meetup where id = :id", Meetup.class)
                .setParameter("id", id)
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

    public void updateByUuid(Long id, MeetupUpdate dto, Long version) {

        EntityManager entityManager = managerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Meetup meetup = entityManager.find(Meetup.class, id);

        if (!meetup.getVersion().equals(version)) {
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

    public void deleteByUuid(Long id, Long version) {

        EntityManager entityManager = managerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Meetup meetup = entityManager.find(Meetup.class, id);

        if (!meetup.getVersion().equals(version)) {
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
        }

            if (filter.getSortingType() != null) {

                String sortingType = filter.getSortingType();

                if (sortingType.equalsIgnoreCase("asc") || sortingType.equalsIgnoreCase("desc")) {

                    line.append(" ").append(filter.getSortingType());

                }
                else {
                    throw new NoResultException("wrong sorting type, use asc or desc");
                }
            }

            return line.toString();
        }
    }


