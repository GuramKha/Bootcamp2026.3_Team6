import http from 'k6/http';
import { check, sleep } from 'k6';
import { CONFIG } from '../data/Constants.js';
import { buildRateScenario } from '../helpers/RateScenario.js';


export const options = {
    stages: [
        { duration: '20s', target: 40 },
        { duration: '30s', target: 40 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        'http_req_duration{endpoint:rate}': ['p(95)<800', 'p(99)<1200'],
        'http_req_failed{endpoint:rate}': ['rate<0.01'],
        checks: ['rate>0.99'],
    },
};

export default function () {
    const scenario = buildRateScenario();
    const rateUrl = `${CONFIG.BASE_URL}/exchangeRates/getMoneyTransferRate?Iso1=${scenario.pair.from}&Iso2=${scenario.pair.to}`;

    const res = http.get(rateUrl, {
        tags: { endpoint: 'rate' },
    });


    check(res, {
        'rate API status is 200': (r) => r.status === 200,
        'rate API body is not empty': (r) => r.body.length > 0,
    });

    sleep(CONFIG.THINK_TIME);
}
