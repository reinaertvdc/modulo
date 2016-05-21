package be.lambdaware.modulomobile.models;

/**
 * Created by Hendrik on 13/05/2016.
 */
public class Score {

    private String name;
    private int totalCompetences;
    private int totalPassed;
    private int totalFailed;

    private int offered;
    private int practiced;
    private int acquired;

    public Score(String name, int totalCompetences, int totalPassed, int totalFailed, int offered, int practiced, int acquired) {
        this.name = name;
        this.totalCompetences = totalCompetences;
        this.totalPassed = totalPassed;
        this.totalFailed = totalFailed;
        this.offered = offered;
        this.practiced = practiced;
        this.acquired = acquired;
    }

    public Score() {}

    public int getTotalCompetences() {
        return totalCompetences;
    }

    public void setTotalCompetences(int totalCompetences) {
        this.totalCompetences = totalCompetences;
    }

    public int getTotalPassed() {
        return totalPassed;
    }

    public void setTotalPassed(int totalPassed) {
        this.totalPassed = totalPassed;
    }

    public int getTotalFailed() {
        return totalFailed;
    }

    public void setTotalFailed(int totalFailed) {
        this.totalFailed = totalFailed;
    }

    public int getOffered() {
        return offered;
    }

    public void setOffered(int offered) {
        this.offered = offered;
    }

    public int getPracticed() {
        return practiced;
    }

    public void setPracticed(int practiced) {
        this.practiced = practiced;
    }

    public int getAcquired() {
        return acquired;
    }

    public void setAcquired(int acquired) {
        this.acquired = acquired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPassedPercentage() {
        return ((float)totalPassed / totalCompetences) * 100;
    }
    public float getFailedPercentage() {
        return ((float)totalFailed / totalCompetences) * 100;
    }

    @Override
    public String toString() {
        return "Score{" +
                "name='" + name + '\'' +
                ", totalCompetences=" + totalCompetences +
                ", totalPassed=" + totalPassed +
                ", totalFailed=" + totalFailed +
                ", offered=" + offered +
                ", practiced=" + practiced +
                ", acquired=" + acquired +
                ", passed %=" + getPassedPercentage() +
                ", failed %=" + getFailedPercentage() +
                '}';
    }
}
