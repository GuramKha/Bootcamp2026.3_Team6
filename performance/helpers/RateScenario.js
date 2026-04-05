import { TEST_DATA } from '../data/Constants.js';
import { pickRandom } from './Random.js';

export function buildRateScenario() {
    return {
        pair: pickRandom(TEST_DATA.RATE_CURRENCY_PAIRS),
    };
}