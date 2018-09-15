package dance.joyfrom.services.paging;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Created by Radosław Osiński
 */
public class TablePageableResponse<X> {

    private Long totalNumberOfPages;
    private List<X> results;

    public Long getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public void setTotalNumberOfPages(Long totalNumberOfPages) {
        this.totalNumberOfPages = totalNumberOfPages;
    }

    public List<X> getResults() {
        return results;
    }

    public void setResults(List<X> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("totalNumberOfPages", totalNumberOfPages)
            .append("results", results)
            .toString();
    }
}
