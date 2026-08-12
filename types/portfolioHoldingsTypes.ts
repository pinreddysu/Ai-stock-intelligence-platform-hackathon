export interface Holdings {
  holdingId: number;
  stockId: number;
  ticker: string;
  companyName: string;
  exchange: string;
  sector: string;
  industry: string;
  shares: number;
  avgPrice: number;
  price: number;
  previousClose: number;
  dailyChange: number;
  dailyChangePercent: number;
  marketTimeStamp: string;
  updatedAt: string;
  allocation: number;
  currentValue: number;
  gainOrLoss: number;
  gainOrLossPercent: number;
  investedAmount: number;
}

export interface PortfolioDetailsAndHoldings {
  id: number;
  portfolioName: string;
  description: string;
  portfolioHoldings: number;
  filteredPortfolioHoldings: number;
  portfolioValue: number;
  portfolioInvested: number;
  gainOrLoss: number;
  gainOrLossPercent: number;
  lastUpdatedAt: string;
  marketTimeStamp: string;
  holdings: Holdings[];
}

export interface ColumnData {
  dataKey: keyof Holdings;
  label: string;
  numeric?: boolean;
  width?: number;
}

export const columns: ColumnData[] = [
  {
    width: 100,
    label: "Ticker",
    dataKey: "ticker",
  },
  {
    width: 100,
    label: "Shares",
    dataKey: "shares",
    numeric: true,
  },
  {
    width: 100,
    label: "Avg",
    dataKey: "avgPrice",
    numeric: true,
  },
  {
    width: 100,
    label: "Price",
    dataKey: "price",
    numeric: true,
  },
  {
    width: 100,
    label: "Value",
    dataKey: "currentValue",
    numeric: true,
  },
  {
    width: 100,
    label: "Allocation",
    dataKey: "allocation",
  },
  {
    width: 100,
    label: "Gain %",
    dataKey: "gainOrLossPercent",
  },
];
