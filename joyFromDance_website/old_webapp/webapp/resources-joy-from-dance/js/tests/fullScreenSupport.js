export class FullScreenSupport {

    static isFullScreenSupported() {
        return document.fullscreenEnabled || document.mozFullScreenEnabled || document.webkitFullscreenEnabled;
    }

    static toggleFullscreen(elementId) {
        let element = document.getElementById(elementId);
        screenfull.toggle(element);
    }

}
