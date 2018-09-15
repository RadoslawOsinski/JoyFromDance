export class I18nLocale {

    constructor() {
        this.i18nMap = new Map();
        this.initializeI18nMap();
    }

    initializeI18nMap() {
    }

    acceptsLang(lang) {
        return false;
    }

    i18n(key) {
        let i18nResult = this.i18nMap.get(key);
        return i18nResult === null ? '___' + key + '___' : i18nResult;
    }
}
