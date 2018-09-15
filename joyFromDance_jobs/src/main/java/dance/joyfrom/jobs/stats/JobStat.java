package dance.joyfrom.jobs.stats;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Radosław Osiński
 */
public class JobStat {

    private String name;
    private long runs;
    private String member;
    private long lastRunDuration;
    private long lastIdleDuration;
    private long totalRunDuration;
    private long totalIdleDuration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public long getRuns() {
        return runs;
    }

    public void setRuns(long runs) {
        this.runs = runs;
    }

    public long getLastRunDuration() {
        return lastRunDuration;
    }

    public void setLastRunDuration(long lastRunDuration) {
        this.lastRunDuration = lastRunDuration;
    }

    public long getLastIdleDuration() {
        return lastIdleDuration;
    }

    public void setLastIdleDuration(long lastIdleDuration) {
        this.lastIdleDuration = lastIdleDuration;
    }

    public long getTotalRunDuration() {
        return totalRunDuration;
    }

    public void setTotalRunDuration(long totalRunDuration) {
        this.totalRunDuration = totalRunDuration;
    }

    public long getTotalIdleDuration() {
        return totalIdleDuration;
    }

    public void setTotalIdleDuration(long totalIdleDuration) {
        this.totalIdleDuration = totalIdleDuration;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("name", name)
            .append("member", member)
            .append("runs", runs)
            .append("lastRunDuration", lastRunDuration)
            .append("lastIdleDuration", lastIdleDuration)
            .append("totalRunDuration", totalRunDuration)
            .append("totalIdleDuration", totalIdleDuration)
            .toString();
    }
}
