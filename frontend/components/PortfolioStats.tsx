import { PortfolioStatsData } from "@/types/portfolioInfoTypes";
import { Box, Typography } from "@mui/material";

export default function PortfolioStats({
  props,
}: {
  props: PortfolioStatsData;
}) {
  return (
    <>
      <Box
        sx={{
          display: "flex",
          flexDirection: { xs: "column", sm: "row" },
          justifyContent: { sm: "space-between" },
          gap: 2,
          width: "100%",
        }}
      >
        <Box>
          <Typography>Portfolio Value:</Typography>
          <Typography
            color={
              props.portfolioValue > props.portfolioInvested
                ? "success"
                : "error"
            }
          >
            ${`${props.portfolioValue.toFixed(2)}`}
          </Typography>
        </Box>
        <Box>
          <Typography>Total Gain/Loss:</Typography>
          <Typography color={props.gainOrLoss > 0 ? "success" : "error"}>
            {props.gainOrLoss > 0
              ? `+ $${props.gainOrLoss.toFixed(2)} (+ ${props.gainOrLossPercent.toFixed(2)}%)`
              : `$${props.gainOrLoss} (- ${props.gainOrLossPercent.toFixed(2)})`}
          </Typography>
        </Box>
        <Box>
          <Typography>Invested:</Typography>
          <Typography>${props.portfolioInvested.toFixed(2)}</Typography>
        </Box>

        <Box>
          <Typography>Holdings:</Typography>
          {props.portfolioHoldings}
        </Box>
      </Box>
    </>
  );
}
