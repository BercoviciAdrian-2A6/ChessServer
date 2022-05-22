package Entities;

public class MatchEntity
{
    private int matchId;
    private UserEntity whiteUser, backUser, winner;
    private String start_timestamp;

    public MatchEntity(int matchId, UserEntity whiteUser, UserEntity backUser, UserEntity winner, String start_timestamp)
    {
        this.matchId = matchId;
        this.whiteUser = whiteUser;
        this.backUser = backUser;
        this.winner = winner;
        this.start_timestamp = start_timestamp;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public UserEntity getWhiteUser() {
        return whiteUser;
    }

    public void setWhiteUser(UserEntity whiteUser) {
        this.whiteUser = whiteUser;
    }

    public UserEntity getBackUser() {
        return backUser;
    }

    public void setBackUser(UserEntity backUser) {
        this.backUser = backUser;
    }

    public UserEntity getWinner() {
        return winner;
    }

    public void setWinner(UserEntity winner) {
        this.winner = winner;
    }

    public String getStart_timestamp() {
        return start_timestamp;
    }

    public void setStart_timestamp(String start_timestamp) {
        this.start_timestamp = start_timestamp;
    }

    @Override
    public String toString() {
        return "MatchEntity{" +
                "matchId=" + matchId +
                ", whiteUser=" + whiteUser +
                ", backUser=" + backUser +
                ", winner=" + winner +
                ", start_timestamp='" + start_timestamp + '\'' +
                '}';
    }
}
