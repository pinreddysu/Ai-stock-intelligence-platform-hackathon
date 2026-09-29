import api from "@/api/client";

export interface GetHoldingsByPortfolioIdRequest {
  portfolioId: number;
}

export interface RefreshPortfolioHoldingsDataRequest {
  portfolioId: number;
}

export interface UpdatePortfiolioHoldingsRequest {
  id: number;
  shares: number;
  avgPrice: number;
}

export interface DeletePortfolioHoldingRequest {
  id: number;
}

export interface AddPortfolioHoldingRequest {
  portfolioId: number;
  stockId: number;
  ticker: string;
  shares: number;
  avgPrice: number;
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
  async refreshPortfolioHoldingsData(
    refreshPortfolioHoldingsDataRequest: RefreshPortfolioHoldingsDataRequest,
  ) {
    const response = await api.post(
      "/getAllHoldings/refresh",
      refreshPortfolioHoldingsDataRequest,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      },
    );
    return response;
  },
  async updatePortfolioHoldings(
    updatePortfolioHoldingsRequest: UpdatePortfiolioHoldingsRequest,
  ) {
    const response = await api.patch(
      "/updateHoldings",
      updatePortfolioHoldingsRequest,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      },
    );
    return response;
  },
  async deletePortfolioHoldings(
    deletePortfolioHoldingRequest: DeletePortfolioHoldingRequest,
  ) {
    const response = await api.delete("/removeHoldings", {
      data: deletePortfolioHoldingRequest,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    return response;
  },
  async addHoldingsList(
    addPortfolioHoldingRequest: AddPortfolioHoldingRequest[],
  ) {
    const response = await api.post("/addStocks", addPortfolioHoldingRequest, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    return response;
  },
};

export default portfolioHoldingService;
