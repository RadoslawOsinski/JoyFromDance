<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:genericPage>
    <jsp:body>

        <br/>
        <br/>
        <br/>

        <section class="main-container">

            <div class="container">
                <div class="row">

                    <div class="main col-lg-12">

                        <h2 class="mt-4"><spring:message code="Account" text="Account"/></h2>

                        <div class="alert alert-success hidden-xs-up"
                             id="successfulBasicAccountUpdateMessage">
                            <spring:message code="account.basic.info.update.successful.message"
                                            text="Saving basic account information was successful"/>
                        </div>
                        <div class="alert alert-danger hidden-xs-up" id="failedBasicAccountUpdateMessage">
                            <spring:message code="contact.message.send.error.message"
                                            text="Oops! Something went wrong please refresh the page and try again."/>
                        </div>

                        <div class="row">
                            <form class="col-lg-12">
                                <div class="form-group row">
                                    <label for="email" class="col-sm-2 col-form-label">
                                        <spring:message code="Email" text="Email"/>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="email" class="form-control col-lg-8" id="email" value="${pageContext.request.userPrincipal.account.keycloakSecurityContext.idToken.email}" disabled/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="firstName" class="col-sm-2 col-form-label">
                                        <spring:message code="FirstName" text="FirstName"/>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control col-lg-8" id="firstName" value="${firstName}" required/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="lastName" class="col-sm-2 col-form-label">
                                        <spring:message code="LastName" text="LastName"/>
                                    </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control col-lg-8" id="lastName" value="${lastName}" required/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-12">
                                        <button type="button" id="updateBasicAccountInfoButton" class="btn btn-default">
                                            <spring:message code="Save" text="Save"/>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </section>

    </jsp:body>
</tag:genericPage>
