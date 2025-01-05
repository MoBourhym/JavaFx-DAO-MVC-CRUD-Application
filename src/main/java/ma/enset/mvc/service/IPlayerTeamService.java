package ma.enset.mvc.service;

import ma.enset.mvc.dao.entities.Player;
import ma.enset.mvc.dao.entities.Team;

import java.util.List;

public interface IPlayerTeamService {

    void savePlayer(Player player);

    void updatePlayer(Player player);

    void deletePlayerById(Long playerId);

    Player findPlayerById(Long playerId);

    List<Player> getAllPlayers();

    List<Player> getTeamPlayers(Long teamId);

    void saveTeam(Team team);

    void updateTeam(Team team);

    void deleteTeamById(Long teamId);

    Team findTeamById(Long teamId);

    List<Team> findAllTeams();

    List<Team> findTeamByKeyword(String keyword);
}
