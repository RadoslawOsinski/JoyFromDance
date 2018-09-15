import { LitElement, html } from '@polymer/lit-element';
import { setPassiveTouchGestures } from '@polymer/polymer/lib/utils/settings.js';
import { connect } from 'pwa-helpers/connect-mixin.js';
import { installMediaQueryWatcher } from 'pwa-helpers/media-query.js';
import { installOfflineWatcher } from 'pwa-helpers/network.js';
import { installRouter } from 'pwa-helpers/router.js';
import { updateMetadata } from 'pwa-helpers/metadata.js';

// This element is connected to the Redux store.
import { store } from '../store.js';

// These are the actions needed by this element.
import {
  navigate,
  updateOffline,
  updateDrawerState,
  updateLayout
} from '../actions/joy-from-dance-app.js';

// These are the elements needed by this element.
import '@polymer/app-layout/app-drawer/app-drawer.js';
import '@polymer/app-layout/app-header/app-header.js';
import '@polymer/app-layout/app-scroll-effects/effects/waterfall.js';
import '@polymer/app-layout/app-toolbar/app-toolbar.js';
import { menuIcon } from './icons/my-icons.js';
import './snack-bar.js';

class JoyFromDanceApp extends connect(store)(LitElement) {
  _render({appTitle, _page, _drawerOpened, _snackbarOpened, _offline}) {
    // Anything that's related to rendering should be done in here.
    return html`
    <app-header condenses reveals effects="waterfall">
      <app-toolbar class="toolbar-top">
        <button class="menu-btn" title="Menu" on-click="${_ => store.dispatch(updateDrawerState(true))}">${menuIcon}</button>
        <div main-title>${appTitle}</div>
      </app-toolbar>

      <!-- This gets hidden on a small screen-->
      <nav class="toolbar-list">
        <a selected?="${_page === 'main'}" href="/main">Main</a>
        <a selected?="${_page === 'view2'}" href="/view2">View Two</a>
        <a selected?="${_page === 'view3'}" href="/view3">View Three</a>
        <a selected?="${_page === 'contact'}" href="/contact">Contact</a>
      </nav>
    </app-header>

    <!-- Drawer content -->
    <app-drawer opened="${_drawerOpened}"
        on-opened-changed="${e => store.dispatch(updateDrawerState(e.target.opened))}">
      <nav class="drawer-list">
        <a selected?="${_page === 'main'}" href="/">Main</a>
        <a selected?="${_page === 'view2'}" href="/view2">View Two</a>
        <a selected?="${_page === 'view3'}" href="/view3">View Three</a>
        <a selected?="${_page === 'contact'}" href="/contact">Contact</a>
      </nav>
    </app-drawer>

    <!-- Main content -->
    <main class="main-content">
      <main-view class="page" active?="${_page === 'main'}"></main-view>
      <my-view2 class="page" active?="${_page === 'view2'}"></my-view2>
      <my-view3 class="page" active?="${_page === 'view3'}"></my-view3>
      <contact-view class="page" active?="${_page === 'contact'}"></contact-view>
      <view-404 class="page" active?="${_page === 'view404'}"></view-404>
    </main>

    <footer>
      <p>Made with &hearts; by CWSFE.</p>
    </footer>

    <snack-bar active?="${_snackbarOpened}">
        You are now ${_offline ? 'offline' : 'online'}.</snack-bar>
    `;
  }

  static get properties() {
    return {
      appTitle: String,
      _page: String,
      _drawerOpened: Boolean,
      _snackbarOpened: Boolean,
      _offline: Boolean
    }
  }

  constructor() {
    super();
    // To force all event listeners for gestures to be passive.
    // See https://www.polymer-project.org/2.0/docs/devguide/gesture-events#use-passive-gesture-listeners
    setPassiveTouchGestures(true);
  }

  _firstRendered() {
    installRouter((location) => store.dispatch(navigate(window.decodeURIComponent(location.pathname))));
    installOfflineWatcher((offline) => store.dispatch(updateOffline(offline)));
    installMediaQueryWatcher(`(min-width: 460px)`,
        (matches) => store.dispatch(updateLayout(matches)));
  }

  _didRender(properties, changeList) {
    if ('_page' in changeList) {
      const pageTitle = properties.appTitle + ' - ' + changeList._page;
      updateMetadata({
          title: pageTitle,
          description: pageTitle
          // This object also takes an image property, that points to an img src.
      });
    }
  }

  _stateChanged(state) {
    this._page = state.joyFromDanceApp.page;
    this._offline = state.joyFromDanceApp.offline;
    this._snackbarOpened = state.joyFromDanceApp.snackbarOpened;
    this._drawerOpened = state.joyFromDanceApp.drawerOpened;
  }
}

window.customElements.define('joy-from-dance-app', JoyFromDanceApp);
