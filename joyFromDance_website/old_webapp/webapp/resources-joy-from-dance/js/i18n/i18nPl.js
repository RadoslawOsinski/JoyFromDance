import {I18nLocale} from './i18nLocale.js';

export class I18nPl extends I18nLocale {

    constructor() {
        super();
    }

    acceptsLang(lang) {
        return 'pl' === lang || 'PL_pl' === lang || 'PL' === lang;
    }

    initializeI18nMap() {
        this.i18nMap.set('Add', 'Dodaj');
        this.i18nMap.set('AcceptPair', 'Akceptuj parę');
        this.i18nMap.set('ChooseRoom', 'Wybierz salę');
        this.i18nMap.set('MissingPair', 'Brak pary');
        this.i18nMap.set('AssignPairByDroppingYourEmail', 'Przypisz parę przez upuszczenie swojego email');
        this.i18nMap.set('AgreedPair', 'Para potwierdzona');
        this.i18nMap.set('AcceptanceOfTheOtherPerson', 'Akceptacja drugiej osoby');
        this.i18nMap.set('Delete', 'Usuń');
        this.i18nMap.set('Preview', 'Podgląd');
        this.i18nMap.set('DataFetchingProblem', 'Problem z pobraniem danych ...');
        this.i18nMap.set('NoResults', 'Brak wyników');
    }
}
