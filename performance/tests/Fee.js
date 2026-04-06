import http from 'k6/http';
import { check, sleep } from 'k6';
import { CONFIG } from '../data/Constants.js';
import { buildFeeScenario } from '../helpers/FeeScenario.js';
import { jUnit, textSummary } from 'https://jslib.k6.io/k6-summary/0.0.2/index.js';

export const options = {
    stages: [
        { duration: '20s', target: 10 },
        { duration: '30s', target: 10 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        'http_req_duration{endpoint:fees}': ['p(95)<2500','p(99)<4000',],
        'http_req_failed{endpoint:fees}': ['rate<0.02'],
        checks: ['rate>0.98'],
    },
};

export default function () {
    const fee = buildFeeScenario();
    const feeUrl = `${CONFIG.BASE_URL}/moneyTransfer/fees?amount=${fee.amount}&currencyCode=${fee.currency}&receiveCountryCode=${fee.country}`;

    const feeRes = http.get(feeUrl, {
        tags: { endpoint: 'fees' },
    });

    check(feeRes, {
        'fee API status is 200': (r) => r.status === 200,
    });

    sleep(CONFIG.THINK_TIME);
}

export function handleSummary(data) {
    return {
        'stdout': textSummary(data, { indent: ' ', enableColors: true }),
        'target/allure-results-performance/fees-report.xml': jUnit(data),
    };
}
