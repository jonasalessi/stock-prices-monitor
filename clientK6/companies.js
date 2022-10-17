import http from "k6/http";
import { check } from "k6";
import papaparse from 'https://jslib.k6.io/papaparse/5.1.1/index.js';
import { SharedArray } from 'k6/data';
import { scenario } from 'k6/execution';


const csvData = new SharedArray('Companies', function () {
  return papaparse.parse(open('./companies.csv'), { header: true }).data;
});

const companiesGroup = {};
csvData.forEach(row => {
  if (companiesGroup[row.name]) {
    companiesGroup[row.name].tickers.push(row.ticker);
  } else {
    companiesGroup[row.name] = { name: row.name, tickers: [row.ticker] };
  }
});
const data = Object.values(companiesGroup);

export const options = {
  scenarios: {
    'use-all-the-data': {
      executor: 'shared-iterations',
      vus: 50,
      iterations: data.length,
      maxDuration: '15m',
    },
  },
};

export default function () {
  const company = data[scenario.iterationInTest];
  if (!company.name || !company.tickers) return;
  const res = http.post(`http://host.docker.internal:8080/company`, company);
  check(res, {
    [`is company ${company.name} created`]: (res) => res.status === 201,
  });
}

