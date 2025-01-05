package ma.enset.mvc.service;

import ma.enset.mvc.dao.PlayerDao;
import ma.enset.mvc.dao.PlayerDaoImpl;
import ma.enset.mvc.dao.TeamDao;
import ma.enset.mvc.dao.TeamDaoImpl;
import ma.enset.mvc.dao.entities.Player;
import ma.enset.mvc.dao.entities.Team;
import ma.enset.mvc.service.CPlayerTeamService;
import ma.enset.mvc.service.IPlayerTeamService;

import java.sql.SQLException;
import java.util.List;

public class CPlayerTeamService implements IPlayerTeamService {

    private PlayerDaoImpl playerDao;
    private TeamDaoImpl teamDao;

    public CPlayerTeamService(PlayerDaoImpl playerDao, TeamDaoImpl teamDao) {
        this.playerDao = playerDao;
        this.teamDao = teamDao;
    }

    @Override
    public void savePlayer(Player player) {
        playerDao.save(player);
    }

    @Override
    public void updatePlayer(Player player) {
        playerDao.update(player);
    }

    @Override
    public void deletePlayerById(Long playerId) {
        playerDao.deleteById(playerId);
    }

    @Override
    public Player findPlayerById(Long playerId) {
        return playerDao.findById(playerId);
    }

    @Override
    public List<Player> getAllPlayers(){
        return playerDao.findAll();
    }

    @Override
    public List<Player> getTeamPlayers(Long teamId) {
        try {
            return teamDao.getTeamPlayers(teamId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveTeam(Team team) {
        teamDao.save(team);
    }

    @Override
    public void updateTeam(Team team) {
        teamDao.update(team);
    }

    @Override
    public void deleteTeamById(Long teamId) {
        teamDao.deleteById(teamId);
    }

    @Override
    public Team findTeamById(Long teamId) {
        return teamDao.findById(teamId);
    }

    @Override
    public List<Team> findAllTeams() {
        return teamDao.findAll();
    }

    @Override
    public List<Team> findTeamByKeyword(String keyword) {
        return teamDao.findTeamByKeyWord(keyword);
    }
}
