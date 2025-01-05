package ma.enset.mvc.dao;

import ma.enset.mvc.dao.entities.Team;
import ma.enset.mvc.dao.entities.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDaoImpl implements TeamDao {

    @Override
    public void save(Team team) {
        String query = "INSERT INTO teams (name, city) VALUES (?, ?)";
        try  {
            Connection connection = SignletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);

            pstm.setString(1, team.getName());
            pstm.setString(2, team.getCity());
            pstm.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Team team) {
        String query = "UPDATE teams SET name = ?, city = ? WHERE team_id = ?";
        try (Connection connection = SignletonConnexionDB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(query)) {

            pstm.setString(1, team.getName());
            pstm.setString(2, team.getCity());
            pstm.setLong(3, team.getId());
            pstm.executeUpdate();

            // Update players if necessary


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM teams WHERE team_id = ?";
        try  {
            Connection connection = SignletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setLong(1, id);
            pstm.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Team findById(Long id) {
        Team team = null;
        String query = "SELECT * FROM teams WHERE team_id = ?";
        try (Connection connection = SignletonConnexionDB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(query)) {

            pstm.setLong(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                team = new Team();
                team.setId(rs.getLong("team_id"));
                team.setName(rs.getString("name"));
                team.setCity(rs.getString("city"));
                return team;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    @Override
    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM teams";
        try  {
            Connection connection = SignletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {

                Team team = new Team();
                team.setId(rs.getLong("team_id"));
                team.setName(rs.getString("name"));
                team.setCity(rs.getString("city"));
                teams.add(team);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public List<Player> getTeamPlayers(Long teamId) throws SQLException {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players WHERE team_id = ?";
        try (Connection connection = SignletonConnexionDB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(query)) {

            pstm.setLong(1, teamId);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Player player = new Player();
                player.setId(rs.getLong("player_id"));
                player.setName(rs.getString("name"));
                player.setPosition(rs.getString("position"));
                player.setNumber(rs.getInt("number"));
                players.add(player);
            }
        }
        return players;
    }



    public    List<Team> findTeamByKeyWord(String keyWord) {
        List<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM teams WHERE name LIKE ? OR city LIKE ?";
        try (Connection connection = SignletonConnexionDB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(query)) {

            pstm.setString(1, "%" + keyWord + "%");
            pstm.setString(2, "%" + keyWord + "%");

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Team team = new Team();
                    team.setId(rs.getLong("team_id"));
                    team.setName(rs.getString("name"));
                    team.setCity(rs.getString("city"));
                    teams.add(team);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }
}