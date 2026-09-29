export interface StockLists {
  id: number;
  ticker: string;
  companyName: string;
}

export interface StockListData {
  totalElements: number;
  stockLists: StockLists[];
}

export interface StockMoreDetails {
  id: number;
  ticker: string;
  companyName: string;
  sector: string;
  industry: string;
  exchange: string;
  prices: number;
  change: number;
  changePercent: number;
  previousClose: number;
  marketTimeStamp: string;
  lastUpdatedAt: string;
}

export interface AddHoldingsData {
  portfolioId: number;
  stockId: number;
  ticker: string;
  companyName: string;
  shares: number;
  avgPrice: number;
}
