<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericPage>
    <jsp:body>

        <div class="mdc-layout-grid__inner">
            <div class="mdc-layout-grid__cell mdc-layout-grid__cell--span-3">&nbsp;</div>
            <div class="mdc-layout-grid__cell mdc-layout-grid__cell--span-6">
                <p>&nbsp;</p>
                <section>
                    <h2>Sign up as a dancer</h2>
                    <p>Mark on map place where you would like to learn dance. It does not matter if there is any dancing school nearby.
                        Mark your preferred small town or nearest city. We will try to find and organize dancing lesson for you.
                    </p>
                    <div>
                        <div>
                            <div class="mdc-text-field mdc-text-field--upgraded" id="dancerPlaceInputWrap">
                                <input type="text" class="mdc-text-field__input" id="dancerPlaceInput" value="Warsaw" placeholder="" aria-controls="dancer-place-helper-text" aria-describedby="dancer-place-helper-text" required>
                                <label for="dancerPlaceInput" class="mdc-floating-label mdc-floating-label--float-above">Search for a place...</label>
                                <div class="mdc-line-ripple" style="transform-origin: 74.5px center 0px;"></div>
                            </div>
                            <p id="dancer-place-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                                Place where you would like to dance is required
                            </p>
                        </div>
                        <div>
                            <div class="mdc-text-field mdc-text-field--upgraded" id="dancerPlaceDistanceInputWrap">
                                <input type="number" class="mdc-text-field__input" id="dancerPlaceDistanceInput" value="15" aria-controls="dancer-place-distance-helper-text" aria-describedby="dancer-place-distance-helper-text" required min="1"/>
                                <label for="dancerPlaceDistanceInput" class="mdc-floating-label mdc-floating-label--float-above">Distance (km or miles)</label>
                                <div class="mdc-line-ripple" style="transform-origin: 74.5px center 0px;"></div>
                            </div>
                            <p id="dancer-place-distance-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                                Max distance from your preferred place is required
                            </p>
                        </div>
                    </div>
                    <div>
                        <div id="dancerMap" style="height: 400px; width: 100%; "/>
                    </div>
                    <p>
                        Choose dancing style in which you are interested in. Write your email address so we could contact you if we are able to organize such lessons.
                    </p>
                    <div>
                        <div class="mdc-text-field mdc-text-field--upgraded" id="dancerEmailInputWrap">
                            <input type="email" class="mdc-text-field__input" id="dancerEmailInput" aria-controls="dancer-email-helper-text" aria-describedby="dancer-email-helper-text" required>
                            <label for="dancerEmailInput" class="mdc-floating-label mdc-floating-label--float-above">Email</label>
                            <div class="mdc-line-ripple" style="transform-origin: 74.5px center 0px;"></div>
                        </div>
                        <p id="dancer-email-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                            Invalid email
                        </p>

                        <div id="dancer-dancing-styles" class="mdc-chip-set mdc-chip-set--filter" aria-controls="dancer-dancing-styles-helper-text" aria-describedby="dancer-dancing-styles-helper-text">
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Ballet</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Break dance</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Cha cha</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Disco dance</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Hip hop</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Jazz</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Modern</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Pop</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Rumba</div>
                            </div>
                            <div class=" mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Salsa</div>
                            </div>
                            <div class=" mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Swing</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Waltz</div>
                            </div>
                            <div class=" mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Tango</div>
                            </div>
                        </div>
                        <p id="dancer-dancing-styles-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                            Choosing dancing style is required
                        </p>
                        <div>
                            <p id="subscribeDancerSuccessMessage" class="mdc-text-field-helper-text mdc-text-field-helper-text mdc-text-field-helper-text--persistent" style="color: #018786; visibility: hidden" role="alert">
                                Signing up was successful
                            </p>
                            <p id="subscribeDancerFailureMessage" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg mdc-text-field-helper-text--persistent" style="color: #b00020;margin-bottom: 8px;opacity: 1; visibility: hidden" role="alert">
                                Signing up was unsuccessful
                            </p>
                        </div>
                    </div>
                    <div>
                        <button class="mdc-button mdc-button--raised mdc-ripple-upgraded mdc-ripple-upgraded--foreground-activation" id="dancerSignUpButton">
                            Sign up
                        </button>
                    </div>
                </section>
                <section>
                    <h2>Sign up as a teacher</h2>
                    <p>Struggling to find group of dancing lerner? We would like to help.
                        Write your email address and mark on map place where you can give lessons.
                        We will contact you when group of students will gather nearby.
                    </p>
                    <div>
                        <div>
                            <div class="mdc-text-field mdc-text-field--upgraded" id="teacherPlaceInputWrap">
                                <input type="text" class="mdc-text-field__input" id="teacherPlaceInput" value="Warsaw" placeholder="" aria-controls="teacher-place-helper-text" aria-describedby="teacher-place-helper-text" required>
                                <label for="teacherPlaceInput" class="mdc-floating-label mdc-floating-label--float-above">Search for a place...</label>
                                <div class="mdc-line-ripple" style="transform-origin: 74.5px center 0px;"></div>
                            </div>
                            <p id="teacher-place-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                                Place where you would like to teach is required
                            </p>
                        </div>
                        <div>
                            <div class="mdc-text-field mdc-text-field--upgraded" id="teacherPlaceDistanceInputWrap">
                                <input type="number" class="mdc-text-field__input" id="teacherPlaceDistanceInput" value="15" aria-controls="teacher-place-distance-helper-text" aria-describedby="teacher-place-distance-helper-text" required min="1"/>
                                <label for="teacherPlaceDistanceInput" class="mdc-floating-label mdc-floating-label--float-above">Distance (km or miles)</label>
                                <div class="mdc-line-ripple" style="transform-origin: 74.5px center 0px;"></div>
                            </div>
                            <p id="teacher-place-distance-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                                Max distance from your preferred place is required
                            </p>
                        </div>
                    </div>
                    <div>
                        <div id="teacherMap" style="height: 400px; width: 100%; "/>
                    </div>
                    <p>
                        Choose dancing styles which you would like to teach. Write your email address so we could contact you if we are able to organize group of students.
                    </p>
                    <div>
                        <div class="mdc-text-field mdc-text-field--upgraded" id="teacherEmailInputWrap">
                            <input type="email" class="mdc-text-field__input" id="teacherEmailInput" aria-controls="teacher-email-helper-text" aria-describedby="teacher-email-helper-text" required>
                            <label for="teacherEmailInput" class="mdc-floating-label mdc-floating-label--float-above">Email</label>
                            <div class="mdc-line-ripple" style="transform-origin: 74.5px center 0px;"></div>
                        </div>
                        <p id="teacher-email-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                            Invalid email
                        </p>

                        <div id="teacher-dancing-styles" class="mdc-chip-set mdc-chip-set--filter" aria-controls="teacher-dancing-styles-helper-text" aria-describedby="teacher-dancing-styles-helper-text">
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Ballet</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Break dance</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Cha cha</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Disco dance</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Hip hop</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Jazz</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Modern</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Pop</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Rumba</div>
                            </div>
                            <div class=" mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Salsa</div>
                            </div>
                            <div class=" mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Swing</div>
                            </div>
                            <div class="mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Waltz</div>
                            </div>
                            <div class=" mdc-chip mdc-ripple-upgraded" tabindex="0">
                                <div class="mdc-chip__checkmark">
                                    <svg class="mdc-chip__checkmark-svg" viewBox="-2 -3 30 30">
                                        <path class="mdc-chip__checkmark-path" fill="none" stroke="black" d="M1.73,12.91 8.1,19.28 22.79,4.59"></path>
                                    </svg>
                                </div>
                                <div class="mdc-chip__text">Tango</div>
                            </div>
                        </div>
                        <p id="teacher-dancing-styles-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                            Choosing dancing style is required
                        </p>
                        <div>
                            <p id="subscribeTeacherSuccessMessage" class="mdc-text-field-helper-text mdc-text-field-helper-text mdc-text-field-helper-text--persistent" style="color: #018786; visibility: hidden" role="alert">
                                Signing up was successful
                            </p>
                            <p id="subscribeTeacherFailureMessage" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg mdc-text-field-helper-text--persistent" style="color: #b00020;margin-bottom: 8px;opacity: 1; visibility: hidden" role="alert">
                                Signing up was unsuccessful
                            </p>
                        </div>
                    </div>
                    <div>
                        <button class="mdc-button mdc-button--raised mdc-ripple-upgraded mdc-ripple-upgraded--foreground-activation" id="teacherSignUpButton">
                            Sign up
                        </button>
                    </div>
                </section>
                <section>
                    <h2>Sign up for dance floor renting</h2>
                    <p>Do you have unused dance floor? Would you like to rent a dance floor for couple of hours for dancing lessons?
                        Write your email address and mark on map your dance floor.
                    </p>
                    <div>
                        <div>
                            <div class="mdc-text-field mdc-text-field--upgraded" id="danceFloorOwnerPlaceInputWrap">
                                <input type="text" class="mdc-text-field__input" id="danceFloorOwnerPlaceInput" value="Warsaw" placeholder="" aria-controls="danceFloorOwner-place-helper-text" aria-describedby="danceFloorOwner-place-helper-text" required>
                                <label for="danceFloorOwnerPlaceInput" class="mdc-floating-label mdc-floating-label--float-above">Search for a place...</label>
                                <div class="mdc-line-ripple" style="transform-origin: 74.5px center 0px;"></div>
                            </div>
                            <p id="danceFloorOwner-place-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                                A place is required
                            </p>
                        </div>
                    </div>
                    <div>
                        <div id="danceFloorOwnerMap" style="height: 400px; width: 100%; "/>
                    </div>
                    <p>
                        Write your email address so we could contact you if we are able to rent your dance floor.
                    </p>
                    <div>
                        <div class="mdc-text-field mdc-text-field--upgraded" id="danceFloorOwnerEmailInputWrap">
                            <input type="email" class="mdc-text-field__input" id="danceFloorOwnerEmailInput" aria-controls="danceFloorOwner-email-helper-text" aria-describedby="danceFloorOwner-email-helper-text" required>
                            <label for="danceFloorOwnerEmailInput" class="mdc-floating-label mdc-floating-label--float-above">Email</label>
                            <div class="mdc-line-ripple" style="transform-origin: 74.5px center 0px;"></div>
                        </div>
                        <p id="danceFloorOwner-email-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                            Invalid email
                        </p>
                    </div>
                    <div>
                        <p id="subscribeDanceFloorOwnerSuccessMessage" class="mdc-text-field-helper-text mdc-text-field-helper-text mdc-text-field-helper-text--persistent" style="color: #018786; visibility: hidden" role="alert">
                            Signing up was successful
                        </p>
                        <p id="subscribeDanceFloorOwnerFailureMessage" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg mdc-text-field-helper-text--persistent" style="color: #b00020;margin-bottom: 8px;opacity: 1; visibility: hidden" role="alert">
                            Signing up was unsuccessful
                        </p>
                    </div>
                    <div>
                        <button class="mdc-button mdc-button--raised mdc-ripple-upgraded mdc-ripple-upgraded--foreground-activation" id="danceFloorOwnerSignUpButton">
                            Sign up
                        </button>
                    </div>
                </section>
                <section>
                    <h4>Unsubscribe</h4>
                    <p>You can unsubscribe from lists above at any moment. Just write your email.</p>
                    <div>
                        <div class="mdc-text-field mdc-text-field--upgraded" id="unsubscribeInputWrap">
                            <input type="email" class="mdc-text-field__input" id="unsubscribeEmailInput" aria-controls="unsubscribe-email-helper-text" aria-describedby="unsubscribe-email-helper-text" required>
                            <label for="dancerEmailInput" class="mdc-floating-label mdc-floating-label--float-above">Email</label>
                            <div class="mdc-line-ripple" style="transform-origin: 74.5px center 0px;"></div>
                        </div>
                        <p id="unsubscribe-email-helper-text" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg" role="alert">
                            Invalid email
                        </p>
                        <div>
                            <p id="unsubscribeSuccessMessage" class="mdc-text-field-helper-text mdc-text-field-helper-text mdc-text-field-helper-text--persistent" style="color: #018786; visibility: hidden" role="alert">
                                Unsubscribe was successful
                            </p>
                            <p id="unsubscribeFailureMessage" class="mdc-text-field-helper-text mdc-text-field-helper-text--validation-msg mdc-text-field-helper-text--persistent" style="color: #b00020;margin-bottom: 8px;opacity: 1; visibility: hidden" role="alert">
                                Unsubscribe was unsuccessful
                            </p>
                        </div>
                    </div>
                    <div>
                        <button class="mdc-button mdc-button--raised mdc-ripple-upgraded mdc-ripple-upgraded--foreground-activation" id="unsubscribeButton">
                            Unsubscribe
                        </button>
                    </div>
                </section>
            </div>
            <div class="mdc-layout-grid__cell mdc-layout-grid__cell--span-2">&nbsp;</div>
        </div>

    </jsp:body>
</t:genericPage>
