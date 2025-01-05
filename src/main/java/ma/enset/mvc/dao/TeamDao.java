package ma.enset.mvc.dao;

import ma.enset.mvc.dao.entities.Team;

import java.util.List;

public interface TeamDao extends DAO<Team,Long>{
    @Override
    List<Team> findAll();

    @Override
    Team findById(Long id);

    @Override
    void save(Team o);

    @Override
    void deleteById(Long id);

    @Override
    void update(Team o);
}
