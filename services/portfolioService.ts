import api from "@/api/client";

export interface EditPortfolioRequest {
  id: number;
  portfolioName: string | null;
  description: string;
}

export interface CreatePortfolioRequest {
  portfolioName: string;
  description: string;
}

export interface DeletePortfolioRequest {
  id: number;
}

const portfolioService = {
  async createPortfolio(createPortfolioRequest: CreatePortfolioRequest) {
    const response = await api.post(
      "/createPortfolio",
      createPortfolioRequest,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      },
    );
    return response.data;
  },
  async getAllUserPortfolio(value?: string) {
    const response = await api.get("/myPortfolios", {
      params: { keyword: value || undefined },
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    return response;
  },
  async editUserPortfolio(editPortfolioRequest: EditPortfolioRequest) {
    const response = await api.patch(
      "/editPortfolioDetails",
      editPortfolioRequest,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      },
    );
    return response.data;
  },
  async deleteUserPortfolioById(
    deleteUserPortfolioRequest: DeletePortfolioRequest,
  ) {
    const response = await api.delete("/deletePortfolio", {
      data: deleteUserPortfolioRequest,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    response.data;
  },
};

export default portfolioService;
