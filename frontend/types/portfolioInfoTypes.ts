export interface PortfolioInfo {
  id: number;
  portfolioName: string;
  description: string;
  portfolioHoldings: number;
}

export interface PortfolioStatsData {
  id: number;
  portfolioValue: number;
  portfolioInvested: number;
  gainOrLoss: number;
  gainOrLossPercent: number;
  portfolioHoldings: number;
  lastUpdatedAt: string;
  marketTimeStamp: string;
}

export interface DeletePortfolioInfo {
  id: number;
  portfolioName: string;
  description: string;
  type: "portfolio";
}

export interface EditPortfolioInfo {
  id: number;
  portfolioName: string;
  description: string;
  type: "portfolio";
}
