package cegeka.scoaladevalori.ro.taskao;

public class UserActivities {

     String userActivityTitile;
     String userActivityDescription;
     String userActivityDate;

    public UserActivities(){
    }

    public UserActivities(String userActivityTitile, String userActivityDescription, String userActivityDate) {
        this.userActivityTitile = userActivityTitile;
        this.userActivityDescription = userActivityDescription;
        this.userActivityDate = userActivityDate;
    }

    public String getUserActivityTitile() {
        return userActivityTitile;
    }

    public void setUserActivityTitile(String userActivityTitile) {
        this.userActivityTitile = userActivityTitile;
    }

    public String getUserActivityDescription() {
        return userActivityDescription;
    }

    public void setuserActivityDescription(String userName) {
        this.userActivityDescription = userName;
    }

    public String getUserActivityDate() {
        return userActivityDate;
    }

    public void setUserActivityDate(String userActivityDate) {
        this.userActivityDate = userActivityDate;
    }

}
