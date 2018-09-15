import {I18nLocale} from './i18nLocale.js';

export class I18nEn extends I18nLocale {

    constructor() {
        super();
    }

    acceptsLang(lang) {
        return 'en' === lang || 'EN' === lang || 'EN_en' === lang || 'EN_gb' === lang;
    }

    initializeI18nMap() {
        this.i18nMap.set('Add', 'Add');
        this.i18nMap.set('AcceptPair', 'Accept pair');
        this.i18nMap.set('ChooseRoom', 'Choose room');
        this.i18nMap.set('MissingPair', 'Missing pair');
        this.i18nMap.set('AssignPairByDroppingYourEmail', 'Assign pair by dropping your email');
        this.i18nMap.set('AgreedPair', 'Agreed pair');
        this.i18nMap.set('AcceptanceOfTheOtherPerson', 'Acceptance of the other person');
        this.i18nMap.set('Delete', 'Delete');
        this.i18nMap.set('Preview', 'Preview');
        this.i18nMap.set('DataFetchingProblem', 'Fetching data failed ...');
        this.i18nMap.set('NoResults', 'No results');
    }
}
