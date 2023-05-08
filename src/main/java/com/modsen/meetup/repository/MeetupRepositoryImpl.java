package com.modsen.meetup.repository;

import com.modsen.meetup.dto.filter.Filter;
import com.modsen.meetup.entity.Meetup;
import com.modsen.meetup.repository.api.MeetupRepository;
import com.modsen.meetup.dto.MeetupUpdate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public List<Meetup> findAllFiltered(Filter filter) {

        EntityManager entityManager = managerFactory.createEntityManager();

        CriteriaQuery<Meetup> filteredQuery = getFilteredQuery(managerFactory.getCriteriaBuilder(), filter);

        List<Meetup> resultList = entityManager.createQuery(filteredQuery).getResultList();

        entityManager.close();

        return resultList;

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


    private CriteriaQuery<Meetup> getFilteredQuery(CriteriaBuilder cb, Filter filter) {

        CriteriaQuery<Meetup> query = cb.createQuery(Meetup.class);
        Root<Meetup> root = query.from(Meetup.class);
        List<Predicate> predicates = new ArrayList<>();

        query.select(root);

        String topic = filter.getTopic();
        String organizer = filter.getOrganizer();
        LocalDateTime dateTime = filter.getDateTime();
        String sortingField = filter.getSortingField();
        String sortingType = filter.getSortingType();

        if (!Objects.isNull(topic)) {
            predicates.add(cb.like(root.get("topic"), "%" + topic + "%"));
        }

        if (!Objects.isNull(organizer)) {
            predicates.add(cb.like(root.get("organizer"), "%" + organizer + "%"));
        }

        if (!Objects.isNull(dateTime)) {
            predicates.add(cb.between(root.get("dateTime"), dateTime, dateTime));
        }

        if (!Objects.isNull(sortingField) && !Objects.isNull(sortingType)) {

            if (sortingType.equalsIgnoreCase("asc")) {
                query.orderBy(cb.asc(root.get(sortingField)));
            }
            if (sortingType.equalsIgnoreCase("desc")) {
                query.orderBy(cb.desc(root.get(sortingField)));
            } else {
                throw new NoResultException("wrong sorting type, use asc/desc");
            }
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
        return query;

    }
}


