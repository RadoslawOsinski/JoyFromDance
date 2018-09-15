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

        <h2 class="mt-4"><spring:message code="ContactFormMessages" text="Contact form messages"/></h2>
        <div class="row">
            <div class="col-7 col-sm-7 col-lg-7">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><spring:message code="Name" text="Name"/> <input type="text" id="nameSearch" class="form-control"/></th>
                        <th><spring:message code="Email" text="Email"/> <input type="text" id="emailSearch" class="form-control"/></th>
                        <th><spring:message code="Created" text="Created"/></th>
                        <th><spring:message code="Actions" text="Actions"/></th>
                    </tr>
                    </thead>
                    <tbody id="contactMessagesTableBody">
                    </tbody>
                </table>
                <button id="previousPageButton" class="btn btn-default-transparent btn-sm"><i class="fa fa-chevron-left pr-10"></i> Previous</button>
                <button id="resetPageButton" class="btn btn-default-transparent btn-sm">0</button>
                <button id="nextPageButton" class="btn btn-default-transparent btn-sm"><i class="fa fa-chevron-right pr-10"></i> Next</button>
            </div>
            <div class="col-5 col-sm-5 col-lg-5">
                <label for="messagePreview"><spring:message code="MessagePreview" text="Message preview"/>:</label>
                <textarea readonly="readonly" class="form-control" id="messagePreview">
                </textarea>
            </div>
        </div>

    </jsp:body>
</tag:genericPage>
