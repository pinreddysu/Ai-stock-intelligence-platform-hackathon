import api from "@/api/client";

export const stocksService = {
  async getBasicStocksInfo(keyword: string, page: number, portfolioId: number) {
    const response = await api.get("/getStocks", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      params: { portfolioId, keyword: keyword, page: page },
    });
    return response;
  },
  async getStockMoreDetails(ticker: string) {
    const response = api.get("/getStockMoreDetails", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      params: { ticker: ticker.trim() },
    });
    return response;
  },
};
