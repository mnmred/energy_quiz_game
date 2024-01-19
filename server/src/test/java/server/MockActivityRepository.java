package server;

import commons.Activity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MockActivityRepository implements ActivityRepository {

    public final List<String> calledMethods;
    public final List<Activity> activities;

    public MockActivityRepository() {
        this.calledMethods = new ArrayList<>();
        this.activities = new ArrayList<>();
    }

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Activity> findByDescriptionContaining(String description) {
        call("findByDescriptionContaining");
        return activities.stream()
                .filter(act -> act.getDescription().contains(description))
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findAll() {
        call("findAll");
        return List.copyOf(activities);
    }

    @Override
    public List<Activity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Activity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Activity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        call("count");
        return activities.size();
    }

    @Override
    public void deleteById(Long aLong) {
        call("deleteById");
        if (activities.stream().map(act -> act.getId()).anyMatch(id -> id == aLong))
            activities.remove(activities.stream().filter(act -> act.getId() == aLong).findAny().get());
    }

    @Override
    public void delete(Activity entity) {
        call("delete");
        activities.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Activity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Activity> S save(S entity) {
        call("save");
        Long id = activities.stream().map(act -> act.getId()).max(Long::compareTo).orElse(0L) + 1;
        entity.setId(id);
        activities.add(entity);
        return entity;
    }

    @Override
    public <S extends Activity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Activity> findById(Long aLong) {
        call("findById");
        return activities.stream().filter(act -> act.getId() == aLong).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        call("existsById");
        return activities.stream().anyMatch(act -> act.getId() == aLong);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Activity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Activity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Activity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Activity getOne(Long aLong) {
        return null;
    }

    @Override
    public Activity getById(Long aLong) {
        call("getById");
        return findById(aLong).orElse(null);
    }

    @Override
    public <S extends Activity> Optional<S> findOne(Example<S> example) {
        call("findOne");
        return (Optional<S>) activities.stream().filter(act -> act.equals(example.getProbe())).findAny();
    }

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Activity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Activity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Activity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Activity, R> R findBy(Example<S> example,
                                            Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
