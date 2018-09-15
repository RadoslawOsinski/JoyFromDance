<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:genericPage>
    <jsp:body>

        <section class="pv-40 dark-translucent-bg">
            <div class="container">
                <h1 class="large text-center text-default"><spring:message code="Dance" text="DANCE"/> <span class="text-default"><spring:message code="WithUs" text="WITH US"/></span></h1>
                <div class="separator"></div>
                <br>
                <div class="row">
                    <div class="col-lg-6">
                        <div class="feature-box-2 object-non-visible right" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
                            <span class="icon white-bg small circle text-default"><i class="fa fa-check"></i></span>
                            <div class="body">
                                <h3 class="title"><spring:message code="OfferForDancers" text="Offer for dancers"/></h3>
                                <div class="separator-3"></div>
                                <ul class="list-icons">
                                    <li>... <spring:message code="InPreparation" text="InPreparation"/> ...</li>
                                    <%--<li>Dobieranie w pary przez internet<i class="icon-check-1"></i></li>--%>
                                    <%--<li>Zaznaczanie supportów<i class="icon-check-1"></i></li>--%>
                                    <%--<li>Zapisy na zajęcia przez internet<i class="icon-check-1"></i></li>--%>
                                    <%--<li>Zapisy na maratony taneczne<i class="icon-check-1"></i></li>--%>
                                    <%--<li>Zapisy na wydarzenia / milongi<i class="icon-check-1"></i></li>--%>
                                    <%--<li>Potwierdzenie obecności mailem, smartfonem przez media społecznościowe<i class="icon-check-1"></i></li>--%>
                                </ul>
                                <div class="separator-2"></div>
                            </div>
                        </div>
                        <br>
                        <%--<div class="feature-box-2 object-non-visible right" data-animation-effect="fadeInDownSmall" data-effect-delay="100">--%>
                            <%--<span class="icon default-bg small circle"><i class="icon-check"></i></span>--%>
                            <%--<div class="body">--%>
                                <%--<h3 class="title">Płatności</h3>--%>
                                <%--<p>Płatności przez internet o ich podgląd</p>--%>
                                <%--<div class="separator-3"></div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="feature-box-2 object-non-visible right" data-animation-effect="fadeInDownSmall" data-effect-delay="100">--%>
                            <%--<span class="icon white-bg small circle text-default"><i class="fa fa-check"></i></span>--%>
                            <%--<div class="body">--%>
                                <%--<h3 class="title">Grafik zajęć</h3>--%>
                                <%--<p>sprawdzanie własnego grafiku zajęć</p>--%>
                                <%--<div class="separator-3"></div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="feature-box-2 object-non-visible right" data-animation-effect="fadeInDownSmall" data-effect-delay="100">--%>
                            <%--<span class="icon default-bg small circle"><i class="icon-check"></i></span>--%>
                            <%--<div class="body">--%>
                                <%--<h3 class="title">Historia uczestnictwa w zajęciach</h3>--%>
                                <%--<p>Historia uczestnictwa w zajęciach</p>--%>
                                <%--<div class="separator-3"></div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </div>
                    <div class="col-lg-6">
                        <div class="feature-box-2 object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
                            <span class="icon default-bg small circle"><i class="fa fa-check"></i></span>
                            <div class="body">
                                <h3 class="title"><spring:message code="OfferForSchools" text="Offer for schools"/></h3>
                                <div class="separator-2"></div>
                                <ul class="list-icons">
                                    <li>... <spring:message code="InPreparation" text="InPreparation"/> ...</li>
                                    <%--<li>grafik sal i ich obłożenia <i class="icon-check-1"></i></li>--%>
                                    <%--<li>grafik zajęć wykładowców <i class="icon-check-1"></i></li>--%>
                                    <%--<li>raport z wynajmu sal <i class="icon-check-1"></i></li>--%>
                                    <%--<li>raport z godzin wykładowców <i class="icon-check-1"></i></li>--%>
                                    <%--<li>raport z liczby uczestników <i class="icon-check-1"></i></li>--%>
                                    <%--<li>powiadomienia uczestników zajęć: odwołanie zajęć, zamknięcie grupy, utworzenie kursu <i class="icon-check-1"></i></li>--%>
                                </ul>
                                <div class="separator-3"></div>
                            </div>
                        </div>
                        <br>
                        <div class="feature-box-2 object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
                            <span class="icon white-bg small circle text-default"><i class="fa fa-check"></i></span>
                            <div class="body">
                                <h3 class="title"><spring:message code="SocialMedia" text="Social media"/></h3>
                                <p><spring:message code="SocialMediaIntegration" text="Social media integration: facebook, twitter, google"/></p>
                                <div class="separator-2"></div>
                            </div>
                        </div>
                        <%--<div class="feature-box-2 object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">--%>
                            <%--<span class="icon default-bg small circle"><i class="fa fa-check"></i></span>--%>
                            <%--<div class="body">--%>
                                <%--<h3 class="title">Grafik zajęć</h3>--%>
                                <%--<p>łatwe tworzenie grafiku</p>--%>
                                <%--<div class="separator-2"></div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="feature-box-2 object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">--%>
                            <%--<span class="icon white-bg small circle text-default"><i class="fa fa-check"></i></span>--%>
                            <%--<div class="body">--%>
                                <%--<h3 class="title">backupy</h3>--%>
                                <%--<p>wydruk XLS'ów w razie awarii</p>--%>
                                <%--<p>backup systemu</p>--%>
                                <%--<div class="separator-2"></div>--%>
                            <%--</div>--%>
                        </div>
                        <br>
                    </div>
                </div>
            </div>
        </section>

        <section class="light-gray-bg pv-30 clearfix">
            <div class="container">
                <div class="row justify-content-lg-center">
                    <div class="col-lg-10 form-inline">
                        <p class="large text-center"><a href="${pageContext.request.contextPath}/dance/" class="btn btn-primary btn-xl margin-clear radius-50 smooth-scroll"><spring:message code="RegistrationAndLoginForDancers" text="Registration and login for dancers"/></a></p>
                        &nbsp;
                        <p class="large text-center"><a href="${pageContext.request.contextPath}/dancer/schooladmin/registration" class="btn btn-default btn-xl margin-clear radius-50 smooth-scroll"><spring:message code="RegistrationAndLoginForSchools" text="Registration and login for schools"/></a></p>
                    </div>
                </div>
            </div>
        </section>

        <section class="light-gray-bg pv-30 clearfix">
            <div class="container">
                <div class="row justify-content-lg-center">
                    <div class="col-lg-10">
                        <p class="large text-center"><spring:message code="AnyCommentsAboutThisSiteAreWelcome" text="Any comments about this site are welcome! You can report them using the contact form at the bottom of the page."/></p>
                    </div>
                </div>
            </div>
        </section>

    </jsp:body>
</tag:genericPage>
