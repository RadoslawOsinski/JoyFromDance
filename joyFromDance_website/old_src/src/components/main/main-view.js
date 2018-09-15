import {html} from '@polymer/lit-element';
import {PageViewElement} from './../page-view-element.js';

// These are the shared styles needed by this element.
import {SharedStyles} from './../shared-styles.js';

class MainView extends PageViewElement {
    _render(props) {
        return html`
      ${SharedStyles}
      <section>
        <h2>Welcome</h2>
        <p>We are a startup which is trying to organize dance classes in an easy way</p>
      </section>      
    `;
    }

}

window.customElements.define('main-view', MainView);
