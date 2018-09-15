<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:genericPage>
    <jsp:body>

        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <div class="stats">
            <div class="row">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Job name</th>
                        <th>Total runs</th>
                        <th>Member</th>
                        <th>Last idle duration</th>
                        <th>Total idle duration</th>
                        <th>Last run duration</th>
                        <th>Total run duration</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody id="jobsTableBody">
                    </tbody>
                </table>
            </div>
        </div>

    </jsp:body>
</tag:genericPage>
