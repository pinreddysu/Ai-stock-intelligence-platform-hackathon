"use client";
import { Box, Button } from "@mui/material";
import ArrowBackOutlinedIcon from "@mui/icons-material/ArrowBackOutlined";
import RefreshOutlinedIcon from "@mui/icons-material/RefreshOutlined";
import { useRouter } from "next/navigation";
import portfolioHoldingService from "@/services/portfolioHoldingService";
import { PortfolioDetailsAndHoldings } from "@/types/portfolioHoldingsTypes";

type RefreshProps = {
  portfolioId: number;
  refreshData: (data: PortfolioDetailsAndHoldings) => void;
};

export default function PortfolioDetailsPageHeader({
  portfolioId,
  refreshData,
}: RefreshProps) {
  const updatePortfolioHoldingsData = async () => {
    try {
      const response =
        await portfolioHoldingService.updatePortfolioHoldingsData({
          portfolioId,
        });
      refreshData(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const router = useRouter();

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
      <Box>
        <Button
          variant="outlined"
          size="small"
          onClick={() => router.replace("/portfolio")}
          startIcon={<ArrowBackOutlinedIcon />}
        >
          Back To Portfolio List
        </Button>
      </Box>

      <Box>
        <Button
          variant="outlined"
          size="small"
          onClick={updatePortfolioHoldingsData}
          startIcon={<RefreshOutlinedIcon />}
        >
          Refresh portfolio
        </Button>
      </Box>
    </Box>
  );
}
