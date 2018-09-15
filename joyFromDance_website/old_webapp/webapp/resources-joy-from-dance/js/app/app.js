import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
// import {setPassiveTouchGestures, setRootPath} from '@polymer/polymer/lib/utils/settings.js';
// import '@polymer/app-layout/app-drawer/app-drawer.js';
// import '@polymer/app-layout/app-drawer-layout/app-drawer-layout.js';
// import '@polymer/app-layout/app-header/app-header.js';
// import '@polymer/app-layout/app-header-layout/app-header-layout.js';
// import '@polymer/app-layout/app-scroll-effects/app-scroll-effects.js';
// import '@polymer/app-layout/app-toolbar/app-toolbar.js';
// import '@polymer/app-route/app-location.js';
// import '@polymer/app-route/app-route.js';
// import '@polymer/iron-pages/iron-pages.js';
// import '@polymer/iron-selector/iron-selector.js';
// import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/paper-button/paper-button.js';

// Gesture events like tap and track generated from touch will not be
// preventable, allowing for better scrolling performance.
// setPassiveTouchGestures(true);

// Set Polymer's root path to the same value we passed to our service worker
// in `index.html`.
// setRootPath(JoyFromDanceGlobals.rootPath);

export class App extends PolymerElement {

    static get template() {
        return html`
        <paper-button class="pink">link</paper-button>
        <paper-button raised class="indigo">raised</paper-button>
        <paper-button toggles raised class="green">toggles</paper-button>
        <paper-button disabled class="disabled">disabled</paper-button>
        `;
    }

}

window.customElements.define('app', App);
