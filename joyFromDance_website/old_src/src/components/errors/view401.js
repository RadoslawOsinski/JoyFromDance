import { html } from '@polymer/lit-element';
import { PageViewElement } from './../page-view-element.js';

import { SharedStyles } from './../shared-styles.js';

class View401 extends PageViewElement {
  _render(props) {
    return html`
      ${SharedStyles}
      <section>
        <h2>Unauthorized</h2>
        <p>The page you're looking for exists but you are unauthorized. Please login
           <a href="/">home</a>.
        </p>
      </section>
    `
  }
}

window.customElements.define('view-401', View401);
