package ma.enset.mvc.dao;

import ma.enset.mvc.dao.entities.Player;

import java.util.List;

public interface PlayerDao extends DAO<Player, Long> {
    @Override
    List<Player> findAll();

    @Override
    Player findById(Long id);

    @Override
    void save(Player o);

    @Override
    void deleteById(Long id);

    @Override
    void update(Player o);
}
