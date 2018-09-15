import {I18nPl} from './i18nPl.js';
import {I18nEn} from './i18nEn.js';

export class I18n {

    constructor() {
        this.i18nLocales = [
            new I18nPl(),
            new I18nEn()
        ];
    }

    i18n(key) {
        const lang = navigator.language || navigator.userLanguage;
        return this.keyI18n(lang, key);
    }

    keyI18n(lang, key) {
        let i18nValue = key;
        if (this.i18nLocales !== undefined) {
            for (let i18nLocale of this.i18nLocales) {
                if (i18nLocale.acceptsLang(lang)) {
                    i18nValue = i18nLocale.i18n(key);
                    return i18nValue;
                }
            }
        }
        return 'Unsupported language for key: ' + i18nValue;
    }

    static acceptsLang(lang) {
        return false;
    }
}
