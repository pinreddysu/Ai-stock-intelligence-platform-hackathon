import api from "@/api/client";

export interface LoginRequest {
  email: string;
  password: string;
}

export interface CreateRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

const authService = {
  async login(request: LoginRequest) {
    const response = await api.post("/Login", request);

    return response;
  },

  async create(request: CreateRequest) {
    const response = await api.post("/Create", request);
    return response.data;
  },
};

export default authService;
