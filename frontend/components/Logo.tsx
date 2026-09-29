"use client"
import { Box, Typography } from "@mui/material";

export default function Logo() {
  return (
    <Box sx={{ display: "flex", gap: 2, alignItems: "center" }}>
      <Typography
        variant="h3"
        sx={{ fontWeight: "bold", color: "common.white" }}
      >
        PIE
      </Typography>

      <Typography variant="subtitle2" sx={{ color: "common.white" }}>
        AI Portfolio
        <br />
        Intelligence Engine
      </Typography>
    </Box>
  );
}
