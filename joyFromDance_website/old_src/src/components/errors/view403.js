import { html } from '@polymer/lit-element';
import { PageViewElement } from './../page-view-element.js';

import { SharedStyles } from './../shared-styles.js';

class View403 extends PageViewElement {
  _render(props) {
    return html`
      ${SharedStyles}
      <section>
        <h2>Access forbidden</h2>
        <p>The page you're looking for exists but you don't have expected permissions. Head back
           <a href="/">home</a>.
        </p>
      </section>
    `
  }
}

window.customElements.define('view-403', View403);
