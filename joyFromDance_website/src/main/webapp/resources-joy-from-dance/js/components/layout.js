export class LayoutView {

    constructor() {
        const mdc = window.mdc;
        var appBarEl = document.getElementById('demo-top-app-bar');
        new mdc.topAppBar.MDCTopAppBar(appBarEl);

        var drawerEl = document.querySelector('.mdc-drawer');
        var drawer = new mdc.drawer.MDCTemporaryDrawer(drawerEl);

        appBarEl.addEventListener('MDCTopAppBar:nav', function () {
            drawer.open = true;
        });
        document.querySelector('#backMenuLink').addEventListener('click', function () {
            drawer.open = false;
        });
    }

}
