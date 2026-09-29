"use client";
import { Alert, Box, Button, Snackbar } from "@mui/material";
import RefreshOutlinedIcon from "@mui/icons-material/RefreshOutlined";
import portfolioHoldingService from "@/services/portfolioHoldingService";
import { PortfolioDetailsAndHoldings } from "@/types/portfolioHoldingsTypes";
import { HttpStatusCode } from "axios";
import { useState } from "react";
import BackPageRedirection from "./BackPageRedirection";

type RefreshProps = {
  portfolioId: number;
  searchValue: string;
  size: number;
  page: number;
  portfolioHoldingsLength: number;
  refreshData: (data: PortfolioDetailsAndHoldings) => void;
};

export default function PortfolioDetailsPageHeader({
  portfolioId,
  searchValue,
  size,
  page,
  portfolioHoldingsLength,
  refreshData,
}: RefreshProps) {
  const [open, setOpen] = useState(false);
  const updatePortfolioHoldingsData = async () => {
    try {
      const response =
        await portfolioHoldingService.refreshPortfolioHoldingsData({
          portfolioId,
        });
      if (response.status === HttpStatusCode.Ok) {
        const data = await portfolioHoldingService.getHoldingsByPortfolioId(
          { portfolioId },
          searchValue,
          size,
          page,
        );
        refreshData(data.data);
      }
    } catch (error) {
      setOpen(true);
    }
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: {
          xs: "column",
          sm: "row",
        },
        justifyContent: "space-between",
        alignItems: {
          xs: "stretch",
          sm: "center",
        },
        width: "100%",
        gap: 1,
      }}
    >
      <BackPageRedirection pageName="portfolioHoldings" route="portfolio" />

      <Box>
        <Button
          variant="outlined"
          size="small"
          onClick={updatePortfolioHoldingsData}
          startIcon={<RefreshOutlinedIcon />}
          disabled={portfolioHoldingsLength === 0}
        >
          Refresh portfolio
        </Button>
      </Box>
      <Snackbar
        anchorOrigin={{ horizontal: "center", vertical: "bottom" }}
        open={open}
        autoHideDuration={1000}
        onClose={() => setOpen(false)}
      >
        <Alert
          onClose={() => setOpen(false)}
          severity="error"
          variant="filled"
          sx={{ width: "100%" }}
        >
          Error refreshing holdings
        </Alert>
      </Snackbar>
    </Box>
  );
}
