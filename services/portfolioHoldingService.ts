import api from "@/api/client";

export interface GetHoldingsByPortfolioIdRequest {
  portfolioId: number;
}

export interface UpdatePortfolioHoldingsDataRequest {
  portfolioId: number;
}

const portfolioHoldingService = {
  async getHoldingsByPortfolioId(
    getHoldingsByPortfolioIdRequest: GetHoldingsByPortfolioIdRequest,
    keyword?: string,
    size?: number,
    page?: number,
  ) {
    const response = await api.post(
      "/getAllHoldings",
      getHoldingsByPortfolioIdRequest,
      {
        params: { keyword: keyword || undefined, size: size, page: page },
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      },
    );
    return response;
  },
  async updatePortfolioHoldingsData(
    updatePortfolioHoldingsDataRequest: UpdatePortfolioHoldingsDataRequest,
  ) {
    const response = await api.post(
      "/getAllHoldings/refresh",
      updatePortfolioHoldingsDataRequest,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      },
    );
    return response;
  },
};

export default portfolioHoldingService;
