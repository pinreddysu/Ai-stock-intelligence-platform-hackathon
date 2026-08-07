"use client";

import { Box, Typography } from "@mui/material";

export default function PortfolioPageHeader() {
  return (
    <Box>
      <Typography
        variant="h4"
        sx={{ fontWeight: "bold", color: "common.white" }}
      >
        My Portfolios
      </Typography>
      <Typography variant="h5" sx={{ mt: 5, color: "text.secondary" }}>
        Manage your investment portfolios
      </Typography>
    </Box>
  );
}
