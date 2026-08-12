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