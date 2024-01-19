package server;

import commons.Player;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.PlayerScoreRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MockPlayerScoreScoreRepository implements PlayerScoreRepository {

    public final List<Player> scores;
    public final List<String> calledMethods;

    public MockPlayerScoreScoreRepository() {
        scores = new ArrayList<>();
        calledMethods = new ArrayList<>();
    }

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Player> findAll() {
        call("findAll");
        return scores;
    }

    @Override
    public List<Player> findAll(Sort sort) {
        call("findAll");
        return scores.stream()
                .sorted(Comparator.comparingInt(ps -> -ps.getScore()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Player> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Player> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        call("count");
        return scores.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Player entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Player> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Player> S save(S entity) {
        call("save");
        entity.setId((long) scores.size());
        scores.add(entity);
        return entity;
    }

    @Override
    public <S extends Player> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Player> findById(Long aLong) {
        call("findById");
        return scores.stream().filter(ps -> ps.getId() == aLong).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        call("existsById");
        return scores.stream().anyMatch(ps -> ps.getId() == aLong);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Player> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Player> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Player> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Player getOne(Long aLong) {
        return null;
    }

    @Override
    public Player getById(Long aLong) {
        call("getById");
        return scores.stream().filter(ps -> ps.getId() == aLong).findFirst().get();
    }

    @Override
    public <S extends Player> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Player> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Player> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Player> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Player> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Player> boolean exists(Example<S> example) {
        call("exists");
        return scores.contains(example);
    }

    @Override
    public <S extends Player, R> R findBy(
            Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
