import {html} from '@polymer/lit-element';
import {PageViewElement} from './../page-view-element.js';
import {MDCRipple} from '@material/ripple';

import {SharedStyles} from './../shared-styles.js';

class ContactView extends PageViewElement {
    _render(props) {
        console.log('render!');
        return html`
      ${SharedStyles}
      <section>
        <h2>Contact</h2>
        <!--<p>Contact informations</p>-->
        <paper-contact-list style="width: 300px;">
            <paper-contact-email>info@joyfrom.dance</paper-contact-email>
            <paper-contact-email><a target="_blank" href="/pgp/Radoslaw.Osinski@cwsfe.pl.asc">GPG Radoslaw.Osinski@cwsfe.pl</a></paper-contact-email>
            <paper-contact-email><a target="_blank" href="/pgp/Radoslaw.Osinski@gmail.com.asc">GPG Radoslaw.Osinski@gmail.com</a></paper-contact-email>
            <paper-contact-mobile>+48 791-101-335</paper-contact-mobile>
            <paper-contact-address latitude="51.5287718" longitude="-0.2416798">06-430 Sońsk, ul Ogrodowa 6, Poland</paper-contact-address>
            <paper-contact-address latitude="51.5287718" longitude="-0.2416798">Plus Code: 9G42QMGX+6C</paper-contact-address>
        </paper-contact-list>
      </section>
      <button class="mdc-button" id="bbbc">
          Button
        </button>
      <section>
        <h2>Issues & features</h2>
        <p>Issues and features requests can be created at: <a href="https://gitlab.com/OsinskiRadoslaw/JoyFromDance/issues" target="_blank">Reporting</a></p>
      </section>
    `;
    }

    constructor() {
        super();
        this.language = 'en';
        this.resources = {
            'en': { 'contact': 'Contact' },
            'pl': { 'contact': 'Kontakt' }
        };
        console.log('const');
        // new MDCRipple(document.querySelector('mdc-button'))
    }

    ready() {
        super.ready();
        console.log('ready');
        console.log(this);
        console.log(this.shadowRoot.querySelector('#bbbc'));
        try {
            const z = new MDCRipple(this.shadowRoot.querySelector('#bbbc'));
            console.log('zzz: ' + z);
        } catch (e) {
            console.log(e)
        }
    }
}

window.customElements.define('contact-view', ContactView);
