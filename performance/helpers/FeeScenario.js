import { TEST_DATA } from '../data/Constants.js';
import { pickRandom } from './Random.js';

export function buildFeeScenario() {
    return {
        amount: pickRandom(TEST_DATA.AMOUNTS),
        currency: pickRandom(TEST_DATA.FEE_CURRENCIES),
        country: pickRandom(TEST_DATA.COUNTRIES),
    };
}
