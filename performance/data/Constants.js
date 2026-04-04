export const CONFIG = {
    BASE_URL: 'https://apigw.tbcbank.ge/api/v1',
    THINK_TIME: 1,
    REQUEST_TIMEOUT: '10s',
};

export const TEST_DATA = {
    AMOUNTS: [50, 75, 100, 150, 200, 300, 400, 500, 650, 800, 1000, 1200, 1500],
    RATE_CURRENCY_PAIRS: [
        { from: 'GEL', to: 'USD' },
        { from: 'USD', to: 'GEL' },
        { from: 'GEL', to: 'EUR' },
        { from: 'EUR', to: 'GEL' },
        { from: 'GEL', to: 'GBP' },
        { from: 'GBP', to: 'GEL' },
    ],
    FEE_CURRENCIES: ['GEL'],
    COUNTRIES: ['USA', 'CAN', 'GBR', 'DEU', 'ITA', 'TUR', 'FRA', 'ESP', 'POL', 'NLD'],
};